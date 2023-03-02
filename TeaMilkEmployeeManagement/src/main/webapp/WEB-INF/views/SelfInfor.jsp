<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/headerAfterLogin.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">
<title>Your information</title>
</head>
<body>
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
</body>
</html>