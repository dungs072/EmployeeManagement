<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/AdminHeader.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">
<title>ADMIN</title>
<style>
/* Set a fixed scrollable wrapper */
.tableWrap {
	margin-top: 40px;
	height: 450px;
	border-radius: 10px;
	overflow: auto;
}
.tableShift{
	margin-top: 40px;
	height: 280px;
	border: 2px solid black;
	overflow: auto;
}

/* Set header to stick to the top of the container. */
thead tr th {
	position: sticky;
	top: 0;
	color: aliceblue;
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
	background: #4e73df;
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
		$(document).on('click', ".deleteJob", function(e) {

			var yesButton = $(document).find('.yes-job-warning')
			yesButton.val($(this).val())
		});
		$(document).on('click', ".deleteFault", function(e) {
			var yesButton = $(document).find('.yes-fault-warning')
			yesButton.val($(this).val())
		});

		$(".detailJob").click(function() {
			localStorage.setItem("isClickedJobInfor", "true");
			var saveButton = $(document).find('.saveJobUpdate');
			saveButton.val($(this).val());
		});
		$(".detailFault").click(function() {
			localStorage.setItem("isClickedFaultInfor", "true");
			var saveButton = $(document).find('.saveFaultUpdate');
			saveButton.val($(this).val());
		});
		
		$('.settingShift').click(function(){
			var val = $(this).val().split(",");
			var shiftInput = $(document).find('.shift');
			var desInput= $(document).find('.description');
			shiftInput.val(val[0]);
			desInput.val(val[1]);
		});
		$(window).on('load', function() {
			var jobValue = localStorage.getItem("isClickedJobInfor");
			if (jobValue == "true") {
				$("#jobDetailModal").modal("show");
				localStorage.setItem("isClickedJobInfor", "false");
			}

			var faultValue = localStorage.getItem("isClickedFaultInfor");
			if (faultValue == "true") {
				$("#faultDetailModal").modal("show");
				localStorage.setItem("isClickedFaultInfor", "false");
			}

		});

	});
</script>
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
						<a style = "text-decoration: none" href = "Recruit.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-people-fill nav-img" viewBox="0 0 16 16">
  							<path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/>
							</svg>
							Employees
							</h5>
						</a>
					</div>

					<div class="nav-option option4">
						<a style = "text-decoration: none" href = "salary.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-bank2 nav-img" viewBox="0 0 16 16">
 							<path d="M8.277.084a.5.5 0 0 0-.554 0l-7.5 5A.5.5 0 0 0 .5 6h1.875v7H1.5a.5.5 0 0 0 0 1h13a.5.5 0 1 0 0-1h-.875V6H15.5a.5.5 0 0 0 .277-.916l-7.5-5zM12.375 6v7h-1.25V6h1.25zm-2.5 0v7h-1.25V6h1.25zm-2.5 0v7h-1.25V6h1.25zm-2.5 0v7h-1.25V6h1.25zM8 4a1 1 0 1 1 0-2 1 1 0 0 1 0 2zM.5 15a.5.5 0 0 0 0 1h15a.5.5 0 1 0 0-1H.5z"/>
							</svg>Paying
							</h5>
						</a>
					</div>

					<div class="nav-option option5">
						<a style = "text-decoration:none" href = "DisplayStaffMistake.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-view-list nav-img" viewBox="0 0 16 16">
  							<path d="M3 4.5h10a2 2 0 0 1 2 2v3a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-3a2 2 0 0 1 2-2zm0 1a1 1 0 0 0-1 1v3a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1v-3a1 1 0 0 0-1-1H3zM1 2a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13A.5.5 0 0 1 1 2zm0 12a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13A.5.5 0 0 1 1 14z"/>
							</svg>Violations
							</h5>
						</a>
					</div>

					<div class="nav-option option6">
						<a style = "text-decoration:none" href = "JobAndFault.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-gear-fill nav-img" viewBox="0 0 16 16">
  							<path d="M9.405 1.05c-.413-1.4-2.397-1.4-2.81 0l-.1.34a1.464 1.464 0 0 1-2.105.872l-.31-.17c-1.283-.698-2.686.705-1.987 1.987l.169.311c.446.82.023 1.841-.872 2.105l-.34.1c-1.4.413-1.4 2.397 0 2.81l.34.1a1.464 1.464 0 0 1 .872 2.105l-.17.31c-.698 1.283.705 2.686 1.987 1.987l.311-.169a1.464 1.464 0 0 1 2.105.872l.1.34c.413 1.4 2.397 1.4 2.81 0l.1-.34a1.464 1.464 0 0 1 2.105-.872l.31.17c1.283.698 2.686-.705 1.987-1.987l-.169-.311a1.464 1.464 0 0 1 .872-2.105l.34-.1c1.4-.413 1.4-2.397 0-2.81l-.34-.1a1.464 1.464 0 0 1-.872-2.105l.17-.31c.698-1.283-.705-2.686-1.987-1.987l-.311.169a1.464 1.464 0 0 1-2.105-.872l-.1-.34zM8 10.93a2.929 2.929 0 1 1 0-5.86 2.929 2.929 0 0 1 0 5.858z"/>
							</svg>Setting
							</h5>
						</a>
					</div>
					<div class="nav-option option7">
						<a style = "text-decoration:none" href = "EnabledStaff.htm">
							<h5>
							<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-toggles nav-img" viewBox="0 0 16 16">
  							<path d="M4.5 9a3.5 3.5 0 1 0 0 7h7a3.5 3.5 0 1 0 0-7h-7zm7 6a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5zm-7-14a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zm2.45 0A3.49 3.49 0 0 1 8 3.5 3.49 3.49 0 0 1 6.95 6h4.55a2.5 2.5 0 0 0 0-5H6.95zM4.5 0h7a3.5 3.5 0 1 1 0 7h-7a3.5 3.5 0 1 1 0-7z"/>
							</svg>Enable
							</h5>
						</a>
					</div>
					<div class="nav-option option8">
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

		<div class="row">
			<div class="col-6">
				<div class="tableWrap">
					<table class="employeeTable">
						<thead>
							<tr>
								<th><span>STT</span></th>
								<th><span>Job type</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="job" varStatus="i" items="${jobs}">
								<tr>
									<td>${i.count}</td>
									<td>${job.TENVITRI}</td>
									<td>
										<form action="JobAndFault/ShowJob.htm" method="get">

											<c:choose>
												<c:when test="${job.canUpdate==true}">
													<button type="submit" name="InforJob"
														class="btn btn-secondary detailJob" value="${job.MACV}"><i class="fa fa-info-circle" aria-hidden="true"></i></button>
												</c:when>
												<c:otherwise>

												</c:otherwise>
											</c:choose>

											<c:choose>

												<c:when test="${job.canDelete==true}">
													<button type="button" name="deleteJob"
														class="btn btn-danger deleteJob" value="${job.MACV}"
														data-bs-toggle="modal" data-bs-target="#jobWarning"><i class="fa fa-trash" aria-hidden="true"></i>
													</button>
												</c:when>
												<c:otherwise>
													
												</c:otherwise>
											</c:choose>


										</form>


									</td>
								</tr>
							</c:forEach>

							<tr class="footButton">
								<td colspan="3">
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
								<th><span>STT</span></th>
								<th><span>Fault type</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="fault" varStatus="i" items="${faults}">
								<tr>
									<td>${i.count}</td>
									<td>${fault.MOTA}</td>
									<td>
										<form action="JobAndFault/ShowFault.htm" method="get">
											<button type="submit" name=InforFault
												class="btn btn-secondary detailFault" value="${fault.IDLOI}"><i class="fa fa-info-circle" aria-hidden="true"></i></button>
											<c:choose>

												<c:when test="${fault.canDelete==true}">
													<button type="button" name="deleteFaults"
														class="btn btn-danger deleteFault" value="${fault.IDLOI}"
														data-bs-toggle="modal" data-bs-target="#faultWarning"><i class="fa fa-trash" aria-hidden="true"></i>
													</button>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>

										</form>

									</td>
								</tr>
							</c:forEach>

							<tr class="footButton">
								<td colspan="3">
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
		<div class="row mt-2 justify-content-md-center">
			<div class="col-6">
				<div class="tableShift">
					<table class="shiftTable">
						<thead>
							<tr>
								<th><span>Shift</span></th>
								<th><span>Description</span></th>
								<th><span>Action</span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="shift" varStatus="i" items="${shifts}">
								<tr>
									<td>${shift.IDCA}</td>
									<td>${shift.TENCA}</td>
									<td>
										<button type="button" name="settingShift"
											class="btn btn-secondary settingShift" value="${shift.IDCA},${shift.TENCA}"
											data-bs-toggle="modal" data-bs-target="#settingShift"><i class="fa fa-cog" aria-hidden="true"></i>
										</button>
									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
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
	
	<!--shift-->
	<div class="modal fade" id="settingShift" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Setting shift</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="JobAndFault/ShiftSetting.htm" method="get">
						<div class="form-group">
							<label for="firstname">Shift:</label> <input type="text"
								class="form-control shift"
								name="ShiftId" value="" maxlength="50" readonly />
							<label for="firstname">Description:</label> <input type="text"
								class="form-control description" placeholder="typing your description"
								name="Description" value="" maxlength="50" />
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
	<!-- Job detail modal -->
	<div class="modal fade" id="jobDetailModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Update job</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="JobAndFault/UpdateJob.htm" method="get">
						<div class="form-group">
							<label for="firstname">Job title:</label> <input type="text"
								class="form-control username" id="TENVITRI" placeholder="...."
								name="updateTENVITRI" value="${showJob.TENVITRI}" maxlength="30" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary saveJobUpdate"
								data-bs-dismiss="modal" name="updateJobId"
								value="${showJob.MACV}">Save changes</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!-- Fault detail modal -->
	<div class="modal fade" id="faultDetailModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Update
						fault</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="JobAndFault/UpdateFault.htm" method="get">
						<div class="form-group">
							<label for="firstname">Fault title:</label> <input type="text"
								class="form-control username" id="MOTA" placeholder="...."
								name="updateMOTA" value="${showFault.MOTA}" maxlength="30" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary saveFaultUpdate"
								data-bs-dismiss="modal" name="updateFaultId"
								value="${showFault.IDLOI}">Save changes</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!--warning Modal-->
	<!-- delete job -->
	<div class="modal" tabindex="-1" id="jobWarning">
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
					<form action="JobAndFault/DeleteJob.htm" method="get">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<button type="submit" class="btn btn-primary yes-job-warning"
							data-bs-dismiss="modal" name="yes-job-warning">Yes</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- delete fault -->
	<div class="modal" tabindex="-1" id="faultWarning">
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
					<form action="JobAndFault/DeleteFault.htm" method="get">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<button type="submit" class="btn btn-primary yes-fault-warning"
							data-bs-dismiss="modal" name="yes-fault-warning">Yes</button>
					</form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>