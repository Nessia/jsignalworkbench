package es.usc.gsi.conversordatosmit.interfaz;

import javax.swing.*;

import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;
import es.usc.gsi.conversordatosmit.ficheros.Parametro;

class PanelFichero extends JPanel {

    /**
    *
    */
    private static final long serialVersionUID = -3152553418779933433L;

//    static final int LISTA = 0;
//    static final int ETIQUETAS = 1;
    enum Modo { LISTA, ETIQUETAS }

    private FicheroHead ficheroHead;
//    private PanelInfo informacion;
    private PanelGrupoParametro[] panelG; //Array de paneles

//**********************************************************************************

     PanelFichero(FicheroHead ficheroHead, Modo modoVista) {
         super();

         this.ficheroHead = ficheroHead;

         this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

         Parametro[] parametros = ficheroHead.getParametros();
         panelG = new PanelGrupoParametro[parametros.length];
         for (int i = 0; i < parametros.length; i++) {
             panelG[i] = new PanelGrupoParametro(parametros[i]);
         }

         switch (modoVista) {
         case LISTA:
             this.vistaLista();
             break;

         case ETIQUETAS:
             this.vistaEtiquetas();
             break;

         default:
             this.vistaLista();
         }
     }

//**********************************************************************************

     private void vistaLista() {
         this.setBorder(BorderFactory.createTitledBorder(ficheroHead.getName()));

         for (int i = 0; i < panelG.length; i++) {
             this.add(panelG[i]);
         }
     }

//**********************************************************************************

     private void vistaEtiquetas() {
         PanelInfo informacion = new PanelInfo(ficheroHead, PanelInfo.Modos.ARCHIVO);
         this.add(informacion);

         for (int i = 0; i < panelG.length; i++) {
             this.add(panelG[i]);
         }
     }

//**********************************************************************************

     public FicheroHead getFicheroHead() {
         return this.ficheroHead;
     }

//**********************************************************************************

     void actualizaFrecuencia() {
         for (int i = 0; i < panelG.length; i++) {
             panelG[i].actualizaFrecuencia();
         }
     }

//**********************************************************************************

     void actualizaFechas(String fechaInicio, String fechaFin) {
         for (int i = 0; i < panelG.length; i++) {
             panelG[i].setFechaInicio(fechaInicio);
             panelG[i].setFechaFin(fechaFin);
         }
     }


} // Fin PanelFichero