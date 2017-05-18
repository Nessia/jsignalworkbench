package net.javahispano.plugins.basicstats;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

class MediaMovilException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -427355725306823580L;

    private final int ventanaTemporal;

    MediaMovilException(String mensaje, int ventanaTemporal) {
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
