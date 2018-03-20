/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arvind
 */
@Entity
@Table(name = "FUNDRAISING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fundraising.findAll", query = "SELECT f FROM Fundraising f"),
    @NamedQuery(name = "Fundraising.findById", query = "SELECT f FROM Fundraising f WHERE f.id = :id"),
    @NamedQuery(name = "Fundraising.findByTitle", query = "SELECT f FROM Fundraising f WHERE f.title = :title"),
    @NamedQuery(name = "Fundraising.findByAssignedTo", query = "SELECT f FROM Fundraising f WHERE f.assignedTo = :assignedTo"),
    @NamedQuery(name = "Fundraising.findByPriority", query = "SELECT f FROM Fundraising f WHERE f.priority = :priority"),
    @NamedQuery(name = "Fundraising.findByStatus", query = "SELECT f FROM Fundraising f WHERE f.status = :status"),
    @NamedQuery(name = "Fundraising.findByCompletePercent", query = "SELECT f FROM Fundraising f WHERE f.completePercent = :completePercent"),
    @NamedQuery(name = "Fundraising.findByDescription", query = "SELECT f FROM Fundraising f WHERE f.description = :description"),
    @NamedQuery(name = "Fundraising.findByStartDate", query = "SELECT f FROM Fundraising f WHERE f.startDate = :startDate"),
    @NamedQuery(name = "Fundraising.findByDueDate", query = "SELECT f FROM Fundraising f WHERE f.dueDate = :dueDate")})
public class Fundraising implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "ASSIGNED_TO")
    private String assignedTo;
    @Column(name = "PRIORITY")
    private String priority;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "COMPLETE_PERCENT")
    private Short completePercent;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "DUE_DATE")
    private String dueDate;
    @JoinColumn(name = "CAMPAIGN", referencedColumnName = "ID")
    @ManyToOne
    private Campaigns campaign;

    public Fundraising() {
    }

    public Fundraising(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Short getCompletePercent() {
        return completePercent;
    }

    public void setCompletePercent(Short completePercent) {
        this.completePercent = completePercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Campaigns getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaigns campaign) {
        this.campaign = campaign;
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
        if (!(object instanceof Fundraising)) {
            return false;
        }
        Fundraising other = (Fundraising) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "charity.Fundraising[ id=" + id + " ]";
    }
    
}
