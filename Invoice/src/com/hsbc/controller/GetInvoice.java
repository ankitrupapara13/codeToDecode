package com.hsbc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hsbc.dao.EmployeeDAOImpl;
import com.hsbc.models.Invoice;
import com.hsbc.models.Product;


@WebServlet("/getInvoice")
public class GetInvoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public GetInvoice() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		ArrayList<Product>productList=new ArrayList<>();
		/*productList.add(new Product(2,"Top", 300,1, null, null));
		productList.add(new Product(3,"shirt", 300,2, null, null));
		productList.add(new Product(4,"e", 300,3, null, null));
		productList.add(new Product(5,"op", 300,1, null, null));
		productList.add(new Product(1,"eop", 300,2, null, null));
		*/
			
			 try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				Connection con = DriverManager.getConnection ("jdbc:derby:invoiceDb;create = true");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 
			//int orderId=request.getParameter("name of attribute");
			//ArrayList<Product>productList=new ArrayList<>();
			//productList=new OrderDetailService().getProductList();
			int orderID=Integer.parseInt(request.getParameter("orderId"));
			
			EmployeeDAOImpl employee = new EmployeeDAOImpl();
			//Invoice inv = employee.getInvoiceByOrderId(orderID);
			Invoice inv=new Invoice(1,new Date(System.currentTimeMillis()),2,3,0, 200,
					2323, true);
			request.setAttribute("inv",inv);
			request.setAttribute("productList", productList);
			/*
			int invoiceId = inv.getInvoiceId();
			Date invoiceDate = inv.getInvoiceDate();
			orderId = inv.getOrderId();
			int customerId = inv.getCustomerId();
			int gstTypeId=inv.getGstTypeId();
			double gstAmount=inv.getGstAmount();
			double totalInvoiceAmount=inv.getTotalInvoiceAmount();
			boolean status=inv.isStatus();
			Time createdAt=inv.getCreatedAt();
			Time updatedAt=inv.getUpdatedAt();
			
			HttpSession session=request.getSession();
			session.setAttribute("invoiceId" , invoiceId );
			session.setAttribute("invoiceDate" , invoiceDate  );
			session.setAttribute("orderId" , orderId );
			session.setAttribute("customerId" , customerId );
			session.setAttribute("gstTypeId" , gstTypeId );
			session.setAttribute("gstAmount" , gstAmount);
			session.setAttribute("totalInvoiceAmount" , totalInvoiceAmount );
			session.setAttribute("status" ,status);
			session.setAttribute("createdAt" ,createdAt);
			session.setAttribute("updatedAt" , updatedAt );
			*/
			request.getRequestDispatcher("Invoice.jsp").forward(request, response);
			//response.sendRedirect("Invoice.jsp");
		
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}