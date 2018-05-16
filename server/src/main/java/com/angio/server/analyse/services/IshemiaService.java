package com.angio.server.analyse.services;

import com.angio.server.analyse.entities.IshemiaEntity;

import java.util.Set;

public interface IshemiaService {
    Set<IshemiaEntity> addNewIshemias(Set<IshemiaEntity> ischemias);
}
