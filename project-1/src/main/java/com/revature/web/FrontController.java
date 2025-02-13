package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.util.RequestHelper;

//===============MAIN============================
@MultipartConfig
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontController() {
        super();
 
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("in FrontController");

		final String URI = request.getRequestURI().replace("/project-1/", "");
		
		switch(URI) {
		case "login":
			RequestHelper.processLogin(request, response);
			break;
		case "logout":
			RequestHelper.processLogout(request, response);
			break;
		//For employee:
		case "new-expense":
			RequestHelper.newExpense(request, response);
			break;
		//For manager
		case "expense-requests":
			RequestHelper.retrieveExpenseRequests(request, response);
			break;
		//DELETE:
//		case "expense-request-detail":
//			RequestHelper.viewRequest(request, response);
//			break;
		case "manage-request":
			RequestHelper.manageRequest(request, response);
			break;
		case "employee-info":
			RequestHelper.updateEmployeeInfo(request, response);
			break;
		
//		case "employees":
//			RequestHelper.processEmployees(request, response);
//			break;
//		case "error":
//			RequestHelper.processError(request, response);
		} 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
