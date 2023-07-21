package com.example.soonsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SoonsulApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties ,"
			+ "classpath:oauth.yml";


	public static void main(String[] args) {

		//System.setProperty("logging.file.name", "/home/ubuntu/Back-end/src/main/resources/static/"
		//		+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".log");

		System.setProperty("logging.file.name", "/home/ubuntu/log/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".log");

		new SpringApplicationBuilder(SoonsulApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
