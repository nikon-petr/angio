package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.dto.AnalyseDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.ExtendedAnalyseDto;
import com.angio.angiobackend.api.analyse.embeddable.AnalyseStatus;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.entity.PatientEntity;
import com.angio.angiobackend.api.analyse.mapper.AnalyseMapper;
import com.angio.angiobackend.api.analyse.mapper.PatientMapper;
import com.angio.angiobackend.api.analyse.messaging.AnalyseToExecuteSender;
import com.angio.angiobackend.api.analyse.repository.AnalyseRepository;
import com.angio.angiobackend.api.analyse.repository.PatientRepository;
import com.angio.angiobackend.api.analyse.specifications.AnalyseInfoSpecification;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.security.entities.UserEntity;
import com.angio.angiobackend.api.uploads.repository.UploadRepository;
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

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service("analyseService")
@Transactional
public class AnalyseService {

    private final AnalyseInfoSpecification analyseInfoSpecification;
    private final AnalyseMapper analyseMapper;
    private final PatientMapper patientMapper;
    private final AnalyseRepository analyseRepository;
    private final PatientRepository patientRepository;
    private final UploadRepository uploadRepository;
    private final AnalyseToExecuteSender analyseToExecuteSender;

    /**
     * Create new analyse and save additional info to database. Set analyse status CREATED.
     *
     * @param dto - analyse additional info
     * @return saved analyse data such as id and etc.
     */
    public ExtendedAnalyseDto createAnalyse(ExtendedAnalyseDto dto) {
        log.trace("createAnalyse() - start - analyse to create: {}", dto);

        log.trace("createAnalyse() - map analyse info dto to entity");
        AnalyseEntity entity = analyseMapper.toNewEntity(dto);

        entity.setOriginalImage(uploadRepository.getOne(dto.getOriginalImage().getId()));

        log.trace("createAnalyse() - save patient data");
        savePatientData(entity, dto);

        log.trace("createAnalyse() - save user data");
        saveUserData(entity);

        log.trace("createAnalyse() - set analyse date");
        entity.setAnalyseDate(new Date());

        log.trace("createAnalyse() - set analyse status to CREATED");
        entity.setStatus(AnalyseStatus.of(AnalyseStatusType.CREATED));

        log.trace("createAnalyse() - save analyse info entity");
        entity = analyseRepository.save(entity);

        AnalyseDto saved = analyseMapper.toAnalyseDto(entity);
        log.trace("createAnalyse() - map saved analyse to dto");

        log.info("createAnalyse() - send analyse to execute: {}", saved);
        try {
            log.info("createAnalyse() - analyse execution result status: IN_PROGRESS");
            entity.setStatus(new AnalyseStatus().setType(AnalyseStatusType.IN_PROGRESS));
            analyseToExecuteSender.sendAnalyseToExecute(saved);
        } catch (Exception e) {
            log.info("createAnalyse() - analyse execution result status: FAILED cause: {}", e);
            entity.setStatus(new AnalyseStatus()
                    .setType(AnalyseStatusType.FAILED)
                    .setExtension(e.getMessage()));
        }

        log.trace("createAnalyse() - end");
        return analyseMapper.toExtendedDto(entity);
    }

    /**
     * Save analyse execution result to database.
     *
     * @param dto - analyse results
     */
    public void saveExecutedAnalyse(AnalyseDto dto) {
        log.trace("saveExecutedAnalyse() - start - analyse to save: {}", dto);

        log.trace("saveExecutedAnalyse() - map analyse info dto to entity");
        AnalyseEntity entity = analyseRepository.getOne(dto.getId());
        analyseMapper.updateEntity(dto, entity);
        entity.getStatus().setType(AnalyseStatusType.SUCCESS);

        log.trace("saveExecutedAnalyse() - save analyse info entity");
        entity = analyseRepository.save(entity);

        log.trace("saveExecutedAnalyse() - map saved analyse info entity to dto");
        ExtendedAnalyseDto savedDto = analyseMapper.toExtendedDto(entity);

        log.info("saveExecutedAnalyse() - received analyse saving result: {}", savedDto);
        log.trace("saveExecutedAnalyse() - end");
    }

    /**
     * Filter analysis by query string matching any one or more fields.
     *
     * @param queryString query string
     * @param date analyse date
     * @param pageable page request
     * @return page of filtered analyses
     */
    public Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable) {

        log.trace("filterAnalysesByQueryString() - start");

        log.trace("filterAnalysesByQueryString() - build analyse info specification");
        Specification<AnalyseEntity> specs = analyseInfoSpecification.getAnalyseInfoFilter(queryString)
                .and(analyseInfoSpecification.analyseDate(date));

        log.trace("filterAnalysesByQueryString() - map sorting fields");
        Pageable mappedPageRequest = mapSortingFields(pageable);

        log.trace("filterAnalysesByQueryString() - filter analyse info");
        Page<AnalyseEntity> analyseInfoEntityPage = analyseRepository.findAll(specs, mappedPageRequest);

        log.trace("filterAnalysesByQueryString() - map and return analyse page");
        return analyseInfoEntityPage.map(analyseMapper::toShortItemDto);
    }

    public ExtendedAnalyseDto getAnalyseById(Long id) {
        log.trace("getAnalyseById() - start");
        log.info("getAnalyseById() - analyse to get: id={}", id);
        Optional<AnalyseEntity> analyse = analyseRepository.findById(id);
        return analyseMapper.toExtendedDto(
                analyse.orElseThrow(() -> new ResourceNotFoundException(format("Analyse with id=%s not found", id))));
    }

    private Pageable mapSortingFields(Pageable pageable) {
        log.trace("mapSortingFields() - start mapping for: {}", pageable);
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
        log.trace("mapSortingFields() - mapping result: {}", result);
        return result;
    }

    private void savePatientData(AnalyseEntity analyseEntity, ExtendedAnalyseDto extendedAnalyseDto) {

        PatientEntity patientEntityFromDB = null;

        log.trace("savePatientData() - find patient by id");
        if (extendedAnalyseDto.getPatient() != null) {
            patientEntityFromDB = patientRepository.findById(extendedAnalyseDto.getPatient().getId()).orElse(null);
        }

        PatientEntity patientEntity;
        if (patientEntityFromDB == null) {

            log.trace("savePatientData() - map patient request to new entity");
            patientEntity = patientMapper.toEntity(extendedAnalyseDto.getPatient());

            log.trace("savePatientData() - create new patient entity: {}", patientEntity);
            patientEntity = patientRepository.save(patientEntity);
        } else {

            log.trace("savePatientData() - map patient request for update entity");
            patientMapper.toEntity(extendedAnalyseDto.getPatient(), patientEntityFromDB);

            log.trace("savePatientData() - save updated patient entity: {}", patientEntityFromDB);
            patientEntity = patientRepository.save(patientEntityFromDB);
        }

        analyseEntity.setPatient(patientEntity);
    }

    private void saveUserData(AnalyseEntity analyseEntity) {
        log.trace("saveUserData() - get user principal");
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.trace("saveUserData() - save user data");
        analyseEntity.setUser(user);
    }
}
