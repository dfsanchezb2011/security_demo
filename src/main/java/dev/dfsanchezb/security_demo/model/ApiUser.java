package dev.dfsanchezb.security_demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "api_user")
public class ApiUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_user_id")
    private UUID apiUserId;
    private String email;
    private String username;
    private String password;
}
