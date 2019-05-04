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
public class Producto {

    protected String codProducto;
    protected String nomProducto;
    protected String susProducto;
    protected String desProducto;
    protected float cantProducto;
    protected String provProducto;
    protected String tipProducto;
    protected float preCompraProducto;
    protected float preVentaProducto;

    public Producto(String codProducto, String nomProducto, String susProducto,
            String desProducto, float cantProducto, String provProducto,
            String tipProducto, float preCompraProducto, float preVentaProducto) {

        this.codProducto = codProducto;
        this.nomProducto = nomProducto;
        this.susProducto = susProducto;
        this.desProducto = desProducto;
        this.cantProducto = cantProducto;
        this.provProducto = provProducto;
        this.tipProducto = tipProducto;
        this.preVentaProducto = preCompraProducto;
        this.preVentaProducto = preVentaProducto;
    }

    public Producto(String codProducto, String nomProducto,String descProducto, String susProducto, float cantProducto, float preVentaProducto) {
        this.codProducto = codProducto;
        this.nomProducto = nomProducto;
        this.desProducto = descProducto;
        this.susProducto = susProducto;
        this.cantProducto = cantProducto;
        this.preVentaProducto = preVentaProducto;
    }
    
        public Producto(String codProducto, String nomProducto,String tipoProducto,String descProducto, String susProducto, float cantProducto, float preVentaProducto) {
        this.codProducto = codProducto;
        this.nomProducto = nomProducto;
        this.tipProducto = tipoProducto;
        this.desProducto = descProducto;
        this.susProducto = susProducto;
        this.cantProducto = cantProducto;
        this.preVentaProducto = preVentaProducto;
    }
    

    public float getPreCompraProducto() {
        return preCompraProducto;
    }

    public void setPreCompraProducto(float preCompraProducto) {
        this.preCompraProducto = preCompraProducto;
    }

    public float getPreVentaProducto() {
        return preVentaProducto;
    }

    public void setPreVentaProducto(float preVentaProducto) {
        this.preVentaProducto = preVentaProducto;
    }

    //Getters and Setters
    public void setCantProducto(float cantProducto) {
        this.cantProducto = cantProducto;
    }

    public float getCantProducto() {
        return cantProducto;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getSusProducto() {
        return susProducto;
    }

    public void setSusProducto(String susProducto) {
        this.susProducto = susProducto;
    }

    public String getDesProducto() {
        return desProducto;
    }

    public void setDesProducto(String desProducto) {
        this.desProducto = desProducto;
    }

    public String getProvProducto() {
        return provProducto;
    }

    public void setProvProducto(String provProducto) {
        this.provProducto = provProducto;
    }

    public String getTipProducto() {
        return tipProducto;
    }

    public void setTipProducto(String tipProducto) {
        this.tipProducto = tipProducto;
    }

}
