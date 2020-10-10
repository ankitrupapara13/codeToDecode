package com.hsbc.models;

public class Company {
	String companyName;
	String companyAddress;
	String companyCity;
	int gstNumber;
	public Company() {
		super();
	}
	public Company(String companyName, String companyAddress, String companyCity, int gstNumber) {
		super();
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.companyCity = companyCity;
		this.gstNumber = gstNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyCity() {
		return companyCity;
	}
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	public int getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(int gstNumber) {
		this.gstNumber = gstNumber;
	}
	@Override
	public String toString() {
		return "Company [companyName=" + companyName + ", companyAddress=" + companyAddress + ", companyCity="
				+ companyCity + ", gstNumber=" + gstNumber + "]";
	}
	
	

}
