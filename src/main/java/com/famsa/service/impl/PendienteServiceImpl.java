/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.famsa.service.impl;

import com.famsa.dao.PendienteDao;
import com.famsa.service.PendienteService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jhonny
 */
public class PendienteServiceImpl implements PendienteService{
    @Autowired
    private PendienteDao pendienteDao;

    public PendienteDao getPendienteDao() {
        return pendienteDao;
    }

    public void setPendienteDao(PendienteDao pendienteDao) {
        this.pendienteDao = pendienteDao;
    }
    
}
