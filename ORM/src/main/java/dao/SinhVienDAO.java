package dao;

import entity.SinhVien;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class SinhVienDAO extends BaseDao<SinhVien, String> {


    @Override
    protected Class<SinhVien> getClazz() {
        return SinhVien.class;
    }
}
