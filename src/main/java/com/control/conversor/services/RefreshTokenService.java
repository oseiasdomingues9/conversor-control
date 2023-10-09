package com.control.conversor.services;

import com.control.conversor.entities.RefreshToken;
import com.control.conversor.exception.TokenRefreshException;
import com.control.conversor.repositories.RefreshTokenRepository;
import com.control.conversor.repositories.UserRepository;
import com.control.conversor.utils.DateUtils;
import com.control.conversor.utils.HeaderUtils;
import com.control.conversor.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${authentication.token_expiration.refresh_token_expiration}")
    private Integer hourRefreshToken;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        var userOpt = userRepository.findById(userId);

        userOpt.ifPresent(refreshToken::setUser);

        refreshToken.setExpiryDate(DateUtils.hourToInstant(hourRefreshToken));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public ResponseEntity<Void> refreshToken(String refreshTokenValue) {
        if ((refreshTokenValue != null) && (!refreshTokenValue.isEmpty())) {
            RefreshToken refreshToken = findByToken(refreshTokenValue)
                    .orElseThrow(() -> new TokenRefreshException("O token de atualização não está na base de dados!"));
            verifyExpiration(refreshToken);
            var user = refreshToken.getUser();
            if (user.isActive()){
                updateExpiryDate(refreshToken);
                var jwtCookie = jwtUtils.generateJwtCookie(user);
                var httpHeaders = HeaderUtils.getHeaders(jwtCookie);
                return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
            }else {
                throw new TokenRefreshException("Desculpe, sua conta está desativada. Por favor, entre em contato com o suporte para obter assistência");
            }
        }
        throw new TokenRefreshException("O token de atualização está vazio!");
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("O token de atualização expirou. Por favor, faça uma nova solicitação de login.");
        }
    }

    @Transactional
    public void deleteByUserId(String userId) {
        userRepository.findById(userId).ifPresent(refreshTokenRepository::deleteByUser);
    }

    @Transactional
    public void updateExpiryDate(RefreshToken refreshToken) {
        refreshToken.setExpiryDate(DateUtils.hourToInstant(hourRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }

}
