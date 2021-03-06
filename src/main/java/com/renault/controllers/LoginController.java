package com.renault.controllers;

import com.renault.dtos.UserDto;
import com.renault.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public void login(@RequestBody UserDto user) {
        userService.verifyUser(user.getUsername(), user.getPassword());
    }

}