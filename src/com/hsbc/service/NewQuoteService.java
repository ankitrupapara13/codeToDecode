package com.hsbc.service;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.dto.ProductQuoteDto;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Customer;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;

public class NewQuoteService {


	private OrderProcessingDAO orderProcessingDAOImpl;
	
	

	public NewQuoteService() 
	{
		super();
	
	
		orderProcessingDAOImpl = OrderProcessingDAOImpl.getInstance();

		
	}


	public Customer getCustomerData(String custId) 
	{
		   int customerId=Integer.parseInt(custId);
		   Customer c=null;
	       	try {
				   c=orderProcessingDAOImpl.getCustomerById(customerId);
				   System.out.println(c);
			}
	       	catch (CustomerNotFoundException e) {
				
				e.printStackTrace();
			}
	       	return c;
	 }
	
	public ProductQuoteDto calcCosts(String prodIds)
	{
		int i=0;
		Product product;
		double totalOrderValue=0;
		double shippingCost=0;
		
		
		String prodId[]=prodIds.split("\\,");
	
		int[] productIds=new int[prodId.length];
		for (String str : prodId)
		{
		    productIds[i++] = Integer.parseInt(str);
		    
		}
		
		System.out.println(productIds[0] + " " + productIds[1]);
		
		for(int j=0;j<productIds.length;j++)
		{
			try {
				product= orderProcessingDAOImpl.productFetcher(productIds[j]);
				totalOrderValue+=product.getProductPrice();
			} catch (CompanyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProductNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		shippingCost=calcShippingPrice(totalOrderValue,productIds);
		ProductQuoteDto productQuoteDto=new ProductQuoteDto(shippingCost, totalOrderValue);
		System.out.println("TOV :" +totalOrderValue + " " +shippingCost);
		return productQuoteDto;
		
	}
	
	public double calcShippingPrice(double totalOrderValue,int[] productIds)
	{
		String categoryId;
		Product product=null;
		double shippingCost=0;
		
		if(totalOrderValue>100000)
		{
			shippingCost=0;
		}
		else
		{
		
			for(int j=0;j<productIds.length;j++)
			{
		
				try {
					product= orderProcessingDAOImpl.productFetcher(productIds[j]);
				} catch (CompanyNotFoundException e) {
					
					e.printStackTrace();
				} catch (ProductNotFoundException e) 
				{
					
					e.printStackTrace();
				}
				
				categoryId=product.getProductCategory();
				if(categoryId.equals("LEVEL 1"))
				{
					shippingCost+=0.05*product.getProductPrice();
				}
				else if(categoryId.equals("LEVEL 2"))
				{
					shippingCost+=0.03*product.getProductPrice();
				}
				else if(categoryId.equals("LEVEL 3"))
				{
					shippingCost+=0.02*product.getProductPrice();
				}
			}
		}
	
		return shippingCost;
	
	}
	
	public void saveOrderDetailsToDb(String Date,String customerId,String employeeId,String gstNumber,String address,String city,String phone,String email,String pincode,String prodIds,String tov,String shippindcst)
	{
	
		ArrayList<Product> prodList=new ArrayList<Product>();
		String prodId[]=prodIds.split("\\,");
		int i=0;
		int[] productIds=new int[prodId.length];
		for (String str : prodId)
		{
			    productIds[i++] = Integer.parseInt(str);
			    
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate orderDate = LocalDate.parse(Date, formatter);
		//System.out.println(orderDate.getDayOfWeek());
		if(orderDate.equals(LocalDate.now()) )         //checing if order date is current date
		{
			LocalDate calculatedDate=add(orderDate,3);
			if(calculatedDate.getDayOfWeek()!=DayOfWeek.SATURDAY ||  calculatedDate.getDayOfWeek() != DayOfWeek.SUNDAY)
			{
				 for(int j=0;j<productIds.length;j++)
				    {
				    	try {
							prodList.add(orderProcessingDAOImpl.productFetcher(productIds[j]));
						} catch (CompanyNotFoundException | ProductNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				     	
				    }
				 
				 double totalOrderValue=Double.parseDouble(tov);
				 double shippingCost=Double.parseDouble(shippindcst);
				 int custId=Integer.parseInt(customerId);
				 int empId=Integer.parseInt(employeeId);
				 OrderDetails orderDetails=new OrderDetails(1000,java.sql.Date.valueOf(orderDate),custId,empId,prodList,totalOrderValue,shippingCost,"BLUE DART","PENDING",new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis()));
				 orderProcessingDAOImpl.addOrdertoDB(orderDetails);
			}
		}
		
	}
	public LocalDate add(LocalDate date, int workdays) {
	    if (workdays < 1) {
	        return date;
	    }

	    LocalDate result = date;
	    int addedDays = 0;
	    while (addedDays < workdays) {
	        result = result.plusDays(1);
	       /* if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
	              result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
	            ++addedDays;
	        }  */
	    }

	    return result;
	}
}


