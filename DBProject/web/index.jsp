<%--
    Document   : index
    Created on : Nov 5, 2012, 11:47:56 PM
    Author     : gautam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Management Portal</title>
    </head>
    <body>
       <%@page import="javax.servlet.http.HttpSession" %>
        <%
            HttpSession s = request.getSession();
            String emp_id = (String)s.getAttribute("emp_id");
            if(emp_id != null)
                response.sendRedirect("empHome.jsp");
        %>
       <div align ="center">
        <h1>Project Management Portal</h1>
        <form action="checkLogin" name="frmLogin" method="post" onsubmit="checkLogin">
            <table>
                <tr>
                    <td> Employee ID </td><td> <input name="emp_id" size=15 type="text" /> </td>
                </tr>
                <tr>
                    <td> Password </td><td> <input name="password" size=15 type="password" /> </td>
                </tr>
            </table>
            <input type="submit" value="Login" />
        </form>
        </div>
    </body>
</html>