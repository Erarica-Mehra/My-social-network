package com.socialnetwork.app;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.socialnetwork.app"})
@EnableJpaRepositories("com.socialnetwork.app.repository")
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class MySocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySocialNetworkApplication.class, args);
	}

}
