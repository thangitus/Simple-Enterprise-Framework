package dao;

import entity.SinhVien;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class SinhVienDAO {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

        public void save (SinhVien sinhVien){
        entityManager.getTransaction().begin();
        entityManager.persist(sinhVien);
        entityManager.getTransaction().commit();
    }

    public SinhVien findByID(String mssv){
            SinhVien sinhVien = entityManager.find(SinhVien.class, mssv);
            return sinhVien;
    }

    public List<SinhVien> findAll(){
            return entityManager.createQuery("SELECT sv FROM SinhVien sv", SinhVien.class).getResultList();
    }

    public void delete(SinhVien sinhVien) {
            entityManager.getTransaction().begin();
            entityManager.remove(sinhVien);
            entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

}
