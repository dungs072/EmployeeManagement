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
</style>

<script type="text/javascript">
	$(window).on(
			'load',
			function() {
				
				$("#weeklyDatePicker").val(localStorage.getItem("weekDates"));
				
	});
	
	$(function() {
		$('#week-button').click(function() {
			var value = $("#weeklyDatePicker").val();
			alert(value)
			const search = new URLSearchParams(location.search);
			search.set('week_start', value.split(" ")[0]);
			search.set('week_end', value.split(" ")[2]);
			location.search = search.toString();
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
							localStorage.setItem("weekDates",firstDate+" - "+lastDate);
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
				<form action=ManagerRegistration/Search.htm method="get" class = "headerForm">
					<input autocomplete="off" class="week-picker form-control"
						type='text' name="week" id="weeklyDatePicker"
						style="text-align: center;" placeholder="Select Week" value = "${weekSelection}" />

					<div class="col-sm-1">
						<button type="submit" class="btn btn-primary">Search</button>
					</div>
					<div class="col-sm-2">
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

						<c:forEach var="shift" varStatus="i" items="${shifts}">
							<tr>
								<th class="bg-primary">
									<h6 class="text-center text-light">Shift ${shift.IDCA}</h6>
									<h6 class="text-center text-light small">${shift.TENCA}</h6>
								</th>
								<c:forEach var="i" begin="1" end="7">
									<td>
										<p>${i}</p>
										<div id="list11">
											<ul>

											</ul>
										</div>

										<div class="text-center d-flex justify-content-center">
											<div class="card bg-secondary" style="width: 10rem;">
												<div class="card-body">
													<button type="button" class="btn btn-success addButton"
														value="1,1">Add</button>
												</div>
											</div>
										</div>
										<div class="text-center d-flex justify-content-center">
											<div class="card bg-secondary" style="width: 10rem;">
												<div class="card-body">
													<button type="button" class="btn btn-success addButton"
														value="1,1">Open</button>
												</div>
											</div>
										</div>
									</td>
								</c:forEach>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>


		</div>


		<!--Function generate-->
		<!--infor card-->
		<div style="display: none;">
			<div id="infor_card">
				<li>
					<div class="text-center d-flex justify-content-center">
						<div class="card btn-outline-primary" style="width: 10rem;">
							<div class="card-body">
								<div class="orderNumber">
									<h3>1</h3>
								</div>
								<h5 class="card-title" style="font-size: 10px;">Nguyễn Hữu
									Dũng</h5>
								<button type="button" class="btn btn-outline-secondary mb-1"
									data-bs-toggle="modal" data-bs-target="#setShift">Setting</button>
								<button type="button"
									class="btn btn-outline-danger deleteButton"
									data-bs-toggle="modal" data-bs-target="#warning">Delete</button>
							</div>
						</div>
					</div>
				</li>
			</div>

		</div>
		<!--waiting card-->
		<div style="display: none;">
			<div id="waiting-card">
				<li>
					<div class="text-center d-flex justify-content-center">
						<div class="card btn-outline-primary" style="width: 10rem;">
							<div class="card-body">
								<div class="orderNumber">
									<h6>1</h6>
								</div>

								<h5 class="card-title">Waiting response</h5>
								<button type="button"
									class="btn btn-outline-secondary px-4 py-3 mb-1 "
									data-bs-toggle="modal" data-bs-target="#setShift">Thiết
									lập</button>
								<button type="button"
									class="btn btn-outline-danger px-4 py-1 deleteButton"
									data-bs-toggle="modal" data-bs-target="#warning">Xóa</button>
							</div>
						</div>
					</div>
				</li>

			</div>
		</div>

		<div style="display: none;">
			<div class="text-center d-flex justify-content-center">
				<div class="card bg-secondary" style="width: 10rem;">
					<div class="card-body">
						<button type="button" class="btn btn-success px-4 py-3">Thêm</button>
					</div>
				</div>
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
								<label for="exampleInputEmail1" class="form-label">Employee</label>
								<select class="form-select form-select-lg mb-3"
									id="employeeSelect" aria-label=".form-select-lg example">
									<option selected>Open this select menu</option>
									<option value="1">One</option>
									<option value="2">Two</option>
									<option value="3">Three</option>
								</select>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">To
									do list</label> <input type="text" class="form-control"
									id="exampleInputPassword1">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" id="close" class="btn btn-secondary"
							data-bs-dismiss="modal">Close</button>
						<button type="button"
							class="btn btn-primary saveChangeSettingShift"
							data-bs-dismiss="modal">Save changes</button>
					</div>
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
						<p>Are you sure you want to delete this?</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">No</button>
						<button type="button" class="btn btn-primary yesWarningButton"
							data-bs-dismiss="modal">Yes</button>
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
		<!--add more employee-->
		<div class="modal" id="addMoreEmployee" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Number of employees for this shift</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<label for="quantity">Quantity (between 1 and 15):</label> <input
							type="number" id="quantity" name="quantity" min="1" max="15">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary quantitySetting"
							data-bs-dismiss="modal">Set</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>