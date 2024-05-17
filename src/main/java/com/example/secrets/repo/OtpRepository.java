package com.example.secrets.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import model.Otp;

@Repository
public interface OtpRepository extends MongoRepository<Otp, String>{
    Optional<Otp> findByNewOtp(String otp);
}
