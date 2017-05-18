//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\DataIO\\CargarDatosMon.java

package es.usc.gsi.trace.importer.monitorizacion.dataio;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.monitorizacion.data.AlmacenDatos;

/**
 * Carga los datos referentes a una monitorizacion ya realizada. Peuede haber en
 * ellos anotaciones y maracas.
 */
class CargarDatosMon extends CargarDatos {

    private static final Logger LOGGER = Logger.getLogger(CargarDatosMon.class.getName());

    CargarDatosMon(String archivo) {
        super(archivo);
        cargaDatos();

    }

    private void cargaDatos() {
        ObjectInputStream in = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(archivo);
            in = new ObjectInputStream(fis);
            almacen = (AlmacenDatos) in.readObject();
            almacen.inicializaPartesNoSerializadas();
            float[][] tmp = (float[][]) almacen.getDatos();
            GestorIO.setNumSenales(tmp.length);
//            GestorIO.setNumDatos(tmp[0].length);
            String fecha_base = almacen.getFechaBase();
            if (fecha_base != null) {
                SamplesToDate.getInstancia().setFechaBase(fecha_base);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            almacen = null;
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (Exception e) {
                   LOGGER.log(Level.FINER, e.getMessage(), e);
                }
            }
            if(fis != null){
               try {
                  fis.close();
               } catch (Exception e) {
                 LOGGER.log(Level.FINER, e.getMessage(), e);
               }
            }
        }

    }

}
