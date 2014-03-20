/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gautam
 */
@WebServlet(name = "EditProfile", urlPatterns = {"/EditProfile"})
public class EditProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   private static final String HOST = "localhost";
    private static final String DBNAME = "db_project";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static final String QUERY = "SELECT * FROM employee WHERE employee_id=?";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetTask</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetTask at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    PrintWriter out=null;
    String employee_type;
    String emp_name;
    String emp_post;
    String action;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        action =  request.getParameter("action").toString();
        if(action.equals("0"))action="<span style='color:#ff0000'>Sorry. Problem with connection. Please Try Again!</span>";
        else  if(action.equals("1"))action="<span style='color:#00ff00'>Changes saved successfully</span>";
         response.setContentType("text/html;charset=UTF-8");
        HttpSession s = request.getSession();
         employee_type= (String)s.getAttribute("pos");
        emp_name = (String)s.getAttribute("emp_name");
        emp_post = (String)s.getAttribute("pos");
        String emp_id = (String)s.getAttribute("emp_id");
        out = response.getWriter();
        //String emp_type = (String)s.getAttribute("emp_type");
        if(emp_id == null){
            System.out.println("Session expired.");
            out.println("Session expired.");
        }
        else{
                    try {
                        boolean haveTask = getDetails(emp_id);
                        if(!haveTask){
                            out.println("No Task.");
                        }
                        else{
                            System.out.println("Task Done.");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GetTaskEngg.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        out.close();
                    }
        }
    }

    private boolean getDetails(String emp_id) throws SQLException{
        Connection con;
        con = null;
         try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(QUERY);
            prepStmt.setString(1, emp_id);
            ResultSet rs = prepStmt.executeQuery();
            String result="";
            out.println("<script src=\"http://localhost/dbproject/js/jquery-1.6.2.min.js\"></script>\n" +
"	<script src=\"http://localhost/dbproject/js/jquery-ui-1.8.16.custom.min.js\"></script>\n" +
"	<link rel=\"stylesheet\" href=\"http://localhost/dbproject/css/jquery-ui.css\" />\n" +
"	<link rel=\"stylesheet\" href=\"http://localhost/dbproject/css/style.css\" />"
                    + "<style>\n" +
"		.left{\n" +
"			width:25%;\n" +
"			padding:20px;\n" +
"			margin:0px;\n" +
"			text-align: center;\n" +
"			float:left;\n" +
"		}\n" +
"		.right{\n" +
"			width:66%;\n" +
"			padding:20px;\n" +
"			margin:10px;\n" +
"			float:left;\n" +
"			border:2px solid #a1a1a1;\n" +
"		}\n" +
"		#navigation{\n" +
"			margin:0px;\n" +
"			text-align:left;\n" +
"		}\n" +
"		a:link {text-decoration: none;}\n" +
"		a:visited {text-decoration: none;}\n" +
"		a:active {text-decoration: none;}\n" +
"		#navigation a:hover {color: blue;}\n" +
"                .hotlinks a:link {text-decoration: none;}\n" +
"		.hotlinks a:visited {text-decoration: none;}\n" +
"		.hotlinks a:active {text-decoration: none;}\n" +
"		.hotlinks a:hover {color: blue;}\n" +
"		#navigation h3 {font-weight:bold;}\n" +
"		.ui-accordion .ui-accordion-header {\n" +
"			display: block;\n" +
"			cursor: pointer;\n" +
"			position: relative;\n" +
"			margin-top: 0px;\n" +
"			padding: .1em .1em .1em 1em;\n" +
"			zoom: 1;\n" +
"		}\n" +
"		.ui-accordion .ui-accordion-content {\n" +
"			padding: 0.2em 2.2em;\n" +
"			border-top: 0;\n" +
"			overflow: auto;\n" +
"			zoom: 1;\n" +
"		}\n" +
"		.miniprofile{\n" +
"			margin-top:10px;\n" +
"			margin-bottom:20px;\n" +
"			height: 250px;\n" +
"		}\n" +
"		.para{\n" +
"			font-family: Georgia;\n" +
"			font-size:20px;\n" +
"			font-weight: bold;\n" +
"		}\n" +
"		.ui-widget-header {\n" +
"			border: 1px solid #AAA/*{borderColorHeader}*/;\n" +
"			background: #088A08/*{bgColorHeader}*/ url(http://localhost/dbproject/images/ui-bg_highlight-soft_75_cccccc_1x100.png)/*{bgImgUrlHeader}*/ 50%/*{bgHeaderXPos}*/ 50%/*{bgHeaderYPos}*/ repeat-x/*{bgHeaderRepeat}*/;\n" +
"			color: #222/*{fcHeader}*/;\n" +
"			font-weight: bold;\n" +
"		}\n" +
"		.projectdetails{\n" +
"			margin:10px;\n" +
"		}\n" +
"	</style>"
                    + "<div id=\"wrapper\">\n" +
"		<div class=\"left\">\n" +
"            <div class=\"miniprofile\">\n" +
"                <img src=\"http://localhost/dbproject/images/img"+emp_id+".jpg\" height=60%;/>\n" +
"                <h1 style=\"color:#A4A4A4;\">"+emp_name+"</h1>\n" +
"                <h1 style=\"color:#848484;\">"+emp_post+"</h1>\n" +
"            </div>\n" +
"            <div id=\"navigation\">\n" +
"                <h3>Home</h3>\n" +
"                <div>\n" +
"                    <p>\n" +
"                        <a href='newhome.jsp'>Projects</a>\n" +
"                    </p>\n" +
"                    <p>\n" +
"                        <a href='LogOut'>Logout</a>\n" +
"                    </p>\n" +
"                </div>\n" +
"                            <h3>Messages</h3>\n" +
"                <div>\n" +
"                    <p>\n" +
"                                    <a href='#' onclick='display_inbox()'>Inbox</a>\n" +
"                    </p>\n" +
"                    <p>\n" +
"                                    <a href='#' onclick='display_sent()'>Sent</a>\n" +
"                    </p>\n" +
"                </div>\n" +
"                <h3>Profile</h3>\n" +
"                <div>\n" +
"                    <p>\n" +
"                        <a href='EditProfile?action=2'>View/Edit Profile</a>\n" +
"                    </p>\n" +
"                    <p>\n" +
"                        <a href='changePassword.jsp'>Change Your Password</a>\n" +
"                    </p>\n" +
"                </div>\n" +
"                <h3>Help</h3>\n" +
"                <div>\n" +
"                    <p>\n" +
"                        <a href='help.html'>FAQ</a>\n" +
"                    </p>\n" +
"                    <p>\n" +
"                        <a href='contact_us.html'>Contact Us</a>\n" +
"                    </p>\n" +
"                </div>\n" +
"            </div>\n" +
"        </div>\n" +
"<script>\n" +
"	function display_inbox(){\n" +
"                var from=new Array();\n" +
"                var msgs=new Array();\n" +
"                var time=new Array();\n" +
"                var status=new Array();\n" +
"                var ids=new Array();\n" +
"                $.ajax({type:\"POST\", url:\"getMessage\",  data:\"type=\"+\"0\", success:function(data) {\n" +
"                        //do nothing\n" +
"                            var arr=data.split(\";;;\");\n" +
"                            var ctr=0;\n" +
"                            for(var x=0;x<arr.length; x++){\n" +
"                                var arr2=arr[x].split(\";;\");\n" +
"                                alert(arr[x]);\n" +
"                                from[ctr]=arr2[0];\n" +
"                                msgs[ctr]=arr2[1];\n" +
"                                time[ctr]=arr2[2];\n" +
"                                status[ctr]=arr2[3];\n" +
"                                ids[ctr]=arr2[4];\n" +
"                                ctr++;\n" +
"                            }\n" +
"                            var code = \"\";\n" +
"                            for(var x=0;x<from.length;x++){\n" +
"                                    var type = \"old\";\n" +
"                                    if(status[x] == '1')\n" +
"                                        type = \"newest\";\n" +
"                                    code+=\"<tr><div class='\"+type+\"' ><td>\"+from[x]+\"</td><td>\"+time[x]+\" </td><td>\"+msgs[x]+\"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='\"+ids[x]+\"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>\";\n" +
"                            }\n" +
"                            document.getElementById(\"inbox\").innerHTML=\"<div><table width=100%><tr><td>From:</td><td>TIme:</td><td>Message:</td></tr>\"+code+\"</table></div>\";\n" +
"                        }\n" +
"                });\n" +
"                document.getElementById('projects').style.display=\"none\";\n" +
"                document.getElementById('outMsg').style.display=\"none\";\n" +
"                document.getElementById('inMsg').style.display=\"block\";\n" +
"	    }\n" +
"\n" +
"        function display_sent(){\n" +
"                var to=new Array();\n" +
"                var msgs=new Array();\n" +
"                var time=new Array();\n" +
"                var status=new Array();\n" +
"                var ids=new Array();\n" +
"                $.ajax({type:\"POST\", url:\"getMessage\",  data:\"type=\"+\"1\", success:function(data) {\n" +
"                        //do nothing\n" +
"                            var arr=data.split(\";;;\");\n" +
"                            var ctr=0;\n" +
"                            for(var x=0;x<arr.length; x++){\n" +
"                                var arr2=arr[x].split(\";;\");\n" +
"                                to[ctr]=arr2[0];\n" +
"                                msgs[ctr]=arr2[1];\n" +
"                                time[ctr]=arr2[2];\n" +
"                                status[ctr]=arr2[3];\n" +
"                                ids[ctr]=arr2[4];\n" +
"                                ctr++;\n" +
"                            }\n" +
"                            var code = \"\";\n" +
"                            for(var x=0;x<to.length;x++){\n" +
"                                    var type = \"old\";\n" +
"                                    code+=\"<tr><div class='\"+type+\"'><td>\"+to[x]+\"</td><td>\"+time[x]+\" </td><td>\"+msgs[x]+\"</td><td><div id='create' style='float:right'><form method='post' action='deleteMessage' onsubmit='deleteMessage'><input type='hidden' name='id' value='\"+ids[x]+\"' /> <input type='submit' value='Delete'/></form></div></td></div></tr>\";\n" +
"                            }\n" +
"                            document.getElementById(\"sent\").innerHTML=\"<table width=100%><tr><td>To:</td><td>TIme:</td><td>Message:</td></tr>\"+code+\"</table>\";\n" +
"                        }\n" +
"        });}\n" +
"</script>\n" +
"\n" +
"		<div class=\"right\" id='projects'>"
                    + action+"<script>\n" +
"	    \n" +
"	    $(function() {\n" +
"	        $( \"#navigation\" ).accordion();\n" +
"	    });</script>");
            if(rs.next()){
                 String name=rs.getString("name");
                 String dob=rs.getString("dob");
                 String address=rs.getString("address");
                 String email_id=rs.getString("email_id");
                 String phone=rs.getString("phone");
                 out.println("<table><form action='updateProfile' method='post'>\n" +
"            <tr><td>Name</td> <td><input type=\"text\" name=\"name\" id=\"name\" value='"+name+"'/></td><tr/>\n" +
"            <tr><td>DOB</td> <td><input type=\"text\" name=\"dob\" id=\"dob\" value='"+dob+"'/></td><tr/>\n" +
"            <tr><td>Address</td> <td><input type=\"text\" name=\"address\" id=\"address\" value='"+address+"'/></td><tr/>\n" +
"            <tr><td>Email ID</td> <td><input type=\"text\" name=\"email_id\" id=\"email_id\" value='"+email_id+"'/></td><tr/>\n" +
"            <tr><td>Phone</td> <td><input type=\"text\" name=\"phone\" id=\"phone\" value='"+phone+"'/></td><tr/>\n" +
"            <tr><td><input type=\"submit\" value=\"Edit\" id=\"submit\" /></td></tr></table>               \n" +
"        </form></div><div id=\"inMsg\" class=\"right\" style=\"display:none\">\n" +
"			<h1 style=\"color:#848484;\">Messages</h1>\n" +
"                        <h2 style=\"color:#848484;\">Inbox</h2>\n" +
"			<div id=\"inbox\" class=\"inbox\" style=\"border:1px solid #a1a1a1;padding:10px\">\n" +
"			</div>\n" +
"                        <div id=\"create\"><a href=\"#createform\" rel=\"facebox\"><button type=\"button\" />Compose Message</button></a></div>\n" +
"		</div>\n" +
"                <div id=\"outMsg\" class=\"right\" style=\"display:none\">\n" +
"			<h1 style=\"color:#848484;\">Messages</h1>\n" +
"                        <h2 style=\"color:#848484;\">Sent</h2>\n" +
"			<div id=\"sent\" class=\"sent\" style=\"border:1px solid #a1a1a1;padding:10px\">\n" +
"			</div>\n" +
"                        <div id=\"create\"><a href=\"#createform\" rel=\"facebox\"><button type=\"button\" />Compose Message</button></a></div>\n" +
"		</div>");
            }else{
                 out.println("Error in connection");
            }
            System.out.println(result);
            out.println(result);
         } catch (Exception ex) {
            System.out.println("Error while getting the project: "+ex.getMessage());
            return false;
         } finally {
            con.close();
         }
         return true;
    }

    Connection connect() throws Exception {
        Connection con = null;
        try {
            String url = "jdbc:mysql://"+HOST+":3306/" + DBNAME + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            System.out.println("SQLException: Unable to open connection to db: " + sqle.getMessage());
            throw sqle;
        } catch (Exception e) {
            System.out.println("Exception: Unable to open connection to db: " + e.getMessage());
            throw e;
        }

        return con;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
