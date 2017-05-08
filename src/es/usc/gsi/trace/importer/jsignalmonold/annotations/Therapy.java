package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 ** @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Therapy extends Annotation {

    /**
     *
     */
    private static final long serialVersionUID = -440114416139741065L;

    private String nombreFarmaco;
    private String faseTerapeutica;
    private String dosificacion;
    private String tipo;

    public Therapy(String nombre_farmaco, String fase_terapeutico,
                   String dosificacion, String tipo) {
        this.nombreFarmaco = nombre_farmaco;
        this.texto = nombre_farmaco;
        this.faseTerapeutica = fase_terapeutico;
        this.dosificacion = dosificacion;
        this.tipo = tipo;
    }

    /**
     * @return String
     */
    public String getFaseTerapeutica() {
        return this.faseTerapeutica;
    }

    /**
     * @return String
     */
    public String getDosificacion() {
        return this.dosificacion;
    }

    /**
     * @return String
     */
    public String getTipoTerapia() {
        return this.tipo;
    }

    /**
     * @param fase
     */
    public void setFaseTerapeutica(String fase) {
        this.faseTerapeutica = fase;
    }

    /**
     * @param dosis
     */
    public void setDosificacion(String dosis) {
        this.dosificacion = dosis;
    }

    /**
     * @param tipo
     */
    public void setTipoTerapia(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreFarmaco() {
        return nombreFarmaco;
    }

    public void setNombreFarmaco(String nombreFarmaco) {
        this.nombreFarmaco = nombreFarmaco;
    }
}
