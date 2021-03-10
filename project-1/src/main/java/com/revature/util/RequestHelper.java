package com.revature.util;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Expense;
import com.revature.models.LoginTemplate;
import com.revature.models.User;
import com.revature.services.ExpenseService;
import com.revature.services.UserService;


public class RequestHelper {
	
	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static ObjectMapper om = new ObjectMapper();
	

	public static void processLogin(HttpServletRequest req, HttpServletResponse res) throws IOException { 
		
		// We want to turn whatever we receive as the request into a string to process
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		
		// logic to transfer everything from our reader to our string builder
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		
		String body = s.toString();
		log.info(body);
		
		
		// I'm going to build a model called LoginTemplate which holds a username and passwrod
		LoginTemplate loginAttempt = om.readValue(body, LoginTemplate.class); // from JSON --> Java Object

		
		String username = loginAttempt.getUsername();
		String password = loginAttempt.getPassword();	
		
		log.info("User attempted to login with username: " + username);
		User u = UserService.confirmLogin(username, password);
		
		if (u != null) {
			
			int roleId = loginAttempt.getRoleId();
			// get the current session OR create one if it doesn't exist
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			
			// Attaching the print writer to our response
			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");
			
			// this is converting our Java Object (with property firstName!) 
			// to JSON format....that means we can grab the firstName property
			// after we parse it. (We parse it in JavaScript)
			pw.println(om.writeValueAsString(u));

			
			log.info(username + " has successfully logged in");	
			
		} else {
			res.setStatus(204); // this means that we still have a connection, but no user is found
		}
		
		
	}
	
	public static void processLogout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession(false); // I'm capturing the session, but if there ISN'T one, I don't create a new one.
		
		
		log.info("Processing logout");
		
		
		if (session != null) {
			
			String username = (String) session.getAttribute("username");
			log.info( username + " has logged out");
							
			session.invalidate();
		}
		
		res.setStatus(200);
	
	}
	
	public static void newExpense(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// BufferedReader method:
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		
		String body = s.toString();
		
		Expense expenseTemp = om.readValue(body, Expense.class);
		
		HttpSession session = req.getSession(false);
		String username = (String) session.getAttribute("username");
		
		Expense expense = new Expense(expenseTemp.getAmount(), expenseTemp.getDescription(), username, 1, expenseTemp.getType());
		log.info(expense);
		
		// getParameter method not working
//		HttpSession session = req.getSession(false);
//		String username = (String) session.getAttribute("username");

//		String type = req.getParameter("type");
//		double amount = Double.parseDouble(req.getParameter("amount"));
//		String description = req.getParameter("description");
//	
//		ExpenseTemplate expense = new ExpenseTemplate(amount, description, "t", 1, "Travel");
//		log.info(expense);
//
//		System.out.println(expense);
//		PrintWriter pw = res.getWriter();
//		res.setContentType("application/json");
//		
//		// this is converting our Java Object (with property firstName!) 
//		// to JSON format....that means we can grab the firstName property
//		// after we parse it. (We parse it in JavaScript)
//		pw.println(om.writeValueAsString(expense));
		
	}
	
	public static void retrieveExpenseRequests(HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<Expense> expenseList = ExpenseService.findAll();
		
		PrintWriter pw = res.getWriter();
		res.setContentType("application/json");
		
		pw.println(om.writeValueAsString(expenseList));
	}
	//DELETE:
//	public static void viewRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, IOException {
//
//		BufferedReader reader = req.getReader();
//		StringBuilder s = new StringBuilder();
//		
//		String line = reader.readLine();
//		while (line != null) {
//			s.append(line);
//			line = reader.readLine();
//		}
//		
//		String body = s.toString();
//		
//		PrintWriter pw = res.getWriter();
//		res.setContentType("application.json");
//		pw.println(s);
//	}
	
	public static void manageRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, IOException {
	
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		
		String body = s.toString();
		
		Expense expense = om.readValue(body, Expense.class);
		
		log.info("manageRequest() called, expense mapped: " + expense);
		
		ExpenseService.update(expense);
		
		log.info("Update request recieved, service layer called.");
	
	}

}