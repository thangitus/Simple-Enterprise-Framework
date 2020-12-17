package dao;

import entity.ChiTietLopHoc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ChiTietLopHocDAO {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void save(ChiTietLopHoc chiTietLopHoc){
        entityManager.getTransaction().begin();
        entityManager.persist(chiTietLopHoc);
        entityManager.getTransaction().commit();
    }

    public List<ChiTietLopHoc> findAll() {
        return entityManager.createQuery("SELECT c from ChiTietLopHoc c", ChiTietLopHoc.class).getResultList();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
