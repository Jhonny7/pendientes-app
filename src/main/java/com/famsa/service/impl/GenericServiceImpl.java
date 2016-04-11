/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.service.impl;

import com.famsa.dao.GenericDao;
import com.famsa.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jhonny
 */
public class GenericServiceImpl<T> implements GenericService<T> {

    @Autowired
    private GenericDao<T> genericDao;

    @Override
    public GenericDao<T> getGenericDao() {
        return genericDao;
    }

    public void setGenericDao(GenericDao<T> genericDao) {
        this.genericDao = genericDao;
    }

}
