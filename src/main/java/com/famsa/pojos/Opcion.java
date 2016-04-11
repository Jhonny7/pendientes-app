/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.famsa.pojos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jhonny
 */
@Entity
@Table(name = "opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcion.findAll", query = "SELECT o FROM Opcion o"),
    @NamedQuery(name = "Opcion.findById", query = "SELECT o FROM Opcion o WHERE o.id = :id"),
    @NamedQuery(name = "Opcion.findByOpcionId", query = "SELECT o FROM Opcion o WHERE o.opcionId = :opcionId"),
    @NamedQuery(name = "Opcion.findByOrden", query = "SELECT o FROM Opcion o WHERE o.orden = :orden"),
    @NamedQuery(name = "Opcion.findByControl", query = "SELECT o FROM Opcion o WHERE o.control = :control"),
    @NamedQuery(name = "Opcion.findByImagen", query = "SELECT o FROM Opcion o WHERE o.imagen = :imagen"),
    @NamedQuery(name = "Opcion.findByNombre", query = "SELECT o FROM Opcion o WHERE o.nombre = :nombre")})
public class Opcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "opcion_id")
    private Integer opcionId;
    @Basic(optional = false)
    @Column(name = "orden")
    private int orden;
    @Column(name = "control")
    private String control;
    @Column(name = "imagen")
    private String imagen;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "opcionId")
    private List<Permiso> permisoList;

    public Opcion() {
    }

    public Opcion(Integer id) {
        this.id = id;
    }

    public Opcion(Integer id, int orden, String nombre) {
        this.id = id;
        this.orden = orden;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(Integer opcionId) {
        this.opcionId = opcionId;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Permiso> getPermisoList() {
        return permisoList;
    }

    public void setPermisoList(List<Permiso> permisoList) {
        this.permisoList = permisoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.famsa.pojos.Opcion[ id=" + id + " ]";
    }

}
