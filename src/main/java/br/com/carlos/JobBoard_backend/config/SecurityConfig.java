package br.com.carlos.JobBoard_backend.config;

import br.com.carlos.JobBoard_backend.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtConfig jwtConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(c -> c.disable())//HABILITAR DPS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login/local").permitAll()
                        .requestMatchers(HttpMethod.POST, "/company").permitAll()
                        .requestMatchers(HttpMethod.POST, "/company/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/info/{userId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/user/currentUser").authenticated()
                        .requestMatchers(HttpMethod.POST, "/user/cv/{userId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/apply/{userId}/{companyId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/logo/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/company/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/company/post/{companyId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/type/{jobType}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/location/{location}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/salary-range/{range}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/salary-based/{based}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/category/{category}").permitAll()
                        .anyRequest().permitAll()) //arrumar e por em authenticated
                .oauth2ResourceServer(config -> config.jwt(jwt -> jwt.decoder(jwtConfig.jwtDecoder())));


        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // 🔹 Permitir o front-end acessar
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
