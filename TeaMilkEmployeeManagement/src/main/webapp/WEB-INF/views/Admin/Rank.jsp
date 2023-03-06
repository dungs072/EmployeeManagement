<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/AdminHeader.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rank Score</title>
    <style>
        /* Set a fixed scrollable wrapper */
        .tableWrap {
            margin-top: 80px;
            height: 400px;
            
            border: 2px solid black;
            overflow: auto;
            border: 1px solid black;
            border-radius: 10px;
        }

        /* Set header to stick to the top of the container. */
        thead tr th {
            color: aliceblue;
            position: sticky;
            top: 0;
            color: aliceblue;
           
        }

        td {
            text-align: center;
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
            background: #6B5B95;
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
	<div class= "row" > RANK SCORE</div>
	    <div class="container">
	    
        <div class="row justify-content-md-center">
            <div class="col-10">
                <div class="tableWrap">
                    <table class="employeeTable">
                        <thead>
                            <tr>
                                <th><span>Rank</span></th>
                                <th><span>ID Staff</span></th>
                                <th><span> Name </span></th>
                                <th><span>Shifts</span></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var = "staff" varStatus="i" items = "${list}">
                        	<tr>
                                <td>${i.count}</td>
                                <td>${staff[0].MANV}</td>
                                <td>${staff[0].HO} ${staff[0].TEN}</td>
                                <td>${staff[1]}</td>
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