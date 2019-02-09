package com.angio.angiobackend.api.security.service;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface SecurityService {

    @Transactional
    String revoke(@NonNull String tokenId);

    @Transactional
    String removeToken(@NonNull String tokenId);
}
