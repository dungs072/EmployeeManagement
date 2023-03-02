<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/headerAfterLogin.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">
<title>Show Mistake of Staff</title>
<style>
/* Set a fixed scrollable wrapper */
.tableWrap {
	margin-top: 50px;
	height: 400px;
	border: 2px solid black;
	overflow: auto;
	border: 1px solid black;
	border-radius: 10px;
}

/* Set header to stick to the top of the container. */
thead tr th {
	color: aliceblue;
	position: sticky;
	top: 0;
}

td {
	text-align: center;
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
	background: #6B5B95;
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

<script type="text/javascript">
$(window).on('load', function() {

	var value = localStorage.getItem("isClickedView");
	if (value == "true") {
		$('.saveChangesAddStaff').val(localStorage.getItem("viewValue"));
		$('#showStaffMistake').modal('show');
		localStorage.setItem("isClickedView", "false");
	}


});
$(document).on('click', ".viewButton", function(e) {
	localStorage.setItem("viewValue", $(this).val());
	localStorage.setItem("isClickedView", "true");
})
</script>
</head>
<body>
	<div class="container">
		<div class="row justify-content-md-center">
			<div class="col-10">
				<div class="tableWrap">
					<table class="employeeTable">
						<thead>
							<tr>
								<th><span>STT</span></th>
								<th><span>Name</span></th>
								<th><span>Phone Number</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="staff" varStatus="i" items="${staffs}">
								<tr>
									<td>${i.count}</td>
									<td>${staff.HO} ${staff.TEN}</td>
									<td>${staff.SDT}</td>
									<td>
										<form action = "DisplayStaffMistake/ShowMistake.htm" method = "get">
											<button type="submit" class="btn btn-success viewButton" name = "staffId" value = "${staff.MANV}">View</button>
										</form>
										
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
	<!--ShowM-->
	<div class="modal fade" id="showStaffMistake" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Mistake</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<label for="exampleInputPassword1" class="form-label">Name</label> 
					<input type="text" class="form-control"
					id="settingToDoListModal" name="toDoListInput" readonly
					value = "${specificStaff.HO} ${specificStaff.TEN}">
					<form>
						<div class="tableWrap">
					<table class="employeeTable">
						<thead>
							<tr>
								<th><span>Violation Date</span></th>
								<th><span>Shift</span></th>
								<th><span>Times</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="mistakeHistory" varStatus="i" items="${mistakeHistoryList}">
								<tr>
									
									<td>${mistakeHistory.shiftDetailEntity.openshift.NGAYLAMVIEC}</td>
									<td>${mistakeHistory.shiftDetailEntity.openShift.shift.IDCA}</td>
									<td>${mistakeHistory.SOLANVIPHAM}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>