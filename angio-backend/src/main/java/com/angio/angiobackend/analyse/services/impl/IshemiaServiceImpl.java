package com.angio.angiobackend.analyse.services.impl;

import com.angio.angiobackend.analyse.entities.IshemiaEntity;
import com.angio.angiobackend.analyse.repositories.IshemiaCrudRepository;
import com.angio.angiobackend.analyse.services.IshemiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service("ishemiaService")
public class IshemiaServiceImpl implements IshemiaService {


    private final IshemiaCrudRepository ishemiaCrudRepository;

    @Autowired
    public IshemiaServiceImpl(IshemiaCrudRepository ishemiaCrudRepository) {
        this.ishemiaCrudRepository = ishemiaCrudRepository;
    }

    @Override
    public Set<IshemiaEntity> addNewIshemias(Set<IshemiaEntity> ischemias) {
        return StreamSupport
                .stream(ishemiaCrudRepository.saveAll(ischemias).spliterator(), false)
                .collect(Collectors.toSet());
    }
}
