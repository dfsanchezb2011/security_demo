package dev.dfsanchezb.security_demo.security.filter;

import dev.dfsanchezb.security_demo.security.config.RsaKeyProperties;
import dev.dfsanchezb.security_demo.security.service.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static dev.dfsanchezb.security_demo.utils.ApplicationConstants.RSA_KEY;
import static dev.dfsanchezb.security_demo.utils.ApplicationConstants.SECRET_KEY;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            UsernamePasswordAuthenticationToken authenticationToken = null;

            if (rsaKeys.getKeySystem().equals(RSA_KEY)) {
                authenticationToken = TokenUtils.getAuthentication(token, rsaKeys.getPrivateKey());
            } else if (rsaKeys.getKeySystem().equals(SECRET_KEY)) {
                authenticationToken = TokenUtils.getAuthentication(token, rsaKeys.getSecretKeyEncoded());
            }

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
