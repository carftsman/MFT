package com.dhatvibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling  //added
@SpringBootApplication
public class MfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfoApplication.class, args);
	}

}
