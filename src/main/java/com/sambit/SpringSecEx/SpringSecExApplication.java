package com.sambit.SpringSecEx;

import com.sambit.SpringSecEx.model.Users;
import com.sambit.SpringSecEx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class 	SpringSecExApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecExApplication.class, args);
	}

}
