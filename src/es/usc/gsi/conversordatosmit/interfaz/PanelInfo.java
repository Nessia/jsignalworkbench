package es.usc.gsi.conversordatosmit.interfaz;

import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;

public class PanelInfo extends JPanel {


    private static final Logger LOGGER = Logger.getLogger(PanelInfo.class.getName());
    /**
    *
    */
    private static final long serialVersionUID = 3310265314087578520L;

//    public static final int DIRECTORIO = 0;
//    public static final int ARCHIVO = 1;
    public enum Modos { DIRECTORIO, ARCHIVO }
    private TitledBorder bordePanel;
    private JLabel labelDatos = new JLabel();

    public PanelInfo(FicheroHead fh, Modos modo) {
        // ANHADIR TODA LA INFORMACION PERTINENTE AL FICHERO
        // Y TAMBIEN A LOS PARAMETROS GENERALES LEIDOS DEL FICHERO.
        File localizacion = null;
        String textoLabel = "";
        String fechaInicioMuestreo = "";
        String fechaFinMuestreo = "";

        String numMuestras = "";
        String frecuenciaMuestreoFrame = "";

        switch (modo) {
        case ARCHIVO:
            localizacion = fh;
            fechaInicioMuestreo = fh.getFechaInicio();
            fechaFinMuestreo = fh.getFechaFin();

            numMuestras = Long.toString(fh.getNumMuestras());
            frecuenciaMuestreoFrame = Float.toString(fh.
                    getFrecuenciaMuestreoFrame());
            textoLabel = "<html><font type='Arial' size=2>" +
                         "<b>Localization:</b> " + localizacion.getPath() +
                         "<br>" +
                         "<b>Begin:</b> " +
                         "18:56:58 11/05/2004"
                         /*fechaInicioMuestreo*/ + "<br>" +
                         "<b>End:</b>" +
                         "18:59:21 11/05/2004"
                         /*fechaFinMuestreo*/ + "<br>" +
                         "<b>Number of samples:</b> " + numMuestras + "<br>" +
                         "<b>Base sampling rate:</b> " +
                         frecuenciaMuestreoFrame + " Hz" +
                         "</font></html>";
            break;
        case DIRECTORIO:
            localizacion = fh.getParentFile();
            fechaInicioMuestreo = fh.getFechaInicio();
            fechaFinMuestreo = fh.getFechaFin();

            numMuestras = Long.toString(fh.getNumMuestras());
            frecuenciaMuestreoFrame = Float.toString(fh.
                    getFrecuenciaMuestreoFrame());
            textoLabel = "<html><font type='Arial' size=2>" +
                         "<b>Localizacion:</b> " + localizacion.getPath() +
                         "<br>" +
                         "<b>Inicio del muestreo:</b> " + fechaInicioMuestreo +
                         "<br>" +
                         "<b>Fin del muestreo:</b>" + fechaFinMuestreo + "<br>" +
                         //  "<b>Numero de muestras:</b> " + numMuestras + "<br>" +
                         //  "<b>Frecuencia base de muestreo:</b> " + frecuenciaMuestreoFrame + " Hz" +
                         "</font></html>";
            break;
//        default:
//            localizacion = null;
        }

        try {
            labelDatos.setText(textoLabel);
        } catch (Exception e) {
           LOGGER.log(Level.INFO, e.getMessage(), e);
        }
        this.add(labelDatos);

        bordePanel = new TitledBorder(BorderFactory.createLineBorder(new Color(
                153, 153, 153), 2), localizacion.getName());
        this.setBorder(bordePanel);
    }

} // Fin clase PanelInfo
