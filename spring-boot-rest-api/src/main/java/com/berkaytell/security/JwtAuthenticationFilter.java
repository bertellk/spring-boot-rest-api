package com.berkaytell.security;

import com.berkaytell.dto.authentication.AuthorityDto;
import com.berkaytell.exception.custom_exceptions.ForbiddenException;
import com.berkaytell.repository.TokenRepository;
import com.berkaytell.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Value("${spring.security.filter.whiteList}")
    private String[] whiteList;

    private final int BEGIN_INDEX = 7;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authenticationHeader.substring(BEGIN_INDEX);
        userName = jwtService.extractUserName(jwt);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            boolean isTokenValid = tokenRepository.findByToken(jwt).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);

            if (Boolean.TRUE.equals(jwtService.isTokenValid(jwt, userDetails)) && Boolean.TRUE.equals(isTokenValid)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        final String pageUrl = getPageUrl(request);
        if (checkAuthorities(request.getServletPath(), request.getMethod(), userName, pageUrl)) {
            filterChain.doFilter(request, response);
            return;
        }

        throw new ForbiddenException();
    }

    private boolean checkAuthorities(String endpoint, String authAction, String userName, String authPage) {
        final AuthorityDto authorityToCheck = getInstance(endpoint, authAction, authPage);
        List<AuthorityDto> authorities = userRepository.getAuthoritiesAssociatedWithUser(userName);

        return checkAuthorityIncludes(authorities, authorityToCheck);
    }

    private AuthorityDto getInstance(String endpoint, String authAction, String authPage) {
        return AuthorityDto.builder().authAction(authAction).authPage(authPage).endpoint(endpoint).build();
    }

    private boolean checkAuthorityIncludes(List<AuthorityDto> authorities, AuthorityDto authorityToCheck) {
        for (AuthorityDto item : authorities) {
            if (item.getAuthAction().equals(authorityToCheck.getAuthAction())
                    && item.getAuthPage().equals(authorityToCheck.getAuthPage())
                    && item.getEndpoint().equals(authorityToCheck.getEndpoint()))
                return true;
        }

        return false;
    }

    // TODO sign-up patladı, endpoint whitelist içinde varsa retun dön
    private boolean whiteListContainsEndpoint() {

    }

    private String getPageUrl(HttpServletRequest request) {
//        String pageUrl = request.getHeader("x-Page-Url");
//
//        if (pageUrl == null || pageUrl.isEmpty())
//            throw new ForbiddenException();
//
//        return pageUrl;
        return "/deneme";
    }
}
