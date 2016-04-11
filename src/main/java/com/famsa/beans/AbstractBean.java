/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.beans;

import com.famsa.service.GenericService;
import com.famsa.utils.MessagesView;
import com.famsa.utils.Utilerias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jhonny
 */
public abstract class AbstractBean<T> implements Serializable{

    public T current;
    public T selectedCurrent;
    public T filterCurrent;

    private Class<T> clazz;
    private List<T> currents = new ArrayList<T>();
    private boolean showPanelDatos;
    private String tipoActualizacion;
    private Map<String, Object> mapaEdit = new HashMap<String, Object>();
    private Map<String, Object> mapaCreate = new HashMap<String, Object>();
    private String nameQueryEdit;
    private String nameQueryCreate;
    @Autowired
    private GenericService<T> genericService;
    /*Metodos previamente implementados*/
    @PostConstruct
    protected abstract void init();

    public abstract void createMapEdit(T current);

    public abstract void createMapCreate(T current);

    public void search() {
        setCurrents(getGenericService().getGenericDao().findAll());
    }

    public void generarMsg(FacesMessage msg, boolean isValidate) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("isValidate", isValidate);
        if (isValidate) {
            search();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        T currentEdit = (T) event.getObject();
        createMapEdit(currentEdit);
        FacesMessage msg = editCurrent(currentEdit, getMapaEdit(), getNameQueryEdit());
        generarMsg(msg, FacesMessage.SEVERITY_INFO.equals(msg.getSeverity()));
    }

    public void onRowSelect(SelectEvent event) {
        setCurrent((T) event.getObject());
    }

    public void onRowCancel(RowEditEvent event) {
        T currentCancel = (T) event.getObject();
        FacesMessage msg = cancelEditCurrent(currentCancel);
        generarMsg(msg, FacesMessage.SEVERITY_INFO.equals(msg.getSeverity()));
    }

    public void create() {
        createMapCreate(getCurrent());
        FacesMessage msg = createCurrent(getCurrent(), getMapaCreate(), getNameQueryCreate());
        generarMsg(msg, FacesMessage.SEVERITY_INFO.equals(msg.getSeverity()));
    }

    public void update() {
        createMapEdit(getCurrent());
        FacesMessage msg = editCurrent(getCurrent(), getMapaEdit(), getNameQueryEdit());
        setShowPanelDatos(true);
        generarMsg(msg, FacesMessage.SEVERITY_INFO.equals(msg.getSeverity()));
    }

    public void delete() {
        FacesMessage msg = deleteCurrent(getSelectedCurrent());
        generarMsg(msg, FacesMessage.SEVERITY_INFO.equals(msg.getSeverity()));
    }

    /*Métodos*/
    public FacesMessage editCurrent(T t, Map<String, Object> mapa, String queryName) {
        try {
            if (getGenericService().getGenericDao().findByQueryName(queryName, mapa).isEmpty()) {
                getGenericService().getGenericDao().edit(t);
                return Utilerias.getFacesMessage(FacesMessage.SEVERITY_INFO, MessagesView.EXITOSO_EDITAR_REGISTRO);
            } else {
                search();
                return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, MessagesView.ERROR_EDITAR_REGISTRO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, MessagesView.ERROR_EDITAR_REGISTRO);
        }
    }

    public FacesMessage cancelEditCurrent(T t) {
        try {
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_INFO, "Editar cancelado ");
        } catch (Exception e) {
            e.printStackTrace();
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, "Error en cancelar edit");
        }
    }

    public FacesMessage createCurrent(T t, Map<String, Object> mapa, String queryName) {
        try {
            if (getGenericService().getGenericDao().findByQueryName(queryName, mapa).isEmpty()) {
                Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, "Entre a crear-> ");
                getGenericService().getGenericDao().create(t);
                setShowPanelDatos(true);
                return Utilerias.getFacesMessage(FacesMessage.SEVERITY_INFO, MessagesView.EXITOSO_CREAR_REGISTRO);
            } else {
                search();
                return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, MessagesView.ERROR_CREAR_REGISTRO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, MessagesView.ERROR_CREAR_REGISTRO);
        }
    }

    public FacesMessage deleteCurrent(T t) {
        try {
            getGenericService().getGenericDao().delete(t);
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_INFO, MessagesView.EXITOSO_BORRAR_REGISTRO);
        } catch (Exception e) {
            e.printStackTrace();
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, MessagesView.ERROR_BORRAR_REGISTRO);
        }
    }

    public void cleanObject() {
        try {
            setCurrent(getClazz().newInstance());
        } catch (Exception e) {
            Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void cleanSearch() {
        try {
            setFilterCurrent(getClazz().newInstance());
            search();
        } catch (Exception e) {
            Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public T getSelectedCurrent() {
        return selectedCurrent;
    }

    public void setSelectedCurrent(T selectedCurrent) {
        this.selectedCurrent = selectedCurrent;
    }

    public T getFilterCurrent() {
        return filterCurrent;
    }

    public void setFilterCurrent(T filterCurrent) {
        this.filterCurrent = filterCurrent;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> getCurrents() {
        return currents;
    }

    public void setCurrents(List<T> currents) {
        this.currents = currents;
    }

    public boolean isShowPanelDatos() {
        return showPanelDatos;
    }

    public void setShowPanelDatos(boolean showPanelDatos) {
        this.showPanelDatos = showPanelDatos;
    }

    public String getTipoActualizacion() {
        return tipoActualizacion;
    }

    public void setTipoActualizacion(String tipoActualizacion) {
        this.tipoActualizacion = tipoActualizacion;
    }

    public Map<String, Object> getMapaEdit() {
        return mapaEdit;
    }

    public void setMapaEdit(Map<String, Object> mapaEdit) {
        this.mapaEdit = mapaEdit;
    }

    public Map<String, Object> getMapaCreate() {
        return mapaCreate;
    }

    public void setMapaCreate(Map<String, Object> mapaCreate) {
        this.mapaCreate = mapaCreate;
    }

    public String getNameQueryEdit() {
        return nameQueryEdit;
    }

    public void setNameQueryEdit(String nameQueryEdit) {
        this.nameQueryEdit = nameQueryEdit;
    }

    public String getNameQueryCreate() {
        return nameQueryCreate;
    }

    public void setNameQueryCreate(String nameQueryCreate) {
        this.nameQueryCreate = nameQueryCreate;
    }

    public GenericService<T> getGenericService() {
        return genericService;
    }

    public void setGenericService(GenericService<T> genericService) {
        this.genericService = genericService;
    }

}
