package com.example.microservice.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JpaAuditConfig {

    private final HttpServletRequest request;

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of(request.getHeader("customer"));
    }

}