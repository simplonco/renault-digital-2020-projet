package com.renault.controllers;

import com.renault.dtos.LoginDto;
import com.renault.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public boolean login(@RequestBody LoginDto login) {
        return loginService.verifyUser(login.getUsername(), login.getPassword());
    }

}