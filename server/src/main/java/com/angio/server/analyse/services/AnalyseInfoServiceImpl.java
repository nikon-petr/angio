package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.AnalyseGeometricEntity;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.VesselEntity;
import com.angio.server.analyse.repositories.AnalyseGeometricCrudRepository;
import com.angio.server.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.repositories.PatientCrudRepository;
import com.angio.server.analyse.repositories.VesselCrudRepository;
import com.angio.server.analyse.requests.AnalyseInfoRequest;
import com.angio.server.util.image.ImageOperation;
import com.angio.server.security.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AnalyseInfoServiceImpl(AnalyseInfoCrudRepository analyseInfoCrudRepository, PatientCrudRepository patientCrudRepository,
                                  AnalyseGeometricCrudRepository analyseGeometricCrudRepository, VesselCrudRepository vesselCrudRepository) {
        this.analyseInfoCrudRepository = analyseInfoCrudRepository;
        this.patientCrudRepository = patientCrudRepository;
        this.analyseGeometricCrudRepository = analyseGeometricCrudRepository;
        this.vesselCrudRepository = vesselCrudRepository;
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
        } else{
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
        String imgFileName = new ImageOperation().save(analyseInfoRequest.getImg());
        AnalyseInfoEntity analyseInfoEntity = new AnalyseInfoEntity(user, patientEntity, analyseInfoRequest.getName(),
                analyseInfoRequest.getShort_description(), analyseInfoRequest.getFull_description(), analyseInfoRequest.getAnalyse_type(),
                analyseInfoRequest.getComments(), imgFileName, new Timestamp(System.currentTimeMillis()), "", false);
        analyseInfoEntity = analyseInfoCrudRepository.save(analyseInfoEntity);
        System.out.println("id of new analyse = " + analyseInfoEntity.getId());

//        //TODO: run matlab analyses instead of test data
        analyseInfoEntity.setFinished(true);
        String testImg = imgFileName;
        AnalyseGeometricEntity analyseGeometricEntity = analyseGeometricCrudRepository.save(new AnalyseGeometricEntity(
                analyseInfoEntity, testImg, testImg, testImg));
        for (int i = 0; i < 5; i++){
            vesselCrudRepository.save(
                    new VesselEntity(analyseGeometricEntity, testImg, testImg, Float.parseFloat((1 + (i/10)) + ""),
                            i + 3, Float.parseFloat(((i+1) * 5) + ""), Float.parseFloat((31*i) + ""), Float.parseFloat((3*i) + "")));
        }

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