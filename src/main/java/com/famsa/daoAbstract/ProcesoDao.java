/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.daoAbstract;


import javax.faces.application.FacesMessage;

/**
 * @author Jhonny
 */
public interface ProcesoDao<T> {
    
    public void listenerSelected();
    
    public void delete(T entity);

    public void consultar();

    public void limpiar();

    public void create();

    public void edit();

    public void generarMsg(FacesMessage msg, boolean isValidate);

    public String getInformacion();

    public void init();

}
