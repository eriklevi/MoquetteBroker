package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(DemoApplication.class);
		final ApplicationContext context = application.run(args);

		MoquetteBroker server = context.getBean(MoquetteBroker.class);
	}
}
