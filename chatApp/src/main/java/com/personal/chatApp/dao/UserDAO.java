package com.personal.chatApp.dao;

import java.util.List;

import com.personal.chatApp.model.User;

public interface UserDAO {
	
	public User getUserByUsername(String username) throws Exception;
	
	public String addUser(User user) throws Exception; 
	
	public List<String> getUsernameList() throws Exception;
	
}
