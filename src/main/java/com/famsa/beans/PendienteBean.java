/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.beans;

import com.famsa.pojos.Pendientes;
import com.famsa.service.PendienteService;
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
public class PendienteBean extends AbstractBean<Pendientes> implements Serializable {

    public Pendientes current;
    public Pendientes selectedCurrent;
    public Pendientes filterCurrent;

    private Class<Pendientes> clazz;
    private List<Pendientes> currents = new ArrayList<Pendientes>();
    private boolean showPanelDatos;
    private String tipoActualizacion;
    private Map<String, Object> mapaEdit = new HashMap<String, Object>();
    private Map<String, Object> mapaCreate = new HashMap<String, Object>();
    private String nameQueryEdit;
    private String nameQueryCreate;
    @Autowired
    private PendienteService pendienteService;

    /*Metodos previamente implementados*/
    @PostConstruct
    protected void init() {
        setClazz(Pendientes.class);
        setShowPanelDatos(true);
        setCurrent(new Pendientes());
        setCurrents(getPendienteService().getPendienteDao().findAll());
        setNameQueryCreate("Pendientes.findByNombre");
        setNameQueryEdit("Pendientes.findByNombreId");
    }

    public void search() {
        setCurrents(getPendienteService().getPendienteDao().findAll());
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
        Pendientes currentEdit = (Pendientes) event.getObject();
        createMapEdit(currentEdit);
        FacesMessage msg = editCurrent(currentEdit, getMapaEdit(), getNameQueryEdit());
        generarMsg(msg, FacesMessage.SEVERITY_INFO.equals(msg.getSeverity()));
    }

    public void onRowSelect(SelectEvent event) {
        setCurrent((Pendientes) event.getObject());
    }

    public void onRowCancel(RowEditEvent event) {
        Pendientes currentCancel = (Pendientes) event.getObject();
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
        search();
    }

    /*Métodos*/
    public FacesMessage editCurrent(Pendientes t, Map<String, Object> mapa, String queryName) {
        try {
            if (getPendienteService().getPendienteDao().findByQueryName(queryName, mapa).isEmpty()) {
                getPendienteService().getPendienteDao().edit(t);
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

    public FacesMessage cancelEditCurrent(Pendientes t) {
        try {
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_INFO, "Editar cancelado ");
        } catch (Exception e) {
            e.printStackTrace();
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, "Error en cancelar edit");
        }
    }

    public FacesMessage createCurrent(Pendientes t, Map<String, Object> mapa, String queryName) {
        try {
            if (getPendienteService().getPendienteDao().findByQueryName(queryName, mapa).isEmpty()) {
                Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, "Entre a crear-> ");
                getPendienteService().getPendienteDao().create(t);
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

    public FacesMessage deleteCurrent(Pendientes t) {
        try {
            getPendienteService().getPendienteDao().delete(t);
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_INFO, MessagesView.EXITOSO_BORRAR_REGISTRO);
        } catch (Exception e) {
            e.printStackTrace();
            return Utilerias.getFacesMessage(FacesMessage.SEVERITY_ERROR, MessagesView.ERROR_BORRAR_REGISTRO);
        }
    }

    public void cleanObject() {
        try {
            setCurrent(new Pendientes());
        } catch (Exception e) {
            Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void cleanSearch() {
        try {
            setFilterCurrent(new Pendientes());
            search();
        } catch (Exception e) {
            Logger.getLogger(AbstractBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Pendientes getCurrent() {
        return current;
    }

    public void setCurrent(Pendientes current) {
        this.current = current;
    }

    public Pendientes getSelectedCurrent() {
        return selectedCurrent;
    }

    public void setSelectedCurrent(Pendientes selectedCurrent) {
        this.selectedCurrent = selectedCurrent;
    }

    public Pendientes getFilterCurrent() {
        return filterCurrent;
    }

    public void setFilterCurrent(Pendientes filterCurrent) {
        this.filterCurrent = filterCurrent;
    }

    public Class<Pendientes> getClazz() {
        return clazz;
    }

    public void setClazz(Class<Pendientes> clazz) {
        this.clazz = clazz;
    }

    public List<Pendientes> getCurrents() {
        return currents;
    }

    public void setCurrents(List<Pendientes> currents) {
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

    public void createMapEdit(Pendientes current) {
        Map<String, Object> mapaEdit = new HashMap<String, Object>();
        mapaEdit.put("nombre", current.getNombre());
        mapaEdit.put("id", current.getId());
        setMapaEdit(mapaEdit);
    }

    public void createMapCreate(Pendientes current) {
        Map<String, Object> mapaCreate = new HashMap<String, Object>();
        mapaCreate.put("nombre", current.getNombre());
        setMapaCreate(mapaCreate);
    }

    public PendienteService getPendienteService() {
        return pendienteService;
    }

    public void setPendienteService(PendienteService pendienteService) {
        this.pendienteService = pendienteService;
    }
    
    public String getInfo(){
        return Utilerias.getInformacion(getCurrents().size());
    }
}
