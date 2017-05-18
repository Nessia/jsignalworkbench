//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\AlmacenDatosFloat.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import es.usc.gsi.trace.importer.estadisticos.ResultadoCorrelacion;
import es.usc.gsi.trace.importer.estadisticos.ResultadosEstadisticos;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;
import es.usc.gsi.trace.importer.perfil.PTBMInterface;

public class AlmacenDatosFloat extends AlmacenDatos {
    static final long serialVersionUID = 32145L;

    private float[][] datos;

    public AlmacenDatosFloat() {

    }

    /**
     * @param datos
     * @param pos
     * @param marcas
     * @param anotaciones
     * @todo eliminar el for de inicializacion
     */
    @SuppressWarnings("unchecked")
    public AlmacenDatosFloat(float[][] datos, byte[][] pos2, SortedSet<Annotation> anotaciones,
          SortedSet<Mark>[] marcas) {
        this.datos = datos;
        this.marcas = marcas;
        this.anotaciones = anotaciones;
        this.numeroSenales = datos.length;
        byte[][] pos = pos2;
        if (pos == null) {
            pos = new byte[datos.length][];
        }

        this.almacenPos = new AlmacenDatosByte(pos, /*null,*/ null, null);
        this.nombreSenales = new String[datos.length];
        //this.olvidado = new java.util.LinkedList();
        this.fs = new float[datos.length];
        java.util.Arrays.fill(fs, 1);
        this.rangosSenales = new float[datos.length][2];
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
     */
    public AlmacenDatosFloat(float[][] datos, byte[][] pos, SortedSet<Annotation> anotaciones,
          SortedSet<Mark>[] marcas, PTBMInterface ptbm) {
        this(datos, pos, anotaciones, marcas);
        this.ptbm = ptbm;
    }

    @Override
    public Object getArray(int senal) {
        return datos[senal];
    }

    @Override
    public Object getDatos(int senal) {
        if (this.numeroSenales <= senal) {
            return this.posTotal;
        }
        return datos[senal];
    }

    @Override
    public void setDatos(int numSenal, Object nuevosDatos) {
        datos[numSenal] = (float[]) nuevosDatos;
    }

    @Override
    public Object getDatos() {
        return datos;
    }

    void anhadeSenhal(float[] nueva_senal, String nombre, String leyenda,
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

    @Override
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

    @Override
    public void setDatos(Object datos) {
        this.datos = (float[][]) datos;
    }

}
