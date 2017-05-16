package es.usc.gsi.trace.importer;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;

import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.monitorizacion.data.GestorDatos;
import es.usc.gsi.trace.importer.monitorizacion.dataio.GestorIO;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.LoaderAdapter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class TraceImporter extends LoaderAdapter {

    /**
     *
     */
    private static final long serialVersionUID = -6570536102428776316L;
    private static final Logger LOGGER = Logger.getLogger(TraceImporter.class.getName());


    @Override
    public List<String> getAvalaibleExtensions() {
        List<String> l = new ArrayList<String>();
        l.add("mon");
        return l;
    }

    @Override
    public String getDescription() {
        return "permite importar las senhales, junto con sus nombres, frecuencias de muestreo, etc. y las anotaciones contenidas en archivos de TRACE";
    }

    @Override
    public Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("trace.gif"));
    }

    @Override
    public String getName() {
        return "TRACE importer";
    }

    @Override
    public String getPluginVersion() {
        return "0.5";
    }

    @Override
    public String getShortDescription() {
        return "TRACE importer";
    }

    @Override
    public boolean load(File file) throws
            Exception {
        //GestorIO gestorIO = GestorIO.getGestorIO();
        String name = file.getPath();
        boolean hubo_exito = GestorIO.cargarDatos(name, true);
        //int num_datos = gestor_io.getNumDatos();
        if (name.endsWith(".mon") && hubo_exito) {
            GestorDatos gestorDatos = GestorDatos.getInstancia();
            gestorDatos.setArchivo(name);
            gestorDatos.setEstaGuardado(true);
            gestorDatos.setTieneArchivoAsociado(true);
            int numeroSenales = gestorDatos.getNumeroSenales();
            SamplesToDate.getInstancia().setFechaBase(new Date(0));
            long fecha = obtenerFecha(gestorDatos.getFechaBase());

            //la fecha que le ponemos al registro
            LOGGER.log(Level.INFO, "%s", (new Date(fecha)).toString());

            for (int i = 0; i < numeroSenales; i++) {
                String unidades = gestorDatos.getAlmacen().getLeyenda(i);
                JSWBManager.getSignalManager().addSignal(gestorDatos.getNombreSenal(i),
                        (float[]) gestorDatos.getDatos(i),
                        gestorDatos.getFsSenal(i), fecha, unidades);
            }
            return true;
        }
        return false;
    }


    private long obtenerFecha(String fechaBaseConversor) {
        StringTokenizer tk = new StringTokenizer(fechaBaseConversor);
        String horaMinSeg = tk.nextToken();
        String diaMesAno = tk.nextToken();
        SamplesToDate.getInstancia().setFechaBase(new Date(0));
        tk = new StringTokenizer(horaMinSeg, ":", false);
        int hora = 0;
        int minutos = 0;
        int segundos = 0;
        try {
            hora = Integer.parseInt(tk.nextToken());
            minutos = Integer.parseInt(tk.nextToken());
            segundos = Integer.parseInt(tk.nextToken());
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return 0;
        }
        tk = new StringTokenizer(diaMesAno, "/", false);
        int ano = 0;
        int mes = 0;
        int dia = 0;
        try {
            dia = Integer.parseInt(tk.nextToken());
            mes = Integer.parseInt(tk.nextToken());
            ano = Integer.parseInt(tk.nextToken());
        } catch (NumberFormatException ex) {
           LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return 0;
        }

        Calendar d = new GregorianCalendar(ano, mes, dia, hora, minutos,
                                           segundos);
        return d.getTime().getTime();
    }

}
