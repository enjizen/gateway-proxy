package com.tua.wanchalerm.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KeyConfig {
    @Value("${rsa.private.key}")
    private String rsaPrivateKey;
}
