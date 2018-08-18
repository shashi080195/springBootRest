package com.example.demo.controller;

// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import com.example.demo.repositories.*;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	Gson gson = new Gson();

	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/User", method = RequestMethod.GET)
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/User", method = RequestMethod.POST)
	public ResponseEntity addUser(@Valid @RequestBody User users) {
		if (userService.checkUserAvailibility(users)) {
			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
			User savedUser = userRepository.insert(users);

			UserResponse response = new UserResponse("1", "user created successfully");

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");

	}

	@RequestMapping(value = "/User/{Username}", method = RequestMethod.GET)
	public Optional<User> getUserByName(@PathVariable("Username") String Username) {
		Optional<User> user = userRepository.findByUsername(Username);
		if (!user.isPresent())
			throw new UserNotFoundException("user not found - " + Username);
		return user;
	}
}
