package com.example.demo.models;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@TypeAlias("user")
public class User {

	@Id
	private ObjectId _id;
	private String name;
	@NotNull(message = "Username can't be null")
	private String username;
	private String email;
	private String password;

	public User() {

	}

	public User(ObjectId _id, String name, String email, String username, String password) {
		this._id = _id;
		// this.name = name;
		this.username = username;
		// this.email = email;
		this.password = password;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
