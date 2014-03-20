<%-- 
    Document   : changePassword
    Created on : Nov 8, 2012, 4:56:03 AM
    Author     : Uday
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
		.left{
			width:25%;
			padding:20px;
			margin:0px;
			text-align: center;
			float:left;
		}
		.right{
			width:66%;
			padding:20px;
			margin:10px;
			float:left;
			border:2px solid #a1a1a1;
		}
		#navigation{
			margin:0px;
			text-align:left;
		}
		a:link {text-decoration: none;}
		a:visited {text-decoration: none;}
		a:active {text-decoration: none;}
		#navigation a:hover {color: blue;}
                .hotlinks a:link {text-decoration: none;}
		.hotlinks a:visited {text-decoration: none;}
		.hotlinks a:active {text-decoration: none;}
		.hotlinks a:hover {color: blue;}
		#navigation h3 {font-weight:bold;}
		.ui-accordion .ui-accordion-header {
			display: block;
			cursor: pointer;
			position: relative;
			margin-top: 0px;
			padding: .1em .1em .1em 1em;
			zoom: 1;
		}
		.ui-accordion .ui-accordion-content {
			padding: 0.2em 2.2em;
			border-top: 0;
			overflow: auto;
			zoom: 1;
		}
		.miniprofile{
			margin-top:10px;
			margin-bottom:20px;
			height: 250px;
		}
		.para{
			font-family: Georgia;
			font-size:20px;
			font-weight: bold;
		}
		.ui-widget-header {
			border: 1px solid #AAA/*{borderColorHeader}*/;
			background: #088A08/*{bgColorHeader}*/ url(http://localhost/dbproject/images/ui-bg_highlight-soft_75_cccccc_1x100.png)/*{bgImgUrlHeader}*/ 50%/*{bgHeaderXPos}*/ 50%/*{bgHeaderYPos}*/ repeat-x/*{bgHeaderRepeat}*/;
			color: #222/*{fcHeader}*/;
			font-weight: bold;
		}
		.projectdetails{
			margin:10px;
		}
	</style>
        <script type="text/javascript">
            function validatePassword(){
                var currpwd = document.getElementById("currentPwd").value;
                var newpwd = document.getElementById("newPwd").value;
                var retypepwd = document.getElementById("retypePwd").value;
                if((currpwd == "") || (newpwd == "") || (retypepwd =="") || (newpwd != retypepwd)){
                    alert("Incorrect entries. Please enter again");
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <div id="wrapper">
		<div class="left">
			<div class="miniprofile">
				<img src="http://localhost/dbproject/images/img1.jpg" height=60%;/>
				<%--<h1 style="color:#A4A4A4;"><% out.print(emp_name); %></h1>
				<h1 style="color:#848484;"><% out.print(emp_post); %></h1>--%>
			</div>
			<div id="navigation">
			    <h3>Home</h3>
			    <div>
			        <p>
			        	<a href='home.html'>Show Projects</a>
			        </p>
			        <p>
			        	<a href='logout.html'>Logout</a>
			        </p>
			    </div>
			    <h3>Profile</h3>
			    <div>
			        <p>
			        	<a href='view_profile.html'>View Profile</a>
			        </p>
			        <p>
			        	<a href='edit_profile.html'>Edit profile</a>
			        </p>
			    </div>
			    <h3>Change Password</h3>
			    <div>
			        <p>
			        	<a href='change_password.html'>Change Your Password</a>
			        </p>
			    </div>
			    <h3>Help</h3>
			    <div>
			        <p>
			        	<a href='help.html'>FAQ</a>
			        </p>
			        <p>
			        	<a href='contact_us.html'>Contact Us</a>
			        </p>
			    </div>
			</div>
		</div>
		<div class="right">
			<form action="changePassword" name="frmLogin" method="post" onsubmit="return validatePassword ()">
                <table>
                    <tr>
                        <td> Current password  : </td><td> <input name="currentpassword" id = "currpwd" size=15 type="password" /> </td> 
                    </tr>
                    <tr>
                        <td> New password  : </td><td> <input name="newpassword" id = "newpwd" size=15 type="password" /> </td> 
                    </tr>
                    <tr>
                        <td> Retype new password  : </td><td> <input name="retypepassword" id = "retypepwd" size=15 type="password" /> </td> 
                    </tr>
                </table>
                <input type="submit" value="login" />
            </form>
			
		</div>
	</div>
    </body>
</html>