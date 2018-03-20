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
public class ContributorsJpaController implements Serializable {

    public ContributorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contributors contributors) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contributors);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContributors(contributors.getId()) != null) {
                throw new PreexistingEntityException("Contributors " + contributors + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contributors contributors) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contributors = em.merge(contributors);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = contributors.getId();
                if (findContributors(id) == null) {
                    throw new NonexistentEntityException("The contributors with id " + id + " no longer exists.");
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
            Contributors contributors;
            try {
                contributors = em.getReference(Contributors.class, id);
                contributors.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contributors with id " + id + " no longer exists.", enfe);
            }
            em.remove(contributors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contributors> findContributorsEntities() {
        return findContributorsEntities(true, -1, -1);
    }

    public List<Contributors> findContributorsEntities(int maxResults, int firstResult) {
        return findContributorsEntities(false, maxResults, firstResult);
    }

    private List<Contributors> findContributorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contributors.class));
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

    public Contributors findContributors(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contributors.class, id);
        } finally {
            em.close();
        }
    }

    public int getContributorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contributors> rt = cq.from(Contributors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
