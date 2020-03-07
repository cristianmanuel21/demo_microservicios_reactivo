package com.pe.demo.cliente.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Value("${config.base.endpoint}")
	private String url;
	
	/*
    @Bean
	public WebClient webclient() {
		return WebClient.create(url);
	}
	 * 
	 * */
	
	
	
	
	@Bean
	@LoadBalanced
	public WebClient.Builder webclient() {
		return WebClient.builder().baseUrl(url);
	}
}
