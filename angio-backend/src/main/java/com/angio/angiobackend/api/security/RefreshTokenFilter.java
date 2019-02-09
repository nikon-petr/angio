package com.angio.angiobackend.api.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class RefreshTokenFilter implements Filter {

    private final JwtJdbcTokenStore tokenStore;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException,
            ServletException {

        log.debug("doFilter() - start");
        HttpServletRequest httpRequest = (HttpServletRequest) req;

        log.debug("doFilter() - extract refresh_token");
        String refreshToken = httpRequest.getParameter("refresh_token");

        if (refreshToken != null && !tokenStore.inWhiteList(refreshToken)) {
            log.debug("doFilter() - token: {} is not in white list", refreshToken);
            throw new UnauthorizedUserException("Unauthorized");
        }

        log.debug("doFilter() - end");
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {}
}
