//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\data\\GestorDatos.java

package es.usc.gsi.trace.importer.monitorizacion.data;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.trace.importer.estadisticos.*;
import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.*;
import es.usc.gsi.trace.importer.perfil.PTBMInterface;

public class GestorDatos {


    private static final Logger LOGGER = Logger.getLogger(GestorDatos.class.getName());
    //private static int TIPO_ALMACEN;
    private static boolean isTiempoReal = false;
//    private static boolean is_instancia;
    private static GestorDatos instancia;

    //private LinkedList manejadoresDeMedidaTerminada = new LinkedList();
    private int numeroSenalesMonitorizadas = 0;
    private ReferenciaDatos referencias;

    private AlmacenDatos almacen;
    //@TODO ponerle un tamaño adecuado a los HasMap
    private Map<Integer,Integer> mapeaSenalCanal = new HashMap<Integer,Integer>(5, 0.75F);
    private Map<Integer,Integer> mapeaCanalSenal = new HashMap<Integer,Integer>(5, 0.75F);

    private boolean estaGuardado = true;
    private boolean tieneArchivoAsociado = false;
    //private String archivo;

    //Maxima duracion del regisstro como indice
    private int duracionTotalRegistro;

    //Seneles que se deben monitorizar (las que se monitorizaron la ultima vez).
    private int[] senalesQueSeMonitorizaronLaUltimaVez;
    /**
     * default constructor throws exception if the user tries to instantiate more than
     * one instance
     * @throws SingletonException@throws monitorizacion.data.SingletonException
     */
    private GestorDatos() throws Exception {
//        if (is_instancia) {
//            throw new Exception("Only one instance allowed");
//        } else {
//            is_instancia = true;
//        }
    }


    /**
     * Crea el almacen del tipo especificado.
     * @param tipo - "int", "short", o "float" segun se requiera.
     * @return monitorizacion.Data.AlmacenDatos
     */
    public AlmacenDatos getAlmacen() {
        return almacen;
    }

    /**
     * Devuelve un LikedList con las referencias a todos los canales que se estan
     * monitorizando.arq
     * @return LinkedList
     */
    public List<Object> getReferenciasDatos() {
        return referencias.getReferencias();
    }

    /**
     * Devuelve un LikedList con las referencias a las posibilidades de todos los
     * canales que se estan monitorizando. Si no hay posibilidad asociada la lista
     * contendra null.
     * @return LinkedList
     */
    public List<Object> getReferenciasPos() {
        return referencias.getReferenciaPos();
    }

    /**
     * Devuelve el actual almacen.
     * @return monitorizacion.data.AlmacenDatos
     */
    public AlmacenDatos getCurrentAlmacen() {
        return almacen;
    }

    /**
     * Merece la pena implementarlo
     * @param datos
     */
    public void setDatos(float[][] datos) {
        almacen.setDatos(datos);
    }

    public float[][] getDatos() {
        return (float[][]) almacen.getDatos();
    }

    /**
     * @param pos
     */
    public void setPos(byte[][] pos) {
        for (int i = 0; i < pos.length; i++) {
            almacen.setPos(i, pos[i]);
        }
    }

    /**
     * @param num_canal
     * @param primero
     * @param ultimo
     * @return int[]
     */
    public void setPos(int num_canal, int primero, int ultimo, byte pos) {
        byte[] tmp = (byte[]) almacen.getPos(num_canal);
        //Si aun no habia posibilidad asociada a este canal
        if (tmp == null) {
            tmp = new byte[this.getNumeroDatos(num_canal)];
            this.almacen.setPos(num_canal, tmp);
        }
        for (int i = primero; i < ultimo && i < tmp.length; i++) {
            tmp[i] = (byte) Math.max(tmp[i], pos);
        }
        this.setEstaGuardado(false);

    }

    /**
     * @return GestorDatos
     */
    public static GestorDatos getInstancia() {
        if (instancia != null) {
            return instancia;
        } else {
            try {
                GestorDatos.instancia = new GestorDatos();
                return instancia;
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                return null;
            }

        }
    }

    /**
     * @param num_canal
     * @param primero
     * @param ultimo
     * @return float[]
     */
    public Object getData(int num_canal, int primero, int ultimo) {
        int num_datos = ultimo - primero;
        float[] result = new float[num_datos + 1];
        if (this.getNumeroSenales() > (this.mapeaCanalSenal.get(num_canal)).intValue()) {
            float[] tmp = (float[]) referencias.getReferencias(num_canal);
            for (int i = primero; i <= ultimo; i++) {
                if (i < tmp.length) {
                    result[i - primero] = tmp[i];
                } else {
                    result[i - primero] = almacen.getRango(this.mapeaCanalSenal.get(num_canal).intValue())[0];
                }

            }
        } else {
            byte[] tmp = this.almacen.getPosibilidadTotal();
            for (int i = primero; (i <= ultimo && i < tmp.length); i++) {
                result[i - primero] = tmp[i];
            }
        }
//        if (result == null) {
//            float[] tt = new float[10];
//            result = tt;
//        }
        return result;
    }

    /**
     * @param num_canal
     * @param primero
     * @param ultimo
     * @return int[]
     */
    public byte[] getPossibility(int num_canal, int primero, int ultimo) {
        int num_datos = ultimo - primero;
        byte[] result = new byte[num_datos + 1];
        byte[] tmp = null;
        try {
            // if (this.getNumeroSenales() >= num_canal) {
            if (this.getNumeroSenales() > (this.mapeaCanalSenal.get(num_canal)).intValue()) {
                // tmp = (byte[])referencias.getReferencias(num_canal-1);
                tmp = (byte[]) almacen.getPos(this.mapeaCanalSenal.get(num_canal).intValue()); ;
            } else {
                tmp = this.almacen.getPosibilidadTotal();
            }

            for (int i = primero; (i <= ultimo && i < tmp.length); i++) {
                result[i - primero] = tmp[i];
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }

        if (tmp == null) {
            result = new byte[num_datos + 1];
        }

        return result;
    }

    /**
     * @param num_canal
     * @param primero
     * @param ultimo
     * @return java.util.LinkedList
     */
    public List<Mark> getMarks(int num_canal, int primero, int ultimo) {
        /* if (true) {
            return  new LinkedList();
          }*/
        int num_senal = (this.mapeaCanalSenal.get(num_canal)).intValue();
        LinkedList<Mark> respuesta = new LinkedList<Mark>();
        SortedSet<Mark> anotaciones = almacen.getMarcas()[num_senal];
//        if (anotaciones == null) {
//            System.out.println("");
//        }

        Mark ev_1 = new Mark(primero);
        Mark ev_2 = new Mark(ultimo + 1);
        SortedSet<Mark> anotaciones_en_rango = anotaciones.subSet(ev_1, ev_2);
        Iterator<Mark> it = anotaciones_en_rango.iterator();
        while (it.hasNext()) {
            respuesta.add(it.next());
        }
        return respuesta;
    }

    /**
     * @param num_canal
     * @param primero
     * @param ultimo
     * @return java.util.LinkedList
     */
    public List<Annotation> getAnnotations(int primero, int ultimo) {
        LinkedList<Annotation> respuesta = new LinkedList<Annotation>();
        try {
            SortedSet<Annotation> anotaciones = almacen.getAnotaciones();
            Annotation ev_1 = new Annotation(primero);
            Annotation ev_2 = new Annotation(ultimo + 1);
            SortedSet<Annotation> anotaciones_en_rango = anotaciones.subSet(ev_1, ev_2);
            Iterator<Annotation> it = anotaciones_en_rango.iterator();
            while (it.hasNext()) {
                respuesta.add(it.next());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }

        return respuesta;

    }

    /**
     * Construye un almacen del tipo especificado.
     * @param datos
     * @param pos
     * @param anotaciones
     * @param marcas@param tipo_almacen - Indica que tipo de almacen se desea, si de
     * Float, int Shorth o byte.
     */
    public void setAlmacen(Object datos, byte[][] pos, SortedSet<Annotation> anotaciones,
                           TreeSet<Mark>[] marcas, AlmacenDatos.TIPOS tipo_almacen, PTBMInterface ptbm) {
        switch (tipo_almacen) {
        case BYTE:
            almacen = new AlmacenDatosByte((byte[][]) datos, /*pos,*/ anotaciones, marcas);
            //GestorDatos.TIPO_ALMACEN = AlmacenDatos.BYTE;
            break;

//        case FLOAT:
//            almacen = new AlmacenDatosFloat((float[][]) datos, pos, anotaciones, marcas, ptbm);
//            //GestorDatos.TIPO_ALMACEN = AlmacenDatos.FLOAT;
//            break;

        default:
            almacen = new AlmacenDatosFloat((float[][]) datos, pos, anotaciones, marcas, ptbm);
            //GestorDatos.TIPO_ALMACEN = AlmacenDatos.FLOAT;
            break;

        }
        referencias = new ReferenciaDatos(almacen);
    }

    /**
     * Metodo al que se le pasa un almacenDatos, pasando este a ser el almacen de
     * datos de este gestos
     * @param AlmcenDatos
     */
    public void setAlmacen(AlmacenDatos almacen) {
        this.almacen = almacen;
        referencias = new ReferenciaDatos(almacen);
    }

    /**
     * Contruye el almacen por defecto, de float.
     * @param datos
     * @param pos
     * @param anotaciones
     * @param marcas
     */
    public void setAlmacen(float[][] datos, byte[][] pos, SortedSet<Annotation> anotaciones,
          SortedSet<Mark>[] marcas, PTBMInterface ptbm) {
        almacen = new AlmacenDatosFloat(datos, pos, anotaciones, marcas, ptbm);
        referencias = new ReferenciaDatos(almacen);
    }

    /**
     * @return boolean
     */
    public boolean getTiempoReal() {
        return isTiempoReal;
    }

    /**
     * @param _is_tiempo_real
     */
    public static void setRealTime(boolean _is_tiempo_real) {
        isTiempoReal = _is_tiempo_real;
    }

    /**
     * @return int
     */
    public int getNumeroSenales() {
        if (this.almacen != null) {
            return almacen.getNumeroSenales();
        }
        return 0;
    }

    /**
     * @param senal
     * @param posicion
     */
    public void anadeReferencia(int senal, int posicion) {
        this.mapeaSenalCanal.put(senal, posicion);
        this.mapeaCanalSenal.put(posicion, senal);
        referencias.anadeReferencia(senal, posicion);
    }

    /**
     * Mantiene la consistencia del mapeo cuando se anhade una senhal.
     * La senhal se anhade antes de la posibilidad, por lo que abra que desplazar esta
     * una posicion hacia arriba.
     */
    public void mantenconsitenciaAlAnhadirSenhal() {
        //Si hay posibilidad
        if (almacen.getPosibilidadTotal() != null) {
            int numero_senhales = almacen.getNumeroSenales();
            //Y si esta se esta monitorizando
            if (this.mapeaSenalCanal.get(numero_senhales) != null) {
                Integer num_canal = this.mapeaSenalCanal.get(numero_senhales);
                Integer new_senal = numero_senhales;
                this.mapeaSenalCanal.remove(numero_senhales);
                this.mapeaSenalCanal.put(new_senal, num_canal);
                this.mapeaCanalSenal.put(num_canal, new_senal);
                referencias.eliminaReferencia(num_canal.intValue() - 1);
                referencias.anadeReferencia(num_canal.intValue(), new_senal.intValue());
            }
        }
    }


    /**
     * @param senal
     * @param leyendas
     */
    public void setLeyenda(int senal, String leyendas) {
        almacen.setLeyenda(senal, leyendas);
        this.setEstaGuardado(false);
    }

    /**
     * @param senal
     * @param leyendas
     */
    public void setLeyendaTemporal(String[] leyendas) {
        almacen.setLeyendaTemporal(leyendas);
        this.setEstaGuardado(false);
    }

    /**
     * @param senal
     * @return String
     */
    public String getNombreSenalDelCanal(int senal) {
        int canal = this.mapeaCanalSenal.get(senal).intValue();
        return almacen.getNombreSenal(canal) + "\n" + almacen.getLeyenda(canal);
    }

    /**
     * @param senal
     * @return String
     */
    public String getNombreSenal(int senal) {
        return almacen.getNombreSenal(senal) + " " + almacen.getLeyenda(senal);
    }

    /**
     * @param senal
     * @param nombre
     */
    public void setNombreSenal(int senal, String nombre) {
        almacen.setNombreSenal(senal, nombre);
        this.setEstaGuardado(false);

    }

    /**
     * @return Object
     */
    public Object getPos() {
        return this.almacen.getPos();
    }

    /**
     * @return Object
     * @param int canal
     */
    public Object getPos(int canal) {
        return this.almacen.getPos(this.mapeaCanalSenal.get(canal).intValue());
    }

    /**
     * @param senal
     * @return int
     */
    public Integer getNumeroReferenciaDeSenal(int senal) {
        return mapeaSenalCanal.get(senal);
    }

    /**
     * @return int
     */
    public int getNumeroSenalesMonitorizadas() {
        return numeroSenalesMonitorizadas;
    }

    /**
     * @param _numero_senales_monitorizadas
     */
    public void setNumeroSenalesMonitorizadas(int _numero_senales_monitorizadas) {
        numeroSenalesMonitorizadas = _numero_senales_monitorizadas;
    }

    /**
     * Permite averigura en que cana se esta monitorizando la senhal senal
     * @param senal
     */
    public int getCanalDeSena(int num_canal) {
        Integer tmp = this.mapeaSenalCanal.get(num_canal);
        return tmp.intValue();
    }

    /**
     * Anhade una marca al canal especificado.
     * @param senal
     * @param anotacion
     */
    public void anadMark(int canal, Mark anotacion) {
        int senal = mapeaCanalSenal.get(canal).intValue();
        this.almacen.anadeMarca(senal, anotacion);
        this.setEstaGuardado(false);
    }

    /**
     * Elimina una marca al canal especificado.
     * @param senal
     * @param anotacion
     */
    public void deleteMark(int canal, Mark anotacion) {
        int senal = this.mapeaCanalSenal.get(canal).intValue();
        this.almacen.eliminaMarca(senal, anotacion);
        this.setEstaGuardado(false);
    }

    /**
     * @param anotacion
     */
    public void addAnnotation(Annotation anotacion) {
        this.almacen.anadeAnotacion(anotacion);
        this.setEstaGuardado(false);

    }

    /**
     * @param anotacion
     */
    public void deleteAnnotation(Annotation anotacion) {
        this.almacen.eliminaAnotacion(anotacion);
        this.setEstaGuardado(false);
    }

    /**
     * Indica si el almacen de datos actual esta o no guardado
     */
    public boolean getEstaGuardado() {
        /* if (this.almacen != null) {
            return this.almacen.getEstaGuardado();
         }*/
        return this.estaGuardado;
    }

    /**
     * @todo: por culpa de la puta Strtus bar La hize "Pseudo singleton" y he
     * hecho que esta claso solo modifique el estado de la variable via
     * este metodo. Mucha chorrada e ineficiencia.
     * Modifica el estado (Guardado o no gurdado) del almacen de datos actual
     */
    public void setEstaGuardado(boolean _esta_guardado) {
        estaGuardado = _esta_guardado;
        this.almacen.setEstaGuardado(_esta_guardado);
    }

    /**
     * Indica si tiene o no un archivo asociado el almacen de datos actual
     */
    public boolean getTieneArchivoAsociado() {
        if (this.almacen != null) {
            return this.almacen.getTieneArchivoAsociado();
        }
        return this.tieneArchivoAsociado;
    }

    /**
     * Vale para modificar el indicador de archivo asociado
     */
    public void setTieneArchivoAsociado(boolean _tiene_archivo_asociado) {
        tieneArchivoAsociado = _tiene_archivo_asociado;
        this.almacen.setTieneArchivoAsociado(_tiene_archivo_asociado);
    }

    /**
     * Devuelve una cadena de caractyeres que es el PAth absoluto, en el
     * formato de la plataforma de ejecucion, del archivo asociado al actual.
     * Si no hay almacen cargado cevuelve null.
     * almace de datos
     */
    public String getArchivo() {
        if (almacen != null) {
            return almacen.getArchivo();
        }
        return null;
    }

    /**
     * Asocia una cadena de caractyeres que es el PAth absoluto, en el
     * formato de la plataforma de ejecucion, del archivo asociado al actual
     * almacen de datos
     */
    public void setArchivo(String _archivo) {
        almacen.setArchivo(_archivo);
    }

    /**
     * Este metodo devuelve el numero de datos de un canal determinado
     */

    public int getNumeroDatos(int i) {
        return Array.getLength(((Object[]) almacen.getDatos())[i]);
    }

    /**
     * Este metodo devuelve el registro de la posicicion especificad ane forma de un array
     */
    public Object getDatos(int i) {
        return this.almacen.getDatos(i);
    }

    public void setDatos(int numSenal, float[] nuevosDatos) {
        almacen.setDatos(numSenal, nuevosDatos);
    }

    /**
     * Asigna una BC  este almacen
     * @param _ptbm
     */
    public void setPTBM(PTBMInterface _ptbm) {
        this.almacen.setPTBM(_ptbm);
    }

    /**
     * Este metodo devuelve la posibilidad global de una deteccion a un almacen de datos
     * El parametro devuelto es un array indicando para cada instante temporal la posibilidad
     * entre 0 y 100 de la ocurrencia de un determinado evento
     * @return
     */
    public byte[] getPosibilidadTotal() {
        return this.almacen.getPosibilidadTotal();
    }

    /**
     * Este metodo asigna una posibilidad globar de una deteccion a un almacen de datos
     * El parametro devuelto es un array indicando para cada instante temporal la posibilidad
     * entre 0 y 100 de la ocurrencia de un determinado evento
     * @return
     * @todo un poco chapuza la forma de elegir el tamanho del vector de posibilidades
     * @todo ponerle una leyenda temporal a la posibilidad en caso de que no todas sean iguales
     */
    public void setPosibilidadTotal(int primero, int ultimo, byte pos) {
        if (this.almacen.getPosibilidadTotal() == null) {
            this.almacen.setPosibilidadTotal(new byte[this.getNumeroDatos(0)]);
        }
        if (almacen.getNumeroSenales() + 1 > almacen.getMarcas().length) {
            SortedSet<Mark>[] marcas = almacen.getMarcas();
            int num_senales = marcas.length + 1;
            @SuppressWarnings("unchecked")
            SortedSet<Mark>[] marcas_new = new TreeSet[num_senales];
            String[] leyendas = new String[num_senales];
            String[] nombres = new String[num_senales];
            String[] leyendas_temporales = new String[num_senales];
            float[] fs = new float[num_senales];
            for (int i = 0; i < num_senales - 1; i++) {
                marcas_new[i] = new TreeSet<Mark>();
                marcas_new[i] = almacen.getMarcas()[i];
                leyendas[i] = almacen.getLeyenda(i);
                nombres[i] = almacen.getNombreSenal(i);
                leyendas_temporales[i] = almacen.getLeyendaTemporal(i);
                fs[i] = almacen.getFs(i);
            }
            marcas_new[num_senales - 1] = new TreeSet<Mark>();
            String tmp = "%";
            leyendas[num_senales - 1] = tmp;
            nombres[num_senales - 1] = /*"Posibilidad";//*/ "Detection";
            leyendas_temporales[num_senales - 1] = leyendas_temporales[0];
            fs[num_senales - 1] = fs[0];
            almacen.setMarcas(marcas_new);
            almacen.setLeyendas(leyendas);
            almacen.setNombreSenales(nombres);
            almacen.setLeyendaTemporal(leyendas_temporales);
            almacen.setFs(fs);
        }
        byte[] tmp = this.almacen.getPosibilidadTotal();
        for (int i = primero; i < ultimo; i++) {
            tmp[i] = (byte) Math.max(tmp[i], pos);
        }

    }

    /**
     * Este metodo elimina el actual gestor de datos. La proxima vez que se solicite
     * un gestor de datos se creara uno nuevo. Consecuentemente se elimina el
     * almacen de datos asociado.
     */
    public static void eliminaGestorDatos() {
        instancia.almacen = null;
        instancia.numeroSenalesMonitorizadas = 0;
        instancia.estaGuardado = true;
    }

    /**
     * Este metodo devuelve la fecha base de este almacen
     * @return
     */
    public String getFechaBase() {
        return almacen.getFechaBase();
    }

    /**
     * Modifica la fecha base del almacende datos actual
     * @param _fecha_base
     */
    public void setFechaBase(String _fecha_base) {
        almacen.setFechaBase(_fecha_base);
        SamplesToDate.getInstancia().setFechaBase(_fecha_base);
        this.setEstaGuardado(false);
    }

    public String getNombrePaciente() {
        return almacen.getNombrePaciente();
    }

    public void setNombrePaciente(String _nombre_paciente) {
        almacen.setNombrePaciente(_nombre_paciente);
    }

    public int getEdadPaciente() {
        return almacen.getEdadPaciente();
    }

    public void setEdadPaciente(int _edad_paciente) {
        almacen.setEdadPaciente(_edad_paciente);
    }

    public boolean getIsHombre() {
        return almacen.getIsHombre();
    }

    public void setIsHombre(boolean _is_hombre) {
        almacen.setIsHombre(_is_hombre);
    }

    /**
     * Devuelve null si no hay comentario.
     * @return
     */

    public String getComentario() {
        if (almacen != null) {
            return almacen.getComentario();
        }
        return null;
    }

    public void setComentario(String _comentario) {
        almacen.setComentario(_comentario);
        this.setEstaGuardado(false);
    }

    public String[] getOtrosComentarios() {
        return almacen.getOtrosComentarios();
    }

    public void setOtrosComentarios(String[] _otros_comentarios) {
        almacen.setOtrosComentarios(_otros_comentarios);
    }

    /**
     * Almacena en el almacen las frecuancias
     * @param fs
     */
    public void setFs(float[] fs) {
        almacen.setFs(fs);
    }

    /**
     * Este metodo almacena en el almacen ,cambia la FS de un canal, pero se le pasa
     *
     * @param num_canal
     * @param fs
     */
    public void setFs(int num_canal, float fs) {
        //almacen.setFs(fs,((Integer)(this.mapea_canal_senal.get(new Integer(num_canal)))).intValue());
        almacen.setFs(fs, num_canal);
    }

    /**
     *
     * @param num_senal
     * @param fs
     */
    public void setFsSenal(int num_senal, float fs) {
        almacen.setFs(fs, num_senal);
        this.setEstaGuardado(false);
    }


    /**
     * Devuelve loa fs de una senhal espcificada.
     * @param num_senal
     * @return
     */
    public float getFsSenal(int num_senal) {
        return almacen.getFs(num_senal);
    }

    /**
     * Devuelve la fs de muestreo de la senal especificada
     * @param num_senal
     * @return
     */
    public float getFsSenhal(int num_senal) {
        return almacen.getFs(num_senal);
    }

    public void setMaximaDuracionDelRegistro(int i) {
        this.duracionTotalRegistro = i;
    }

    public int getMaximaDuracionDelRegistro() {
        return duracionTotalRegistro;
    }

    /**
     * anhade una nueva senhal al almacen de datos
     * @todo: supone que el almacen de datos el float. No debiera serlo necesariamente.
     * @param nueva_senal
     * @param nombre
     * @param leyenda
     * @param Leyenda_temporal
     * @param fs
     * @param rango
     */
    public void anhadeSenhal(float[] nueva_senal, String nombre, String leyenda,
                             String Leyenda_temporal, float fs,
                             float[] rango) {
        //PUTO BUG Desmonitorizo la posibilidad antes de nada:
        if (this.getPosibilidadTotal() != null) {
            byte[] tmp = this.getPosibilidadTotal();
            byte[][] tmps = new byte[this.getNumeroSenales()][];
            for (int i = 0; i < tmps.length; i++) {
                tmps[i] = (byte[]) almacen.getPos(i);
            }

            for (int i = 0; i < tmps.length; i++) {
                almacen.setPos(i, tmps[i]);
            }

            almacen.setPosibilidadTotal(tmp);

        }
//Fin chapuza
        ((AlmacenDatosFloat) (almacen)).anhadeSenhal(nueva_senal, nombre, leyenda,
                Leyenda_temporal, fs, rango, nueva_senal.length);
    }


    /**
     * Anhade un estadistico al actual almacen.
     * @param resultados
     */
    public void anadeEstadistico(ResultadosEstadisticos resultados) {
        almacen.anadeEstadistico(resultados);
    }

    /**
     * Anhade una correlacion al actual almacen.
     * @param resultados
     */
    public void anadeCorrelacion(ResultadoCorrelacion resultados) {
        almacen.anadeCorrelacion(resultados);
    }

    /**
     * Elimina el estadistico indicado por la key que se le pasa. La key de un estadistico es el
     * nombre que tiene, mas su fecha de incio, mas su fecha de fin, todos ellos como String.
     * @param estadistico
     * @return
     */
    public boolean eliminaEstadistico(String estadistico) {
        return almacen.eliminaEstadistico(estadistico);
    }

    /**
     * Elimina la correlacion indicado por la key que se le pasa. La key de un estadistico es el
     * nombre que tiene la primera senhal, mas el nombre de la segunada, mas la fecha de incio de la
     * primera senhal, mas la fecha de inicio de la segunda senhal mas la fecha de fin
     * de la segunda senhal, todos ellos cocatenados en un String.
     * @param estadistico
     * @return
     */
    public boolean eliminaCorrelacion(String correlacion) {
        return almacen.eliminaCorrelacion(correlacion);
    }

    /**
     * Elimina la correlacion que se le pasa.
     * @param estadistico
     * @return
     */
    public boolean eliminaCorrelacion(ResultadoCorrelacion correlacion) {
        return almacen.eliminaCorrelacion(correlacion);
    }

    /**
     * Elimina todos los estadisticos.
     */
    public void eliminaTodosLosEstadisticos() {
        almacen.eliminaTodosLosEstadisticos();
    }

    /**
     * Elimina todos los estadisticos.
     */
    public void eliminaTodosLasCorrelaciones() {
        almacen.eliminaTodasLasCorrelaciones();
    }

    /**
     * Devuelve todos los estadisticos como un colection.
     * OJO: devuelve la coleccion original del almacen, y no una copia de esta, por lo
     * que modificar esa coleccion es modificar el almacen.
     * @return
     */
//    public Collection<ResultadosEstadisticos> getEstadisticos() {
//        if (almacen == null) {
//           return null;
//        }
//        return almacen.getEstadisticos();
//    }

    /**
     * Devuelve todos las correlaciones como un colection.
     * OJO: devuelve la coleccion original del almacen, y no una copia de esta, por lo
     * que modificar esa coleccion es modificar el almacen.
     * @return
     */
//    public Collection<ResultadoCorrelacion> getCorrelaciones() {
//        if (almacen == null) {
//            return null;
//        }
//        return almacen.getCorrelaciones();
//    }

    /**
     * Devuelve el resultado estadistico asociado con el String que se le pasa.
     * @param estadistico
     * @return
     */
    public ResultadosEstadisticos getEstadistico(String estadistico) {
        if (almacen == null) {
           return null;
        }
        return almacen.getEstadistico(estadistico);
    }

    /**
     * Devuelve el resultado estadistico asociado con el String que se le pasa.
     * @param estadistico
     * @return
     */
    public ResultadoCorrelacion getCorrelacion(String correlacion) {
        if (almacen != null) {
            return almacen.getCorrelacion(correlacion);
        } else {
            return null;
        }
    }

    /**
     * Devuelve un array con los indices de la senhales que se estan monitorizando en este momento.
     * @return
     */
    @SuppressWarnings("unchecked")
    public int[] getSenalesMonitorizadas() {
        Object[] senales_object = this.mapeaCanalSenal.entrySet().toArray();
        int[] senales_monitorizadas = new int[senales_object.length];
//Necesario porque no se borran realmente los mapeos cuando se melimina un canal:
        //Si hay tres canales y eliminamos uno sigue habiendo tres objetos de mapeos.
        for (int i = 0; i < senales_monitorizadas.length &&
                     i < this.getNumeroSenalesMonitorizadas(); i++) {
            senales_monitorizadas[i] = (((Map.Entry<Integer,Integer>) (senales_object[i])).getValue()).intValue();
        }
        //     senales_monitorizadas[i] = ((Integer)((HashMap.Entry)(senales_object[i])).getValue()).intValue();
        return senales_monitorizadas;
    }

    /**
     * Devuelve un array con las seneles que se monitorizaron la ultima vez
     * @return
     */
    public int[] getSenalesQueSeMonitorizaronLaUltimaVez() {
        return senalesQueSeMonitorizaronLaUltimaVez;
    }

    /**
     * Modifica las senales que se monitoizaron la ultima vez. duvuelve null si no hay informacion.
     * @param _senales_que_se_monitorizaron_la_ultima_vez
     */
    public void setSenalesQueSeMonitorizaronLaUltimaVez(int[]
            _senales_que_se_monitorizaron_la_ultima_vez) {
        senalesQueSeMonitorizaronLaUltimaVez =
                _senales_que_se_monitorizaron_la_ultima_vez;
    }

    /**
     * Elimina los posibles ceros finales de todos las senhales del almacen y reajusta
     * el tamanho tanto del registro como del monitor.
     */
    public void eliminaCerosFinalesDelRegistro() {
        int numSenales = this.getNumeroSenales();
        int nuevaLongitudMaxima = 0;
        for (int i = 0; i < numSenales; i++) {
            float[] nuvosDatos = Remuestrea.elimina0Finales((float[])this.
                    getDatos(i));
            nuevaLongitudMaxima = Math.max(nuevaLongitudMaxima,
                                           nuvosDatos.length);
            this.setDatos(i, nuvosDatos);
            //Reajustamos tambien la posibilidad
            //Primero comprovamos si esta senhal tiene posibilidad
            if (!almacen.isPosAsociada(i)) {
                continue;
            }

            byte[] pos = (byte[])this.getPos(i);
            byte[] nuevaPos = new byte[nuvosDatos.length];
            for (int j = 0; j < nuevaPos.length; j++) {
                nuevaPos[j] = pos[j];
            }
            //Y almacenamos la nueva posibilidad
            almacen.setPos(i, nuevaPos);
        }
        //Y finalmente reajusamos la longitud maxima de la posibilidad total
        byte[] posTotal = this.getPosibilidadTotal();
        if (posTotal != null) {
            byte[] nuevaPosTotal = new byte[nuevaLongitudMaxima];
            for (int i = 0; i < nuevaPosTotal.length; i++) {
                nuevaPosTotal[i] = posTotal[i];
            }
            almacen.setPosibilidadTotal(nuevaPosTotal);
        }
        //Esto tambien cambia el tamanho del monitor
        this.setMaximaDuracionDelRegistro(nuevaLongitudMaxima);

    }

}
