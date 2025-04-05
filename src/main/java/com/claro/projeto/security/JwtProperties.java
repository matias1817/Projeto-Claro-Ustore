package com.claro.projeto.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
 

    @Value("${jwt.secretKey}")
    private String secretKey;

    // validity in milliseconds
    private long validityInMs = 3600000; // 1h

    // validity in milliseconds
    private long longLiveValidityInMs = 604800000; // 1 semana

}