package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.dto.LoginRequest;
import javafest.dlpadmin.dto.UserDto;
import javafest.dlpadmin.model.User;
import javafest.dlpadmin.service.UserService;
import javafest.dlpadmin.util.JwtTokenProvider;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest) {
        UserDto userDto = userService.authenticateUser(loginRequest);

        if (userDto != null) {
            String token = jwtTokenProvider.createToken(userDto.getUserId());
            userDto.setToken(token);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user) {
        UserDto userDto = userService.register(user);
        if (userDto != null) {
            String token = jwtTokenProvider.createToken(userDto.getUserId());
            userDto.setToken(token);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
