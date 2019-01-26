package com.angio.angiobackend.api.analyse.service.impl;

import com.angio.angiobackend.api.analyse.entity.IshemiaEntity;
import com.angio.angiobackend.api.analyse.repository.IshemiaRepository;
import com.angio.angiobackend.api.analyse.service.IshemiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service("ishemiaService")
public class IshemiaServiceImpl implements IshemiaService {


    private final IshemiaRepository ishemiaRepository;

    @Autowired
    public IshemiaServiceImpl(IshemiaRepository ishemiaRepository) {
        this.ishemiaRepository = ishemiaRepository;
    }

    @Override
    public Set<IshemiaEntity> addNewIshemias(Set<IshemiaEntity> ischemias) {
        return StreamSupport
                .stream(ishemiaRepository.saveAll(ischemias).spliterator(), false)
                .collect(Collectors.toSet());
    }
}
