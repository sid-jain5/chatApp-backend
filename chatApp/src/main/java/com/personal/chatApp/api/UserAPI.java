package com.personal.chatApp.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.personal.chatApp.model.User;
import com.personal.chatApp.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("UserAPI")
public class UserAPI {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(value="/userLogin", method = RequestMethod.POST)
	public ResponseEntity<User> authenticateUser(@RequestBody User user){
		try {
			User userFromDB = userService.authenticateUser(user.getUsername(), user.getPassword());
			return new ResponseEntity<User>(userFromDB, HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, environment.getProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/userRegister", method=RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user){
		try {
			String status = userService.addUser(user);
			return new ResponseEntity<String>(status,HttpStatus.ACCEPTED);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, environment.getProperty(e.getMessage()));
		}
	}
	
	@RequestMapping(value="/getUsername", method=RequestMethod.GET)
	public ResponseEntity<List<String>> getUsernames(){
		try {
			List<String> usernameList = userService.listUsers();
			return new ResponseEntity<List<String>>(usernameList, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
}
