package net.javahispano.plugins.basicstats;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>. </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

class CorrelacionException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5030201611028529763L;
    private final int longitud1;
    private final int longitud2;

    CorrelacionException(String mensaje, int longitud1,
                                int longitud2) {
        super(mensaje);
        this.longitud2 = longitud2;
        this.longitud1 = longitud1;
    }

    /**
     * Devuelve la longitud del primero de los arrays
     * @return
     */
    public int getLongitud1() {
        return longitud1;
    }

    /**
     * Devuelve la longitud del segundo de los arrays.
     * @return
     */
    public int getLongitud2() {
        return longitud2;
    }


}
