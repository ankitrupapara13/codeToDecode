<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  import ="java.util.ArrayList,com.hsbc.models.Customer,java.util.List,com.hsbc.models.OrderDetails"%>
<!DOCTYPE html>
<html lang="en">

	<%
        List<OrderDetails> list = new ArrayList();
        list = (List<OrderDetails>) request.getAttribute("orderList");
        Customer customer = (Customer)request.getAttribute("customerData");
        ArrayList<OrderDetails> completed= new ArrayList();
        ArrayList<OrderDetails> pendingList = new ArrayList();
        ArrayList<OrderDetails> approvedList = new ArrayList();
        for(OrderDetails a:list){
        	if(a.getStatus().toLowerCase().equals("approved")){
        		approvedList.add(a);
        	}else if(a.getStatus().toLowerCase().equals("completed")){
        		completed.add(a);
        	}else{
        		pendingList.add(a);
        	}
        }
	%>
	
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Customer</title>
    <script>
    	
        function toApprove(orderId) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                	if(xhr.responseText=="sessionexpired"){
                		alert("Session Expired");
                		document.location.href="home.html";
                	}else if(xhr.responseText=="error"){
                		alert("Order with orderID: "+orderId+" Unable to find the Order or Some Products in the order contact to our Customer Care Service");
                		document.getElementById("displayquote"+orderId).innerHTML = 'Technical Error';
                        document.getElementById("displayquote"+orderId).disabled = true; 
                	}else{//do what so ever you want to do
                		document.getElementById("displayquote"+orderId).innerHTML = xhr.responseText;
                        document.getElementById("displayquote"+orderId).disabled = true; 
                	}
                    
                }
                
            }
            xhr.open('GET', './approveOrder?orderId='+orderId, true);
            xhr.send();
        }
        function onLoad( orderId){
        	document.location.href="./getInvoice?orderId="+orderId;
        }
        
        
    </script>
    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="orderCustomer.css">
</head>

<body>

    <nav class="navbar fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAjVBMVEX////bABHZAADbAAn66Oj1zs7eJi/zv8HbAA7bAAD87e7aAAX+9vfiT1X30dP65OX98PH0w8X1ycvkXGH529zsk5ffMjn/+vvhSlDrjJDjVFnpgIT41tjoeHzncnbwq67ytLfvo6btm57cFB/qiIzlY2jmaW3iQEndHCffOD/fLDTxrrHqg4fndHnrkJN+XPi7AAAFmElEQVR4nO3ce1fbRhDG4ZWc2N4SmkATnNIWCLQFcun3/3iVfNNtZ/YdaVc6J+f9/c8ePWdMYkaWnWOMMcYYY4wxxhhjjDHGGGOMMcYYY4wxxhhjjDHGGGOMMcYYY4z9ZL27fpuv61+X5lXA502Zr5v7T4sDn17cQ7ktsuSf3VX5uDBw7deuIuYBvu5c9QL5sijwxa8qYZ4p+tfq5E1ZLDnF3doXe2GOKdYT3AuL5aZYA4/C9ER/UwP3wsWIu5cKeBKmJvqbw7F74ULE3dcaeBa6bymJxwmehEV5vxiwEaYk+q9H4Em4APEEbAnTERvgWTg7cXdzBLaF7j4NsQVshEX5bRlgR5iG6NcNsCWcl9gAu8IUxA6wLZyT+NoAe0L3ZSrRr9+1z2sLi/JhAWBfOJXoXzrArnA7E7EDHAinEXsT7AlnIj53gEPhFGJ/gn3hLMTuBEPC8cQhsC+cgdibYFDoHscRA8CBsCJusgI/94FB4Tiif/owPGkgzEwcTFAQjiH6p+EEQ8KsxOEEJaH7ZCUGJxgUZiSGgJLQShSAQWE2YhAoCt1/FqIvwsCwsCJeZQBeBoGy0EL0xS/CIWFhFqIAVIQ40W+FCYrCDEQJqAnd7xhRnqAsrIjXSYG3ElAVur8RYrmVgbIwMfFWvlJViBBLrwAVYVKiAowI48SL7Rvt5xVhQuJv2lVGhO67TrzwKlAVVsS3SYDaBONCnXihvkRjwkRTVCcICN0f8gGxCcaEFXH6LdQIEBDKxAv/PvazEWECYgyICCVifIJx4WTiP9F/7RFhmAhMEBBWxD+zAjGh+2t40AoBAsJJUwSAoHBIxICIcMIU/0Xec4HCPhEEQsJi68cRISAsdD/ax6FATFisRk0RA+LCNnHlP4I/hAkrInqgGWgQurvTkTgQFVqONAItwhPRcjWo0Ey8Qw82CQ/Hmq4FFhqJP+BzbcL6pWH7lcGFJiI+QauwItpeTQahgWgBWoXuzvbrYhHiRHCBNE54a9uRmYRe3xe0suyrjcLP3rYGtAilvXkowzLXJrz0xk2nQagtJacQTcL91tVExIV+hU+wDv5dtAiPa2XLdgUW2iZoIRqE57WygYgKL1ZWILivtghbNwZwIijU18rTiLCws5SEiZgwslaeRkSFvZUWusyFhMhKK1xkX20RDtbKIBERjgdCREwYWEpiCyRACO3sxJR9tUUY3LpCxLhwygQhIiIU1soIMSqcNkGECAjFpSSwBowJpwODy1ybUNm6xokRYQpgbIpRobpWjhJ1YRpgZIoxYWSlFVvmqkJ46zqJGBFGd3aRZa4mTAdUiboQWErqmylFaF+RasmrG1UIbXzUK5WFaYHKtWpC9MaAMkVRmBooExUhvLNTrlYSpgeK1ysLDUtJ+XoF4Zg7MfHCrzlRaNu6SsSwMMcERaIkjLwVQq85KFyNvCM6jigIjUDxP7eQMM9LVCSGhWagRAwI800wTAwK439WosTQ0wh5v0li8D46JBwFDL+PDjxRknOCIWJAOBIYJA6fCsr/XSC9P9iHQmR/BRMHT3bN8WUnXeJAOHqCQWL/6bx5vs2lQ+wLJ0zwQOzulXpPWKb5xGy89vKzJwTvBSjE7vq6LUz98XyQ2BVOnOCe2JliSzjfBLvEjtB0f1wktp9OaIRzTrDu8vz5ppYwCbB7K/AsnBvY3CtrCRMBK2Jzr+wknB94JjZC08NqEeJ5ikdhnofVMOJZmBDYIh6EywCPDySehOaHRmPED40w99PNOvEoTAw8fzimFi4H3BMPwuTA0xQr4ZLAmrgXjnwAP0Ksp7gplwU691yuMwEPxE25MNC516dcwJq4u1ocWP2l8ZjxmyFXjw9L+6rev8nZ0jrGGGOMMcYYY4wxxhhjjDHGGGOMMcYYY4wxxhhjjDHGGGNj+h/pT1sbioHmJgAAAABJRU5ErkJggg=="
                width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>


    <div style="margin-top: 7%;" class="container">

        <div class="jumbotron">
            <h1 class="display-4">Welcome,<span id="custName"><%=customer.getName() %></span></h1>
            <p class="lead">Upload Files!</p>
            <hr style=" border: 2px solid red;"><br>
        </div>

        <div class="custdetails">

            <p>Customer Name: <span class="blc" id="custName"><%=customer.getName() %></span></p>
            <p>Customer ID: <span class="blc" id="custId"><%=customer.getCustomerId() %></span></p>
            <p>GST: <span class="blc" id="custGst"><%=customer.getGstNumber() %></span></p>
            <p>Address: <span class="blc" id="custAdd"><%=customer.getAddress() %></span></p>
            <p>City: <span class="blc" id="custCity"><%=customer.getCity() %></span></p>
            <p>Email ID: <span class="blc" id="custEmail"><%=customer.getEmail() %></span></p>
            <p>Phone: <span class="blc" id="custPhone"><%=customer.getPhone()%></span></p>
            <p>Pincode: <span class="blc" id="custPin"><%=customer.getPincode()%></span></p>
            <p>Last Logged in at: <span class="blc" id="custLastLogin"><%=customer.getLastLogin() %></span></p>

        </div>

        <hr id="h">

        <p><b>Your Orders:</p></b>
        

        <div class="table-responsive-sm">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Order ID</th>
                        <th scope="col">Order Date</th>
                        <th scope="col">Shipping Cost</th>
                        <th scope="col">Total Order Value</th>
                        <th scope="col">Status</th>
                        <th scope="col">Invoice</th>
                    </tr>
                </thead>

                <tbody>
                	<%int x = 0; 
                	for(OrderDetails actor:completed) {
                		x++;
            		%>
            		
		            <tr>
		            	<th scope="row"><%=x %></th>
		                        <td id="orderId"><%=actor.getOrderId() %> </td>
		                        <td id="orderDate"><%= actor.getOrderDate()%></td>
		                        <td id="shippingCost"><%=actor.getShippingCost()%></td>
		                        <td id="orderValue"><%=actor.getTotalOrderValue() %></td>
		                        <td id="status"><%=actor.getStatus() %></td>
		                        <td id="invoice"><button type="button"
		                                    onclick="onLoad('<%=actor.getOrderId() %>')" class="btn btn-danger">View Invoice</button></a></td>
	               </tr>
		            <%
		            };
		            %>
                </tbody>
                 <tbody>
                	<% for(OrderDetails actor:approvedList) {
                		x++;
            		%>
            		
		            <tr>
		            	<th scope="row"><%=x %></th>
		                        <td id="orderId"><%=actor.getOrderId() %> </td>
		                        <td id="orderDate"><%= actor.getOrderDate()%></td>
		                        <td id="shippingCost"><%=actor.getShippingCost()%></td>
		                        <td id="orderValue"><%=actor.getTotalOrderValue() %></td>
		                        <td id="status"><%=actor.getStatus() %></td>
		                        
		            </tr>
		            <%
		            };
		            %>
                </tbody>


            </table>
        </div>

        <p><b>A List of Quotes Pending for Approval:</p></b>

        <div class="table-responsive-sm">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Order ID</th>
                        <th scope="col">Order Date</th>
                        <th scope="col">Shipping Cost</th>
                        <th scope="col">Total Order Value</th>
                        <th scope="col">Approval</th>
                    </tr>
                </thead>
				<tbody>
						
                	<%int y = 0; 
                	for(OrderDetails actor:pendingList) {
                		y++;	
            		%>
            		
		            <tr>
		            	<th scope="row"><%=y %></th>
		                        <td id="orderId"><%=actor.getOrderId() %> </td>
		                        <td id="orderDate"><%= actor.getOrderDate()%></td>
		                        <td id="shippingCost"><%=actor.getShippingCost()%></td>
		                        <td id="orderValue"><%=actor.getTotalOrderValue() %></td>
		                        <td id="approve"><button type="button" id="displayquote<%=actor.getOrderId() %>"
                                onclick="toApprove('<%=actor.getOrderId() %>')" class="btn btn-success">Approve</button></a> </td>
	               </tr>
		            <%
		            };
		            %>
                </tbody>
            </table>
        </div>




    </div>

    <div class="navbar navbar-dark bg-dark">
        <footer>
            <br>
            <strong><b><u><span style="color: black;">CONTENT CREATORS: WFS7-codeToDecode</span></strong></u></b>
            <div></div>
            <br>
        </footer>
    </div>
</body>

</html>