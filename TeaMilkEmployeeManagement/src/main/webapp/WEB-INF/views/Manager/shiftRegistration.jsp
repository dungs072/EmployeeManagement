<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>Create Shifts</title>
<%@include file="/WEB-INF/views/include/headerAfterLogin.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">

<style>
.ui-datepicker-calendar tbody tr:hover {
	background-color: rgba(0, 170, 255, 0.5);
	color: #444444;
}

.ui-datepicker-calendar tbody tr:hover td, .ui-datepicker-calendar tbody tr:hover a
	{
	background: transparent !important;
	color: #fff !important;
	font-weight: bold;
}

.scrollit {
	overflow: scroll;
	height: 450px;
}

ul {
	list-style: none; /* Remove list bullets */
	padding: 0;
	margin: 0;
}

table {
	margin-left: auto;
	margin-right: auto;
	height: 100%;
	table-layout: fixed;
}

td {
	text-align: center;
}

span {
	cursor: pointer;
}

.minus, .plus {
	width: 20px;
	height: 20px;
	background: #f2f2f2;
	border-radius: 4px;
	border: 1px solid #ddd;
	display: inline-block;
	vertical-align: middle;
	text-align: center;
	border: 1px solid #ddd;
}

input {
	height: 34px;
	width: 100px;
	text-align: center;
	font-size: 26px;
	border: 1px solid #ddd;
	border-radius: 4px;
	display: inline-block;
	vertical-align: middle;
}
</style>

<script type="text/javascript">
	$(window).on('load', function() {

		var value = localStorage.getItem("isClickedAdd");
		if (value == "true") {
			$('.saveChangesAddStaff').val(localStorage.getItem("addValue"));
			$('#addStaff').modal('show');
			localStorage.setItem("isClickedAdd", "false");
		}

		if (!$("#weeklyDatePicker").val()) {
			$("#weeklyDatePicker").val(localStorage.getItem("weekDates"));
		}

	});

	$(function() {
		$('#week-button').click(function() {
			var value = $("#weeklyDatePicker").val();
			const search = new URLSearchParams(location.search);
			search.set('week_start', value.split(" ")[0]);
			search.set('week_end', value.split(" ")[2]);
			location.search = search.toString();
		});

	});
	$(document).on('click', ".cancelButton", function(e) {

		var yesButton = $(document).find('.yesWarningButton')
		yesButton.val($(this).val())
	});
	$(document).on('click', ".addButton", function(e) {
		localStorage.setItem("addValue", $(this).val());
		localStorage.setItem("isClickedAdd", "true");
	})

	$(document).on('click', ".openButton", function(e) {
		var yesButton = $(document).find('.yesOpenButton');
		yesButton.val($(this).val())
	})

	$(document).on('click',".setStaffShiftButton",function(e) {
						var value = $(this).val();
						var values = value.split('+');
						document.getElementById('settingNameModal').innerHTML = values[0];
						document.getElementById('settingJobPositionModal').innerHTML = values[1];

						$('#settingToDoListModal').val(values[2]);
						$('.saveChangeSettingShift').val(values[3]);

	})
	
	$(document).on('click',".deleteStaffButton",function(e){
		
		$('#yesWarningStaffButton').val($(this).val());
	})

	$(document).ready(function() {

		$('.minus').click(function() {
			var $input = $(this).parent().find('input');
			var count = parseInt($input.val()) - 1;
			count = count < 1 ? 1 : count;
			$input.val(count);
			$input.change();
			return false;
		});
		$('.plus').click(function() {
			var $input = $(this).parent().find('input');
			$input.val(parseInt($input.val()) + 1);
			$input.change();
			return false;
		});
	});

	$(document).ready(

			function() {
				moment.updateLocale('en', {
					week : {
						dow : 1
					}
				// Monday is the first day of the week
				});

				//Initialize the datePicker(format mm-dd-yyyy)
				$("#weeklyDatePicker").datepicker({
					format : 'MM-DD-YYYY',
					firstDay : 1
				});

				//Get the value of Start and End of Week
				$('#weeklyDatePicker').on(
						'change',
						function(e) {
							var value = $("#weeklyDatePicker").val();
							var firstDate = moment(value, "MM-DD-YYYY").day(1)
									.format("DD/MM/YYYY");
							var lastDate = moment(value, "MM-DD-YYYY").day(7)
									.format("DD/MM/YYYY");
							$("#weeklyDatePicker").val(
									firstDate + " - " + lastDate);
							var v = firstDate + " - " + lastDate;
							localStorage.setItem("weekDates", v);
							$('.searchButton').val(v);
						});
			});
</script>
</head>
<body>
	<div class="container">
		<div class="mt-2 row ">
			<label class="col-sm-1 col-form-label"> <span
				class="badge rounded-pill bg-primary " style="font-size: 1rem;">Week</span>
			</label>
			<div class="col-sm-3">
				<input autocomplete="off" class="week-picker form-control"
					type='text' name="week" id="weeklyDatePicker"
					style="text-align: center;" placeholder="Select Week"
					value="${weekSelection}" />
			</div>
			<div class="col-sm-1">
				<form action=ManagerRegistration/Search.htm method="get"
					class="headerForm">
					<div class="col-auto">
						<button type="submit" name="searchButton"
							class="btn btn-primary searchButton">Search</button>
					</div>
				</form>
			</div>
			<div class="col-sm-1">
				<form action=ManagerRegistration/Search.htm method="get"
					class="headerForm">
					<div class="col-auto">
						<button type="button" class="btn btn-primary"
							data-bs-toggle="modal" data-bs-target="#confirmWarning">Confirm</button>
					</div>
				</form>
			</div>






			<div class="mt-2 row align-items-center scrollit">
				<table class="table table-bordered" id="shiftTable">
					<thead class="bg-primary">
						<tr>
							<th scope="col">
								<h6 class="text-center text-light">Time table</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Monday</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Tuesday</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Wednesday</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Thursday</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Friday</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Saturday</h6>
							</th>
							<th scope="col">
								<h6 class="text-center text-light">Sunday</h6>
							</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="shift" varStatus="indexShift" items="${shifts}">
							<tr>
								<th class="bg-primary">
									<h6 class="text-center text-light">Shift ${shift.IDCA}</h6>
									<h6 class="text-center text-light small">${shift.TENCA}</h6>
								</th>
								<c:forEach var="i" begin="1" end="7">
									<td><c:if
											test="${not empty shiftStaffs[indexShift.index][i-1]}">
											<div class="text-center d-flex justify-content-center mb-1">
												<div class="card bg-secondary" style="width: 10rem;">
													<div class="card-body">
														<h5 class="card-title" style="font-size: 10px;">Staff
															left: ${shiftStaffs[indexShift.index][i-1].maxStaff}</h5>
													</div>
												</div>
											</div>
										</c:if>
										<div>
											<ul>

												<c:if test="${not empty shiftStaffs[indexShift.index][i-1]}">
													<c:forEach var="shiftStaff" varStatus="indexStaff"
														items="${shiftStaffs[indexShift.index][i-1].getListShiftDataUI()}">
														<li>
															<div class="text-center d-flex justify-content-center">
																<div class="card btn-outline-primary"
																	style="width: 10rem;">
																	<div class="card-body">
																		<div class="orderNumber">
																			<h3>${indexStaff.count}</h3>
																		</div>
																		<h5 class="card-title" style="font-size: 10px;">${shiftStaff.fullName}</h5>
																		<button type="button"
																			class="btn btn-outline-secondary mb-1 setStaffShiftButton"
																			data-bs-toggle="modal" data-bs-target="#setShift"
																			value="${shiftStaff.fullName}+${shiftStaff.jobPositionName}+${shiftStaff.additionalJobs}+${shiftStaff.shiftDetailId}">Setting</button>
																		<button type="button"
																			class="btn btn-outline-danger deleteStaffButton"
																			data-bs-toggle="modal" data-bs-target="#warning" value = "${shiftStaff.shiftDetailId}">Delete</button>

																	</div>
																</div>
															</div>
														</li>
													</c:forEach>

												</c:if>




											</ul>
										</div> <c:choose>

											<c:when
												test="${canDisplayCancelButton[indexShift.index][i-1] == false}">
												<div class="text-center d-flex justify-content-center">
													<div class="card bg-secondary" style="width: 10rem;">
														<div class="card-body">
															<button type=button name="openShift"
																class="btn btn-success openButton"
																data-bs-toggle="modal" data-bs-target="#openSetting"
																value="${indexShift.count},${i}">Open</button>
														</div>
													</div>
												</div>
											</c:when>
											<c:otherwise>


												<div class="text-center d-flex justify-content-center mb-1">
													<div class="card bg-secondary" style="width: 10rem;">
														<div class="card-body">

															<form action="ManagerRegistration/AddStaff.htm"
																method="get">
																<button type="submit" class="btn btn-success addButton"
																	data-bs-toggle="modal" data-bs-target="#addStaff"
																	name="addStaffButton" value="${indexShift.count},${i}">Add</button>
															</form>
														</div>
													</div>
												</div>

												<div class="text-center d-flex justify-content-center">
													<div class="card bg-secondary" style="width: 10rem;">
														<div class="card-body">
															<button type="button"
																class="btn btn-danger functionButton cancelButton"
																data-bs-toggle="modal" data-bs-target="#cancelWarning"
																value="${indexShift.count},${i}">Cancel</button>

														</div>
													</div>
												</div>
											</c:otherwise>
										</c:choose></td>
								</c:forEach>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>


		</div>

		<!-- Modal -->
		<!--setting-->
		<div class="modal fade" id="setShift" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div
				class="modal-dialog modal-dialog-centered modal-dialog-scrollable"
				role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Employee's
							shift detail</h5>
						<button type="button" class="close" data-bs-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form>
							<div class="mb-3">
								<label for="settingName">Name: </label>
								<h6 id="settingNameModal">ABC</h6>
								<label for="settingJobPosition">Job Position: </label>
								<h6 id="settingJobPositionModal">CBA</h6>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">To
									do list</label> <input type="text" class="form-control"
									id="settingToDoListModal">
							</div>

							<div class="modal-footer">
								<button type="button" id="close" class="btn btn-secondary"
									data-bs-dismiss="modal">Close</button>
								<button type="button"
									class="btn btn-primary saveChangeSettingShift"
									data-bs-dismiss="modal">Save changes</button>
							</div>
						</form>
					</div>

				</div>
			</div>
		</div>

		<!--add-->
		<div class="modal fade" id="addStaff" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div
				class="modal-dialog modal-dialog-centered modal-dialog-scrollable"
				role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Employee's
							shift detail</h5>
						<button type="button" class="close" data-bs-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form action="ManagerRegistration/SaveAddStaff.htm" method="get">
							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">Job
									Positions</label> <select name="staffInfor" class="form-select"
									aria-label="Default select example">
									<c:forEach var="staff" varStatus="i" items="${staffs}">
										<option
											value="${staff.MANV},${staff.HO} ${staff.TEN},${staff.jobPosition.TENVITRI}">${staff.HO}
											${staff.TEN} ${staff.jobPosition.TENVITRI }</option>
									</c:forEach>
								</select>

							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">To
									do list</label> <input type="text" name="TodoList" class="form-control"
									id="exampleInputPassword1">
							</div>

							<div class="modal-footer">
								<button type="button" id="close" class="btn btn-secondary"
									data-bs-dismiss="modal">Close</button>
								<button type="submit"
									class="btn btn-primary saveChangesAddStaff"
									name="saveAddChangeButton" data-bs-dismiss="modal">Save</button>
							</div>
						</form>
					</div>

				</div>
			</div>
		</div>


		<!--open setting-->
		<div class="modal" id="openSetting" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title text-warning">Setting</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<form action="ManagerRegistration/Open.htm">
						<div class="modal-body">
							<label for="exampleInputEmail1" class="form-label">Max
								staff: </label>
							<div class="number">
								<span class="minus">-</span> <input type="text" value="1"
									name="maxStaff" /> <span class="plus">+</span>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">No</button>
							<button type="submit" class="btn btn-primary yesOpenButton"
								name="yesOpenShift" data-bs-dismiss="modal">Yes</button>
						</div>
					</form>

				</div>
			</div>
		</div>

		<!--delete-warning-->
		<div class="modal" id="warning" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title text-warning">Warning !!!</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to delete this staff from the shift?</p>
					</div>
					<form action = "ManagerRegistration/DeleteStaffFromShift.htm" method = "get">
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">No</button>
							<button type="submit" class="btn btn-primary" id = "yesWarningStaffButton"
								data-bs-dismiss="modal" name = "yesWarningStaffButton">Yes</button>
						</div>
					</form>

				</div>
			</div>
		</div>
		<!--cancel-warning-->
		<div class="modal" id="cancelWarning" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title text-warning">Warning !!!</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<p>Are you sure you want to cancel this shift?</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<form action=ManagerRegistration/Cancel.htm method="get">
							<button type="submit" class="btn btn-primary yesWarningButton"
								name="cancelShift" data-bs-dismiss="modal">Yes</button>
						</form>

					</div>
				</div>
			</div>
		</div>
		<!--confirm-warning-->
		<div class="modal" id="confirmWarning" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title"></h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<p>All changes will be save in database !!!</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancel</button>
						<button type="button" class="btn btn-primary"
							data-bs-dismiss="modal">Save</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>