package com.sambit.SpringSecEx.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping
    public String getMessage(HttpServletRequest request)
    {
        return "Hello world, your sessionId is :- " + request.getSession().getId();
    }
}
