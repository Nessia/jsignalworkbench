package es.usc.gsi.trace.importer.estadisticos;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Exception que se genera cuando se pide un percentil de un valor no adecuado: Ej: 83.4, 123... </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class NotPercentilException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 7184112469595184667L;

    private final int percentilPedido;

    NotPercentilException(String mensaje, int percentilPedido) {
        super(mensaje);
        this.percentilPedido = percentilPedido;
    }

    /**
     * Devuelve el valor del cual se pidio un percentil como un String.
     * @return
     */
    public int getPercentilPedido() {
        return percentilPedido;
    }

}
