package com.revature.services;

import java.util.List;

import com.revature.models.Expense;
import com.revature.models.User;
import com.revature.repositories.ExpenseDAO;
import com.revature.repositories.ExpenseDAOImpl;

public class ExpenseService {
	
	public static ExpenseDAO eDao = new ExpenseDAOImpl();
	
	public static boolean insert(Expense e) {
		
		return eDao.insert(e);
	}
	
	public static boolean update(Expense e) {
		
		return eDao.update(e);
	}
	
	public static List<Expense> findAll() {
		return eDao.findAll();
	}
	
	public static void main(String[] args) {
		System.out.println(findAll());
		
	}
}
