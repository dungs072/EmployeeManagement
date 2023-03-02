<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>Register Shifts</title>
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

.minus, .plus, .minus-setting {
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

.confirmCheckBox {
	width: 20px;
	height: 20px;
	text-align: left;
	vertical-align: top;
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

	$(document).on('click', ".settingMaxStaffButton", function(e) {
		var datas = $(this).val().split('+');
		var inputRange = $(document).find('.inputMaxStaff');
		var minusSetting = $(document).find('.minus-setting');
		var yesSettingMaxStaffButton = $(document).find('.yesSettingMaxStaff');
		minusSetting.val(datas[3]);
		inputRange.val(datas[2]);
		yesSettingMaxStaffButton.val(datas[0] + "," + datas[1])
	})

	$(document)
			.on(
					'click',
					".setStaffShiftButton",
					function(e) {
						var value = $(this).val();
						var values = value.split('+');
						document.getElementById('settingNameModal').innerHTML = values[0];
						document.getElementById('settingJobPositionModal').innerHTML = values[1];

						$('#settingToDoListModal').val(values[2]);
						$('.saveChangeSettingShift').val(values[3]);

					})

	$(document).on('click', ".deleteStaffButton", function(e) {

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

		$('.minus-setting').click(function() {
			var $input = $(this).parent().find('input');
			var count = parseInt($input.val()) - 1;
			count = count < $(this).val() ? $(this.val()) : count;
			$input.val(count);
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
									.format("MM/DD/YYYY");
							var lastDate = moment(value, "MM-DD-YYYY").day(7)
									.format("MM/DD/YYYY");
							$("#weeklyDatePicker").val(
									firstDate + " - " + lastDate);
							var firstDates = firstDate.split("/")
							var lastDates = lastDate.split("/")
							var v = firstDates[1] + "/" + firstDates[0] + "/"
									+ firstDates[2] + " - " + lastDates[1]
									+ "/" + lastDates[0] + "/" + lastDates[2];
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
					style="text-align: center;" placeholder="Select Week" readonly
					value="${weekSelection}" />
			</div>
			<div class="col-sm-1">
				<form action=StaffRegisterShift/Search.htm method="get"
					class="headerForm">
					<div class="col-auto">
						<button type="submit" name="searchButton"
							class="btn btn-primary searchButton">Search</button>
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
														<h5 class="card-title" style="font-size: 10px;">Registrations
															left: ${shiftStaffs[indexShift.index][i-1].leftStaff}</h5>
													</div>
												</div>
											</div>
										</c:if>
										<div>
											<ul>

												<c:if test="${not empty shiftStaffs[indexShift.index][i-1]}">
													<c:forEach var="shiftStaff" varStatus="indexStaff"
														items="${shiftStaffs[indexShift.index][i-1].getList()}">
														<li>
															<div class="text-center d-flex justify-content-center">
																<div class="card btn-outline-primary"
																	style="width: 10rem;">
																	<div class="card-body">
																		<div class="orderNumber">
																			<h3>${indexStaff.count}</h3>
																		</div>
																		<h5 class="card-title" style="font-size: 10px;">${shiftStaff.fullName}</h5>
																		<c:choose>
																			<c:when test="${shiftStaff.isConfirmed == true}">
																				<input type="checkbox" class="confirmCheckBox"
																					onclick="return false;" checked>
																			</c:when>
																			<c:otherwise>
																				<input type="checkbox" class="confirmCheckBox"
																					onclick="return false;">
																			</c:otherwise>
																		</c:choose>
																		<c:choose>
																			<c:when test="${shiftStaff.isOwned==true}">
																				<c:choose>
																					<c:when test = "${shiftStaff.isConfirmed ==false&&shiftStaffs[indexShift.index][i-1].canSettingShift ==true}">
																						<button type="button"
																						class="btn btn-outline-danger deleteStaffButton"
																						data-bs-toggle="modal" data-bs-target="#warning"
																						value="${shiftStaff.shiftDetailId}">Cancel</button>
																					</c:when>
																					
																					<c:otherwise>
																						<button type="button"
																						class="btn btn-outline-danger deleteStaffButton"
																						data-bs-toggle="modal" data-bs-target="#warning" disabled
																						value="${shiftStaff.shiftDetailId}">Cancel</button>
																					</c:otherwise>
																				</c:choose>
																				
																			</c:when>
																			<c:otherwise>

																			</c:otherwise>
																		</c:choose>


																	</div>
																</div>
															</div>
														</li>
													</c:forEach>

												</c:if>

											</ul>
										</div> <c:if
											test="${canRegisterShift[indexShift.index][i-1] == true}">
											<div class="text-center d-flex justify-content-center">
												<div class="card bg-secondary" style="width: 10rem;">
													<div class="card-body">
														<form action="StaffRegisterShift/Register.htm"
															method="get">
															<c:choose>
																<c:when
																	test="${not empty shiftStaffs[indexShift.index][i-1] && shiftStaffs[indexShift.index][i-1].leftStaff>0 &&shiftStaffs[indexShift.index][i-1].canSettingShift==true}">
																	<button type="submit" name="registerShift"
																		class="btn btn-success openButton"
																		value="${indexShift.count},${i}">Register</button>
																</c:when>
																<c:otherwise>
																	<button type="submit" name="registerShift"
																		class="btn btn-success openButton" disabled
																		value="${indexShift.count},${i}">Register</button>
																</c:otherwise>
															</c:choose>
														</form>
													</div>
												</div>
											</div>
										</c:if>
								</c:forEach>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>


		</div>

		<!-- Modal -->

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
						<p>Are you sure you want to cancel your registration ?</p>
					</div>
					<form action="StaffRegisterShift/CancelRegistration.htm"
						method="get">
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">No</button>
							<button type="submit" class="btn btn-primary"
								id="yesWarningStaffButton" data-bs-dismiss="modal"
								name="yesWarningStaffButton">Yes</button>
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
	</div>
</body>
</html>