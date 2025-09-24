package com.example.Manager.controllers;

import com.example.Manager.dto.UserDto;
import com.example.Manager.entities.User;
import com.example.Manager.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void create (@RequestBody UserDto userDto) {
        userService.create(userDto);
    }
}