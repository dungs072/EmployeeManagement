<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/WEB-INF/views/include/header.jsp"%>
<title>Login</title>
<style>
* {
	padding: 0;
	margin: 0;
	box-sizing: border-box;
}

body {
	background: rgb(227, 235, 248);
}

.row {
	background: white;
	border-radius: 30px;
}

.img-responsive {
	width: 100%;
}

img {
	border-top-left-radius: 30px;
	border-bottom-left-radius: 30px;
}

.btn1 {
	border: none;
	outline: none;
	height: 50px;
	width: 100%;
	background-color: rgb(69, 69, 252);
	color: white;
	border-radius: 4px;
	font-weight: bold;
}

.btn1:hover {
	background-color: white;
	border: 1px solid;
	color: black;
}
.InputInvalid{
color:red;
font-style: italic;
margin-left:5px;
margin-top:5px;
}
</style>
<base href="${pageContext.servletContext.contextPath }/">
<title>Login</title>

<script>

$(document).on('click', "#Forgot", function(e) {
	alert("You should contact to employer to request to reset your password");
});
</script>
</head>
<body>
	<section class="Form my-4 mx-5">
		<div class="container">
			<div class="row no-gutters">
				<div class="col-lg-5">
					<img src="./images/teamilk.jpg" class="img-fluid" alt=""
						width="347px" height="200px"
						style="position: relative; right: 12px;">
				</div>

				<div class="col-lg-7 px-5 pt-5">
					<h1 class="font-weight-boild py-3">TéaMilk Employee Management</h1>
					<h4>Sign into your account</h4>
					<form action="CheckLogin.htm" method="post"
						class="needs-validation" novalidate>
						<div class="form-row">
							<div class="col-lg-7">
								<input name="username" type="text" placeholder="Username"
									class="form-control my-3 p-2" maxLength = "10"
									required>
								<div class = "InputInvalid">${UserNameMessage}</div>
								
							</div>
						</div>
						<div class="form-row">
							<div class="col-lg-7">
								<input name="password" type="password" placeholder="Password"
									class="form-control my-3 p-2" required maxLength = "25">
								<div class = "InputInvalid">${PasswordMessage}</div>
							</div>
						</div>
						<div class="form-row">
							<div class="col-lg-7">
								<button type="submit" class="btn1 mt-3 mb-2">Login</button>
							</div>
						</div>
						<a href="#" id = "Forgot">Forgot Password</a>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>