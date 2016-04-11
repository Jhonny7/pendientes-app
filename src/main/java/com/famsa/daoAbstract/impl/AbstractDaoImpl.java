/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.daoAbstract.impl;

import com.famsa.daoAbstract.AbstractDao;
import org.hibernate.Hibernate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;

@Transactional
@Repository
public abstract class AbstractDaoImpl<T> extends HibernateDaoSupport implements AbstractDao<T> {

    private Class<T> entityClass;

    protected static <T> void toInitializedList(List<T> list) {
        for (final T element : list) {
            Hibernate.initialize(element);
        }
    }

    @Override
    public boolean create(T entity) throws Exception {
        try {
            getHibernateTemplate().persist(entity);
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(T entity) throws Exception {
        try {
            getHibernateTemplate().saveOrUpdate(entity);
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(T entity) throws Exception {
        try {
            //System.out.println("Entro a delete");
            getHibernateTemplate().delete(entity);
            getHibernateTemplate().flush();
            getHibernateTemplate().clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<T> findAll() {
        try {
            List<T> entities = getHibernateTemplate().loadAll(getEntityClass());
            toInitializedList(entities);
            return entities;
        } catch (Exception e) {
            System.err.println("Error en linea " + e);
            e.printStackTrace();
            return new ArrayList<T>();
        }
    }

    //
    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        if (entityClass == null) {
            ParameterizedType absDaoType = (ParameterizedType) this.getClass().getGenericSuperclass();
            entityClass = (Class<T>) absDaoType.getActualTypeArguments()[0];
        }
        return entityClass;
    }
    @Override
    public <T> List<T> findByQueryName(final String queryName, final Map<String, Object> values) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session sn) throws HibernateException {
                Query query = sn.getNamedQuery(queryName);
                for (String key : values.keySet()) {
                    query.setParameter(key, values.get(key));
                }
                return query.list();
            }
        });
    }
}
