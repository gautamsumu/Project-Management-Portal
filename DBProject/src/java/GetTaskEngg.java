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
@WebServlet(name = "GetTaskEngg", urlPatterns = {"/GetTaskEngg"})
public class GetTaskEngg extends HttpServlet {

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
    private static final String QUERY = "SELECT * FROM task WHERE employee_id=?";

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

        HttpSession s = request.getSession();
        String emp_id = (String)s.getAttribute("emp_id");
        out = response.getWriter();
        //String emp_type = (String)s.getAttribute("emp_type");
        if(emp_id == null){
            System.out.println("Session expired.");
            out.println("Session expired.");
        }
        else{
                    try {
                        boolean haveTask = getTask(emp_id);
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

    private boolean getTask(String emp_id) throws SQLException{
        Connection con;
        con = null;
         try {
            con = connect();
            System.out.println("Getting the tasks!");
            PreparedStatement prepStmt = con.prepareStatement(QUERY);
            prepStmt.setString(1, emp_id);
            ResultSet rs = prepStmt.executeQuery();
            String result="";
            while(rs.next()){
                 result += rs.getString("task_id")+";"+rs.getString("task_title")+";"+rs.getString("deadline")+";"+rs.getString("status")+";";
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
