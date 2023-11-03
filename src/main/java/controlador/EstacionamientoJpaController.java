package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Estacionamiento;
import modelo.Vehiculo;

public class EstacionamientoJpaController implements Serializable {

    public EstacionamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EstacionamientoJpaController() {
        emf = Persistence.createEntityManagerFactory("controlParqueoPrivadoJPA");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estacionamiento estacionamiento) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(estacionamiento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstacionamiento(estacionamiento.getNumeroParqueo()) != null) {
                throw new PreexistingEntityException("Estacionamiento " + estacionamiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estacionamiento estacionamiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            estacionamiento = em.merge(estacionamiento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = estacionamiento.getNumeroParqueo();
                if (findEstacionamiento(id) == null) {
                    throw new NonexistentEntityException("The estacionamiento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estacionamiento estacionamiento;
            try {
                estacionamiento = em.getReference(Estacionamiento.class, id);
                estacionamiento.getNumeroParqueo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estacionamiento with id " + id + " no longer exists.", enfe);
            }
            em.remove(estacionamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estacionamiento> findEstacionamientoEntities() {
        return findEstacionamientoEntities(true, -1, -1);
    }

    public List<Estacionamiento> findEstacionamientoEntities(int maxResults, int firstResult) {
        return findEstacionamientoEntities(false, maxResults, firstResult);
    }

    private List<Estacionamiento> findEstacionamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estacionamiento.class));
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

    public Estacionamiento findEstacionamiento(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estacionamiento.class, id);
        } finally {
            em.close();
        }
    }

    public List<Estacionamiento> findEstacionamientoByVehiculo(String id) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("Select e FROM Estacionamiento e WHERE e.vehiculo.placa = :id" );
            query.setParameter("id", id);
            List<Estacionamiento> result = query.getResultList();

            return result;
        } finally {
            em.close();
        }
    }

    public int getEstacionamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estacionamiento> rt = cq.from(Estacionamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
