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

.footButton {
	position: sticky;
	bottom: 0;
	background: #ffffff;
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

.addBtn {
	width: 100%;
	height: 100%;
}
</style>
<script>
	var isClickedInfor = "";
	$(document).ready(function() {

		//call function
		$(document).on('click', ".deleteEmployee", function(e) {

			var yesButton = $(document).find('.yes-warning')
			yesButton.val($(this).val())
		});

		$(".detailEmployee").click(function() {
			localStorage.setItem("isClickedInfor", "true");
			var saveButton = $(document).find('.saveUpdate');
			saveButton.val($(this).val());
		});

		$(window).on('load', function() {
			var value = localStorage.getItem("isClickedInfor");
			if (value == "true") {
				$("#detailModal").modal("show");
				localStorage.setItem("isClickedInfor", "false");
			}

		});

	});
</script>
</head>
<body>
	<div class = "container">
		
	</div>
	<div class="container">
		<div class="row mt-3">
			<div class="col">
				<div class="search mb-2">

					<form action="Recruit/SearchStaff.htm" method="get">
						<input type="text" name="searchInput" placeholder="Search Job..">
						<button type="submit" class="btn btn-outline-dark">Search</button>
					</form>

				</div>

			</div>
			<div class="col">
				<div class="search mb-2">

					<form action="Recruit/SearchStaff.htm" method="get">
						<input type="text" name="searchInput" placeholder="Search fault..">
						<button type="submit" class="btn btn-outline-dark">Search</button>
					</form>

				</div>
			</div>

		</div>
		<div class="row mt-3">
			<div class="col-6">
				<div class="tableWrap">
					<table class="employeeTable">
						<thead>
							<tr>
								<th><span>Job type</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="job" varStatus="i" items="${jobs}">
								<tr>
									<td>${job.TENVITRI}</td>
									<td>
										<form action="Recruit/InforStaff.htm" method="get">
											<button type="submit" name="InforStaff"
												class="btn btn-secondary detailEmployee" value="${job.MACV}">Detail</button>
											<button type="button" name="deleteEmployee"
												class="btn btn-danger deleteEmployee" value="${job.MACV}"
												data-bs-toggle="modal" data-bs-target="#warning">Delete</button>
										</form>

									</td>
								</tr>
							</c:forEach>

							<tr class="footButton">
								<td colspan="2">
									<button type="button" class="btn btn-success addBtn"
										data-bs-toggle="modal" data-bs-target="#addJobModal">
										+</button>

								</td>

							</tr>

						</tbody>
					</table>
				</div>
			</div>
			<div class="col-6">
				<div class="tableWrap">
					<table class="employeeTable">
						<thead>
							<tr>
								<th><span>Fault type</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="fault" varStatus="i" items="${faults}">
								<tr>
									<td>${fault.MOTA}</td>
									<td>
										<form action="Recruit/InforStaff.htm" method="get">
											<button type="submit" name="InforStaff"
												class="btn btn-secondary detailEmployee"
												value="${fault.IDLOI}">Detail</button>
											<button type="button" name="deleteEmployee"
												class="btn btn-danger deleteEmployee" value="${fault.IDLOI}"
												data-bs-toggle="modal" data-bs-target="#warning">Delete</button>
										</form>

									</td>
								</tr>
							</c:forEach>

							<tr class="footButton">
								<td colspan="2">
									<button type="button" class="btn btn-success addBtn"
										data-bs-toggle="modal" data-bs-target="#addFaultModal">
										+</button>
								</td>

							</tr>

						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
	<!-- Modal -->
	<!-- addModal -->
	<!--job-->
	<div class="modal fade" id="addJobModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Add job</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="JobAndFault/AddJob.htm" method="get">
						<div class="form-group">
							<label for="firstname">Job type:</label> <input type="text"
								class="form-control username" placeholder="job title"
								name="TENVITRI" value="Server" maxlength="30" />
						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary"
								data-bs-dismiss="modal">Save changes</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!--fault-->
	<div class="modal fade" id="addFaultModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Add fault</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="JobAndFault/AddFault.htm" method="get">
						<div class="form-group">
							<label for="firstname">Fault type:</label> <input type="text"
								class="form-control username" placeholder="fault name"
								name="MOTA" value="Đi làm trễ" maxlength="50" />
						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary"
								data-bs-dismiss="modal">Save changes</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- detail Modal -->

	<div class="modal fade" id="detailModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Detail
						information</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="Recruit/UpdateStaff.htm" method="get"
						modelAttribute="staff">
						<div class="form-group">
							<label for="firstname">First name:</label> <input type="text"
								class="form-control username" id="firstname"
								placeholder="First name..." name="HO" value="${staff.HO}"
								maxlength="30" />
						</div>
						<div class="modal-footer">

							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary saveUpdate"
								data-bs-dismiss="modal" name="saveUpdate">Save changes</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!--warning Modal-->
	<!-- delete -->
	<div class="modal" tabindex="-1" id="warning">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Warning</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Are you sure about deleting it ?</p>
				</div>
				<div class="modal-footer">
					<form action="Recruit/DeleteStaff.htm" method="get">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<button type="submit" class="btn btn-primary yes-warning"
							data-bs-dismiss="modal" name="yes-warning">Yes</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!--cannot delete notification-->
	<div class="modal" id = "cannotDelete" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Notification</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Cannot delete this !!!</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">Save changes</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>