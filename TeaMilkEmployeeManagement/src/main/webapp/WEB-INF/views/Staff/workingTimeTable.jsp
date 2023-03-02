<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>Timetable</title>
<%@include file="/WEB-INF/views/include/StaffHeader.jsp"%>
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

.cardSize {
	width: 100%;
	height: 100%;
}
</style>

<script type="text/javascript">
	$(window).on('load', function() {
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
	$(document).on('click', ".detailButton", function(e) {
		$('.detailJob').val($(this).val());
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
				<form action=StaffTimetable/Search.htm method="get"
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
											<div class=cardSize>
												<div class="text-center d-flex justify-content-center">
													<div class="card btn-outline-danger border-danger"
														style="width: 10rem;">
														<div class="card-body">
															<div class="orderNumber">
																<h3>${indexStaff.count}</h3>
															</div>
															<h5 class="card-title" style="font-size: 10px;">${shiftStaffs[indexShift.index][i-1].fullName}</h5>
															<button type="button" name="detailButton"
																class="btn btn-outline-secondary detailButton"
																data-bs-toggle="modal" data-bs-target="#detail"
																value="${shiftStaffs[indexShift.index][i-1].additionalJob}">Detail</button>
														</div>
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

		<!-- detail -->
		<div class="modal" id="detail" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title text-warning">Detail </h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<label for="inputLabel" class="form-label">You should do: </label>
						<input type = "text" class = "detailJob" readonly>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success"
							data-bs-dismiss="modal">Ok</button>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>