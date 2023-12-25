package com.bilgeadam.teknikservis.config;

import com.bilgeadam.teknikservis.security.JWTAuthenticationFilter;
import com.bilgeadam.teknikservis.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SeConfig  {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, @Autowired AuthenticationConfiguration authenticationConfiguration) throws Exception{
        http.authorizeHttpRequests(x->x
                .requestMatchers(HttpMethod.GET).hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        http.csrf(customizer -> customizer.disable());


//        http.exceptionHandling(x->x.authenticationEntryPoint(unauthorizedHandler));

        http.sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
        http.addFilterAfter(new JWTAuthorizationFilter(),JWTAuthenticationFilter.class);

//        CorsConfigurationSource corsconsfigurationsource = new CorsConfigurationSource()
//        {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request)
//            {
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedHeaders(List.of(""));
//                config.setAllowedOrigins(List.of(""));
//                config.setAllowedMethods(List.of("*"));
//                return config;
//            }
//        };
//        http.cors(customizer -> customizer.configurationSource(corsconsfigurationsource));


        return http.build();
    }

}
