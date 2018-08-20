package com.example.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.models.OtpModel;
import com.example.demo.models.User;
import com.example.demo.models.UserResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.MyEmailService;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shashi shekhar Aug 14, 2018
 */
@Controller
public class OtpController {

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

	// private static final int USERNAME_NOT_AVAILABLE = 1;
	// private static final int EMAIL_NOT_AVAILABLE = 1;
	public OtpController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * for this otp service key will be the device id ,so that if otp is sent to a
	 * device then it will be only validated from that particular device
	 */
	@RequestMapping(value = "/generateOtp", method = RequestMethod.POST)
	public ResponseEntity generateOtp(@Valid @RequestBody User user) {
		try {
			int otp = otpService.generateOTP(user.getEmail());
			String message = String.valueOf(otp);
			if (userService.checkUserAvailibility(user) == AVAILABLE_USER) {
				// myEmailService.sendOtpMessage(user.getEmail(), "OTP -SpringBoot", message);
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				user.setIsVerified(false);
				userRepository.insert(user);
				userResponse = new UserResponse("1", message);
				return ResponseEntity.status(200).body(userResponse);
			} else {
				if (userService.isUserAvailable(user)) {
					// myEmailService.sendOtpMessage(user.getEmail(), "OTP -SpringBoot", message);
					userResponse = new UserResponse("1", message);
					return ResponseEntity.status(200).body(userResponse);
				}
				userResponse = new UserResponse("0", "already registered user");
				return ResponseEntity.status(400).body(userResponse);
			}

		} catch (Exception ex) {
			logger.info(ex.getMessage());
			userResponse = new UserResponse("0", "unable to generate otp");
			return ResponseEntity.status(400).body(userResponse);
		}

	}

	@RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
	public ResponseEntity validateOtp(@RequestParam("otpnum") int otpnum, @RequestParam("email") String email) {
		final String SUCCESS = "1";
		final String FAIL = "0";
		if (otpnum >= 0) {
			int serverOtp = otpService.getOtp(email);

			if (serverOtp > 0) {
				if (otpnum == serverOtp) {
					otpService.clearOTP(email);
					Optional<User> user = userRepository.findByEmail(email);
					User users = user.get();
					users.setIsVerified(true);
					userRepository.delete(users);
					userRepository.insert(users);
					otpModel = new OtpModel(SUCCESS, "otp verified", true, email);

					return ResponseEntity.status(HttpStatus.OK).body(otpModel);
				}
			}
		}
		otpModel = new OtpModel(FAIL, "otp not verified", false, email);
		return ResponseEntity.status(HttpStatus.OK).body(otpModel);
	}
}