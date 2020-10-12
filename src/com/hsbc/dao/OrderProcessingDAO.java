package com.hsbc.dao;

import java.util.List;

import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.DatabaseModificationFailedException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.InvoiceNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;
import com.hsbc.models.SessionEntity;

public interface OrderProcessingDAO {

	Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;

	Customer getCustomerById(int customerId) throws CustomerNotFoundException;

	List<Product> getProductByProductIds(int[] productIds) throws ProductNotFoundException, CompanyNotFoundException;

	List<OrderDetails> getOrdersOfEmployee(int employeeId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException;

	List<OrderDetails> getOrdersOfCustomer(int customerId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException;

	Invoice addInvoiceToDB(Invoice invoice) throws DatabaseModificationFailedException;

	Invoice getInvoiceByOrderId(int orderId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, InvoiceNotFoundException, CompanyNotFoundException;

	void addProductsToDB(Product products[]) throws DatabaseModificationFailedException;

	List<Product> getProducts() throws ProductNotFoundException, CompanyNotFoundException;

	OrderDetails addOrdertoDB(OrderDetails orderDetails) throws DatabaseModificationFailedException;

	OrderDetails approveOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException;

	List<OrderDetails> completeOrder() throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException; // CRON Job

	List<OrderDetails> expiryOrder() throws OrderNotFoundForEmployee, ProductNotFoundException; // CRON Job

	Customer updateCustomerPassword(Customer customer);

	Employee updateEmployeePassword(Employee employee);

	SessionEntity tokenFetcher(int personId);
	
	SessionEntity updateToken(SessionEntity sessionEntity);
	
	OrderDetails orderFetcher(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException; // Order Fetcher
																										// is working
																										// behind the
																										// scenes to
																										// fetch
																										// OrderDetails
																										// object for
																										// approveOrder,
																										// completeOrder,
																										// expiryOrder

	public Product productFetcher(int productId) throws CompanyNotFoundException, ProductNotFoundException;

}