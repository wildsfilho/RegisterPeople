package com.register.people.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = {RegisterPeopleApplication.class}, basePackages = "com.register.people")
@ComponentScan({"com.register.people"})
@EnableJpaRepositories("com.register.people.repository")
public class RegisterPeopleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterPeopleApplication.class, args);
	}

}
