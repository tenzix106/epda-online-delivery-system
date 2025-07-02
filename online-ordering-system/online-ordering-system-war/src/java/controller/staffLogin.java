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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.StaffManagerFacade;

/**
 *
 * @author melvin
 */
public class staffLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private StaffManagerFacade staffManagerFacade;
    
    public staffLogin() {
        super();
    }
    
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("staffId") != null) {
            String staffRole = (String) session.getAttribute("staffRole");
            redirectToDashboard(response, staffRole);
            return;
        }
        
        response.sendRedirect("login.jsp");
    }
    /**
     * Handles POST requests - processes login form submission
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String staffEmail = request.getParameter("staffEmail");
        String staffPassword = request.getParameter("staffPassword");
        
        //Validate input parameters
        if (staffEmail = null || staffEmail.trim().isEmpty() || staffPassword == null || staffPassword.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=missing_fields");
            return;
        }
        try {
            Staff staff = authenticateStaff(staffEmail.trim(), staffPassword);
            
            if(staff != null ) {
                
            }
        }
    }
    /**
     * Authenticates staff member using EJB facade
     */
    
    private Staff authenticateStaff(String email, String password){
        try {
            Staff staff = StaffManagerFacade.findStaffByEmail(email);
            
            if(staff != null && "active".equals(staff.getStaffStatus())){
                //Verify password
                if(verifyPassword(password, staff.getStaffPassword())){
                    return staff;
                }
            }
        } catch (Exception e) {
            System.err.println("Error during staff authentication: " + e.getMessage());
        }
        return null;
    }
    /**
     * Verifies password against stored hash
     */
    
    private boolean verifyPassword(String inputPassword, String storedPassword){
        try {
            //Hash the input password
            String hashedInput = hashPassword(inputPassword);
            
            //Compare with stored password
            return hashedInput.equals(storedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
            return false;
        }
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance('SHA-256');
        byte[] hashBytes = md.digest(password.getBytes());
        
        StringBuilder sb = new StringBuilder();
        for(byte b : hashBytes ){
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
    /**
     * Redirects user to appropriate dashboard based on role
     */
    private void redirectToDashboard(HttpServletResponse response, String staffRole){
        throws IOException {
        if(staffRole == null) {
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
                //Unknown role - redirect to login with error
                response.sendRedirect("login.jsp?error=invalid_role");
                break;
                
        }
    }
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
        processRequest(request, response);
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
