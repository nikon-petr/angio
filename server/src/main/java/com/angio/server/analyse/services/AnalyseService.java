package com.angio.server.analyse.services;

import com.angio.server.analyse.dto.AnalyseInfoDto;
import com.angio.server.analyse.dto.AnalyseShortItemDto;
import com.angio.server.analyse.entities.AnalyseInfoEntity;
import com.angio.server.analyse.entities.PatientEntity;
import com.angio.server.analyse.mappers.AnalyseInfoMapper;
import com.angio.server.analyse.mappers.PatientMapper;
import com.angio.server.analyse.repositories.AnalyseInfoCrudRepository;
import com.angio.server.analyse.repositories.PatientCrudRepository;
import com.angio.server.analyse.specifications.AnalyseInfoSpecification;
import com.angio.server.security.entities.UserEntity;
import com.angio.server.util.image.ImageOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service("analyseService")
@Transactional
public class AnalyseService {

    private final AnalyseInfoSpecification analyseInfoSpecification;
    private final AnalyseInfoMapper analyseInfoMapper;
    private final PatientMapper patientMapper;
    private final AnalyseInfoCrudRepository analyseInfoCrudRepository;
    private final PatientCrudRepository patientCrudRepository;

    /**
     * Filter analysis by query string matching any one or more fields.
     *
     * @param queryString query string
     * @param date analyse date
     * @param pageable page request
     * @return page of filtered analyses
     */
    public Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable) {

        log.info("filterAnalysesByQueryString() - start");

        log.info("filterAnalysesByQueryString() - build analyse info specification");
        Specification<AnalyseInfoEntity> specs = analyseInfoSpecification.getAnalyseInfoFilter(queryString)
                .and(analyseInfoSpecification.analyseDate(date));

        log.info("filterAnalysesByQueryString() - map sorting fields");
        Pageable mappedPageRequest = mapSortingFields(pageable);

        log.info("filterAnalysesByQueryString() - filter analyse info");
        Page<AnalyseInfoEntity> analyseInfoEntityPage = analyseInfoCrudRepository.findAll(specs, mappedPageRequest);

        log.info("filterAnalysesByQueryString() - map and return analyse page");
        return analyseInfoEntityPage.map(e -> analyseInfoMapper.map(e, AnalyseShortItemDto.class));
    }

    public AnalyseInfoDto createAnalyse(AnalyseInfoDto dto) {
        log.info("createAnalyse() - start - analyse to create: {}", dto);

        log.info("createAnalyse() - map analyse info dto to entity");
        AnalyseInfoEntity entity = analyseInfoMapper.map(dto, AnalyseInfoEntity.class);

        log.info("createAnalyse() - save patient data");
        savePatientData(entity, dto);

        log.info("createAnalyse() - save user data");
        saveUserData(entity);

        log.info("createAnalyse() - sat analyse date");
        entity.setAnalyseDate(new Date());

        log.info("createAnalyse() - sat analyse state");
        entity.setFinished(false);

        log.info("createAnalyse() - save analyse info entity");
        entity = analyseInfoCrudRepository.save(entity);

        AnalyseInfoDto saved = analyseInfoMapper.map(entity, AnalyseInfoDto.class);
        log.info("createAnalyse() - map saved analyse to dto and return: {}", saved);

        return saved;
    }

    private Pageable mapSortingFields(Pageable pageable) {
        log.info("mapSortingFields() - start mapping for: {}", pageable);
        Map<String, String> dtoSortingFields = new HashMap<>();
        dtoSortingFields.put("patient", "patient.lastname");
        dtoSortingFields.put("policy", "patient.policy");
        dtoSortingFields.put("user", "user.userInfo.lastname");

        List<Sort.Order> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            if (order.getDirection() != null && !isEmpty(order.getProperty())) {

                String field = dtoSortingFields.getOrDefault(order.getProperty(), null);

                if (field == null) {
                    field = order.getProperty();
                }

                Sort.Order copyOrder = new Sort.Order(order.getDirection(), field);
                orders.add(copyOrder);
            }
        }

        PageRequest result = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
        log.info("mapSortingFields() - mapping result: {}", result);
        return result;
    }

    private void savePatientData(AnalyseInfoEntity analyseInfoEntity, AnalyseInfoDto analyseInfoDto) {

        PatientEntity patientEntityFromDB = null;

        log.info("savePatientData() - find patient by id");
        if (analyseInfoDto.getPatient() != null) {
            patientEntityFromDB = patientCrudRepository.findById(analyseInfoDto.getPatient().getId()).orElse(null);
        }

        PatientEntity patientEntity;
        if (patientEntityFromDB == null) {

            log.info("savePatientData() - map patient request to new entity");
            patientEntity = patientMapper.map(analyseInfoDto.getPatient(), PatientEntity.class);

            log.info("savePatientData() - create new patient entity: {}", patientEntity);
            patientEntity = patientCrudRepository.save(patientEntity);
        } else {

            log.info("savePatientData() - map patient request for update entity");
            patientMapper.map(analyseInfoDto.getPatient(), patientEntityFromDB);

            log.info("savePatientData() - save updated patient entity: {}", patientEntityFromDB);
            patientEntity = patientCrudRepository.save(patientEntityFromDB);
        }

        analyseInfoEntity.setPatient(patientEntity);
    }

    private void saveUserData(AnalyseInfoEntity analyseInfoEntity) {
        log.info("saveUserData() - get user principal");
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("saveUserData() - save user data");
        analyseInfoEntity.setUser(user);
    }
}
