package net.javahispano.jsignalwb;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: a
 * Date: 01-dic-2008
 * Time: 15:54:17
 */
public class EnvironmentConfiguration {


    private static final Logger LOGGER = Logger.getLogger(EnvironmentConfiguration.class.getName());

    private static final String PROP_USER_HOME = "user.home";
    private static final String DIRECTORIO = "/.HerrmientaDeMonitorizacion";
    private static final String ARCHIVO = "/config.txt";
    private static final String PATH_JF_ABRIR_FICHEROS = "PATH_JF_ABRIR_FICHEROS";
    private static final String DEFAULT_LOADER = "Default_Loader";
    private static final String RECORDAR_PATH_FICHEROS = "Recordar_Path_Ficheros";
    /*private final static String PAIS = "Pais", IDIOMA = "Idioma", NUMERO_DE_DIGITOS = "Numero_de_digitos_decimales",
    , PATH_JF_ABRIR_BC = "PATH_JF_ABRIR_BC",
    DIVIDER_LOCATION = "Posicon_del_divisor", X_LOCATION_MAIN_FRAME = "Posicion_x_del_frame",
    Y_LOCATION_MAIN_FRAME = "Posicion_y_del_frame", ALTO_MAIN_FRAME = "Alto_del_frame",
    ANCHO_MAIN_FRAME = "Ancho_del_frame", RECORDAR_TAMANO_MAIN_FRAME = "Recordar_Tamano_MainFrame",
    RECORDAR_POSICION_MAIN_FRAME = "Recordar_Posicion_Main_Frame",
    RECORDAR_PATH_BC = "Recordar_Path_BC", RECORDAR_POSICION_SLIDER = "Recordar_Posicion_Slider",
    RECORDAR_VISTA_FICHEROS = "Recordar_Vista_Ficheros", ULTIMO_ARCHIVO_CARGADO = "Ultimo_archivo_cargado",
    RECORDAR_ULTIMO_ARCHIVO_CARGADO = "Recordar_ultimo_archivo_cargado", ANCHO_EDITOR = "Ancho_del_editor",
    RECORDAR_TAMANO_EDITOR = "Recordar_Tamano_Editor", RECORDAR_POSICION_EDITOR = "Recordar_Posicion_Del_EditOR",
    X_LOCATION_EDITOR = "Posicion_x_del_Editor", Y_LOCATION_EDITOR = "Posicion_y_del_Editor",
    ALTO_EDITOR = "Alto_del_Editor", LOOK_AND_FEEL = "Look_and_Feel", DESPLAZAMENTO = "desplazamiento_temporal";
    */
    private Properties propiedades;
    private final String rutaDelArchivo;
    //private String desplazamiento;
    private static EnvironmentConfiguration environmentConfiguration;

    private EnvironmentConfiguration() {
        String home = System.getProperty(PROP_USER_HOME);
        rutaDelArchivo = System.getProperty(PROP_USER_HOME) + "/.JSignalWorkBench" + ARCHIVO;
        propiedades = new Properties();
        File archivo = new File(rutaDelArchivo);
        try {
            if (!archivo.exists()) {
                Properties tmp = new Properties();
                tmp.setProperty(PATH_JF_ABRIR_FICHEROS, System.getProperty(PROP_USER_HOME));
                File directorio_file = new File(home + DIRECTORIO);
                directorio_file.mkdir();
                FileOutputStream out = new FileOutputStream(archivo);
                tmp.store(out, "Configuracion de JSignalWorkBench");
                out.close();
            }
            FileInputStream in = new FileInputStream(archivo);
            propiedades.load(in);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Fallo en al configuracion", ex);
        }

    }

    public static EnvironmentConfiguration getInstancia() {
        if (environmentConfiguration == null) {
            environmentConfiguration = new EnvironmentConfiguration();
        }
        return environmentConfiguration;
    }


    /*
       /**
      * Almacena el path del JF de abrir los ficheros
      * @param path
      */
     public void setDefaultLoader(String defaultLoader) {
         propiedades.setProperty(DEFAULT_LOADER, defaultLoader);
     }

    /**
     * Devuleveel path del JF de abrir los ficheros
     * @return path
     */
    public String getDefaultLoader() {
        String defaultLoader = propiedades.getProperty(DEFAULT_LOADER);
        if (defaultLoader == null) {
            defaultLoader = "HRVLoader";
        }
        return defaultLoader;
    }

    /*
     /**
      * Almacena el path del JF de abrir los ficheros
      * @param path
      */
     public void setPathJFFicheros(String path) {
         propiedades.setProperty(PATH_JF_ABRIR_FICHEROS, path);
     }

    /**
     * Devuleveel path del JF de abrir los ficheros
     * @return path
     */
    public String getPathJFFicheros() {
        return propiedades.getProperty(PATH_JF_ABRIR_FICHEROS);
    }


    /**
     * Almacena a disco el contenido actual del archivo de configuracion.
     */
    public void almacenaADisco() {
        try {
            File archivo = new File(rutaDelArchivo);
            FileOutputStream out = new FileOutputStream(archivo);
            propiedades.store(out, "Configuracion de JSignalWorkbench");
        } catch (IOException ex) {
           LOGGER.log(Level.SEVERE, "Fallo en al configuracion", ex);
        }
    }


    /**
     * Indica si se debe o no recordar el path de los ficheros.
     * @return
     */
    public boolean getRecordarPathFicheros() {
        String recordar_path_ficheros = propiedades.getProperty(RECORDAR_PATH_FICHEROS);
        if (recordar_path_ficheros == null) {
            propiedades.setProperty(RECORDAR_PATH_FICHEROS, "true");
            this.almacenaADisco();
            return false;
        }
        return Boolean.parseBoolean(recordar_path_ficheros);
    }

    /**
     * Modifica el boolean que indica si hay que recordar o no el path de los ficheros.
     * @param recordarPathFicheros
     */
    public void setRecordarPathFicheros(boolean recordarPathFicheros) {
        propiedades.setProperty(RECORDAR_PATH_FICHEROS, Boolean.toString(recordarPathFicheros));
    }

}
