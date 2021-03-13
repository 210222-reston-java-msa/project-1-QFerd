package com.revature.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.revature.models.User;
import com.revature.services.UserService;

public class PasswordUtil {
	private static final Random RANDOM = new SecureRandom();
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;

	public static String getSalt(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}

	public static byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}

	public static String generateSecurePassword(String password, String salt) {
		String returnValue = null;
		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

		returnValue = Base64.getEncoder().encodeToString(securePassword);

		return returnValue;
	}

	public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
		boolean returnValue = false;

		// Generate New secure password with the same salt
		String newSecurePassword = generateSecurePassword(providedPassword, salt);

		// Check if two passwords are equal
		returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

		return returnValue;
	}
	
    public static void main(String[] args)
    {
    	
    	//==========================TEST INSERT USER
//        String myPassword = "jdpw";
//        
//        // Generate Salt. The generated value can be stored in DB. 
//        String salt = getSalt(30);
//        
//        // Protect user's password. The generated value can be stored in DB.
//        String mySecurePassword = generateSecurePassword(myPassword, salt);
//        
//        // Print out protected password 
//        System.out.println("My secure password = " + mySecurePassword);
//        System.out.println("Salt value = " + salt);
//        
//        User u = new User("John", "jdpw", "John", "Doe", "jd@gmail.com", 2, mySecurePassword, salt);
//        System.out.println(u);
//        
//        UserService.insert(u);
        
        //===========================TEST VALIDATION
//    	  String providedPassword = "jdpw";
//          
//    	  User u = UserService.findByUsername("John");
//          // Encrypted and Base64 encoded password read from database
//          String securePassword = u.getSecurePassword();
//          
//          // Salt value stored in database 
//          String salt = u.getSalt();
//          
//          boolean passwordMatch = verifyUserPassword(providedPassword, securePassword, salt);
//          
//          if(passwordMatch) 
//          {
//              System.out.println("Provided user password " + providedPassword + " is correct.");
//          } else {
//              System.out.println("Provided password is incorrect");
//          }
          
        //==========================GENERATE SECURE PASSWORDS FOR USERS
        String myPassword = "pcpw";
          
        // Generate Salt. The generated value can be stored in DB. 
        String salt = getSalt(30);
        
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = generateSecurePassword(myPassword, salt);
        
        System.out.println("Password is: " + mySecurePassword);
        System.out.println("Salt: " + salt);
    }
}
