package com.control.conversor.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.control.conversor.entities.User;
import com.control.conversor.exception.ApplicationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JwtUtils {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.jwtExpirationHour}")
    private Integer hourToken;

    @Value("${api.security.cookie.token}")
    private String tokenCookie;

    @Value("${api.security.cookie.token-refresh}")
    private String tokenRefreshCookie;

    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateToken(user.getUsername());
        return generateCookie(tokenCookie, jwt, "/api");
    }
    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(tokenRefreshCookie, refreshToken, "/api/auth/refresh-token");
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).maxAge(3600).httpOnly(true).build();
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, tokenCookie);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, tokenRefreshCookie);
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String generateToken(String username){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(username)
                    .withExpiresAt(DateUtils.hourToInstant(hourToken))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new ApplicationException("Erro na geração do token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(tokenCookie,"").path("/api").build();
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return ResponseCookie.from(tokenRefreshCookie, "").path("/api/auth/refresh-token").build();
    }
}
