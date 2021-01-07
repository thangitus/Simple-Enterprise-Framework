package dao;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseDao<T, E> {
    EntityManager entityManager = EntityManagerProvider.createEntityManager();

    public void insert(T... objects) {
        entityManager.getTransaction().begin();
        for (T object : objects) {
            entityManager.persist(object);
        }
        entityManager.getTransaction().commit();
    }

    public T getById(E id) {
        return entityManager.find(getClazz(), id);
    }

    public List<T> getAll() {
        return entityManager.createQuery("SELECT obj FROM " + getClazz().getSimpleName() + " obj", getClazz())
                            .getResultList();
    }

    public void delete(T... objects) {
        entityManager.getTransaction().begin();
        for (T object : objects) {
            entityManager.remove(object);
        }
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
    }

    protected abstract Class<T> getClazz();

}
