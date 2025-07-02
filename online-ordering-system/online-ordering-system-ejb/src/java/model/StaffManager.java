/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
/**
 *
 * @author melvin
 */
@Entity
@Table(name = "staff")
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByEmail", query = "SELECT s FROM Staff s WHERE s.staffEmail = :email"),
    @NamedQuery(name = "Staff.findByRole", query = "SELECT s FROM Staff s WHERE s.staffRole = :role"),
    @NamedQuery(name = "Staff.findActiveStaff", query = "SELECT s FROM Staff s WHERE s.staffStatus = 'active'"),
    @NamedQuery(name = "Staff.findAvailableDeliveryStaff", 
                query = "SELECT s FROM Staff s WHERE s.staffRole = 'delivery' AND s.staffStatus = 'active' AND s.staffAvailability = 'available'")
})
public class StaffManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staffId")
    private Integer staffId;
    
    @Column(name = "staffName", nullable = false, length = 100)
    private String staffName;
    
    @Column(name = "staffEmail", nullable = false, unique = true, length = 150)
    private String staffEmail;
    
    @Column(name = "staffPassword", nullable = false, length = 255)
    private String staffPassword;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "staffRole", nullable = false)
    private StaffRole staffRole;
    
    @Column(name = "staffPhone", nullable = false, length = 20)
    private String staffPhone;
    
    @Column(name = "staffAddress", nullable = false, columnDefinition = "TEXT")
    private String staffAddress;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "staffStatus", nullable = false)
    private StaffStatus staffStatus = StaffStatus.ACTIVE;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "staffHireDate", nullable = false)
    private Date staffHireDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "staffAvailability")
    private StaffAvailability staffAvailability = StaffAvailability.OFFLINE;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLogin")
    private Date lastLogin;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt")
    private Date updatedAt;

    public StaffManager(String staffName, String staffEmail, String staffPassword, 
                StaffRole staffRole, String staffPhone, String staffAddress) {
        this.staffName = staffName;
        this.staffEmail = staffEmail;
        this.staffPassword = staffPassword;
        this.staffRole = staffRole;
        this.staffPhone = staffPhone;
        this.staffAddress = staffAddress;
        this.staffStatus = StaffStatus.ACTIVE;
        this.staffAvailability = StaffAvailability.OFFLINE;
    }
    public enum StaffRole {
        MANAGING("managing"),
        DELIVERY("delivery");
        
        private final String value;
        
        StaffRole(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    public enum StaffStatus {
        ACTIVE("active"),
        INACTIVE("inactive");
        
        private final String value;
        
        StaffStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    
    public enum StaffAvailability {
        AVAILABLE("available"),
        BUSY("busy"),
        OFFLINE("offline");
        
        private final String value;
        
        StaffAvailability(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    
    
    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
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
    
    

    public Integer getStaffId() {
        return staffId;
    }

    public void setId(Integer id) {
        this.staffId = StaffId;
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
