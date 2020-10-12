<%@page import="java.sql.Date"%>
<%@page import="com.hsbc.dto.CustomerDTO"%>
<%@page import="java.util.Map"%>
<%@page import="com.hsbc.models.OrderDetails"%>
<%@page import="java.util.List"%>
<%@page import="com.hsbc.dto.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <title>Order Management Employee</title>
    <script>
        function onLoad() {

            
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    document.getElementById("displayInvoice").innerHTML = xhr.responseText;
                }
            }


            xhr.open('GET', './demo', true);
            xhr.send(); 
        }
    </script>
    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="orderEmp.css">
</head>

<body>

    <nav class="navbar fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAjVBMVEX////bABHZAADbAAn66Oj1zs7eJi/zv8HbAA7bAAD87e7aAAX+9vfiT1X30dP65OX98PH0w8X1ycvkXGH529zsk5ffMjn/+vvhSlDrjJDjVFnpgIT41tjoeHzncnbwq67ytLfvo6btm57cFB/qiIzlY2jmaW3iQEndHCffOD/fLDTxrrHqg4fndHnrkJN+XPi7AAAFmElEQVR4nO3ce1fbRhDG4ZWc2N4SmkATnNIWCLQFcun3/3iVfNNtZ/YdaVc6J+f9/c8ePWdMYkaWnWOMMcYYY4wxxhhjjDHGGGOMMcYYY4wxxhhjjDHGGGOMMcYYY4z9ZL27fpuv61+X5lXA502Zr5v7T4sDn17cQ7ktsuSf3VX5uDBw7deuIuYBvu5c9QL5sijwxa8qYZ4p+tfq5E1ZLDnF3doXe2GOKdYT3AuL5aZYA4/C9ER/UwP3wsWIu5cKeBKmJvqbw7F74ULE3dcaeBa6bymJxwmehEV5vxiwEaYk+q9H4Em4APEEbAnTERvgWTg7cXdzBLaF7j4NsQVshEX5bRlgR5iG6NcNsCWcl9gAu8IUxA6wLZyT+NoAe0L3ZSrRr9+1z2sLi/JhAWBfOJXoXzrArnA7E7EDHAinEXsT7AlnIj53gEPhFGJ/gn3hLMTuBEPC8cQhsC+cgdibYFDoHscRA8CBsCJusgI/94FB4Tiif/owPGkgzEwcTFAQjiH6p+EEQ8KsxOEEJaH7ZCUGJxgUZiSGgJLQShSAQWE2YhAoCt1/FqIvwsCwsCJeZQBeBoGy0EL0xS/CIWFhFqIAVIQ40W+FCYrCDEQJqAnd7xhRnqAsrIjXSYG3ElAVur8RYrmVgbIwMfFWvlJViBBLrwAVYVKiAowI48SL7Rvt5xVhQuJv2lVGhO67TrzwKlAVVsS3SYDaBONCnXihvkRjwkRTVCcICN0f8gGxCcaEFXH6LdQIEBDKxAv/PvazEWECYgyICCVifIJx4WTiP9F/7RFhmAhMEBBWxD+zAjGh+2t40AoBAsJJUwSAoHBIxICIcMIU/0Xec4HCPhEEQsJi68cRISAsdD/ax6FATFisRk0RA+LCNnHlP4I/hAkrInqgGWgQurvTkTgQFVqONAItwhPRcjWo0Ey8Qw82CQ/Hmq4FFhqJP+BzbcL6pWH7lcGFJiI+QauwItpeTQahgWgBWoXuzvbrYhHiRHCBNE54a9uRmYRe3xe0suyrjcLP3rYGtAilvXkowzLXJrz0xk2nQagtJacQTcL91tVExIV+hU+wDv5dtAiPa2XLdgUW2iZoIRqE57WygYgKL1ZWILivtghbNwZwIijU18rTiLCws5SEiZgwslaeRkSFvZUWusyFhMhKK1xkX20RDtbKIBERjgdCREwYWEpiCyRACO3sxJR9tUUY3LpCxLhwygQhIiIU1soIMSqcNkGECAjFpSSwBowJpwODy1ybUNm6xokRYQpgbIpRobpWjhJ1YRpgZIoxYWSlFVvmqkJ46zqJGBFGd3aRZa4mTAdUiboQWErqmylFaF+RasmrG1UIbXzUK5WFaYHKtWpC9MaAMkVRmBooExUhvLNTrlYSpgeK1ysLDUtJ+XoF4Zg7MfHCrzlRaNu6SsSwMMcERaIkjLwVQq85KFyNvCM6jigIjUDxP7eQMM9LVCSGhWagRAwI800wTAwK439WosTQ0wh5v0li8D46JBwFDL+PDjxRknOCIWJAOBIYJA6fCsr/XSC9P9iHQmR/BRMHT3bN8WUnXeJAOHqCQWL/6bx5vs2lQ+wLJ0zwQOzulXpPWKb5xGy89vKzJwTvBSjE7vq6LUz98XyQ2BVOnOCe2JliSzjfBLvEjtB0f1wktp9OaIRzTrDu8vz5ppYwCbB7K/AsnBvY3CtrCRMBK2Jzr+wknB94JjZC08NqEeJ5ikdhnofVMOJZmBDYIh6EywCPDySehOaHRmPED40w99PNOvEoTAw8fzimFi4H3BMPwuTA0xQr4ZLAmrgXjnwAP0Ksp7gplwU691yuMwEPxE25MNC516dcwJq4u1ocWP2l8ZjxmyFXjw9L+6rev8nZ0jrGGGOMMcYYY4wxxhhjjDHGGGOMMcYYY4wxxhhjjDHGGGNj+h/pT1sbioHmJgAAAABJRU5ErkJggg=="
                width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>
    <%
		EmployeeDTO employeeDTO = (EmployeeDTO)request.getAttribute("employee");
	%>
    
    <div style="margin-top: 7%;" class="container">
        <div class="jumbotron">
            <h1 class="display-4">Welcome,<span id="empName"><%=employeeDTO.getUserName() %></span></h1>
            <p class="lead">Find all your order related stuff in one place!</p>
            <hr style=" border: 2px solid red;"><br>
            <p class="lead">
                <a class="btn btn-primary btn-danger" href="#" role="button">Learn more</a>
            </p>
        </div>
    </div>
    <div class="empdetails">

        <p>Employee ID: <span class="blc" id="empId"><%=employeeDTO.getEmployeeId() %></span></p>
        <p>Employee Username: <span class="blc" id="empUsername"><%=employeeDTO.getUserName() %></span></p>
        <p>Last Logged in at: <span class="blc" id="empLastLogin"><%=employeeDTO.getLastLogin() %></span></p>

    </div>
    <hr id="h">
    <div class="table-responsive-sm">
        <table class="table table-hover">
            <thead class="thead-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Order ID</th>
                    <th scope="col">Customer Name</th>
                    <th scope="col">Order Date</th>
                    <th scope="col">Order Value</th>
                    <th scope="col">Customer City</th>
                    <th scope="col">Status</th>
                </tr>
            </thead>
            <tbody>
           <%
            	List<OrderDetails> orderDetails = employeeDTO.getOrderDetails();
            	Map<Integer,CustomerDTO> customers = employeeDTO.getCustomers(); 
            	for(int i = 0; i < orderDetails.size(); i++){
            		int orderId = orderDetails.get(i).getOrderId();
            		String customerName = customers.get(orderDetails.get(i).getCustomerId()).getName();
            		String city = customers.get(orderDetails.get(i).getCustomerId()).getCity();
            		double orderValue = orderDetails.get(i).getTotalOrderValue();
            		Date orderDate = orderDetails.get(i).getOrderDate();
            		String status = orderDetails.get(i).getStatus();
            		
            
            %>
                <tr>
                    <th scope="row"><%=(i+1) %></th>
                    <td class="orderId"><%=orderId%></td>
                    <td class="custName"><%=customerName %></td>
                    <td class="orderDate"><%=orderDate %></td>
                    <td class="orderValue"><%=orderValue %></td>
                    <td class="custCity"><%=city %></td>
                    <td class="status"><%=status %></td>

                </tr>
                <%} %>
            
            </tbody>
        </table>
    </div>
    </div>

    <hr id="h">

    <div id="displayInvoiceHere"></div><br>
    <div style="padding-left: 20px;">
        <a href="#" target="_blank"> <button type="button" id="displayInvoice" onlick="onLoad()"
                class="btn btn-danger">View Invoice</button></a>
    </div><br>
    <a href="./getAddQuote" style="padding-left: 20px;">Add New Quote</a>
    <br>
    <a href="./product" target="_blank" style="padding-left: 20px;">Import Products</a>
    <br><br>

    <div class="navbar navbar-dark bg-dark footer-content-outer-div">
        <footer>
            <div class="footer-text">
               Copyright &#169; 2020 | CodeFury | codeToDecode | All rights reserved | Handcrafted with &#10084;&#65039;
            </div>
        </footer>
    </div>

</body>
</html>