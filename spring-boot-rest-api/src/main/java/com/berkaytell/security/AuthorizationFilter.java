package com.berkaytell.security;

import com.berkaytell.dto.authentication.AuthorityDto;
import com.berkaytell.exception.custom_exceptions.DisabledUserException;
import com.berkaytell.exception.custom_exceptions.ForbiddenException;
import com.berkaytell.repository.TokenRepository;
import com.berkaytell.repository.UserRepository;
import com.berkaytell.service.token.TokenService;
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
public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Value("${spring.security.filter.whiteList}")
    private String[] whiteList;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authenticationHeader.substring(7);
        userName = jwtService.extractUserName(jwt);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (Boolean.TRUE.equals(jwtService.isTokenValid(jwt, userDetails)) && Boolean.TRUE.equals(tokenService.isTokenValid(jwt))) {
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
        final String endpoint = request.getServletPath();

        if (isWhiteListed(endpoint)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (checkAuthorities(endpoint, request.getMethod(), userName, pageUrl)) {
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

    private boolean isWhiteListed(String endpoint) {
        for (String item : whiteList) {
            final String patternPrefix = item.replace("/**", "");
            if (endpoint.startsWith(patternPrefix))
                return true;
        }
        return false;
    }

    private String getPageUrl(HttpServletRequest request) {
        //TODO frontend bağlanınca uncomment yap
//        String pageUrl = request.getHeader("x-Page-Url");
//
//        if (pageUrl == null || pageUrl.isEmpty())
//            throw new ForbiddenException();
//
//        return pageUrl;
        return "/deneme";
    }
}
