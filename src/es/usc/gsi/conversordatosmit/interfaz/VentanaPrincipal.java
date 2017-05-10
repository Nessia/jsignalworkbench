// Ventana principal de la aplicacion: es la que hace falta para hacer una
// aplicacion standalone.
package es.usc.gsi.conversordatosmit.interfaz;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 5357930700642598451L;
    private static final Logger LOGGER = Logger.getLogger(VentanaPrincipal.class.getName());
    private static final int ANCHOVENTANA = 670;
    private static final int ALTOVENTANA = 550;

    private ControladorInterfaz controlInterfaz = ControladorInterfaz.getControlador();
    private BarraMenuPrincipal barraMenu = new BarraMenuPrincipal();
    //private PanelPrincipal panelPrincipal; // Contenedor de todos los demas paneles.


    public VentanaPrincipal() {
        super("Conversor MITDB");
        try {
            jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void jbInit() {
        this.addWindowListener(new EventoVentana());
        this.setSize(ANCHOVENTANA, ALTOVENTANA);
//    this.setResizable(false);

        // Hay que inicializarlo despues de haberle dado un tamanho a la ventana
        // o al contenedor, ya que el panelPrincipal ocupa t.odo
        // el espacio de su contenedor.
        PanelPrincipal panelPrincipal = controlInterfaz.getPanelPrincipal();

        this.getContentPane().add(panelPrincipal);
        // Prueba de la clase Paciente

        /* Paciente paci=new Paciente(null);
         panelPrincipal.add (paci, new XYConstraints(30, 30, 500, 500) ); */

        this.setJMenuBar(barraMenu);
    }

    // InnerClass: Eventos de Ventana
    // LLAMAR A METODOS DEL CONTROLADOR PARA COMPROBAR QUE T.ODO ESTA CORRECTO
    // ANTES DE CERRAR LA APLICACION
    class EventoVentana extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

} // Fin clase VentanaPrincipal
