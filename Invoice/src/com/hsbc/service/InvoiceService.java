
package com.hsbc.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import com.hsbc.dao.EmployeeDAOImpl;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;
public class InvoiceService
{
	static EmployeeDAOImpl empDAO=new EmployeeDAOImpl();
	public Invoice generateInvoice(int orderId)
	{
		//Calling DAO to get order details by orderID
		OrderDetails obj3=null;
		Invoice obj1=new Invoice();
		obj1.setInvoiceId(generateInvoiceId());
		obj1.setInvoiceDate(new Date(System.currentTimeMillis()));
		obj1.setOrderId(orderId);
		obj1.setCustomerId(obj3.getCustomerId());
		obj1.setGstTypeId(0);
		obj1.setGstAmount(generateGST(obj3.getTotalOrderValue()));
		double totalInvoiceValue=generateGST(obj3.getTotalOrderValue())+getShippingCost(obj3.getProducts(),obj3.getTotalOrderValue());
		obj1.setTotalInvoiceAmount(totalInvoiceValue);
		obj1.setStatus(true);
		//Invoice obj=empDAO.getInvoiceByOrderId(orderId);
		return obj1;
	}
	public int generateInvoiceId()
	{
		//return create sequence user_seq as int start with 100;
		//next value for user_seq
		return new Random().nextInt(10000);
	}
	public double generateGST(double totalInvoiceAmount)
	{
		return (double) (0.10*totalInvoiceAmount);
	}
	public double getShippingCost(ArrayList<Product> products, double totalOrderValue )
	{
		double shippingCharges=0;
		int i=0;
		
		if(totalOrderValue>1000000) {
			shippingCharges=0;
		}
		else
		{
		
		for(Product prod:products)
		{
			i=prod.getProductCategoryId();
			switch(i)
			{
			case 1:
				shippingCharges+=0.05*prod.getProductPrice();
				break;
			case 2:
				shippingCharges+=0.03*prod.getProductPrice();
				break;
			case 3:
				shippingCharges+=0.02*prod.getProductPrice();
				break;
			}
		}
		}
		return shippingCharges;
		
	}
}































