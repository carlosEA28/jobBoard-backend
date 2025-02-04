package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
//@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/google")
    public ResponseEntity<String> loginGoogleAuth(HttpServletResponse response) throws IOException {
        // Faz o redirecionamento direto para o Google, sem exibir a tela de escolha
        response.sendRedirect("/oauth2/authorization/google");
        return ResponseEntity.ok("Redirecting to Google...");
    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<?> handleGoogleSuccess(OAuth2AuthenticationToken token) throws IOException {
        // Salva o usuário no banco e redireciona para a home page
        userService.loginRegisterByGoogleOauth2(token);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:5173/")).build();
    }
}


