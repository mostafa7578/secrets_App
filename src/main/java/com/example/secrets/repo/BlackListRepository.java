package com.example.secrets.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.BlackList;

public interface BlackListRepository extends MongoRepository<BlackList, String>{

    Optional<BlackList> findByEmail(String email);
}
