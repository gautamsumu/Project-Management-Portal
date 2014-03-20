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
@WebServlet(name = "checkLogin", urlPatterns = {"/checkLogin"})
public class checkLogin extends HttpServlet {
    private static final String HOST = "10.4.11.63";
    private static final String DBNAME = "db_project";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "scorpio";
    private static final String LOGIN_QUERY = "select * from login where employee_id=? and password=?";
    private static final String POSITION_QUERY = "select position.name, employee.name from position, employee where position.post_id=employee.post_id and employee.employee_id=?";
    HttpSession session = null;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet checkLogin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet checkLogin at " + request.getContextPath() + "</h1>");
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
        try {
            String emp_id = request.getParameter("emp_id").toString();
            String pswd = request.getParameter("password").toString();
            String str_err;
            session = request.getSession();
            boolean is_valid_login=false;
            response.getWriter().println(is_valid_login);
            is_valid_login = authenticateLogin(emp_id, pswd);
            response.getWriter().println(is_valid_login);
            if(is_valid_login) {
                session.setAttribute("emp_id", emp_id);
                getPosition(emp_id);
                response.sendRedirect("newhome.jsp");
                System.out.println("Sucessfull Login");
            }
            else {
                str_err = "User name or Password is invalid. Please try again.";
                session.setAttribute("error", str_err);
                response.sendRedirect("index.jsp");
                System.out.println("Login failed");
            }
        } catch (Exception ex) {
            Logger.getLogger(checkLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean authenticateLogin(String emp_id, String pswd) throws Exception {

        boolean valid = false;
        Connection con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(LOGIN_QUERY);
            prepStmt.setString(1, emp_id);
            prepStmt.setString(2, pswd);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                valid = true;
            }
        } catch (Exception ex) {
            System.out.println("validateLogon: Error while validating password: "+ex.getMessage());
            throw ex;
        } finally {
            con.close();
        }
        return valid;
    }

    Connection connect() throws Exception {
        Connection con = null;
        try {
            String url = "jdbc:mysql://"+HOST+":3306/" + DBNAME + "?user=" +DB_USERNAME + "&password="+DB_PASSWORD;
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

     private void getPosition(String emp_id) throws Exception {
        String pos;
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(POSITION_QUERY);
            prepStmt.setString(1, emp_id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                pos = rs.getString(1);
                session.setAttribute("pos", pos);
                pos = rs.getString(2);
                session.setAttribute("emp_name", pos);
            }
        } catch (Exception ex) {
            System.out.println("validateLogon: Error while validating password: "+ex.getMessage());
            throw ex;
        } finally {
            con.close();
        }
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
