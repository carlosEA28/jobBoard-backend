package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.CreateUserDto;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import br.com.carlos.JobBoard_backend.enums.Roles;
import br.com.carlos.JobBoard_backend.exceptions.UserAlreadyExists;
import br.com.carlos.JobBoard_backend.exceptions.UserNotFound;
import br.com.carlos.JobBoard_backend.repository.UserRepository;
import br.com.carlos.JobBoard_backend.utils.JwtActions;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtActions jwtActions;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.bCryptPasswordEncoder = passwordEncoder;
    }


    public UserEntity createUser(CreateUserDto dto) {
        userRepository.findByEmail(dto.email()).ifPresent(user -> {
            throw new UserAlreadyExists("user already exists");
        });

        var user = new UserEntity();
        user.setFirstName(dto.firstName());
        user.setSecondName(dto.secondName());
        user.setEmail(dto.email());
        user.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        user.setRoles(Roles.USER);

        return userRepository.save(user);
    }

    public LoginResponseDto loginUser(LoginDto dto) {
        var user = userRepository.findByEmail(dto.email()).orElseThrow(() -> new UserAlreadyExists("This user already exists"));

        if (!bCryptPasswordEncoder.matches(dto.password(), user.getPassword())) {
            throw new WrongThreadException("Email/Password incorrect");
        }

        return jwtActions.jwtCreate(dto);
    }
}
