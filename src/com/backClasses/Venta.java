/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backClasses;

/**
 *
 * @author gamer
 */
public class Venta {
    String id;
    String nombre;
    float precio;
    float precioCompra;
    float cantidad;

    public Venta(float cantidad,String id, String nombre, float precio, float precioCompra) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = 1.0f;
        this.precioCompra = precioCompra;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
    
}
