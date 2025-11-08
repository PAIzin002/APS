package modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class LeiturasDAO {

    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("DHT_PU");

    public void salvar(Leituras leitura) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(leitura);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Leituras> listarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Leituras.findAll", Leituras.class)
                     .setMaxResults(50)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public Leituras buscarUltima() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Leituras> lista = em.createNamedQuery("Leituras.findAll", Leituras.class)
                                     .setMaxResults(1)
                                     .getResultList();
            return lista.isEmpty() ? null : lista.get(0);
        } finally {
            em.close();
        }
    }
}
