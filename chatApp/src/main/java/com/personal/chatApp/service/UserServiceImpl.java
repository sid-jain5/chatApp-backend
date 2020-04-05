package com.personal.chatApp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.chatApp.dao.UserDAO;
import com.personal.chatApp.model.User;
import com.personal.chatApp.utility.HashingUtility;

@Service(value="userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public User authenticateUser(String username, String password) throws Exception {
		
		User userFromDB = userDAO.getUserByUsername(username);
		
		//Checking if the user exists in database or not
		if(userFromDB == null) {
			throw new Exception("UserService.USER_NOT_FOUND");
		}
		
		//Checking if the password field is null or not
		String passwordFromDB = userFromDB.getHashedPassword();
		if(passwordFromDB==null) {
			throw new Exception("UserService.NULL_PASSWORD");
		}
		
		String hashPassword = HashingUtility.getHashValue(password); // finding the hash value of the entered password 
		
		//Matching if the entered password matches the password stored in database
		if(!hashPassword.equals(passwordFromDB)){
			throw new Exception("UserService.INVALID_PASSWORD");
		}
		
		userFromDB.setPassword(null);
		return userFromDB; //returning the user if login successfull
	}
	
	public String addUser(User user) throws Exception{
		
		User userFromDB = userDAO.getUserByUsername(user.getUsername());
//		if(userFromDB==null) {
//			System.out.println("USER NULL FOUND");
//		}
		if(userFromDB!=null) {
			throw new Exception("UserService.USER_EXISTS");
		}
		String hashedPassword = HashingUtility.getHashValue(user.getPassword());
		user.setHashedPassword(hashedPassword);
		
		String usernameFromDAO = userDAO.addUser(user);
		
		if(!usernameFromDAO.equals(user.getUsername())) {
			throw new Exception("UserService.ERROR");
		}
		return "UserService.SUCCESS_MESSAGE"+usernameFromDAO;
	}
	
	public List<String> listUsers() throws Exception {
		
		List<String> listOfUsers = userDAO.getUsernameList();
		if(listOfUsers==null || listOfUsers.isEmpty()) {
			throw new Exception("UserService.NO_USERNAME_FOUND");
		}
		return listOfUsers;
	}
}
