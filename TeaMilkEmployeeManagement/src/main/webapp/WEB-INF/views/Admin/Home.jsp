<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@include file="/WEB-INF/views/include/AdminHeader.jsp"%>

<title>Trang chủ</title>
<style>
.info-shift {
	height: 150px;
	margin-bottom: 10px;
	color: black;
	border: 3px solid blue;
	border-radius: 15px;
}

.shift {
	margin-top: 1px;
}


</style>

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

				<div class="container-fluid">
					<div class="shift">
						<div class="row">
							<div class="col-md-3">
								<h2>Shift on day</h2>
								<div class="row">
									<div class="col-md-12 info-shift">
										<h3>Shift 1</h3>

										<form action="showDetail.htm" method="get">
											<button name="idCa" value="1" type="submit"
												class="btn btn-primary">More Information</button>
										</form>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 info-shift">
										<h3>Shift 2</h3>

										<form action="showDetail.htm" method="get">
											<button name="idCa" value="2" type="submit"
												class="btn btn-primary">More Information</button>
										</form>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 info-shift">
										<h3>Shift 3</h3>

										<form action="showDetail.htm" method="get">
											<button name="idCa" value="3" type="submit"
												class="btn btn-primary">More Information</button>
										</form>
									</div>
								</div>
							</div>
							<div class=" col-xs-9 col-sm-9 col-md-9 col-lg-9 shift-now">
								<div class="row">
									<div class=" col-md-6">
										<h3>Shift number: ${idca}</h3>

									</div>
									<div class="col-md-6 text-left">
										<h3>Date: ${getDate}</h3>
									</div>
								</div>
								<table class="table table-striped table-hover">
									<thead>
										<tr>
											<th>Employee Code</th>
											<th>Fullname</th>
											<th>Additional jobs</th>
											<th>Salary</th>
											<th>Fault</th>
											<th>CheckIn</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="s" items="${shiftNow}">
											<tr>
												<td>${s.staff.MANV}</td>
												<td>${s.staff.HO} ${s.staff.TEN}</td>
												<td>${s.CONGVIEC }</td>
												<c:choose>
													<c:when test="${empty s.THOIGIANCHAMCONG}">
														<td><form action="updateSalary.htm" method="get"
																class="form-inline">
																<input name="salaryOfShift" type="text"
																	placeholder="Add salary" value="" required>
																<button name="updateSalary" type="submit"
																	class="btn btn-success" value="${s.staff.MANV}">Update
																</button>
															</form></td>
													</c:when>
													<c:otherwise>
														<td><form class="form-inline">
																<div>${s.LUONGCA}</div>
																<div>${s.THOIGIANCHAMCONG}</div>
															</form></td>
													</c:otherwise>
												</c:choose>
												<td>
													<!-- Button trigger modal -->
													<button type="button" class="btn btn-danger"
														data-toggle="modal" data-target="#exampleModal"
														value="${s.staff.MANV}">Fault</button>

													<form action="setFault.htm">
														<div class="modal fade" id="exampleModal" tabindex="-1"
															role="dialog" aria-labelledby="exampleModalLabel"
															aria-hidden="true">
															<div class="modal-dialog" role="document">
																<div class="modal-content">
																	<div class="modal-header">
																		<h2 class="modal-title" id="exampleModalLabel">Ghi
																			nhận lỗi</h2>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-label="Close">
																			<span aria-hidden="true">&times;</span>
																		</button>
																	</div>
																	<div class="modal-body">
																		<div class="form-group">
																			<label for="idStaff">ID Staff</label> <input
																				name="idStaff" id="idStaff" value="${s.staff.MANV}">
																		</div>
																		<div class="form-group">
																			<label for="Fault">Fault</label> <select name="fault"
																				id="Fault">
																				<c:forEach var="f" items="${faults}">
																					<option>${f.MOTA}</option>
																				</c:forEach>
																			</select>
																		</div>
																		<div class="form-group">
																			<label for="times">Times</label> <input name="times"
																				type="number" class="form-check-input" id="times"
																				value="1">
																		</div>
																	</div>
																	<div class="modal-footer">
																		<button type="button" class="btn btn-secondary"
																			data-dismiss="modal">Close</button>
																		<button type="submit" class="btn-success">Save
																			changes</button>
																	</div>
																</div>
															</div>
														</div>
													</form>
												</td>
												<c:choose>
													<c:when test="${empty s.THOIGIANDILAM}">
														<td>
															<form action="checkin.htm">
																<button name="maNV" type="submit"
																	class="btn btn-secondary" value="${s.staff.MANV}">CheckIn</button>
															</form>
														</td>
													</c:when>
													<c:when test="${not empty s.THOIGIANDILAM}">
														<td>
															<div>${s.THOIGIANDILAM}</div>
														</td>
													</c:when>
												</c:choose>
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

</body>
</html>