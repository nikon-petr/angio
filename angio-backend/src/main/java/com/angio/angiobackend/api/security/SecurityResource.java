package com.angio.angiobackend.api.security;

import com.angio.angiobackend.api.common.dto.Response;
import com.angio.angiobackend.api.security.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Security", description = "Angio security REST API")
@AllArgsConstructor
@RestController
public class SecurityResource {

    private final SecurityService securityService;

    @ApiOperation("Revoke token by id")
    @PostMapping("/oauth/revoke/{tokenId}")
    public Response revokeToken(@PathVariable String tokenId) {
        return Response.success(securityService.revoke(tokenId));
    }

    @ApiOperation("Remove token by id")
    @PostMapping("/oauth/remove/{tokenId}")
    public Response removeToken(@PathVariable String tokenId) {
        return Response.success(securityService.removeToken(tokenId));
    }
}
