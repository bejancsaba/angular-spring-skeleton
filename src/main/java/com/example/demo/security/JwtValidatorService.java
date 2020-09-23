package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.security.JwtBuilderService.JWT_ISSUER;

public class JwtValidatorService {

    private static final Claim EMPTY_CLAIM = new NullClaim();
    private static final String BEARER_PREFIX = "Bearer";

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtValidatorService() {
        this.algorithm = Algorithm.HMAC512("secret");
        this.verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();
    }

    public String extractToken(String authorizationHeaderValue) {
        if (StringUtils.hasText(authorizationHeaderValue) && authorizationHeaderValue.startsWith(BEARER_PREFIX)) {
            return authorizationHeaderValue.substring(BEARER_PREFIX.length() + 1);
        }
        return "";
    }

    public Optional<Authentication> getAuthentication(String token) {
        return Optional.of(createAuthenticationFromJwt(verifyToken(token)));
    }

    public DecodedJWT verifyToken(String authToken) {
        try {
            return verifier.verify(authToken);
        }
        catch (JWTVerificationException exception) {
            throw new AuthenticationServiceException("JWT decoding failed at validation", exception);
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    private Authentication createAuthenticationFromJwt(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();

        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        String permissions = claims.getOrDefault("auth", EMPTY_CLAIM).asString();
        if (!StringUtils.isEmpty(permissions)) {
            authorities = Arrays.stream(permissions.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        EnhancedUserDetails principal = getPrincipal(jwt.getSubject(), claims, authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private EnhancedUserDetails getPrincipal(String subject, Map<String, Claim> claims, Collection<? extends GrantedAuthority> authorities) {
        return EnhancedUserDetails.builder()
                .userDetails(new User(subject, "", authorities))
                .build();
    }
}
