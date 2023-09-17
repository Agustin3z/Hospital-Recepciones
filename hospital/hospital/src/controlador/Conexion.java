/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Miqueas Corzo
 */
public class Conexion {

    public static Connection Conexion() throws ClassNotFoundException, SQLException {
        
       
        
        Class.forName("org.postgresql.Driver");
        String direccion = "jdbc:postgresql://localhost:5432/hospitalbd";
        Connection conexion = DriverManager.getConnection(direccion, "postgres", "miqueas123");
        System.out.println("Conexion Exitosa");
        
        return (conexion);
    }
}
