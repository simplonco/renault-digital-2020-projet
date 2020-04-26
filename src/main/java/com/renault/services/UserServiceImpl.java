package com.renault.services;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void verifyUser(String username, String password) {
        // TODO use the password encoder to check if the raw password matches the password in database
    }

}
