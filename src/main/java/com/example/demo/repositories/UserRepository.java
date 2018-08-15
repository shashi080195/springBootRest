package com.example.demo.repositories;

import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findBy_id(long id);

	List<User> findByName(String name);

	Optional<User> findByUname(String uname);
}
