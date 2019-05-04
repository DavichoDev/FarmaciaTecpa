/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backClasses;

import java.sql.Date;

/**
 *
 * @author David Carmona
 */
public class Ventas {

    private int idVenta;
    private String folioReceta;
    private java.sql.Date fecha;
    private float subtotalVenta;
    private float subtotal;
    private float ivaAcum;
    private float ivaAcumPag;
    private float costoAd;
    private float total;
    private float gananciaRelativa;
    private float gananciaReal;

    public Ventas(int idVenta, String folioReceta, Date fecha, float subtotalVenta, float subtotal, float ivaAcum, float ivaAcumPag, float costoAd, float total, float gananciaRelativa, float gananciaReal) {
        this.idVenta = idVenta;
        this.folioReceta = folioReceta;
        this.fecha = fecha;
        this.subtotalVenta = subtotalVenta;
        this.subtotal = subtotal;
        this.ivaAcum = ivaAcum;
        this.ivaAcumPag = ivaAcumPag;
        this.costoAd = costoAd;
        this.total = total;
        this.gananciaRelativa = gananciaRelativa;
        this.gananciaReal = gananciaReal;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFolioReceta() {
        return folioReceta;
    }

    public void setFolioReceta(String folioReceta) {
        this.folioReceta = folioReceta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(float subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getIvaAcum() {
        return ivaAcum;
    }

    public void setIvaAcum(float ivaAcum) {
        this.ivaAcum = ivaAcum;
    }

    public float getIvaAcumPag() {
        return ivaAcumPag;
    }

    public void setIvaAcumPag(float ivaAcumPag) {
        this.ivaAcumPag = ivaAcumPag;
    }

    public float getCostoAd() {
        return costoAd;
    }

    public void setCostoAd(float costoAd) {
        this.costoAd = costoAd;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getGananciaRelativa() {
        return gananciaRelativa;
    }

    public void setGananciaRelativa(float gananciaRelativa) {
        this.gananciaRelativa = gananciaRelativa;
    }

    public float getGananciaReal() {
        return gananciaReal;
    }

    public void setGananciaReal(float gananciaReal) {
        this.gananciaReal = gananciaReal;
    }

}
