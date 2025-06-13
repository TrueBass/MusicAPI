package com.example.musicapi.configs;

import com.example.musicapi.filters.JwtAuthFilter;
import com.example.musicapi.services.implementations.CustomUserDetailsService;
import com.example.musicapi.utils.JwtAuthEntryPoint;
import com.example.musicapi.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthEntryPoint authEntryPoint) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/music-api/users/signup",
                                "/music-api/users/login",
                                "/music-api/users/refresh",
                                "/music-api/users/logout",
                                "/music-api/users/update-password",
                                "/music-api/users/update-username",
                                "/music-api/users/update-email",
                                "/music-api/users/update-social-credit",
                                "/music-api/users/{username}",
                                "/music-api/songs/add",
                                "/music-api/songs/{id}",
                                "/music-api/songs/search",
                                "/music-api/songs/bytes/{songId}",
                                "/music-api/songs/info/all/{playerId}",
                                "/music-api/songs/popular/all",
                                "music-api/songs/top10",
                                "/music-api/playlist/create",
                                "/music-api/playlist/get-all",
                                "/music-api/playlist/delete",
                                "/music-api/playlist/change-visibility").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        http.addFilterBefore(jwtAuthFilter(jwtProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
        return new JwtAuthFilter(jwtProvider, customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
