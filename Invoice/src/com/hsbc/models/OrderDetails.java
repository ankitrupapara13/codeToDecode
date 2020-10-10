package com.hsbc.models;



import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class OrderDetails {
	private int orderId;
	private Date orderDate;
	private int customerId;
	private int employeeId;
	private ArrayList<Product> products;
	private double totalOrderValue; // **will be calculated on the go
	private double shippingCost; // **will be calculated on the go
	private String status;
	private Time createdAt;
	private Time updatedAt;

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @return the products
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

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
	 * @param employeeId
	 * @param products
	 * @param totalOrderValue
	 * @param shippingCost
	 * @param status
	 * @param createdAt
	 * @param updatedAt
	 */
	public OrderDetails(int orderId, Date orderDate, int customerId, int employeeId, ArrayList<Product> products,
			double totalOrderValue, double shippingCost, String status, Time createdAt, Time updatedAt) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.products = products;
		this.totalOrderValue = totalOrderValue;
		this.shippingCost = shippingCost;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	/**
	 * @param orderId
	 * @param orderDate
	 * @param customerId
	 * @param employeeId
	 * @param products
	 * @param totalOrderValue
	 * @param shippingCost
	 * @param status
	 */
	public OrderDetails(int orderId, Date orderDate, int customerId, int employeeId, ArrayList<Product> products,
			double totalOrderValue, double shippingCost, String status) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.products = products;
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


