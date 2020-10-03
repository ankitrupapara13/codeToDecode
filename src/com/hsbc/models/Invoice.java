package com.hsbc.models;

import java.sql.Time;
import java.util.Date;

public class Invoice {

	private int invoiceId;
	private Date invoiceDate;
	private int orderId;
	private int customerId;
	private int gstTypeId; // 0 -> inter state, 1 -> same state
	private double gstAmount;
	private double totalInvoiceAmount;
	private boolean status; // 0 -> fail, 1 -> done
	private Time createdAt;
	private Time updatedAt;
	/**
	 * @return the invoiceId
	 */
	public int getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	 * @return the gstTypeId
	 */
	public int getGstTypeId() {
		return gstTypeId;
	}
	/**
	 * @param gstTypeId the gstTypeId to set
	 */
	public void setGstTypeId(int gstTypeId) {
		this.gstTypeId = gstTypeId;
	}
	/**
	 * @return the gstAmount
	 */
	public double getGstAmount() {
		return gstAmount;
	}
	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(float gstAmount) {
		this.gstAmount = gstAmount;
	}
	/**
	 * @return the totalInvoiceAmount
	 */
	public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}
	/**
	 * @param totalInvoiceAmount the totalInvoiceAmount to set
	 */
	public void setTotalInvoiceAmount(float totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
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
	 * @param invoiceId
	 * @param invoiceDate
	 * @param orderId
	 * @param customerId
	 * @param gstTypeId
	 * @param gstAmount
	 * @param totalInvoiceAmount
	 * @param status
	 */
	public Invoice(int invoiceId, Date invoiceDate, int orderId, int customerId, int gstTypeId, double gstAmount,
			double totalInvoiceAmount, boolean status) {
		super();
		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.orderId = orderId;
		this.customerId = customerId;
		this.gstTypeId = gstTypeId;
		this.gstAmount = gstAmount;
		this.totalInvoiceAmount = totalInvoiceAmount;
		this.status = status;
	}
	/**
	 * 
	 */
	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
