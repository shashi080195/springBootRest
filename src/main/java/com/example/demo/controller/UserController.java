package com.example.demo.controller;

// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import com.example.demo.repositories.*;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.hateoas.Resource;
// import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import javax.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	public OtpService otpService;

	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/User", method = RequestMethod.GET)
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/User", method = RequestMethod.POST)
	public ResponseEntity<Object> addUser(@Valid @RequestBody User users) {
		users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
		User savedUser = userRepository.insert(users);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Username}")
				.buildAndExpand(savedUser.getUsername()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "/User/{Username}", method = RequestMethod.GET)
	public Optional<User> getUserByName(@PathVariable("Username") String Username) {
		Optional<User> user = userRepository.findByUsername(Username);
		if (!user.isPresent())
			throw new UserNotFoundException("user not found - " + Username);
		return user;
	}
}
