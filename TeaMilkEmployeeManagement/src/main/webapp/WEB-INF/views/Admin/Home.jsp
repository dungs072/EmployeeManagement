<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/xml" prefix = "x" %>


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
<link
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/base/jquery-ui.css"
	rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<script src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous" />

<link rel="stylesheet"
	href="extensions/sticky-header/bootstrap-table-sticky-header.css">
<script src="extensions/sticky-header/bootstrap-table-sticky-header.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
	integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
	integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
	crossorigin="anonymous"></script>
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

.btn-form {
	
}

textarea {
	border-radius: 15px;
}

.table {
	
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="shift">
			<div class="row">
				<div class="col-md-4">
					<h2>Shift on day</h2>
					<div class="row">
						<div class="col-md-12 info-shift">
							<h3>Shift 1</h3>
							<label>Num of employees</label> <br>
							<button type="button" class="btn btn-primary">More
								Information</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 info-shift">
							<h3>Shift 2</h3>
							<label>Num of employees</label> <br>
							<button type="button" class="btn btn-primary">More
								Information</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 info-shift">
							<h3>Shift 3</h3>
							<label>Num of employees</label> <br>
							<button type="button" class="btn btn-primary">More
								Information</button>
						</div>
					</div>
				</div>
				<div class=" col-xs-8 col-sm-8 col-md-8 col-lg-8 shift-now">
					<div class="row">
						<div class=" col-md-6">
							<h3>Shift number:</h3>
						</div>
						<div class="col-md-6 text-left">
							<h3>Date:</h3>
						</div>
					</div>
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>Employee Code</th>
								<th>Fullname</th>
								<th>Position at shift</th>
								<th>Salary</th>
								<th>Delete employee</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var ="s" items= "${shiftNow}">
							<tr>
								<td>${s.MANV}</td>
								<td>${s.MANV.TEN}</td>
								<td></td>
								<td><form class="form-inline">
										<input placeholder="Add salary">
										<button type="button" class="btn btn-primary">Update salary</button>
									</form></td>
								<td align="center"><button name="btnRemove"
										class="btn btn-danger">
										<span class="glyphicon glyphicon-remove"></span>
									</button></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<button type="button" class="btn btn-primary">Add staff</button>
					<hr>
					<textarea rows="5" cols="109" placeholder="Note"></textarea>
					<button type="button" class="btn btn-primary">Save Note</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>