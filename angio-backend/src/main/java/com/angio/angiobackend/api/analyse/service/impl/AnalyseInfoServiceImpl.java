package com.angio.angiobackend.api.analyse.service.impl;

import com.angio.angiobackend.AngioBackendProperties;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.mapper.AnalyseMapper;
import com.angio.angiobackend.api.analyse.repository.AnalyseRepository;
import com.angio.angiobackend.api.analyse.repository.PatientRepository;
import com.angio.angiobackend.api.analyse.repository.VesselRepository;
import com.angio.angiobackend.api.analyse.requests.NewAnalyseRequest;
import com.angio.angiobackend.api.analyse.service.AnalyseInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("analyseInfoService")
@Transactional
public class AnalyseInfoServiceImpl implements AnalyseInfoService {

    private final AnalyseMapper analyseMapper;
    private final AnalyseRepository analyseRepository;
    private final PatientRepository patientRepository;
    private final VesselRepository vesselRepository;
    private final AngioBackendProperties props;

    @Deprecated
    @Override
    public Page<AnalyseEntity> listAllByPageAndSortAndFilter(Pageable pageable, String search, String date) throws Exception {
        return null;
//        if (search == null) search = "";
//        if (date == null) date = "";
//
//        String sortField = null;
//        boolean isAscending = false;
//        if (pageable.getSort() != null) {
//            String[] sortData = pageable.getSort().toString().replaceAll(" ", "").split(":");
//            sortField = sortData[0];
//            isAscending = sortData[1].equals("ASC");
//        }
//
//        String formattedDate = null;
//        if (!date.equals("")){
//            String[] values = date.split("-"); //dd-MM-yyyy
//            formattedDate = values[2] + "-" + values[1] + "-" + values[0]; //yyyy-MM-dd
//        }
//
//        if (sortField != null && (sortField.equals("patient") || sortField.equals("policy") || sortField.equals("user"))){
//            String sortProperty = null;
//            switch (sortField){
//                case "patient":
//                    sortProperty = "patient.lastname";
//                    break;
//                case "policy":
//                    sortProperty = "patient.policy";
//                    break;
//                case "user":
//                    sortProperty = "user.userInfo.lastname";
//                    break;
//            }
//            if (formattedDate == null) {
//                return analyseInfoCrudRepository.findAll(search, PageRequest.of(
//                        pageable.getPageNumber(),
//                        pageable.getPageSize(),
//                        Sort.by(
//                                new Sort.Order(
//                                        isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
//                                        sortProperty)
//                        )));
//            } else{
//                return analyseInfoCrudRepository.findAll(search, formattedDate, PageRequest.of(
//                        pageable.getPageNumber(),
//                        pageable.getPageSize(),
//                        Sort.by(
//                                new Sort.Order(
//                                        isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
//                                        sortProperty)
//                        )));
//            }
//        } else{
//            if (formattedDate == null) return analyseInfoCrudRepository.findAll(search, pageable);
//            else return analyseInfoCrudRepository.findAll(search, formattedDate, pageable);
//        }
    }

    @Override
    @Transactional
    public AnalyseEntity addNewAnalyseInfo(NewAnalyseRequest newAnalyseRequest)
            throws Exception {

//        log.info("addNewAnalyseInfo() - find patient by id");
//        PatientEntity patientEntityFromDB = null;
//        if (newAnalyseRequest.getPatient() != null) {
//            patientEntityFromDB = patientRepository.findById(newAnalyseRequest.getPatient().getId()).get();
//        }
//
//        PatientEntity patientEntity;
//        if (patientEntityFromDB == null) {
//
//            log.info("addNewAnalyseInfo() - map patient request to new entity");
//            patientEntity = patientMapper.map(newAnalyseRequest.getPatient(), PatientEntity.class);
//
//            log.info("addNewAnalyseInfo() - create new patient entity: {}", patientEntity);
//            patientEntity = patientRepository.save(patientEntity);
//        } else {
//
//            log.info("addNewAnalyseInfo() - map patient request for update entity");
//            patientMapper.map(newAnalyseRequest.getPatient(), patientEntityFromDB);
//
//            log.info("addNewAnalyseInfo() - save updated patient entity: {}", patientEntityFromDB);
//            patientEntity = patientRepository.save(patientEntityFromDB);
//        }
//
//        log.info("addNewAnalyseInfo() - save original image");
//        ImageOperation imageOperation = new ImageOperation();
//        String imgFileName = imageOperation.save(newAnalyseRequest.getInfo().getImg());
//
//        log.info("addNewAnalyseInfo() - get user principal");
//        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        log.info("addNewAnalyseInfo() - map new analyse request to entity");
//        AnalyseEntity analyseEntity = analyseMapper.toNewEntity(newAnalyseRequest.getInfo());
//        analyseEntity.setPatient(patientEntity);
//        analyseEntity.setUser(user);
////        analyseInfoEntity.setImg(imgFileName);
//        analyseEntity.setAnalyseDate(new Date());
//        analyseEntity.setConclusion("");
//        analyseEntity.setStatus(AnalyseStatus.of(AnalyseStatusType.SUCCESS));
//
//        log.info("addNewAnalyseInfo() - save analyse info entity");
//        analyseEntity = analyseRepository.save(analyseEntity);

        return null;
    }

    @Deprecated
    @Override
    public AnalyseEntity startNewAnalyse(long id) throws Exception {
        return null;
    }

    @Override
    public AnalyseEntity getAnalyseInfoById(long id) {

        log.info("getAnalyseInfoById() - start - search analyse info with id: {}", id);
        AnalyseEntity analyseInfo = analyseRepository.findById(id).get();

        log.info("getAnalyseInfoById() - end - found: {}", analyseInfo);
        return analyseInfo;
    }

    @Override
    public AnalyseEntity updateAnalyseInfoConclusion(long id, String conclusion) {
        log.info("updateAnalyseInfoConclusion() - start");

        log.info("updateAnalyseInfoConclusion() - search analyse info entity with id:", id);
        AnalyseEntity analyseEntity = analyseRepository.findById(id).get();

        log.info("updateAnalyseInfoConclusion() - update conclusion field with: {}", conclusion);
        analyseEntity.setConclusion(conclusion);

        log.info("updateAnalyseInfoConclusion() - end - save updated analyse info entity");
        return analyseRepository.save(analyseEntity);
    }

    @Override
    public void deleteAnalyse(long id) throws Exception {
//        ImageOperation imageOperation = new ImageOperation();
//        AnalyseInfoEntity analyseInfoEntity = analyseInfoCrudRepository.findById(id).get();
//        imageOperation.deleteImage(analyseInfoEntity.getImg());
//
//        AnalyseGeometricEntity analyseGeometricEntity = analyseInfoEntity.getAnalyseGeometric();
//        if (analyseGeometricEntity != null){
//            imageOperation.deleteImage(analyseGeometricEntity.getBinarizedImage());
//            imageOperation.deleteImage(analyseGeometricEntity.getSkelImage());
//            Set<VesselEntity> vessels = analyseGeometricEntity.getVessels();
//            if (vessels != null && vessels.size() > 0){
//                for (VesselEntity vesselEntity : vessels){
//                    imageOperation.deleteImage(vesselEntity.getVesselImage());
//                    imageOperation.deleteImage(vesselEntity.getMainVesselImage());
//                }
//            }
//        }
//
//        AnalyseBloodFlowEntity analyseBloodFlowEntity = analyseInfoEntity.getAnalyseBloodFlow();
//        if (analyseBloodFlowEntity != null){
//            imageOperation.deleteImage(analyseBloodFlowEntity.getDensityImage());
//            imageOperation.deleteImage(analyseBloodFlowEntity.getIschemiaImage());
//        }
//
//
//        analyseInfoCrudRepository.deleteById(id);
    }

    @Override
    public long getCountOfAnalyses(String search, String date) {
//        if (date == null) date = "";
//        String formattedDate = null;
//        if (!date.equals("")){
//            String[] values = date.split("-"); //dd-MM-yyyy
//            formattedDate = values[2] + "-" + values[1] + "-" + values[0]; //yyyy-MM-dd
//        }
//        return date.equals("") ? analyseInfoCrudRepository.count(search) : analyseInfoCrudRepository.count(search, formattedDate);
        return 0;
    }
}