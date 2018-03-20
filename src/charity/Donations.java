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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "DONATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donations.findAll", query = "SELECT d FROM Donations d"),
    @NamedQuery(name = "Donations.findById", query = "SELECT d FROM Donations d WHERE d.id = :id"),
    @NamedQuery(name = "Donations.findByContributor", query = "SELECT d FROM Donations d WHERE d.contributor = :contributor"),
    @NamedQuery(name = "Donations.findByOwner", query = "SELECT d FROM Donations d WHERE d.owner = :owner"),
    @NamedQuery(name = "Donations.findByAmount", query = "SELECT d FROM Donations d WHERE d.amount = :amount"),
    @NamedQuery(name = "Donations.findByPaid", query = "SELECT d FROM Donations d WHERE d.paid = :paid"),
    @NamedQuery(name = "Donations.findByPledgeDate", query = "SELECT d FROM Donations d WHERE d.pledgeDate = :pledgeDate"),
    @NamedQuery(name = "Donations.findByPaymentDate", query = "SELECT d FROM Donations d WHERE d.paymentDate = :paymentDate"),
    @NamedQuery(name = "Donations.findByPaymentMethod", query = "SELECT d FROM Donations d WHERE d.paymentMethod = :paymentMethod")})
public class Donations implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CONTRIBUTOR")
    private String contributor;
    @Column(name = "OWNER")
    private String owner;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "PAID")
    private String paid;
    @Column(name = "PLEDGE_DATE")
    private String pledgeDate;
    @Column(name = "PAYMENT_DATE")
    private String paymentDate;
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;
    @JoinColumn(name = "CAMPAIGN", referencedColumnName = "ID")
    @ManyToOne
    private Campaigns campaign;
    @JoinColumn(name = "EVENT", referencedColumnName = "ID")
    @ManyToOne
    private Events event;

    public Donations() {
    }

    public Donations(Long id) {
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

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        String oldContributor = this.contributor;
        this.contributor = contributor;
        changeSupport.firePropertyChange("contributor", oldContributor, contributor);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        String oldOwner = this.owner;
        this.owner = owner;
        changeSupport.firePropertyChange("owner", oldOwner, owner);
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        Long oldAmount = this.amount;
        this.amount = amount;
        changeSupport.firePropertyChange("amount", oldAmount, amount);
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        String oldPaid = this.paid;
        this.paid = paid;
        changeSupport.firePropertyChange("paid", oldPaid, paid);
    }

    public String getPledgeDate() {
        return pledgeDate;
    }

    public void setPledgeDate(String pledgeDate) {
        String oldPledgeDate = this.pledgeDate;
        this.pledgeDate = pledgeDate;
        changeSupport.firePropertyChange("pledgeDate", oldPledgeDate, pledgeDate);
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        String oldPaymentDate = this.paymentDate;
        this.paymentDate = paymentDate;
        changeSupport.firePropertyChange("paymentDate", oldPaymentDate, paymentDate);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        String oldPaymentMethod = this.paymentMethod;
        this.paymentMethod = paymentMethod;
        changeSupport.firePropertyChange("paymentMethod", oldPaymentMethod, paymentMethod);
    }

    public Campaigns getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaigns campaign) {
        Campaigns oldCampaign = this.campaign;
        this.campaign = campaign;
        changeSupport.firePropertyChange("campaign", oldCampaign, campaign);
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        Events oldEvent = this.event;
        this.event = event;
        changeSupport.firePropertyChange("event", oldEvent, event);
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
        if (!(object instanceof Donations)) {
            return false;
        }
        Donations other = (Donations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "charity.Donations[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
