package es.usc.gsi.conversordatosmit.interfaz;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;

class PanelParametros extends JPanel {


    /**
    *
    */
    private static final long serialVersionUID = -2864241673451213419L;

    private PanelFichero[] panelesFichero;
    private BoxLayout layoutPanel;

    PanelParametros(FicheroHead[] ficherosHead) {
        this.layoutPanel = new BoxLayout(this, BoxLayout.Y_AXIS); // Una sola columna y tantas filas como ficherosHead
        this.setLayout(layoutPanel);

        PanelInfo informacion = new PanelInfo(ficherosHead[0], PanelInfo.Modos.DIRECTORIO);
        this.add(informacion);
        this.creaPanelesFichero(ficherosHead);
    }

    private void creaPanelesFichero(FicheroHead[] ficherosHead) {
        panelesFichero = new PanelFichero[ficherosHead.length];

        for (int i = 0; i < ficherosHead.length; i++) {
            panelesFichero[i] = new PanelFichero(ficherosHead[i], PanelFichero.Modo.LISTA);
            this.add(panelesFichero[i]);
        }
    }

    void actualizaFrecuencias() {
        for (int i = 0; i < panelesFichero.length; i++) {
            panelesFichero[i].actualizaFrecuencia();
        }
    }

    void actualizaFechas(String fechaInicio, String fechaFin) {
        for (int i = 0; i < panelesFichero.length; i++) {
            panelesFichero[i].actualizaFechas(fechaInicio, fechaFin);
        }
    }


} // Fin clase PanelParametros