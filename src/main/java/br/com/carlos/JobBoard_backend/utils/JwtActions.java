package br.com.carlos.JobBoard_backend.utils;

import br.com.carlos.JobBoard_backend.config.JwtConfig;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.dto.currentUserResponse;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import br.com.carlos.JobBoard_backend.enums.Roles;
import br.com.carlos.JobBoard_backend.exceptions.UserNotFound;
import br.com.carlos.JobBoard_backend.repository.CompanyRepository;
import br.com.carlos.JobBoard_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private CompanyRepository companyRepository;

    public LoginResponseDto jwtCreate(LoginDto dto) {
        var user = userRepository.findByEmail(dto.email());
        var company = companyRepository.findByBusinessEmail(dto.email());

        if (user.isEmpty() && company.isEmpty()) {
            throw new UserNotFound("Account not found");
        }

        var now = Instant.now();
        var expiresIn = 7 * 24 * 60 * 60L;

        String subject;
        String role;

        if (user.isPresent()) {
            subject = String.valueOf(user.get().getUserId());
            role = user.get().getRoles().name();
        } else {
            subject = String.valueOf(company.get().getCompanyId());
            role = company.get().getRoles().name();
        }

        // Criar o token com os claims apropriados
        var claims = JwtClaimsSet.builder()
                .issuer("jobboard")
                .subject(subject)
                .issuedAt(now)
                .claim("role", role)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtConfig.jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDto(jwtValue, expiresIn);
    }

    public String getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
}
