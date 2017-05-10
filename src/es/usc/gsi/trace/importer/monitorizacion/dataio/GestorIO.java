//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\dataIO\\GestorIO.java

package es.usc.gsi.trace.importer.monitorizacion.dataio;

import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;
import es.usc.gsi.trace.importer.monitorizacion.data.*;

import org.jdom.JDOMException;

public class GestorIO {


    private static final Logger LOGGER = Logger.getLogger(GestorIO.class.getName());

    private static GestorIO instancia;
    //private static String fichero;
    private static int numDatos;
    private static int numSenales;

    //private CargarDatos cargador;

    /**
     * private default constructor
     * @throws SingletonException
     */
    private GestorIO() /*throws Exception*/ {
//        if (instanceFlagForGestorIO) {
//            throw new Exception("Only one instance allowed");
//        }
//        instanceFlagForGestorIO = true;
    }

    /**
     *
     * @param archivo
     * @param macacar_datos_antiguos si es true machaca los datos antiguos, si no los anhade.
     * @return
     */
    public static boolean cargarDatos(String archivo, boolean machacar_datos_antiguos) {
        AlmacenDatos almacen = null;
        CargarDatos cargador = null;
        if (archivo.endsWith(".txt") || archivo.endsWith(".picos")) {
            cargador = new CargarDatosTxt(archivo);
            float[][] datos = cargador.getDatos();
            byte[][] pos = cargador.getPos();
            SortedSet<Mark>[] marcas = cargador.getMarcas();
            SortedSet<Annotation> anotaciones = cargador.getAnotaciones();

            numDatos = datos[0].length;
            numSenales = datos.length;
            //+1 para la posibilidad global
            float[][] rango = new float[numSenales + 1][2];
            for (int i = 0; i < numSenales + 1; i++) {
                rango[i][0] = 0;
                rango[i][1] = 100;
            }

            //if (datos != null) {
                almacen = new AlmacenDatosFloat(datos, pos, anotaciones, marcas);
                almacen.setRango(rango);
            //}
        } else {
            //Por defecto intentamos cargar XML
            try {
                cargador = new CargarDatosXML(archivo);
            }
            //Pero si no va nos volvemos al serialismo
            catch (JDOMException ex) {
                //System.out.println("Exception " + ex.getMessage());
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                cargador = new CargarDatosMon(archivo);
            }
            almacen = cargador.getAlmacen();
        }

        if (almacen != null) {
            //Si hay que machacar => machacamos
            if (machacar_datos_antiguos) {
                GestorDatos.getInstancia().setAlmacen(almacen);
                return true;
            }
            //Si no anhado los datos al archivo actual
            else {
                float[][] datos = (float[][]) almacen.getDatos();
                String[] nombres_Vila = {"VLF", "LF", "HF", "HF/LF", "FC"};
                GestorDatos gestor_fdatos = GestorDatos.getInstancia();
                //Si carhgo el archivo de Vila
                if (archivo.endsWith(".picos")) {
                    for (int i = 0; i < datos.length && i < nombres_Vila.length;
                                 i++) {
                        gestor_fdatos.anhadeSenhal(datos[i], nombres_Vila[i], "",
                                "",
                                almacen.getFs(i),
                                almacen.getRango(i));
                    }

                }
                //Si no se cargan sin nombre
                else {
                    for (int i = 0; i < datos.length; i++) {
                        gestor_fdatos.anhadeSenhal(datos[i],
                                almacen.getNombreSenal(i),
                                almacen.getLeyenda(i),
                                "",
                                almacen.getFs(i),
                                almacen.getRango(i));
                    }
                }

                return true;
            }

        }
        //Si datos = null hubo un error al leer
        //@todo: quizas debiera resposabilizarse de esto otro
        else {
            JOptionPane.showOptionDialog(null, "Error en el formato", "ERROR",
                                         JOptionPane.DEFAULT_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return false;
        }
    }

    /**
     * @param fichero
     * @return GestorIO
     */
    public synchronized static GestorIO getGestorIO() {
       if(GestorIO.instancia == null){
          try {
             GestorIO.instancia = new GestorIO();
         } catch (Exception ex) {
             LOGGER.log(Level.WARNING, ex.getMessage(), ex);
             return null;
         }
       }
       return instancia;
    }

    public static int getNumDatos() {
        return numDatos;
    }

    public static void setNumDatos(int _num_datos) {
        numDatos = _num_datos;
    }

    public static int getNumSenales() {
        return numSenales;
    }

    public static void setNumSenales(int _num_senales) {
        numSenales = _num_senales;
    }


}
