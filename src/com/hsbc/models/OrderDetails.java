package com.hsbc.models;

import java.sql.Time;
import java.util.Date;

public class OrderDetails {

//	--  customerShippingAddress varchar(100), **we can get this by referencing to customer table using customer table
//	--  listOfProducts int[], **multivalued... so new table will be craeted that will have joint primary key of orderId and productId

	private int orderId;
	private Date orderDate;
	private int customerId;
	private double totalOrderValue; // **will be calculated on the go
	private double shippingCost; // **will be calculated on the go
	private String status;
	private Time createdAt;
	private Time updatedAt;

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 * @param orderDate
	 * @param customerId
	 * @param totalOrderValue
	 * @param shippingCost
	 * @param status
	 * @param createdAt
	 * @param updatedAt
	 */
	

	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the totalOrderValue
	 */
	public double getTotalOrderValue() {
		return totalOrderValue;
	}

	/**
	 * @param totalOrderValue the totalOrderValue to set
	 */
	public void setTotalOrderValue(double totalOrderValue) {
		this.totalOrderValue = totalOrderValue;
	}

	/**
	 * @return the shippingCost
	 */
	public double getShippingCost() {
		return shippingCost;
	}

	/**
	 * @param shippingCost the shippingCost to set
	 */
	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createdAt
	 */
	public Time getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Time createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Time getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Time updatedAt) {
		this.updatedAt = updatedAt;
	}


	/**
	 * @param orderId
	 * @param orderDate
	 * @param customerId
	 * @param totalOrderValue
	 * @param shippingCost
	 * @param status
	 */
	public OrderDetails(int orderId, Date orderDate, int customerId, double totalOrderValue, double shippingCost,
			String status) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customerId = customerId;
		this.totalOrderValue = totalOrderValue;
		this.shippingCost = shippingCost;
		this.status = status;
	}

	/**
	 * 
	 */
	public OrderDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
