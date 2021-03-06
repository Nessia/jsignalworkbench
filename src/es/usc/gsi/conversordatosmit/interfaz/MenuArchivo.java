package es.usc.gsi.conversordatosmit.interfaz;

import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuArchivo extends JMenu implements ActionListener {


    /**
    *
    */
    private static final long serialVersionUID = 3596737894964668987L;

    private static final String CMD_ABRIRPACIENTE = "Abrir paciente";
    private static final String CMD_ABRIRFICHERO = "Abrir fichero";
    private static final String CMD_CERRAR = "Cerrar";
    private static final String CMD_EXPORTAR = "Exportar";
//  private static final String CMD_GUARDAR = "Guardar";
//  private static final String CMD_IMPORTAR="Importar";

    private ControladorInterfaz controlInterfaz = ControladorInterfaz.getControlador();

    private JMenuItem abrirPaciente = new JMenuItem("Abrir paciente");
    private JMenuItem abrirFichero = new JMenuItem("Abrir fichero");
    private JMenuItem cerrar = new JMenuItem("Cerrar");
    private JMenuItem exportar = new JMenuItem("Exportar");
//  private JMenuItem importar=new JMenuItem("Importar");

    public MenuArchivo(String nombre) {
        super(nombre);

        this.setMnemonic(KeyEvent.VK_A);

        abrirPaciente.setActionCommand(CMD_ABRIRPACIENTE);
        abrirPaciente.addActionListener(this);
        this.add(abrirPaciente);

        abrirFichero.setActionCommand(CMD_ABRIRFICHERO);
        abrirFichero.addActionListener(this);
        this.add(abrirFichero);

        cerrar.setActionCommand(CMD_CERRAR);
        cerrar.addActionListener(this);
        this.add(cerrar);

        exportar.setActionCommand(CMD_EXPORTAR);
        exportar.addActionListener(this);
        this.add(exportar);

//    importar.setActionCommand(CMD_IMPORTAR);
//    importar.addActionListener(this);
//    this.add(importar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(CMD_ABRIRPACIENTE)) {
            controlInterfaz.abrirPaciente(null);
        }
        if (e.getActionCommand().equals(CMD_ABRIRFICHERO)) {
            controlInterfaz.abrirFichero(null);
        }
        if (e.getActionCommand().equals(CMD_CERRAR)) {
            controlInterfaz.cerrarPaciente();
        }
        if (e.getActionCommand().equals(CMD_EXPORTAR)) {
            controlInterfaz.exportaFicheros();
        }
//  if ( e.getActionCommand().equals(CMD_IMPORTAR) ) controlInterfaz.getParametrosSeleccionados();

    }
} // Fin clase MenuArchivo
