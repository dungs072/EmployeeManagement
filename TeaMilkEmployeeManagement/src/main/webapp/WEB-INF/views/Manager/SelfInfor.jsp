<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/ManagerHeader.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">
<title>Your information</title>
</head>
<body>
	<div class="main-container">
		<div class="navcontainer">
			<nav class="nav">
				<div class="nav-upper-options">
					<div class="nav-option option1">
						<img
							src="https://media.geeksforgeeks.org/wp-content/uploads/20221210182148/Untitled-design-(29).png"
							class="nav-img" alt="dashboard">
					</div>

					<div class="option2 nav-option">
						<a style="text-decoration: none" href="home.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-house-door-fill nav-img" viewBox="0 0 16 16">
  								<path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5Z"/>
							</svg>
							Home
							</h5>
						</a> 

					</div>

					<div class="nav-option option3">
						<a style = "text-decoration: none" href = "ManagerRegistration.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-calendar2-plus-fill nav-img" viewBox="0 0 16 16">
  							<path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM2 3.5v1c0 .276.244.5.545.5h10.91c.3 0 .545-.224.545-.5v-1c0-.276-.244-.5-.546-.5H2.545c-.3 0-.545.224-.545.5zm6.5 5a.5.5 0 0 0-1 0V10H6a.5.5 0 0 0 0 1h1.5v1.5a.5.5 0 0 0 1 0V11H10a.5.5 0 0 0 0-1H8.5V8.5z"/>
							</svg>
							Registration
							</h5>
						</a>
					</div>

					<div class="nav-option option4">
						<a style = "text-decoration:none" href = "DisplayStaffMistake.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-view-list nav-img" viewBox="0 0 16 16">
  							<path d="M3 4.5h10a2 2 0 0 1 2 2v3a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-3a2 2 0 0 1 2-2zm0 1a1 1 0 0 0-1 1v3a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1v-3a1 1 0 0 0-1-1H3zM1 2a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13A.5.5 0 0 1 1 2zm0 12a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13A.5.5 0 0 1 1 14z"/>
							</svg>Violations
							</h5>
						</a>
					</div>

					<div class="nav-option option5">
						<a style = "text-decoration:none" href = "ChangePassword.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-person-fill-lock nav-img" viewBox="0 0 16 16">
  							<path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm-9 8c0 1 1 1 1 1h5v-1a1.9 1.9 0 0 1 .01-.2 4.49 4.49 0 0 1 1.534-3.693C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Zm7 0a1 1 0 0 1 1-1v-1a2 2 0 1 1 4 0v1a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1h-4a1 1 0 0 1-1-1v-2Zm3-3a1 1 0 0 0-1 1v1h2v-1a1 1 0 0 0-1-1Z"/>
							</svg>Password
							</h5>
						</a>
					</div>
					
					<div class="nav-option option6">
						<a style = "text-decoration:none" href = "SelfInfor.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-person-fill nav-img" viewBox="0 0 16 16">
  							<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3Zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z"/>
							</svg>Profile
							</h5>
						</a>
					</div>

					<div class="nav-option logout">
						<a style = "text-decoration:none" href = "Logout.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-box-arrow-left nav-img" viewBox="0 0 16 16">
  							<path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
  							<path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
							</svg>Logout
							</h5>
						</a>
					</div>

				</div>
			</nav>
		</div>
		<div class="main">
	<div class="container">
		<div class="row align-items-center" style="height: 100vh;">
			<div class="mx-auto col-10 col-md-8 col-lg-6">
				<!-- Form -->
				<form:form class="form-example" action="SelfInfor/update.htm" method="get" modelAttribute = "staff">
					<h1>Your Information</h1>
					<p class="description">Add information about yourself.</p>
					<!-- Input fields -->
					<div class="form-group">
						<label for="firstname">First name:</label> <form:input path = "HO" type="text"
							class="form-control username" id="firstname"
							placeholder="First name..." name="firstname"
							maxlength="30" />
					</div>
					<div class="form-group">
						<label for="lastname">Last name:</label> <form:input path = "TEN" type="text"
							class="form-control username" id="lastname"
							placeholder="Last name..." name="lastname"
							maxlength="30" />
					</div>
					<div class="form-group">
						<label for="birthday">Birthday:</label> <input type="date"
							class="form-control username" id="birthday" 
							placeholder="00/00/0000..." name="birthday"
							value="${staff.NGAYSINH}" />
					</div>
					<c:if test="${staff.GIOITINH=='Nam'}">
						<div class="form-check">
							<input class="form-check-input" type="radio" name="GIOITINH" checked
								id="gender1" value = "Nam"> <label class="form-check-label"
								for="flexRadioDefault1"> Male </label>
							
						</div>
						<div class="form-check">
						<input class="form-check-input" type="radio" name="GIOITINH"
							id="gender2" value = "Nữ"> <label class="form-check-label"
							for="flexRadioDefault2"> Female </label>
					</div>
					</c:if>
					
					<c:if test="${staff.GIOITINH=='Nữ'}">
						<div class="form-check">
							<input class="form-check-input" type="radio" name="GIOITINH"
								id="gender1" value = "Nam"> <label class="form-check-label"
								for="flexRadioDefault1"> Male </label>
							
						</div>
						<div class="form-check">
						<input class="form-check-input" type="radio" name="GIOITINH"
							id="gender2" checked value = "Nữ"> <label class="form-check-label"
							for="flexRadioDefault2"> Female </label>
					</div>
					</c:if>

					

					<div class="form-group">
						<label for="idcard">Identification card number:</label> <form:input path = "CCCD"
							type="text" class="form-control username" id="idcard"
							name="idcard"
							oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
							maxlength="12" />
					</div>

					<div class="form-group">
						<label for="phoneNumber">Phone number:</label> <form:input type="text" path = "SDT"
							class="form-control username" id="phoneNumber" name="phoneNumber"
							oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
							maxlength="10" />
					</div>
					<div class="form-group">
						<label for="email">Email:</label> <form:input type="email" path = "EMAIL"
							class="form-control username" id="email"
							placeholder="name123@gmail.com" name="email"
							maxlength="50" />
					</div>

					<div class="form-group">
						<label for="address">Address:</label> <form:input type="text" path = "DIACHI"
							class="form-control username" id="adderess" name="address"
							value="" maxlength="50" />
					</div>
					
					<button type="submit" class="btn btn-primary btn-customized mt-4">
						Save</button>
				</form:form>
				<!-- Form end -->
			</div>
		</div>
	</div>
	</div>
	</div>
</body>
</html>