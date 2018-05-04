package com.angio.app.analyse.services;

import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.app.analyse.entities.PatientEntity;
import com.angio.app.analyse.repositories.PatientCrudRepository;
import com.angio.app.analyse.services.AnalyseInfoService;
import com.angio.app.util.image.ImageOperation;
import com.angio.app.security.entities.UserEntity;
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

    @Autowired
    public AnalyseInfoServiceImpl(AnalyseInfoCrudRepository analyseInfoCrudRepository, PatientCrudRepository patientCrudRepository) {
        this.analyseInfoCrudRepository = analyseInfoCrudRepository;
        this.patientCrudRepository = patientCrudRepository;
    }

    @Override
    public List<AnalyseInfoEntity> getAllBaseAnalyseInfo() throws Exception {
        List<AnalyseInfoEntity> list = analyseInfoCrudRepository.findAll();

        return list;
    }

    @Override
    public AnalyseInfoEntity addNewAnalyse(UserEntity user,
                                           PatientEntity patient,
                                           String name,
                                           String short_description,
                                           String full_description,
                                           String analyse_type,
                                           String comment,
                                           String img) throws Exception {
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
        String imgFileName = new ImageOperation().save(img);
        AnalyseInfoEntity analyseInfoEntity = new AnalyseInfoEntity(user, patientEntity, name, short_description,
                full_description, analyse_type, comment, imgFileName, new Timestamp(System.currentTimeMillis()), "", false);
        analyseInfoEntity = analyseInfoCrudRepository.save(analyseInfoEntity);

//        TODO: run matlab analyses

        return analyseInfoEntity;
    }
}
