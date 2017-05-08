//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\AlmacenDatosFloat.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.HashMap;
import java.util.TreeSet;

import es.usc.gsi.trace.importer.Perfil.PTBMInterface;
import es.usc.gsi.trace.importer.estadisticos.ResultadoCorrelacion;
import es.usc.gsi.trace.importer.estadisticos.ResultadosEstadisticos;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;

public class AlmacenDatosFloat extends AlmacenDatos {
    private float datos[][];
    static final long serialVersionUID = 32145L;
    public AlmacenDatosFloat() {

    }

    /**
     * @param datos
     * @param pos
     * @param marcas
     * @param anotaciones
     * @todo inicializar las listas
     * @todo eliminar el for de inicializacion
     */
    @SuppressWarnings("unchecked")
    public AlmacenDatosFloat(float[][] datos, byte[][] pos, TreeSet<Annotation> anotaciones,
                             TreeSet<Mark>[] marcas) {
        this.datos = datos;
        this.marcas = marcas;
        this.anotaciones = anotaciones;
        this.numero_senales = datos.length;
        if (pos == null) {
            pos = new byte[datos.length][];
        }

        this.almacen_pos = new AlmacenDatosByte(pos, null, null, null);
        this.nombreSenales = new String[datos.length];
        //this.olvidado = new java.util.LinkedList();
        this.fs = new float[datos.length];
        java.util.Arrays.fill(fs, 1);
        this.rangos_senales = new float[datos.length][2];
        this.leyendas = new String[datos.length];
        this.tienePosAsociada = new boolean[datos.length];
        this.leyendaTemporal = new String[datos.length];
        //@SuppressWarnings("unchecked")
        this.marcas = new TreeSet[datos.length];
        this.anotaciones = new TreeSet<Annotation>();
        for (int i = 0; i < datos.length; i++) {
            this.nombreSenales[i] = "parametro " + (i + 1);
            this.leyendaTemporal[i] = "leyenda temporal";
            String tmp = "";
            this.leyendas[i] = tmp;
            this.marcas[i] = new TreeSet<Mark>();

        }
        this.estadisticos = new HashMap<String,ResultadosEstadisticos>();
        this.correlaciones = new HashMap<String,ResultadoCorrelacion>();
    }

    /**
     * @param datos
     * @param pos
     * @param marcas
     * @param anotaciones
     * @todo inicializar las listas
     * @todo eliminar el for de inicializacion
     */
    public AlmacenDatosFloat(float[][] datos, byte[][] pos, TreeSet<Annotation> anotaciones,
                             TreeSet<Mark>[] marcas, PTBMInterface ptbm) {
        this(datos, pos, anotaciones, marcas);
        this.ptbm = ptbm;
    }

    /**
     * @param senal
     * @return Object
     */
    public Object getArray(int senal) {
        return datos[senal];
    }

    /**
     * @param senal
     * @return Object
     */
    public Object getDatos(int senal) {
        if (this.numero_senales <= senal) {
            return this.pos_total;
        }

        return datos[senal];
    }

    /**
     *
     * @param numSenal
     * @param datos
     */
    public void setDatos(int numSenal, Object nuevosDatos) {
        datos[numSenal] = (float[]) nuevosDatos;
    }

    /**
     * @return Object
     */
    public Object getDatos() {
        return datos;
    }

    public void anhadeSenhal(float[] nueva_senal, String nombre, String leyenda,
                             String Leyenda_temporal, float fs,
                             float[] rango, int numero_datos) {
        super.anhadeSenhal(nombre, leyenda, Leyenda_temporal, fs, rango,
                           numero_datos);
        float[][] datos_tmp = new float[datos.length + 1][];
        for (int i = 0; i < datos.length; i++) {
            datos_tmp[i] = datos[i];
        }
        datos_tmp[datos.length] = nueva_senal;
        datos = datos_tmp;
    }

    void eliminaSenhal(int numero_senhal) {
        super.eliminaSenhal(numero_senhal);
        float[][] datos_tmp = new float[datos.length - 1][];
        int cout = 0;
        for (int i = 0; i < datos.length; i++) {
            if (i != numero_senhal) {
                datos_tmp[cout] = datos[i];
                cout++;
            }
        }
        datos = datos_tmp;
    }

    /**
     * Devuelve la longitud maxima de la senhal mas grande.
     * @return
     */
    public int getMaximoNumeroDeDatos() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < datos.length; i++) {
            if (datos[i].length > max) {
                max = datos[i].length;
            }

        }
        return max;

    }

    public void setDatos(Object datos) {
        this.datos = (float[][]) datos;
    }

}
/**
 *
 * AlmacenDatosFloat.
 *
 */
