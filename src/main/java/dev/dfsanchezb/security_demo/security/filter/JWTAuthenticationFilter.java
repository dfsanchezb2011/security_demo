package dev.dfsanchezb.security_demo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dfsanchezb.security_demo.response_model.ResponseBody;
import dev.dfsanchezb.security_demo.security.config.RsaKeyProperties;
import dev.dfsanchezb.security_demo.security.model.ApiUserDetails;
import dev.dfsanchezb.security_demo.security.model.AuthCredentials;
import dev.dfsanchezb.security_demo.security.service.TokenUtils;
import dev.dfsanchezb.security_demo.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static dev.dfsanchezb.security_demo.utils.ApplicationConstants.RSA_KEY;
import static dev.dfsanchezb.security_demo.utils.ApplicationConstants.SECRET_KEY;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private RsaKeyProperties rsaKeys;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Starting authentication attempt.");
        AuthCredentials authCredentials;

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (AuthenticationException | IOException e) {
            authCredentials = new AuthCredentials();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword(), Collections.emptyList());

        log.info("Authenticating user {}", authToken.getPrincipal());
        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ApiUserDetails userDetails = (ApiUserDetails) authResult.getPrincipal();

        String token = "";
        if (rsaKeys.getKeySystem().equals(RSA_KEY)) {
            token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getEmail(), rsaKeys.getPrivateKey());
        } else if (rsaKeys.getKeySystem().equals(SECRET_KEY)) {
            token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getEmail(), rsaKeys.getSecretKeyEncoded());
        }

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseBody<String> body = new ResponseBody<>(0, "Success", "Bearer " + token);
        response.getWriter().write(JsonUtils.getBodyAsJsonString(body));
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseBody<String> body = new ResponseBody<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), failed.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JsonUtils.getBodyAsJsonString(body));
        response.getWriter().flush();
    }
}
