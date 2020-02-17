package com.angio.angiobackend.api.security.service;

public interface SecurityService {

    String revoke(String tokenId);

    String removeToken(String tokenId);
}
