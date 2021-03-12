package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Expense;
import com.revature.models.User;
import com.revature.repositories.ExpenseDAO;
import com.revature.repositories.ExpenseDAOImpl;
import com.revature.util.RequestHelper;

public class ExpenseService {

	private static Logger log = Logger.getLogger(ExpenseService.class);
	
	public static ExpenseDAO eDao = new ExpenseDAOImpl();
	
	public static boolean insert(Expense e) {
		
		return eDao.insert(e);
	}
	
	public static boolean update(Expense e) {
		
		return eDao.update(e);
	}
	
	public static List<Expense> findAll() {
		List<Expense> expenseList = eDao.findAll();
		log.info("ExpenseService: Expense list recieved: \n" + expenseList);
		
		return expenseList;
	}
	
	public static void main(String[] args) {
		List<Expense> expenseList = findAll();

//		Expense expense = null;
//		for (Expense e : expenseList) {
//			if (e.getReimbId() == 1) {
//				expense = e;
//				expense.setDescription("Hotel");
//			}
//		}
//		update(expense);

	}
}