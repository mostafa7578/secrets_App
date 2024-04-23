package com.example.secrets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.secrets.service.AdminService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/secret")
    public String secret(@RequestBody JsonNode jsonNode) {
        String s = jsonNode.get("secret").asText();
        return adminService.secret(s);
    }

    @PostMapping("/block/user")
    public String blockUser(@RequestBody JsonNode jsonNode) {
        String email = jsonNode.get("email").asText();
        return adminService.blockUser(email);
    }
}
