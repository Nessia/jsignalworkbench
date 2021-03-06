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

    private List<Attribute> atributos;
    private int tipoManifestacion;
    public static final int SINTOMA = 1;
    public static final int SIGNO = 2;
    public static final int TEST = 3;
    public static final int SENAL = 4;
    public Attribute[] theAtributo;

    public Manifestacion(String nombre, String comentario,
                         int tipo_manifestacion,
                         List<Attribute> atributos) {
        this.texto = nombre;
        this.tipoManifestacion = tipo_manifestacion;
        this.atributos = atributos;
        this.comentario = comentario;
        this.tipo = ClinicalEvent.MANIFESTACION;
    }

    /**
     * @return java.util.LinkedList
     */
    public List<Attribute> getAtributos() {
        return this.atributos;
    }

    /**
     * @return pintar.MarcasAnotaciones.Atributo
     */
    public void anadeAtributo(Attribute atributo) {
        this.atributos.add(atributo);
    }

    /**
     * @param numer_atributo
     */
    public void eliminaAtibuto(int numer_atributo) {
        this.atributos.remove(numer_atributo);
    }

    /**
     * @param num
     * @return java.util.LinkedList
     */
    public Attribute getAtributo(int num) {
        return (Attribute)this.atributos.get(num);
    }

    /**
     * @return int
     */
    public int getTipoManifestacion() {
        return this.tipoManifestacion;
    }

    public void setTipoManifestacion(int i) {
        this.tipoManifestacion = i;
    }
}
