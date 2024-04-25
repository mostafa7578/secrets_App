package com.example.secrets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.secrets.dto.UserDTO;
import com.example.secrets.repo.BlackListRepository;
import com.example.secrets.repo.LoginUsersRepository;

import model.BlackList;
import model.LoginUser;
import model.User;

@Service
public class AdminService {

    public String AdminSecret = "";

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private LoginUsersRepository loginUsersRepository;

    public String secret(String secret) {

        AdminSecret = secret;
        return "Secret set successfully";
    }

    public String getSecret(UserDTO userDTO) {

        Optional<BlackList> blackList = blackListRepository.findByEmail(userDTO.getEmail());
        if (blackList.isPresent()) {
            return "User is blacklisted";
        }

        return AdminSecret;
    }

    public String blockUser(String email) {
        BlackList blackList = blackListRepository.findByEmail(email).orElse(null);
        if (blackList != null) {
            return "User already blocked";
        }

        blackList = new BlackList();
        blackList.setEmail(email);
        blackListRepository.save(blackList);

        return "User blocked successfully";
    }

    public ResponseEntity<List<LoginUser>> users() {
        return ResponseEntity.ok(loginUsersRepository.findAll());
    }

}
