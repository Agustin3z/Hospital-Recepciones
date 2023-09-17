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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class ubicacionControlador {
    Connection conexion = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel modelo;
    ResultSetMetaData rsm = null;

    public ubicacionControlador() throws ClassNotFoundException, SQLException {
        conexion = Conexion.Conexion();
    }
     public void llenarTablaUbicacion(JTable tabla) throws SQLException {
        modelo = new DefaultTableModel();

        modelo.addColumn("hospital");
        modelo.addColumn("nombre");
        modelo.addColumn("id");
       
        
        

        tabla.setModel(modelo);

        String consulta = "SELECT* FROM public.ubicacion";

        pst = conexion.prepareStatement(consulta);
        rs = pst.executeQuery();
        rsm = rs.getMetaData();
        while (rs.next()) {
            Object[] fila = new Object[rsm.getColumnCount()];
            fila[0] = rs.getString(1);
            fila[1] = rs.getString(2);
            fila[2] = rs.getString(3);
            
            

            modelo.addRow(fila);

        }
        rs.close();

    }
     
      public void agregar_ubicacion(String hospital, String nombre, int id, JTable tabla) throws SQLException {
        String consulta = "INSERT INTO public.ubicacion( hospital, nombre, id) VALUES (?, ?,? )";
        pst = conexion.prepareStatement(consulta);
        pst.setString(1, hospital);
        pst.setString(2, nombre);
        pst.setInt (3,id);

        pst.execute();

          llenarTablaUbicacion(tabla);
    }
      
       public void Eliminar_ubicacion(int id, JTable tabla) throws SQLException {

        String consulta = "DELETE FROM public.ubicacion WHERE id = ?";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, id);
        pst.execute();

           llenarTablaUbicacion(tabla);
    }
      
    
}
