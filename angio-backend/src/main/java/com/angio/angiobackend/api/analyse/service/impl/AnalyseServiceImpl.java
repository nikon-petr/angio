package com.angio.angiobackend.api.analyse.service.impl;

import com.angio.angiobackend.api.analyse.AnalyseActions;
import com.angio.angiobackend.api.analyse.dto.AdditionalInfoDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseJmsDto;
import com.angio.angiobackend.api.analyse.dto.AnalyseShortItemDto;
import com.angio.angiobackend.api.analyse.dto.DetailedAnalyseDto;
import com.angio.angiobackend.api.analyse.embeddable.AnalyseStatus;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.mapper.AdditionalInfoMapper;
import com.angio.angiobackend.api.analyse.mapper.AnalyseMapper;
import com.angio.angiobackend.api.analyse.messaging.AnalyseToExecuteSender;
import com.angio.angiobackend.api.analyse.repository.AnalyseRepository;
import com.angio.angiobackend.api.analyse.service.AnalyseService;
import com.angio.angiobackend.api.analyse.specification.AnalyseSpecification;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import com.angio.angiobackend.api.common.accessor.DynamicLocaleMessageSourceAccessor;
import com.angio.angiobackend.api.common.exception.ResourceNotFoundException;
import com.angio.angiobackend.api.patient.service.PatientService;
import com.angio.angiobackend.api.security.service.impl.UserDetailsServiceImpl;
import com.angio.angiobackend.api.uploads.repository.UploadRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalyseServiceImpl implements AnalyseService {

    private final AnalyseSpecification analyseSpecification;
    private final AnalyseMapper analyseMapper;
    private final AdditionalInfoMapper additionalInfoMapper;
    private final AnalyseRepository analyseRepository;
    private final UploadRepository uploadRepository;
    private final PatientService patientService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AnalyseToExecuteSender analyseToExecuteSender;
    private final DynamicLocaleMessageSourceAccessor msa;

    /**
     * Create new analyse and save additional info to database. Set analyse status CREATED.
     *
     * @param dto - analyse additional info
     * @return saved analyse data such as id and etc.
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ANALYSE_CREATE')")
    public DetailedAnalyseDto createAnalyse(@NonNull DetailedAnalyseDto dto) {
        log.trace("createAnalyse() - start - analyse to create: {}", dto);

        log.trace("createAnalyse() - map analyse info dto to entity");
        AnalyseEntity entity = analyseMapper.toNewEntity(dto);

        entity.setOriginalImage(uploadRepository.findById(dto.getOriginalImage().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.analyse.originalImage.notFound",
                                new Object[] {dto.getOriginalImage().getId()}))));

        log.trace("createAnalyse() - save patient data");
        entity.getAdditionalInfo().setPatient(patientService.getPatientEntityById(dto.getAdditionalInfo().getPatientId()));

        log.trace("createAnalyse() - save diagnostician data");
        entity.getAdditionalInfo().setDiagnostician(userDetailsServiceImpl.getUserFromContext());

        log.trace("createAnalyse() - set analyse date");
        entity.setAnalyseDate(new Date());

        log.trace("createAnalyse() - set analyse status to CREATED");
        entity.setStatus(AnalyseStatus.of(AnalyseStatusType.CREATED));

        log.trace("createAnalyse() - save analyse info entity");
        entity = analyseRepository.save(entity);

        AnalyseJmsDto saved = analyseMapper.toAnalyseDto(entity);
        log.trace("createAnalyse() - map saved analyse to dto");

        log.info("createAnalyse() - send analyse to execute: {}", saved);
        sendAnalyseToExecution(saved, entity);

        log.trace("createAnalyse() - map saved analyse without result");
        DetailedAnalyseDto savedResult = analyseMapper.toExtendedDto(entity);
        savedResult.setGeometricAnalyse(null);
        savedResult.setBloodFlowAnalyse(null);
        log.trace("createAnalyse() - end");
        return savedResult;
    }

    /**
     * Save analyse execution result to database.
     *
     * @param dto - analyse results
     */
    @Override
    @Transactional
    public void saveExecutedAnalyse(@NonNull AnalyseJmsDto dto) {
        log.trace("saveExecutedAnalyse() - start - analyse to save: {}", dto);

        log.trace("saveExecutedAnalyse() - map analyse info dto to entity");
        AnalyseEntity entity = analyseRepository.getOne(dto.getId());
        analyseMapper.updateAnalyseResult(dto, entity);
        entity.getStatus().setType(AnalyseStatusType.SUCCESS);

        log.trace("saveExecutedAnalyse() - save analyse info entity");
        entity = analyseRepository.save(entity);
        log.info("saveExecutedAnalyse() - saved analyse execution result {}", entity.getId());

        log.trace("saveExecutedAnalyse() - map saved analyse info entity to dto");
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
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ANALYSE_VIEW')")
    public Page<AnalyseShortItemDto> filterAnalysesByQueryString(String queryString, Date date, Pageable pageable) {

        log.trace("filterAnalysesByQueryString() - start");

        log.trace("filterAnalysesByQueryString() - build analyse info specification");
        Specification<AnalyseEntity> specs = analyseSpecification.getAnalyseInfoFilter(queryString)
                .and(analyseSpecification.analyseDate(date))
                .and(analyseSpecification.notDeleted())
                .and(analyseSpecification.fetchAll());

        log.trace("filterAnalysesByQueryString() - map sorting fields");
        Pageable mappedPageRequest = mapSortingFields(pageable);

        log.trace("filterAnalysesByQueryString() - filter analyse info");
        Page<AnalyseEntity> analyseInfoEntityPage = analyseRepository.findAll(specs, mappedPageRequest);

        log.trace("filterAnalysesByQueryString() - map and return analyse page");
        return analyseInfoEntityPage.map(analyseMapper::toShortItemDto);
    }

    /**
     * Get analyse by id or throw {@link ResourceNotFoundException} if not found.
     *
     * @param id analyse id
     * @return analyse data
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('ANALYSE_VIEW')")
    public DetailedAnalyseDto getAnalyseById(@NonNull Long id) {
        log.trace("getAnalyseById() - start");
        log.info("getAnalyseById() - analyse to get: id={}", id);
        return analyseMapper.toExtendedDto(analyseRepository.findOne(analyseSpecification.analyseId(id)
                .and(analyseSpecification.notDeleted())
                .and(analyseSpecification.fetchAll()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.analyse.notFound", new Object[] {id}))));
    }

    /**
     * Set analyse status to DELETED.
     *
     * @param id analyse id
     * @return deleted analyse data
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ANALYSE_REMOVE')")
    public DetailedAnalyseDto deleteAnalyse(@NonNull Long id) {
        log.trace("deleteAnalyse() - start");
        AnalyseEntity analyse = analyseRepository.findOne(analyseSpecification.analyseId(id)
                .and(analyseSpecification.notDeleted())
                .and(analyseSpecification.fetchAll()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.analyse.notFound", new Object[] {id})));

        log.info("deleteAnalyse() - set analyse status DELETED for id={}", id);
        analyse.getStatus().setType(AnalyseStatusType.DELETED);
        analyse.getStatus().setExtension(msa.getMessage("analyse.status.extension.deletedByUser", new Object[] {id}));

        log.trace("deleteAnalyse() - save updated analyse status");
        analyse = analyseRepository.save(analyse);

        log.trace("deleteAnalyse() - end");
        return analyseMapper.toExtendedDto(analyse);
    }

    /**
     * Execute action on analyse with given id.
     *
     * @param id analyse id
     * @param action action
     * @return analyse data after applying action
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ANALYSE_EXECUTE_ACTION')")
    public DetailedAnalyseDto executeAction(@NonNull Long id, @NonNull AnalyseActions action) {
        log.info("executeAction() - action to execute {} for analyse id={}", action, id);
        switch (action) {
            case SEND_TO_EXECUTION: return sendAnalyseToExecution(id);
            default: throw new IllegalArgumentException(msa.getMessage("errors.api.analyse.actionDoesNotExists", new Object[] {action}));
        }
    }

    /**
     * Update or create conclusion for analyse with given id or throw {@link ResourceNotFoundException} if not found.
     *
     * @param id analyse id
     * @param dto conclusion
     * @return updated analyse data
     */
    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ANALYSE_EDIT')")
    public DetailedAnalyseDto updateAnalyseAdditionalInfo(@NonNull Long id, @NonNull AdditionalInfoDto dto) {
        log.trace("updateAnalyseAdditionalInfo() - start");

        log.trace("updateAnalyseAdditionalInfo() - search analyse info entity with id:", id);
        AnalyseEntity analyseEntity = analyseRepository.findOne(analyseSpecification.analyseId(id)
                .and(analyseSpecification.notDeleted())
                .and(analyseSpecification.fetchAll()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.analyse.notFound", new Object[] {id})));

        log.info("updateAnalyseAdditionalInfo() - update conclusion field with: {}", dto);
        additionalInfoMapper.updateEntity(dto, analyseEntity.getAdditionalInfo());

        if (dto.getPatientId() != null) {
            log.trace("updateAnalyseAdditionalInfo() - update patient id");
            analyseEntity.getAdditionalInfo().setPatient(patientService.getPatientEntityById(dto.getPatientId()));
        }

        log.trace("updateAnalyseAdditionalInfo() - end - save updated analyse info entity");
        return analyseMapper.toExtendedDto(analyseRepository.save(analyseEntity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ANALYSE_EDIT')")
    public DetailedAnalyseDto deleteGeometricAnalyseVessel(@NonNull Long analyseId, @NonNull Long vesselId) {
        log.trace("deleteAnalyse() - start");

        log.trace("deleteAnalyse() - find analyse: id={}", analyseId);
        AnalyseEntity analyse = analyseRepository.findOne(analyseSpecification.analyseId(analyseId)
                .and(analyseSpecification.notDeleted())
                .and(analyseSpecification.fetchAll()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.analyse.notFound", new Object[] {analyseId})));

        log.trace("deleteAnalyse() - delete vessel", analyseId);
        boolean result = analyse.getGeometricAnalyse().getVessels().removeIf(e -> e.getId().equals(vesselId));

        if (!result) {
            throw new ResourceNotFoundException(msa.getMessage("errors.api.vessel.notFound", new Object[] {vesselId}));
        }

        analyse = analyseRepository.save(analyse);

        log.trace("deleteAnalyse() - end");
        return analyseMapper.toExtendedDto(analyse);
    }

    @Override
    @Transactional
    public void purgeAnalyses() {
        log.trace("purgeAnalyses() - start");
        List<AnalyseEntity> inStatusDeleted = analyseRepository.findAll(analyseSpecification.inStatus(AnalyseStatusType.DELETED));
        log.trace("purgeAnalyses() - ids to delete: {}", inStatusDeleted.stream().mapToLong(AnalyseEntity::getId).toArray());
        analyseRepository.deleteInBatch(inStatusDeleted);
        log.trace("purgeAnalyses() - end");
    }

    private Pageable mapSortingFields(Pageable pageable) {
        log.trace("mapSortingFields() - start mapping for: {}", pageable);
        Map<String, String> dtoSortingFields = new HashMap<>();
        dtoSortingFields.put("patient", "patient.lastname");
        dtoSortingFields.put("policy", "patient.policy");
        dtoSortingFields.put("user", "diagnostician.userInfo.lastname");

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

    /**
     * Send analysis to execution again after fail.
     *
     * @param id analyse id
     * @return sent analyse data
     */
    private DetailedAnalyseDto sendAnalyseToExecution(Long id) {

        log.trace("sendAnalyseToExecution() - start");
        AnalyseEntity analyse = analyseRepository.findOne(analyseSpecification.analyseId(id)
                .and(analyseSpecification.notDeleted())
                .and(analyseSpecification.fetchAll()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        msa.getMessage("errors.api.analyse.notFound", new Object[] {id})));

        log.trace("sendAnalyseToExecution() - check analyse status");
        if (analyse.getStatus().getType() != AnalyseStatusType.FAILED) {
            throw new IllegalArgumentException(msa.getMessage("errors.api.analyse.alreadyExecuted", new Object[] {id}));
        }

        AnalyseJmsDto dto = analyseMapper.toAnalyseDto(analyse);
        log.trace("sendAnalyseToExecution() - map saved analyse to dto");

        log.info("sendAnalyseToExecution() - send analyse to execute: {}", dto);
        sendAnalyseToExecution(dto, analyse);

        log.trace("sendAnalyseToExecution() - map saved analyse without result");
        DetailedAnalyseDto savedResult = analyseMapper.toExtendedDto(analyse);
        savedResult.setGeometricAnalyse(null);
        savedResult.setBloodFlowAnalyse(null);
        log.trace("sendAnalyseToExecution() - end");
        return savedResult;
    }

    private void sendAnalyseToExecution(AnalyseJmsDto dto, AnalyseEntity analyse) {
        try {
            log.info("sendAnalyseToExecution() - analyse execution result status: IN_PROGRESS");
            analyse.setStatus(new AnalyseStatus().setType(AnalyseStatusType.IN_PROGRESS));
            analyseToExecuteSender.sendAnalyseToExecute(dto);
        } catch (Exception e) {
            log.info("sendAnalyseToExecution() - analyse execution result status: FAILED cause: {}", e);
            analyse.setStatus(new AnalyseStatus()
                    .setType(AnalyseStatusType.FAILED)
                    .setExtension(e.getMessage()));
        }
    }
}