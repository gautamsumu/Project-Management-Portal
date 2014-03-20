<%-- 
    Document   : edit-profile
    Created on : 8 Nov, 2012, 5:47:29 AM
    Author     : Vaio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="http://localhost/dbproject/js/jquery-1.6.2.min.js"></script>
        <script src="http://localhost/dbproject/js/jquery-ui-1.8.16.custom.min.js"></script>
        <link rel="stylesheet" href="http://localhost/dbproject/css/jquery-ui.css" />
        <link rel="stylesheet" href="http://localhost/dbproject/css/style.css" />
    </head>
    <body>
        <form>
            <input type="text" name="name" id="name"/>
            <input type="text" name="dob" id="dob"/>
            <input type="text" name="address" id="address"/>
            <input type="text" name="email_id" id="email_id"/>
            <input type="text" name="phone" id="phone"/>            
            <input type="submit" value="Edit" id="submit"/>               
        </form>
    </body>
</html>
