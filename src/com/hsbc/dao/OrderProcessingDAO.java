package com.hsbc.dao;

import java.util.List;

import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;

public interface OrderProcessingDAO {
	
	Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;

	Customer getCustomerById(int customerId) throws CustomerNotFoundException;

	List<Product> getProductByProductId(int[] productIds) throws ProductNotFoundException, CompanyNotFoundException;

	List<OrderDetails> getOrdersOfEmployee(int employeeId) throws OrderNotFoundForEmployee, ProductNotFoundException;

	Invoice getInvoiceByOrderId(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException;

	void addProductsToDB(Product products[]);

	OrderDetails addOrdertoDB(OrderDetails orderDetails);

	OrderDetails approveOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException;

	OrderDetails completeOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException; // CRON Job

	OrderDetails expiryOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException; // CRON Job

	OrderDetails orderFetcher(int orderId, OrderProcessingDAOImpl emImpl)
			throws OrderNotFoundForEmployee, ProductNotFoundException; // Order Fetcher is working behind the scenes to
																	// fetch OrderDetails object for approveOrder,
																	// completeOrder, expiryOrder

}