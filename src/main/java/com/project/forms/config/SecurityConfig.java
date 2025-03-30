package com.project.forms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static com.project.forms.utils.APIConstants.AUTH;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private GoogleJwtTokenService googleJwtTokenService;

    @Autowired
    private GuestJwtTokenService guestTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH + "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())))
                .csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        final NimbusJwtDecoder googleDecoder = NimbusJwtDecoder.withSecretKey(googleJwtTokenService.getSecretKey()).build();
        final NimbusJwtDecoder guestDecoder = NimbusJwtDecoder.withSecretKey(guestTokenService.getSecretKey()).build();

        return token -> {
            try {
                return googleDecoder.decode(token); // Try Google JWT first
            } catch (Exception e) {
                return guestDecoder.decode(token); // If it fails, try Guest JWT
            }
        };
    }
}

