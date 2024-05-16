package controlador.baseDeDatos;

import modelo.Equipo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TablaEquipos {
    private Connection con;

    public TablaEquipos(Connection con) {
        this.con = con;
    }

    public void altaEquipo (Equipo eq){
        try {
            String plantilla = "INSERT INTO equipos (nombre,fecha_fundacion) VALUES (?,?)";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setString(1, eq.getNombre());
            String fechaVentana = String.valueOf(eq.getFechaFundacion());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaJava = formato.parse(fechaVentana);
            java.sql.Timestamp fechaSql = new java.sql.Timestamp(fechaJava.getTime());
            sentenciaPre.setTimestamp(2, fechaSql);
            int n =sentenciaPre.executeUpdate();
            if (n != 1){
                throw new Exception("No se ha insertado ningún equipo");
            }else{
                System.out.println("Equipo insertado");
                mostrar("Equipo insertado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void bajaEquipo(Equipo eq){
        try {
            String plantilla = "DELETE FROM equipos WHERE nombre = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setString(1, eq.getNombre());
            int n = sentenciaPre.executeUpdate();
            sentenciaPre.close();
            if (n == 1){
                throw new Exception("Equipo borrado");
            }else{
                throw new Exception("Equipo no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void modiEquipo(Equipo eq){
        try {
            String plantilla = "UPDATE equipos SET fecha_fundacion = ? WHERE nombre = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            String fechaVentana = String.valueOf(eq.getFechaFundacion());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaJava = formato.parse(fechaVentana);
            System.out.println(fechaJava);
            java.sql.Date fechaSql = new java.sql.Date(fechaJava.getTime());
            System.out.println(fechaSql);
            sentenciaPre.setDate(1, fechaSql);
            sentenciaPre.setString(2, eq.getNombre());
            int n = sentenciaPre.executeUpdate();
            sentenciaPre.close();
            if (n == 1){
                throw new Exception("Equipo actualizado");
            }else{
                throw new Exception("Equipo no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Equipo consultaEquipo(String nombreEq){
        Equipo eq = null;
        try {
            String plantilla = "SELECT cod_equipo,fecha_fundacion FROM equipos WHERE nombre = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setString(1, nombreEq);
            ResultSet respuesta = sentenciaPre.executeQuery();
            if (respuesta.next()){
                Integer codEquipo = respuesta.getInt("cod_equipo");
                java.sql.Date fecha = respuesta.getDate("fecha_fundacion");
                eq = new Equipo();
                eq.setNombre(nombreEq);
                eq.setFechaFundacion(fecha.toLocalDate());
                eq.setCodEquipo(codEquipo);
            }
            return eq;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Equipo buscarEquipo(String nombreEq){
        Equipo eq = null;
        try {
            String plantilla = "SELECT cod_equipo FROM equipos WHERE nombre = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setString(1, nombreEq);
            ResultSet respuesta = sentenciaPre.executeQuery();
            if (respuesta.next()){
                Integer codEquipo = respuesta.getInt("cod_equipo");
                eq = new Equipo();
                eq.setNombre(nombreEq);
                eq.setCodEquipo(codEquipo);
            }
            return eq;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrar(String m) {
        JOptionPane.showMessageDialog(null, m);
    }
}
