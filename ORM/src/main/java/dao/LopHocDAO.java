package dao;

import entity.LopHoc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LopHocDAO {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void save (LopHoc lopHoc){
        entityManager.getTransaction().begin();
        entityManager.persist(lopHoc);
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
