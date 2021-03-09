package com.revature.repositories;

import java.util.List;

import com.revature.models.Expense;

public interface ExpenseDAO {
	
	public boolean insert(Expense e); // returns true if successfully inserted	
	public boolean update(Expense e);
	
	public List<Expense> findAll(); 
	
}
