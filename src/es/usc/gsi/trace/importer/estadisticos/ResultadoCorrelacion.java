package es.usc.gsi.trace.importer.estadisticos;

import java.io.Serializable;

public class ResultadoCorrelacion implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -38110179814087979L;

    private String senal1;
    private String senal2;
    private String fechaInicio1;
    private String fechaInicio2;
    private String fechaFin1;
    private String fechaFin2;
    private String comentario;
    private String nombre;
    private float nivelDeSignificacion;
    private int nivelDeSignificacionDiscreto;


    public String getSenal1() {
        return senal1;
    }

    public void setSenal1(String senal1) {
        this.senal1 = senal1;
    }

    public String getSenal2() {
        return senal2;
    }

    public void setSenal2(String senal2) {
        this.senal2 = senal2;
    }

    public String getFechaInicio1() {
        return fechaInicio1;
    }

    public void setFechaInicio1(String fechaInicio1) {
        this.fechaInicio1 = fechaInicio1;
    }

    public String getFechaInicio2() {
        return fechaInicio2;
    }

    public void setFechaInicio2(String fechaInicio2) {
        this.fechaInicio2 = fechaInicio2;
    }

    public String getFechaFin1() {
        return fechaFin1;
    }

    public void setFechaFin1(String fechaFin1) {
        this.fechaFin1 = fechaFin1;
    }

    public String getFechaFin2() {
        return fechaFin2;
    }

    public void setFechaFin2(String fechaFin2) {
        this.fechaFin2 = fechaFin2;
    }

    public float getNivelDeSignificacion() {
        return nivelDeSignificacion;
    }

    public void setNivelDeSignificacion(float nivelDeSignificacion) {
        this.nivelDeSignificacion = nivelDeSignificacion;
    }

    public int getNivelDeSignificacionDiscreto() {
        return nivelDeSignificacionDiscreto;
    }

    public void setNivelDeSignificacionDiscreto(int nivelDeSignificacionDiscreto) {
        this.nivelDeSignificacionDiscreto = nivelDeSignificacionDiscreto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * crea el texto descriptivo de esta correlacion
     * @return
     */
    public String getTextoDescriptivo() {
        String texto = "La correlacion fue calculada sobre la senhal " +
                       this.getSenal1() +
                       " desde " + this.getFechaInicio1() + " hasta " +
                       this.getFechaFin1() + " y sobre la senhal " +
                       this.getSenal2() +
                       " desde " + this.getFechaInicio2() + " hasta " +
                       this.getFechaFin2() + ".";
        return texto;
    }

    /**
     * crea la key con la que se lmacena esta correlacion.
     * Ser indentica siempre que coincidan las senhales y los intervalos.
     * @return
     */
    public String getKey() {
        return getSenal1() + getSenal2() + getFechaInicio1() + getFechaFin1() +
                getFechaInicio2() + getFechaFin2();
    }
}
