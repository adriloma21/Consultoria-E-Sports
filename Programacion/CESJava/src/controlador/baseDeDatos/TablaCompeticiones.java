package controlador.baseDeDatos;

import modelo.*;
import oracle.jdbc.proxy.annotation.Pre;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Clase TablaCompeticiones para manejar las operaciones relacionadas con las competiciones.
 */

public class TablaCompeticiones {
    Connection con;
    ControladorBaseDeDatos cbd;

    /**
     * Constructor que inicializa la conexión a la base de datos y el controlador de base de datos.
     *
     * @param con la conexión a la base de datos.
     * @param cbd el controlador de base de datos.
     */

    public TablaCompeticiones(Connection con, ControladorBaseDeDatos cbd) {
        this.con = con;
        this.cbd = cbd;
    }

    /**
     * Método para dar de alta una nueva competición.
     *
     * @param compe el objeto Competicion que se va a dar de alta.
     */

    public void altaCompeticion(Competicion compe) {
        try {
            String plantilla = "INSERT INTO competiciones (nombre,fecha_inicio,fecha_fin,curso,cod_juego) VALUES (?,?,?,?,?)";
            PreparedStatement sentencia = con.prepareStatement(plantilla);
            sentencia.setString(1, compe.getNombre());
            String fechaVentana1 = String.valueOf(compe.getFechaInicio());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaJava1 = formato.parse(fechaVentana1);
            java.sql.Timestamp fechaSql1 = new java.sql.Timestamp(fechaJava1.getTime());
            sentencia.setTimestamp(2, fechaSql1);
            String fechaVentana2 = String.valueOf(compe.getFechaFin());
            java.util.Date fechaJava2 = formato.parse(fechaVentana2);
            java.sql.Timestamp fechaSql2 = new java.sql.Timestamp(fechaJava2.getTime());
            sentencia.setTimestamp(3, fechaSql2);
            sentencia.setInt(4, 0);
            sentencia.setObject(5, compe.getCodJuego().getCodJuego());
            int n = sentencia.executeUpdate();
            if (n != 1) {
                mostrar("No se ha insertado ninguna competición");
            } else {
                mostrar("Competición insertada");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para dar de baja una competición.
     *
     * @param compe el objeto Competicion que se va a dar de baja.
     */

    public void bajaCompeticion(Competicion compe) {
        try {
            String plantilla = "DELETE FROM competiciones WHERE nombre = ?";
            PreparedStatement sentencia = con.prepareStatement(plantilla);
            sentencia.setString(1, compe.getNombre());
            int n = sentencia.executeUpdate();
            sentencia.close();
            if (n == 1) {
                mostrar("Competición borrada");
            } else {
                mostrar("Competición no encontrada");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para modificar una competición.
     *
     * @param compe el objeto Competicion que se va a modificar.
     */

    public void modiCompeticion(Competicion compe) {
        try {
            String plantilla = "UPDATE competiciones SET fecha_inicio = ?, fecha_fin = ?, cod_juego = ? WHERE nombre = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            String fechaVentana1 = String.valueOf(compe.getFechaInicio());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaJava1 = formato.parse(fechaVentana1);
            java.sql.Timestamp fechaSql1 = new java.sql.Timestamp(fechaJava1.getTime());
            sentenciaPre.setTimestamp(1, fechaSql1);
            String fechaVentana2 = String.valueOf(compe.getFechaFin());
            java.util.Date fechaJava2 = formato.parse(fechaVentana2);
            java.sql.Timestamp fechaSql2 = new java.sql.Timestamp(fechaJava2.getTime());
            sentenciaPre.setTimestamp(2, fechaSql2);
            sentenciaPre.setObject(3, compe.getCodJuego().getCodJuego());
            sentenciaPre.setString(4, compe.getNombre());
            int n = sentenciaPre.executeUpdate();
            sentenciaPre.close();
            if (n == 1) {
                mostrar("Competición actualizada");
            } else {
                mostrar("Competición no encontrada");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para consultar una competición por su nombre.
     *
     * @param nombreCompe el nombre de la competición.
     * @return un StringBuilder con la información de la competición.
     */

    public StringBuilder consultaCompeticion(String nombreCompe) {
        try {
            String plantilla = "SELECT fecha_inicio,fecha_fin,cod_juego FROM competiciones WHERE nombre = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setString(1, nombreCompe);
            ResultSet respuesta = sentenciaPre.executeQuery();
            StringBuilder pantalla = new StringBuilder();
            if (respuesta.next()) {
                java.sql.Timestamp fechaInicio = respuesta.getTimestamp("fecha_inicio");
                java.sql.Timestamp fechaFin = respuesta.getTimestamp("fecha_fin");
                int codJuego = respuesta.getInt("cod_juego");
                Juego ju = cbd.getNombreJuegoPorCodigo(codJuego);
                pantalla.append("FECHA INICIO: ").append(fechaInicio).append("\n").append("FECHA FIN: ").append(fechaFin).append("\n").append("JUEGO: ").append(ju.getNombre().toLowerCase());
            }
            return pantalla;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para mostrar un mensaje en una ventana de diálogo.
     *
     * @param m el mensaje a mostrar.
     */

    public void mostrar(String m) {
        JOptionPane.showMessageDialog(null, m);
    }

    /**
     * Método para obtener el código de una competición por su nombre.
     *
     * @param nombreCompeticion el nombre de la competición.
     * @return el código de la competición o -1 si no se encuentra.
     */

    public int getCodigoCompeticionPorNombre(String nombreCompeticion) {
        try {
            String query = "SELECT cod_compe FROM competiciones WHERE nombre = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombreCompeticion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod_compe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * Método para obtener una lista de competiciones.
     *
     * @return una lista de objetos Competicion.
     * @throws Exception si ocurre un error en la consulta.
     */

    public ArrayList clasiEquipo() throws Exception {
        ArrayList<Competicion> lista = new ArrayList<>();
        String plantilla = "select nombre from competiciones";
        PreparedStatement pre = con.prepareStatement(plantilla);
        ResultSet res = pre.executeQuery();
        while (res.next()) {
            Clasificacion cla = new Clasificacion();
            Competicion c = new Competicion();

            c.setNombre(res.getString(1));
            cla.setCodcompe(c);
            lista.add(c);
        }

        return lista;
    }

    /**
     * Método para obtener la clasificación de una competición.
     *
     * @param com el objeto Competicion.
     * @return una lista de objetos Clasificacion.
     */

    public ArrayList clasificacionAdmin(Competicion com) {
        Clasificacion cla = new Clasificacion();
        ArrayList<Clasificacion> lista = new ArrayList<>();
        try {
            String nombreCompe = com.getNombre();//solo hay nombre en com
            String plantilla = "select ec.cod_equipo, ec.puntos, ec.cod_competicion from equipo_competicion ec join competiciones c on ec.cod_competicion=c.cod_compe where c.nombre=? order by puntos desc";
            PreparedStatement pre = con.prepareStatement(plantilla);
            pre.setString(1, (nombreCompe));
            ResultSet res = pre.executeQuery();
            while (res.next()) {

                EquipoCompeticion ec = new EquipoCompeticion();
                Equipo equi = new Equipo();
                Competicion compe = new Competicion();

                equi.setCodEquipo(res.getInt(1));
                ec.setCodEquipo(equi);
                compe.setCodCompe(res.getInt(3));
                ec.setCodCompe(compe);

                cla = datosCompletosClasi(ec, com);

                lista.add(cla);

            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para obtener los datos completos de la clasificación de un equipo en una competición.
     *
     * @param ec el objeto EquipoCompeticion.
     * @param com el objeto Competicion.
     * @return el objeto Clasificacion con los datos completos.
     */

    public Clasificacion datosCompletosClasi(EquipoCompeticion ec, Competicion com) {
        Clasificacion cla = new Clasificacion();
        Equipo eq = new Equipo();

        try {
            String nombreCompe = com.getNombre();
            int codigoEquipo = ec.getCodEquipo().getCodEquipo();
            int codigoCompe = ec.getCodCompe().getCodCompe();

            String plantilla = "select e.nombre from equipo_competicion ec join competiciones c on ec.cod_competicion=c.cod_compe join equipos e on ec.cod_equipo=e.cod_equipo where c.nombre= ? and e.cod_equipo=?";
            PreparedStatement pre = con.prepareStatement(plantilla);
            pre.setString(1, String.valueOf(nombreCompe));
            pre.setString(2, String.valueOf(codigoEquipo));
            ResultSet res = pre.executeQuery();

            while (res.next()) {
                eq.setNombre(res.getString("nombre"));
                ec.setCodEquipo(eq);
            }

            String plantilla1 = "select puntos from equipo_competicion where cod_competicion=? and cod_equipo=?";
            PreparedStatement pre1 = con.prepareStatement(plantilla1);
            pre1.setString(1, String.valueOf(codigoCompe));
            pre1.setString(2, String.valueOf(codigoEquipo));
            ResultSet res1 = pre1.executeQuery();
            while (res1.next()) {
                ec.setPuntos(res1.getInt(1));
            }

            cla.setCodequipo(eq);
            cla.setPuntos(ec);

            return cla;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cierra la inscripción para una competición específica.
     *
     * @param nombreCompeticion El nombre de la competición para la cual se quiere cerrar la inscripción.
     * @throws RuntimeException Si ocurre un error durante la operación, ya sea una excepción SQL o una excepción general.
     */

    public void cerrarInscripcion(String nombreCompeticion) {
        try {
            int codCompeticion = getCodigoCompeticionPorNombre(nombreCompeticion);

            if (codCompeticion == -1) {
                System.out.println("Competición no encontrada");
                throw new Exception("Competición no encontrada");
            }

            boolean inscripcionCerrada = etapaInscripcionCerrada(nombreCompeticion);
            if (inscripcionCerrada) {
                System.out.println("La inscripción para la competición ya está cerrada.");
                mostrar("La inscripción para la competición ya está cerrada.");
                return; // Salir del método si la inscripción ya está cerrada
            }

            String plantilla = "UPDATE competiciones SET curso = 1 WHERE cod_compe = ?";
            PreparedStatement sentenciaPre = con.prepareStatement(plantilla);
            sentenciaPre.setInt(1, codCompeticion);

            int n = sentenciaPre.executeUpdate();
            if (n != 1) {
                mostrar("No se ha podido cerrar la inscripción de la competición: " + nombreCompeticion);
                throw new Exception("No se ha podido cerrar la inscripción");
            } else {
                System.out.println("Inscripción cerrada para la competición: " + nombreCompeticion);
                mostrar("Inscripción cerrada para la competición: " + nombreCompeticion);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace(); // Logs Depuración
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace(); // Logs Depuración
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica si la etapa de inscripción está cerrada para una competición específica.
     *
     * @param nombreCompeticion El nombre de la competición para la cual se quiere verificar el estado de la inscripción.
     * @return true si la inscripción está cerrada, false en caso contrario.
     */

    private boolean etapaInscripcionCerrada(String nombreCompeticion) {
        try {
            String query = "SELECT curso FROM competiciones WHERE nombre = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombreCompeticion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int curso = rs.getInt("curso");
                return curso == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String sacarNumCompe (String nombreCompe)throws Exception{
        String plantilla ="select cod_compe from competiciones where nombre = ?";
        PreparedStatement pre = con.prepareStatement(plantilla);
        pre.setString(1,nombreCompe);
        ResultSet res = pre.executeQuery();
        res.next();
        String cod_compe = res.getString(1);
        return cod_compe;
    }
}