package com.sambit.SpringSecEx;

import com.sambit.SpringSecEx.model.Users;
import com.sambit.SpringSecEx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersDataInitializer implements CommandLineRunner {

    @Autowired
    private final UserRepository repository;

    public UsersDataInitializer(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        //create username and passwords in database

        repository.save(new Users(1,"admin","admin"));

    }
}
