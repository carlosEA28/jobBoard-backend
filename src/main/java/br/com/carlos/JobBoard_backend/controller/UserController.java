package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.dto.*;
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
import java.util.Map;
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

    @PostMapping("/apply/{userId}/{companyId}")
    public String applyForJob(@PathVariable String companyId,
                              @PathVariable String userId
    ) {
        return userService.applyForJob(userId, companyId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateJob(@PathVariable String userId, @RequestBody UpdateUserDto dto) {

        return userService.updateUserInfo(userId, dto);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteJob(@PathVariable String userId) {

        userService.deleteJob(userId);
        return ResponseEntity.ok().body("Job deleted");
    }


    @PostMapping("/redeem-password")
    public ResponseEntity<Map<String, String>> redeemPassword(@RequestBody @Valid UserReedemPasswordDto userReedemPasswordDto) {
        userService.reedemPassword(userReedemPasswordDto.email());

        return ResponseEntity.ok().body(Map.of("message", "Send the redeem password link to your email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody @Valid UserResetPasswordDto dto) {
        userService.resetPassword(dto);

        return ResponseEntity.ok().body(Map.of("message", "Credentials updated"));
    }
}
