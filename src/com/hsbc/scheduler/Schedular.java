package com.hsbc.scheduler;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hsbc.dao.EmployeeDAOImpl;
import com.hsbc.dao.OrderProcessingDAOImpl;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.OrderDetails;

public class Schedular implements ServletContextListener {
	
	ScheduledExecutorService scheduler;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdownNow();

	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		LocalTime midnight = LocalTime.of(23, 59, 59);
		LocalTime now = LocalTime.now();
		long initialDelay = Duration.between(midnight, now).getSeconds();
		long PERIOD = 86400000L;
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(()->{
			
			//call the function to be executed here
			System.out.println("CONSTANT---" + midnight + "----" + initialDelay + "-----" + now);
			System.out.println("Repeat task: " + LocalDateTime.now());
		},0,10000,TimeUnit.MILLISECONDS );
		
		//initial period, delay between every execution
		
	}
	public void expire() throws OrderNotFoundForEmployee, ProductNotFoundException
	{
		OrderProcessingDAOImpl ob=new OrderProcessingDAOImpl();
		ob.expiryOrder();
	}
	public void complete() throws OrderNotFoundForEmployee, ProductNotFoundException
	{
		OrderProcessingDAOImpl ob=new OrderProcessingDAOImpl();
		ob.completeOrder();
	}
	
	

}
