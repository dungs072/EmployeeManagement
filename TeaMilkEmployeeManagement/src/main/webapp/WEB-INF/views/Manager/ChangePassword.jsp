<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Password</title>
<%@include file="/WEB-INF/views/include/ManagerHeader.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">

<style>
.eye-Icon {
	position: absolute;
	right: 5px;
	top: 8px;
}

.InputInvalid {
	color: red;
	font-style: italic;
	margin-left: 5px;
	margin-top: 5px;
}

.resultText {
	color: #008000;
}
</style>
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
						<a style = "text-decoration:none" href = "SalaryHistory.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-cash-coin nav-img" viewBox="0 0 16 16">
							  <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"/>
							  <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z"/>
							  <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z"/>
							  <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z"/>
							</svg>Salary
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
		<div class="row justify-content-md-center">
			<div class="col-5 mt-5">
				<div class="card card-outline-secondary">
					<div class="card-header">
						<h3 class="mb-0">Change Password</h3>
					</div>
					<div class="card-body">
						<form class="form" role="form" autocomplete="off"
							action="ChangePassword/saveChange.htm" method="get">
							<div class="form-group">
								<label for="inputPasswordOld">Current Password</label> <input
									type="password" class="form-control" id="inputPasswordOld"
									required="" name="oldPassword"  pattern = "[a-zA-Z0-9]+">
								<div class = "InputInvalid">${oldPasswordMessage}</div>
							</div>
							<div class="form-group">
								<label for="inputPasswordNew">New Password</label>
								<div class="col">
									<div class="input-group" id="show_hide_password">
										<input class="form-control" type="password" maxlength="20"
											name="newPassword" required=""  pattern = "[a-zA-Z0-9]+">
										<div class = "InputInvalid">${newPasswordMessage}</div>
										<div class="input-group-addon eye-Icon">
											<a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
										</div>
										<span class="form-text small text-muted"> The password
											must be less than 20 characters, and must not contain spaces.
										</span>

									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputPasswordNewVerify">Verify</label> <input
									type="password" class="form-control" maxlength="20"
									id="inputPasswordNewVerify" required="" name="confirmPassword"  pattern = "[a-zA-Z0-9]+">
								<span class="form-text small text-muted"> To confirm,
									type the new password again. </span>
								<div class = "InputInvalid">${confirmPasswordMessage}</div>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-success btn-lg float-right">Save</button>
							</div>
							<div class="resultMessage">
								<p class ="resultText">${successMessage}</p>
								<p class="InputInvalid">${failureMessage}</p>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>
	</div>
	</div>
</body>
</html>