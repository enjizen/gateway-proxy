package com.tua.wanchalerm.gateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KeyConfig {
    @Value("${rsa.private.key}")
    private String rsaPrivateKey;
}
