package br.com.carlos.JobBoard_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BCryptPasswordConfig {
    @Bean
    public BCryptPasswordConfig bCryptPasswordConfig() {
        return new BCryptPasswordConfig();
    }
}
