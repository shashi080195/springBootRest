package com.example.demo.repositories;

import com.example.demo.models.User;
import com.mongodb.operation.UpdateUserOperation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findBy_id(long id);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

}
