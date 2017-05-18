package net.javahispano.plugins.basicstats;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

class Estadistico implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7861627846557938077L;

    private static final Logger LOGGER = Logger.getLogger(Estadistico.class.getName());

     /*
      * Atributos
      */

    private float[] datos;
    private int[] percentiles;
    private ResultadosEstadisticos resultados;
    private String nombreSenal;
    private String fechaFin;
    private String fechaInicio;

    /*
     * Constructor
     */

    Estadistico(float[] datos, int[] percentiles_adicionales,
                       String nombreSenal,
                       String fechaInicio, String fechaFin) {
        this.datos = datos;
        this.nombreSenal = nombreSenal;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;

        if (percentiles_adicionales != null) {
            this.percentiles = new int[3 + percentiles_adicionales.length];
        } else {
            this.percentiles = new int[3];
        }
        //Rellenamos el array con los percentiles adicionales que se nos piden
        this.percentiles[0] = 25;
        this.percentiles[1] = 50;
        this.percentiles[2] = 75;
        if (percentiles_adicionales != null) {
            for (int i = 0; i < percentiles_adicionales.length; i++) {
                percentiles[i + 3] = percentiles_adicionales[i];
            }
        }
        percentiles = RutinasEstadisticas.ordenaPercentiles(percentiles);
        resultados = this.calculaLosEstadisticos();

    }


    /*
     * Metodos
     */

    private ResultadosEstadisticos calculaLosEstadisticos() {
        float media = RutinasEstadisticas.calculaMedia(datos);
        float[] mediana_y_percentiles = RutinasEstadisticas.calculaMediana(
                datos, percentiles);
        float mediana = mediana_y_percentiles[0];
        float[] percentiles = new float[mediana_y_percentiles.length - 1];
        for (int i = 0; i < percentiles.length; i++) {
            percentiles[i] = mediana_y_percentiles[i + 1];
        }
        float varianza = RutinasEstadisticas.calculaVarianza(datos, media);
        float desviacion_tipica = RutinasEstadisticas.calculaDesviacionTipica(
                varianza);
        float error_estandar = RutinasEstadisticas.calculaErrorEstandar(
                varianza, datos.length);
        float cociente_de_variacion = RutinasEstadisticas.
                                      calculaCocienteDeVariacion(varianza,
                media);
        float[] intervalo_de_confianza = RutinasEstadisticas.
                                         calculaIntervaloDeConfianza(varianza,
                datos.length, media);

        try {
            return new ResultadosEstadisticos(media, mediana, varianza,
                                              desviacion_tipica, error_estandar,
                                              cociente_de_variacion,
                                              intervalo_de_confianza,
                                              this.percentiles, percentiles,
                                              fechaInicio,
                                              fechaFin, nombreSenal);
        } catch (NotPercentilException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return null;
        }

    }

    /**public static void main(String[] args) {
      float[] f = new float[100];
      for (int i = 0; i < f.length; i++) {
        f[i] = i;
      }

      int[] in =null;// {10,100};
      Estadistico estadistico1 = new Estadistico(f,in);
      ResultadosEstadisticos re = estadistico1.getResultados();
      JFrame fr = new JFrame();
      fr.setSize(300,600);
      fr.getContentPane().add(new PanelMostrarEstadisticos(re));
      fr.show();

       }*/
    public ResultadosEstadisticos getResultados() {
        return resultados;
    }
}