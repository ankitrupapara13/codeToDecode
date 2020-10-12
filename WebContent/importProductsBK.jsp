<%@page import="com.hsbc.dto.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Import Products</title>
    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="importProducts.css">
</head>

<body>

    <nav class="navbar fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
                src="data:image/png;base64,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"
                width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>


    <div style="margin-top: 7%;" class="container">
	
	<%
		EmployeeDTO employeeDTO = (EmployeeDTO)request.getAttribute("employee");
	%>


        <div class="jumbotron">
            <h1 class="display-4">Welcome,<span id="empName"><%=employeeDTO.getUserName()%></span></h1>
            <p class="lead">Upload Files!</p>
            <hr style=" border: 2px solid red;"><br>

        </div>
        <div class="row">

            <form id="file-form">
                <p style="color: black;">Enter JSON/XML file to upload:</p>

                <div class="input-group mb-3">
                    <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="file-input"
                            accept=".xml,.json">
                        <label id="file-input-label" class="custom-file-label" for="file-input">Choose file</label>
                    </div>
                    <div class="input-group-append">
                        <button id="file-submit" class="input-group-text"
                            class="btn btn-danger">Upload</button>

                    </div>
                </div>
            </form>
        </div>
        <div id="file-upload-information">
		
    	</div>

    </div>

    <br>
    <div class="navbar navbar-dark bg-dark footer-content-outer-div">
        <footer>
            <div class="footer-text">
               Copyright &#169; 2020 | CodeFury | codeToDecode | All rights reserved | Handcrafted with &#10084;&#65039;
            </div>
        </footer>
    </div>
	<script>
		document.getElementById('file-input').onchange = function(){
	        document.getElementById('file-input-label').innerHTML = this.files[0].name;
	    }
        document.getElementById('file-submit').addEventListener('click', function (event){
        	event.preventDefault();
        	this.disable = true;
          	var input = document.getElementById('file-input');
            if(input.files.length == 0){
            	alert('Please select a file');
            	document.getElementById("file-upload-information").innerHTML = "";
            	this.disable = false;
            	return;
            }
            
            var data = new FormData();
            data.append('file', input.files[0]);
          	        	
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                	if(xhr.responseText=="sessionexpired"){
                		alert("Session Expired");
                		document.location.href="home.html";
                	}else{
                		alert('File uploaded successfully')
                        document.getElementById("file-upload-information").innerHTML = xhr.responseText;
                	}
                	
                }
                document.getElementById('file-input-label').innerHTML = 'Choose file';
                document.getElementById('file-form').reset();
            }	
            xhr.open('POST', 'http://localhost:8090/orderProcessing/product', true);
            xhr.send(data);
            this.disable = false;
        })
 
    </script>
</body>

</html>