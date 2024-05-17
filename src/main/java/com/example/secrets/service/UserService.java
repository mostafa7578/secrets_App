package com.example.secrets.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.*;
import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.secrets.dto.UserDTO;
import com.example.secrets.repo.BlackListRepository;
import com.example.secrets.repo.LoginUsersRepository;
import com.example.secrets.repo.OtpRepository;
import com.example.secrets.repo.UserRepository;

import model.BlackList;
import model.LoginUser;
import model.Otp;
import model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private LoginUsersRepository loginUsersRepository;

    @Autowired
    private OtpRepository otpRepository;

    String AdminEmail = "SecreTs@gmail.com";
    String AdminPassword = "SecreTs";

    public String register(UserDTO userDTO) {
        Optional<User> userExists = userRepository.findByEmail(userDTO.getEmail());
        if (userExists.isPresent()) {
            return "User already exists";
        }

        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }

    public String login(UserDTO userDTO) {
        if (userDTO.getEmail().equals(AdminEmail) && userDTO.getPassword().equals(AdminPassword)) {
            return "admin";
        }

        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            if (user.get().getPassword().equals(userDTO.getPassword())) {

                List<Otp> otps = otpRepository.findAll();
                for (Otp otp : otps) {
                    if (otp.getNewOtp().equals(userDTO.getOtp())) {
                        return "Invalid OTP";
                    }
                }

                Optional<BlackList> blackList = blackListRepository.findByEmail(userDTO.getEmail());
                if (blackList.isPresent()) {
                    return "User is blacklisted";
                }

                Optional<LoginUser> login = loginUsersRepository.findByOtp(userDTO.getOtp());
                if (login.isPresent()) {
                    return "User is already logged in";
                }

                loginUsersRepository.save(LoginUser.builder().otp(userDTO.getOtp()).email(userDTO.getEmail()).build());
                return "User logged in successfully";

            }
        }

        return "Invalid credentials";
    }

    public String logout(UserDTO userDTO) {
        Optional<LoginUser> login = loginUsersRepository.findByOtp(userDTO.getOtp());
        if (login.isPresent()) {
            loginUsersRepository.delete(login.get());
            return "User logged out successfully";
        }
        return "Invalid OTP";
    }

    public String forgotPassword(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {

            List<Otp> otps = otpRepository.findAll();
            for (Otp otp : otps) {
                if (otp.getNewOtp().equals(userDTO.getOtp())) {
                    return "Invalid OTP";
                }
            }
            user.get().setPassword(userDTO.getPassword());
            userRepository.save(user.get());
            return "Password updated successfully";

        }
        return "Invalid OTP or email";
    }
}
