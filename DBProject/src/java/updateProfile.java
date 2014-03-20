/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
@WebServlet(name = "updateProfile", urlPatterns = {"/updateProfile"})
public class updateProfile extends HttpServlet {
    private static final String HOST = "localhost";
    private static final String DBNAME = "db_project";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static final String UPDATE_PROFILE = "update employee set name=?, dob=?, address=?, email_id=?, phone=? where employee_id=?";
    
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
            out.println("<title>Servlet updateProfile</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateProfile at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String name, dob, email, phone, addr, emp_id;
        HttpSession session = request.getSession();
        emp_id=(String)session.getAttribute("emp_id");
        name = request.getParameter("name");
        dob = request.getParameter("dob");
        email = request.getParameter("email_id");
        phone = request.getParameter("phone");
        addr = request.getParameter("address");
        try {
            updateEmployee(emp_id, name, dob, email, phone, addr);
            response.sendRedirect("EditProfile?action=1");
        } catch (Exception ex) {
            Logger.getLogger(updateProfile.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("EditProfile?action=0");
        }
        //response.sendRedirect("EditProfile?action=2");
    }

    
    Connection connect() throws Exception
    {
        Connection con=null;
        try
        {
            String url = "jdbc:mysql://"+HOST+":3306/"+DBNAME+"?user="+DB_USERNAME+"&password="+DB_PASSWORD;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
        } 
        catch (SQLException sqle) 
        {
            System.out.println("SQLException: Unable to open connection to db: "+sqle.getMessage());
            throw sqle;
        }
         catch(Exception e)
        {
            System.out.println("Exception: Unable to open connection to db: "+e.getMessage());
            throw e;
        }
        return con;
    }
    
    private void updateEmployee(String emp_id, String name, String dob, String email, String phone, String addr) throws Exception  {
        Connection con;
        con = null;
        try {    
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(UPDATE_PROFILE);
            prepStmt.setString(1, name);
            prepStmt.setString(2, dob);
            prepStmt.setString(3, addr);
            prepStmt.setString(4, email);
            prepStmt.setString(5, phone);
            prepStmt.setString(6, emp_id);
            prepStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("validateLogon: Error while validating password: "+ex.getMessage());
            throw ex;
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
