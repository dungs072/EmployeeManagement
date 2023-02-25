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
	margin-top: 40px;
	height: 450px;
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
	<div class="container"></div>
	<div class="container">

		<div class="row mt-3">
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
														class="btn btn-secondary detailJob" value="${job.MACV}">Detail</button>
												</c:when>
												<c:otherwise>
													<button type="submit" name="InforJob"
														class="btn btn-secondary detailJob" value="${job.MACV}"
														disabled>Detail</button>
												</c:otherwise>
											</c:choose>

											<c:choose>

												<c:when test="${job.canDelete==true}">
													<button type="button" name="deleteJob"
														class="btn btn-danger deleteJob" value="${job.MACV}"
														data-bs-toggle="modal" data-bs-target="#jobWarning">Delete
													</button>
												</c:when>
												<c:otherwise>
													<button type="button" name="deleteJob"
														class="btn btn-danger deleteJob" value="${job.MACV}"
														data-bs-toggle="modal" data-bs-target="#jobWarning"
														disabled>Delete</button>
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
												class="btn btn-secondary detailFault" value="${fault.IDLOI}">Detail</button>
											<c:choose>

												<c:when test="${fault.canDelete==true}">
													<button type="button" name="deleteFaults"
														class="btn btn-danger deleteFault" value="${fault.IDLOI}"
														data-bs-toggle="modal" data-bs-target="#faultWarning">Delete
													</button>
												</c:when>
												<c:otherwise>
													<button type="button" name="deleteFault"
														class="btn btn-danger deleteFault" value="${fault.IDLOI}"
														data-bs-toggle="modal" data-bs-target="#faultWarning"
														disabled>Delete</button>
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