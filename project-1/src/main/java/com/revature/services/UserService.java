package com.revature.services;

import java.util.List;

import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.repositories.UserDAOImpl;

public class UserService {
	
	public static UserDAO uDao = new UserDAOImpl();
	
	
	public static boolean insert(User u) {
		
		return uDao.insert(u);
	}
	
	public static boolean update(User u) {
		
		return uDao.update(u);
	}
	
	public static List<User> findAll() {
		return uDao.findAll();
	}
	
	public static User findByUsername(String username) {
		List<User> all = uDao.findAll();
		
		for (User u : all) { 
			if (u.getUsername().equals(username)) {
				return u; 
			}
		}
		
		return null;
	}
	
	public static User confirmLogin(String username, String password) {
		
		User u = findByUsername(username);
		
		if (u == null) {
			return null;
		}
		
		if (u.getPassword().equals(password)) {
			return u;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(UserService.findAll());
	}
}
