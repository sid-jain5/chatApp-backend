package com.personal.chatApp.service;

import java.util.List;

import com.personal.chatApp.model.User;

public interface UserService {

	public User authenticateUser(String username, String password) throws Exception;
	
	public String addUser(User user) throws Exception;
	
	public List<String> listUsers() throws Exception;
}
