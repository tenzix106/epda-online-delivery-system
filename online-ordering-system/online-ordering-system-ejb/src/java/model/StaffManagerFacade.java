/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.StaffManager;
/**
 * 
 *
 * @author melvin
 */
@Stateless
public class StaffManagerFacade extends AbstractFacade<StaffManager> {

    @PersistenceContext(unitName = "online-ordering-system-ejbPU")
    private EntityManager em;
    
    private static final Logger logger = Logger.getLogger(StaffManagerFacade.class.getName());
    
    @PersistenceContext(unitName = "APUDeliverySystemPU")
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StaffManagerFacade() {
        super(StaffManager.class);
    }
    
    public StaffManager findStaffByEmail(String email) {
        try {
            TypedQuery<StaffManager> query = em.createNamedQuery("Staff.findByEmail", StaffManager.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "No staff found with email: "+ email);
            return null;
        } catch (Exception e){
            logger.log(Level.SEVERE, "Error finding staff by email: " + email, e);
            return null;
        }
    }
    public void updateLastLogin(Integer staffId) {
        try{
            StaffManager staff = em.find(StaffManager.class, staffId);
            if(staff != null) {
                staff.setLastLogin(new Date());
                em.merge(staff);
                logger.log(Level.INFO, "Updated last login for staff ID: " + staffId);
                
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating last login for staff ID: " +staffId, e);
        }
    }
      /**
     * Creates a new staff member
     */
    public StaffManager createStaff(StaffManager staff) {
        try {
            if(staff.getStaffPassword() != null) {
                String hashedPassword = hashPassword(staff.getStaffPassword());
                staff.setStaffPassword(hashedPassword);
            }
            
            em.persist(staff);
            logger.log(Level.INFO, "Created new staff: " + staff.getStaffEmail());
            return staff;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating staff", e);
            throw new RuntimeException("Failed to create staff", e);
        }
    }
    
    public StaffManager updateStaff(StaffManager staff) {
        try {
            StaffManager updatedStaff = em.merge(staff);
            logger.log(Level.INFO, "Updated staff: " +staff.getStaffEmail());
            return updatedStaff;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating staff ID: " + staff.getId(), e);
            throw new RuntimeException("Failed to update staff", e);
        }
    }
    
    public StaffManager findStaffById(Integer staffId) {
        try {
            return em.find(StaffManager.class , staffId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding staff by ID: " + staffId, e);
            return null;
        }
    }
    
    public void deleteStaff(Integer staffId)  {
        try {
            StaffManager staff = em.find(StaffManager.class, staffId);
            if( staff != null ){
                staff.setStaffStatusEnum(StaffStatus.INACTIVE);
                em.merge(staff);
                logger.log(Level.INFO, "Soft deleted staff ID: " + staffId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting staff ID: " + staffId, e);
            throw new RuntimeException("Failed to delete staff", e);
        }
    }
    
    
    
    
    
    
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for(byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error hashing password", e);
            throw new RuntimeException("Failed to hash password", e);
        }
    }
}
