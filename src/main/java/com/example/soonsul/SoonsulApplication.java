package com.example.soonsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SoonsulApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties ,"
			+ "classpath:oauth.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(SoonsulApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
