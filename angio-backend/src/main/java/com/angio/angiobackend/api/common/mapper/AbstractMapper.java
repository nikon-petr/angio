package com.angio.angiobackend.api.common.mapper;

import java.util.List;
import java.util.Set;

public interface AbstractMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
    Set<E> toEntities(List<D> dtos);
    List<D> toDtos(Set<E> entities);
}
