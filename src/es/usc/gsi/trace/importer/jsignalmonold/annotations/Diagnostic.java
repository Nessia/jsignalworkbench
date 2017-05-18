package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Diagnostic extends Annotation {

    /**
     *
     */
    private static final long serialVersionUID = -5769222345970481773L;

    private Attribute atributo;

    public Diagnostic(String nombre, Attribute atributo) {
        this.texto = nombre;
        this.atributo = atributo;
    }

    /**
     * @return pintar.MarcasAnotaciones.Atributo
     */
    public Attribute getAtributo() {
        return this.atributo;
    }

    /**
     * @param atributo
     */
    public void setAtributo(Attribute atributo) {
        this.atributo = atributo;
    }
}
