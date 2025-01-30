package br.com.carlos.JobBoard_backend.utils;

import br.com.carlos.JobBoard_backend.config.JwtConfig;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.exceptions.UserNotFound;
import br.com.carlos.JobBoard_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class JwtActions {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    public LoginResponseDto jwtCreate(LoginDto dto) {

        var user = userRepository.findByEmail(dto.email());

        if (user.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        var now = Instant.now();
        var expiresIn = 7 * 24 * 60 * 60L;
        var role = user.get().getRoles();

        var claims = JwtClaimsSet.builder()
                .issuer("jobboard")
                .subject(String.valueOf(user.get().getUserId()))
                .issuedAt(now)
                .claim("role", role.name())
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtConfig.jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDto(jwtValue, expiresIn);
    }

}
