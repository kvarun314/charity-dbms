/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charity;

import charity.exceptions.NonexistentEntityException;
import charity.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Arvind
 */
public class DonationsJpaController implements Serializable {

    public DonationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Donations donations) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campaigns campaign = donations.getCampaign();
            if (campaign != null) {
                campaign = em.getReference(campaign.getClass(), campaign.getId());
                donations.setCampaign(campaign);
            }
            Events event = donations.getEvent();
            if (event != null) {
                event = em.getReference(event.getClass(), event.getId());
                donations.setEvent(event);
            }
            em.persist(donations);
            if (campaign != null) {
                campaign.getDonationsCollection().add(donations);
                campaign = em.merge(campaign);
            }
            if (event != null) {
                event.getDonationsCollection().add(donations);
                event = em.merge(event);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDonations(donations.getId()) != null) {
                throw new PreexistingEntityException("Donations " + donations + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Donations donations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Donations persistentDonations = em.find(Donations.class, donations.getId());
            Campaigns campaignOld = persistentDonations.getCampaign();
            Campaigns campaignNew = donations.getCampaign();
            Events eventOld = persistentDonations.getEvent();
            Events eventNew = donations.getEvent();
            if (campaignNew != null) {
                campaignNew = em.getReference(campaignNew.getClass(), campaignNew.getId());
                donations.setCampaign(campaignNew);
            }
            if (eventNew != null) {
                eventNew = em.getReference(eventNew.getClass(), eventNew.getId());
                donations.setEvent(eventNew);
            }
            donations = em.merge(donations);
            if (campaignOld != null && !campaignOld.equals(campaignNew)) {
                campaignOld.getDonationsCollection().remove(donations);
                campaignOld = em.merge(campaignOld);
            }
            if (campaignNew != null && !campaignNew.equals(campaignOld)) {
                campaignNew.getDonationsCollection().add(donations);
                campaignNew = em.merge(campaignNew);
            }
            if (eventOld != null && !eventOld.equals(eventNew)) {
                eventOld.getDonationsCollection().remove(donations);
                eventOld = em.merge(eventOld);
            }
            if (eventNew != null && !eventNew.equals(eventOld)) {
                eventNew.getDonationsCollection().add(donations);
                eventNew = em.merge(eventNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = donations.getId();
                if (findDonations(id) == null) {
                    throw new NonexistentEntityException("The donations with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Donations donations;
            try {
                donations = em.getReference(Donations.class, id);
                donations.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The donations with id " + id + " no longer exists.", enfe);
            }
            Campaigns campaign = donations.getCampaign();
            if (campaign != null) {
                campaign.getDonationsCollection().remove(donations);
                campaign = em.merge(campaign);
            }
            Events event = donations.getEvent();
            if (event != null) {
                event.getDonationsCollection().remove(donations);
                event = em.merge(event);
            }
            em.remove(donations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Donations> findDonationsEntities() {
        return findDonationsEntities(true, -1, -1);
    }

    public List<Donations> findDonationsEntities(int maxResults, int firstResult) {
        return findDonationsEntities(false, maxResults, firstResult);
    }

    private List<Donations> findDonationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Donations.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Donations findDonations(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Donations.class, id);
        } finally {
            em.close();
        }
    }

    public int getDonationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Donations> rt = cq.from(Donations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
