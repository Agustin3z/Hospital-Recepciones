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
public class camaControlador {
Connection conexion = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel modelo;
    ResultSetMetaData rsm = null;

    public camaControlador() throws ClassNotFoundException, SQLException {
        conexion = Conexion.Conexion();
    }
    
    public void llenarTablcama(JTable tabla) throws SQLException {
        modelo = new DefaultTableModel();

        modelo.addColumn("numero");
        modelo.addColumn("estado");
        
        

        tabla.setModel(modelo);

        String consulta = "SELECT numero, estado  FROM public.cama";

        pst = conexion.prepareStatement(consulta);
        rs = pst.executeQuery();
        rsm = rs.getMetaData();
        while (rs.next()) {
            Object[] fila = new Object[rsm.getColumnCount()];
            fila[0] = rs.getInt(1);
            fila[1] = rs.getString(2);
            
            

            modelo.addRow(fila);

        }
        rs.close();

    }
    
    
    public void agregar_cama(Cama cama, JTable tabla) throws SQLException {
        String consulta = "INSERT INTO public.cama(numero, estado, id_habitacion)VALUES (?, ?, ?)";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, cama.getNumero());
        pst.setString(2, cama.getEstado());
        pst.setInt(3, cama.getHabitacion().getNumero());
      
        
        

        pst.execute();
         llenarTablcama(tabla);

     
    }
    
    public ArrayList<Habitacion> Extraer_habitaciones() throws SQLException {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        String consulta = "SELECT numero, id_ubicacion FROM public.habitacion";
        pst = conexion.prepareStatement(consulta);
        ResultSet rs = null;
        rs = pst.executeQuery();

        while (rs.next()) {
            Habitacion habitacion = new Habitacion();
            habitacion.setNumero(rs.getInt(1));
           
             habitaciones.add(habitacion);
           
        }
        rs.close();

        return habitaciones;
    }
    
    public ArrayList<Cama> Extraer_cama() throws SQLException {
        ArrayList<Cama> camas = new ArrayList<>();
        String consulta = "SELECT * FROM public.cama";
        pst = conexion.prepareStatement(consulta);
        ResultSet rs = null;
        rs = pst.executeQuery();

        while (rs.next()) {
            Cama cama = new Cama();
            cama.setNumero(rs.getInt(1));
            cama.setEstado(rs.getString(2));
           
             camas.add(cama);
           
        }
        rs.close();

        return camas;
    }
    
     public void Eliminar_cama(int id, JTable tabla) throws SQLException {

        String consulta = "DELETE FROM public.cama WHERE numero = ?";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, id);
        pst.execute();

        llenarTablcama(tabla);
    }
     
     public void modificar_cama( int numero, String estado, JTable tabla) throws SQLException {

        String consulta = "UPDATE public.cama SET estado= ? WHERE numero=?";
        pst = conexion.prepareStatement(consulta);
        pst.setString(1, estado);
        pst.setInt(2, numero);

        pst.execute();
         llenarTablcama(tabla);
    }

 
}
