/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entidades.SAccesos;
import entidades.SAccesos_;
import entidades.SPerfiles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Root;
import entidades.SPerfilesAccesos;
import entidades.SPerfilesAccesos_;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import util.LocalEntityManagerFactory;

/**
 *
 * @author admin
 */
public class SAccesosJpaController implements Serializable {

    public SAccesosJpaController() {
        this.emf = LocalEntityManagerFactory.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SAccesos SAccesos) throws PreexistingEntityException, Exception {
        if (SAccesos.getSPerfilesAccesosCollection() == null) {
            SAccesos.setSPerfilesAccesosCollection(new ArrayList<SPerfilesAccesos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SPerfilesAccesos> attachedSPerfilesAccesosCollection = new ArrayList<SPerfilesAccesos>();
            for (SPerfilesAccesos SPerfilesAccesosCollectionSPerfilesAccesosToAttach : SAccesos.getSPerfilesAccesosCollection()) {
                SPerfilesAccesosCollectionSPerfilesAccesosToAttach = em.getReference(SPerfilesAccesosCollectionSPerfilesAccesosToAttach.getClass(), SPerfilesAccesosCollectionSPerfilesAccesosToAttach.getSPerfilesAccesosPK());
                attachedSPerfilesAccesosCollection.add(SPerfilesAccesosCollectionSPerfilesAccesosToAttach);
            }
            SAccesos.setSPerfilesAccesosCollection(attachedSPerfilesAccesosCollection);
            em.persist(SAccesos);
            for (SPerfilesAccesos SPerfilesAccesosCollectionSPerfilesAccesos : SAccesos.getSPerfilesAccesosCollection()) {
                SAccesos oldSAccesosOfSPerfilesAccesosCollectionSPerfilesAccesos = SPerfilesAccesosCollectionSPerfilesAccesos.getSAccesos();
                SPerfilesAccesosCollectionSPerfilesAccesos.setSAccesos(SAccesos);
                SPerfilesAccesosCollectionSPerfilesAccesos = em.merge(SPerfilesAccesosCollectionSPerfilesAccesos);
                if (oldSAccesosOfSPerfilesAccesosCollectionSPerfilesAccesos != null) {
                    oldSAccesosOfSPerfilesAccesosCollectionSPerfilesAccesos.getSPerfilesAccesosCollection().remove(SPerfilesAccesosCollectionSPerfilesAccesos);
                    oldSAccesosOfSPerfilesAccesosCollectionSPerfilesAccesos = em.merge(oldSAccesosOfSPerfilesAccesosCollectionSPerfilesAccesos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSAccesos(SAccesos.getIdAcceso()) != null) {
                throw new PreexistingEntityException("SAccesos " + SAccesos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SAccesos SAccesos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAccesos persistentSAccesos = em.find(SAccesos.class, SAccesos.getIdAcceso());
            Collection<SPerfilesAccesos> SPerfilesAccesosCollectionOld = persistentSAccesos.getSPerfilesAccesosCollection();
            Collection<SPerfilesAccesos> SPerfilesAccesosCollectionNew = SAccesos.getSPerfilesAccesosCollection();
            List<String> illegalOrphanMessages = null;
            for (SPerfilesAccesos SPerfilesAccesosCollectionOldSPerfilesAccesos : SPerfilesAccesosCollectionOld) {
                if (!SPerfilesAccesosCollectionNew.contains(SPerfilesAccesosCollectionOldSPerfilesAccesos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SPerfilesAccesos " + SPerfilesAccesosCollectionOldSPerfilesAccesos + " since its SAccesos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SPerfilesAccesos> attachedSPerfilesAccesosCollectionNew = new ArrayList<SPerfilesAccesos>();
            for (SPerfilesAccesos SPerfilesAccesosCollectionNewSPerfilesAccesosToAttach : SPerfilesAccesosCollectionNew) {
                SPerfilesAccesosCollectionNewSPerfilesAccesosToAttach = em.getReference(SPerfilesAccesosCollectionNewSPerfilesAccesosToAttach.getClass(), SPerfilesAccesosCollectionNewSPerfilesAccesosToAttach.getSPerfilesAccesosPK());
                attachedSPerfilesAccesosCollectionNew.add(SPerfilesAccesosCollectionNewSPerfilesAccesosToAttach);
            }
            SPerfilesAccesosCollectionNew = attachedSPerfilesAccesosCollectionNew;
            SAccesos.setSPerfilesAccesosCollection(SPerfilesAccesosCollectionNew);
            SAccesos = em.merge(SAccesos);
            for (SPerfilesAccesos SPerfilesAccesosCollectionNewSPerfilesAccesos : SPerfilesAccesosCollectionNew) {
                if (!SPerfilesAccesosCollectionOld.contains(SPerfilesAccesosCollectionNewSPerfilesAccesos)) {
                    SAccesos oldSAccesosOfSPerfilesAccesosCollectionNewSPerfilesAccesos = SPerfilesAccesosCollectionNewSPerfilesAccesos.getSAccesos();
                    SPerfilesAccesosCollectionNewSPerfilesAccesos.setSAccesos(SAccesos);
                    SPerfilesAccesosCollectionNewSPerfilesAccesos = em.merge(SPerfilesAccesosCollectionNewSPerfilesAccesos);
                    if (oldSAccesosOfSPerfilesAccesosCollectionNewSPerfilesAccesos != null && !oldSAccesosOfSPerfilesAccesosCollectionNewSPerfilesAccesos.equals(SAccesos)) {
                        oldSAccesosOfSPerfilesAccesosCollectionNewSPerfilesAccesos.getSPerfilesAccesosCollection().remove(SPerfilesAccesosCollectionNewSPerfilesAccesos);
                        oldSAccesosOfSPerfilesAccesosCollectionNewSPerfilesAccesos = em.merge(oldSAccesosOfSPerfilesAccesosCollectionNewSPerfilesAccesos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SAccesos.getIdAcceso();
                if (findSAccesos(id) == null) {
                    throw new NonexistentEntityException("The sAccesos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAccesos SAccesos;
            try {
                SAccesos = em.getReference(SAccesos.class, id);
                SAccesos.getIdAcceso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SAccesos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SPerfilesAccesos> SPerfilesAccesosCollectionOrphanCheck = SAccesos.getSPerfilesAccesosCollection();
            for (SPerfilesAccesos SPerfilesAccesosCollectionOrphanCheckSPerfilesAccesos : SPerfilesAccesosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SAccesos (" + SAccesos + ") cannot be destroyed since the SPerfilesAccesos " + SPerfilesAccesosCollectionOrphanCheckSPerfilesAccesos + " in its SPerfilesAccesosCollection field has a non-nullable SAccesos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(SAccesos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SAccesos> findSAccesosEntities() {
        return findSAccesosEntities(true, -1, -1);
    }

    public List<SAccesos> findSAccesosEntities(int maxResults, int firstResult) {
        return findSAccesosEntities(false, maxResults, firstResult);
    }

    private List<SAccesos> findSAccesosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SAccesos.class));
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

    public SAccesos findSAccesos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SAccesos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSAccesosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SAccesos> rt = cq.from(SAccesos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean existOrder(int numeroOrden) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("SAccesos.findByOrden");
        query.setParameter("orden", numeroOrden);
        List<SAccesos> list = query.getResultList();
        if (list.size() > 0) {
            em.close();
            return true;
        }
        em.close();
        return false;
    }

    public List<SAccesos> AccesosAsignados(SPerfiles perfiles) {
        List<SAccesos> lista = new ArrayList<>();
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        try {

            CriteriaQuery<SAccesos> query = cb.createQuery(SAccesos.class);
            Root<SAccesos> perfil = query.from(SAccesos.class);

            CollectionJoin<SAccesos, SPerfilesAccesos> usuarioPerfil = perfil.join(SAccesos_.sPerfilesAccesosCollection);
            query.select(perfil).where(cb.equal(usuarioPerfil.get(SPerfilesAccesos_.sPerfiles), perfiles));

            TypedQuery<SAccesos> typedQuery = em.createQuery(query);
            //typedQuery.getResultList().forEach(System.out::println);
            lista = typedQuery.getResultList();

        } catch (Exception ex) {
            em.close();
            Logger.getLogger(SAccesosJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<SAccesos> AccesosDisponibles(SPerfiles perfiles) {
        List<SAccesos> lista = new ArrayList<>();
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        try {

            CriteriaQuery<SAccesos> query = cb.createQuery(SAccesos.class);
            Root<SAccesos> perfil = query.from(SAccesos.class);
            CollectionJoin<SAccesos, SPerfilesAccesos> usuarioPerfil = perfil.join(SAccesos_.sPerfilesAccesosCollection,JoinType.LEFT);
            usuarioPerfil.on(cb.equal(usuarioPerfil.get(SPerfilesAccesos_.sPerfiles), perfiles));
            query.select(perfil).where(cb.isNull(usuarioPerfil.get(SPerfilesAccesos_.sPerfiles)));

            query.select(perfil).where(cb.equal(usuarioPerfil.get(SPerfilesAccesos_.sPerfiles), perfiles));

            TypedQuery<SAccesos> typedQuery = em.createQuery(query);
            typedQuery.getResultList().forEach(System.out::println);
            lista = typedQuery.getResultList();
            em.close();
        } catch (Exception ex) {
            em.close();
            Logger.getLogger(SAccesosJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }
}
