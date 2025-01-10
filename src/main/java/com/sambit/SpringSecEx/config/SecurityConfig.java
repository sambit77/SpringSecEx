package com.sambit.SpringSecEx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable()) //Disable CSRF token for post put delete requests
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) //Enable login form
                .httpBasic(Customizer.withDefaults()); //Enable authentication in postman
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return  http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("Virat")
//                .password("Kohli")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("Rohit")
//                .password("Sharma")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return  provider;
    }
}
