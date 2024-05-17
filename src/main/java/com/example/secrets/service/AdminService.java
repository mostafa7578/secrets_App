package com.example.secrets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.secrets.dto.UserDTO;
import com.example.secrets.repo.BlackListRepository;
import com.example.secrets.repo.LoginUsersRepository;
import com.example.secrets.repo.OtpRepository;

import model.BlackList;
import model.LoginUser;
import model.Otp;
import model.User;

@Service
public class AdminService {

    public String AdminSecret = "";

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private LoginUsersRepository loginUsersRepository;

    @Autowired
    private OtpRepository otpRepository;

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

    public String otp(String new_otp) {
        Otp otp = Otp.builder().newOtp(new_otp).build();
        otpRepository.save(otp);
        return "OTP set successfully";
    }

    public String deleteOtp(String deleted_otp) {
        Otp otp = otpRepository.findByNewOtp(deleted_otp).orElse(null);
        if (otp == null) {
            return "OTP not found";
        }

        otpRepository.delete(otp);
        return "OTP deleted successfully";
    }

}
