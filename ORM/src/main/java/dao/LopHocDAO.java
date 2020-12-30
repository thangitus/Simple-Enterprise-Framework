package dao;

import entity.LopHoc;
import entity.SinhVien;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class LopHocDAO {
    EntityManagerFactory entityManagerFactory = EntityManagerProvider.getEntityManagerFactory();
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


    public LopHoc findByID(String lophocid) {
        LopHoc lopHoc = entityManager.find(LopHoc.class, lophocid);
        return lopHoc;
    }

    public List<LopHoc> findAll() {
        return entityManager.createQuery("SELECT lh FROM LopHoc lh", LopHoc.class).getResultList();
    }

    public void delete(LopHoc... lopHoc) {
        entityManager.getTransaction().begin();
        entityManager.remove(lopHoc);
        entityManager.getTransaction().commit();
    }
}
