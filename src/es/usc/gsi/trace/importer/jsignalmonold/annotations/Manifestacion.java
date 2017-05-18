package es.usc.gsi.trace.importer.jsignalmonold.annotations;

import java.util.List;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Manifestacion extends Annotation {

    /**
     *
     */
    private static final long serialVersionUID = -1269831574909541649L;
    public enum Tipo { SINTOME, SIGNO, TEST, SENAL }

    private List<Attribute> atributos;
    private Tipo tipoManifestacion;
//    public static final int SINTOMA = 1;
//    public static final int SIGNO = 2;
//    public static final int TEST = 3;
//    public static final int SENAL = 4;


    public Manifestacion(String nombre, String comentario, Tipo tipo_manifestacion,
            List<Attribute> atributos) {
        this.texto = nombre;
        this.tipoManifestacion = tipo_manifestacion;
        this.atributos = atributos;
        this.comentario = comentario;
        this.tipo = ClinicalEvent.Tipo.MANIFESTACION;
    }

    /**
     * @return java.util.LinkedList
     */
    public List<Attribute> getAtributos() {
        return this.atributos;
    }

    /**
     * @return int
     */
    public Tipo getTipoManifestacion() {
        return this.tipoManifestacion;
    }

    public void setTipoManifestacion(Tipo i) {
        this.tipoManifestacion = i;
    }
}
