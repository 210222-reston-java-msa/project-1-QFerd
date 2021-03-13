package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	
	@Override
	public boolean insert(User u) {
		
		PreparedStatement stmt = null;

		try {

			Connection conn = ConnectionUtil.getConnection();
			String sql = "INSERT INTO users (username, first_name, last_name, email, role_id, secure_pw, salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUsername());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRoleId());
			stmt.setString(7, u.getSecurePassword());
			stmt.setString(8, u.getSalt());

			if (stmt.execute()) {
				return false;
			}

		} catch (SQLException ex) {
			log.warn("Unable to insert user", ex);
			return false;
		}

		log.info("User added");
		return true;
	}

	@Override
	public boolean update(User u) {
		
		PreparedStatement stmt = null;

		try {

			Connection conn = ConnectionUtil.getConnection();
			String sql = "";

			sql = "UPDATE users \r\n"
					+ "	SET username = '" + u.getUsername() + "', email = '" + u.getEmail() + 
					"', secure_pw = '" + u.getSecurePassword() + "', salt = '" + u.getSalt() + "'\r\n"
					+ "	WHERE user_id = " + u.getUserId();
	

			stmt = conn.prepareStatement(sql);
			stmt.execute();

		} catch (SQLException ex) {
			log.warn("Unable to update user", ex);
			return false;
		}

		log.info("User updated");
		return true;
	}

	@Override
	public List<User> findAll() {

		List<User> list = new ArrayList<User>();

		try {
			
			Connection conn = ConnectionUtil.getConnection();
			
			String sql = "SELECT * FROM users";
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("user_id");
				String username = rs.getString("username");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id");
				String securePassword = rs.getString("secure_pw");
				String salt = rs.getString("salt");
				
				User u = new User(id, username, firstName, lastName, email, roleId, securePassword, salt);
				list.add(u);
			}

		} catch (SQLException ex) {
			log.warn("Unable to retrieve all users", ex);

		}

		return list;
	}

}
