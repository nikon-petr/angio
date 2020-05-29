package com.angio.angiobackend.api.uploads.repository.impl;

import com.angio.angiobackend.api.uploads.entity.StaticFile;
import com.angio.angiobackend.api.uploads.repository.UploadExtendedRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@AllArgsConstructor
public class UploadExtendedRepositoryImpl implements UploadExtendedRepository {

    private final EntityManager em;

    @Override
    public List<StaticFile> getUnlinkedImages() {
        final String sql = "select u.id, u.type, u.uri from uploads u " +
                "where u.type = 1 and u.id not in ( " +
                "  select u.id from analyses a " +
                "    join uploads u on u.id = a.original_image_id " +
                "  union select u.id from analyses a " +
                "    join uploads u on u.id = a.ischemia_image_id " +
                "  union select u.id from analyses a " +
                "    join uploads u on u.id = a.density_image_id " +
                "  union select u.id from analyses a " +
                "    join uploads u on u.id = a.binarized_image_id " +
                "  union select u.id from analyses a " +
                "    join uploads u on u.id = a.skeletonized_image_id " +
                "  union select u.id from vessels v " +
                "    join uploads u on u.id = v.vessel_image_id " +
                "  union select u.id from vessels v " +
                "    join uploads u on u.id = v.main_vessel_image_id " +
                "  union select u.id from cystic_volumes c " +
                "    join uploads u on u.id = c.angiogram_image_id " +
                "  union select u.id from cystic_volumes c " +
                "    join uploads u on u.id = c.profile_image_id " +
                "  union select u.id from retinal_positive_extremums r " +
                "    join uploads u on u.id = r.angiogram_image_id " +
                "  union select u.id from retinal_positive_extremums r " +
                "    join uploads u on u.id = r.profile_image_id " +
                ")";

        Query query = em.createNativeQuery(sql, StaticFile.class);
        return query.getResultList();
    }
}
