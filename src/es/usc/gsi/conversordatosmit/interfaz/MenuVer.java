package es.usc.gsi.conversordatosmit.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

class MenuVer extends JMenu implements ActionListener {

    /**
    *
    */
    private static final long serialVersionUID = 1828847339301712032L;
    private static final String CMD_LISTA = "Lista";
    private static final String CMD_ETIQUETAS = "Etiquetas";

    private ControladorInterfaz controlInterfaz = ControladorInterfaz.getControlador();

    private JMenuItem verLista = new JMenuItem("Ver como lista");
    private JMenuItem verEtiquetas = new JMenuItem("Ver como etiquetas");

    /*
     * Constructor
     */

    MenuVer(String nombre) {

        super(nombre);

        verLista.setActionCommand(CMD_LISTA);
        verLista.addActionListener(this);
        this.add(verLista);

        verEtiquetas.setActionCommand(CMD_ETIQUETAS);
        verEtiquetas.addActionListener(this);
        this.add(verEtiquetas);
    }

    // Control de eventos
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(CMD_LISTA)) {
            controlInterfaz.cambiaVista(ControladorInterfaz.LISTA);
        }
        if (e.getActionCommand().equals(CMD_ETIQUETAS)) {
            controlInterfaz.cambiaVista(ControladorInterfaz.ETIQUETAS);
        }

    }

} // Fin MenuVer
