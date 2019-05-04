/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backClasses;

/**
 *
 * @author David Carmona
 */
public class Receta {
    private String folio;
    private String nombre;
    private String direccion;
    private String cedula;

    public Receta(String folio, String nombre, String direccion, String cedula) {
        this.folio = folio;
        this.nombre = nombre;
        this.direccion = direccion;
        this.cedula = cedula;
    }
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}
