package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.*;
import br.com.carlos.JobBoard_backend.entity.CurriculumEntity;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import br.com.carlos.JobBoard_backend.enums.AuthProvider;
import br.com.carlos.JobBoard_backend.enums.Roles;
import br.com.carlos.JobBoard_backend.exceptions.*;
import br.com.carlos.JobBoard_backend.mail.MailService;
import br.com.carlos.JobBoard_backend.repository.CompanyRepository;
import br.com.carlos.JobBoard_backend.repository.CurriculumRepository;
import br.com.carlos.JobBoard_backend.repository.UserRepository;
import br.com.carlos.JobBoard_backend.utils.JwtActions;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtActions jwtActions;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${token.expiration.seconds:300}")
    private long tokenExpirationSeconds;


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
        var user = userRepository.findByEmail(dto.email()).orElseThrow(() -> new UserNotFound("User Not found"));

        if (!bCryptPasswordEncoder.matches(dto.password(), user.getPassword())) {
            throw new WrongCreadentials("Email/Password incorrect");
        }
        return jwtActions.jwtCreate(dto);
    }

    //salva o user que fez login pelo google no banco
    public void loginRegisterByGoogleOauth2(OAuth2AuthenticationToken token) {

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

        userRepository.save(newUser);
    }

    public LoggedUserDto loggedUser(String userId) {
        var userExists = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFound("User not found"));
        return new LoggedUserDto(userExists.getFirstName(), userExists.getSecondName(), userExists.getEmail(), userExists.getPassword());
    }

    public CurriculumEntity uploadCv(String userId, MultipartFile file) {

        curriculumRepository.findByUser_UserId(UUID.fromString(userId))
                .ifPresent(cv -> {
                    throw new CvAlreadyExists();
                });

        try {
            var cv = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "cv",
                            "format", "png"
                    )
            );

            var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFound("User not found"));

            var newCv = new CurriculumEntity();
            newCv.setUser(user);
            newCv.setCvUrl(cv.get("url").toString());


            return curriculumRepository.save(newCv);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to upload the file");
        }
    }

    //fazer async
    //fazer retornar um html link para o curriculo
    public String applyForJob(String userId, String companyId) {

        var companyExists = companyRepository.findByCompanyId(UUID.fromString(companyId))
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        var userExists = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFound("User not found"));

        var cvExists = curriculumRepository.findByUser_UserId(UUID.fromString(userId));

        if (cvExists.isEmpty()) {
            throw new CvNotFound();
        }

        return mailService.sendEmailTest(
                companyExists.getBusinessEmail(),
                "CV from " + userExists.getFirstName() + " " + userExists.getSecondName(),
                userExists.getCurriculum().getCvUrl()
        );
    }

    @Transactional
    public ResponseEntity<Void> updateUserInfo(String userId, UpdateUserDto dto) {
        if (userId == null || dto == null) {
            throw new IllegalArgumentException("Job ID and DTO must not be null");
        }

        var userExists = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(JobNotFound::new);

        if (dto.firstName() != null) {
            userExists.setFirstName(dto.firstName());
        }
        if (dto.secondName() != null) {
            userExists.setSecondName(dto.secondName());
        }

        userRepository.save(userExists);

        return ResponseEntity.noContent().build();
    }

    public void deleteJob(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

    private void sendPasswordResetEmail(String email, String token) {

        String subject = "Redefinição de senha solicitado";

        String resetUrl = String.format("https://Board.com/reset-password?token=%s", token); //colocar o link do frontend de resetar senha

        String body = String.format("""
                Olá,
                
                Recebemos uma solicitação para redefinir sua senha. \
                Clique no link abaixo para redefinir sua senha:
                
                %s
                
                Se você não solicitou a redefinição de senha, ignore este e-mail.
                
                Atenciosamente,
                Equipe Job Board""", resetUrl);

        mailService.sendEmailTest(email, subject, body);
    }

    public void reedemPassword(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound("User not found"));

        var token = UUID.randomUUID().toString();

        user.withResetToken(token, Instant.now().plusSeconds(this.tokenExpirationSeconds));

        userRepository.save(user);

        sendPasswordResetEmail(user.getEmail(), token);
    }

    public void resetPassword(UserResetPasswordDto userResetPasswordDto) {
        var customer = userRepository
                .findByResetToken(userResetPasswordDto.token())
                .orElseThrow(() -> new UserNotFound("User not found"));

        customer.setPassword(
                bCryptPasswordEncoder.encode(userResetPasswordDto.password())
        );

        customer.withResetToken(null, null);

        userRepository.save(customer);
    }
}

