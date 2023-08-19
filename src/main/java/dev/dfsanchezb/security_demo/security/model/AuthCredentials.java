package dev.dfsanchezb.security_demo.security.model;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
