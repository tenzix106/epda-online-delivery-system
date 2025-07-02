/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.StaffManagerFacade;
import model.StaffManager;
/**
 *
 * @author melvin
 */
@WebServlet("/staffLogin")
public class StaffLogin extends HttpServlet {

    @EJB
    private StaffManagerFacade staffManagerFacade;
    
    private static final long serialVersionUID = 1L;
    
    public StaffLogin() {
        super();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffLogin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffLogin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void redirectToDashboard(HttpServletResponse response, String staffRole) 
            throws IOException {
        
        if (staffRole == null) {
            response.sendRedirect("login.jsp?error=invalid_role");
            return;
        }
        
        switch (staffRole.toLowerCase()) {
            case "managing":
                response.sendRedirect("managingDashboard.jsp");
                break;
            case "delivery":
                response.sendRedirect("deliveryDashboard.jsp");
                break;
            default:
                // Unknown role - redirect to login with error
                response.sendRedirect("login.jsp?error=invalid_role");
                break;
        }
    }
    private StaffManager authenticateStaff(String email, String password) {
        try {
            // Find staff by email using facade
            StaffManager staff = staffManagerFacade.findStaffByEmail(email);
            
            if (staff != null && "active".equals(staff.getStaffStatus())) {
                // Verify password
                if (verifyPassword(password, staff.getStaffPassword())) {
                    return staff;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error during staff authentication: " + e.getMessage());
        }
        
        return null; // Authentication failed
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("staffId") != null) {
            String staffRole = (String) session.getAttribute("staffRole");
            redirectToDashboard(response, staffRole);
            return;
        }
        
        // Redirect to login page
        response.sendRedirect("login.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String staffEmail = request.getParameter("staffEmail");
        String staffPassword = request.getParameter("staffPassword");
        
        // Validate input parameters
        if (staffEmail == null || staffEmail.trim().isEmpty() || 
            staffPassword == null || staffPassword.trim().isEmpty()) {
            
            response.sendRedirect("login.jsp?error=missing_fields");
            return;
        }
        
        try {
            // Authenticate staff member using EJB facade
            StaffManager staff = authenticateStaff(staffEmail.trim(), staffPassword);
            
            if (staff != null) {
                // Authentication successful - create session
                HttpSession session = request.getSession(true);
                session.setAttribute("staffId", staff.getStaffId());
                session.setAttribute("staffName", staff.getStaffName());
                session.setAttribute("staffEmail", staff.getStaffEmail());
                session.setAttribute("staffRole", staff.getStaffRole());
                session.setAttribute("staffStatus", staff.getStaffStatus());
                session.setAttribute("staffPhone", staff.getStaffPhone());
                session.setAttribute("staffAddress", staff.getStaffAddress());
                
                // If delivery staff, also store availability
                if ("delivery".equals(staff.getStaffRole())) {
                    session.setAttribute("staffAvailability", staff.getStaffAvailability());
                }
                
                // Set session timeout (30 minutes)
                session.setMaxInactiveInterval(1800);
                
                // Update last login time using facade
                staffManagerFacade.updateLastLogin(staff.getStaffId());
                
                // Log successful login
                System.out.println("Staff login successful: " + staff.getStaffEmail() + 
                                 " (Role: " + staff.getStaffRole() + ")");
                
                // Redirect to appropriate dashboard based on role
                redirectToDashboard(response, staff.getStaffRole());
                
            } else {
                // Authentication failed
                System.out.println("Failed login attempt for email: " + staffEmail);
                response.sendRedirect("login.jsp?error=invalid_credentials");
            }
            
        } catch (Exception e) {
            // Handle any unexpected errors
            System.err.println("Error during staff authentication: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=system_error");
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
