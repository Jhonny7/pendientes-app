/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.famsa.dao.impl;

import com.famsa.dao.PendienteDao;
import com.famsa.daoAbstract.impl.AbstractDaoImpl;
import com.famsa.pojos.Pendientes;
import java.io.Serializable;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jhonny
 */
public class PendientesDaoImpl extends AbstractDaoImpl<Pendientes> implements PendienteDao, Serializable{
    @Autowired
    SessionFactory sessionFactory;

}
