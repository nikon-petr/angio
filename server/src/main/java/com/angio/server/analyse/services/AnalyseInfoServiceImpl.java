package com.angio.server.analyse.services;

import com.angio.server.AngioAppProperties;
import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.entities.VesselEntity;
import com.angio.server.analyse.repositories.AnalyseGeometricCrudRepository;
import com.angio.server.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.server.analyse.repositories.PatientCrudRepository;
import com.angio.server.analyse.repositories.VesselCrudRepository;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.util.image.ImageOperation;
import com.angio.server.util.matlab.bloodflow.BloodFlowAnalyseAdapter;
import com.angio.server.util.matlab.bloodflow.BloodFlowAnalyseResult;
import com.angio.server.util.matlab.geometric.GeometricAnalyseAdapter;
import com.angio.server.util.matlab.geometric.model.GeometricAnalyseModel;
import com.angio.server.util.matlab.geometric.model.VesselModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mathworks.toolbox.javabuilder.MWException;

import java.io.IOException;
import java.sql.Timestamp;

@Service("analyseInfoService")
@Transactional
public class AnalyseInfoServiceImpl implements AnalyseInfoService {

    AnalyseInfoCrudRepository analyseInfoCrudRepository;
    PatientCrudRepository patientCrudRepository;
    AnalyseGeometricCrudRepository analyseGeometricCrudRepository;
    VesselCrudRepository vesselCrudRepository;
    private final AnalyseBloodFlowService analyseBloodFlowService;
    private final AngioAppProperties angioAppProperties;


    @Autowired
    public AnalyseInfoServiceImpl(
            AnalyseInfoCrudRepository analyseInfoCrudRepository,
            PatientCrudRepository patientCrudRepository,
            AnalyseGeometricCrudRepository analyseGeometricCrudRepository,
            VesselCrudRepository vesselCrudRepository,
            AnalyseBloodFlowService analyseBloodFlowService,
            AngioAppProperties angioAppProperties) {
        this.analyseInfoCrudRepository = analyseInfoCrudRepository;
        this.patientCrudRepository = patientCrudRepository;
        this.analyseGeometricCrudRepository = analyseGeometricCrudRepository;
        this.vesselCrudRepository = vesselCrudRepository;
        this.analyseBloodFlowService = analyseBloodFlowService;
        this.angioAppProperties = angioAppProperties;
    }

    @Override
    public Page<AnalyseInfoEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception {
        if (search == null) search = "";
        if (date == null) date = "";

        String sortField = null;
        boolean isAscending = false;
        if (pageable.getSort() != null) {
            String[] sortData = pageable.getSort().toString().replaceAll(" ", "").split(":");
            sortField = sortData[0];
            isAscending = sortData[1].equals("ASC");
        }

        String formattedDate = null;
        if (!date.equals("")){
            String[] values = date.split("-"); //dd-MM-yyyy
            formattedDate = values[2] + "-" + values[1] + "-" + values[0]; //yyyy-MM-dd
        }

        if (sortField != null && (sortField.equals("patient") || sortField.equals("policy") || sortField.equals("user"))){
            String sortProperty = null;
            switch (sortField){
                case "patient":
                    sortProperty = "patient.lastname";
                    break;
                case "policy":
                    sortProperty = "patient.policy";
                    break;
                case "user":
                    sortProperty = "user.userInfo.lastname";
                    break;
            }
            if (formattedDate == null) {
                return analyseInfoCrudRepository.findAll(search, new PageRequest(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        new Sort(
                                new Sort.Order(
                                        isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
                                        sortProperty)
                        )));
            } else{
                return analyseInfoCrudRepository.findAll(search, formattedDate, new PageRequest(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        new Sort(
                                new Sort.Order(
                                        isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
                                        sortProperty)
                        )));
            }
        } else{
            if (formattedDate == null) return analyseInfoCrudRepository.findAll(search, pageable);
            else return analyseInfoCrudRepository.findAll(search, formattedDate, pageable);
        }
    }

    @Override
    @Transactional
    public AnalyseInfoEntity addNewAnalyseInfo(UserEntity user, PatientEntity patient, AnalyseInfoRequest analyseInfoRequest) throws Exception {
        PatientEntity patientEntityFromDB = patientCrudRepository.findByPolicy(patient.getPolicy()).stream()
                .findFirst()
                .orElse(null);

        PatientEntity patientEntity = null;
        if (patientEntityFromDB == null) {
            patientEntity = patientCrudRepository.save(patient);
        } else {
            patientEntityFromDB.setFirstname(patient.getFirstname());
            patientEntityFromDB.setLastname(patient.getLastname());
            patientEntityFromDB.setPatronymic(patient.getPatronymic());
            patientEntityFromDB.setEmail(patient.getEmail());
            patientEntityFromDB.setPhone(patient.getPhone());
            patientEntityFromDB.setBday(patient.getBday());
            patientEntityFromDB.setLocation_address(patient.getLocation_address());
            patientEntityFromDB.setWork_address(patient.getWork_address());
            patientEntityFromDB.setComment(patient.getComment());
            patientEntityFromDB.setPolicy(patient.getPolicy());
            patientEntity = patientCrudRepository.save(patientEntityFromDB);
        }

        ImageOperation imageOperation = new ImageOperation();
        String imgFileName = imageOperation.save(analyseInfoRequest.getImg());
        AnalyseInfoEntity analyseInfoEntity = new AnalyseInfoEntity(user, patientEntity, analyseInfoRequest.getName(),
                analyseInfoRequest.getShortDescription(), analyseInfoRequest.getFullDescription(), analyseInfoRequest.getAnalyseType(),
                analyseInfoRequest.getComment(), imgFileName, new Timestamp(System.currentTimeMillis()), "", false);
        analyseInfoEntity = analyseInfoCrudRepository.save(analyseInfoEntity);

        return analyseInfoEntity;
    }

    @Override
    public AnalyseInfoEntity startNewAnalyse(long id) throws Exception {
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findOne(id);

        GeometricAnalyseModel geometricAnalyseModel = new GeometricAnalyseAdapter().runAnalyse(analyseInfoEntity.getImg());

        ImageOperation imageOperation = new ImageOperation();
        String binarizedImage = imageOperation.save(geometricAnalyseModel.getBinarized());
        String skelImage = imageOperation.save(geometricAnalyseModel.getSkel());
        AnalyseGeometricEntity analyseGeometricEntity = analyseGeometricCrudRepository.save(new AnalyseGeometricEntity(
                analyseInfoEntity, binarizedImage, skelImage));
        for (VesselModel vesselModel: geometricAnalyseModel.getAnalyseResult()) {
            String vesselImage = imageOperation.save(vesselModel.getVesselImage());
            String mainVessel = imageOperation.save(vesselModel.getMainVessel());
            vesselCrudRepository.save(new VesselEntity(analyseGeometricEntity, vesselImage, mainVessel,
                    (float) vesselModel.getTortuosity(), vesselModel.getCountOfBranchesOf1Orders(),
                    (float) vesselModel.getBranching(), (float) vesselModel.getArea(), (float) vesselModel.getAreaPercent()));
        }
        analyseInfoEntity.setFinished(true);
        analyseInfoEntity.setAnalyseDate(new Timestamp(System.currentTimeMillis()));
        analyseInfoEntity = analyseInfoCrudRepository.save(analyseInfoEntity);

//      Blood flow analyse
        String originalImage = new ClassPathResource(angioAppProperties.getAnalyseImagesDirectory() + analyseInfoEntity.getImg())
                .getFile()
                .getAbsolutePath();
        BloodFlowAnalyseResult bloodFlowAnalyseResult = new BloodFlowAnalyseAdapter().runAnalyse(originalImage);

        String ishemiaImagePath = imageOperation.save(bloodFlowAnalyseResult.getIshemiaImage());
        String densityImagePath = imageOperation.save(bloodFlowAnalyseResult.getCapillarDensityImage());

        analyseBloodFlowService.addNewAnalyse(analyseInfoEntity, ishemiaImagePath, densityImagePath, bloodFlowAnalyseResult);

        analyseInfoEntity.setFinished(true);
        analyseInfoCrudRepository.save(analyseInfoEntity);

        return analyseInfoEntity;
    }

    @Override
    public AnalyseInfoEntity getAnalyseInfoEntity(long id) throws Exception {
        return analyseInfoCrudRepository.findOne(id);
    }

    @Override
    public AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) throws Exception {
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findOne(id);
        analyseInfoEntity.setConclusion(conclusion);

        return analyseInfoCrudRepository.save(analyseInfoEntity);
    }

    @Override
    public void deleteAnalyse(long id) throws Exception {
        analyseInfoCrudRepository.delete(id);
    }

    @Override
    public long getCountOfAnalyses(String search, String date) throws Exception {
        if (date == null) date = "";
        String formattedDate = null;
        if (!date.equals("")){
            String[] values = date.split("-"); //dd-MM-yyyy
            formattedDate = values[2] + "-" + values[1] + "-" + values[0]; //yyyy-MM-dd
        }
        return date.equals("") ? analyseInfoCrudRepository.count(search) : analyseInfoCrudRepository.count(search, formattedDate);
    }
}