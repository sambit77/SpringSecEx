package com.sambit.SpringSecEx.service;

import com.sambit.SpringSecEx.model.UserPrincipal;
import com.sambit.SpringSecEx.model.Users;
import com.sambit.SpringSecEx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //load user by username from repository layer
        Users user = userRepository.findByUsername(username);

        if(user == null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
}
