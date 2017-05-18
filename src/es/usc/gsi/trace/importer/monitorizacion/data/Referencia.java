//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\Referencia.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.LinkedList;


abstract class Referencia {
    static final long serialVersionUID = 382145L;
    /**
     * Array de punteros que apunta a un vector de datos contenido en el almacen,
     * estos datos se estan monitorizando.
     */
    private LinkedList<Object> referenciaDatos;
//    private AlmacenDatos almacen;

    /**
     * @param almacen
     */
    protected Referencia(/*AlmacenDatos almacen*/) {
        referenciaDatos = new LinkedList<Object>();
        //Generamos 20 posiciones en la lista.
        //@todo: esot es un pocoo trapalleiro.
        for (int i = 0; i < 20; i++) {
            referenciaDatos.add(null);
        }

//        this.almacen = almacen;
    }

    /**
     * @return LinkedList
     */
    protected LinkedList<Object> getReferencias() {
        return referenciaDatos;
    }

    /**
     * @param posicion -
     * Posicion en la que se ha de anhadir la referencia. Las que estuviesen en
     * posiciones superiores o iguales a esta se desplazan hacia arriba.@param canal
     */
//    void anadeReferencia(int canal, int posicion) {
//        referenciaDatos.add(posicion, almacen.getDatos(canal));
//        // System.out.println("Anade a " + (posicion-1));
//    }

    /**
     * @param posicion
     */
//    void eliminaReferencia(int posicion) {
//        referenciaDatos.remove(posicion);
//    }

    /**
     * @param referencia
     * @return Object
     */
//    protected Object getReferencias(int referencia) {
//        return referenciaDatos.get(referencia);
//    }

}
