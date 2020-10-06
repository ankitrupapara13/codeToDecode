package com.hsbc.service;


import com.hsbc.dto.ProductQuoteDto;
import com.hsbc.models.Customer;
import com.hsbc.models.Product;

public class NewQuoteService {
	
	private double totalOrderValue;
	private double shippingCost;
	Customer c;
	Product product;
	ProductDao productDao;
	CustomerDao customerDao;
	ProductQuoteDto productQuoteDto;
	

	public NewQuoteService() 
	{
		super();
		c=new Customer();
		customerDao=new new CustomerDaoImpl(); 
		productDao=new ProductDaoImpl();
		
	}


	public Customer getCustomerData(String customerId)
	{
		int custId=Integer.parseInt(customerId);
	       	return(customerDao.getCustomerById(customerId));
	       	
	 }
	
	public ProductQuoteDto calcCosts(String prodIds)
	{
		int i=0;
		
		String[] prodId=prodIds.split("\\,");
		Integer[] productIds=new Integer[prodId.length];
		for (String str : prodId)
		{
		    productIds[i++] = Integer.parseInt(str);
		    
		}
		
		
		for(int j=0;j<productIds.length;j++)
		{
			product= productDao.getProductById(productIds[j]));
			totalOrderValue+=product.getProductPrice();
			
			
		}
		
		shippingCost=calcShippingCost(totalOrderValue,productIds);
		productQuoteDto=new ProductQuoteDto(shippingCost, totalOrderValue);
		
		return productQuoteDto;
		
	}
	
	public double calcShippingPrice(double totalOrderValue,int[] productIds)
	{
		int categoryId;
		
		if(totalOrderValue>100000)
		{
			shippingCost=0;
		}
		else
		{
		
			for(int j=0;j<productIds.length;j++)
			{
		
				product= productDao.getProductById(productIds[j]));
				categoryId=productDao.getProductCategoryId();
				if(categoryId==1)
				{
					shippingCost+=0.05*product.getProductPrice();
				}
				else if(categoryId==2)
				{
					shippingCost+=0.03*product.getProductPrice();
				}
				else if(categoryId==3)
				{
					shippingCost+=0.02*product.getProductPrice();
				}
			}
		}
		
		return shippingCost;
	
	}

}
