package com.clonemovie.Cinemaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CinemaprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaprojectApplication.class, args);
	}

}
