package com.hsbc.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.dto.ProductFileDTO;
import com.hsbc.service.ProductService;

/**
 * Servlet implementation class ProductController
 * 
 * This receives JSON or XML file containing products information to
 * be stored and calls service layer for further processing
 * 
 */
@WebServlet("/product")
@MultipartConfig
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ProductService productService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
        productService = new ProductService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductFileDTO productFileDTO = productService.addProduct(request.getPart("file"));
		System.out.println(productFileDTO.getSuccessCount() + "|||" + productFileDTO.getFailedCount());
		request.setAttribute("productFileResponse", productFileDTO);
//		doGet(request, response);
		
	}

}
