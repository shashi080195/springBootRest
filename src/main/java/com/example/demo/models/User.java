package com.example.demo.models;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@Document(collection = "user")
@TypeAlias("user")
@ToString
public class User {

	@Id
	private ObjectId _id;
	@NotNull(message = "Username can't be null")
	private String username;
	private String email;
	private String password;
	private boolean isVerified;

	public User() {

	}

	public User(ObjectId _id, String email, String username, String password, boolean isVerified) {
		this._id = _id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isVerified = isVerified;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
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

	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean getIsVerified() {
		return this.isVerified;
	}
}
