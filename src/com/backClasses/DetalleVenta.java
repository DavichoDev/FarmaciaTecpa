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
public class DetalleVenta {
    
    private String idProducto;
    private String nombreProducto;
    private float cantidadProducto;
    private float ivaDeVenta;
    private float subTotalVenta;
    private float totalDeVenta;
    private float totalAdq;

    public DetalleVenta(String idProducto,String nombreProducto, float cantidadProducto, float ivaDeVenta, float subTotalVenta, float totalDeVenta, float totalAdq) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.ivaDeVenta = ivaDeVenta;
        this.subTotalVenta = subTotalVenta;
        this.totalDeVenta = totalDeVenta;
        this.totalAdq = totalAdq;
    }

    public float getTotalAdq() {
        return totalAdq;
    }

    public void setTotalAdq(float totalAdq) {
        this.totalAdq = totalAdq;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public float getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(float cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public float getIvaDeVenta() {
        return ivaDeVenta;
    }

    public void setIvaDeVenta(float ivaDeVenta) {
        this.ivaDeVenta = ivaDeVenta;
    }

    public float getSubTotalVenta() {
        return subTotalVenta;
    }

    public void setSubTotalVenta(float subTotalVenta) {
        this.subTotalVenta = subTotalVenta;
    }

    public float getTotalDeVenta() {
        return totalDeVenta;
    }

    public void setTotalDeVenta(float totalDeVenta) {
        this.totalDeVenta = totalDeVenta;
    }
    
    
}
