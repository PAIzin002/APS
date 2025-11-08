package DAL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPAUtil {

    private static final String PU_NAME = "EstacaoPU";
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory(PU_NAME);

    private JPAUtil() { }

    public static EntityManager getEM() {
        if (!EMF.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory já foi fechado.");
        }
        return EMF.createEntityManager();
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
