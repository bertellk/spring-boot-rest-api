package com.berkaytell.security;

import com.berkaytell.security.handlers.CustomSecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthorizationFilter authorizationFilter;
    private final CustomSecurityExceptionHandler exceptionHandler;

    @Value("${spring.security.filter.whiteList}")
    private String[] whiteList;

    @Value("${spring.security.filter.blackList}")
    private String[] blackList;

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(c -> c.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(ahr -> ahr
                        .requestMatchers(whiteList).permitAll()
                        //.requestMatchers("/api/admin/**").hasAnyAuthority("ADMIN")  //ihtiyacımız olmayacak, kendi yetkilendirme yapımızı kuracağız
                        .requestMatchers(blackList).denyAll() // database drop edildiğinde permitAll yap sonrasında tekrar denyAll yap
                        .anyRequest().authenticated()
                )
                .sessionManagement(sm -> sm
                        .sessionFixation().migrateSession()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(exceptionHandler, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, CustomSecurityExceptionHandler.class);
        http.addFilterAfter(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
