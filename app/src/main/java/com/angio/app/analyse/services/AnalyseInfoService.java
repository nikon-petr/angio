package com.angio.app.analyse.services;

import com.angio.app.analyse.entities.AnalyseInfoEntity;
import com.angio.app.analyse.entities.PatientEntity;
import com.angio.app.security.entities.UserEntity;

import java.util.List;

public interface AnalyseInfoService {
    List<AnalyseInfoEntity> getAllBaseAnalyseInfo() throws Exception;
    AnalyseInfoEntity addNewAnalyse(UserEntity user, PatientEntity patient, String name, String short_description,
                       String full_description, String analyse_type, String comment, String img) throws Exception;
}