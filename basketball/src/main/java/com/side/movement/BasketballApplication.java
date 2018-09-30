package com.side.movement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BasketballApplication {

	public static void main(String[] args) {

		SpringApplication.run(BasketballApplication.class, args);
	}
}
