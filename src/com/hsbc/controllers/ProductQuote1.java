package com.hsbc.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hsbc.models.Customer;
import com.hsbc.service.NewQuoteService;

/**
 * Servlet implementation class ProductQuote1
 */
@WebServlet("/ProductQuote1")
public class ProductQuote1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	NewQuoteService newQuoteService;
    public ProductQuote1() {
       
    	super();
        newQuoteService=new NewQuoteService();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession ss=request.getSession();
		String custId=request.getParameter("customerId");
		Customer customer=newQuoteService.getCustomerData(custId);      //calling service class getCustomerData()
		ss.setAttribute("customer", customer);
		response.sendRedirect("NewQuote.jsp");
	
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
