package ru.andrey.ServerAdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerAddApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ServerAddApplication.class, args);
		System.out.println("Hello world");
	}

}
