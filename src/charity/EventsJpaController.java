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
public class EventsJpaController implements Serializable {

    public EventsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Events events) throws PreexistingEntityException, Exception {
        if (events.getDonationsCollection() == null) {
            events.setDonationsCollection(new ArrayList<Donations>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campaigns relatedCampaign = events.getRelatedCampaign();
            if (relatedCampaign != null) {
                relatedCampaign = em.getReference(relatedCampaign.getClass(), relatedCampaign.getId());
                events.setRelatedCampaign(relatedCampaign);
            }
            Collection<Donations> attachedDonationsCollection = new ArrayList<Donations>();
            for (Donations donationsCollectionDonationsToAttach : events.getDonationsCollection()) {
                donationsCollectionDonationsToAttach = em.getReference(donationsCollectionDonationsToAttach.getClass(), donationsCollectionDonationsToAttach.getId());
                attachedDonationsCollection.add(donationsCollectionDonationsToAttach);
            }
            events.setDonationsCollection(attachedDonationsCollection);
            em.persist(events);
            if (relatedCampaign != null) {
                relatedCampaign.getEventsCollection().add(events);
                relatedCampaign = em.merge(relatedCampaign);
            }
            for (Donations donationsCollectionDonations : events.getDonationsCollection()) {
                Events oldEventOfDonationsCollectionDonations = donationsCollectionDonations.getEvent();
                donationsCollectionDonations.setEvent(events);
                donationsCollectionDonations = em.merge(donationsCollectionDonations);
                if (oldEventOfDonationsCollectionDonations != null) {
                    oldEventOfDonationsCollectionDonations.getDonationsCollection().remove(donationsCollectionDonations);
                    oldEventOfDonationsCollectionDonations = em.merge(oldEventOfDonationsCollectionDonations);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvents(events.getId()) != null) {
                throw new PreexistingEntityException("Events " + events + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Events events) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Events persistentEvents = em.find(Events.class, events.getId());
            Campaigns relatedCampaignOld = persistentEvents.getRelatedCampaign();
            Campaigns relatedCampaignNew = events.getRelatedCampaign();
            Collection<Donations> donationsCollectionOld = persistentEvents.getDonationsCollection();
            Collection<Donations> donationsCollectionNew = events.getDonationsCollection();
            if (relatedCampaignNew != null) {
                relatedCampaignNew = em.getReference(relatedCampaignNew.getClass(), relatedCampaignNew.getId());
                events.setRelatedCampaign(relatedCampaignNew);
            }
            Collection<Donations> attachedDonationsCollectionNew = new ArrayList<Donations>();
            for (Donations donationsCollectionNewDonationsToAttach : donationsCollectionNew) {
                donationsCollectionNewDonationsToAttach = em.getReference(donationsCollectionNewDonationsToAttach.getClass(), donationsCollectionNewDonationsToAttach.getId());
                attachedDonationsCollectionNew.add(donationsCollectionNewDonationsToAttach);
            }
            donationsCollectionNew = attachedDonationsCollectionNew;
            events.setDonationsCollection(donationsCollectionNew);
            events = em.merge(events);
            if (relatedCampaignOld != null && !relatedCampaignOld.equals(relatedCampaignNew)) {
                relatedCampaignOld.getEventsCollection().remove(events);
                relatedCampaignOld = em.merge(relatedCampaignOld);
            }
            if (relatedCampaignNew != null && !relatedCampaignNew.equals(relatedCampaignOld)) {
                relatedCampaignNew.getEventsCollection().add(events);
                relatedCampaignNew = em.merge(relatedCampaignNew);
            }
            for (Donations donationsCollectionOldDonations : donationsCollectionOld) {
                if (!donationsCollectionNew.contains(donationsCollectionOldDonations)) {
                    donationsCollectionOldDonations.setEvent(null);
                    donationsCollectionOldDonations = em.merge(donationsCollectionOldDonations);
                }
            }
            for (Donations donationsCollectionNewDonations : donationsCollectionNew) {
                if (!donationsCollectionOld.contains(donationsCollectionNewDonations)) {
                    Events oldEventOfDonationsCollectionNewDonations = donationsCollectionNewDonations.getEvent();
                    donationsCollectionNewDonations.setEvent(events);
                    donationsCollectionNewDonations = em.merge(donationsCollectionNewDonations);
                    if (oldEventOfDonationsCollectionNewDonations != null && !oldEventOfDonationsCollectionNewDonations.equals(events)) {
                        oldEventOfDonationsCollectionNewDonations.getDonationsCollection().remove(donationsCollectionNewDonations);
                        oldEventOfDonationsCollectionNewDonations = em.merge(oldEventOfDonationsCollectionNewDonations);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = events.getId();
                if (findEvents(id) == null) {
                    throw new NonexistentEntityException("The events with id " + id + " no longer exists.");
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
            Events events;
            try {
                events = em.getReference(Events.class, id);
                events.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The events with id " + id + " no longer exists.", enfe);
            }
            Campaigns relatedCampaign = events.getRelatedCampaign();
            if (relatedCampaign != null) {
                relatedCampaign.getEventsCollection().remove(events);
                relatedCampaign = em.merge(relatedCampaign);
            }
            Collection<Donations> donationsCollection = events.getDonationsCollection();
            for (Donations donationsCollectionDonations : donationsCollection) {
                donationsCollectionDonations.setEvent(null);
                donationsCollectionDonations = em.merge(donationsCollectionDonations);
            }
            em.remove(events);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Events> findEventsEntities() {
        return findEventsEntities(true, -1, -1);
    }

    public List<Events> findEventsEntities(int maxResults, int firstResult) {
        return findEventsEntities(false, maxResults, firstResult);
    }

    private List<Events> findEventsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Events.class));
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

    public Events findEvents(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Events.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Events> rt = cq.from(Events.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
