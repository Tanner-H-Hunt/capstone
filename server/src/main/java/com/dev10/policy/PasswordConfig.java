package com.dev10.policy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        int HASHING_ROUNDS = 10;

        // bcrypt salts passwords automatically
        return new BCryptPasswordEncoder(HASHING_ROUNDS);
    }

}
