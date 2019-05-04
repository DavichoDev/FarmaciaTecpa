
package com.backClasses;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author David Carmona
 */
public class Conexion {
    
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/farmacia";

    public Conexion() {
        conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null){
                System.out.println("Conexion establecida!");
            }
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar: " + e);
        }
    }
    
    //Este metodo nos retorna la conexion
    public Connection getConnection(){
        return conn;
    }

    public void desconectar(){
        conn = null;
        if(conn == null){
            System.out.println("Conexion terminada.");
        }
    }
}
