package es.usc.gsi.trace.importer.jsignalmonold;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Esta clase es util a la hora de averiguar la fecha a la cual corresponde una
 * determinada muestra. Esta clase se ha disenhado mediante el patron Singleton,
 * se configura pasandole una fecha de nase y probee metodos para saber dada una
 * frecuencia de muestreo a que fecha corresponde una muestra, o a que muestra corresponde una fecha.
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */

public class SamplesToDate {


    private static final Logger LOGGER = Logger.getLogger(SamplesToDate.class.getName());

//  static ResourceBundle res =ResourceBundle.getBundle("pintar.i18n.Res",Configuracion.localidad);
    private static SamplesToDate instancia;
    private SimpleDateFormat parser_fecha_completa;
    private Date fecha_base;

    private SamplesToDate() throws Exception {
//        if (isInstancia) {
//            throw new Exception("Only one instance allowed");
//        }
//        isInstancia = true;
        parser_fecha_completa = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    }

    public void setFechaBase(Date _fecha) {
        fecha_base = _fecha;

    }

    /**
     * Data una fecha la toma como fecha base para todos os calculos que se le pidan
     * a continuacion: es la fecha donde se empieza a monitorizar.
     * Su argumento debe ser una fecha completa.
     * @return true si no hay fallos en la fecha, false en caso contrario
     */
    public boolean setFechaBase(String _fecha) {
        try {
            fecha_base = parser_fecha_completa.parse(_fecha);
            return true;
        } catch (ParseException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return false;
        }
    }

    public String getFechaBase() {
        return parser_fecha_completa.format(fecha_base);
    }



    /**
     * @return GestorDatos
     */
    public static SamplesToDate getInstancia() {
        if (instancia != null) {
            return instancia;
        }
        try {
            SamplesToDate.instancia = new SamplesToDate();
            return instancia;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return null;
        }
    }

}
