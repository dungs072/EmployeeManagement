<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Password</title>
<%@include file="/WEB-INF/views/include/headerAfterLogin.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">

<style>
	.eye-Icon{
		position:absolute;
		right:5px;
		top:8px;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="row justify-content-md-center">
			<div class="col-5 mt-5">
				<div class="card card-outline-secondary">
					<div class="card-header">
						<h3 class="mb-0">Change Password</h3>
					</div>
					<div class="card-body">
						<form class="form" role="form" autocomplete="off">
							<div class="form-group">
								<label for="inputPasswordOld">Current Password</label> <input
									type="password" class="form-control" id="inputPasswordOld"
									required="">
								<div>${oldPasswordMessage}</div>
							</div>
							<div class="form-group">
								<label for="inputPasswordNew">New Password</label>
								<div class="col">
									<div class="input-group" id="show_hide_password">
										<input class="form-control" type="password" maxlength="20">
										<div>${newPasswordMessage}</div>
										<div class="input-group-addon eye-Icon">
											<a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
										</div>
										<span
										class="form-text small text-muted"> The password must be less than 20 characters, 
										and must not contain spaces. </span>
										
									</div>
								</div>
							</div>
								<div class="form-group">
									<label for="inputPasswordNewVerify">Verify</label> <input
										type="password" class="form-control" maxlength = "20"
										id="inputPasswordNewVerify" required=""> <span
										class="form-text small text-muted"> To confirm, type
										the new password again. </span>
										<div>${confirmPasswordMessage}</div>
								</div>
								<div class="form-group">
									<button type="submit"
										class="btn btn-success btn-lg float-right">Save</button>
								</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>