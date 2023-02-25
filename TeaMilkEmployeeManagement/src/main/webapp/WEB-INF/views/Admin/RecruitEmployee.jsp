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
		
		$(".addModalButton").click(function(){
			
			localStorage.setItem("isClickedAddStaffButton","true");
		});

		$(window).on('load', function() {
			var value = localStorage.getItem("isClickedInfor");
			if (value == "true") {
				$("#detailModal").modal("show");
				localStorage.setItem("isClickedInfor", "false");
			}
			var addValue = localStorage.getItem("isClickedAddStaffButton");
			if(addValue=="true"){
				$("#addModal").modal("show");
				localStorage.setItem("isClickedAddStaffButton", "false");
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

					<form action="Recruit/SearchStaff.htm" method="get">
						<input type="text" name="searchInput"
							placeholder="Search employee..">
						<button type="submit" class="btn btn-outline-dark">Search</button>
					</form>

				</div>
				<form action = "Recruit/ShowJobPosition.htm" method = "get">
					<button type="submit" class="btn btn-success addModalButton">Add an employee</button>
				</form>
				
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
										<form action="Recruit/InforStaff.htm" method="get">
											<button type="submit" name="InforStaff"
												class="btn btn-secondary detailEmployee"
												value="${staff.MANV}">Detail</button>
											<button type="button" name="deleteEmployee"
												class="btn btn-danger deleteEmployee" value="${staff.MANV}"
												data-bs-toggle="modal" data-bs-target="#warning">Delete</button>
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
	<!-- addModal -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Add
						employee</h5>
					<button type="button" class="close" data-bs-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="Recruit.htm" method="post">
						<div class="form-group">
							<label for="firstname">First name:</label> <input type="text"
								class="form-control username" id="firstname"
								placeholder="First name..." name="HO"
								maxlength="30" />
						</div>
						<div class="form-group">
							<label for="lastname">Last name:</label> <input type="text"
								class="form-control username" id="lastname"
								placeholder="Last name..." name="TEN" 
								maxlength="30" />
						</div>
						<div class="form-check">
							<input class="form-check-input" type="radio" name="GIOITINH"
								id="gender1" value="Nam"> <label
								class="form-check-label" for="flexRadioDefault1"> Male </label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="radio" name="GIOITINH"
								id="gender2" value="Nữ" checked> <label
								class="form-check-label" for="flexRadioDefault2"> Female
							</label>
						</div>
						<div class="form-group">
							<label for="idcard">Identification card number:</label> <input
								type="text" class="form-control username" id="idcard"
								name="CCCD"
								oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
								maxlength="12" />
						</div>

						<div class="form-group">
							<label for="phoneNumber">Phone number:</label> <input type="text"
								class="form-control username" id="phoneNumber" name="SDT"
								oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
								maxlength="10" />
						</div>
						<div class="form-group">
							<label for="address">Address:</label> <input type="text"
								class="form-control username" id="adderess" name="DIACHI"
								value="97 Man Thiện" maxlength="50" />
						</div>
						<div class="form-group">
							<label for="add-jobId">Job position:</label> <select
								name = "add-jobId" class="form-select" aria-label="Default select example">
								<c:forEach var="job" varStatus="i" items="${jobs}">
									<option value="${job.MACV}">${job.TENVITRI}</option>
								</c:forEach>

							</select>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" name="saveAddEmployee"
								class="btn btn-primary add-save" data-bs-dismiss="modal">Save
								changes</button>
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
						<div class="form-group">
							<label for="lastname">Last name:</label> <input type="text"
								class="form-control username" id="lastname"
								placeholder="Last name..." name="TEN" value="${staff.TEN}"
								maxlength="30" />
						</div>


						<c:set var="gender" scope="session" value="${staff.GIOITINH }" />
						<c:if test="${gender == 'Nam'}">

							<div class="form-check">
								<input class="form-check-input" type="radio" name="GIOITINH"
									id="gender1" value="Nam" checked> <label
									class="form-check-label" for="flexRadioDefault1"> Male
								</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="GIOITINH"
									id="gender2" value="Nữ"> <label
									class="form-check-label" for="flexRadioDefault2">
									Female </label>
							</div>
						</c:if>

						<c:if test="${gender == 'Nữ'}">

							<div class="form-check">
								<input class="form-check-input" type="radio" name="GIOITINH"
									id="gender1" value="Nam"> <label
									class="form-check-label" for="flexRadioDefault1"> Male
								</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="GIOITINH"
									id="gender2" value="Nữ" checked> <label
									class="form-check-label" for="flexRadioDefault2">
									Female </label>
							</div>
						</c:if>



						<div class="form-group">
							<label for="birthday">Birthday:</label> <input type="date"
								class="form-control username" id="birthday" name="birthday"
								value="${staff.NGAYSINH }" maxlength="12" />
						</div>
						<div class="form-group">
							<label for="idcard">Identification card number:</label> <input
								type="text" class="form-control username" id="idcard"
								name="CCCD" value="${staff.CCCD }"
								oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
								maxlength="12" />
						</div>

						<div class="form-group">
							<label for="phoneNumber">Phone number:</label> <input type="text"
								class="form-control username" id="phoneNumber" name="SDT"
								value="${staff.SDT }"
								oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
								maxlength="10" />
						</div>
						<div class="form-group">
							<label for="email">Email:</label> <input type="email"
								class="form-control username" id="email" name="EMAIL"
								value="${staff.EMAIL }" maxlength="50" />
						</div>

						<div class="form-group">
							<label for="address">Address:</label> <input type="text"
								class="form-control username" id="adderess" name="DIACHI"
								value="${staff.DIACHI }" maxlength="50" />
						</div>
						<div class="form-group">
							<label for="jobPosition">Job position:</label> <select
								name="jobId" class="form-select"
								aria-label="Default select example">
								<c:forEach var="job" varStatus="i" items="${jobs}">
									<c:choose>
										<c:when test="${staff.jobPosition.MACV == job.MACV}">
											<option value="${job.MACV}" selected>${job.TENVITRI}</option>
										</c:when>
										<c:otherwise>
											<option value="${job.MACV}">${job.TENVITRI}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
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



	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
		integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
		integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
		crossorigin="anonymous"></script>
</body>
</html>