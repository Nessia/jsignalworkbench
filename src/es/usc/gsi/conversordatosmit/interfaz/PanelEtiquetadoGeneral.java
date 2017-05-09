package es.usc.gsi.conversordatosmit.interfaz;

import javax.swing.JTabbedPane;

public abstract class PanelEtiquetadoGeneral extends JTabbedPane {

    /**
    *
    */
    private static final long serialVersionUID = -349063290556537350L;

   // Actualizara las frecuencias a las presentes en el interfaz grafico.
    public abstract void actualizaFrecuencias();

    // Actualizara las fechas a las presentes en el interfaz grafico.
    public abstract void actualizaFechas(String fechaInicio, String fechaFin);

    // Cerrar todos los ficheros
    public abstract void cerrarTodosFicheros();

} // Fin clase PanelEtiquetadoGeneral
