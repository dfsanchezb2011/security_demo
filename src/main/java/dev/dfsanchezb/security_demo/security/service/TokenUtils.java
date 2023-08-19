package dev.dfsanchezb.security_demo.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);
    private static final long MINUTES = TimeUnit.MINUTES.toMinutes(5);
    private static final long SECONDS = TimeUnit.SECONDS.toSeconds(60);
    private static final String ACCESS_TOKEN_SECRET_KEY = "Secur1ty/Dem04*ppl1cati0n";
    private static final byte[] ACCESS_KEY_ENCODED = Base64.getEncoder().encode(ACCESS_TOKEN_SECRET_KEY.getBytes());
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = (long) (SECONDS * MINUTES);

    private TokenUtils() {

    }

    public static String createToken(String name, String email) {
        log.info("Creating new token.");
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date dateIssued = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);

        String tokenString = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(dateIssued)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_KEY_ENCODED))
                .compact();

        log.debug("Token created: {}", tokenString);
        return tokenString;
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        log.info("Getting Authentication Information from token {}", token);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_KEY_ENCODED)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            log.debug("Consumer's email: {}", email);

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            log.error("Error at getting the consumer's email from token {}\nClass: {}\nMessage: {}", token, e.getClass(), e.getMessage());
            log.error("Issue in Class: {}", e.getClass());
            log.error("Error Message: {}", e.getMessage());
            return null;
        }
    }
}
