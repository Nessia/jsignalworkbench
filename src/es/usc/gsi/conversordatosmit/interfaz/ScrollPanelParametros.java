package es.usc.gsi.conversordatosmit.interfaz;

import javax.swing.JScrollPane;

import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;

public class ScrollPanelParametros extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 4663156974036621689L;

    private PanelParametros panelParametros;

//*********************************************************************************

     public ScrollPanelParametros(FicheroHead[] ficherosHead) {
         super();
         this.panelParametros = new PanelParametros(ficherosHead);
         this.setViewportView(panelParametros);
     }

//*********************************************************************************

     public PanelParametros getPanelParametros() {
         return panelParametros;
     }

//*********************************************************************************

     public void actualizaFrecuencias() {
         panelParametros.actualizaFrecuencias();
     }

//*********************************************************************************

     public void actualizaFechas(String fechaInicio, String fechaFin) {
         panelParametros.actualizaFechas(fechaInicio, fechaFin);
     }

} // Fin clase ScrollPanelParametros