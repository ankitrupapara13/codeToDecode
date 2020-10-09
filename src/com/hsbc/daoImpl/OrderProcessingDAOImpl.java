package com.hsbc.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection("jdbc:derby:C:\\Users\\Ankit\\MyDB;create=true", "admin", "derby");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		Employee emp = null;
		System.out.println(con);
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
//		OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();
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
//		OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();
		List<Product> al = new ArrayList<>();
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
		String queryCompany = "SELECT * FROM APP.COMPANY WHERE gstNumber=?";

		ResultSet rsProduct = null;
		ResultSet rsCompany = null;

		PreparedStatement ppstmtProduct;
		PreparedStatement ppstmtCompany;

		try {

			ppstmtProduct = con.prepareStatement(queryProduct);
			ppstmtCompany = con.prepareStatement(queryCompany);

			for (int pId : productIds) {
				ppstmtProduct.setInt(1, pId);

				al.add(productFetcher(pId));

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
			throws OrderNotFoundForEmployee, ProductNotFoundException, CompanyNotFoundException {
		// TODO Auto-generated method stub
//		OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

		List<OrderDetails> orderList = new ArrayList<OrderDetails>();

		ResultSet rs = null;
		ResultSet rsOrder = null;
		ResultSet rsProduct = null;

		String query = "SELECT * FROM APP.ORDERDETAILS WHERE employeeId=?";
		String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=? ";
		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
		try {
			PreparedStatement ppstmt = con.prepareStatement(query);

			PreparedStatement ppstmtOrder = con.prepareStatement(queryOrder);
			PreparedStatement ppstmtProduct = con.prepareStatement(queryProduct);

			ppstmt.setInt(1, employeeId);
			rs = ppstmt.executeQuery();

			if (rs.next()) {

//				rs = ppstmt.executeQuery();
				do {

					// WE GOT NEW ORDER ID

					ppstmtOrder.setInt(1, rs.getInt(1));
					rsOrder = ppstmtOrder.executeQuery();

					ArrayList<Product> productList = new ArrayList<Product>();
					if (rsOrder.next()) {

//						rsOrder = ppstmtOrder.executeQuery();
						do {
							// WE GOT NEW PRODUCT ID

							ppstmtProduct.setInt(1, rsOrder.getInt(2));
							rsProduct = ppstmtProduct.executeQuery();

							if (rsProduct.next()) {
								do {

//								System.out.println("prod added");
									// PRODUCT IS ADDED TO PRODUCT ARRAY_LIST
									productList.add(productFetcher(rsProduct.getInt(1)));
//											new Product(rsProduct.getInt(1), rsProduct.getString(2),
//											rsProduct.getDouble(3), rsProduct.getString(4),
//											, rsProduct.getTime(6),
//											rsProduct.getTime(7))

								} while (rsProduct.next());
							} else {
								throw new ProductNotFoundException(
										"Product is not added in Product Database. Please add Product First.");
							}

							orderList.add(new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4),
									productList, rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8),
									rs.getTime(9), rs.getTime(10)));

//						System.out.println(orderList);
						} while (rsOrder.next());

					} else {
						throw new OrderNotFoundForEmployee(
								"No order found for this employee ID in OrderProducts table");
					}
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
	public Invoice addInvoiceToDB(Invoice invoice) {

		String queryInvoice = "INSERT INTO APP.INVOICE VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmt = con.prepareStatement(queryInvoice);

			ppstmt.setInt(1, invoice.getInvoiceId());
			ppstmt.setDate(2, (Date) invoice.getInvoiceDate());
			ppstmt.setInt(3, invoice.getOrderDetails().getOrderId());
			ppstmt.setString(4, invoice.getGstType());
			ppstmt.setDouble(5, invoice.getGstAmount());
			ppstmt.setDouble(6, invoice.getTotalInvoiceAmount());
			ppstmt.setString(7, invoice.getInvoiceStatus());
			ppstmt.setTime(8, invoice.getInvoiceCreatedAt());
			ppstmt.setTime(9, invoice.getInvoiceUpdatedAt());
			ppstmt.execute();

			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Invoice getInvoiceByOrderId(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException, InvoiceNotFoundException {
		// TODO Auto-generated method stub

		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

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
			}else {
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

		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

		List<Product> al = new ArrayList<>();
		String queryProduct = "SELECT * FROM APP.PRODUCT";
		String queryCompany = "SELECT * FROM APP.COMPANY WHERE gstNumber=?";

		ResultSet rsProduct = null;
		ResultSet rsCompany = null;

		PreparedStatement ppstmtProduct;
		PreparedStatement ppstmtCompany;

		try {

			ppstmtProduct = con.prepareStatement(queryProduct);
			ppstmtCompany = con.prepareStatement(queryCompany);

			rsProduct = ppstmtProduct.executeQuery();

			if (rsProduct.next()) {

				do {
					al.add(productFetcher(rsProduct.getInt(1)));
				} while (rsProduct.next());

//				rsProduct = ppstmtProduct.executeQuery();
//				while (rsProduct.next()) {
//					String gstNum = rsProduct.getString(5);
//					ppstmtCompany.setString(1, gstNum);
//					rsCompany = ppstmtCompany.executeQuery();
//					if (rsCompany.next()) {
//						// PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
//						al.add(new Product(rsProduct.getInt(1), rsProduct.getString(2), rsProduct.getDouble(3),
//								rsProduct.getString(4),
//								new Company(rsCompany.getString(1), rsCompany.getString(2), rsCompany.getString(3),
//										rsCompany.getString(4), rsCompany.getTime(5), rsCompany.getTime(6)),
//								rsProduct.getTime(6), rsProduct.getTime(7)));
//					} else {
//						throw new CompanyNotFoundException(
//								"Company is not added in Company Database. Please add Company First.");
//					}
//
//				}
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
		// TODO Auto-generated method stub
		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

		String queryOrderProduct = "INSERT INTO APP.PRODUCT VALUES(?,?,?,?,?,?,?)";
		try {
			con.setAutoCommit(false);
			PreparedStatement ppstmt = con.prepareStatement(queryOrderProduct);

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
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public OrderDetails addOrdertoDB(OrderDetails orderDetails) {
		// TODO Auto-generated method stub
		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

		String query = "INSERT INTO APP.ORDERDETAILS VALUES(?,?,?,?,?,?,?,?,?)";
		String queryOrderProduct = "INSERT INTO APP.ORDERPRODUCTS VALUES(?,?)";
		try {
			con.setAutoCommit(false);

			PreparedStatement ppstmt = con.prepareStatement(query);
			PreparedStatement ppstmtOrderProduct = con.prepareStatement(queryOrderProduct);

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
			con.commit();

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
		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

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

		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

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

		// OrderProcessingDAO emImpl = OrderProcessingDAOImpl.getInstance();

		List<OrderDetails> updatedOrders = new ArrayList<OrderDetails>();

		//untested
		String fetchPendingQuery = "SELECT * FROM APP.ORDERDETAILS WHERE STATUS = 'PENDING' WHEN DATEDIFF(day, CURRENT_DATE, CREATEDAT) > 29 ;";

		ResultSet rs;
		try {
			PreparedStatement ppstmtFetch = con.prepareStatement(fetchPendingQuery);
			rs = ppstmtFetch.executeQuery();

			while (rs.next()) {

				int orderId = rs.getInt(1);

				OrderDetails od = orderFetcher(orderId);
				System.out.println(od);
				od.setStatus("EXPIRED");

				try {
					Statement stmt = con.createStatement();
					stmt.execute("UPDATE APP.ORDERDETAILS SET STATUS='EXPIRED' WHERE orderId=" + orderId);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updatedOrders.add(od);
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
//		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";

		ResultSet rs = null;
		ResultSet rsOrder = null;
//		ResultSet rsProduct = null;

		ArrayList<Product> productList = null;

		try {
			PreparedStatement ppstmt = con.prepareStatement(query);
			PreparedStatement ppstmtOrder = con.prepareStatement(queryOrder);
//			PreparedStatement ppstmtProduct = con.prepareStatement(queryProduct);
			ppstmt.setInt(1, orderId);
			rs = ppstmt.executeQuery();

			while (rs.next()) {

				// ORDER FETCHED
				ppstmtOrder.setInt(1, rs.getInt(1));
				rsOrder = ppstmtOrder.executeQuery();

				while (rsOrder.next()) {

					// WE GOT NEW PRODUCT ID
					productList = new ArrayList<Product>();
//					ppstmtProduct.setInt(1, rsOrder.getInt(2));
//
//					rsProduct = ppstmtProduct.executeQuery();
//					if (!rsProduct.next()) {
//						System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//					} else {
//
//						do {
//							// PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
//							System.out.println("product added");
					productList.add(productFetcher(rsOrder.getInt(2)));
//						} while (rsProduct.next());
				}

//				}
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

//	public OrderDetails orderFetcher(int orderId) throws OrderNotFoundForEmployee, ProductNotFoundException {
//
//		String query = "SELECT * FROM APP.ORDERDETAILS WHERE orderId=?";
//		String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=?";
//		String queryProduct = "SELECT * FROM APP.PRODUCT WHERE productId=?";
//
//		ResultSet rs = null;
//		ResultSet rsOrder = null;
//		ResultSet rsProduct = null;
//
//		ArrayList<Product> productList = null;
//
//		try {
//			PreparedStatement ppstmt = con.prepareStatement(query);
//			PreparedStatement ppstmtOrder = con.prepareStatement(queryOrder);
//			PreparedStatement ppstmtProduct = con.prepareStatement(queryProduct);
//			ppstmt.setInt(1, orderId);
//			rs = ppstmt.executeQuery();
////			if (rs.next()) {
////				rs.previous();
//			while (rs.next()) {
//
////				System.out.println("inside while");
//				// ORDER FETCHED
//				ppstmtOrder.setInt(1, rs.getInt(1));
//				rsOrder = ppstmtOrder.executeQuery();
//
////				if (rsOrder.next()) {
////					System.out.println("pro if");
////					rsOrder = ppstmtOrder.executeQuery();
//				while (rsOrder.next()) {
//
////						System.out.println("pro while");
//					// WE GOT NEW PRODUCT ID
//					productList = new ArrayList<Product>();
//					ppstmtProduct.setInt(1, rsOrder.getInt(2));
//					rsProduct = ppstmtProduct.executeQuery();
//
////							if (rsProduct.next()) {
////
////								System.out.println("p if");
////								rsProduct = ppstmtProduct.executeQuery();
//					while (rsProduct.next()) {
//
////							System.out.println("p while");
//						// PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
//						productList.add(new Product(rsProduct.getInt(1), rsProduct.getString(2), rsProduct.getDouble(3),
//								rsProduct.getString(4), (Company) rsProduct.getObject(5), rsProduct.getTime(6),
//								rsProduct.getTime(7)));
//					}
////						if (productList.size() < 1) {
////							throw new ProductNotFoundException(
////									"Product is not added in Product Database. Please add Product First.");
////						}
////							} else {
////								throw new ProductNotFoundException(
////										"Product is not added in Product Database. Please add Product First.");
////							}
//
////					}
////					} else {
////						throw new OrderNotFoundForEmployee(
////								"No order found for this Order ID in OrderProducts table");
////					}
//
//				}
//				return (new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), productList,
//						rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getTime(9),
//						rs.getTime(10)));
//
////			} else {
////				throw new OrderNotFoundForEmployee("No order found for this employee ID");
////			}
//
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

}