package net.javahispano.plugins.basicstats;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Exception que se genera cuando se pide un percentil de un valor no adecuado: Ej: 83.4, 123... </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

class NotPercentilException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5425176841957321048L;
    private final int percentilPedido;

    NotPercentilException(String mensaje, int percetil_pedido) {
        super(mensaje);
        this.percentilPedido = percetil_pedido;
    }

    /**
     * Devuelve el valor del cual se pidio un percentil como un String.
     * @return
     */
    public int getPercentilPedido() {
        return percentilPedido;
    }

}
