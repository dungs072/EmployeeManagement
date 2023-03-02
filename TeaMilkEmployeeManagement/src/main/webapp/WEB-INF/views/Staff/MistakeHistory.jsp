<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/StaffHeader.jsp"%>
<base href="${pageContext.servletContext.contextPath }/">
<title>History of your mistakes</title>
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
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<div class="row mt-5">
					<div class="col">
						<div class="tableWrap">
							<table class="employeeTable">
								<thead>
									<tr>
										<th><span>STT</span></th>
										<th><span>Mistake Name</span></th>
										<th><span>Violation Date</span></th>
										<th><span>Shift</span></th>
										<th><span>Times</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="history" varStatus="i" items="${histories}">
										<tr>
											<td>${i.count}</td>
											<td>${history.mistakeEntity.MOTA}</td>
											<td>${history.shiftDetailEntity.openshift.NGAYLAMVIEC}</td>
											<td><span>${history.shiftDetailEntity.openshift.shift.IDCA}</span></td>
											<td>${history.SOLANVIPHAM}</td>
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
</body>
</html>