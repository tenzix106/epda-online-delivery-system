/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author melvin
 */
@Entity
public class StaffManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String staffName;
    private String staffEmail;
    private String role;
    private String staffPhone;
    private String staffAddress;
    private String staffStatus;
    private Date staffHireDate;
    private String staffAvailability;

    public StaffManager(Integer id, String staffName, String staffEmail, String role, String staffPhone, String staffAddress, String staffStatus, Date staffHireDate, String staffAvailability) {
        this.id = id;
        this.staffName = staffName;
        this.staffEmail = staffEmail;
        this.role = role;
        this.staffPhone = staffPhone;
        this.staffAddress = staffAddress;
        this.staffStatus = staffStatus;
        this.staffHireDate = staffHireDate;
        this.staffAvailability = staffAvailability;
    }

    public StaffManager() {
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus;
    }

    public Date getStaffHireDate() {
        return staffHireDate;
    }

    public void setStaffHireDate(Date staffHireDate) {
        this.staffHireDate = staffHireDate;
    }

    public String getStaffAvailability() {
        return staffAvailability;
    }

    public void setStaffAvailability(String staffAvailability) {
        this.staffAvailability = staffAvailability;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StaffManager)) {
            return false;
        }
        StaffManager other = (StaffManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.StaffManager[ id=" + id + " ]";
    }
    
}
