package com.hsbc.models;

import java.sql.Time;

public class Product {
	private int productId;
	private String productName;
	private double productPrice;
	private int productCategoryId;
	//private Company company;
	private Time createdAt;
	private Time updatedAt;
	
	
	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productPrice
	 */
	public double getProductPrice() {
		return productPrice;
	}
	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	/**
	 * @return the productCategoryId
	 */
	public int getProductCategoryId() {
		return productCategoryId;
	}
	/**
	 * @param productCategoryId the productCategoryId to set
	 */
	public void setProductCategoryId(int productCategoryId) {
		this.productCategoryId = productCategoryId;
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
	 * @param productId
	 * @param productName
	 * @param productPrice
	 * @param productCategoryId
	 * @param company
	 * @param createdAt
	 * @param updatedAt
	 */
	public Product(int productId, String productName, double productPrice, int productCategoryId, //Company company,
			Time createdAt, Time updatedAt) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productCategoryId = productCategoryId;
		//this.company = company;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	/**
	 * 
	 */
	public Product() {
		super();
		// TODO Auto-generated constructor stu
	}
	
	
}

