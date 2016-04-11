/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.famsa.service;

import com.famsa.dao.GenericDao;

/**
 *
 * @author Jhonny
 */
public interface GenericService <T>{
    public GenericDao<T> getGenericDao();
}
