package com.angio.server.util;

import com.angio.server.AngioAppProperties;
import com.angio.server.security.entities.UserEntity;
import eu.bitwalker.useragentutils.DeviceType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private AngioAppProperties angioAppProperties;

    private static final String CLAIM_KEY_USERNAME = "usr";
    private static final String CLAIM_KEY_AUTHORITIES = "aut";

    public String getTokenBody(String token){
        if (token != null) {
            return token.replaceFirst(angioAppProperties.jwt.getTokenType() + " ", "");
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.get(CLAIM_KEY_USERNAME, String.class);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        Date date = new Date();
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date expiration;
        Date date = new Date();
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getIssuedAt();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public long getIdFromToken(String token) {
        String id;
        try {
            final Claims claims = getClaimsFromToken(token);
            id = claims.getId();
        } catch (Exception e) {
            id = null;
        }
        return Long.valueOf(Objects.requireNonNull(id));
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(angioAppProperties.jwt.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }


    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (DeviceType.TABLET.getName().equals(audience) || DeviceType.MOBILE.getName().equals(audience));
    }

    public String generateToken(UserDetails userDetails, long id, String deviceType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUTHORITIES, userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setId(String.valueOf(id))
                .setAudience(deviceType)
                .setExpiration(new Date(System.currentTimeMillis() + angioAppProperties.jwt.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, angioAppProperties.jwt.getSecret())
                .compact();
    }

    public String generateToken(Claims claims, long id, String deviceType){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setId(String.valueOf(id))
                .setAudience(deviceType)
                .setExpiration(new Date(System.currentTimeMillis() + angioAppProperties.jwt.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, angioAppProperties.jwt.getSecret())
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token, long id) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims, id, getAudienceFromToken(token));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String refreshToken(String token, long id, String username) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_USERNAME, username);
            refreshedToken = generateToken(claims, id, getAudienceFromToken(token));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }
}