<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/headerAfterLogin.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">
<title>ADMIN</title>
<style>
/* Set a fixed scrollable wrapper */
.tableWrap {
	height: 390px;
	border: 2px solid black;
	overflow: auto;
}

/* Set header to stick to the top of the container. */
thead tr th {
	position: sticky;
	top: 0;
}

/* If we use border,
  we must use table-collapse to avoid
  a slight movement of the header row */
table {
	border-collapse: collapse;
}

/* Because we must set sticky on th,
   we have to apply background styles here
   rather than on thead */
th {
	padding: 16px;
	padding-left: 15px;
	border-left: 1px dotted rgba(200, 209, 224, 0.6);
	border-bottom: 1px solid #e8e8e8;
	background: #2970ff;
	text-align: center;
	/* With border-collapse, we must use box-shadow or psuedo elements
    for the header borders */
	box-shadow: 0px 0px 0 2px #e8e8e8;
}

/* Basic Demo styling */
table {
	width: 100%;
	font-family: sans-serif;
}

table td {
	padding: 16px;
	text-align: center;
}

tbody tr {
	border-bottom: 2px solid #e8e8e8;
}

thead {
	font-weight: 500;
	color: rgba(0, 0, 0, 0.85);
}

tbody tr:hover {
	background: #e6f7ff;
}
</style>
<script>
	var isClickedInfor = "";
	$(document).ready(function() {

						
						$('.enableButton').click(function(){
							$(document).find('.yes-warning').val($(this).val())
							localStorage.setItem("staffId",$(this).val());
						});
						
						$('.yes-warning').click(function(){
							
							localStorage.setItem("isClickedEnable","true");
							
						});

						$(window).on('load',function() {
								var enabledValue = localStorage.getItem("isClickedEnable");
								if (enabledValue == "true") {	
									$("#createdModal").modal("show");
									
									$(document).find('#username').val(localStorage.getItem("staffId"));
									localStorage.setItem("isClickedEnable","false");
								}

						});

	});
</script>
</head>
<body>
	<div class="container">
		<div class="row mt-3">
			<div class="col">
				<div class="search mb-3">

					<form action="EnabledStaff/SearchStaff.htm" method="get">
						<input type="text" name="searchInput"
							placeholder="Search employee..">
						<button type="submit" class="btn btn-outline-dark">Search</button>
					</form>

				</div>

			</div>
			<div class="col"></div>

		</div>
		<div class="row mt-3">
			<div class="col">
				<div class="tableWrap">
					<table class="employeeTable">
						<thead>
							<tr>
								<th><span>STT</span></th>
								<th><span>Employee Id</span></th>
								<th><span>Full Name</span></th>
								<th><span>Phone number</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="staff" varStatus="i" items="${staffs}">
								<tr>
									<td>${i.count}</td>
									<td>${staff.MANV}</td>
									<td>${staff.HO} ${staff.TEN}</td>
									<td><span>${staff.SDT}</span></td>
									<td>
										<button type="button" name="InforStaff"
												class="btn btn-success enableButton"
												data-bs-toggle="modal" data-bs-target="#warning"
												value="${staff.MANV}">Enable</button>
									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->


	<!-- successfully created account -->
	<div class="modal" tabindex="-1" id="createdModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">New account</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3 row">
						<label for="staticEmail" class="col-sm-2 col-form-label">Username</label>
						<div class="col-sm-10">
							<input type="text" readonly class="form-control-plaintext"
								id="username">
						</div>
					</div>
					<div class="mb-3 row">
						<label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value = "123" readonly>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<form action="Recruit/DeleteStaff.htm" method="get">
						<button type="button" class="btn btn-success"
							data-bs-dismiss="modal">Ok</button>
					</form>

				</div>
			</div>
		</div>
	</div>

	<!--warning Modal-->
	<!-- enable -->
	<div class="modal" tabindex="-1" id="warning">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">!!!</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Are you sure about enabling this account ?</p>
				</div>
				<div class="modal-footer">
					<form action="EnabledStaff/Enable.htm" method="get">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<button type="submit" class="btn btn-primary yes-warning"
							data-bs-dismiss="modal" name="yes-warning">Yes</button>
					</form>

				</div>
			</div>
		</div>
	</div>
</body>
</html>