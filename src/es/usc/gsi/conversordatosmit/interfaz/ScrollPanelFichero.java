package es.usc.gsi.conversordatosmit.interfaz;

import javax.swing.JScrollPane;

class ScrollPanelFichero extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 5189370840558775111L;

    private PanelFichero panelFichero;

//*********************************************************************************

     ScrollPanelFichero(PanelFichero panelFichero) {
         super();
         this.panelFichero = panelFichero;
         this.setViewportView(panelFichero);

     }

//*********************************************************************************

     public PanelFichero getPanelFichero() {
         return panelFichero;
     }


//*********************************************************************************

     void actualizaFrecuencia() {
         panelFichero.actualizaFrecuencia();
     }

//*********************************************************************************

     void actualizaFechas(String fechaInicio, String fechaFin) {
         panelFichero.actualizaFechas(fechaInicio, fechaFin);
     }

} // Fin clase ScrollPanelFichero