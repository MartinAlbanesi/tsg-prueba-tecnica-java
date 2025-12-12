package com.martinalba.tsg_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:env.properties")
public class TsgChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsgChallengeApplication.class, args);
	}

}
