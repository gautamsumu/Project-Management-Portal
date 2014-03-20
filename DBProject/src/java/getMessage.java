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
 * @author anirudh
 */
@WebServlet(name = "getMessage", urlPatterns = {"/getMessage"})
public class getMessage extends HttpServlet {

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
    private static final String INBOX_QUERY = "SELECT employee.name, message, time, flag, message_id from employee, message where to_id=? and employee_id = from_id";
    private static final String SENT_QUERY = "SELECT employee.name, message, time, flag, message_id from employee, message where from_id=? and employee_id = to_id";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet getMessage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet getMessage at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession s = request.getSession();
        String emp_id = (String)s.getAttribute("emp_id");
        String flag = request.getParameter("type");
        out = response.getWriter();

        if(emp_id == null){
            System.out.println("Session expired.");
            out.println("Session expired.");
        }
        else{
            try {
                getMessages(emp_id, flag);
            } catch (Exception ex) {
                Logger.getLogger(getMessage.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
        }
    }

    Connection connect() throws Exception {
        Connection con = null;
        try {
            String url = "jdbc:mysql://"+HOST+":3306/"+DBNAME+"?user="+DB_USERNAME+"&password="+DB_PASSWORD;
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

    private void getMessages(String id, String flag) throws Exception {
        Connection con;
        con = null;
        String QUERY;
        if(flag.equals("1")) {
            QUERY = SENT_QUERY;
        }
        else {
            QUERY = INBOX_QUERY;
        }
         try {
            con = connect();
            System.out.println("Getting the tasks!");
            PreparedStatement prepStmt = con.prepareStatement(QUERY);
            prepStmt.setString(1, id);
            ResultSet rs = prepStmt.executeQuery();
            String result="";
            while(rs.next()){
                if(result.equals("")) {
                    result += rs.getString(1)+";;"+rs.getString(2)+";;"+rs.getString(3)+";;"+rs.getString(4)+";;"+rs.getString(5);
                }
                else {
                    result += ";;;"+rs.getString(1)+";;"+rs.getString(2)+";;"+rs.getString(3)+";;"+rs.getString(4)+";;"+rs.getString(5);
                }
            }
            if (result.equals("")) {
                result = "No Messages to display!";
            }
            System.out.println(result);
            out.println(result);
         } catch (Exception ex) {
            System.out.println("Error while getting the project: "+ex.getMessage());
         } finally {
            con.close();
         }
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
