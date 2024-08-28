package javafest.dlpadmin.service;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javafest.dlpadmin.dto.LoginRequest;
import javafest.dlpadmin.dto.UserDto;
import javafest.dlpadmin.model.RefreshToken;
import javafest.dlpadmin.model.User;
import javafest.dlpadmin.repository.RefreshTokenRepository;
import javafest.dlpadmin.repository.UserRepository;
import javafest.dlpadmin.util.JwtTokenProvider;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {
    private UserRepository userRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public UserDto authenticateUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String rawPassword = loginRequest.getPassword();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return toDto(user);
        }
        return null;
    }

    public boolean authenticateUser(String userId, String email) {
        return userRepository.existsByUserIdAndEmail(userId, email);
    }

    public String createAccessTokenFromRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .filter(token -> !token.getExpiryDate().isBefore(Instant.now()))
                .map(token -> jwtTokenProvider.createAccessToken(token.getUserId()))
                .orElse(null);
    }

    public void saveRefreshToken(String refreshToken, String userId) {
        Instant expiryDate = jwtTokenProvider.getExpiryInstantFromToken(refreshToken);
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken, expiryDate));
    }

    public void invalidateRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    public UserDto register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return toDto(userRepository.save(user));
    }

    private UserDto toDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }
}
