/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.daoAbstract;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jhonny
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface AbstractDao<T> extends Serializable {

    boolean create(T entity) throws Exception;
    
    boolean edit(T entity) throws Exception;
    
    boolean delete(T entity) throws Exception;
    
    List<T> findAll();
    
    public <T> List<T> findByQueryName(final String queryName, final Map<String, Object> values);
}
