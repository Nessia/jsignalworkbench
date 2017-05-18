package es.usc.gsi.trace.importer.estadisticos;

/**
 * @todo documentar
 */
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class ResultadosEstadisticos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5645152427437884919L;

    private final float mediaAritmetica;
    private final float mediana;
    private final float varianza;
    private final float desviacionTipica;
    private final float errorEstandar;
    private final float cocienteVariacion;
    private final float[] intervaloConfianza;
    private final Map<String,String> percentiles;
    private final String fechaInicio;
    private final String fechaFin;
    private final boolean tienePercentiles;
    private String nombreSenal;
    private String comentario;

    /**
     * Construye un Resultado estadistico, se le paso tod menos el comentario.
     * @param media_aritmetica
     * @param mediana
     * @param varianza
     * @param desviacion_tipica
     * @param error_estandar
     * @param cociente_de_variacion
     * @param intervalo_de_confianza
     * @param percentiles
     * @param valores_percentiles
     * @param fecha_inicio
     * @param fecha_fin
     * @param nombre_senhal
     */
    public ResultadosEstadisticos(float media_aritmetica, float mediana,
                                  float varianza,
                                  float desviacion_tipica, float error_estandar,
                                  float cociente_de_variacion,
                                  float[] intervalo_de_confianza,
                                  int[] percentiles,
                                  float[] valores_percentiles,
                                  String fecha_inicio, String fecha_fin,
                                  String nombre_senhal) throws
            NotPercentilException {
        this.mediaAritmetica = media_aritmetica;
        this.mediana = mediana;
        this.varianza = varianza;
        this.desviacionTipica = desviacion_tipica;
        this.errorEstandar = error_estandar;
        this.cocienteVariacion = cociente_de_variacion;
        this.intervaloConfianza = intervalo_de_confianza;
        this.fechaFin = fecha_fin;
        this.fechaInicio = fecha_inicio;
        this.nombreSenal = nombre_senhal;
        this.percentiles = new HashMap<String,String>();
        if (percentiles != null) {
            this.tienePercentiles = true;
            if (percentiles.length != valores_percentiles.length) {
                throw new NotPercentilException(
                        "Se paso un numero distinto de percentiles y de valores de percentil: " +
                        percentiles.length + " != " +
                        valores_percentiles.length, 0);
            }

            for (int i = 0; i < percentiles.length; i++) {
                this.percentiles.put(Integer.toString(percentiles[i]),
                                     Float.toString(valores_percentiles[i]));
            }
        } else {
            this.tienePercentiles = false;
        }
    }


    public float getMediaAritmetica() {
        return mediaAritmetica;
    }

    public float getMediana() {
        return mediana;
    }

    public float getVarianza() {
        return varianza;
    }

    public float getDesviacionTipica() {
        return desviacionTipica;
    }

    public float getErrorEstandar() {
        return errorEstandar;
    }

    public float getCocienteDeVariacion() {
        return cocienteVariacion;
    }

    public float[] getIntervaloDeConfianza() {
        return intervaloConfianza;
    }

    public Map<String,String> getPercentiles() {
        return percentiles;
    }

    public String getComentario() {
        if (comentario == null) {
            return "";
        }
        return comentario;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getNombreSenhal() {
        return nombreSenal;
    }

    /**
     * Permite modificar el nombnre de este estdistico.
     * @param nombreSenhal
     */
    public void setNombreSenhal(String nombreSenhal) {
        this.nombreSenal = nombreSenhal;
    }

    /**
     * Permite modificar el comentario de este Estadistico.
     * @param comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Devuelve true si se han calculado los percentiles.
     * @return
     */
    public boolean getTienePercentiles() {
        return tienePercentiles;
    }

}
