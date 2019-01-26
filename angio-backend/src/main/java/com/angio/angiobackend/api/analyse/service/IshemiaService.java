package com.angio.angiobackend.api.analyse.service;

import com.angio.angiobackend.api.analyse.entity.IshemiaEntity;

import java.util.Set;

public interface IshemiaService {
    Set<IshemiaEntity> addNewIshemias(Set<IshemiaEntity> ischemias);
}
