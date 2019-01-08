package com.angio.angiobackend.analyse.services;

import com.angio.angiobackend.analyse.entities.IshemiaEntity;

import java.util.Set;

public interface IshemiaService {
    Set<IshemiaEntity> addNewIshemias(Set<IshemiaEntity> ischemias);
}
