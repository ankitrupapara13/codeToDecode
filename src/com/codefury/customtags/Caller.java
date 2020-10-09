package com.codefury.customtags;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;



public class Caller extends BodyTagSupport{
	
	
	String query;
	
	/*String database;
	String username;
	String password;*/
	
	
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	
	@Override
	public int doStartTag() throws JspException {
		
		
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			Connection con = DriverManager.getConnection("jdbc:derby:C:\\Users\\RUPALI TRIPATHI\\MyDB;create=true");	 
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			pageContext.getOut().print("  <table> <tr> <th> Product List </th> > <th> Product Category </th> <th><th> Product Price </th> <th> ");
			 while(rs.next())
			 {
				/* pageContext.getOut().print("<tr> <td>  <input type=\"checkbox\" id=\"1\" name=\"extraProductsList\" value=\"\">\r\n" + 
				 		"                    <label for =\"extraProductsList\">" + rs.getString(4)+"</label> </td></tr>");
				 
				 
				  * pageContext.getOut().print("<tr> <td>  <input type=\"checkbox\" id=\"rs.getString(4)\" name=\"extraProductsList\" value=\"\">\r\n" + 
				 		"                    <label for =\"extraProductsList\">" + rs.getString(4)+"</label> </td></tr>");
				  * */
				String s=rs.getString(1)+",";
				
				 pageContext.getOut().print("<tr> <td>  <input type=\"checkbox\" id="+rs.getString(1)+" name=\"extraProductsList\" value="+rs.getString(1)+">\r\n" + 
					 		"                    <label for =\"extraProductsList\">" + rs.getString(1)+"</label> </td><td>"+rs.getString(4)+"</td><td>"+rs.getDouble(6)+"</td></tr>");
				
//				 pageContext.getOut().print("<tr> <td> " + crs.getInt(1) + " </td> <td> " + crs.getString(2) + " </td>  <td> " + crs.getString(3) + " </td> <td> " + crs.getInt(4) + " </td> <td> " + crs.getInt(5) + " </td> <td> " + crs.getInt(6) + " </td></tr>");
				 
			 }
			 pageContext.getOut().print(" <tr>\r\n" + 
			 		"                  <td>\r\n" + 
			 		"                    <input type = \"submit\" value = \"Compute Costs\" />\r\n" + 
			 		"                  </td>\r\n" + 
			 		"                </tr>");
			 pageContext.getOut().print("</table>");
			 con.close();
		  }
		
				catch (IOException e) 
				{
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		return EVAL_PAGE;
			 
		}
		
		
	
	
	@Override
	public int doEndTag() throws JspException {
		
		return super.doEndTag();
	}

}
