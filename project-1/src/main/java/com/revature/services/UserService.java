package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.repositories.ExpenseDAOImpl;
import com.revature.repositories.UserDAO;
import com.revature.repositories.UserDAOImpl;
import com.revature.util.PasswordUtil;

public class UserService {
	private static Logger log = Logger.getLogger(UserService.class);
	
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
		//==============OLD METHOD
		
//		User u = findByUsername(username);
//		
//		if (u == null) {
//			return null;
//		}
//		
//		if (u.getPassword().equals(password)) {
//			return u;
//		} else {
//			return null;
//		}
		
		//=============WITH ENCRYPTION
  	  	User u = UserService.findByUsername(username);
		
		if (u == null) {
			return null;
		}
      
        // Encrypted and Base64 encoded password read from database
  	  	
        String securePassword = u.getSecurePassword();
        
        // Salt value stored in database 
        String salt = u.getSalt();
        
        boolean passwordMatch = PasswordUtil.verifyUserPassword(password, securePassword, salt);
        
        if(passwordMatch) {
            log.info("Provided user password " + password + " is correct.");
        	return u;
        } else {        	
            log.warn("Provided password is incorrect");
            return null;
        }
	}
	
//	public static void main(String[] args) {
		//=============TEST FIND ALL
//		System.out.println(UserService.findAll());
		
		//=============TEST CONFIRM LOGIN
//		System.out.println(confirmLogin("Ellen", "edpw"));
		
		//=============TEST UPDATE
//        String myPassword = "pcpw";
//        
//        // Generate Salt. The generated value can be stored in DB. 
//        String salt = PasswordUtil.getSalt(30);
//        
//        // Protect user's password. The generated value can be stored in DB.
//        String mySecurePassword = PasswordUtil.generateSecurePassword(myPassword, salt);
//        
//        System.out.println("Password is: " + mySecurePassword);
//        System.out.println("Salt: " + salt);
//        
//		User u = findByUsername("pcook");
//		u.setSecurePassword(mySecurePassword);
//		u.setSalt(salt);
//		update(u);
//	}
}
