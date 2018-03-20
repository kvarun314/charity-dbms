/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charity;

import charity.exceptions.NonexistentEntityException;
import charity.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Arvind
 */
public class CampaignsJpaController implements Serializable {

    public CampaignsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campaigns campaigns) throws PreexistingEntityException, Exception {
        if (campaigns.getEventsCollection() == null) {
            campaigns.setEventsCollection(new ArrayList<Events>());
        }
        if (campaigns.getDonationsCollection() == null) {
            campaigns.setDonationsCollection(new ArrayList<Donations>());
        }
        if (campaigns.getFundraisingCollection() == null) {
            campaigns.setFundraisingCollection(new ArrayList<Fundraising>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Events> attachedEventsCollection = new ArrayList<Events>();
            for (Events eventsCollectionEventsToAttach : campaigns.getEventsCollection()) {
                eventsCollectionEventsToAttach = em.getReference(eventsCollectionEventsToAttach.getClass(), eventsCollectionEventsToAttach.getId());
                attachedEventsCollection.add(eventsCollectionEventsToAttach);
            }
            campaigns.setEventsCollection(attachedEventsCollection);
            Collection<Donations> attachedDonationsCollection = new ArrayList<Donations>();
            for (Donations donationsCollectionDonationsToAttach : campaigns.getDonationsCollection()) {
                donationsCollectionDonationsToAttach = em.getReference(donationsCollectionDonationsToAttach.getClass(), donationsCollectionDonationsToAttach.getId());
                attachedDonationsCollection.add(donationsCollectionDonationsToAttach);
            }
            campaigns.setDonationsCollection(attachedDonationsCollection);
            Collection<Fundraising> attachedFundraisingCollection = new ArrayList<Fundraising>();
            for (Fundraising fundraisingCollectionFundraisingToAttach : campaigns.getFundraisingCollection()) {
                fundraisingCollectionFundraisingToAttach = em.getReference(fundraisingCollectionFundraisingToAttach.getClass(), fundraisingCollectionFundraisingToAttach.getId());
                attachedFundraisingCollection.add(fundraisingCollectionFundraisingToAttach);
            }
            campaigns.setFundraisingCollection(attachedFundraisingCollection);
            em.persist(campaigns);
            for (Events eventsCollectionEvents : campaigns.getEventsCollection()) {
                Campaigns oldRelatedCampaignOfEventsCollectionEvents = eventsCollectionEvents.getRelatedCampaign();
                eventsCollectionEvents.setRelatedCampaign(campaigns);
                eventsCollectionEvents = em.merge(eventsCollectionEvents);
                if (oldRelatedCampaignOfEventsCollectionEvents != null) {
                    oldRelatedCampaignOfEventsCollectionEvents.getEventsCollection().remove(eventsCollectionEvents);
                    oldRelatedCampaignOfEventsCollectionEvents = em.merge(oldRelatedCampaignOfEventsCollectionEvents);
                }
            }
            for (Donations donationsCollectionDonations : campaigns.getDonationsCollection()) {
                Campaigns oldCampaignOfDonationsCollectionDonations = donationsCollectionDonations.getCampaign();
                donationsCollectionDonations.setCampaign(campaigns);
                donationsCollectionDonations = em.merge(donationsCollectionDonations);
                if (oldCampaignOfDonationsCollectionDonations != null) {
                    oldCampaignOfDonationsCollectionDonations.getDonationsCollection().remove(donationsCollectionDonations);
                    oldCampaignOfDonationsCollectionDonations = em.merge(oldCampaignOfDonationsCollectionDonations);
                }
            }
            for (Fundraising fundraisingCollectionFundraising : campaigns.getFundraisingCollection()) {
                Campaigns oldCampaignOfFundraisingCollectionFundraising = fundraisingCollectionFundraising.getCampaign();
                fundraisingCollectionFundraising.setCampaign(campaigns);
                fundraisingCollectionFundraising = em.merge(fundraisingCollectionFundraising);
                if (oldCampaignOfFundraisingCollectionFundraising != null) {
                    oldCampaignOfFundraisingCollectionFundraising.getFundraisingCollection().remove(fundraisingCollectionFundraising);
                    oldCampaignOfFundraisingCollectionFundraising = em.merge(oldCampaignOfFundraisingCollectionFundraising);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCampaigns(campaigns.getId()) != null) {
                throw new PreexistingEntityException("Campaigns " + campaigns + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campaigns campaigns) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campaigns persistentCampaigns = em.find(Campaigns.class, campaigns.getId());
            Collection<Events> eventsCollectionOld = persistentCampaigns.getEventsCollection();
            Collection<Events> eventsCollectionNew = campaigns.getEventsCollection();
            Collection<Donations> donationsCollectionOld = persistentCampaigns.getDonationsCollection();
            Collection<Donations> donationsCollectionNew = campaigns.getDonationsCollection();
            Collection<Fundraising> fundraisingCollectionOld = persistentCampaigns.getFundraisingCollection();
            Collection<Fundraising> fundraisingCollectionNew = campaigns.getFundraisingCollection();
            Collection<Events> attachedEventsCollectionNew = new ArrayList<Events>();
            for (Events eventsCollectionNewEventsToAttach : eventsCollectionNew) {
                eventsCollectionNewEventsToAttach = em.getReference(eventsCollectionNewEventsToAttach.getClass(), eventsCollectionNewEventsToAttach.getId());
                attachedEventsCollectionNew.add(eventsCollectionNewEventsToAttach);
            }
            eventsCollectionNew = attachedEventsCollectionNew;
            campaigns.setEventsCollection(eventsCollectionNew);
            Collection<Donations> attachedDonationsCollectionNew = new ArrayList<Donations>();
            for (Donations donationsCollectionNewDonationsToAttach : donationsCollectionNew) {
                donationsCollectionNewDonationsToAttach = em.getReference(donationsCollectionNewDonationsToAttach.getClass(), donationsCollectionNewDonationsToAttach.getId());
                attachedDonationsCollectionNew.add(donationsCollectionNewDonationsToAttach);
            }
            donationsCollectionNew = attachedDonationsCollectionNew;
            campaigns.setDonationsCollection(donationsCollectionNew);
            Collection<Fundraising> attachedFundraisingCollectionNew = new ArrayList<Fundraising>();
            for (Fundraising fundraisingCollectionNewFundraisingToAttach : fundraisingCollectionNew) {
                fundraisingCollectionNewFundraisingToAttach = em.getReference(fundraisingCollectionNewFundraisingToAttach.getClass(), fundraisingCollectionNewFundraisingToAttach.getId());
                attachedFundraisingCollectionNew.add(fundraisingCollectionNewFundraisingToAttach);
            }
            fundraisingCollectionNew = attachedFundraisingCollectionNew;
            campaigns.setFundraisingCollection(fundraisingCollectionNew);
            campaigns = em.merge(campaigns);
            for (Events eventsCollectionOldEvents : eventsCollectionOld) {
                if (!eventsCollectionNew.contains(eventsCollectionOldEvents)) {
                    eventsCollectionOldEvents.setRelatedCampaign(null);
                    eventsCollectionOldEvents = em.merge(eventsCollectionOldEvents);
                }
            }
            for (Events eventsCollectionNewEvents : eventsCollectionNew) {
                if (!eventsCollectionOld.contains(eventsCollectionNewEvents)) {
                    Campaigns oldRelatedCampaignOfEventsCollectionNewEvents = eventsCollectionNewEvents.getRelatedCampaign();
                    eventsCollectionNewEvents.setRelatedCampaign(campaigns);
                    eventsCollectionNewEvents = em.merge(eventsCollectionNewEvents);
                    if (oldRelatedCampaignOfEventsCollectionNewEvents != null && !oldRelatedCampaignOfEventsCollectionNewEvents.equals(campaigns)) {
                        oldRelatedCampaignOfEventsCollectionNewEvents.getEventsCollection().remove(eventsCollectionNewEvents);
                        oldRelatedCampaignOfEventsCollectionNewEvents = em.merge(oldRelatedCampaignOfEventsCollectionNewEvents);
                    }
                }
            }
            for (Donations donationsCollectionOldDonations : donationsCollectionOld) {
                if (!donationsCollectionNew.contains(donationsCollectionOldDonations)) {
                    donationsCollectionOldDonations.setCampaign(null);
                    donationsCollectionOldDonations = em.merge(donationsCollectionOldDonations);
                }
            }
            for (Donations donationsCollectionNewDonations : donationsCollectionNew) {
                if (!donationsCollectionOld.contains(donationsCollectionNewDonations)) {
                    Campaigns oldCampaignOfDonationsCollectionNewDonations = donationsCollectionNewDonations.getCampaign();
                    donationsCollectionNewDonations.setCampaign(campaigns);
                    donationsCollectionNewDonations = em.merge(donationsCollectionNewDonations);
                    if (oldCampaignOfDonationsCollectionNewDonations != null && !oldCampaignOfDonationsCollectionNewDonations.equals(campaigns)) {
                        oldCampaignOfDonationsCollectionNewDonations.getDonationsCollection().remove(donationsCollectionNewDonations);
                        oldCampaignOfDonationsCollectionNewDonations = em.merge(oldCampaignOfDonationsCollectionNewDonations);
                    }
                }
            }
            for (Fundraising fundraisingCollectionOldFundraising : fundraisingCollectionOld) {
                if (!fundraisingCollectionNew.contains(fundraisingCollectionOldFundraising)) {
                    fundraisingCollectionOldFundraising.setCampaign(null);
                    fundraisingCollectionOldFundraising = em.merge(fundraisingCollectionOldFundraising);
                }
            }
            for (Fundraising fundraisingCollectionNewFundraising : fundraisingCollectionNew) {
                if (!fundraisingCollectionOld.contains(fundraisingCollectionNewFundraising)) {
                    Campaigns oldCampaignOfFundraisingCollectionNewFundraising = fundraisingCollectionNewFundraising.getCampaign();
                    fundraisingCollectionNewFundraising.setCampaign(campaigns);
                    fundraisingCollectionNewFundraising = em.merge(fundraisingCollectionNewFundraising);
                    if (oldCampaignOfFundraisingCollectionNewFundraising != null && !oldCampaignOfFundraisingCollectionNewFundraising.equals(campaigns)) {
                        oldCampaignOfFundraisingCollectionNewFundraising.getFundraisingCollection().remove(fundraisingCollectionNewFundraising);
                        oldCampaignOfFundraisingCollectionNewFundraising = em.merge(oldCampaignOfFundraisingCollectionNewFundraising);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = campaigns.getId();
                if (findCampaigns(id) == null) {
                    throw new NonexistentEntityException("The campaigns with id " + id + " no longer exists.");
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
            Campaigns campaigns;
            try {
                campaigns = em.getReference(Campaigns.class, id);
                campaigns.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campaigns with id " + id + " no longer exists.", enfe);
            }
            Collection<Events> eventsCollection = campaigns.getEventsCollection();
            for (Events eventsCollectionEvents : eventsCollection) {
                eventsCollectionEvents.setRelatedCampaign(null);
                eventsCollectionEvents = em.merge(eventsCollectionEvents);
            }
            Collection<Donations> donationsCollection = campaigns.getDonationsCollection();
            for (Donations donationsCollectionDonations : donationsCollection) {
                donationsCollectionDonations.setCampaign(null);
                donationsCollectionDonations = em.merge(donationsCollectionDonations);
            }
            Collection<Fundraising> fundraisingCollection = campaigns.getFundraisingCollection();
            for (Fundraising fundraisingCollectionFundraising : fundraisingCollection) {
                fundraisingCollectionFundraising.setCampaign(null);
                fundraisingCollectionFundraising = em.merge(fundraisingCollectionFundraising);
            }
            em.remove(campaigns);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Campaigns> findCampaignsEntities() {
        return findCampaignsEntities(true, -1, -1);
    }

    public List<Campaigns> findCampaignsEntities(int maxResults, int firstResult) {
        return findCampaignsEntities(false, maxResults, firstResult);
    }

    private List<Campaigns> findCampaignsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campaigns.class));
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

    public Campaigns findCampaigns(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campaigns.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampaignsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campaigns> rt = cq.from(Campaigns.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
