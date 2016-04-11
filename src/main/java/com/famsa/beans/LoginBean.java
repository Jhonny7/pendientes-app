/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.beans;

import com.famsa.pojos.Usuario;
import com.famsa.service.UsuarioService;
import com.famsa.utils.Utilerias;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jhonny
 */
public class LoginBean implements Serializable{

    private Usuario usuario = new Usuario();
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {
        if (getUsuario() == null) {
            setUsuario(new Usuario());
        }
    }

    public void login() {
        RequestContext context = RequestContext.getCurrentInstance();/*Request para */
        FacesMessage msg;/*Mensaje en pantalla*/
        String ruta = "";/*redirecciona*/
        Map<String, Object> mapafind = new HashMap<String, Object>();
        mapafind.put("user", getUsuario().getUser());
        mapafind.put("password", getUsuario().getPassword());
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();/*External para redireccionar*/
        try {
            Usuario user = (Usuario) getUsuarioService().getUsuarioDao().findByQueryName("Usuario.findByUserAndPassword", mapafind).get(0);
            if (user != null) {
                Utilerias.setUsuarioSession(user);/*Mapa de sesión*/
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Login correcto", getUsuario().getUser());
                ruta = "/inicio.xhtml";
                ec.redirect(ec.getRequestContextPath()+ruta);/*Redirecciona a página de inicio*/
            } else {
                setUsuario(new Usuario());
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales erroneas", null);
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

}
