//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\AlmacenDatosByte.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.SortedSet;
import java.util.TreeSet;

import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;

class AlmacenDatosByte extends AlmacenDatos {
    private static final long serialVersionUID = 3212L;

    /*
     * Atributos
     */

    private byte[][] datos;
    //public AlmacenDatos theAlmacenDatos; // nadie estaba utilizando este atributo

    /*
     * Constructores
     */

    public AlmacenDatosByte() {
        super();
    }

    /**
     * @param datos
     * @param pos
     * @param marcas
     * @param anotaciones
     */
    AlmacenDatosByte(byte[][] datos,/* byte[][] pos,*/
                            SortedSet<Annotation> anotaciones, TreeSet<Mark>[] marcas) {
        super(marcas, anotaciones);
        this.datos = datos;
    }

    /*
     * Métodos
     */

    @Override
    public Object getArray(int senal) {
        return datos[senal];
    }

    @Override
    public Object getDatos(int senal) {
        return datos[senal];
    }

    @Override
    public void setDatos(int numSenal, Object nuevoDatos) {
        datos[numSenal] = (byte[]) nuevoDatos;
    }

    @Override
    public Object getDatos() {
        return datos;
    }

    @Override
    public void setDatos(Object nuevosDatos) {
        datos = (byte[][]) nuevosDatos;
    }

    /**
     * Emplear solo cuando sea un almacen de posibilidad.
     * @param senal
     * @param pos
     */
    @Override
    public void setPos(int senal, byte[] pos) {
        this.datos[senal] = pos;
    }

    /**
     * Anhade la senhal indicada al almacend e datos byte.
     * @param nueva_senal
     */
    void anhadeSenhal(byte[] nueva_senal) {
        byte[][] datos_tmp = new byte[datos.length + 1][];
        for (int i = 0; i < datos.length; i++) {
            datos_tmp[i] = datos[i];
        }
        datos_tmp[datos.length] = nueva_senal;
        datos = datos_tmp;
    }

    /**
     * Devuelve la longitud maxima de la senhal mas grande.
     * @return
     */
    @Override
    public int getMaximoNumeroDeDatos() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < datos.length; i++) {
            if (datos[i].length > max) {
                max = datos[i].length;
            }
        }
        return max;
    }
}
