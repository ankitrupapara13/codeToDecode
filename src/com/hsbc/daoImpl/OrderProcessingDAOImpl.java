package com.hsbc.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Company;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;

public class OrderProcessingDAOImpl implements OrderProcessingDAO {
	public Connection con;
	private static OrderProcessingDAOImpl single_instance = null;

	public static OrderProcessingDAOImpl getInstance() {
		if (single_instance == null)
			single_instance = new OrderProcessingDAOImpl();
		return single_instance;
	}

	public OrderProcessingDAOImpl() {
		super();

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection("jdbc:derby:C:\\Users\\Ankit\\MyDB;create=true", "admin", "derby");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();
		ResultSet rs = null;
		Employee emp = null;
		System.out.println(con);
		String query = "SELECT * FROM APP.EMPLOYEE WHERE employeeId=?";
		try {
			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTime(4), rs.getTime(5),
						rs.getTime(6));
			} else {
				throw new EmployeeNotFoundException(
						"Employee is not added in Employee Database. Please add Employee First.");
			}
			System.out.println(emp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();
		ResultSet rs = null;
		Customer cust = null;
		String query = "SELECT * FROM APP.CUSTOMER WHERE customerId=?";
		try {
			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
			ppstmt.setInt(1, customerId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				cust = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getTime(10),
						rs.getTime(11), rs.getTime(12));
			} else {
				throw new CustomerNotFoundException(
						"Customer is not added in Customer Database. Please add Customer First.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cust;
	}

	@Override
	public List<Product> getProductByProductId(int[] productIds)
			throws ProductNotFoundException, CompanyNotFoundException {
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();
		List<Product> al = new ArrayList<>();
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
		String queryCompany = "SELECT * FROM APP.COMPANY WHERE gstNumber=?";

		ResultSet rsProduct = null;
		ResultSet rsCompany = null;

		PreparedStatement ppstmtProduct;
		PreparedStatement ppstmtCompany;

		try {

			ppstmtProduct = emImpl.con.prepareStatement(queryProduct);
			ppstmtCompany = emImpl.con.prepareStatement(queryCompany);

			for (int pId : productIds) {
				ppstmtProduct.setInt(1, pId);
				rsProduct = ppstmtProduct.executeQuery();
				if (rsProduct.next()) {

					String gstNum = rsProduct.getString(5);
					ppstmtCompany.setString(1, gstNum);
					rsCompany = ppstmtCompany.executeQuery();
					if (rsCompany.next()) {
						// PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
						al.add(new Product(rsProduct.getInt(1), rsProduct.getString(2), rsProduct.getDouble(3),
								rsProduct.getString(4),
								new Company(rsCompany.getString(1), rsCompany.getString(2), rsCompany.getString(3),
										rsCompany.getString(4), rsCompany.getTime(5), rsCompany.getTime(6)),
								rsProduct.getTime(6), rsProduct.getTime(7)));
					} else {
						throw new CompanyNotFoundException(
								"Company is not added in Company Database. Please add Company First.");
					}

				} else {
					throw new ProductNotFoundException(
							"Product is not added in Product Database. Please add Product First.");
				}
			}

			return al;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<OrderDetails> getOrdersOfEmployee(int employeeId)
			throws OrderNotFoundForEmployee, ProductNotFoundException {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		List<OrderDetails> orderList = new ArrayList<OrderDetails>();

		ResultSet rs = null;
		ResultSet rsOrder = null;
		ResultSet rsProduct = null;

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE employeeId=?";
		String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=? ";
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
		try {
			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);

			PreparedStatement ppstmtOrder = emImpl.con.prepareStatement(queryOrder);
			PreparedStatement ppstmtProduct = emImpl.con.prepareStatement(queryProduct);

			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				rs.previous();
				while (rs.next()) {

					// WE GOT NEW ORDER ID

					ppstmtOrder.setInt(1, rs.getInt(1));
					rsOrder = ppstmtOrder.executeQuery();

					ArrayList<Product> productList = new ArrayList<Product>();
					if (rsOrder.next()) {

						rsOrder.previous();
						while (rsOrder.next()) {
							// WE GOT NEW PRODUCT ID

							ppstmtProduct.setInt(1, rsOrder.getInt(2));
							rsProduct = ppstmtProduct.executeQuery();

							if (rsProduct.next()) {

								rsProduct.previous();
								while (rsProduct.next()) {
									System.out.println("prod added");
									// PRODUCT IS ADDED TO PRODUCT ARRAY_LIST
									productList.add(new Product(rsProduct.getInt(1), rsProduct.getString(2),
											rsProduct.getDouble(3), rsProduct.getString(4),
											(Company) rsProduct.getObject(5), rsProduct.getTime(6),
											rsProduct.getTime(7)));
								}
							} else {
								throw new ProductNotFoundException(
										"Product is not added in Product Database. Please add Product First.");
							}

						}
					} else {
						throw new OrderNotFoundForEmployee("No order found for this employee ID in OrderProducts table");
					}
					orderList.add(new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), productList,
							rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getTime(9),
							rs.getTime(10)));

				}

			} else {
				throw new OrderNotFoundForEmployee("No order found for this employee ID");
			}

			return orderList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Invoice getInvoiceByOrderId(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {
		// TODO Auto-generated method stub

		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		ResultSet rs = null;

		String query = "SELECT * FROM APP.INVOICE WHERE orderId=?";

		try {

			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				OrderDetails od = orderFetcher(orderId, emImpl);

				return new Invoice(od, rs.getInt(1), rs.getDate(2), rs.getString(4), rs.getFloat(5), rs.getFloat(6),
						rs.getString(7), rs.getTime(8), rs.getTime(9));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public void addProductsToDB(Product[] products) {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		String queryOrderProduct = "INSERT INTO APP.PRODUCT VALUES(?,?,?,?,?,?,?)";
		try {
			emImpl.con.setAutoCommit(false);
			PreparedStatement ppstmt = emImpl.con.prepareStatement(queryOrderProduct);

			for (Product p : products) {
				ppstmt.setInt(1, p.getProductId());
				ppstmt.setString(2, p.getProductName());
				ppstmt.setDouble(3, p.getProductPrice());
				ppstmt.setString(4, p.getProductCategory());
				ppstmt.setString(5, p.getCompany().getGstNumber());
				ppstmt.setTime(6, p.getCreatedAt());
				ppstmt.setTime(7, p.getUpdatedAt());
				ppstmt.execute();
			}
			emImpl.con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public OrderDetails addOrdertoDB(OrderDetails orderDetails) {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		String query = "INSERT INTO APP.ORDERDETAILS VALUES(?,?,?,?,?,?,?,?,?)";
		String queryOrderProduct = "INSERT INTO APP.ORDERPRODUCTS VALUES(?,?)";
		try {
			emImpl.con.setAutoCommit(false);

			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
			PreparedStatement ppstmtOrderProduct = emImpl.con.prepareStatement(queryOrderProduct);

			ppstmt.setInt(1, orderDetails.getOrderId());
			ppstmt.setDate(2, (java.sql.Date) orderDetails.getOrderDate());
			ppstmt.setInt(3, orderDetails.getCustomerId());
			ppstmt.setInt(4, orderDetails.getEmployeeId());
			ppstmt.setDouble(5, orderDetails.getTotalOrderValue());
			ppstmt.setDouble(6, orderDetails.getShippingCost());
			ppstmt.setString(7, orderDetails.getShippingCompany());
			ppstmt.setString(8, orderDetails.getStatus());
			ppstmt.setTime(9, orderDetails.getCreatedAt());
			ppstmt.setTime(10, orderDetails.getUpdatedAt());

			ppstmtOrderProduct.setInt(1, orderDetails.getOrderId());

			for (Product p : orderDetails.getProducts()) {
				ppstmtOrderProduct.setInt(2, p.getProductId());
				ppstmtOrderProduct.execute();
			}

			ppstmt.execute();
			emImpl.con.commit();

			return orderDetails;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OrderDetails approveOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		OrderDetails od = orderFetcher(orderId, emImpl);

		od.setStatus("APPROVED");

		try {
			Statement stmt = emImpl.con.createStatement();
			stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='APPROVED' WHERE orderId=" + orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return od;

	}

	@Override // CRON
	public OrderDetails completeOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {
		// TODO Auto-generated method stub
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		OrderDetails od = orderFetcher(orderId, emImpl);

		od.setStatus("COMPLETED");

		try {
			Statement stmt = emImpl.con.createStatement();
			stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='COMPLETED' WHERE orderId=" + orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return od;
	}

	@Override // CRON
	public OrderDetails expiryOrder(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {
		OrderProcessingDAOImpl emImpl = OrderProcessingDAOImpl.getInstance();

		OrderDetails od = orderFetcher(orderId, emImpl);

		od.setStatus("EXPIRED");

		try {
			Statement stmt = emImpl.con.createStatement();
			stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='EXPIRED' WHERE orderId=" + orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return od;
	}

	public OrderDetails orderFetcher(int orderId, OrderProcessingDAOImpl emImpl) throws OrderNotFoundForEmployee, ProductNotFoundException {

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE orderId=?";
		String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=?";
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";

		ResultSet rs = null;
		ResultSet rsOrder = null;
		ResultSet rsProduct = null;

		ArrayList<Product> productList = null;

		try {
			PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
			PreparedStatement ppstmtOrder = emImpl.con.prepareStatement(queryOrder);
			PreparedStatement ppstmtProduct = emImpl.con.prepareStatement(queryProduct);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				rs.previous();
				while (rs.next()) {
					// ORDER FETCHED
					ppstmtOrder.setInt(1, rs.getInt(1));
					rsOrder = ppstmtOrder.executeQuery();

					if (rsOrder.next()) {

						rsOrder.previous();
						while (rsOrder.next()) {
							// WE GOT NEW PRODUCT ID
							productList = new ArrayList<Product>();

							ppstmtProduct.setInt(1, rsOrder.getInt(2));
							rsProduct = ppstmtProduct.executeQuery();

							if (rsProduct.next()) {

								rsProduct.previous();
								while (rsProduct.next()) {
									// PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
									productList.add(new Product(rsProduct.getInt(1), rsProduct.getString(2),
											rsProduct.getDouble(3), rsProduct.getString(4),
											(Company) rsProduct.getObject(5), rsProduct.getTime(6),
											rsProduct.getTime(7)));
								}
							} else {
								throw new ProductNotFoundException(
										"Product is not added in Product Database. Please add Product First.");
							}

						}
					} else {
						throw new OrderNotFoundForEmployee("No order found for this employee ID in OrderProducts table");
					}

					return (new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), productList,
							rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getTime(9),
							rs.getTime(10)));
				}
			} else {
				throw new OrderNotFoundForEmployee("No order found for this employee ID");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}