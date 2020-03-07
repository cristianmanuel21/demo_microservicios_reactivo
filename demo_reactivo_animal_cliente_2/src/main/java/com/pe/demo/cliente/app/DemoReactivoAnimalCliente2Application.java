package com.pe.demo.cliente.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DemoReactivoAnimalCliente2Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoReactivoAnimalCliente2Application.class, args);
	}

}
