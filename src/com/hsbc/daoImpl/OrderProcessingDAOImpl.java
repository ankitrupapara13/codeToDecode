package com.hsbc.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hsbc.dao.OrderProcessingDAO;
import com.hsbc.exceptions.CompanyNotFoundException;
import com.hsbc.exceptions.CustomerNotFoundException;
import com.hsbc.exceptions.EmployeeNotFoundException;
import com.hsbc.exceptions.InvoiceNotFoundException;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;
import com.hsbc.models.Company;
import com.hsbc.models.Customer;
import com.hsbc.models.Employee;
import com.hsbc.models.Invoice;
import com.hsbc.models.OrderDetails;
import com.hsbc.models.Product;
import com.hsbc.models.SessionEntity;

public class OrderProcessingDAOImpl implements OrderProcessingDAO {

	private static Connection con;
	private static OrderProcessingDAO single_instance = null;

	public static OrderProcessingDAO getInstance() {
		if (single_instance == null)
			single_instance = new OrderProcessingDAOImpl();
		return single_instance;
	}

	static {
		try {	
			@SuppressWarnings("unused")
			OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection("jdbc:derby:C:\\Users\\surya\\Desktop\\Code fury\\V2\\codeToDecode\\src\\com\\hsbc\\MyDB;create=true", "admin", "derby");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		Employee emp = null;
		String query = "SELECT * FROM APP.EMPLOYEE WHERE employeeId=?";
		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTime(4), rs.getTime(5),
						rs.getTime(6));
			} else {
				throw new EmployeeNotFoundException(
						"Employee is not added in Employee Database. Please add Employee First.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		Customer cust = null;
		String query = "SELECT * FROM APP.CUSTOMER WHERE customerId=?";
		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
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

	public Product productFetcher(int productId) throws CompanyNotFoundException, ProductNotFoundException {
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
		String queryCompany = "SELECT * FROM APP.COMPANY WHERE gstNumber=?";

		ResultSet rsProduct = null;
		ResultSet rsCompany = null;

		PreparedStatement ppstmtProduct;
		PreparedStatement ppstmtCompany;

		try {
			ppstmtProduct = con.prepareStatement(queryProduct);
			ppstmtCompany = con.prepareStatement(queryCompany);

			ppstmtProduct.setInt(1, productId);

			rsProduct = ppstmtProduct.executeQuery();
			if (rsProduct.next()) {
				// Product Found
				String gstNum = rsProduct.getString(5);

				ppstmtCompany.setString(1, gstNum);
				rsCompany = ppstmtCompany.executeQuery();
				if (rsCompany.next()) {
					// PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
					return (new Product(rsProduct.getInt(1), rsProduct.getString(2), rsProduct.getDouble(3),
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Product> getProductByProductIds(int[] productIds)
			throws ProductNotFoundException, CompanyNotFoundException {

		List<Product> al = new ArrayList<>();

		for (int pId : productIds) {

			al.add(productFetcher(pId));

		}

		return al;

	}

	@Override
	public List<OrderDetails> getOrdersOfEmployee(int employeeId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException {
		// TODO Auto-generated method stub

		List<OrderDetails> orderList = new ArrayList<OrderDetails>();

		ResultSet rs = null;

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE employeeId=?";

		try {

			PreparedStatement ppstmt = con.prepareStatement(query);

			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				do {

					orderList.add(orderFetcher(rs.getInt(1)));

				} while (rs.next());

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
	public List<OrderDetails> getOrdersOfCustomer(int customerId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException {

		List<OrderDetails> orderList = new ArrayList<OrderDetails>();

		ResultSet rs = null;

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE customerId=?";

		PreparedStatement ppstmt;
		try {
			ppstmt = con.prepareStatement(query);

			ppstmt.setInt(1, customerId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				do {
					orderList.add(orderFetcher(rs.getInt(1)));
				} while (rs.next());
			} else {
				throw new OrderNotFoundForEmployee("No order found for this Customer ID");
			}
			return orderList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Invoice addInvoiceToDB(Invoice invoice) {

		String queryInvoice = "INSERT INTO APP.INVOICE VALUES(?,?,?,?,?,?,?,?,?)";
		String counter = "SELECT COUNT(*) AS TOTALENTRIES FROM APP.INVOICE";

		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmt = con.prepareStatement(queryInvoice);

			PreparedStatement ps = con.prepareStatement(counter);
			ResultSet count = ps.executeQuery();

			if (count.next()) {

				int currentEntries = count.getInt("TOTALENTRIES");

				ppstmt.setInt(1, 101 + currentEntries);
				ppstmt.setDate(2, (Date) invoice.getInvoiceDate());
				ppstmt.setInt(3, invoice.getOrderDetails().getOrderId());
				ppstmt.setString(4, invoice.getGstType());
				ppstmt.setDouble(5, invoice.getGstAmount());
				ppstmt.setDouble(6, invoice.getTotalInvoiceAmount());
				ppstmt.setString(7, invoice.getInvoiceStatus());
				ppstmt.setTime(8, invoice.getInvoiceCreatedAt());
				ppstmt.setTime(9, invoice.getInvoiceUpdatedAt());
				int status = ppstmt.executeUpdate();
				if (status > 0) {
					con.commit();
				} else {

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Invoice getInvoiceByOrderId(int orderId)
			throws OrderNotFoundForEmployee, ProductNotFoundException, InvoiceNotFoundException {
		// TODO Auto-generated method stub

		ResultSet rs = null;

		String query = "SELECT * FROM APP.INVOICE WHERE orderId=?";

		try {

			PreparedStatement ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

				OrderDetails od = orderFetcher(orderId);

				return new Invoice(od, rs.getInt(1), rs.getDate(2), rs.getString(4), rs.getFloat(5), rs.getFloat(6),
						rs.getString(7), rs.getTime(8), rs.getTime(9));
			} else {
				throw new InvoiceNotFoundException("Invioce is not present in database.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public List<Product> getProducts() throws ProductNotFoundException, CompanyNotFoundException {

		List<Product> al = new ArrayList<>();
		String queryProduct = "SELECT * FROM APP.PRODUCT";

		ResultSet rsProduct = null;

		PreparedStatement ppstmtProduct;

		try {

			ppstmtProduct = con.prepareStatement(queryProduct);

			rsProduct = ppstmtProduct.executeQuery();

			if (rsProduct.next()) {

				do {
					al.add(productFetcher(rsProduct.getInt(1)));
				} while (rsProduct.next());

				return al;

			} else {
				throw new ProductNotFoundException(
						"Product is not added in Product Database. Please add Product First.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public void addProductsToDB(Product[] products) {

		String queryOrderProduct = "INSERT INTO APP.PRODUCT VALUES(?,?,?,?,?,?,?)";
		String counter = "SELECT COUNT(*) AS TOTALENTRIES FROM APP.PRODUCT";

		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmt = con.prepareStatement(queryOrderProduct);
			PreparedStatement ps = con.prepareStatement(counter);
			ResultSet count = ps.executeQuery();

			if (count.next()) {

				int currentEntries = count.getInt("TOTALENTRIES");

				int i = 0;

				for (Product p : products) {

					ppstmt.setInt(1, 11 + currentEntries + i);
					ppstmt.setString(2, p.getProductName());
					ppstmt.setDouble(3, p.getProductPrice());
					ppstmt.setString(4, p.getProductCategory());
					ppstmt.setString(5, p.getCompany().getGstNumber());
					ppstmt.setTime(6, p.getCreatedAt());
					ppstmt.setTime(7, p.getUpdatedAt());
					int status = ppstmt.executeUpdate();
					if (status > 0) {
						con.commit();
					}
					i++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public OrderDetails addOrdertoDB(OrderDetails orderDetails) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO APP.ORDERDETAILS VALUES(?,?,?,?,?,?,?,?,?,?)";
		String queryOrderProduct = "INSERT INTO APP.ORDERPRODUCTS VALUES(?,?)";
		String counter = "SELECT COUNT(*) AS TOTALENTRIES FROM APP.ORDERDETAILS";

		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(query);
			PreparedStatement ppstmtOrderProduct = con.prepareStatement(queryOrderProduct);

			PreparedStatement ps = con.prepareStatement(counter);
			ResultSet count = ps.executeQuery();

			if (count.next()) {

				int currentEntries = count.getInt("TOTALENTRIES");

				ppstmt.setInt(1, 1001 + currentEntries);
				ppstmt.setDate(2, (java.sql.Date) orderDetails.getOrderDate());
				ppstmt.setInt(3, orderDetails.getCustomerId());
				ppstmt.setInt(4, orderDetails.getEmployeeId());
				ppstmt.setDouble(5, orderDetails.getTotalOrderValue());
				ppstmt.setDouble(6, orderDetails.getShippingCost());
				ppstmt.setString(7, orderDetails.getShippingCompany());
				ppstmt.setString(8, orderDetails.getStatus());
				ppstmt.setTime(9, orderDetails.getCreatedAt());
				ppstmt.setTime(10, orderDetails.getUpdatedAt());

				ppstmtOrderProduct.setInt(1, 1001 + currentEntries);

				for (Product p : orderDetails.getProducts()) {
					ppstmtOrderProduct.setInt(2, p.getProductId());
					int status = ppstmtOrderProduct.executeUpdate();
					if (status > 0) {
						con.commit();
					}
				}

				int status = ppstmt.executeUpdate();

				if (status > 0) {
					con.commit();
				}
			}
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

		System.out.println("in approve order with id " + orderId);
		OrderDetails od = orderFetcher(orderId);

		od.setStatus("APPROVED");

		try {
			Statement stmt = con.createStatement();
			stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='APPROVED' WHERE orderId=" + orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return od;

	}

	@Override // CRON
	public List<OrderDetails> completeOrder() throws OrderNotFoundForEmployee, ProductNotFoundException {

		List<OrderDetails> updatedOrders = new ArrayList<OrderDetails>();

		String fetchApprovedQuery = "SELECT * FROM APP.ORDERDETAILS WHERE STATUS = 'APPROVED'";

		try {

			PreparedStatement ppstmtFetch = con.prepareStatement(fetchApprovedQuery);
			ResultSet rs = ppstmtFetch.executeQuery();

			while (rs.next()) {

				int orderId = rs.getInt(1);

				OrderDetails od = orderFetcher(orderId);

				od.setStatus("COMPLETED");

				try {
					Statement stmt = con.createStatement();
					stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='COMPLETED' WHERE orderId=" + orderId);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				updatedOrders.add(od);

			}

			return updatedOrders;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	@Override // CRON
	public List<OrderDetails> expiryOrder() throws OrderNotFoundForEmployee, ProductNotFoundException {

		List<OrderDetails> updatedOrders = new ArrayList<OrderDetails>();

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -30);

		@SuppressWarnings("deprecation")
		Date tryDate = new Date(cal.getTime().getYear(), cal.getTime().getMonth(), cal.getTime().getDate());

		String fetchPendingQuery = "UPDATE APP.ORDERDETAILS SET STATUS = 'EXPIRED' where orderDate < ? AND STATUS != 'EXPIRED'";

		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmtFetch = con.prepareStatement(fetchPendingQuery);
			ppstmtFetch.setDate(1, tryDate);
			int status = ppstmtFetch.executeUpdate();

			if (status > 0) {
				System.out.println(status);
				con.commit();
			}

			return updatedOrders;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		return null;
	}

	public OrderDetails orderFetcher(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE orderId=?";
		String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=?";

		ResultSet rs = null;
		ResultSet rsOrder = null;

		ArrayList<Product> productList = null;

		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
			PreparedStatement ppstmtOrder = con.prepareStatement(queryOrder);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			while (rs.next()) {

				// ORDER FETCHED
				ppstmtOrder.setInt(1, rs.getInt(1));
				rsOrder = ppstmtOrder.executeQuery();
				productList = new ArrayList<Product>();

				while (rsOrder.next()) {

					// WE GOT NEW PRODUCT ID
					productList.add(productFetcher(rsOrder.getInt(2)));

				}

				return (new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), productList,
						rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getTime(9),
						rs.getTime(10)));

			}
		} catch (SQLException | CompanyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer updateCustomerPassword(Customer customer) {

		String updateQuery = "UPDATE APP.CUSTOMER SET password = ? WHERE customerId=?";

		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(updateQuery);
			ppstmt.setString(1, customer.getPassword());
			ppstmt.setInt(2, customer.getCustomerId());

			boolean status = ppstmt.execute();

			if (status) {
				con.commit();
				return customer;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Employee updateEmployeePassword(Employee employee) {

		String updateQuery = "UPDATE APP.EMPLOYEE SET password = ? WHERE employeeId=?";

		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(updateQuery);
			ppstmt.setString(1, employee.getPassword());
			ppstmt.setInt(2, employee.getEmployeeId());

			boolean status = ppstmt.execute();

			if (status) {
				con.commit();
				return employee;
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public SessionEntity tokenFetcher(int personId) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM APP.SESSIONENTITY WHERE personId=?";
		PreparedStatement ppstmt;
		ResultSet rs;
		try {
			ppstmt = con.prepareStatement(query);
			ppstmt.setInt(1, personId);
			rs = ppstmt.executeQuery();
			if (rs.next()) {
				return new SessionEntity(rs.getInt(1), rs.getString(2));
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public SessionEntity updateToken(SessionEntity sessionEntity) {

		String query = "UPDATE APP.SESSIONENTITY SET sessionToken = ? WHERE personId=?";
		PreparedStatement ppstmt;
		try {
			con.setAutoCommit(false);
			ppstmt = con.prepareStatement(query);
			ppstmt.setString(1, sessionEntity.getSessionToken());
			ppstmt.setInt(2, sessionEntity.getPersonId());
			boolean status = ppstmt.execute();
			if (status) {
				con.commit();
				return sessionEntity;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}