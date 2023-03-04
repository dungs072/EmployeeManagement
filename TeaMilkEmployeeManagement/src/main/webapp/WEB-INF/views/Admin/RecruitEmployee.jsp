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
.tableWrap {
	height: 390px;
	border: 2px solid black;
	overflow: auto;
}

/* Set header to stick to the top of the container. */
thead tr th {
	position: sticky;
	top: 0;
	color: aliceblue;
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
.InputInvalid{
color:red;
font-style: italic;
margin-left:5px;
margin-top:5px;
}
</style>
<script th:inline="javascript">
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

						$(".addModalButton").click(function() {
							localStorage.setItem("isClickedAddStaffButton", "true");
						});

						$(".add-save-created").click(function() {
							localStorage.setItem("isClickedSaveStaffButton","true");
							var firstName = $(document).find('.firstName').val();
							var lastName = $(document).find('.lastName').val();
							var idCard = $(document).find('.idCard').val();
							var phoneNumber = $(document).find('.phoneNumber').val();
							var address = $(document).find('.address').val();
							var staffInfor = firstName +","+lastName+","+idCard+","+phoneNumber+","+address;
							localStorage.setItem("staffInfo",staffInfor);
						});
						$(".resetPassword").click(function() {
							var yesResetButton = $(document).find('.yes-reset-warning');
							yesResetButton.val($(this).val());
						});
						$('.yes-reset-warning').click(function() {
							localStorage.setItem("isClickedReset", "true");
							localStorage.setItem("resetId", $(this).val());
						});
						$(window).on('load',function() {

							var value = localStorage.getItem("isClickedInfor");
							if (value == "true") {
								$("#detailModal").modal("show");
								localStorage.setItem("isClickedInfor","false");
							}
							
							var addValue = localStorage.getItem("isClickedAddStaffButton");
							if (addValue == "true") {
								$("#addModal").modal("show");
								localStorage.setItem("isClickedAddStaffButton","false");
							}
							var isWrongIdCard = [[${isWrongIDCard}]];
							var isWrongPhoneNumber = [[${isWrongPhoneNumber}]];

							if(isWrongIdCard=='true'||isWrongPhoneNumber=='true'){
								var staffInfo = localStorage.getItem("staffInfo").split(',');
								$(document).find('.firstName').val(staffInfo[0]);
								$(document).find('.lastName').val(staffInfo[1]);
								$(document).find('.idCard').val(staffInfo[2]);
								$(document).find('.phoneNumber').val(staffInfo[3]);
								$(document).find('.address').val(staffInfo[4]);
								$("#addModal").modal("show");
								localStorage.setItem("isClickedSaveStaffButton","false");
							}
							else{
								var saveAddValue = localStorage.getItem("isClickedSaveStaffButton");
								if (saveAddValue == "true") {
									$('#createdModal').modal("show");
									$(document).find('#username').val($('.add-save-created').val());
									localStorage.setItem("isClickedSaveStaffButton","false");
								}
							}
							
							
							var resetValue = localStorage.getItem("isClickedReset");
							if (resetValue == "true") {
								$('#ResetSuccessfullyModal').modal('show');
								$(document).find('#ResetUsername').val(localStorage.getItem('resetId'));
								localStorage.setItem("isClickedReset","false");

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

			<div class="searchbar2">
				<input type="text" name="" id="" placeholder="Search">
				<div class="searchbtn">
					<img
						src="https://media.geeksforgeeks.org/wp-content/uploads/20221210180758/Untitled-design-(28).png"
						class="icn srchicn" alt="search-button">
				</div>
			</div>

			<div class="box-container">
				<div class="container">
		<div class="row ">
			<div class="col-6">
				<div class="search mb-3">

					<form action="Recruit/SearchStaff.htm" method="get">
						<input type="text" name="searchInput"
							placeholder="Name, Job position..">
						<button type="submit" class="btn btn-outline-dark">Search</button>
					</form>
					
					<form action="Recruit/ShowJobPosition.htm" method="get">
					<button type="submit" class="btn btn-success addModalButton">Add
						an employee</button>
				</form>

				</div>
				
				

			</div>

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
								<th><span>Position</span></th>
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
									<td><span>${staff.jobPosition.TENVITRI}</span></td>
									<td>
										<form action="Recruit/InforStaff.htm" method="get">
											<button type="submit" name="InforStaff"
												class="btn btn-success detailEmployee"
												value="${staff.MANV}">Detail</button>
											<button type="button" name="resetPassword"
												class="btn btn-secondary resetPassword"
												value="${staff.MANV}" data-bs-toggle="modal"
												data-bs-target="#ResetWarning">Reset</button>
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
								class="form-control firstName" id="firstname" oninput="this.value = this.value.replace(/[a-zA-Z]/g, '').replace(/(\..*?)\..*/g, '$1');"
								placeholder="First name..." name="HO" maxlength="30" required />
						</div>
						<!-- fix pattern there -->
						<div class="form-group">
							<label for="lastname">Last name:</label> <input type="text"
								class="form-control lastName" id="lastname" pattern="[a-zA-Z]+"
								placeholder="Last name..." name="TEN" maxlength="30" required/>
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
								type="text" class="form-control idCard" id="idcard"
								name="CCCD"
								oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
								maxlength="12" 
								required/>
							<p class = "InputInvalid">${idCardMessage}</p>
						</div>

						<div class="form-group">
							<label for="phoneNumber">Phone number:</label> <input type="text"
								class="form-control phoneNumber" id="phoneNumber" name="SDT"
								oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
								maxlength="10" required />
							<p class = "InputInvalid">${phoneMessage}</p>
						</div>
						<div class="form-group">
							<label for="address">Address:</label> <input type="text"
								class="form-control address" id="adderess" name="DIACHI"
								value="97 Man Thiện" maxlength="50" required/>
						</div>
						<div class="form-group">
							<label for="add-jobId">Job position:</label> <select
								name="add-jobId" class="form-select"
								aria-label="Default select example">
								<c:forEach var="job" varStatus="i" items="${jobs}">
									<option value="${job.MACV}">${job.TENVITRI}</option>
								</c:forEach>

							</select>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" name="saveAddEmployee"
								class="btn btn-primary add-save-created"
								value="${staffIdValue}">Save changes</button>
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
						<label for="staticEmail" class="col-sm-2 col-form-label">Username: </label>
						<div class="col-sm-10">
							<input type="text" readonly class="form-control-plaintext"
								id="username">
						</div>
					</div>
					<div class="mb-3 row">
						<label for="inputPassword" class="col-sm-2 col-form-label">Password: </label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="123" readonly>
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

	<!-- Reset -->
	<div class="modal" tabindex="-1" id="ResetWarning">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">!!!</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Do you want to reset this password account to 123 ?</p>
				</div>
				<div class="modal-footer">
					<form action="Recruit/ResetPassword.htm" method="get">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<button type="submit" class="btn btn-primary yes-reset-warning"
							data-bs-dismiss="modal" name="yes-reset-warning">Yes</button>
					</form>

				</div>
			</div>
		</div>
	</div>

	<!-- ResetPassword successfully -->
	<div class="modal" tabindex="-1" id="ResetSuccessfullyModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">New Password</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3 row">
						<label for="staticEmail" class="col-sm-2 col-form-label">Username</label>
						<div class="col-sm-10">
							<input type="text" readonly class="form-control-plaintext"
								id="ResetUsername">
						</div>
					</div>
					<div class="mb-3 row">
						<label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="123" readonly>
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