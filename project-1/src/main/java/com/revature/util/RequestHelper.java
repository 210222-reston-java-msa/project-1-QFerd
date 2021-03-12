package com.revature.util;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
			session.setAttribute("currentUser", u);
			
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
//		Expense expenseTemp = om.readValue(body, Expense.class);
//		
//		HttpSession session = req.getSession(false);
//		User u = (User) session.getAttribute("currentUser");
//		
//		Expense expense = new Expense(expenseTemp.getAmount(), expenseTemp.getDescription(), u, 1, expenseTemp.getType());
//		log.info("New expense mapped: \n" + expense);
//		
//		ExpenseService.insert(expense);
		
		
		// getParameter method not working
//		HttpSession session = req.getSession(false);
//		String username = (String) session.getAttribute("username");
//
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
		

		//Retrieve form data
		String type = req.getParameter("type");
		double amount = Double.parseDouble(req.getParameter("amount"));
		String description = req.getParameter("description");
		InputStream receiptStream = null;
		try {
			Part receiptPart = req.getPart("receipt");
			receiptStream = receiptPart.getInputStream(); 
		} catch (IOException | ServletException e1) {
			e1.printStackTrace();
		}
		System.out.println(type);
		System.out.println(amount);
		System.out.println(description);
		System.out.println(receiptStream);
		
		byte[] receiptBytes = receiptStream.readAllBytes();
		
		HttpSession session = req.getSession(false);
		User u = (User) session.getAttribute("currentUser");
		Expense expense = new Expense(amount, description, u, 1, type, receiptBytes);
		log.info("New expense mapped: \n" + expense);
		log.info("byte[] length: " + receiptBytes.length);
		ExpenseService.insert(expense);
	}
	
	public static void retrieveExpenseRequests(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		
		//If manager is logged in, no need to re-retrieve expenses from database after activity
		//Just grab from session
		if (currentUser.getRoleId() == 1 && session.getAttribute("expenseList") != null) {
			List<Expense> expenseList = (List<Expense>) session.getAttribute("expenseList");
			
			log.info("Expense list retrieved from session: \n" + expenseList);
			
			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");
			
			pw.println(om.writeValueAsString(expenseList));
		} else {

			List<Expense> expenseList = ExpenseService.findAll();
			
			session.setAttribute("expenseList", expenseList);
			
			log.info("RequestHelper: Expense list retrieved from data base and stored in new session: \n" + expenseList);
			
			PrintWriter pw = res.getWriter();
			res.setContentType("application/json");
			
			pw.println(om.writeValueAsString(expenseList));

		}
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
	//1. Map expenseTemp
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		
		String line = reader.readLine();
		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}
		
		String body = s.toString();
		
		Expense expenseTemp = om.readValue(body, Expense.class);
		
	//2. Grab expenseList from session and update in storage and in database
		HttpSession session = req.getSession();
		List<Expense> expenseList = (List<Expense>) session.getAttribute("expenseList");
		
		int i = 0;
		for (i = 0; i < expenseList.size(); i++) {
			if (expenseList.get(i).getReimbId() == expenseTemp.getReimbId()) {
				
				expenseList.get(i).setResolved(new Timestamp(System.currentTimeMillis()));
				expenseList.get(i).getResolver().setUserId(expenseTemp.getResolver().getUserId());
				expenseList.get(i).setStatusId(expenseTemp.getStatusId());
				if (expenseTemp.getStatusId() == 2) {
					expenseList.get(i).setStatus("Approved");
				} else if (expenseTemp.getStatusId() == 3) {
					expenseList.get(i).setStatus("Denied");
				}
				
				User currentUser = (User) session.getAttribute("currentUser");
				expenseList.get(i).getResolver().setFirstName(currentUser.getFirstName());
				expenseList.get(i).getResolver().setLastName(currentUser.getLastName());
				
				session.setAttribute("expenseList", expenseList);
				log.info("RequestHelper: manageRequest() called, expense in storage updated: \n" + expenseList.get(i));
				
				ExpenseService.update(expenseList.get(i));
				log.info("RequestHelper: Update request recieved, update() in service layer called.");
				break;
			}
		}
		
		//====================SEND EMAIL

		
				String recipient = expenseList.get(i).getAuthor().getEmail();
				String change = null;
				if (expenseList.get(i).getStatusId() == 2) {
					change = "approved";
				} else if (expenseList.get(i).getStatusId() == 3) {
					change = "denied";
				}
				
				System.out.println("Preparing to send email");
		        Properties properties = new Properties();

		        //Enable authentication
		        properties.put("mail.smtp.auth", "true");
		        //Set TLS encryption enabled
		        properties.put("mail.smtp.starttls.enable", "true");
		        //Set SMTP host
		        properties.put("mail.smtp.host", "smtp.gmail.com");
		        //Set smtp port
		        properties.put("mail.smtp.port", "587");

		        //Your gmail address
		        String myAccountEmail = "fakecompanyhr@gmail.com";
		        //Your gmail password
		        String password = "project1demo";

		        //Create a session with account credentials
		        Session emailSession = Session.getInstance(properties, new Authenticator() {
		            @Override
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(myAccountEmail, password);
		            }
		        });

		        //Prepare email message
		        Message message = null;
		        try {
		            message = new MimeMessage(emailSession);
		            message.setFrom(new InternetAddress(myAccountEmail));
		            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		            message.setSubject("Reimbursement request updated");
		            String htmlCode = "<h1> A manager has " + change + " your reimbursement request.</h1> <br/> "
		            		+ "<h2><b>Please login to your account to view the changes. </b></h2>";
		            message.setContent(htmlCode, "text/html");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		        

		        //Send mail
		        try {
					Transport.send(message);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println("Message sent successfully");
		    }
		    
	

}