package com.example.secrets.service;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.secrets.dto.UserDTO;
import com.example.secrets.repo.BlackListRepository;
import com.example.secrets.repo.LoginUsers;
import com.example.secrets.repo.UserRepository;

import model.BlackList;
import model.LoginUser;
import model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private LoginUsers loginUsers;

    Integer otp1 = 836295;
    Integer otp2 = 638046;
    Integer otp3 = 138356;
    Integer otp4 = 501486;
    Integer otp5 = 730149;

    String AdminEmail = "SecreTs@gmail.com";
    String AdminPassword = "SecreTs";

    public String register(UserDTO userDTO) {
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

                if (userDTO.getOtp().equals(otp1.toString()) || userDTO.getOtp().equals(otp2.toString())
                        || userDTO.getOtp().equals(otp3.toString()) || userDTO.getOtp().equals(otp4.toString())
                        || userDTO.getOtp().equals(otp5.toString())) {
                    Optional<BlackList> blackList = blackListRepository.findByEmail(userDTO.getEmail());
                    if (blackList.isPresent()) {
                        return "User is blacklisted";
                    }
                    Optional<LoginUser> login = loginUsers.findByOtp(userDTO.getEmail());
                    if (login.isPresent()) {
                        return "User is already logged in";
                    }
                    loginUsers.save(LoginUser.builder().otp(userDTO.getOtp()).build());
                    return "User logged in successfully";
                }
            }
        }

        return "Invalid credentials";
    }

    public String logout(UserDTO userDTO) {
        Optional<LoginUser> login = loginUsers.findByOtp(userDTO.getOtp());
        if (login.isPresent()) {
            loginUsers.delete(login.get());
            return "User logged out successfully";
        }
        return "Invalid OTP";
    }

    public String forgotPassword(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            if (userDTO.getOtp().equals(otp1.toString()) || userDTO.getOtp().equals(otp2.toString())
                    || userDTO.getOtp().equals(otp3.toString()) || userDTO.getOtp().equals(otp4.toString())
                    || userDTO.getOtp().equals(otp5.toString())) {
                user.get().setPassword(userDTO.getPassword());
                userRepository.save(user.get());
                return "Password updated successfully";
            }
        }
        return "Invalid OTP or email";
    }
}
