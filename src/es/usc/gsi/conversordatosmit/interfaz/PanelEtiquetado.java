package es.usc.gsi.conversordatosmit.interfaz;


import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;

class PanelEtiquetado extends PanelEtiquetadoGeneral {

    /**
    *
    */
    private static final long serialVersionUID = -4865324331412750141L;

    private ScrollPanelFichero[] scrollesPaneles;
    //  private JScrollPane[] scrollesPaneles;

    PanelEtiquetado(FicheroHead[] ficherosHead) {
        this.creaScrollesPaneles(ficherosHead);
    }

    // Crea un PanelFichero por cada fichero, y un
    // ScrollPanelFichero por cada PanelFichero
    private void creaScrollesPaneles(FicheroHead[] ficherosHead) {
        PanelFichero[] panelesFichero = new PanelFichero[ficherosHead.length];
        scrollesPaneles = new ScrollPanelFichero[ficherosHead.length];

        for (int i = 0; i < ficherosHead.length; i++) {
            panelesFichero[i] = new PanelFichero(ficherosHead[i], PanelFichero.Modo.ETIQUETAS);
            scrollesPaneles[i] = new ScrollPanelFichero(panelesFichero[i]);
            this.add(ficherosHead[i].getNombreFrame(), scrollesPaneles[i]);
        }
    }

    ///////////////////////////
    public FicheroHead getFicheroHeadSeleccionado() {
        ScrollPanelFichero spf =
                (ScrollPanelFichero)this.getSelectedComponent();
        PanelFichero pf = spf.getPanelFichero();

        return pf.getFicheroHead();
    }

/////////////////
    @Override
    public void actualizaFrecuencias() {
        for (int i = 0; i < scrollesPaneles.length; i++) {
            scrollesPaneles[i].actualizaFrecuencia();
        }
    }

/////////////////

    public void actualizaFechas(String fechaInicio, String fechaFin) {
        for (int i = 0; i < scrollesPaneles.length; i++) {
            scrollesPaneles[i].actualizaFechas(fechaInicio, fechaFin);
        }
    }

////////////////

    public void cerrarTodosFicheros() {
        this.removeAll();
        this.validate();
    }

} // Fin clase PanelEtiquetado