package com.angio.server.analyse.services.impl;

import com.angio.server.analyse.entities.MakulaEntity;
import com.angio.server.analyse.repositories.MakulaCrudRepository;
import com.angio.server.analyse.services.MakulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("makulaService")
public class MakulaServiceImpl implements MakulaService {


    private final MakulaCrudRepository makulaCrudRepository;

    @Autowired
    public MakulaServiceImpl(MakulaCrudRepository makulaCrudRepository) {
        this.makulaCrudRepository = makulaCrudRepository;
    }

    @Override
    public MakulaEntity addNewMakula(MakulaEntity makula) {
        return makulaCrudRepository.save(makula);
    }
}
