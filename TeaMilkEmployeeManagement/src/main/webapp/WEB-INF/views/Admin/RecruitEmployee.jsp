<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8">
	<%@include file = "/WEB-INF/views/include/header.jsp" %>
	<title>ADMIN</title>
	<style>
        /* Set a fixed scrollable wrapper */
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


        /*Top menu*/
        .topnav {
            background-color: #333;
            overflow: hidden;
        }

        /* Style the links inside the navigation bar */
        .topnav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        /* Change the color of links on hover */
        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        /* Add a color to the active/current link */
        .topnav a.active {
            background-color: #04AA6D;
            color: white;
        }

        .topnav-right {
            float: right;
            margin-top: 15px;
            margin-right:20px;
          }
    </style>
     <script>
        $(document).ready(function () {

            //start 
            var bodyTable = $(document).find('.employeeTable').find("tbody")
            var rowItem = bodyTable.find('tr:first');
            rowItem.hide()

            //call function
           $(document).on('click',".add-save",function(e){
                
                var newRowItem = rowItem.clone();
                newRowItem.show()
                bodyTable.after(newRowItem);
           });
        });
    </script>
</head>
<body>
	
  	<div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="#news">Employees</a>
        <a href="#contact">Contact</a>
        <a href="#about">About</a>
        <div class="topnav-right text-light">
            <p>ADMIN</p>
        </div>
    </div>
    <div class="container">
        <div class="row mt-3">
            <div class="col">
                <div class="search mb-3">
                    <input type="text" placeholder="Search employee..">
                </div>
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">
                    Add an employee
                </button>
            </div>
            <div class="col">

            </div>

        </div>
        <div class="row mt-3">
            <div class="col">
                <div class="tableWrap">
                    <table class = "employeeTable">
                        <thead>
                            <tr>
                                <th><span>Employee Id</span></th>
                                <th>
                                    <span>Full Name</span>
                                </th>
                                <th><span>Phone number</span></th>
                                <th><span>Action</span></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>NV01</td>
                                <td>Nguyễn Hữu Dũng</td>
                                <td>
                                    <span>0866156874</span>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-secondary settingButton">Setting</button>
                                    <button type="button" class="btn btn-danger deleteButton">Delete</button>
                                </td>
                            </tr>
                           	
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
    <!-- Modal -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
        aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle">Add employee</h5>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action = "CheckLogin.htm" method = "post">
                        <div class="form-group">
                            <label for="firstname">First name:</label>
                            <input type="text" class="form-control username" id="firstname" placeholder="First name..."
                                name="HO" value="Nguyễn Hữu" maxlength="30" />
                        </div>
                        <div class="form-group">
                            <label for="lastname">Last name:</label>
                            <input type="text" class="form-control username" id="lastname" placeholder="Last name..."
                                name="TEN" value="Dũng" maxlength="30" />
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="GIOITINH" id="gender1" value = "Nam">
                            <label class="form-check-label" for="flexRadioDefault1">
                                Male
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="GIOITINH" id="gender2" value = "Nữ" checked>
                            <label class="form-check-label" for="flexRadioDefault2">
                                Female
                            </label>
                        </div>
                        <div class="form-group">
                            <label for="idcard">Identification card number:</label>
                            <input type="text" class="form-control username" id="idcard" name="CCCD"
                                oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
                                maxlength="12" />
                        </div>

                        <div class="form-group">
                            <label for="phoneNumber">Phone number:</label>
                            <input type="text" class="form-control username" id="phoneNumber" name="SDT"
                                oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');"
                                maxlength="10" />
                        </div>
                        <div class="form-group">
                            <label for="address">Address:</label>
                            <input type="text" class="form-control username" id="adderess" name="DIACHI"
                                value="97 Man Thiện" maxlength="50" />
                        </div>
                        <div class="form-group">
                            <label for="address">Job position:</label>
                            <select class="form-select" aria-label="Default select example">
                                <option selected>Server</option>
                                <option value="1">Manager</option>
                                <option value="2">Server</option>
                                <option value="3">Bartender</option>
                            </select>
                        </div>
                         <div class="modal-footer">
                    		<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    		<button type="submit" name = "saveAddEmployee" class="btn btn-primary add-save" data-bs-dismiss="modal">Save changes</button>
                		</div>
                    </form>
                </div>
              
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
        crossorigin="anonymous"></script>
</body>
</html>