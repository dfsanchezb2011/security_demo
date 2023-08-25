package dev.dfsanchezb.security_demo.security.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Data
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(RsaKeyProperties.class);

    @Value("${rsa.public-key}")
    private RSAPublicKey publicKey;

    @Value("${rsa.private-key}")
    private RSAPrivateKey privateKey;

    @Value("${rsa.secret-key}")
    private String secretKey;

    @Value("${rsa.key-system}")
    private String keySystem;

    private byte[] secretKeyEncoded;

    @PostConstruct
    private void validatingRSAKeys() {
        log.info("Checking RSA Keys are being loaded");
        log.debug("Public Key: {}", getPublicKey());
        log.debug("Private Key: {}", getPrivateKey());
        log.debug("Secret Key: {}", getSecretKey());
        setSecretKeyEncoded(Base64.getEncoder().encode(getSecretKey().getBytes()));
        log.debug("Key system to Use: {}", getKeySystem());
        log.info("All keys were loaded.");
    }
}
