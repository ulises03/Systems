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
import entidades.SPerfiles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import entidades.SPerfilesAccesos;
import java.util.ArrayList;
import java.util.Collection;
import entidades.SUsuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import util.LocalEntityManagerFactory;
import entidades.SAccesos_;
import entidades.SPerfilesAccesos_;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 *
 * @author admin
 */
public class SPerfilesJpaController implements Serializable {

    public SPerfilesJpaController() {
        this.emf = LocalEntityManagerFactory.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SPerfiles SPerfiles) throws PreexistingEntityException, Exception {
        if (SPerfiles.getSPerfilesAccesosCollection() == null) {
            SPerfiles.setSPerfilesAccesosCollection(new ArrayList<SPerfilesAccesos>());
        }
        if (SPerfiles.getSUsuariosCollection() == null) {
            SPerfiles.setSUsuariosCollection(new ArrayList<SUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SPerfilesAccesos> attachedSPerfilesAccesosCollection = new ArrayList<SPerfilesAccesos>();
            for (SPerfilesAccesos SPerfilesAccesosCollectionSPerfilesAccesosToAttach : SPerfiles.getSPerfilesAccesosCollection()) {
                SPerfilesAccesosCollectionSPerfilesAccesosToAttach = em.getReference(SPerfilesAccesosCollectionSPerfilesAccesosToAttach.getClass(), SPerfilesAccesosCollectionSPerfilesAccesosToAttach.getSPerfilesAccesosPK());
                attachedSPerfilesAccesosCollection.add(SPerfilesAccesosCollectionSPerfilesAccesosToAttach);
            }
            SPerfiles.setSPerfilesAccesosCollection(attachedSPerfilesAccesosCollection);
            Collection<SUsuarios> attachedSUsuariosCollection = new ArrayList<SUsuarios>();
            for (SUsuarios SUsuariosCollectionSUsuariosToAttach : SPerfiles.getSUsuariosCollection()) {
                SUsuariosCollectionSUsuariosToAttach = em.getReference(SUsuariosCollectionSUsuariosToAttach.getClass(), SUsuariosCollectionSUsuariosToAttach.getIdUsuario());
                attachedSUsuariosCollection.add(SUsuariosCollectionSUsuariosToAttach);
            }
            SPerfiles.setSUsuariosCollection(attachedSUsuariosCollection);
            em.persist(SPerfiles);
            for (SPerfilesAccesos SPerfilesAccesosCollectionSPerfilesAccesos : SPerfiles.getSPerfilesAccesosCollection()) {
                SPerfiles oldSPerfilesOfSPerfilesAccesosCollectionSPerfilesAccesos = SPerfilesAccesosCollectionSPerfilesAccesos.getSPerfiles();
                SPerfilesAccesosCollectionSPerfilesAccesos.setSPerfiles(SPerfiles);
                SPerfilesAccesosCollectionSPerfilesAccesos = em.merge(SPerfilesAccesosCollectionSPerfilesAccesos);
                if (oldSPerfilesOfSPerfilesAccesosCollectionSPerfilesAccesos != null) {
                    oldSPerfilesOfSPerfilesAccesosCollectionSPerfilesAccesos.getSPerfilesAccesosCollection().remove(SPerfilesAccesosCollectionSPerfilesAccesos);
                    oldSPerfilesOfSPerfilesAccesosCollectionSPerfilesAccesos = em.merge(oldSPerfilesOfSPerfilesAccesosCollectionSPerfilesAccesos);
                }
            }
            for (SUsuarios SUsuariosCollectionSUsuarios : SPerfiles.getSUsuariosCollection()) {
                SPerfiles oldIdPerfilOfSUsuariosCollectionSUsuarios = SUsuariosCollectionSUsuarios.getIdPerfil();
                SUsuariosCollectionSUsuarios.setIdPerfil(SPerfiles);
                SUsuariosCollectionSUsuarios = em.merge(SUsuariosCollectionSUsuarios);
                if (oldIdPerfilOfSUsuariosCollectionSUsuarios != null) {
                    oldIdPerfilOfSUsuariosCollectionSUsuarios.getSUsuariosCollection().remove(SUsuariosCollectionSUsuarios);
                    oldIdPerfilOfSUsuariosCollectionSUsuarios = em.merge(oldIdPerfilOfSUsuariosCollectionSUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSPerfiles(SPerfiles.getIdPerfil()) != null) {
                throw new PreexistingEntityException("SPerfiles " + SPerfiles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SPerfiles SPerfiles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SPerfiles persistentSPerfiles = em.find(SPerfiles.class, SPerfiles.getIdPerfil());
            Collection<SPerfilesAccesos> SPerfilesAccesosCollectionOld = persistentSPerfiles.getSPerfilesAccesosCollection();
            Collection<SPerfilesAccesos> SPerfilesAccesosCollectionNew = SPerfiles.getSPerfilesAccesosCollection();
            Collection<SUsuarios> SUsuariosCollectionOld = persistentSPerfiles.getSUsuariosCollection();
            Collection<SUsuarios> SUsuariosCollectionNew = SPerfiles.getSUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (SPerfilesAccesos SPerfilesAccesosCollectionOldSPerfilesAccesos : SPerfilesAccesosCollectionOld) {
                if (!SPerfilesAccesosCollectionNew.contains(SPerfilesAccesosCollectionOldSPerfilesAccesos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SPerfilesAccesos " + SPerfilesAccesosCollectionOldSPerfilesAccesos + " since its SPerfiles field is not nullable.");
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
            SPerfiles.setSPerfilesAccesosCollection(SPerfilesAccesosCollectionNew);
            Collection<SUsuarios> attachedSUsuariosCollectionNew = new ArrayList<SUsuarios>();
            for (SUsuarios SUsuariosCollectionNewSUsuariosToAttach : SUsuariosCollectionNew) {
                SUsuariosCollectionNewSUsuariosToAttach = em.getReference(SUsuariosCollectionNewSUsuariosToAttach.getClass(), SUsuariosCollectionNewSUsuariosToAttach.getIdUsuario());
                attachedSUsuariosCollectionNew.add(SUsuariosCollectionNewSUsuariosToAttach);
            }
            SUsuariosCollectionNew = attachedSUsuariosCollectionNew;
            SPerfiles.setSUsuariosCollection(SUsuariosCollectionNew);
            SPerfiles = em.merge(SPerfiles);
            for (SPerfilesAccesos SPerfilesAccesosCollectionNewSPerfilesAccesos : SPerfilesAccesosCollectionNew) {
                if (!SPerfilesAccesosCollectionOld.contains(SPerfilesAccesosCollectionNewSPerfilesAccesos)) {
                    SPerfiles oldSPerfilesOfSPerfilesAccesosCollectionNewSPerfilesAccesos = SPerfilesAccesosCollectionNewSPerfilesAccesos.getSPerfiles();
                    SPerfilesAccesosCollectionNewSPerfilesAccesos.setSPerfiles(SPerfiles);
                    SPerfilesAccesosCollectionNewSPerfilesAccesos = em.merge(SPerfilesAccesosCollectionNewSPerfilesAccesos);
                    if (oldSPerfilesOfSPerfilesAccesosCollectionNewSPerfilesAccesos != null && !oldSPerfilesOfSPerfilesAccesosCollectionNewSPerfilesAccesos.equals(SPerfiles)) {
                        oldSPerfilesOfSPerfilesAccesosCollectionNewSPerfilesAccesos.getSPerfilesAccesosCollection().remove(SPerfilesAccesosCollectionNewSPerfilesAccesos);
                        oldSPerfilesOfSPerfilesAccesosCollectionNewSPerfilesAccesos = em.merge(oldSPerfilesOfSPerfilesAccesosCollectionNewSPerfilesAccesos);
                    }
                }
            }
            for (SUsuarios SUsuariosCollectionOldSUsuarios : SUsuariosCollectionOld) {
                if (!SUsuariosCollectionNew.contains(SUsuariosCollectionOldSUsuarios)) {
                    SUsuariosCollectionOldSUsuarios.setIdPerfil(null);
                    SUsuariosCollectionOldSUsuarios = em.merge(SUsuariosCollectionOldSUsuarios);
                }
            }
            for (SUsuarios SUsuariosCollectionNewSUsuarios : SUsuariosCollectionNew) {
                if (!SUsuariosCollectionOld.contains(SUsuariosCollectionNewSUsuarios)) {
                    SPerfiles oldIdPerfilOfSUsuariosCollectionNewSUsuarios = SUsuariosCollectionNewSUsuarios.getIdPerfil();
                    SUsuariosCollectionNewSUsuarios.setIdPerfil(SPerfiles);
                    SUsuariosCollectionNewSUsuarios = em.merge(SUsuariosCollectionNewSUsuarios);
                    if (oldIdPerfilOfSUsuariosCollectionNewSUsuarios != null && !oldIdPerfilOfSUsuariosCollectionNewSUsuarios.equals(SPerfiles)) {
                        oldIdPerfilOfSUsuariosCollectionNewSUsuarios.getSUsuariosCollection().remove(SUsuariosCollectionNewSUsuarios);
                        oldIdPerfilOfSUsuariosCollectionNewSUsuarios = em.merge(oldIdPerfilOfSUsuariosCollectionNewSUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SPerfiles.getIdPerfil();
                if (findSPerfiles(id) == null) {
                    throw new NonexistentEntityException("The sPerfiles with id " + id + " no longer exists.");
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
            SPerfiles SPerfiles;
            try {
                SPerfiles = em.getReference(SPerfiles.class, id);
                SPerfiles.getIdPerfil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SPerfiles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SPerfilesAccesos> SPerfilesAccesosCollectionOrphanCheck = SPerfiles.getSPerfilesAccesosCollection();
            for (SPerfilesAccesos SPerfilesAccesosCollectionOrphanCheckSPerfilesAccesos : SPerfilesAccesosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SPerfiles (" + SPerfiles + ") cannot be destroyed since the SPerfilesAccesos " + SPerfilesAccesosCollectionOrphanCheckSPerfilesAccesos + " in its SPerfilesAccesosCollection field has a non-nullable SPerfiles field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SUsuarios> SUsuariosCollection = SPerfiles.getSUsuariosCollection();
            for (SUsuarios SUsuariosCollectionSUsuarios : SUsuariosCollection) {
                SUsuariosCollectionSUsuarios.setIdPerfil(null);
                SUsuariosCollectionSUsuarios = em.merge(SUsuariosCollectionSUsuarios);
            }
            em.remove(SPerfiles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SPerfiles> findSPerfilesEntities() {
        return findSPerfilesEntities(true, -1, -1);
    }

    public List<SPerfiles> findSPerfilesEntities(int maxResults, int firstResult) {
        return findSPerfilesEntities(false, maxResults, firstResult);
    }

    private List<SPerfiles> findSPerfilesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SPerfiles.class));
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

    public SPerfiles findSPerfiles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SPerfiles.class, id);
        } finally {
            em.close();
        }
    }

    public int getSPerfilesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SPerfiles> rt = cq.from(SPerfiles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<SAccesos> getAccesFromUser(SPerfiles user) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<SAccesos> lista = new ArrayList<>();
        try {
            CriteriaQuery<SAccesos> query = cb.createQuery(SAccesos.class);
            Root<SAccesos> accesos = query.from(SAccesos.class);

            CollectionJoin<SAccesos, SPerfilesAccesos> perfilesAccesos = accesos.join(SAccesos_.sPerfilesAccesosCollection);
            query.select(accesos)
                    .where(cb.equal(perfilesAccesos.get(SPerfilesAccesos_.sPerfiles), user));
            TypedQuery<SAccesos> typedQuery = em.createQuery(query);
            lista = typedQuery.getResultList();
            em.close();
        } catch (Exception e) {
            em.close();
            Logger.getLogger(SPerfilesJpaController.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }

    public List<SAccesos> getAviableAccesFromUser(SPerfiles user) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<SAccesos> lista = new ArrayList<>();

        try {
            CriteriaQuery<SAccesos> query = cb.createQuery(SAccesos.class);
            Root<SAccesos> accesos = query.from(SAccesos.class);
            CollectionJoin<SAccesos, SPerfilesAccesos> accesPerfil = accesos.join(SAccesos_.sPerfilesAccesosCollection, JoinType.LEFT);
            accesPerfil.on(cb.equal(accesPerfil.get(SPerfilesAccesos_.sPerfiles), user));
            query.select(accesos).where(cb.isNull(accesPerfil.get(SPerfilesAccesos_.sPerfiles)));
            TypedQuery<SAccesos> typedQuery = em.createQuery(query);
            lista = typedQuery.getResultList();
            em.close();
        } catch (Exception e) {
            em.close();
            Logger.getLogger(SPerfilesJpaController.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
}
