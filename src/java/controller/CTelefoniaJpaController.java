/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entidades.CTelefonia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.CTipoTelefono;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import util.LocalEntityManagerFactory;

/**
 *
 * @author admin
 */
public class CTelefoniaJpaController implements Serializable {
    
    public CTelefoniaJpaController() {
        this.emf = LocalEntityManagerFactory.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CTelefonia CTelefonia) throws PreexistingEntityException, Exception {
        if (CTelefonia.getCTipoTelefonoCollection() == null) {
            CTelefonia.setCTipoTelefonoCollection(new ArrayList<CTipoTelefono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<CTipoTelefono> attachedCTipoTelefonoCollection = new ArrayList<CTipoTelefono>();
            for (CTipoTelefono CTipoTelefonoCollectionCTipoTelefonoToAttach : CTelefonia.getCTipoTelefonoCollection()) {
                CTipoTelefonoCollectionCTipoTelefonoToAttach = em.getReference(CTipoTelefonoCollectionCTipoTelefonoToAttach.getClass(), CTipoTelefonoCollectionCTipoTelefonoToAttach.getId());
                attachedCTipoTelefonoCollection.add(CTipoTelefonoCollectionCTipoTelefonoToAttach);
            }
            CTelefonia.setCTipoTelefonoCollection(attachedCTipoTelefonoCollection);
            em.persist(CTelefonia);
            for (CTipoTelefono CTipoTelefonoCollectionCTipoTelefono : CTelefonia.getCTipoTelefonoCollection()) {
                CTelefonia oldIdTelefoniaOfCTipoTelefonoCollectionCTipoTelefono = CTipoTelefonoCollectionCTipoTelefono.getIdTelefonia();
                CTipoTelefonoCollectionCTipoTelefono.setIdTelefonia(CTelefonia);
                CTipoTelefonoCollectionCTipoTelefono = em.merge(CTipoTelefonoCollectionCTipoTelefono);
                if (oldIdTelefoniaOfCTipoTelefonoCollectionCTipoTelefono != null) {
                    oldIdTelefoniaOfCTipoTelefonoCollectionCTipoTelefono.getCTipoTelefonoCollection().remove(CTipoTelefonoCollectionCTipoTelefono);
                    oldIdTelefoniaOfCTipoTelefonoCollectionCTipoTelefono = em.merge(oldIdTelefoniaOfCTipoTelefonoCollectionCTipoTelefono);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCTelefonia(CTelefonia.getIdTelefonia()) != null) {
                throw new PreexistingEntityException("CTelefonia " + CTelefonia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CTelefonia CTelefonia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CTelefonia persistentCTelefonia = em.find(CTelefonia.class, CTelefonia.getIdTelefonia());
            Collection<CTipoTelefono> CTipoTelefonoCollectionOld = persistentCTelefonia.getCTipoTelefonoCollection();
            Collection<CTipoTelefono> CTipoTelefonoCollectionNew = CTelefonia.getCTipoTelefonoCollection();
            Collection<CTipoTelefono> attachedCTipoTelefonoCollectionNew = new ArrayList<CTipoTelefono>();
            for (CTipoTelefono CTipoTelefonoCollectionNewCTipoTelefonoToAttach : CTipoTelefonoCollectionNew) {
                CTipoTelefonoCollectionNewCTipoTelefonoToAttach = em.getReference(CTipoTelefonoCollectionNewCTipoTelefonoToAttach.getClass(), CTipoTelefonoCollectionNewCTipoTelefonoToAttach.getId());
                attachedCTipoTelefonoCollectionNew.add(CTipoTelefonoCollectionNewCTipoTelefonoToAttach);
            }
            CTipoTelefonoCollectionNew = attachedCTipoTelefonoCollectionNew;
            CTelefonia.setCTipoTelefonoCollection(CTipoTelefonoCollectionNew);
            CTelefonia = em.merge(CTelefonia);
            for (CTipoTelefono CTipoTelefonoCollectionOldCTipoTelefono : CTipoTelefonoCollectionOld) {
                if (!CTipoTelefonoCollectionNew.contains(CTipoTelefonoCollectionOldCTipoTelefono)) {
                    CTipoTelefonoCollectionOldCTipoTelefono.setIdTelefonia(null);
                    CTipoTelefonoCollectionOldCTipoTelefono = em.merge(CTipoTelefonoCollectionOldCTipoTelefono);
                }
            }
            for (CTipoTelefono CTipoTelefonoCollectionNewCTipoTelefono : CTipoTelefonoCollectionNew) {
                if (!CTipoTelefonoCollectionOld.contains(CTipoTelefonoCollectionNewCTipoTelefono)) {
                    CTelefonia oldIdTelefoniaOfCTipoTelefonoCollectionNewCTipoTelefono = CTipoTelefonoCollectionNewCTipoTelefono.getIdTelefonia();
                    CTipoTelefonoCollectionNewCTipoTelefono.setIdTelefonia(CTelefonia);
                    CTipoTelefonoCollectionNewCTipoTelefono = em.merge(CTipoTelefonoCollectionNewCTipoTelefono);
                    if (oldIdTelefoniaOfCTipoTelefonoCollectionNewCTipoTelefono != null && !oldIdTelefoniaOfCTipoTelefonoCollectionNewCTipoTelefono.equals(CTelefonia)) {
                        oldIdTelefoniaOfCTipoTelefonoCollectionNewCTipoTelefono.getCTipoTelefonoCollection().remove(CTipoTelefonoCollectionNewCTipoTelefono);
                        oldIdTelefoniaOfCTipoTelefonoCollectionNewCTipoTelefono = em.merge(oldIdTelefoniaOfCTipoTelefonoCollectionNewCTipoTelefono);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = CTelefonia.getIdTelefonia();
                if (findCTelefonia(id) == null) {
                    throw new NonexistentEntityException("The cTelefonia with id " + id + " no longer exists.");
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
            CTelefonia CTelefonia;
            try {
                CTelefonia = em.getReference(CTelefonia.class, id);
                CTelefonia.getIdTelefonia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The CTelefonia with id " + id + " no longer exists.", enfe);
            }
            Collection<CTipoTelefono> CTipoTelefonoCollection = CTelefonia.getCTipoTelefonoCollection();
            for (CTipoTelefono CTipoTelefonoCollectionCTipoTelefono : CTipoTelefonoCollection) {
                CTipoTelefonoCollectionCTipoTelefono.setIdTelefonia(null);
                CTipoTelefonoCollectionCTipoTelefono = em.merge(CTipoTelefonoCollectionCTipoTelefono);
            }
            em.remove(CTelefonia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CTelefonia> findCTelefoniaEntities() {
        return findCTelefoniaEntities(true, -1, -1);
    }

    public List<CTelefonia> findCTelefoniaEntities(int maxResults, int firstResult) {
        return findCTelefoniaEntities(false, maxResults, firstResult);
    }

    private List<CTelefonia> findCTelefoniaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CTelefonia.class));
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

    public CTelefonia findCTelefonia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CTelefonia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCTelefoniaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CTelefonia> rt = cq.from(CTelefonia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
