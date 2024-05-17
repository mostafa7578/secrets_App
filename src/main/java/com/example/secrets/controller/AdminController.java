package com.example.secrets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.secrets.service.AdminService;
import com.fasterxml.jackson.databind.JsonNode;

import model.LoginUser;
import model.User;

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

    @GetMapping("/users")   
    public ResponseEntity<List<LoginUser>> users() {
        return adminService.users();
    }

    @PostMapping("/otp")
    public String otp(@RequestParam String new_otp) {
        return adminService.otp(new_otp);
    }

    @DeleteMapping("/otp")
    public String deleteOtp(@RequestParam String deleted_otp) {
        return adminService.deleteOtp(deleted_otp);
    }
}
