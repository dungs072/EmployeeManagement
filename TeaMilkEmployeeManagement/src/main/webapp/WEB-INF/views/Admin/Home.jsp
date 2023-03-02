<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
	margin-top: 60px;
}

.navcolor {
	background-color: lightblue;
	color: black;
}

.navcolor>li>a {
	color: black;
}

textarea {
	border-radius: 15px;
}
</style>
</head>
<body>
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
								<th>Position at shift</th>
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
										<c:when test="${s.TRANGTHAILUONG == false}">
											<td><form action="updateSalary.htm" method="get"
													class="form-inline">
													<input name="salaryOfShift" type="text"
														placeholder="Add salary" value="" required>
													<button name="updateSalary" type="submit"
														class="btn btn-primary" value="${s.staff.MANV}">Update
														</button>
												</form></td>
										</c:when>
										<c:when test="${s.TRANGTHAILUONG == true}">
											<td><form class="form-inline">
													<div>${s.LUONGCA}</div>
												</form></td>
										</c:when>
									</c:choose>
									<td>
										<!-- Button trigger modal -->
										<button type="button" class="btn btn-primary"
											data-toggle="modal" data-target="#exampleModal"
											value="${s.staff.MANV}">Fault</button>
											
										<form action ="setFault.htm">
										<div class="modal fade" id="exampleModal" tabindex="-1"
											role="dialog" aria-labelledby="exampleModalLabel"
											aria-hidden="true">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h2 class="modal-title" id="exampleModalLabel">Ghi
															nhận lỗi</h2>
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
													</div>
													<div class="modal-body">
															<div class="form-group">
																<label for="idStaff">ID Staff</label> 
																<input name="idStaff" id="idStaff" value="${s.staff.MANV}">
															</div>
															<div class="form-group" >
																<label for="Fault">Fault</label> 
																<select name ="fault" id="Fault">
																	<c:forEach var="f" items="${faults}">
																		<option >${f.MOTA}</option>
																	</c:forEach>
																</select>
															</div>
															<div class="form-group">
																<label for="times">Times</label>
																<input name="times" type="number"class="form-check-input" id="times" value="1">
															</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary"
															data-dismiss="modal">Close</button>
														<button type="submit" class="btn btn-primary">Save changes</button>
													</div>
												</div>
											</div>
										</div>
										</form>
									</td>
									<c:choose>
										<c:when test = "${empty s.THOIGIANDILAM}">
										<td>
										<form action="checkin.htm">
										 	<button name="maNV" type ="submit" class="btn btn-primary" value= "${s.staff.MANV}">CheckIn</button>
										</form>
										</td>
										</c:when>
										<c:when test = "${not empty s.THOIGIANDILAM}">
										<td>
											<div> ${s.THOIGIANDILAM} </div>
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
</body>
</html>