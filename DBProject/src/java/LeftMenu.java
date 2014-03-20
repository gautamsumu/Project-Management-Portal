/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vaio
 */
@WebServlet(name = "LeftMenu", urlPatterns = {"/LeftMenu"})
public class LeftMenu extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<div class=\"left\">\n" +
"                            <div class=\"miniprofile\">\n" +
"                                    <img src=\"http://localhost/dbproject/images/img1.jpg\" height=60%;/>\n" +
"                                    <h1 style=\"color:#A4A4A4;\">Chandan Kumar</h1>\n" +
"                                    <h1 style=\"color:#848484;\">CEO</h1>\n" +
"                            </div>\n" +
"                            <div id=\"navigation\">\n" +
"                                <h3>Home</h3>\n" +
"                                <div>\n" +
"                                    <p>\n" +
"                                            <a href='home.html'>Show Projects</a>\n" +
"                                    </p>\n" +
"                                    <p>\n" +
"                                            <a href='LogOut'>Logout</a>\n" +
"                                    </p>\n" +
"                                </div>\n" +
"                                <h3>Profile</h3>\n" +
"                                <div>\n" +
"                                    <p>\n" +
"                                            <a href='view_profile.html'>View Profile</a>\n" +
"                                    </p>\n" +
"                                    <p>\n" +
"                                            <a href='edit_profile.html'>Edit profile</a>\n" +
"                                    </p>\n" +
"                                </div>\n" +
"                                <h3>Change Password</h3>\n" +
"                                <div>\n" +
"                                    <p>\n" +
"                                            <a href='change_password.html'>Change Your Password</a>\n" +
"                                    </p>\n" +
"                                </div>\n" +
"                                <h3>Help</h3>\n" +
"                                <div>\n" +
"                                    <p>\n" +
"                                            <a href='help.html'>FAQ</a>\n" +
"                                    </p>\n" +
"                                    <p>\n" +
"                                            <a href='contact_us.html'>Contact Us</a>\n" +
"                                    </p>\n" +
"                                </div>\n" +
"                            </div>\n" +
"                    </div>");
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
