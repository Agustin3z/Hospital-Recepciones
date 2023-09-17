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
import controlador.*;

public class internacionControlador {

    Connection conexion = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    DefaultTableModel modelo;
    ResultSetMetaData rsm = null;

    public internacionControlador() throws ClassNotFoundException, SQLException {
        conexion = Conexion.Conexion();
    }

    public void llenarTablinternacion(JTable tabla) throws SQLException {
        modelo = new DefaultTableModel();

        modelo.addColumn("paciente");
        modelo.addColumn("fecha");
        modelo.addColumn("diagnostico");
        modelo.addColumn("Cama");
        modelo.addColumn("Id");

        tabla.setModel(modelo);

        String consulta = "SELECT paciente, fecha, diagnostico, id_cama, id FROM public.internacion";

        pst = conexion.prepareStatement(consulta);
        rs = pst.executeQuery();
        rsm = rs.getMetaData();
        while (rs.next()) {
            Object[] fila = new Object[rsm.getColumnCount()];
            fila[0] = rs.getString(1);
            fila[1] = rs.getDate(2);
            fila[2] = rs.getString(3);
            fila[3] = rs.getString(4);
            fila[4] = rs.getInt(5);

            modelo.addRow(fila);

        }
        rs.close();

    }

    public void cambiaEstado_cama(int numero) throws SQLException {

        String consulta = "UPDATE public.cama SET estado= 'ocupada' WHERE estado= 'disponible'";
        pst = conexion.prepareStatement(consulta);

        pst.setInt(1, numero);

        pst.execute();

    }

    public void agregar_internacion(Internacion internacion, JTable tabla) throws SQLException, ClassNotFoundException {
        camaControlador c = new camaControlador();
        String consulta = "SELECT paciente, fecha, diagnostico, id FROM public.internacion where id_cama= ?";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, internacion.getCama().getNumero());
        rs = pst.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Cama ocupada");
        } else {

            consulta = "INSERT INTO public.internacion(paciente, fecha, diagnostico, id, id_cama)VALUES (?, ?,?,?,?)";
            pst = conexion.prepareStatement(consulta);
            pst.setString(1, internacion.getPaciente());
            pst.setDate(2, internacion.getFecha());
            pst.setString(3, internacion.getDiagnostico());
            pst.setInt(4, internacion.getId());
            pst.setInt(5, internacion.getCama().getNumero());
            pst.execute();

            c.modificar_cama(internacion.getCama().getNumero(), "ocupada", tabla);

        }
        llenarTablinternacion(tabla);
    }

    public void cambio_estado(Cama cama, JTable tabla) {

    }

    public ArrayList<Cama> Extraer_cama() throws SQLException {
        ArrayList<Cama> camas = new ArrayList<>();
        String consulta = "SELECT numero, estado, id_habitacion FROM public.cama where estado = 'disponible'";

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

    public void eliminar_internacion(int numero,Internacion internacion, JTable tabla) throws SQLException, ClassNotFoundException {
       
        camaControlador c = new camaControlador(); 
        
        String consulta = "UPDATE public.cama SET estado= 'disponible' WHERE numero=?";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, numero);
        pst.execute();
               

        
       
         consulta = "DELETE FROM public.internacion WHERE id=? ";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, internacion.getId());
       

        JOptionPane.showMessageDialog(null, "Eliminado");

        pst.execute();
         
        
        llenarTablinternacion(tabla);

    }

    public void Modificar(Internacion internacion, JTable tabla) throws SQLException, ClassNotFoundException {
        camaControlador cc = new camaControlador();

        String consulta = "UPDATE public.internacion SET paciente=?, diagnostico=?, id_cama=? WHERE id= ?";
        pst = conexion.prepareStatement(consulta);
        pst.setString(1, internacion.getPaciente());
        pst.setString(2, internacion.getDiagnostico());
        pst.setInt(3, internacion.getCama().getNumero());
        pst.setInt(4, internacion.getId());
cc.modificar_cama(internacion.getCama().getNumero(), "ocupada", tabla);
        pst.execute();

        llenarTablinternacion(tabla);
    }

    public Cama extraer_cama(int id) throws SQLException {
        String consulta = "Select * from public.cama where numero = ?";
        pst = conexion.prepareStatement(consulta);
        pst.setInt(1, id);
        ResultSet rs = null;
        rs = pst.executeQuery();
        Cama cama = new Cama();
        while (rs.next()) {

            cama.setNumero(rs.getInt(1));
            cama.setEstado(rs.getString(2));
        }
        rs.close();

        return cama;

    }
}
