/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arvind
 */
@Entity
@Table(name = "CONTRIBUTORS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contributors.findAll", query = "SELECT c FROM Contributors c"),
    @NamedQuery(name = "Contributors.findById", query = "SELECT c FROM Contributors c WHERE c.id = :id"),
    @NamedQuery(name = "Contributors.findByCompany", query = "SELECT c FROM Contributors c WHERE c.company = :company"),
    @NamedQuery(name = "Contributors.findByFname", query = "SELECT c FROM Contributors c WHERE c.fname = :fname"),
    @NamedQuery(name = "Contributors.findByLname", query = "SELECT c FROM Contributors c WHERE c.lname = :lname"),
    @NamedQuery(name = "Contributors.findByEmail", query = "SELECT c FROM Contributors c WHERE c.email = :email"),
    @NamedQuery(name = "Contributors.findByPrimaryContact", query = "SELECT c FROM Contributors c WHERE c.primaryContact = :primaryContact"),
    @NamedQuery(name = "Contributors.findByJobTitle", query = "SELECT c FROM Contributors c WHERE c.jobTitle = :jobTitle"),
    @NamedQuery(name = "Contributors.findByMobilePhone", query = "SELECT c FROM Contributors c WHERE c.mobilePhone = :mobilePhone"),
    @NamedQuery(name = "Contributors.findByAddress", query = "SELECT c FROM Contributors c WHERE c.address = :address")})
public class Contributors implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "COMPANY")
    private String company;
    @Column(name = "FNAME")
    private String fname;
    @Column(name = "LNAME")
    private String lname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PRIMARY_CONTACT")
    private Long primaryContact;
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;
    @Column(name = "ADDRESS")
    private String address;

    public Contributors() {
    }

    public Contributors(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        String oldCompany = this.company;
        this.company = company;
        changeSupport.firePropertyChange("company", oldCompany, company);
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        String oldFname = this.fname;
        this.fname = fname;
        changeSupport.firePropertyChange("fname", oldFname, fname);
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        String oldLname = this.lname;
        this.lname = lname;
        changeSupport.firePropertyChange("lname", oldLname, lname);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
    }

    public Long getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(Long primaryContact) {
        Long oldPrimaryContact = this.primaryContact;
        this.primaryContact = primaryContact;
        changeSupport.firePropertyChange("primaryContact", oldPrimaryContact, primaryContact);
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        String oldJobTitle = this.jobTitle;
        this.jobTitle = jobTitle;
        changeSupport.firePropertyChange("jobTitle", oldJobTitle, jobTitle);
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        String oldMobilePhone = this.mobilePhone;
        this.mobilePhone = mobilePhone;
        changeSupport.firePropertyChange("mobilePhone", oldMobilePhone, mobilePhone);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        String oldAddress = this.address;
        this.address = address;
        changeSupport.firePropertyChange("address", oldAddress, address);
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
        if (!(object instanceof Contributors)) {
            return false;
        }
        Contributors other = (Contributors) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "charity.Contributors[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
