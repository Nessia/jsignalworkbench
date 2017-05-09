//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\Data\\AlmacenDatos.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.io.Serializable;
import java.util.*;

import es.usc.gsi.trace.importer.estadisticos.ResultadoCorrelacion;
import es.usc.gsi.trace.importer.estadisticos.ResultadosEstadisticos;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;
import es.usc.gsi.trace.importer.perfil.PTBMInterface;


public abstract class AlmacenDatos implements Serializable {

    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 3211L;

//    public static final int FLOAT = 4;
//    public static final int INT = 3;
//    public static final int SHORT = 2;
//    public static final int BYTE = 1;

    public enum TIPOS { BYTE, SHORT, INT, FLOAT }

    /**
     * Numero de senhales contenidas en este almacen.
     */
    //int i;
    protected int numeroSenales;
    protected boolean[] tienePosAsociada;

    protected float[][] rangosSenales;
    protected String[] leyendas;
    protected PTBMInterface ptbm;
    protected String[] nombreSenales;
    protected String[] leyendaTemporal;
    protected AlmacenDatosByte almacenPos;
    protected TreeSet<Mark>[] marcas;
    protected TreeSet<Annotation> anotaciones;
    protected byte[] posTotal;
    protected String fechaBase;

    /**
     * para meter lo que sea en el futuro....
     */
    //protected LinkedList olvidado;
    protected float[] fs;
    protected String nombre_paciente;
    protected int edad_paciente;

    /**
     * false si es mujer
     */
    protected boolean is_hombre;
    protected String comentario;
    protected String[] otros_comentarios;

    private transient boolean esta_guardado = false;
    private transient boolean tiene_archivo_asociado = false;
    private transient String archivo;

    /**
     * Para los estadisticos
     */
    protected HashMap<String,ResultadosEstadisticos> estadisticos;
    protected HashMap<String,ResultadoCorrelacion> correlaciones;


    /*
     * Constructores
     */

    public AlmacenDatos() {
        // Vacio
    }


    /**
     * @param datos
     * @param pos
     * @param marcas
     * @param anotaciones
     */
//    public AlmacenDatos(float[][] datos, byte[][] pos,
//            TreeSet<Mark>[] marcas,
//            TreeSet<Annotation> anotaciones) {
//    }

    public AlmacenDatos(TreeSet<Mark>[] marcas,
            TreeSet<Annotation> anotaciones) {
        this.anotaciones = anotaciones;
        this.marcas = marcas;
    }


    /**
     * @return Object
     */
    public abstract Object getDatos();


    public abstract void setDatos(Object datos);


    /**
     * @return int
     */
    public int getNumeroSenales() {
        return numeroSenales;
    }


    /**
     * @return Object
     */
    public Object getPos() {
        return almacenPos.getDatos();
    }


    /**
     * @param canal
     * @return boolean
     */
    public boolean isPosAsociada(int canal) {
        //En este caso es la posibilidad total
        if (this.numeroSenales == canal) {
            return true;
        }

        if (almacenPos.getDatos() != null &&
            ((byte[][]) almacenPos.getDatos())[canal] != null) {
            byte[] pos = ((byte[]) almacenPos.getDatos(canal));
            for (int i = 0; i < pos.length; i++) {
                if (pos[i] != 0) {
                    return true;
                }

            }

            return false;
        }

        return false;
    }


    /**
     * @param senal - Datos del array i, es decir la senhal i.
     * @return Object
     */
    public Object getPos(int senal) {
        if (almacenPos.getDatos() == null) {
            return null; //(new int[1]);
        }
        Object tmp = almacenPos.getDatos();
        return ((byte[][]) (tmp))[senal];
    }


    /**
     * @param senal - Datos del array i, es decir la senhal i.
     * @return Object
     * @todo cerciorarse de que funciona
     */
    public void setPos(int senal, byte[] pos) {
        almacenPos.setPos(senal, pos);
    }


    /**
     * @param senal - Datos del array i, es decir la senhal i.
     * @return Object
     */
    public abstract Object getArray(int senal);


    /**
     * @param senal
     * @return Object
     */
    public abstract Object getDatos(int senal);


    /**
     *
     * @param numSenal
     * @param nuevosDatos
     */
    public abstract void setDatos(int numSenal, Object nuevosDatos);


    /**
     * @return LinkedList[]
     */
    public TreeSet<Mark>[] getMarcas() {
        return marcas;
    }


    public void setMarcas(TreeSet<Mark>[] marcas) {
        this.marcas = marcas;
    }


    /**
     * @return TreeSet<Annotation>
     */
    public TreeSet<Annotation> getAnotaciones() {
        return anotaciones;
    }


    /**
     * @param senal
     * @return float[]
     */
    public float[] getRango(int senal) {
        if (rangosSenales.length <= senal) {
            float[] f = {
                        0, 100};
            return f;
        }
        return rangosSenales[senal];
    }


    /**
     * @param senal
     * @param rango
     */
    public void setRango(int senal, float[] rango) {
        rangosSenales[senal] = rango;
    }


    /**
     *
     * @param rango
     */
    public void setRango(float[][] rango) {
        rangosSenales = rango;
    }


    /**
     * @param senal
     * @return String[]
     * @todo cutrada
     */
    public String getLeyenda(int senal) {
        /*   if (this.numero_senales == senal) {
            String[] tmp = {"W","W","W"};
         return tmp;
             }*/
        return leyendas[senal];
    }


    /**
     * @param senal
     * @param leyendas
     */
    public void setLeyenda(int senal, String leyendas) {
        this.leyendas[senal] = leyendas;
    }


    /**
     * @param senal
     * @return String
     * @todo cutrada
     */
    public String getNombreSenal(int senal) {
        /*    if (this.numero_senales == senal) {
              return "Posibilidad";
            }*/

        return this.nombreSenales[senal];
    }


    /**
     * @param senal
     * @param nombre
     */
    public void setNombreSenal(int senal, String nombre) {
        this.nombreSenales[senal] = nombre;

    }


    /**
     * @param senal
     * @return String
     * @todo cutrada
     */
    public String getLeyendaTemporal(int senal) {
        /*       if (this.numero_senales == senal) {
             return "Posibilidad";
           }*/
        return this.leyendaTemporal[senal];
    }


    /**
     *
     * @param leyenda_temporal
     */
    protected void setLeyendaTemporal(String[] leyenda_temporal) {
        this.leyendaTemporal = leyenda_temporal;
    }


    /**
     * @param senal
     * @param anotacion
     */
    public void anadeMarca(int senal, Mark anotacion) {
        this.marcas[senal].add(anotacion);
    }


    /**
     * @param senal
     * @param anotacion
     */
    public void eliminaMarca(int senal, Mark anotacion) {
        this.marcas[senal].remove(anotacion);
    }


    /**
     * @param anotacion
     */
    public void anadeAnotacion(Annotation anotacion) {
        this.anotaciones.add(anotacion);
    }


    /**
     * @param anotacion
     */
    public void eliminaAnotacion(Annotation anotacion) {
        this.anotaciones.remove(anotacion);
    }


    //////////////Conjunto de metodos para la gestion del almacenaje a archivo
    public boolean getEstaGuardado() {
        return esta_guardado;
    }


    public void setEstaGuardado(boolean _esta_guardado) {
        esta_guardado = _esta_guardado;
    }


    public boolean getTieneArchivoAsociado() {
        return tiene_archivo_asociado;
    }


    public void setTieneArchivoAsociado(boolean _tiene_archivo_asociado) {
        tiene_archivo_asociado = _tiene_archivo_asociado;
    }


    public String getArchivo() {
        return archivo;
    }


    public void setArchivo(String _archivo) {
        archivo = _archivo;
    }


    public PTBMInterface getPTBM() {
        return ptbm;
    }


    public void setPTBM(PTBMInterface _ptbm) {
        ptbm = _ptbm;
    }


    public byte[] getPosibilidadTotal() {
        return posTotal;
    }


    public void setPosibilidadTotal(byte[] _pos_total) {
        posTotal = _pos_total;
    }


    protected void setLeyendas(String[] _leyendas) {
        leyendas = _leyendas;
    }


    protected void setNombreSenales(String[] _nombre_senales) {
        nombreSenales = _nombre_senales;
    }


    protected void setFS(float[] _nombre_senales) {
        this.fs = _nombre_senales;
    }


    public String getFechaBase() {
        return fechaBase;
    }


    public void setFechaBase(String _fecha_base) {
        fechaBase = _fecha_base;
    }


    public String getNombrePaciente() {
        return nombre_paciente;
    }


    public void setNombrePaciente(String _nombre_paciente) {
        nombre_paciente = _nombre_paciente;
    }


    public int getEdadPaciente() {
        return edad_paciente;
    }


    public void setEdadPaciente(int _edad_paciente) {
        edad_paciente = _edad_paciente;
    }


    public boolean getIsHombre() {
        return is_hombre;
    }


    public void setIsHombre(boolean _is_hombre) {
        is_hombre = _is_hombre;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String _comentario) {
        comentario = _comentario;
    }


    public String[] getOtrosComentarios() {
        return otros_comentarios;
    }


    public void setOtrosComentarios(String[] _otros_comentarios) {
        otros_comentarios = _otros_comentarios;
    }


    public float getFs(int i) {
        return fs[i];
    }


    public void setFs(float _fs, int i) {
        fs[i] = _fs;
    }


    /**
     * Devuelve la longitud maxima de la senhal mas grande.
     * Han de implementarlo las clases que la extiendan.
     * @return
     */
    public abstract int getMaximoNumeroDeDatos();


    void anhadeSenhal(String nombre, String leyenda, String Leyenda_temporal,
                      float fs,
                      float[] rango, int numero_datos) {
        int nueva_num_senales = numeroSenales + 1; //this.marcas.length+1;
        if (posTotal != null) {
            nueva_num_senales++;
        }

        String[] nombre_senales_tmp = new String[nueva_num_senales];
        float[] fs_tmp = new float[nueva_num_senales];
        float[][] rango_tmp = new float[nueva_num_senales /*+ 1*/][2];
        String[] leyendas_tmp = new String[nueva_num_senales];
        @SuppressWarnings("unchecked")
        TreeSet<Mark>[] marcas_tmp = new TreeSet[nueva_num_senales];
        boolean[] tienePosAsociada_tmp = null;
        if (posTotal == null) {
            tienePosAsociada_tmp = new boolean[nueva_num_senales];
        } else {
            tienePosAsociada_tmp = new boolean[nueva_num_senales];
        }

        String[] leyenda_temporal_tmp = new String[nueva_num_senales];
        //Si hay posibilida total

        if (posTotal != null) {
            nueva_num_senales--;
        }

        for (int i = 0; i < nueva_num_senales - 1; i++) {
            nombre_senales_tmp[i] = this.nombreSenales[i];
            fs_tmp[i] = this.fs[i];
            leyendas_tmp[i] = this.leyendas[i];
            rango_tmp[i][0] = this.rangosSenales[i][0];
            rango_tmp[i][1] = this.rangosSenales[i][1];
            marcas_tmp[i] = this.marcas[i];
            //TienePosibilidadAsociadano no incluye a la posibilidad
            if ( /*pos_total ==  null || */i != nueva_num_senales - 1) {
                tienePosAsociada_tmp[i] = this.tienePosAsociada[i];
            }
            leyenda_temporal_tmp[i] = this.leyendaTemporal[i];
        }
        //if (pos_total == null) {
        nueva_num_senales--;
        //  }
        nombre_senales_tmp[nueva_num_senales] = nombre;
        fs_tmp[nueva_num_senales] = fs;
//      leyendas_tmp[nueva_num_senales] = tmp;
        rango_tmp[nueva_num_senales][0] = rango[0];
        rango_tmp[nueva_num_senales][1] = rango[1];
        marcas_tmp[nueva_num_senales] = new TreeSet<Mark>();
        tienePosAsociada_tmp[nueva_num_senales - 1] = true;
        leyendas_tmp[nueva_num_senales] = leyenda;
        leyenda_temporal_tmp[nueva_num_senales] = Leyenda_temporal;
        //La posibilidad siempre debe ser la ultima en almacenar su rango
        if (posTotal != null) {
            nueva_num_senales++;
            nombre_senales_tmp[nueva_num_senales] = this.nombreSenales[
                    numeroSenales];
            fs_tmp[nueva_num_senales] = this.fs[numeroSenales];
            leyendas_tmp[nueva_num_senales] = this.leyendas[numeroSenales];
            rango_tmp[nueva_num_senales][0] = this.rangosSenales[
                                              numeroSenales][0];
            rango_tmp[nueva_num_senales][1] = this.rangosSenales[
                                              numeroSenales][1];
            marcas_tmp[nueva_num_senales] = this.marcas[numeroSenales];
            tienePosAsociada_tmp[nueva_num_senales - 1] = true;
            //leyendas_tmp[nueva_num_senales] = this.leyenda_temporal[numero_senales];
            leyenda_temporal_tmp[nueva_num_senales] = this.leyendaTemporal[
                    numeroSenales];
        }

        nombreSenales = nombre_senales_tmp;
        this.fs = fs_tmp;
        leyendas = leyendas_tmp;
        this.rangosSenales = rango_tmp;
        marcas = marcas_tmp;
        tienePosAsociada = tienePosAsociada_tmp;
        this.leyendaTemporal = leyenda_temporal_tmp;
        this.leyendas = leyendas_tmp;
        numeroSenales++;
        this.almacenPos.anhadeSenhal(new byte[numero_datos]);

    }


    void eliminaSenhal(int numero_senhal) {

        float[][] rangos_tmp = new float[rangosSenales.length - 1][];
        float[] fs_tmp = new float[fs.length - 1];
        String[] nombres_tmp = new String[nombreSenales.length - 1];
        String[] leyendas_temporales_tmp = new String[leyendaTemporal.length -
                                           1];
        String[] leyendsd_tmp = new String[leyendas.length - 1];
        @SuppressWarnings("unchecked")
        TreeSet<Mark>[] marcas_tmp = new TreeSet[marcas.length - 1];
        boolean[] pos_tmp = new boolean[tienePosAsociada.length - 1];
        int cout = 0;
        int num_senales = rangosSenales.length;
        /*   if ( pos_total != null) {
             num_senales++;
           }*/

        //Si hay posibilidad => esa no cuenta
        for (int i = 0; i < num_senales; i++) {
            if (i != numero_senhal) {
                rangos_tmp[cout] = rangosSenales[i];
                fs_tmp[cout] = fs[i];
                nombres_tmp[cout] = nombreSenales[i];
                leyendas_temporales_tmp[cout] = leyendaTemporal[i];
                leyendsd_tmp[cout] = leyendas[i];
                marcas_tmp[cout] = marcas[i];
                if (posTotal == null || i != num_senales - 1) {
                    pos_tmp[cout] = tienePosAsociada[i];
                }
                cout++;
            }
        }
        //Si hay posibilidad global hay que almacenar sus datos
        //El segundo chequeo siempre deberiera ser true, pero es para curarse en salud
        if (getPosibilidadTotal() != null && marcas_tmp.length == num_senales) {
            fs_tmp[num_senales - 1] = fs[num_senales];
            nombres_tmp[num_senales - 1] = nombreSenales[num_senales];
            leyendas_temporales_tmp[num_senales -
                    1] = leyendaTemporal[num_senales];
            leyendsd_tmp[num_senales - 1] = leyendas[num_senales];
            marcas_tmp[num_senales - 1] = marcas[num_senales];
        }

        numeroSenales--;

        rangosSenales = rangos_tmp;
        fs = fs_tmp;
        nombreSenales = nombres_tmp;
        leyendaTemporal = leyendas_temporales_tmp;
        leyendas = leyendsd_tmp;
        marcas = marcas_tmp;
        tienePosAsociada = pos_tmp;
    }

    /**
     *
     * @param tmp
     */
    public void setAnotaciones(TreeSet<Annotation> anotaciones) {
        this.anotaciones = anotaciones;
    }

    /**
     *
     * @param resultados
     */
    void anadeEstadistico(ResultadosEstadisticos resultados) {
        estadisticos.put(resultados.getNombreSenhal() +
                         resultados.getFechaInicio() +
                         resultados.getFechaFin(), resultados);
    }


    /**
     *
     * @param resultados
     */
    void anadeCorrelacion(ResultadoCorrelacion resultados) {
        correlaciones.put(resultados.getKey(), resultados);
    }


    /**
     *
     * @param estadistico
     * @return
     */
    boolean eliminaEstadistico(String estadistico) {
        if (estadisticos.remove(estadistico) != null) {
            return true;
        }
        return false;
    }


    /**
     *
     * @param estadistico
     * @return
     */
    boolean eliminaCorrelacion(ResultadoCorrelacion correlacion) {
        if (correlaciones.remove(correlacion.getKey()) != null) {
            return true;
        }
        return false;
    }


    /**
     *
     * @param estadistico
     * @return
     */
    boolean eliminaCorrelacion(String correlacion) {
        if (correlaciones.remove(correlacion) != null) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    void eliminaTodosLosEstadisticos() {
        estadisticos = new HashMap<String, ResultadosEstadisticos>();
    }

    /**
     *
     */
    void eliminaTodasLasCorrelaciones() {
        correlaciones = new HashMap<String, ResultadoCorrelacion>();
    }

    /**
     *
     * @return
     */
    protected Collection<ResultadosEstadisticos> getEstadisticos() {
        return estadisticos.values();
    }


    /**
     *
     * @return
     */
    protected Collection<ResultadoCorrelacion> getCorrelaciones() {
        return correlaciones.values();
    }


    /**
     *
     * @param estadistico
     * @return
     */
    protected ResultadosEstadisticos getEstadistico(String estadistico) {
        return ((ResultadosEstadisticos) estadisticos.get(estadistico));
    }


    protected ResultadoCorrelacion getCorrelacion(String correlacion) {
        return ((ResultadoCorrelacion) correlaciones.get(correlacion));
    }


    /**Cuando se carga un archivo serializado este no posee ciertos campos que se anhadienron
     * al almacen a posteriori. Los inicializamos aqui
     */

    public void inicializaPartesNoSerializadas() {
        eliminaTodosLosEstadisticos();
        eliminaTodasLasCorrelaciones();
    }
}
