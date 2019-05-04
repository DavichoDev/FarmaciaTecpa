/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backClasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author David Carmona
 */
public class OPBase {

    /**
     * Clase dedicada a la logica de la base de datos.
     */
    Conexion con = new Conexion();
    Connection re = con.getConnection();

    public boolean compararID(String num) {

        String query = "SELECT idProducto FROM productos";

        Statement st;

        try {
            st = re.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (num.equals(rs.getString(1))) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return true;
    }

    public boolean compararFolio(String num) {

        con.getConnection();
        String query = "SELECT folio_receta FROM RecetaMedica";

        Statement st;

        try {
            st = re.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (num.equals(rs.getString(1))) {
                    System.out.println("REPEDITOD ALV");
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        System.out.println("NO REPETIDO");
        return true;
    }

    public boolean compararNombre(String num) {

        String query = "SELECT nombre_prv FROM Proveedores";

        Statement st;

        try {
            st = re.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (num.equals(rs.getString(1))) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

        return true;
    }


}
