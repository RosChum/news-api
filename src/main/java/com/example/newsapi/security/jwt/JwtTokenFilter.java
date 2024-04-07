package com.example.newsapi.security.jwt;

import com.example.newsapi.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getToken(request);

            if (jwtProvider.checkValidToken(token)) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getUserEmail(token));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                log.error("JwtTokenFilter doFilterInternal: UncheckValidToken: " + token);
            }

        } catch (Exception ex) {
            log.error("JwtTokenFilter doFilterInternal: Authentication error!");
        }

        filterChain.doFilter(request, response);
    }


    private String getToken(HttpServletRequest request) {
        String headers = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("getToken headers: " + headers);

        if (headers != null && headers.startsWith("Bearer ")) {
            return headers.substring(7);
        }

        return null;
    }

}
