package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.dto.CreateUserDto;
import br.com.carlos.JobBoard_backend.dto.LoggedUserDto;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.entity.CurriculumEntity;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import br.com.carlos.JobBoard_backend.service.UserService;
import br.com.carlos.JobBoard_backend.utils.JwtActions;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtActions jwtActions;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody CreateUserDto dto) {
        var user = userService.createUser(dto);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login/local")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        var token = userService.loginUser(loginDto);

        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<LoggedUserDto> getUserInfo(@PathVariable("userId") String userId) {
        var user = userService.loggedUser(userId);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/currentUser")
    public String getCurrentUser(Authentication authentication) {

        return jwtActions.getLoggedUser();
    }

    @PostMapping("/cv/{userId}")
    public ResponseEntity<CurriculumEntity> uploadCv(@Valid @PathVariable("userId") String userId, MultipartFile file) {
        var cv = userService.uploadCv(userId, file);

        return ResponseEntity.ok().body(cv);
    }
}
