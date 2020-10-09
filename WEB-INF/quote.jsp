<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="test" uri="http://rupali.com"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<script>
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function () {
    	if (xhr.readyState == 4 && xhr.status == 200) {
        document.getElementById("quoteUserId").innerHTML = xhr.responseText;
   		}
    	document.getElementById('quoteUserId').innerHTML = 'customerId';
   
		xhr.open('POST', 'http://localhost:8080/orderProcessing/ProductQuote1.java?customerId=&quoteUserId', true);
		xhr.send();
	}
</script>

<div id="autoPopulationData">
	<% 
	Customer c = ((Customer)request.getAttribute("customer")); 
	%>
</div>

<script>
		//Next xhr request part!
		function ajaxCall(){
		var xhr1 = new XMLHttpRequest();
		xhr1.onreadystatechange = function () {
                xhr1.open('POST', 'http://localhost:8080/orderProcessing/ProductQuote1.java?productIds=&s', true);
                xhr1.send();
              }
		}
</script>


<script>
		//Next xhr request part!
		var xhr2 = new XMLHttpRequest();
		xhr2.onreadystatechange = function () {
    	if (xhr2.readyState == 4 && xhr2.status == 200) {
        document.getElementById("quoteUserId").innerHTML = xhr2.responseText;
    	}
    	document.getElementById('quoteUserId').innerHTML = 'customerId';
   		// document.getElementById('file-form').reset();
		}
		xhr2.open('POST', 'http://localhost:8080/orderProcessing/ProductQuote1.java?orderDate=&quoteOrderDateproduct?customerId=&quoteUserId?gstNumber=&quoteGSTNumber?shippingAdress=&quoteShippingAddress?city=quoteCity?phone=&quotePhoneNumber?email=&quoteEmail?pincode=&quotePincode?productsIds=&s?totalOrderValue=&totalOrderValue?shippingCost=&shippingCost', true);
		xhr2.send();
</script>



<body>
	   <form>
        
        <div class="form-group row">
        <label for="quoteOrderDate" class="col-sm-2 col-form-label">Order Date:</label>
            <div class="col-sm-10">
            <input type="date" class="form-control" id="quoteOrderDate" aria-describedby="dateHelp" placeholder="Order Date" min=<%=new java.util.Date() %>>
            </div>
        </div>
                
        <div class="form-group row">
          <label for="quoteUserId" class="col-sm-2 col-form-label">User Id:</label>
          <div class="col-sm-10">
          <input type="text" class="form-control" id="quoteUserId" placeholder="User Id" onChange="cidAjaxCall()">
          </div>
        </div>       
                

        <div class="form-group row">
          <label for="quoteGSTNumber" class="col-sm-2 col-form-label">GST Number:</label>
          <div class="col-sm-10">
          <input type="text" class="form-control" id="quoteGSTNumber"  placeholder="GST Number" value=<%=c.getGSTNumber() %> disabled>
          </div>
        </div>
        
        
        <div class="form-group row">
          <label for="quoteShippingAddress"class="col-sm-2 col-form-label">Shipping Address:</label>
          <div class="col-sm-10">
          <input type="text" class="form-control" id="quoteShippingAddress" placeholder="Shipping Address" value=<%=c.getAddress() %> disabled>
          </div>
        </div>
                 

        <div class="form-group row">
        <label for="quoteCity" class="col-sm-2 col-form-label">City:</label>
          <div class="col-sm-10">
          <input type="text" class="form-control" id="quoteCity" placeholder="City" value=<%=c.getCity() %> disabled>
          </div>
        </div>
                    

        <div class="form-group row" >
          <label for="quotePhoneNumber" class="col-sm-2 col-form-label">Phone Number:</label>
          <div class="col-sm-10">
          <input type="text" class="form-control" id="quotePhoneNumber" placeholder="Phone Number" value=<%=c.getPhoneNumber() %> disabled>
          </div>
        </div>
                  
      
        <div class="form-group row">
          <label for="quoteEmail" class="col-sm-2 col-form-label">E-mail:</label>
          <div class="col-sm-10">
          <input type="email" class="form-control" id="quoteEmail"  placeholder="E-mail" value=<%=c.getEmail() %> disabled>
          </div>
          
        </div>
                  

        <div class="form-group row">
          <label for="quotePincode" class="col-sm-2 col-form-label">Pincode:</label>
          <div class="col-sm-10">
          <input type="text" class="form-control" id="quotePincode" placeholder="Pincode" value=<%=c.getPincode() %> disabled>
          </div>
        </div>
                  
      
        <div class="form-group row">
            <div class="col-sm-10 offset-sm-2">
            <button type="submit" id="addMoreProductsButton" onMouseMove="myFunction()" class="btn btn-primary">Add More Products</button>
            </div>
        </div>
                
                
              
               
	    <div id="addMoreProductsDiv" style="display:none;" >
        <h3>Available products are:</h3>
        <test:tag1 query="Select * from admin.productdetails" database="jdbc:derby:C:\Users\RUPALI TRIPATHI\MyDB;create=true" username="admin" password="derby"></test:tag1>
      </div>

		  <div class="form-group row">
            <div class="col-sm-10 offset-sm-2">
                <button type="submit" id="computeCosts" onClick="ajaxCall()" class="btn btn-primary">Compute Costs</button>
            </div>
      </div>
                
      
      <div id="calculatedCosts">
        <input type="text" id="totalOrderValue" name="" value=<%=request.getAttribute("totalOrderValue") %>>
        <input type="text" id="shippingCost" name="" value=<%=request.getAttribute("shippingCost") %>>
      </div>
       
   
 </form>
 
	<input type="submit" id="generatequoteButton" onClick="onClickGenerateQuoteButton()">Generate Quote</>
              
<script>
	function myFunction() {
	document.getElementById('addMoreProductsDiv').style.display = "block";
	}
</script>
		
</body>
</html>