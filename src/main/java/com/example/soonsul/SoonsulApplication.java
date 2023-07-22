package com.example.soonsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SoonsulApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties ,"
			+ "classpath:oauth.yml";

	public static void main(String[] args) {
		System.out.println(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
		System.setProperty("logging.file.name", "/home/ubuntu/log/"
				+ LocalDate.now(ZoneId.of("Asia/Seoul"))+".log");

		new SpringApplicationBuilder(SoonsulApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}


}
