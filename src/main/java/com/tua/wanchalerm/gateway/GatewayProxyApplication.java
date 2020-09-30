package com.tua.wanchalerm.gateway;

import com.tua.wanchalerm.gateway.filter.RequestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayProxyApplication.class, args);
	}

	@Bean
	public RequestFilter requestFilter() {
		return new RequestFilter();
	}
}
