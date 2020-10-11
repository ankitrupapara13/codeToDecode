package com.hsbc.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.Security.SessionManager;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.SystemSecurityException;
import com.hsbc.models.Employee;
import com.hsbc.service.NewQuoteService;

@WebServlet("/employeeLogin")
public class EmployeeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewQuoteService newQuoteService; 
    public EmployeeLogin() {
        super();
        newQuoteService = new NewQuoteService();
    }
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String employeeId = request.getParameter("employeeId");
		String password = request.getParameter("password");
		try {
			System.out.println(employeeId);
			Employee c = null;
			if(( c = newQuoteService.employeelogin(Integer.parseInt(employeeId), password))!=null) {
				SessionManager.createSession(request, response, c.getEmployeeId());
				request.getRequestDispatcher("orderEmp.html").forward(request, response);
			}else {
				request.setAttribute("loginMessage", "Username Or Password is incorrect");
				request.getRequestDispatcher("FuryEmployee.jsp").forward(request, response);
			}
		}catch(SystemSecurityException sse) {
			request.setAttribute("errorMessage", "Server Error Try After Sometime");
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}catch(EmployeeNotFoundException e) {
			request.setAttribute("loginMessage", "Username Or Password is incorrect");
			request.getRequestDispatcher("FuryEmployee.jsp").forward(request, response);
		}
	}

}
