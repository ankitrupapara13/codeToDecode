package com.hsbc.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	
    public ProductQuote2() {
        
    	super();
        newQuoteService=new NewQuoteService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String param=request.getParameter("productIds");
		ProductQuoteDto productQuoteDto=newQuoteService.calcCosts(param);
		request.setAttribute("totalOrderValue", productQuoteDto.getTotalOrderValue());
		request.setAttribute("shippingCost",productQuoteDto.getShippingCost());
		String destination = "/WEB-INF/NewQuote.jsp";
		RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
		rd.forward(request, response);
		
		
	 }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
