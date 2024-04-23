package com.example.secrets.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.LoginUser;

public interface LoginUsers extends MongoRepository<LoginUser, String>{

    Optional<LoginUser> findByOtp(String otp);
}
