package com.control.conversor.services;

import com.control.conversor.entities.RefreshToken;
import com.control.conversor.exception.TokenRefreshException;
import com.control.conversor.repositories.RefreshTokenRepository;
import com.control.conversor.repositories.UserRepository;
import com.control.conversor.utils.DateUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${api.security.token.jwtRefreshExpirationHour}")
    private Integer hourRefreshToken;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        var userOpt = userRepository.findById(userId);

        userOpt.ifPresent(refreshToken::setUser);


        refreshToken.setExpiryDate(Instant.now().plusMillis(180000));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("Refresh token was expired. Please make a new signin request");
        }
    }

    @Transactional
    public void deleteByUserId(String userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    @Transactional
    public void updateExpiryDate(RefreshToken refreshToken) {
        refreshToken.setExpiryDate(DateUtils.hourToInstant(hourRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }

}
