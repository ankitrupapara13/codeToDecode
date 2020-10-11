package com.hsbc.controllers;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hsbc.dto.ProductQuoteDto;
import com.hsbc.service.NewQuoteService;

/**
 * Servlet implementation class ProductQuote2
 */
@WebServlet("/ProductQuote2")
public class ProductQuote2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private NewQuoteService newQuoteService;
	private Gson gson;
	
    public ProductQuote2() {
        
    	super();
        newQuoteService=new NewQuoteService();
        gson = new Gson();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String param=request.getParameter("productIds");
		ProductQuoteDto productQuoteDto=newQuoteService.calcCosts(param);
		String responseStr = gson.toJson(productQuoteDto);
		response.setContentType("application/json");
		response.getWriter().print(responseStr);		
	 }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
