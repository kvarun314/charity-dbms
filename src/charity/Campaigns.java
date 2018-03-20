/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Arvind
 */
@Entity
@Table(name = "CAMPAIGNS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campaigns.findAll", query = "SELECT c FROM Campaigns c"),
    @NamedQuery(name = "Campaigns.findById", query = "SELECT c FROM Campaigns c WHERE c.id = :id"),
    @NamedQuery(name = "Campaigns.findByName", query = "SELECT c FROM Campaigns c WHERE c.name = :name"),
    @NamedQuery(name = "Campaigns.findByOwner", query = "SELECT c FROM Campaigns c WHERE c.owner = :owner"),
    @NamedQuery(name = "Campaigns.findByLaunchDate", query = "SELECT c FROM Campaigns c WHERE c.launchDate = :launchDate"),
    @NamedQuery(name = "Campaigns.findByDeadline", query = "SELECT c FROM Campaigns c WHERE c.deadline = :deadline"),
    @NamedQuery(name = "Campaigns.findByFundGoal", query = "SELECT c FROM Campaigns c WHERE c.fundGoal = :fundGoal"),
    @NamedQuery(name = "Campaigns.findByStatus", query = "SELECT c FROM Campaigns c WHERE c.status = :status")})
public class Campaigns implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "OWNER")
    private String owner;
    @Column(name = "LAUNCH_DATE")
    private String launchDate;
    @Column(name = "DEADLINE")
    private String deadline;
    @Column(name = "FUND_GOAL")
    private String fundGoal;
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "relatedCampaign")
    private Collection<Events> eventsCollection;
    @OneToMany(mappedBy = "campaign")
    private Collection<Donations> donationsCollection;
    @OneToMany(mappedBy = "campaign")
    private Collection<Fundraising> fundraisingCollection;

    public Campaigns() {
    }

    public Campaigns(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getFundGoal() {
        return fundGoal;
    }

    public void setFundGoal(String fundGoal) {
        this.fundGoal = fundGoal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Events> getEventsCollection() {
        return eventsCollection;
    }

    public void setEventsCollection(Collection<Events> eventsCollection) {
        this.eventsCollection = eventsCollection;
    }

    @XmlTransient
    public Collection<Donations> getDonationsCollection() {
        return donationsCollection;
    }

    public void setDonationsCollection(Collection<Donations> donationsCollection) {
        this.donationsCollection = donationsCollection;
    }

    @XmlTransient
    public Collection<Fundraising> getFundraisingCollection() {
        return fundraisingCollection;
    }

    public void setFundraisingCollection(Collection<Fundraising> fundraisingCollection) {
        this.fundraisingCollection = fundraisingCollection;
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
        if (!(object instanceof Campaigns)) {
            return false;
        }
        Campaigns other = (Campaigns) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "charity.Campaigns[ id=" + id + " ]";
    }
    
}
