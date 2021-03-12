package com.revature.repositories;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Expense;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class ExpenseDAOImpl implements ExpenseDAO {

	private static Logger log = Logger.getLogger(ExpenseDAOImpl.class);

	@Override
	public boolean insert(Expense e) {
		
		PreparedStatement stmt = null;

		try {

			Connection conn = ConnectionUtil.getConnection();
			String sql = "INSERT INTO reimbursements (amount, description, author_id, status_id, type_id, receipt)"
						+ "VALUES (?, ?, ?, ?, ?, ?)";

			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, e.getAmount());
			stmt.setString(2, e.getDescription());
			stmt.setInt(3, e.getAuthor().getUserId());
			stmt.setInt(4, e.getStatusId());
			stmt.setInt(5, e.getTypeId());
			stmt.setBytes(6, e.getReceiptBytes());

			if (stmt.execute()) {
				return false;
			}

		} catch (SQLException ex) {
			log.warn("Unable to insert expense", ex);
			return false;
		}

		log.info("Expense added");
		return true;
	}

	@Override
	public boolean update(Expense e) {
		
		PreparedStatement stmt = null;

		try {

			Connection conn = ConnectionUtil.getConnection();
			String sql = "";
			if (e.getResolver().getUserId() != 0) {
				//Description included for debugging purposes
				sql = "UPDATE reimbursements \r\n"
						+ "	SET resolved = '" + e.getResolved() + "', resolver_id = " + e.getResolver().getUserId() + 
						", status_id = " + e.getStatusId() + ", description = '" + e.getDescription() + "'\r\n"
						+ "	WHERE reimb_id = " + e.getReimbId();
			} else {
				sql = "UPDATE reimbursements \r\n"
						+ "	SET resolved = null, resolver_id = null, description = '" + e.getDescription() + "'\r\n"
						+ "	WHERE reimb_id = " + e.getReimbId();
			}

			stmt = conn.prepareStatement(sql);
			stmt.execute();

		} catch (SQLException ex) {
			log.warn("Unable to update expense", ex);
			return false;
		}

		log.info("Expense updated");
		return true;
	}

	@Override
	public List<Expense> findAll() {

			List<Expense> list = new ArrayList<Expense>();

			try {
				
				Connection conn = ConnectionUtil.getConnection();
				
				String sql = "select reimb_id, amount, submitted, resolved, description, \r\n"
						+ "author_id, authors.first_name as author_first_name, authors.last_name as author_last_name, authors.email as author_email, \r\n"
						+ "resolver_id, resolvers.first_name as resolver_first_name, resolvers.last_name as resolver_last_name, \r\n"
						+ "rs.status_id, rs.status_name, rt.type_id, rt.type_name, receipt \r\n"
						+ "from reimbursements\r\n"
						+ "	left join users authors on reimbursements.author_id = authors.user_id\r\n"
						+ "	left join users resolvers on reimbursements.resolver_id = resolvers.user_id \r\n"
						+ "	left join reimb_type rt on reimbursements.type_id = rt.type_id \r\n"
						+ "	left join reimb_status rs on reimbursements.status_id = rs.status_id"
						+ " order by reimb_id asc";
				
				PreparedStatement stmt = conn.prepareStatement(sql);

				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					int reimbId = rs.getInt("reimb_id");
					double amount = rs.getDouble("amount");
					Timestamp submitted = rs.getTimestamp("submitted");
					Timestamp resolved = rs.getTimestamp("resolved");
					String description = rs.getString("description");
					int authorId = rs.getInt("author_id");
					String authorFirstName = rs.getString("author_first_name");
					String authorLastName = rs.getString("author_last_name");
					String authorEmail = rs.getString("author_email");
					int resolverId = rs.getInt("resolver_id");
					String resolverFirstName = rs.getString("resolver_first_name");
					String resolverLastName = rs.getString("resolver_last_name");
					int statusId = rs.getInt("status_id");
					String status = rs.getString("status_name");
					int typeId = rs.getInt("type_id");
					String type = rs.getString("type_name");
					byte[] receiptBytes = rs.getBytes("receipt");
					
					
					Expense e = new Expense(reimbId, amount, submitted, resolved, description, new User(authorId, authorFirstName, authorLastName, authorEmail), new User(resolverId, resolverFirstName, resolverLastName), status, statusId, type, typeId, receiptBytes);
					list.add(e);
				}

			} catch (SQLException ex) {
				log.warn("Unable to retrieve all expenses", ex);

			}

			return list;
	}
	

}
