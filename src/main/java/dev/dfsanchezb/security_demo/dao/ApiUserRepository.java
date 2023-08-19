package dev.dfsanchezb.security_demo.dao;

import dev.dfsanchezb.security_demo.model.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApiUserRepository extends JpaRepository<ApiUser, UUID> {

    Optional<ApiUser> findOneByEmail(String email);
}
