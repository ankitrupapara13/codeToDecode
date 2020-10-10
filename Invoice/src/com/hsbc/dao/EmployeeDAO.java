package com.hsbc.dao;
import java.util.ArrayList;
import java.util.List;

//import com.hsbc.models.Customer;
//import com.hsbc.models.Employee;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;

public interface EmployeeDAO {

	/*Employee getEmployeeById(int employeeId);

	Customer getCustomerById(int customerId); */

	ArrayList<OrderDetails> getOrdersOfEmployee(int employeeId);

	OrderDetails approveOrder(int orderId);

	Invoice getInvoiceByOrderId(int orderId);
	
//	addtransactiontoda

	void addProductsToDB(Product products[]); // XML -- Pending
	void addOrdertoDB(OrderDetails orderDetails); // XML -- Pending
	OrderDetails completeOrder(int orderId); // CRON Job
	OrderDetails expiryOrder(int orderId); // CRON Job
}
