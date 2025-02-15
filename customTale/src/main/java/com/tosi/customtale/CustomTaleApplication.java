package com.tosi.customtale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tosi")
public class CustomTaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomTaleApplication.class, args);
	}

}
