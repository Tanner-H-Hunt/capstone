package com.dev10.policy;

import com.dev10.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class AuthConfig {
    private final PasswordConfig passwordConfig;

    public AuthConfig(PasswordConfig passwordConfig){
        this.passwordConfig = passwordConfig;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserService userService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordConfig.passwordEncoder());
        return new ProviderManager(provider);
    }


}
