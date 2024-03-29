package com.example.demo.controller;

// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import com.example.demo.repositories.*;
import com.example.demo.controller.customannotations.ExecutionLog;
import com.example.demo.error.RedundantUserException;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.hateoas.Resource;
// import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
// import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
//import services
import com.example.demo.service.OtpService;
import com.example.demo.service.UserDetailsServiceImpl;
import com.google.gson.Gson;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailsServiceImpl userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	public OtpService otpService;
	@Autowired
	public UserDetailsServiceImpl userDetailsServiceImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	Gson gson = new Gson();

	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/User", method = RequestMethod.GET)
	public List<User> getAllUser() {
		return userDetailsServiceImpl.getAllUser();
	}

	@RequestMapping(value = "/User", method = RequestMethod.POST)
	public ResponseEntity addUser(@Valid @RequestBody User users) {
		if (userService.checkUserAvailibility(users) == 1) {
			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
			User savedUser = userRepository.insert(users);

			UserResponse response = new UserResponse("1", "user created successfully");

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");

	}

	@RequestMapping(value = "/User/{Username}", method = RequestMethod.GET)
	public Optional<User> getUserByName(@PathVariable("Username") String Username) {
		return userDetailsServiceImpl.getUserByUserName(Username);
	}

	@RequestMapping(value = "/singUp", method = RequestMethod.POST)
	public ResponseEntity generateOtp(@Valid @RequestBody User user) {
		try {
			// int otp = otpService.generateOTP(user.getEmail());
			// String message = String.valueOf(otp);
			// myEmailService.sendOtpMessage(user.getEmail(), "OTP -SpringBoot", message);
			// user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			// user.setIsVerified(false);
			// userRepository.insert(user);
			logger.info("here");
			UserResponse userResponse = new UserResponse("1", "otp generated");
			return ResponseEntity.status(200).body(userResponse);
		} catch (Exception ex) {
			UserResponse userResponse = new UserResponse("0", "unable to generate otp");
			return ResponseEntity.status(400).body(userResponse);
		}

	}
}
