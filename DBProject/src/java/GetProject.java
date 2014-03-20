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
@WebServlet(name = "GetProject", urlPatterns = {"/GetProject"})
public class GetProject extends HttpServlet {

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
    private static final String SELECT_QUERY = "SELECT * FROM project WHERE project_id=?";
    private static final String QUERY = "SELECT * FROM project";
    private static final String TOTALQUERY = "SELECT count(*) as ctr FROM task WHERE project_id=?";
    private static final String PARTIALQUERY = "SELECT count(*) as ctr FROM task WHERE project_id=? AND status='c'";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Project</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Project at " + request.getContextPath() + "</h1>");
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
        
        //@page import="javax.servlet.http.HttpSession";
        HttpSession s = request.getSession();
        String emp_id = (String)s.getAttribute("emp_id");
        //String emp_type = (String)s.getAttribute("emp_type");
        String project_id = request.getParameter("project_id").toString();
        if(project_id.equals("")){
            boolean done=false;
            out = response.getWriter();
            try {
                done = getProject("-99");
            } catch (Exception ex) {response.getWriter().println("Error1 "+ex.getMessage());
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            boolean done=false;
            out = response.getWriter();
            try {
                done = getProject(project_id);
            } catch (Exception ex) {response.getWriter().println("Error1 "+ex.getMessage());
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    private boolean getProject(String project_id){
        Connection con;
        con = null;
         try {    
            con = connect();
            //out.println("Getting the project!");
            if(project_id.equals("-99")){
                PreparedStatement prepStmt = con.prepareStatement(QUERY);
                ResultSet rs = prepStmt.executeQuery();
                String result="";
                while(rs.next()){
                    PreparedStatement prepStmt2 = con.prepareStatement(TOTALQUERY);
                    prepStmt2.setString(1, rs.getString("project_id"));
                    ResultSet rs2 = prepStmt2.executeQuery();
                    float status=0;
                    int total=0,complete=0;
                    if(rs2.next())total=rs2.getInt("ctr");
                    if(total!=0){
                        prepStmt2 = con.prepareStatement(PARTIALQUERY);
                        prepStmt2.setString(1, rs.getString("project_id"));
                        rs2 = prepStmt2.executeQuery();
                        if(rs2.next())complete=rs2.getInt("ctr");
                        status=100*complete/total;
                    }
                    if(result.equals(""))result=rs.getString("project_id")+";"+rs.getString("title")+";"+rs.getString("budget")+";"+rs.getString("deadline")+";"+rs.getString("emp_id")+";"+status;
                    else result=result+";;;"+rs.getString("project_id")+";"+rs.getString("title")+";"+rs.getString("budget")+";"+rs.getString("deadline")+";"+rs.getString("emp_id")+";"+status;
                }
                out.println(result);
            }
            else{
                PreparedStatement prepStmt = con.prepareStatement(SELECT_QUERY);
                prepStmt.setString(1, project_id);
                ResultSet rs = prepStmt.executeQuery();
                String result="";
                if(rs.next()){
                    result=rs.getString("project_id")+";"+rs.getString("title")+";"+rs.getString("budget")+";"+rs.getString("deadline")+";"+rs.getString("emp_id");
                    out.println(result);
                }
            }
         } catch (Exception ex) {
            out.println("Error while getting the project: "+ex.getMessage());
            return false;
            //throw ex;
         } finally {
            //con.close();
         }
         return true;
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
    
    
    /*
    private boolean addProject(String title, String budget, String deadline, String emp_id, String project_id) throws Exception {
        out.println("yo");
        Connection con;
        con = null;
        boolean inserted = false;
        String QUERY = INSERT_QUERY;
        if(!project_id.equals("")) {
            QUERY = UPDATE_QUERY;
        }
        try {    
            con = connect();
            out.println("Creating the project!");
            
            PreparedStatement prepStmt = con.prepareStatement(QUERY);
            prepStmt.setString(1, title);
            if(budget.equals("")) {
                prepStmt.setString(2, "0");out.println("nooo");
            }else {
                prepStmt.setString(2, budget);
            }
            if(deadline.equals("")) {
                prepStmt.setString(3, "2022-12-31");
            }else{
                prepStmt.setString(3, deadline);
            }
            if(emp_id.equals("")) {
                prepStmt.setString(4, "0");
            }else{
                prepStmt.setString(4, emp_id);
            }
            if(!project_id.equals("")) {
                prepStmt.setString(5, project_id);
            }
            prepStmt.executeUpdate();
            inserted = true;
            out.println("Created the project!");
        } catch (Exception ex) {
            out.println("Error while creating the project: "+ex.getMessage());
            throw ex;
        } finally {
            //con.close();
        }
        
        return inserted;
    }*/
    
    private String getProjectID() throws Exception {
        String project_id="";
        
        Connection con;
        con = null;
        try {    
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement("SELECT project_id FROM  `project` ORDER BY  `project_id` DESC LIMIT 0 , 1");
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            project_id = rs.getString(1);
        } catch (Exception ex) {
            System.out.println("Error while retrieving project for sending notification: "+ex.getMessage());
            throw ex;
        } finally {
            con.close();
        }
        
        return project_id;
    }
    
    private void sendNotification(String emp_id, String assign_to, String project_id) {
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
