<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Login</title>
   <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
</head>
<style>
 
body{
    margin: 0;
    padding: 0;
    font-family: sans-serif;
    background: url("https://static.wixstatic.com/media/5673b6_2bb9e73b905e405b8d5826cffb95a226~mv2.jpg");
    background-size: cover;
    background-color: rgba(0,0,0,1);
   
    

}
#customerPassword{
background-image: url("passwordicon.svg");
position: left;
padding-left: 30px;
background-size: 20px;
background-repeat: no-repeat;
border-radius: 8px;
}
#customerId{
    background-image: url("usericon.svg");
position: left;
padding-left: 30px;
background-size: 20px;
background-repeat: no-repeat;
border-radius: 8px;
    
}
.login-box{
    width: 280px;
    position: absolute;
    top: 55%;
    left: 50%;
    transform: translate(-50%,-50%);
    color:#000000;
}
.login-box h1{
    float: left;
    font-size: 30px;
    border-bottom: 6px solid #000000;
    margin-bottom: 30px;
    padding: 13px 0;
}
.textbox{
    width: 100%;
    overflow: hidden;
    font-size: 20px;
    color: #000000;
    padding: 8px 2px;
    margin: 8px 0;
    border-bottom: 1px solid;    
}
.textbox i{
    width: 26px;
    float: left;
    text-align: center;
}
.textbox input{
    border: none;
    background: none;
    outline: none;
    color:#000000;
    font-size: 18px;
    width: 200px;
    float: left;
    margin-left: 10px;
    margin-right: 10px;

}
.customerbutton{
    width: 50%;
    background: none;;
    border: 4px solid #000000;
    cursor: pointer;
    color:#000000;
    padding: 10px;
    margin: 15px;
    font-size: 18px;
    transform: translate(35%,20%);
   
}
::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
    color:black;
    opacity: 1; /* Firefox */
  }
  
  :-ms-input-placeholder { /* Internet Explorer 10-11 */
    color: black;
  }
  
  ::-ms-input-placeholder { /* Microsoft Edge */
    color: black;
  }
  .footer {
   position: fixed;
   left: 0;
   bottom: 0;
   width: 100%;
   background-color:#343a40;
   color: white;
   text-align: center;
}
</style>
<body>
	<%
		String loginMessage = "";
		loginMessage = (String)request.getAttribute("loginMessage");
	%>
    <nav class="navbar navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><img
                src="data:image/png;base64,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"
                width="30" height="30" alt="Blogs Home">HOME</a>
    </nav>
    <div class="login-box">
        <h1>Customer Login</h1>
        <form action="./customerLogin" class ="customerLoginForm" method="POST">
            <div class="textbox">
               
            <label for ="customerId"></label>
            <input type="number" placeholder="CustomerId" id="customerId" name="customerId" required>
            </div>
            <div class="textbox">
               
                <label for ="customerPassword"></label>
                 <input type="password" placeholder="Password"id="password" name="password" required>
            </div>
           <div>
            <input class="customerbutton" type="submit" value="Log In">
            </div>
        </form>
        <label id = "loginMessage" style="color:red"></label>
    </div>
    <div class="footer">
        
        <br>
        <u><span>CONTENT CREATORS: WFS7-codeToDecode</span></u>
        <div></div>
        <br>
    
</div>
<script type="text/javascript">
	var loginMessage = '<%=loginMessage%>';	
	if( loginMessage!='null'){
		 document.getElementById('loginMessage').innerHTML = loginMessage;
	}
	</script>

</body>

</html>
