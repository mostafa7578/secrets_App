package com.example.secrets.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import model.LoginUser;

@Repository
public interface LoginUsersRepository extends MongoRepository<LoginUser, String>{

    Optional<LoginUser> findByOtp(String otp);
}
