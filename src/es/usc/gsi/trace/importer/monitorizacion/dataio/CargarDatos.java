//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\DataIO\\CargarDatos.java

package es.usc.gsi.trace.importer.monitorizacion.dataio;

import java.util.SortedSet;

import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;
import es.usc.gsi.trace.importer.monitorizacion.data.AlmacenDatos;

/**
 * Esta clase se encargara de cargar los datos de un archivo, que se le pasara en
 * su contructor. En funcion del tipo de archivo (.txt o .mon, segun sean un
 * archivo de texto cuyos datos estan organizadas en columnas, o una
 * monitorizacion ya realizada) creara uno u otro objeto para leer el archivo y
 * los devolvera a Fuente de datos como un objeto CargarDatos.
 */
abstract class CargarDatos {

    protected float[][] datos;
    protected byte[][] pos;
    protected String archivo;
    protected SortedSet<Mark>[] marcas;
    protected SortedSet<Annotation> anotaciones;
    protected AlmacenDatos almacen;

    protected CargarDatos() {

    }

    /**
     * @param fichero
     */
    protected CargarDatos(String fichero) {
        this.datos = null;
        this.marcas = null;
        this.anotaciones = null;
        this.pos = null;
        this.archivo = fichero;
    }

    /**
     * @param canal
     * @return java.util.LinkedList
     * @todo Que devuelva null en la superclase
     * Devuelve las marcas de una determinada senal.
     */
    protected SortedSet<Mark>[] getMarcas() {
        return marcas;
    }

    /**
     * @param canal
     * @return java.util.LinkedList
     * @todo Que devuelva null en la superclase
     * Devuelve las anotaciones de una determinada senhal.
     */
    protected SortedSet<Annotation> getAnotaciones() {
        return anotaciones;
    }

    /**
     * @return float[][]
     */
    protected float[][] getDatos() {
        return datos;
    }

    /**
     * @return int[][]
     * @todo Que devuelva null en la superclase
     */
    protected byte[][] getPos() {
        return pos;
    }

    /**Este metodo debe ser sobre escrito por todos los descendientes
     * @param fichero
     */
//    private void cargaDatos() {
//        this.datos = null;
//        this.marcas = null;
//        this.anotaciones = null;
//        this.pos = null;
//    }

    protected AlmacenDatos getAlmacen() {
        return almacen;
    }

    public void setAlmacen(SortedSet<Annotation> anotaciones) {
        this.anotaciones = anotaciones;
    }
}
