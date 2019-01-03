package com.angio.server.analyse.services.impl;

import com.angio.server.AngioAppProperties;
import com.angio.server.analyse.entities.*;
import com.angio.server.analyse.mappers.AnalyseInfoMapper;
import com.angio.server.analyse.mappers.PatientMapper;
import com.angio.server.analyse.repositories.AnalyseGeometricCrudRepository;
import com.angio.server.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.server.analyse.repositories.PatientCrudRepository;
import com.angio.server.analyse.repositories.VesselCrudRepository;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import com.angio.server.analyse.requests.NewAnalyseRequest;
import com.angio.server.analyse.requests.PatientRequest;
import com.angio.server.analyse.services.AnalyseBloodFlowService;
import com.angio.server.analyse.services.AnalyseInfoService;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.util.image.ImageOperation;
import com.angio.server.util.matlab.bloodflow.BloodFlowAnalyseAdapter;
import com.angio.server.util.matlab.bloodflow.BloodFlowAnalyseResult;
import com.angio.server.util.matlab.geometric.GeometricAnalyseAdapter;
import com.angio.server.util.matlab.geometric.model.GeometricAnalyseModel;
import com.angio.server.util.matlab.geometric.model.VesselModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mathworks.toolbox.javabuilder.MWException;

import java.util.Date;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service("analyseInfoService")
@Transactional
public class AnalyseInfoServiceImpl implements AnalyseInfoService {

    private final AnalyseInfoMapper analyseInfoMapper;
    private final PatientMapper patientMapper;
    private final AnalyseInfoCrudRepository analyseInfoCrudRepository;
    private final PatientCrudRepository patientCrudRepository;
    private final AnalyseGeometricCrudRepository analyseGeometricCrudRepository;
    private final VesselCrudRepository vesselCrudRepository;
    private final AnalyseBloodFlowService analyseBloodFlowService;
    private final AngioAppProperties angioAppProperties;

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
    public AnalyseInfoEntity addNewAnalyseInfo(NewAnalyseRequest newAnalyseRequest)
            throws Exception {

        log.info("addNewAnalyseInfo() - find patient by id");
        PatientEntity patientEntityFromDB = null;
        if (newAnalyseRequest.getPatient() != null) {
            patientEntityFromDB = patientCrudRepository.findOne(newAnalyseRequest.getPatient().getId());
        }

        PatientEntity patientEntity;
        if (patientEntityFromDB == null) {

            log.info("addNewAnalyseInfo() - map patient request to new entity");
            patientEntity = patientMapper.map(newAnalyseRequest.getPatient(), PatientEntity.class);

            log.info("addNewAnalyseInfo() - create new patient entity: {}", patientEntity);
            patientEntity = patientCrudRepository.save(patientEntity);
        } else {

            log.info("addNewAnalyseInfo() - map patient request for update entity");
            patientMapper.map(newAnalyseRequest.getPatient(), patientEntityFromDB);

            log.info("addNewAnalyseInfo() - save updated patient entity: {}", patientEntityFromDB);
            patientEntity = patientCrudRepository.save(patientEntityFromDB);
        }

        log.info("addNewAnalyseInfo() - save original image");
        ImageOperation imageOperation = new ImageOperation();
        String imgFileName = imageOperation.save(newAnalyseRequest.getInfo().getImg());

        log.info("addNewAnalyseInfo() - get user principal");
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("addNewAnalyseInfo() - map new analyse request to entity");
        AnalyseInfoEntity analyseInfoEntity = analyseInfoMapper.map(newAnalyseRequest.getInfo(), AnalyseInfoEntity.class);
        analyseInfoEntity.setPatient(patientEntity);
        analyseInfoEntity.setUser(user);
        analyseInfoEntity.setImg(imgFileName);
        analyseInfoEntity.setAnalyseDate(new Date());
        analyseInfoEntity.setConclusion("");
        analyseInfoEntity.setFinished(false);

        log.info("addNewAnalyseInfo() - save analyse info entity");
        analyseInfoEntity = analyseInfoCrudRepository.save(analyseInfoEntity);

        return analyseInfoEntity;
    }

    @Override
    public AnalyseInfoEntity startNewAnalyse(long id) throws Exception {
        log.info("startNewAnalyse() - start");

        log.info("startNewAnalyse() - find analyse info with id: {}", id);
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findOne(id);

        log.info("startNewAnalyse() - geometric analyse");
        GeometricAnalyseModel geometricAnalyseModel = new GeometricAnalyseAdapter().runAnalyse(analyseInfoEntity.getImg());

        log.info("startNewAnalyse() - save images of geometric analyse");
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

        log.info("startNewAnalyse() - blood flow analyse");
        String originalImage = new ClassPathResource(
                angioAppProperties.getAnalyseImagesDirectory() + "/" + analyseInfoEntity.getImg())
                .getFile()
                .getAbsolutePath();
        BloodFlowAnalyseResult bloodFlowAnalyseResult = new BloodFlowAnalyseAdapter().runAnalyse(originalImage);

        log.info("startNewAnalyse() - save images of blood flow analyse");
        String ishemiaImagePath = imageOperation.save(bloodFlowAnalyseResult.getIshemiaImage());
        String densityImagePath = imageOperation.save(bloodFlowAnalyseResult.getCapillarDensityImage());

        log.info("startNewAnalyse() - save blood flow analyse result");
        analyseBloodFlowService.addNewAnalyse(analyseInfoEntity, ishemiaImagePath, densityImagePath, bloodFlowAnalyseResult);

        log.info("startNewAnalyse() - save completed analyse info");
        analyseInfoEntity.setFinished(true);
        analyseInfoCrudRepository.save(analyseInfoEntity);

        log.info("startNewAnalyse() - end");
        return analyseInfoEntity;
    }

    @Override
    public AnalyseInfoEntity getAnalyseInfoById(long id) {

        log.info("getAnalyseInfoById() - start - search analyse info with id: {}", id);
        AnalyseInfoEntity analyseInfo = analyseInfoCrudRepository.findOne(id);

        log.info("getAnalyseInfoById() - end - found: {}", analyseInfo);
        return analyseInfo;
    }

    @Override
    public AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) {
        log.info("updateAnalyseInfoConclusion() - start");

        log.info("updateAnalyseInfoConclusion() - search analyse info entity with id:", id);
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findOne(id);

        log.info("updateAnalyseInfoConclusion() - update conclusion field with: {}", conclusion);
        analyseInfoEntity.setConclusion(conclusion);

        log.info("updateAnalyseInfoConclusion() - end - save updated analyse info entity");
        return analyseInfoCrudRepository.save(analyseInfoEntity);
    }

    @Override
    public void deleteAnalyse(long id) throws Exception {
        ImageOperation imageOperation = new ImageOperation();
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findOne(id);
        imageOperation.deleteImage(analyseInfoEntity.getImg());

        AnalyseGeometricEntity analyseGeometricEntity = analyseInfoEntity.getAnalyseGeometric();
        if (analyseGeometricEntity != null){
            imageOperation.deleteImage(analyseGeometricEntity.getBinarizedImage());
            imageOperation.deleteImage(analyseGeometricEntity.getSkelImage());
            Set<VesselEntity> vessels = analyseGeometricEntity.getVessels();
            if (vessels != null && vessels.size() > 0){
                for (VesselEntity vesselEntity : vessels){
                    imageOperation.deleteImage(vesselEntity.getVesselImage());
                    imageOperation.deleteImage(vesselEntity.getMainVesselImage());
                }
            }
        }

        AnalyseBloodFlowEntity analyseBloodFlowEntity = analyseInfoEntity.getAnalyseBloodFlow();
        if (analyseBloodFlowEntity != null){
            imageOperation.deleteImage(analyseBloodFlowEntity.getDensityImageFileName());
            imageOperation.deleteImage(analyseBloodFlowEntity.getIshemiaImageFileName());
        }


        analyseInfoCrudRepository.delete(id);
    }

    @Override
    public long getCountOfAnalyses(String search, String date) {
        if (date == null) date = "";
        String formattedDate = null;
        if (!date.equals("")){
            String[] values = date.split("-"); //dd-MM-yyyy
            formattedDate = values[2] + "-" + values[1] + "-" + values[0]; //yyyy-MM-dd
        }
        return date.equals("") ? analyseInfoCrudRepository.count(search) : analyseInfoCrudRepository.count(search, formattedDate);
    }
}