package com.example.secrets.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import model.User;

@Repository
public interface UserRepository  extends MongoRepository <User, String>{

    Optional<User> findByEmail(String email);
}
