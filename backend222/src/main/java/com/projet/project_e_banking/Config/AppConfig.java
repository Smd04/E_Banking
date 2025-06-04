package com.projet.project_e_banking.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {
        "com.projet.project_e_banking.Config",
        "com.projet.project_e_banking.Service",
        "com.projet.project_e_banking.Controller",
        "com.projet.project_e_banking.Repository"})
@Import({WebConfig.class, JpaConfig.class, SecurityConfig.class})
public class AppConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
