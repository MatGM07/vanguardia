package com.software2.colegio.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new HttpSessionSecurityContextRepository()) // Usar la sesión HTTP para persistir el SecurityContext
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register").authenticated() // Permitir estas rutas
                        .requestMatchers("/content/citas/**").authenticated()
                        .requestMatchers("/content/horas").authenticated()// Requiere autenticación para /content/citas
                        .anyRequest().permitAll() // Permitir las demás rutas sin autenticación
                )
                .addFilterBefore(new RoleBasedAccessFilter(), UsernamePasswordAuthenticationFilter.class) // Agregar el filtro personalizado
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .anonymous(anonymous -> anonymous.disable());

        return http.build();
    }
}