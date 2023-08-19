package dev.dfsanchezb.security_demo.security.service;

import dev.dfsanchezb.security_demo.dao.ApiUserRepository;
import dev.dfsanchezb.security_demo.model.ApiUser;
import dev.dfsanchezb.security_demo.security.model.ApiUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApiUserDetailService implements UserDetailsService {

    private final ApiUserRepository apiUserRepository;

    public ApiUserDetailService(ApiUserRepository apiUserRepository) {
        this.apiUserRepository = apiUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApiUser apiUser = apiUserRepository.findOneByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Email " + email + " does not exists.")
                );

        return new ApiUserDetails(apiUser);
    }
}
