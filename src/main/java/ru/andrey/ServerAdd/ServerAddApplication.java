package ru.andrey.ServerAdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.andrey.ServerAdd.commands.AddCommand;
import ru.andrey.ServerAdd.commands.Command;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ServerAddApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ServerAddApplication.class, args);
	}

}
