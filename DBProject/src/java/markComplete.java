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
@WebServlet(name = "markComplete", urlPatterns = {"/markComplete"})
public class markComplete extends HttpServlet {

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
    //private static final String LOGIN_QUERY = "select * from user where username=? and password=?";
    private static final String CHECK_PROJECT = "select * from project where project_id=?";
    private static final String CHECK_TASK = "select project_id from task where task_id=?";
    private static final String UPDATE_PROJECT = "update project set status = 'c' where project_id=?";
    private static final String UPDATE_TASK = "update task set status = 'c' where task_id = ?";
    private static final String PROJECT_COMPLETE_QUERY = "select task.status from task, project where task.project_id=project.project_id and project.project_id=?";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet markComplete</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet markComplete at " + request.getContextPath() + "</h1>");
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
        String type, id;

        HttpSession session = request.getSession();

        type =  request.getParameter("type").toString();
        System.out.println(type);
        int int_type = Integer.parseInt (type);
        id = request.getParameter("id").toString();
        String project_id="";
        System.out.println("Updating status");
        try {
            project_id = checkAndUpdateID(type, id);
        } catch (Exception ex) {
            Logger.getLogger(markComplete.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean is_complete;
        if(!project_id.equals("")) {
            try {
                is_complete = isProjectComplete(project_id);
                if(is_complete){
                    /*
                     * Notification code
                     */
                    System.out.println("Project Complete");
                    checkAndUpdateID("1", project_id);
                }
            } catch (Exception ex) {
                Logger.getLogger(markComplete.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            /*
            * Notification code
            */
        }

        response.sendRedirect("newhome.jsp");

    }

    Connection connect() throws Exception
    {
        Connection con=null;
        try
        {
           String url = "jdbc:mysql://"+HOST+":3306/" + DBNAME + "?user=" +DB_USERNAME + "&password="+DB_PASSWORD;
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

    private String checkAndUpdateID (String type, String id) throws Exception {
        String project_id="";
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt;
            int int_type = Integer.parseInt(type);

            if (int_type == 1) {
                prepStmt = con.prepareStatement(CHECK_PROJECT);
            }
            else {
                prepStmt = con.prepareStatement(CHECK_TASK);
            }

            prepStmt.setString(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
            System.out.println("User login is valid in DB");
                if (int_type == 1) {
                    prepStmt = con.prepareStatement(UPDATE_PROJECT);
                }
                else {
                    project_id = rs.getString(1);
                    prepStmt = con.prepareStatement(UPDATE_TASK);
                }
                prepStmt.setString(1, id);
                prepStmt.executeUpdate();
                System.out.println("Updated");
            }
        } catch(Exception e) {
            System.out.println("validateLogon: Error while validating password: "+e.getMessage());
            throw e;
        } finally {
            con.close();
        }
        return project_id;
    }

    private boolean isProjectComplete(String project_id) throws Exception {
        boolean is_complete = false;
        Connection con;
        con = null;
        try {
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(PROJECT_COMPLETE_QUERY);
            prepStmt.setString(1, project_id);
            ResultSet rs = prepStmt.executeQuery();
            String s;
            is_complete=true;
            while(rs.next()) {
                s=rs.getString(1);
                if(!s.equals("c")) {
                    is_complete=false;
                    System.out.println("Project Incomplete");
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error while validating password: "+ex.getMessage());
            throw ex;
        } finally {
            con.close();
        }
        return is_complete;
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
