package es.usc.gsi.trace.importer.estadisticos;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class MediaMovilException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -7653785216136652411L;


    private int ventanaTemporal;

    public MediaMovilException(String mensaje, int ventanaTemporal) {
        super(mensaje);
        this.ventanaTemporal = ventanaTemporal;
    }

    /**
     * Devuelve el valor del cual se pidio un percentil como un String.
     * @return
     */
    public int getVentanaTemporal() {
        return ventanaTemporal;
    }

}
