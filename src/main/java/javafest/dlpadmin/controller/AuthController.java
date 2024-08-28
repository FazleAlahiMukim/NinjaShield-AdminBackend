package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import javafest.dlpadmin.dto.LoginRequest;
import javafest.dlpadmin.dto.UserDto;
import javafest.dlpadmin.service.AuthService;
import javafest.dlpadmin.util.JwtTokenProvider;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        UserDto userDto = authService.authenticateUser(loginRequest);

        if (userDto != null) {
            String accessToken = jwtTokenProvider.createAccessToken(userDto.getUserId());
            String refreshToken = jwtTokenProvider.createRefreshToken(userDto.getUserId());

            authService.saveRefreshToken(refreshToken, userDto.getUserId());

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();
            response.addHeader("Set-Cookie", refreshTokenCookie.toString());

            userDto.setToken(accessToken);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@CookieValue String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String newAccessToken = authService.createAccessTokenFromRefreshToken(refreshToken);
        if (newAccessToken != null) {
            return ResponseEntity.ok(newAccessToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue String refreshToken, HttpServletResponse response) {
        authService.invalidateRefreshToken(refreshToken);
        response.addHeader("Set-Cookie", ResponseCookie.from("refreshToken", "").path("/").maxAge(0).build().toString());
        return ResponseEntity.ok("Logged out successfully");
    }

    // @PostMapping("/register")
    // public ResponseEntity<UserDto> register(@RequestBody User user) {
    // UserDto userDto = authService.register(user);
    // if (userDto != null) {
    // String token = jwtTokenProvider.createToken(userDto.getUserId());
    // userDto.setToken(token);
    // return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    // } else {
    // return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    // }
    // }

}
