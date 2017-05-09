package es.usc.gsi.conversordatosmit.interfaz;

import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;


public class PanelEtiquetadoSimple extends PanelEtiquetadoGeneral {

    /**
    *
    */
    private static final long serialVersionUID = 3773213104297711059L;
    private ScrollPanelParametros scrollPanel;


    public PanelEtiquetadoSimple(FicheroHead[] ficherosHead) {
        this.creaScrollPanel(ficherosHead);
    }

    private void creaScrollPanel(FicheroHead[] ficherosHead) {
        scrollPanel = new ScrollPanelParametros(ficherosHead);
        this.add(ficherosHead[0].getParentFile().getName(), scrollPanel);
    }

    @Override
    public void actualizaFrecuencias() {
        scrollPanel.actualizaFrecuencias();
    }

/////////////////

    @Override
    public void actualizaFechas(String fechaInicio, String fechaFin) {
        scrollPanel.actualizaFechas(fechaInicio, fechaFin);
    }

////////////////

    @Override
    public void cerrarTodosFicheros() {
        this.removeAll();
        this.validate();
    }


} // Fin clase PanelEtiquetadoSimple