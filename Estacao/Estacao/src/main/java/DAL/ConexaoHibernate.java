package DAL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConexaoHibernate {

    private static final EntityManagerFactory EMF = 
            Persistence.createEntityManagerFactory("crud-pessoasPU");
    
    private static EntityManager em = null;

    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = EMF.createEntityManager();
            System.out.println("🔗 Conexão com o banco aberta.");
        }
        return em;
    }

    public static void close() {
        try {
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("EntityManager fechado.");
            }
            if (EMF.isOpen()) {
                EMF.close();
                System.out.println("EntityManagerFactory fechado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
