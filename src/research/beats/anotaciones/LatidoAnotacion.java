package research.beats.anotaciones;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class LatidoAnotacion extends LimitacionAnotacion {

    /**
     *
     */
    private static final long serialVersionUID = -2399437301734724669L;

    public LatidoAnotacion() {
        super();
        this.setTipo(LimitacionAnotacion.N);
    }

    @Override
    public String getName() {
        return "Latido normal";
    }
}
