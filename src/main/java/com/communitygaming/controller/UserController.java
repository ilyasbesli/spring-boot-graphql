package com.communitygaming.controller;

import com.communitygaming.model.User;
import com.communitygaming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @QueryMapping(value = "getUser")
    public Optional<User> getUser(@Argument(name = "userId") String userId) {
        return userService.findById(userId);
    }
}
