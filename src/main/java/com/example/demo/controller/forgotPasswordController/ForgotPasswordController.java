package com.example.demo.controller.forgotPasswordController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import com.example.demo.models.OtpModel;
import com.example.demo.models.User;
import com.example.demo.models.UserResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.MyEmailService;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ForgotPasswordController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OtpService otpService;
    @Autowired
    public MyEmailService myEmailService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private OtpModel otpModel;
    private UserResponse userResponse;
    private static final int AVAILABLE_USER = 1;

    public ForgotPasswordController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<UserResponse> forgotPassword(@RequestBody User user) {
        try {
            int otp = otpService.generateOTP(user.getEmail());
            String message = String.valueOf(otp);
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                userResponse = new UserResponse("1", message);
                return ResponseEntity.status(200).body(userResponse);
            } else {
                // logger.info(user.getUsername() + user.getEmail());
                // logger.info(String.valueOf(userRepository.findByUsername(user.getUsername()).isPresent()));
                userResponse = new UserResponse("0", "User not available");
                return ResponseEntity.status(400).body(userResponse);
            }

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            userResponse = new UserResponse("0", "unable to generate otp");
            return ResponseEntity.status(400).body(userResponse);
        }
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ResponseEntity validateOtp(@RequestParam("otpnum") int otpnum, @RequestBody User user) {
        final String SUCCESS = "1";
        final String FAIL = "0";
        if (otpnum >= 0) {
            int serverOtp = otpService.getOtp(user.getEmail());

            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(user.getEmail());
                    Optional<User> IsUser = userRepository.findByEmail(user.getEmail());
                    User users = IsUser.get();
                    users.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    users.setIsVerified(true);
                    userRepository.delete(users);
                    userRepository.insert(users);
                    otpModel = new OtpModel(SUCCESS, "otp verified", true, user.getEmail());

                    return ResponseEntity.status(HttpStatus.OK).body(otpModel);
                }
            }
        }
        otpModel = new OtpModel(FAIL, "otp not verified", false, user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(otpModel);
    }
}