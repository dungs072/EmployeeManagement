<%@include file = "/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<style>
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
        .topnav:checked > a {
        	background-color: #04AA6D;
            color: white;
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

	</script>
    
</head>
<body>
	<div class="topnav">
        <a class="active" href="#home">Home</a>
        <a href="Recruit.htm">Employees</a>
        <a href="JobAndFault.htm">Job&Fault</a>
        <a href="#about">About</a>
        <div class="topnav-right text-light">
            <p>ADMIN</p>
        </div>
    </div>
</body>
<footer>

</footer>
</html>