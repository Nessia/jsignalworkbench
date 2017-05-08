package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Annotation extends ClinicalEvent {

    /**
     *
     */
    private static final long serialVersionUID = -7075014003144943068L;

    private int tiempoFin;


    public Annotation(){
        // Empty
    }

    public Annotation(int i){
        super(i);
    }

    /**
     * @return int
     */
    public int getTiempoFin() {
        return tiempoFin;
    }

    /**
     * @param tiempo
     */
    public void setTiempoFin(int tiempo) {
        this.tiempoFin = tiempo;
    }
}
