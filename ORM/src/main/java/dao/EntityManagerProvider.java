package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private static final String PERSISTENCE_UNIT_NAME = "persistence";

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }

    public void closeEmf() {
        if (emf.isOpen() || emf != null) {
            emf.close();
        }
    }
}
