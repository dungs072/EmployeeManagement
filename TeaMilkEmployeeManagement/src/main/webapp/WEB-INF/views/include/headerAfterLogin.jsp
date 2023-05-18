<%@include file="/WEB-INF/views/include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,
				initial-scale=1.0">
	<title>TeaMilk</title>
	<link rel="icon" type="image/png" href="images/logo1.png">
    <script>
	    window.addEventListener('load',function(){
	        let menuicn = document.querySelector(".menuicn");
	        let nav = document.querySelector(".navcontainer");
	        menuicn.addEventListener("click", () => {
	            nav.classList.toggle("navclose");
	        })
	    });
	    $(document).ready( function() {
	    	let nav = document.querySelector(".navcontainer");
	    	nav.classList.toggle("navclose");
	    })
    </script>
    <style>
        /* Responsive CSS Here */
        @media screen and (max-width: 950px) {
            .nav-img {
                height: 25px;
            }

            .nav-option {
                gap: 30px;
            }

            .nav-option h3 {
                font-size: 15px;
            }

            .report-topic-heading,
            .item1,
            .items {
                width: 800px;
            }
        }

        @media screen and (max-width: 850px) {
            .nav-img {
                height: 30px;
            }

            .nav-option {
                gap: 30px;
            }

            .nav-option h3 {
                font-size: 20px;
            }

            .report-topic-heading,
            .item1,
            .items {
                width: 700px;
            }

            .navcontainer {
                width: 100vw;
                position: absolute;
                transition: all 0.6s ease-in-out;
                top: 0;
                left: -100vw;
            }

            .nav {
                width: 100%;
                position: absolute;
            }

            .navclose {
                left: 00px;
            }


            .main {
                padding: 40px 30px 30px 30px;
            }

        }

        @media screen and (max-width: 490px) {
            .message {
                display: none;
            }

            .logosec {
                width: 100%;

            }

            .logo {
                font-size: 20px;
            }

            .menuicn {
                height: 25px;
            }

            .nav-img {
                height: 25px;
            }

            .nav-option {
                gap: 25px;
            }

            .nav-option h3 {
                font-size: 12px;
            }

            .nav-upper-options {
                gap: 15px;
            }

            .recent-Articles {
                font-size: 20px;
            }

            .report-topic-heading,
            .item1,
            .items {
                width: 550px;
            }
        }

        @media screen and (max-width: 400px) {
            .recent-Articles {
                font-size: 17px;
            }

            .view {
                width: 60px;
                font-size: 10px;
                height: 27px;
            }

            .report-header {
                height: 60px;
                padding: 10px 10px 5px 10px;
            }

            .searchbtn img {
                height: 20px;
            }
        }

        @media screen and (max-width: 320px) {
            .recent-Articles {
                font-size: 12px;
            }

            .view {
                width: 50px;
                font-size: 8px;
                height: 27px;
            }

            .report-header {
                height: 60px;
                padding: 10px 5px 5px 5px;
            }

            .t-op {
                font-size: 12px;
            }

            .t-op-nextlvl {
                font-size: 10px;
            }

            .report-topic-heading,
            .item1,
            .items {
                width: 300px;
            }

            .report-body {
                padding: 10px;
            }

            .label-tag {
                width: 70px;
            }

            .searchbtn {
                width: 40px;
            }

            .searchbar2 input {
                width: 180px;
            }
        }
    </style>
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Poppins", sans-serif;
        }

        :root {
            --background-color1: #f6fafe;
            --background-color2: #f6fafe;
            --background-color3: #ededed;
            --background-color4: #ffffffa4;
            --primary-color: #4b49ac;
            --secondary-color: #0c007d;
            --Border-color: #3f0097;
            --one-use-color: #3f0097;
            --two-use-color: #5500cb;
        }

        body {
            background-color: var(--background-color4);
            max-width: 100%;
            overflow-x: hidden;
        }

        header {
            height: 70px;
            width: 100vw;
            padding: 0 30px;
            background-color: var(--background-color1);
            position: fixed;
            z-index: 100;
            box-shadow: 1px 1px 15px rgba(161, 182, 253, 0.825);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 27px;
            font-weight: 600;
            color: rgb(47, 141, 70);
        }

        .icn {
            height: 30px;
        }

        .menuicn {
            cursor: pointer;
        }

        .logosec {
        	margin-top:12px;
            align-items: center;
            justify-content: center;
            float:left;
        }
        .menu{
        	float:left;
        	margin-top:16px;
        }
        .message{
        	margin-top:12px;
        	align-items: center;
            justify-content: center;
            float:right;
        }

        .searchbar2 {
            display: none;
        }

        .logosec {
            gap: 60px;
        }

        .searchbar input {
            width: 250px;
            height: 42px;
            border-radius: 50px 0 0 50px;
            background-color: var(--background-color3);
            padding: 0 20px;
            font-size: 15px;
            outline: none;
            border: none;
        }

        .searchbtn {
            width: 50px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 0px 50px 50px 0px;
            background-color: var(--secondary-color);
            cursor: pointer;
        }

        .message {
            gap: 40px;
            position: relative;
            cursor: pointer;
        }

        .circle {
            height: 7px;
            width: 7px;
            position: absolute;
            background-color: #fa7bb4;
            border-radius: 50%;
            left: 19px;
            top: 8px;
        }

        .dp {
            height: 40px;
            width: 40px;
            background-color: #626262;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        .main-container {
            display: flex;
            width: 100vw;
            position: relative;
            top: 70px;
            z-index: 1;
        }

        .dpicn {
            height: 42px;
        }

        .main {
            height: calc(100vh - 70px);
            width: 100%;
            overflow-y: scroll;
            overflow-x: hidden;
            padding: 40px 30px 30px 30px;
        }

        .main::-webkit-scrollbar-thumb {
            background-image:
                linear-gradient(to bottom, rgb(0, 0, 85), rgb(0, 0, 50));
        }

        .main::-webkit-scrollbar {
            width: 5px;
        }

        .main::-webkit-scrollbar-track {
            background-color: #9e9e9eb2;
        }

        .box-container {
            display: flex;
            justify-content: space-evenly;
            align-items: center;
            flex-wrap: wrap;
            gap: 50px;
        }

        .nav {
            min-height: 100vh;
            width: 250px;
            background-color: var(--background-color2);
            position: absolute;
            top: 0px;
            left: 00;
            box-shadow: 1px 1px 10px rgba(198, 189, 248, 0.825);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            overflow: hidden;
            padding: 0px 0 20px 10px;
        }

        .navcontainer {
            height: calc(100vh - 70px);
            width: 250px;
            position: relative;
            overflow-y: scroll;
            overflow-x: hidden;
            transition: all 0.5s ease-in-out;
        }

        .navcontainer::-webkit-scrollbar {
            display: none;
        }

        .navclose {
            width: 75px;
        }

        .nav-option {
            width: 250px;
            height: 60px;
            display: flex;
            align-items: center;
            padding: 0 30px 0 20px;
            gap: 20px;
            transition: all 0.1s ease-in-out;
        }

        .nav-option:hover {
            border-left: 5px solid #a2a2a2;
            background-color: #dadada;
            cursor: pointer;
        }

        .nav-img {
        	margin-right: 20px;
        	margin-left:-15px;
            height: 30px;
        }

        .nav-upper-options {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 30px;
        }

        .option1 {
            border-left: 5px solid #010058af;
            background-color: var(--Border-color);
            color: white;
            cursor: pointer;
        }

        .option1:hover {
            border-left: 5px solid #010058af;
            background-color: var(--Border-color);
        }

        .box {
            height: 130px;
            width: 250px;
            border-radius: 20px;
            box-shadow: 3px 3px 10px rgba(0, 30, 87, 0.751);
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: space-around;
            cursor: pointer;
            transition: transform 0.3s ease-in-out;
        }

        .box:hover {
            transform: scale(1.08);
        }

        .box:nth-child(1) {
            background-color: var(--one-use-color);
        }

        .box:nth-child(2) {
            background-color: var(--two-use-color);
        }

        .box:nth-child(3) {
            background-color: var(--one-use-color);
        }

        .box:nth-child(4) {
            background-color: var(--two-use-color);
        }

        .box img {
            height: 50px;
        }

        .box .text {
            color: white;
        }

        .topic {
            font-size: 13px;
            font-weight: 400;
            letter-spacing: 1px;
        }

        .topic-heading {
            font-size: 30px;
            letter-spacing: 3px;
        }

        .report-container {
            min-height: 300px;
            max-width: 1200px;
            margin: 70px auto 0px auto;
            background-color: #ffffff;
            border-radius: 30px;
            box-shadow: 3px 3px 10px rgb(188, 188, 188);
            padding: 0px 20px 20px 20px;
        }

        .report-header {
            height: 80px;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 20px 20px 10px 20px;
            border-bottom: 2px solid rgba(0, 20, 151, 0.59);
        }

        .recent-Articles {
            font-size: 30px;
            font-weight: 600;
            color: #5500cb;
        }

        .view {
            height: 35px;
            width: 90px;
            border-radius: 8px;
            background-color: #5500cb;
            color: white;
            font-size: 15px;
            border: none;
            cursor: pointer;
        }

        .report-body {
            max-width: 1160px;
            overflow-x: auto;
            padding: 20px;
        }

        .report-topic-heading,
        .item1 {
            width: 1120px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .t-op {
            font-size: 18px;
            letter-spacing: 0px;
        }

        .items {
            width: 1120px;
            margin-top: 15px;
        }

        .item1 {
            margin-top: 20px;
        }

        .t-op-nextlvl {
            font-size: 14px;
            letter-spacing: 0px;
            font-weight: 600;
        }

        .label-tag {
            width: 100px;
            text-align: center;
            background-color: rgb(0, 177, 0);
            color: white;
            border-radius: 4px;
        }
		
    </style>

    <style>
        .link{
            display:inline-block
        }
        .priority{
        	float:right;
        }
    </style>
	
</head>
<body>
	    <!-- for header part -->
    <header>

		<div class = "menu">

            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" class="bi bi-list icn menuicn" id = "menuicn" viewBox="0 0 16 16">
  			<path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
			</svg>
        </div>

        <div class="logosec">
            <div class="logo">T�amilk</div>
             
        </div>
        


        <div class="message">
            <div class="dp">
                <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210180014/profile-removebg-preview.png"
                    class="dpicn" alt="dp">
            </div>
        </div>

    </header>
    <script src="./index.js"></script>
</body>
<footer> </footer>
</html>