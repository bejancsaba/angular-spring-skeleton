package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtBuilderService {

    public static final String INVALID_TOKEN = "INVALID TOKEN";
    public static final String LOGIN_SESSION_KEY_NAME = "loginSession";
    public static final String JWT_ISSUER = "DemoIssuer";

    private final Algorithm algorithm = Algorithm.HMAC512("secret");

    @Value("${security.jwt.token-validity-in-seconds}")
    private long tokenValidity;

    @Value("${security.user.name}")
    private String userName;

    @Value("${security.user.password}")
    private String userPassword;

    @Value("${security.user.roles}")
    private String userRoles;

    @Value("${security.admin.name}")
    private String adminName;

    @Value("${security.admin.password}")
    private String adminPassword;

    @Value("${security.admin.roles}")
    private String adminRoles;

    public String createToken(String user, String password) {
        String roles = "";
        if (userName.equals(user) && userPassword.equals(password)) {
            roles = userRoles;
        }
        else if (adminName.equals(user) && adminPassword.equals(password)) {
            roles = adminRoles;
        }

        return roles.isBlank() ? INVALID_TOKEN : buildToken(user, roles);
    }

    private String buildToken(String name, String roles) {
        String token = INVALID_TOKEN;
        try {
            String loginSession = UUID.randomUUID().toString();

            token = JWT.create()
                    .withClaim("auth", roles)
                    .withClaim(LOGIN_SESSION_KEY_NAME, loginSession)
                    .withSubject(name)
                    .withIssuer(JWT_ISSUER)
                    .withExpiresAt(determineValidity())
                    .sign(algorithm);
        }
        catch (com.auth0.jwt.exceptions.JWTCreationException exception) {
            log.error("JWT creation issue: " + exception);
        }

        return token;
    }

    private Date determineValidity() {
        return calculateValidityTimeStamp(tokenValidity);
    }

    private Date calculateValidityTimeStamp(long validityInSeconds) {
        return new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(validityInSeconds));
    }
}
