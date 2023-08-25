package dev.dfsanchezb.security_demo.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);
    private static final long MINUTES = TimeUnit.MINUTES.toMinutes(5);
    private static final long SECONDS = TimeUnit.SECONDS.toSeconds(60);
    private static final Long ACCESS_TOKEN_VALIDITY_MILLISECONDS = (SECONDS * MINUTES * 1000);

    private TokenUtils() {
    }

    public static String createToken(String name, String email, RSAPrivateKey rsaPrivateKey) {
        log.info("Creating new token by using RSA Private Key.");
        long expirationTime = ACCESS_TOKEN_VALIDITY_MILLISECONDS;
        Date dateIssued = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);

        String tokenString = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(dateIssued)
                .setExpiration(expirationDate)
                .signWith(rsaPrivateKey)
                .compact();

        log.debug("Token created: {}", tokenString);
        return tokenString;
    }

    public static String createToken(String name, String email, byte[] secretKeyEncoded) {
        log.info("Creating new token by using Encoded Key.");
        long expirationTime = ACCESS_TOKEN_VALIDITY_MILLISECONDS;
        Date dateIssued = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);

        String tokenString = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(dateIssued)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secretKeyEncoded))
                .compact();

        log.debug("Token created: {}", tokenString);
        return tokenString;
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token, RSAPrivateKey rsaPrivateKey) {
        log.info("Getting Authentication Information from token {}", token);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(rsaPrivateKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            log.debug("Consumer's email: {}", email);

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            log.error("Error at getting the consumer's email from token {}", token);
            log.error("Issue in Class: {}", e.getClass());
            log.error("Error Message: {}", e.getMessage());
            return null;
        }
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token, byte[] secretKeyEncoded) {
        log.info("Getting Authentication Information from token {}", token);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKeyEncoded)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            log.debug("Consumer's email: {}", email);

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            log.error("Error at getting the consumer's email from token {}", token);
            log.error("Issue in Class: {}", e.getClass());
            log.error("Error Message: {}", e.getMessage());
            return null;
        }
    }
}
