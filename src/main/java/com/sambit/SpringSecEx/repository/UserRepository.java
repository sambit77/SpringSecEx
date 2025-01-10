package com.sambit.SpringSecEx.repository;

import com.sambit.SpringSecEx.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);
}
