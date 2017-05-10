//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\ReferenciaDatos.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.LinkedList;

/**
 * Contien las referencias a los arrays que conriene los datos que se estan
 * monitorizando.
 */
public class ReferenciaDatos extends Referencia {

    boolean[] posAsociada;
    private ReferenciaPosibilidades almacenPosibilidades;

    /**
     * @param almacen
     */
    protected ReferenciaDatos(AlmacenDatos almacen) {
        super(almacen);
        almacenPosibilidades = new ReferenciaPosibilidades();
    }

    /**
     * @return LinkedList
     */
    protected LinkedList<Object> getReferenciaPos() {
        return almacenPosibilidades.getReferencias();
    }

    /**
     * @param pos
     * @return Object
     */
    protected Object getReferenciaPos(int pos) {
        return almacenPosibilidades.getReferencias(pos);
    }
}
