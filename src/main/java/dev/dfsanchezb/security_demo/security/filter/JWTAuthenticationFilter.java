package dev.dfsanchezb.security_demo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.dfsanchezb.security_demo.response_model.ResponseBody;
import dev.dfsanchezb.security_demo.security.model.ApiUserDetails;
import dev.dfsanchezb.security_demo.security.model.AuthCredentials;
import dev.dfsanchezb.security_demo.security.service.TokenUtils;
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

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials;

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (AuthenticationException | IOException e) {
            authCredentials = new AuthCredentials();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword(), Collections.emptyList());

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ApiUserDetails userDetails = (ApiUserDetails) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getEmail());

        response.addHeader("Authorization", "Bearer " + token);
        response.setHeader("Content-Type", "application/json");

        ResponseBody<String> body = new ResponseBody<>(0, "Success", "Bearer " + token);
        String mapStr = new Gson().toJson(body);
        response.getWriter().write(mapStr);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
