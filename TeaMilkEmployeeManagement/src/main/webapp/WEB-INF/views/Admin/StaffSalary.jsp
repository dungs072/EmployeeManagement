<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
<title>Staff's Salary</title>
<script>
	$(document).on('click',".BillSalaryButton", function(e){
		var datas = $(this).val().split('+');
		var maNV = $(document).find('.maNV');
		var hovaten = $(document).find('.hovaten');
		maNV.val(datas[0]);
		hovaten.val(datas[1]+ " " +datas[2]);
	})
</script>
</head>
<body>
	<h1>DANH SÁCH LƯƠNG</h1>

	<div class="row mt-3">
		<div class="col">
			<div class="tableWrap">
				<table class="employeeTable">
					<thead>
						<tr>
							<th><span>STT</span></th>
							<th><span>Employee Id</span></th>
							<th><span>Full Name</span></th>
							<th><span>Salary</span></th>
							<th><span>PrintSalaryBill</span></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="staff" varStatus="i" items="${salaryStaff}">
							<tr>
								<td>${i.count}</td>
								<td>${staff.MANV}</td>
								<td>${staff.HO}${staff.TEN}</td>
								<td><span>${staff.LUONGTICHLUY}</span></td>
								<td>
									<!-- Button trigger modal -->
									<button type="button" class="btn btn-primary BillSalaryButton"
										data-toggle="modal" data-target="#BillSalaryModal"
										value="${staff.MANV}+${staff.HO}+${staff.TEN}">PrintBill</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<form action="paySalary.htm">
		<div class="modal fade" id="BillSalaryModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h2 class="modal-title" id="exampleModalLabel">Salary Bill</h2>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="idStaff">ID Staff</label> <input class = "maNV" name="idStaff"
								id="idStaff" value="${staff.MANV}" readonly>
						</div>
						<div class="form-group">
							<label for="Name">Họ và tên</label> <input id="Name" class="hovaten"
								value="${staff.HO} ${staff.TEN}" readonly>
						</div>
						<div class="form-group">
							<label for="salary"> Lương nhận</label> <input class ="salary" name="salary"
								type="text" class="form-check-input" id="salary" value ="${staff.LUONGTICHLUY}" required>
						</div>
					</div>
					<div class = "modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">Save changes</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>