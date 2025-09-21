package com.psp.psp.config;

import com.psp.psp.repository.interfaces.IUserRepository;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    private final IUserRepository iUserRepository;

    public ApplicationConfig(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Bean
    UserDetailsService userDetailsService(){
        return iUserRepository::findByEmail;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
