package com.personal.chatApp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.personal.chatApp.entity.UserEntity;
import com.personal.chatApp.model.User;

@Repository(value = "userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public User getUserByUsername(String username) throws Exception {
		
		Query q = entityManager.createQuery("Select u from UserEntity u where u.username = :username");
		q.setParameter("username", username);
		
		User u = null;
		//System.out.println("here dao: " + q.getResultList());
		if(q.getResultList()==null || q.getResultList().isEmpty()) {
			System.out.println("user not found dao");
			return u;
		}
		
		UserEntity ue = (UserEntity) q.getResultList().get(0);
		//System.out.println("checking entity: " + ue.getUsername());
		u = new User();
		u.setUsername(ue.getUsername());
		u.setEmailId(ue.getEmailId());
		u.setName(ue.getName());
		u.setPhoneNumber(ue.getPhoneNumber());
		u.setSecurityQuestion(ue.getSecurityQuestion());
		u.setSecurityAnswer(ue.getSecurityAnswer());
		u.setPassword(ue.getPassword());
		u.setHashedPassword(ue.getHashedPassword());
		return u;
	}
	
	@Override
	public String addUser(User user) throws Exception{
		
		UserEntity newUser = new UserEntity();
		
		newUser.setEmailId(user.getEmailId());
		newUser.setHashedPassword(user.getHashedPassword());
		newUser.setName(user.getName());
		newUser.setPassword(user.getPassword());
		newUser.setPhoneNumber(user.getPhoneNumber());
		newUser.setSecurityAnswer(user.getSecurityAnswer());
		newUser.setSecurityQuestion(user.getSecurityQuestion());
		newUser.setUsername(user.getUsername());
		
		entityManager.persist(newUser);
		
		return newUser.getUsername();
	}
	
	@Override
	public List<String> getUsernameList() throws Exception{
		
		Query q = entityManager.createQuery("SELECT u.username from UserEntity u");
		List<String> usernameList = q.getResultList();
		return usernameList;
	}
}
