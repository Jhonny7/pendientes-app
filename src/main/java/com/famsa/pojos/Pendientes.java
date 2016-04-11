/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.famsa.pojos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jhonny
 */
@Entity
@Table(name = "pendientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pendientes.findAll", query = "SELECT p FROM Pendientes p"),
    @NamedQuery(name = "Pendientes.findById", query = "SELECT p FROM Pendientes p WHERE p.id = :id"),
    @NamedQuery(name = "Pendientes.findByNombre", query = "SELECT p FROM Pendientes p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Pendientes.findByNombreId", query = "SELECT p FROM Pendientes p WHERE p.nombre = :nombre AND p.id<> :id"),
    @NamedQuery(name = "Pendientes.findByActividad", query = "SELECT p FROM Pendientes p WHERE p.actividad = :actividad")})
public class Pendientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "actividad")
    private String actividad;

    public Pendientes() {
    }

    public Pendientes(Integer id) {
        this.id = id;
    }

    public Pendientes(Integer id, String nombre, String actividad) {
        this.id = id;
        this.nombre = nombre;
        this.actividad = actividad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
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
        if (!(object instanceof Pendientes)) {
            return false;
        }
        Pendientes other = (Pendientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.famsa.pojos.Pendientes[ id=" + id + " ]";
    }

}
