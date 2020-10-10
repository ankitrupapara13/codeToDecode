package com.hsbc.dao;
import com.hsbc.models.*;
import java.util.List;
import com.hsbc.exceptions.*;
public interface OrderProcessingDAO {
	
	List<OrderDetails> completeOrder() throws OrderNotFoundForEmployee, ProductNotFoundException; // CRON Job

	List<OrderDetails> expiryOrder() throws OrderNotFoundForEmployee, ProductNotFoundException;

}
