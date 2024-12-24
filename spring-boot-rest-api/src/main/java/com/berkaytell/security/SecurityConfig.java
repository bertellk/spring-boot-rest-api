package com.berkaytell.security;

import com.berkaytell.security.handlers.CustomAccessDeniedHandler;
import com.berkaytell.security.handlers.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Value("${spring.security.filter.whiteList}")
    private String[] whiteList;

    @Value("${spring.security.filter.blackList}")
    private String[] blackList;

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
                )
                .exceptionHandling(ex -> ex
                        //.accessDeniedHandler(customAccessDeniedHandler) //ihtiyaç olursa aç
                        .authenticationEntryPoint(customAuthenticationFailureHandler)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
