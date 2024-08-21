package javafest.dlpadmin.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javafest.dlpadmin.dto.LoginRequest;
import javafest.dlpadmin.dto.UserDto;
import javafest.dlpadmin.model.User;
import javafest.dlpadmin.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    public UserDto authenticateUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String rawPassword = loginRequest.getPassword();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return toDto(user);
        }
        return null;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public UserDto register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return toDto(userRepository.save(user));
    }

    private UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
