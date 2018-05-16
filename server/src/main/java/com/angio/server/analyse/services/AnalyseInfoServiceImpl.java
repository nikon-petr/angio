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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

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
    public List<AnalyseInfoEntity> getAllBaseAnalyseInfo() throws Exception {
        return analyseInfoCrudRepository.findAll();
    }

    @Override
    public AnalyseInfoEntity addNewAnalyse(UserEntity user, PatientEntity patient, AnalyseInfoRequest analyseInfoRequest) throws Exception {
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
                analyseInfoRequest.getShort_description(), analyseInfoRequest.getFull_description(), analyseInfoRequest.getAnalyse_type(),
                analyseInfoRequest.getComments(), imgFileName, new Timestamp(System.currentTimeMillis()), "", false);
        analyseInfoEntity = analyseInfoCrudRepository.save(analyseInfoEntity);

//      Geometric analyse
        GeometricAnalyseModel geometricAnalyseModel = new GeometricAnalyseAdapter().runAnalyse(analyseInfoEntity.getImg());

        String binarizedImage = imageOperation.save(geometricAnalyseModel.getBinarized());
        String skelImage = imageOperation.save(geometricAnalyseModel.getSkel());
        analyseInfoEntity.setFinished(true);
        AnalyseGeometricEntity analyseGeometricEntity = analyseGeometricCrudRepository.save(new AnalyseGeometricEntity(
                analyseInfoEntity, binarizedImage, skelImage));
        for (VesselModel vesselModel : geometricAnalyseModel.getAnalyse_result()) {
            String vesselImage = imageOperation.save(vesselModel.getVessel_image());
            String mainVessel = imageOperation.save(vesselModel.getMain_vessel());
            vesselCrudRepository.save(new VesselEntity(analyseGeometricEntity, vesselImage, mainVessel,
                    (float) vesselModel.getTortuosity(), vesselModel.getCount_of_branches_of_1_orders(),
                    (float) vesselModel.getBranching(), (float) vesselModel.getArea(), (float) vesselModel.getArea_percent()));
        }

//      Blood flow analyse
        String originalImage = new ClassPathResource(angioAppProperties.getAnalyseImagesDirectory() + analyseInfoEntity.getImg())
                .getFile()
                .getAbsolutePath();
        BloodFlowAnalyseResult bloodFlowAnalyseResult = new BloodFlowAnalyseAdapter().runAnalyse(originalImage);

        String ishemiaImagePath = imageOperation.save(bloodFlowAnalyseResult.getIshemiaImage());
        String densityImagePath = imageOperation.save(bloodFlowAnalyseResult.getCapillarDensityImage());

        analyseBloodFlowService.addNewAnalyse(analyseInfoEntity, ishemiaImagePath, densityImagePath, bloodFlowAnalyseResult);

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
}