package com.Grifo.JuanDiego.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Necesario para validar la clave del admin
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivado para facilitar pruebas locales
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Acceso libre a las páginas
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Aquí defines quién ve qué:
                .requestMatchers("/admin/**").hasAuthority("ADMIN") 
                .requestMatchers("/empleado/**").hasAnyAuthority("ADMIN", "EMPLEADO")
                .anyRequest().authenticated() // Todo lo demás requiere login
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout.permitAll());
            
        return http.build();
    }
}