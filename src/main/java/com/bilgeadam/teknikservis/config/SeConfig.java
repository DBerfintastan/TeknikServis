package com.bilgeadam.teknikservis.config;

import com.bilgeadam.teknikservis.security.JWTAuthenticationFilter;
import com.bilgeadam.teknikservis.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SeConfig  {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, @Autowired AuthenticationConfiguration authenticationConfiguration) throws Exception {
            http.authorizeHttpRequests(request -> request
                            .requestMatchers("/booking/user/**").hasAuthority("ROLE_USER")
                            .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/booking/admin/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/sale/admin/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers("/proposal/user/**").hasAuthority("ROLE_USER")
                            .requestMatchers("/proposal/admin/**").hasAuthority("ROLE_ADMIN")

                            .anyRequest().permitAll())
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                    .addFilterAfter(new JWTAuthorizationFilter(), JWTAuthenticationFilter.class);

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
