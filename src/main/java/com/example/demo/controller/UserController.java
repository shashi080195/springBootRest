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

	@Autowired
	public OtpService otpService;

	@RequestMapping(value = "/User", method = RequestMethod.GET)
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/User", method = RequestMethod.POST)
	public ResponseEntity<Object> addUser(@Valid @RequestBody User users) {
		User savedUser = userRepository.insert(users);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uname}")
				.buildAndExpand(savedUser.getUname()).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "/User/{uname}", method = RequestMethod.GET)
	public Optional<User> getUserByName(@PathVariable("uname") String uname) {
		Optional<User> user = userRepository.findByUname(uname);
		if (!user.isPresent())
			throw new UserNotFoundException("user not found - " + uname);
		return user;
	}

}
