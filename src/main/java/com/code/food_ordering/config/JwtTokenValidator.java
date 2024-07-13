package com.code.food_ordering.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JwtTokenValidator extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(JwtTokenValidator.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstants.JWT_HEADER);

        if (jwt != null && !jwt.isBlank() && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);

            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
                String email = claims.get("email", String.class);
                String authorities = claims.get("authorities", String.class);

                if (email != null && authorities != null) {
                    List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.log(Level.WARNING, "Token claims are invalid: {0}", claims);
                    throw new BadCredentialsException("Invalid token claims");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Token validation error: {0}", e.getMessage());
                throw new BadCredentialsException("Token invalid: " + e.getMessage());
            }
        } else {
            logger.log(Level.WARNING, "JWT is missing or improperly formatted");
        }

        filterChain.doFilter(request, response);
    }
}
