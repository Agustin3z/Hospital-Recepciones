/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author Miqueas Corzo
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.*;
public class habitacionControlador {
     Connection conexion = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel modelo;
    ResultSetMetaData rsm = null;

    public habitacionControlador() throws ClassNotFoundException, SQLException {
        conexion = Conexion.Conexion();
    }
    
     public void llenarTablahabitacion(JTable tabla) throws SQLException {
        modelo = new DefaultTableModel();

        modelo.addColumn("numero");
        

        tabla.setModel(modelo);

        String consulta = "SELECT numero autor_id FROM public.habitacion";

        pst = conexion.prepareStatement(consulta);
        rs = pst.executeQuery();
        rsm = rs.getMetaData();
        while (rs.next()) {
            Object[] fila = new Object[rsm.getColumnCount()];
            fila[0] = rs.getInt(1);
            

            modelo.addRow(fila);

        }
        rs.close();

    }
     
      public void agregar_habitacion(Habitacion habitacion, JTable tabla) throws SQLException {
        String consulta = "INSERT INTO public.habitacion(numero, id_ubicacion)VALUES (?, ?)";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, habitacion.getNumero());
        pst.setInt(2, habitacion.getUbicacion().getId());
        
        

        pst.execute();
          llenarTablahabitacion(tabla);

     
    }
    
      
      public ArrayList<Ubicacion> Extraer_ubicaciones() throws SQLException {
        ArrayList<Ubicacion> ubicaciones = new ArrayList<>();
        String consulta = "SELECT * FROM public.ubicacion";
        pst = conexion.prepareStatement(consulta);
        ResultSet rs = null;
        rs = pst.executeQuery();

        while (rs.next()) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setHospital(rs.getString(1));
            ubicacion.setNombre(rs.getString(2));
            ubicacion.setId(rs.getInt(3));
            ubicaciones.add(ubicacion);
        }
        rs.close();

        return ubicaciones;
    }
     public void Eliminar_habitacion(int id, JTable tabla) throws SQLException {

        String consulta = "DELETE FROM public.habitacion WHERE numero = ?";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, id);
        pst.execute();

         llenarTablahabitacion(tabla);
    }
    
}
