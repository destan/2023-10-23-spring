package com.example.demo;

import com.example.demo.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		new User();
		SpringApplication.run(DemoApplication.class, args);
	}

}
