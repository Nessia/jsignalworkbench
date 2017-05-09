package es.usc.gsi.conversorDatosMIT.interfaz;

import javax.swing.JMenuBar;

public class BarraMenuPrincipal extends JMenuBar {

    /**
    *
    */
    private static final long serialVersionUID = -5838659721964817356L;



    private MenuArchivo archivo = new MenuArchivo("Archivo");
    private MenuVer ver = new MenuVer("Ver");

    public BarraMenuPrincipal() {
        this.add(archivo);
        this.add(ver);
    }
} // Fin clase BarraMenuPrincipal
