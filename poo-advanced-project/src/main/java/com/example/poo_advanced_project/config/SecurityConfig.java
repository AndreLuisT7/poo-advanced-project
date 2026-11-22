package com.example.poo_advanced_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // paginas e recursos do portal
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**",
                                "/favicon.ico", "/error").permitAll()

                        // o aluno se cadastra e cancela a propria assinatura sem login
                        .requestMatchers(HttpMethod.POST, "/api/alunos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/assinaturas/cancelar").permitAll()

                        // o portal lista eventos e instituicoes publicamente
                        .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instituicoes/**").permitAll()

                        // cadastrar evento e instituicao e ato administrativo
                        .requestMatchers(HttpMethod.POST, "/api/eventos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/instituicoes/**").hasRole("ADMIN")

                        // dados pessoais de aluno e disparo em massa de e-mail
                        .requestMatchers("/api/alunos/**").hasRole("ADMIN")
                        .requestMatchers("/api/informativo/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout.permitAll())
                .build();
    }
}
