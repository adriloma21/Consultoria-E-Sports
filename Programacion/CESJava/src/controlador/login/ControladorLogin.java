package controlador.login;

import controlador.ControladorVista;
import modelo.Competicion;
import modelo.Jornada;
import modelo.Usuario;
import view.VentanaLogin;
import view.VentanaPrincipalAdmin;
import view.VentanaPrincipalUsuario;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ControladorLogin {
    private VentanaPrincipalUsuario vpu;
    private VentanaPrincipalAdmin vpa;
    private VentanaLogin vl;
    private ControladorVista cv;

    public ControladorLogin(ControladorVista cv) {
        this.cv=cv;

        vl=new VentanaLogin();

        vl.clickRatonUsuAL(new clickRatonUsu());
        vl.clickRatonPassAL(new clickRatonPass());
        vl.bAyudaAL(new bAyuda());
        vl.bEntrarAL(new bEntrar());
        vl.bSalirAL(new bSalir());



        vl.setVisible(true);
    }

//    public void mostrar() {
//        vl=new VentanaLogin();
//        vl.clickRatonUsuAL(new clickRatonUsu());
//        vl.clickRatonPassAL(new clickRatonPass());
//        vl.bAyudaAL(new bAyuda());
//        vl.bEntrarAL(new bEntrar());
//        vl.bSalirAL(new bSalir());
//        vl.setVisible(true);
//    }

    //Clases creadas para los tf y los botones
    /*Estas clases son para el login*/
    public class bAyuda implements ActionListener {
        public void actionPerformed (ActionEvent e){
            JOptionPane.showMessageDialog(null,"Si necesitas ayuda contacte con nuestros administradores. \n " +
                    "Jordi.fernandez@ikasle.egibide.org \n Adrian.lopez@ikasle.egibide.org \n Jon.garay@ikasle.egibide.org","Ayuda",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public class bSalir implements ActionListener {
        public void actionPerformed (ActionEvent e){
            System.exit(0);
        }
    }
    public class bEntrar implements ActionListener {
        public void actionPerformed (ActionEvent e){
            try {
                Usuario usu = new Usuario();

                usu.setTipo(vl.getTfUsu().getText());
                usu.setContrasena(vl.getTfPassword().getText());
                String nombreAU = cv.login(usu);

                if (nombreAU.equals("Administrador")) {
                    vpa = new VentanaPrincipalAdmin();
                    vpa.setVisible(true);
                } else if (nombreAU.equals("Usuario")) {
                    vpu = new VentanaPrincipalUsuario();
                    vpu.setVisible(true);
                    botones();
                    vpu.getpClasificacion().setVisible(false);
                    vpu.getpJornada().setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        public void botones (){
            vpu.rbClasiAL(new rbUsuClasi());
            vpu.rbJornadaAL(new rbUsuJornada());
            vpu.botonAceprtarJornadaAL(new bAceptarJornada());
            vpu.botonAceptarClasiAL(new bAceptarClase());
            vpu.bSalirAL(new bSalirUsu());
            vpu.bSesion(new bSesionUsu());
        }
    }
    /*Estas clases son para la ventana del usuario*/
    public class rbUsuJornada implements ActionListener{
        public void actionPerformed (ActionEvent e) {

            if (vpu.getpJornada().isEnabled()) {
                vpu.getpJornada().setVisible(true);
                vpu.getpClasificacion().setVisible(false);

                
            }

        }
    }

    public class rbUsuClasi implements ActionListener{
        public void actionPerformed (ActionEvent e){
            vpu.getpClasificacion().setVisible(false);
            vpu.getpJornada().setVisible(false);

            if (vpu.getpClasificacion().isEnabled()) {
                vpu.getpJornada().setVisible(false);
                vpu.getpClasificacion().setVisible(true);
            }
        }
    }
    public class clickRatonUsu implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            vl.getTfUsu().setText("");
            if (vl.getTfPassword().getText().isEmpty()){
                vl.getTfPassword().setText("Contraseña");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    public class clickRatonPass implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            vl.getTfPassword().setText("");
            if (vl.getTfUsu().getText().isEmpty()){
                vl.getTfUsu().setText("Usuario");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    public class bAceptarJornada implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Competicion com = new Competicion();
            com.setNombre(vpu.getTfJornada().getText());
            Jornada jor = new Jornada();
            jor.setCodCompe(com);
            StringBuilder total = new StringBuilder();

        }
    }
    public class bAceptarClase implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }
    public class bSalirUsu implements ActionListener{
        public void actionPerformed (ActionEvent e){
            System.exit(0);
        }
    }
    public class bSesionUsu implements ActionListener{
        public void actionPerformed (ActionEvent e){
            vpu.dispose();
            vpu.getTfJornada().setText("");
            vpu.getTfClasi().setText("");
        }
    }
}
