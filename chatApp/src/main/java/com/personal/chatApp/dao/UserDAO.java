package com.personal.chatApp.dao;

import com.personal.chatApp.model.User;

public interface UserDAO {
	
	public User getUserByUsername(String username) throws Exception;
	
	public String addUser(User user) throws Exception; 
	
}
