package com.hsbc.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.hsbc.models.*;

import com.hsbc.models.*;
public class EmployeeDAOImpl implements EmployeeDAO{
Connection con;
	public EmployeeDAOImpl() {
        super();

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection ("jdbc:derby:invoiceDb;create = true");
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	
	public OrderDetails orderFetcher(int orderId, String query, String queryOrder, String queryProduct,
            EmployeeDAOImpl emImpl) {

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

            while (rs.next()) {
                // ORDER FETCHED
                ppstmtOrder.setInt(1, rs.getInt(1));
                rsOrder = ppstmtOrder.executeQuery();

                while (rsOrder.next()) {
                    // WE GOT NEW PRODUCT ID
                    productList = new ArrayList<Product>();

                    ppstmtProduct.setInt(1, rsOrder.getInt(2));
                    rsProduct = ppstmtProduct.executeQuery();

                    while (rsProduct.next()) {
                        // PRODUCT OBJECT IS ADDED TO PRODUCT ARRAY_LIST
                        productList.add(new Product(rsProduct.getInt(1), rsProduct.getString(2), rsProduct.getDouble(3),
                                rsProduct.getInt(4), rsProduct.getTime(5), rsProduct.getTime(6)));
                    }

                }

                return (new OrderDetails(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), productList,
                        rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getTime(9), rs.getTime(10)));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
	
	 @Override
	    public Invoice getInvoiceByOrderId(int orderId) {
	        // TODO Auto-generated method stub
	
        EmployeeDAOImpl emImpl =  new EmployeeDAOImpl();

        ResultSet rs = null;

        String query = "SELECT * FROM APP.INVOICE WHERE orderId=?";
        String queryOrder = "SELECT * FROM APP.ORDERPRODUCTS WHERE orderId=?  GROUP BY orderId";
        String queryProduct = "SELECT * FROM APP.PRODUCTS WHERE productId=?";

        try {

            PreparedStatement ppstmt = emImpl.con.prepareStatement(query);
            ppstmt.setInt(1, orderId);
            rs = ppstmt.executeQuery();
            if (rs.next()) {

                OrderDetails od = orderFetcher(orderId, query, queryOrder, queryProduct, emImpl);

                return new Invoice(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
                        rs.getDouble(6),rs.getDouble(7), rs.getBoolean(8));
                
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

/*@Override
public Employee getEmployeeById(int employeeId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Customer getCustomerById(int customerId) {
	// TODO Auto-generated method stub
	return null;
}
*/
@Override
public ArrayList<OrderDetails> getOrdersOfEmployee(int employeeId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public OrderDetails approveOrder(int orderId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void addProductsToDB(Product[] products) {
	// TODO Auto-generated method stub
	
}

@Override
public void addOrdertoDB(OrderDetails orderDetails) {
	// TODO Auto-generated method stub
	
}

@Override
public OrderDetails completeOrder(int orderId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public OrderDetails expiryOrder(int orderId) {
	// TODO Auto-generated method stub
	return null;
}
}