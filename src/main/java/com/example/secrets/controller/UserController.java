package com.example.secrets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PostExchange;

import com.example.secrets.dto.UserDTO;
import com.example.secrets.service.AdminService;
import com.example.secrets.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")  
    public String login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody UserDTO userDTO) {
        return userService.logout(userDTO);
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody UserDTO userDTO) {
        return userService.forgotPassword(userDTO);
    }

    @GetMapping("/secret")
    public String secret(@RequestBody UserDTO userDTO) {
        return adminService.getSecret(userDTO);
    }

}
