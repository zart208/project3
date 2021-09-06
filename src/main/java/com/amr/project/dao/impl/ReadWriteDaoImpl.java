package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class ReadWriteDaoImpl<T, K> implements ReadWriteDao<T, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected ReadWriteDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public void persist(T obj) {
        entityManager.persist(obj);
    }

    @Override
    @Transactional
    public void update(T obj) {
        entityManager.merge(obj);
    }

    @Override
    @Transactional
    public void delete(T obj) {
        entityManager.remove(entityManager.contains(obj) ? obj : entityManager.merge(obj));
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeEnable(K key) {
        entityManager.remove(entityManager.find(entityClass, key));
    }

    @Override
    @Transactional
    public void deleteByKeyCascadeIgnore(K key) {
        Query query = entityManager.createQuery(
                "DELETE FROM " + entityClass.getName() + " t WHERE t.id = :id");
        query.setParameter("id", key);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(K id) {
        return entityManager.find(entityClass, id) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public T getByKey(K key) {
        return entityManager.find(entityClass, key);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> getAll() {
        return entityManager.createQuery("FROM " + entityClass.getName()).getResultList();
    }
}
