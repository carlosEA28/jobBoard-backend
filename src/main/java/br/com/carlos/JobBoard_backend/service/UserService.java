package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.CreateUserDto;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import br.com.carlos.JobBoard_backend.enums.AuthProvider;
import br.com.carlos.JobBoard_backend.enums.Roles;
import br.com.carlos.JobBoard_backend.exceptions.UserAlreadyExists;
import br.com.carlos.JobBoard_backend.exceptions.UserNotFound;
import br.com.carlos.JobBoard_backend.repository.UserRepository;
import br.com.carlos.JobBoard_backend.utils.JwtActions;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
        user.setAuthProvider(AuthProvider.LOCAL);

        return userRepository.save(user);
    }

    public LoginResponseDto loginUser(LoginDto dto) {
        var user = userRepository.findByEmail(dto.email()).orElseThrow(() -> new UserAlreadyExists("This user already exists"));

        if (!bCryptPasswordEncoder.matches(dto.password(), user.getPassword())) {
            throw new WrongThreadException("Email/Password incorrect");
        }
        return jwtActions.jwtCreate(dto);
    }

    //salva o user que fez login pelo google no banco
    public UserEntity loginRegisterByGoogleOauth2(OAuth2AuthenticationToken token) {

        OAuth2User user = token.getPrincipal();
        String name = user.getAttribute("name");
        String email = user.getAttribute("email");

        System.out.println("USER FROM GOOGLE EMAIL IS: " + email);
        System.out.println("USER FROM GOOGLE NAME IS: " + name);

        userRepository.findByEmail(email).orElseThrow(() -> new UserAlreadyExists("User already exists"));

        var newUser = new UserEntity();
        newUser.setFirstName(name);
        newUser.setEmail(email);
        newUser.setAuthProvider(AuthProvider.GOOGLE);

        return userRepository.save(newUser);
    }
}
