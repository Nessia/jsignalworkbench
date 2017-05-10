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

    private float mediaAritmetica;
    private float mediana;
    private float varianza;
    private float desviacionTipica;
    private float errorEstandar;
    private float cocienteVariacion;
    private float[] intervaloConfianza = new float[2];
    private HashMap<String,String> percentiles = new HashMap<String,String>();
    private String comentario;
    private String fechaInicio;
    private String fechaFin;
    private String nombreSenal;
    private boolean tienePercentiles = true;

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
        if (percentiles != null) {
            this.tienePercentiles = true;
            if (percentiles.length != valores_percentiles.length) {
                throw new NotPercentilException(
                        "Se paso un numero distinto de percentiles y de valores de percentil: " +
                        percentiles.length + " != " +
                        valores_percentiles.length, 0);
            }
            this.percentiles = new HashMap<String,String>();
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

    protected void setMediaAritmetica(float _media_aritmetica) {
        mediaAritmetica = _media_aritmetica;
    }

    protected void setMediana(float _mediana) {
        mediana = _mediana;
    }

    protected void setVarianza(float _varianza) {
        varianza = _varianza;
    }

    protected void setDesviacion_tipica(float _desviacion_tipica) {
        desviacionTipica = _desviacion_tipica;
    }

    protected void setErrorEstandar(float _error_estandar) {
        errorEstandar = _error_estandar;
    }

    protected void setCocienteDeVariacion(float _cociente_de_variacion) {
        cocienteVariacion = _cociente_de_variacion;
    }

    protected void setIntervaloDeConfianza(float[] _intervalo_de_confianza) {
        intervaloConfianza = _intervalo_de_confianza;
    }

    protected void setPercentiles(HashMap<String,String> _percentiles) {
        percentiles = _percentiles;
    }

    protected void setPercentil(int percentil, float valor) throws
            NotPercentilException {
        if (percentil < 0 || percentil > 100) {
            throw new NotPercentilException("No es un percentil admisible ",
                                            percentil);
        }

        String percentil_key = null;
        percentil_key = Integer.toString(percentil);
        percentiles.put(percentil_key, Float.toString(valor));
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

    protected void setFechaInicio(String _fecha_inicio) {
        fechaInicio = _fecha_inicio;
    }

    protected void setFechaFin(String _fecha_fin) {
        fechaFin = _fecha_fin;
    }

    /**
     * Permite modificar el nombnre de este estdistico.
     * @param _nombre_senhal
     */
    public void setNombreSenhal(String _nombre_senhal) {
        nombreSenal = _nombre_senhal;
    }

    /**
     * Permite modificar el comentario de este Estadistico.
     * @param _comentario
     */
    public void setComentario(String _comentario) {
        comentario = _comentario;
    }

    /**
     * Devuelve true si se han calculado los percentiles.
     * @return
     */
    public boolean getTienePercentiles() {
        return tienePercentiles;
    }

    /**
     * Elimina los percentiles de este resultado estadistico.
     */
    public void invalidaPercentiles() {
        this.percentiles = null;
        this.tienePercentiles = false;
    }


}
