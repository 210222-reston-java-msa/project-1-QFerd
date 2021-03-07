package com.revature.models;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ExpenseTemplate {
	int reimbId;
	double amount;
	Timestamp submitted;
	Timestamp resolved;
	String description;
	String authorUsername;
	int authorId;
	String resolverUsername;
	int resolverId;
	int statusId;
	String type;
	int typeId;
	
	public ExpenseTemplate() {
		super();
	}
	
	
	
	public ExpenseTemplate(double amount, String description, String authorUsername, int statusId,
			String type) {
		super();
		
		this.amount = amount;
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();

		// 2) get a java.util.Date from the calendar instance.
//		    this date will represent the current instant, or "now".
		Date date = calendar.getTime();

		// 3) a java current time (now) instance
		Timestamp currentTimestamp = new Timestamp(date.getTime());
		
		this.submitted = currentTimestamp;
		this.description = description;
		this.authorUsername = authorUsername;
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
	public String getAuthorUsername() {
		return authorUsername;
	}
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getResolverUsername() {
		return resolverUsername;
	}
	public void setResolverUsername(String resolverUsername) {
		this.resolverUsername = resolverUsername;
	}
	public int getResolverId() {
		return resolverId;
	}
	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorId;
		result = prime * result + ((authorUsername == null) ? 0 : authorUsername.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + reimbId;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolverId;
		result = prime * result + ((resolverUsername == null) ? 0 : resolverUsername.hashCode());
		result = prime * result + statusId;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + typeId;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpenseTemplate other = (ExpenseTemplate) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorId != other.authorId)
			return false;
		if (authorUsername == null) {
			if (other.authorUsername != null)
				return false;
		} else if (!authorUsername.equals(other.authorUsername))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (resolverUsername == null) {
			if (other.resolverUsername != null)
				return false;
		} else if (!resolverUsername.equals(other.resolverUsername))
			return false;
		if (statusId != other.statusId)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "ExpenseTemplate [reimbId=" + reimbId + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", authorUsername=" + authorUsername + ", authorId="
				+ authorId + ", resolverUsername=" + resolverUsername + ", resolverId=" + resolverId + ", statusId="
				+ statusId + ", type=" + type + ", typeId=" + typeId + "]";
	}
	
	
	
	
}
