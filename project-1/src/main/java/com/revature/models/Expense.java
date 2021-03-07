package com.revature.models;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Expense {
	int reimbId;
	double amount;
	Timestamp submitted;
	Timestamp resolved;
	String description;
	User author;
	User resolver;
	String status;
	int statusId;
	String type;
	int typeId;
	
	public Expense() {
		super();
	}
		
	public Expense(double amount, String description, String authorUsername, int statusId,
			String type) {
		super();
		
		this.amount = amount;
		
		//Timestamp will be created by database:
//		// 1) create a java calendar instance
//		Calendar calendar = Calendar.getInstance();
//
//		// 2) get a java.util.Date from the calendar instance.
////		    this date will represent the current instant, or "now".
//		Date date = calendar.getTime();
//
//		// 3) a java current time (now) instance
//		Timestamp currentTimestamp = new Timestamp(date.getTime());
//		
//		this.submitted = currentTimestamp;
		
		this.description = description;
		this.author = new User(authorUsername);
		this.statusId = statusId;
		this.type = type;
		if (type.equals("Travel")) {
			this.typeId = 1;
		} else if (type.equals("Training")) {
			this.typeId = 2;
		} else if (type.equals("Supplies")) {
			this.typeId = 3;
		}
	}
	
	

	public Expense(int reimbId, double amount, Timestamp submitted, Timestamp resolved, String description, User author,
			User resolver, String status, int statusId, String type, int typeId) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.author.setRoleId(2);
		this.resolver = resolver;
		this.resolver.setRoleId(1);
		this.status = status;
		this.statusId = statusId;
		this.type = type;
		this.typeId = typeId;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "Expense [reimbId=" + reimbId + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", author=" + author + ", resolver=" + resolver
				+ ", status=" + status + ", statusId=" + statusId + ", type=" + type + ", typeId=" + typeId + "]";
	}
	
	
	
	
}
