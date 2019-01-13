package com.angio.angiobackend.analyse.services.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.analyse.entities.AnalyseBloodFlowEntity;
import com.angio.angiobackend.analyse.entities.AnalyseGeometricEntity;
import com.angio.angiobackend.analyse.entities.AnalyseInfoEntity;
import com.angio.angiobackend.analyse.entities.PatientEntity;
import com.angio.angiobackend.analyse.entities.VesselEntity;
import com.angio.angiobackend.analyse.mappers.AnalyseInfoMapper;
import com.angio.angiobackend.analyse.mappers.PatientMapper;
import com.angio.angiobackend.analyse.repositories.AnalyseGeometricCrudRepository;
import com.angio.angiobackend.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.angiobackend.analyse.repositories.PatientCrudRepository;
import com.angio.angiobackend.analyse.repositories.VesselCrudRepository;
import com.angio.angiobackend.analyse.requests.NewAnalyseRequest;
import com.angio.angiobackend.analyse.services.AnalyseBloodFlowService;
import com.angio.angiobackend.analyse.services.AnalyseInfoService;
import com.angio.angiobackend.security.entities.UserEntity;
import com.angio.angiobackend.util.image.ImageOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final AngioBackendProperties props;

    @Deprecated
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
                return analyseInfoCrudRepository.findAll(search, PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(
                                new Sort.Order(
                                        isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
                                        sortProperty)
                        )));
            } else{
                return analyseInfoCrudRepository.findAll(search, formattedDate, PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(
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
            patientEntityFromDB = patientCrudRepository.findById(newAnalyseRequest.getPatient().getId()).get();
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

    @Deprecated
    @Override
    public AnalyseInfoEntity startNewAnalyse(long id) throws Exception {
        return null;
    }

    @Override
    public AnalyseInfoEntity getAnalyseInfoById(long id) {

        log.info("getAnalyseInfoById() - start - search analyse info with id: {}", id);
        AnalyseInfoEntity analyseInfo = analyseInfoCrudRepository.findById(id).get();

        log.info("getAnalyseInfoById() - end - found: {}", analyseInfo);
        return analyseInfo;
    }

    @Override
    public AnalyseInfoEntity updateAnalyseInfoConclusion(long id, String conclusion) {
        log.info("updateAnalyseInfoConclusion() - start");

        log.info("updateAnalyseInfoConclusion() - search analyse info entity with id:", id);
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findById(id).get();

        log.info("updateAnalyseInfoConclusion() - update conclusion field with: {}", conclusion);
        analyseInfoEntity.setConclusion(conclusion);

        log.info("updateAnalyseInfoConclusion() - end - save updated analyse info entity");
        return analyseInfoCrudRepository.save(analyseInfoEntity);
    }

    @Override
    public void deleteAnalyse(long id) throws Exception {
        ImageOperation imageOperation = new ImageOperation();
        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findById(id).get();
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


        analyseInfoCrudRepository.deleteById(id);
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