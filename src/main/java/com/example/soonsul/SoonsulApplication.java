package com.example.soonsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SoonsulApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties ,"
			+ "classpath:oauth.yml";

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}


	public static void main(String[] args) {
		System.setProperty("logging.file.name", "/home/ubuntu/log/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".log");

		new SpringApplicationBuilder(SoonsulApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}


}
