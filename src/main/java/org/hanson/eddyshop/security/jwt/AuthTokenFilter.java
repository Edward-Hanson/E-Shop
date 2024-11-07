package org.hanson.eddyshop.security.jwt;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.security.user.ShopUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ShopUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
         String jwt = parseJwt(request);
         if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
             String username = jwtUtils.getUsernameFromToken(jwt);
             UserDetails userDetails = userDetailsService.loadUserByUsername(username);
             Authentication auth = new UsernamePasswordAuthenticationToken(
                     userDetails,null,userDetails.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(auth);
         }
         filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if ( StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
             return headerAuth.substring(7);
        }
        return null;
    }
}
