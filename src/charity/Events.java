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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "EVENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Events.findAll", query = "SELECT e FROM Events e"),
    @NamedQuery(name = "Events.findById", query = "SELECT e FROM Events e WHERE e.id = :id"),
    @NamedQuery(name = "Events.findByName", query = "SELECT e FROM Events e WHERE e.name = :name"),
    @NamedQuery(name = "Events.findByDescription", query = "SELECT e FROM Events e WHERE e.description = :description"),
    @NamedQuery(name = "Events.findByOwner", query = "SELECT e FROM Events e WHERE e.owner = :owner"),
    @NamedQuery(name = "Events.findByStartDate", query = "SELECT e FROM Events e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Events.findByEndDate", query = "SELECT e FROM Events e WHERE e.endDate = :endDate"),
    @NamedQuery(name = "Events.findByStatus", query = "SELECT e FROM Events e WHERE e.status = :status"),
    @NamedQuery(name = "Events.findByFundGoal", query = "SELECT e FROM Events e WHERE e.fundGoal = :fundGoal")})
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "OWNER")
    private String owner;
    @Column(name = "START_DATE")
    private String startDate;
    @Column(name = "END_DATE")
    private String endDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FUND_GOAL")
    private String fundGoal;
    @JoinColumn(name = "RELATED_CAMPAIGN", referencedColumnName = "ID")
    @ManyToOne
    private Campaigns relatedCampaign;
    @OneToMany(mappedBy = "event")
    private Collection<Donations> donationsCollection;

    public Events() {
    }

    public Events(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFundGoal() {
        return fundGoal;
    }

    public void setFundGoal(String fundGoal) {
        this.fundGoal = fundGoal;
    }

    public Campaigns getRelatedCampaign() {
        return relatedCampaign;
    }

    public void setRelatedCampaign(Campaigns relatedCampaign) {
        this.relatedCampaign = relatedCampaign;
    }

    @XmlTransient
    public Collection<Donations> getDonationsCollection() {
        return donationsCollection;
    }

    public void setDonationsCollection(Collection<Donations> donationsCollection) {
        this.donationsCollection = donationsCollection;
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
        if (!(object instanceof Events)) {
            return false;
        }
        Events other = (Events) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "charity.Events[ id=" + id + " ]";
    }
    
}
