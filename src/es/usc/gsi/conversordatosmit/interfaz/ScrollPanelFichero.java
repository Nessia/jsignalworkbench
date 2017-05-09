package es.usc.gsi.conversordatosmit.interfaz;

import javax.swing.JScrollPane;

public class ScrollPanelFichero extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 5189370840558775111L;

    private PanelFichero panelFichero;

//*********************************************************************************

     public ScrollPanelFichero(PanelFichero panelFichero) {
         super();
         this.panelFichero = panelFichero;
         this.setViewportView(panelFichero);

     }

//*********************************************************************************

     public PanelFichero getPanelFichero() {
         return panelFichero;
     }


//*********************************************************************************

     public void actualizaFrecuencia() {
         panelFichero.actualizaFrecuencia();
     }

//*********************************************************************************

     public void actualizaFechas(String fechaInicio, String fechaFin) {
         panelFichero.actualizaFechas(fechaInicio, fechaFin);
     }

} // Fin clase ScrollPanelFichero