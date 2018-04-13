package com.epam.chatspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontController {
	
	@RequestMapping("/")
    @ResponseBody
    public String welcome() {
        return "Welcome to RestTemplate Example.";
    }
	
	@RequestMapping("/user")
    @ResponseBody
    public String getUser() {
        return "USEEEEER";
    }
}
