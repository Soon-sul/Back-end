package com.example.soonsul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.time.LocalDate;
import java.time.ZoneId;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SoonsulApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties ,"
			+ "classpath:oauth.yml";


	public static void main(String[] args) {

		System.setProperty("logging.file.name", "/home/ubuntu/log/"
				+ LocalDate.now(ZoneId.of("Asia/Seoul"))+".log");

		new SpringApplicationBuilder(SoonsulApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}


}
