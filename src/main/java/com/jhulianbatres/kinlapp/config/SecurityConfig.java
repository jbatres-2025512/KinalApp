package com.jhulianbatres.kinlapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Habilita @PreAuthorize en servicios y controllers
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
                        .requestMatchers("/login", "/register").permitAll()

                        .requestMatchers(
                                "/client/edit/**",
                                         "/client/delete/**",
                                         "/product/edit/**",
                                         "/product/delete/**",
                                         "/sale/edit/**",
                                         "/sale/delete/**",
                                         "/saleDetail/edit/**",
                                         "/saleDetail/delete/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/client/**",
                                "/product/**",
                                "/sale/**",
                                "/saleDetail/**"
                        ).authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}