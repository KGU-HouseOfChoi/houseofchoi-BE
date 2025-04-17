package com.noraknorak.core.infrastructure.filter;

import com.noraknorak.auth.infrastructure.JwtTokenProvider;
import com.noraknorak.core.service.CustomUserDetailsService;
import com.noraknorak.user.exception.UserErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final String ACCESS_TOKEN_COOKIE = "accessToken";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private static final List<String> EXCLUDED_PATTERNS = List.of(
            "/test/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/swagger-ui/**",
            "/swagger-ui.html",
            "/api/swagger-ui.html"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/v1/auth/") && !path.equals("/v1/auth/relation/verify")) {
            return false;
        }
        return EXCLUDED_PATTERNS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        if (token != null) {
            Authentication auth = createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie accessTokenCookie = WebUtils.getCookie(request, ACCESS_TOKEN_COOKIE);
        if (accessTokenCookie != null) {
            return accessTokenCookie.getValue();
        }

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(7);
        }

        return null;
    }

    private Authentication createAuthentication(String token) {
        Long userId = jwtTokenProvider.getUserId(token);
        UserDetails userDetails = userDetailsService.loadUserById(userId);
        if (userDetails == null) {
            // 사용자 없을 경우 예외 던지기
            throw UserErrorCode.USER_NOT_FOUND.toException();
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
