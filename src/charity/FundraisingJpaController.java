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
public class FundraisingJpaController implements Serializable {

    public FundraisingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fundraising fundraising) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campaigns campaign = fundraising.getCampaign();
            if (campaign != null) {
                campaign = em.getReference(campaign.getClass(), campaign.getId());
                fundraising.setCampaign(campaign);
            }
            em.persist(fundraising);
            if (campaign != null) {
                campaign.getFundraisingCollection().add(fundraising);
                campaign = em.merge(campaign);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFundraising(fundraising.getId()) != null) {
                throw new PreexistingEntityException("Fundraising " + fundraising + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fundraising fundraising) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fundraising persistentFundraising = em.find(Fundraising.class, fundraising.getId());
            Campaigns campaignOld = persistentFundraising.getCampaign();
            Campaigns campaignNew = fundraising.getCampaign();
            if (campaignNew != null) {
                campaignNew = em.getReference(campaignNew.getClass(), campaignNew.getId());
                fundraising.setCampaign(campaignNew);
            }
            fundraising = em.merge(fundraising);
            if (campaignOld != null && !campaignOld.equals(campaignNew)) {
                campaignOld.getFundraisingCollection().remove(fundraising);
                campaignOld = em.merge(campaignOld);
            }
            if (campaignNew != null && !campaignNew.equals(campaignOld)) {
                campaignNew.getFundraisingCollection().add(fundraising);
                campaignNew = em.merge(campaignNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fundraising.getId();
                if (findFundraising(id) == null) {
                    throw new NonexistentEntityException("The fundraising with id " + id + " no longer exists.");
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
            Fundraising fundraising;
            try {
                fundraising = em.getReference(Fundraising.class, id);
                fundraising.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fundraising with id " + id + " no longer exists.", enfe);
            }
            Campaigns campaign = fundraising.getCampaign();
            if (campaign != null) {
                campaign.getFundraisingCollection().remove(fundraising);
                campaign = em.merge(campaign);
            }
            em.remove(fundraising);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fundraising> findFundraisingEntities() {
        return findFundraisingEntities(true, -1, -1);
    }

    public List<Fundraising> findFundraisingEntities(int maxResults, int firstResult) {
        return findFundraisingEntities(false, maxResults, firstResult);
    }

    private List<Fundraising> findFundraisingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fundraising.class));
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

    public Fundraising findFundraising(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fundraising.class, id);
        } finally {
            em.close();
        }
    }

    public int getFundraisingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fundraising> rt = cq.from(Fundraising.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
