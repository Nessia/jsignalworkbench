package es.usc.gsi.conversordatosmit;

import java.io.File;

import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.*;

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
public class ConversorDatosMITPlugin extends PluginAdapter implements GenericPlugin {


    /*
     * Atributos
     */


    private String ultimoDirectorioAbierto = null;

    /*
     * Constructor
     */

    public ConversorDatosMITPlugin() {
       //Empty
    }

    /*
     * Métodos
     */

    @Override
    public String getDataToSave() {
        return ultimoDirectorioAbierto;
    }

    @Override
    public String getDescription() {
        return "Permite importar senhales de registros MIT-BIH. Importa nombre de senhales, frecuencias de muestreo, fecha de comienzo del registro, etc.";
    }

    @Override
    public Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("MIT.gif"));
    }

    /**
     * Proporciona el hombre del plugin.
     *
     * @return Nombre del plugin
     */
    @Override
    public String getName() {
        return "Conversor datos MIT-BIH";
    }

    /**
     * Devuelve la version del plugin.
     *
     * @return Version del plugin
     */
    @Override
    public String getPluginVersion() {
        return "0.5";
    }

    /**
     * Devuelve una de extincion textual corta sobre la funcionalidad del
     * plugin.
     *
     * @return descripcion textual corta
     */
    @Override
    public String getShortDescription() {
        return "Importa registros MIT-BIH";
    }

    /**
     * Devuelve ciertos y el plugin tiene datos se desea que el entorno
     * guarde y le devuelva la proxima vez que se ejecute la herramienta.
     *
     * @return ciertos y el entorno debe guardar de datos, falso en caso
     *   contrario.
     */
    @Override
    public boolean hasDataToSave() {
        return true;
    }

    /**
     * Indica si el plugin tiene una interfaz grafica propia para
     * configurarse.
     *
     * @return devuelve cierto si tiene interfaz y falso en caso contrario
     */
    @Override
    public boolean hasOwnConfigureGUI() {
        return false;
    }


    /**
     * Este metodo es invocado por el entorno al cargar el plugin para
     * pasarle una cadena de caracteres con los datos que el plugin le pidio
     * que almacenarse en la ultima ejecucion.
     *
     * @param data datos que el plugin pidio al entorno que almacenarse en
     *   la ultima ejecucion
     */
    @Override
    public void setSavedData(String data) {
        ultimoDirectorioAbierto = data;
    }

    @Override
    public void launch(JSWBManager jswbManager) {
        FrameConversorMIT f = new FrameConversorMIT(JSWBManager.getParentWindow()/*, jswbManager*/);
        if (this.ultimoDirectorioAbierto != null) {

            File directorioViejo = new File(this.ultimoDirectorioAbierto);
            if (directorioViejo.exists()) {
                f.setArchivoAbierto(directorioViejo);
            }
        }
        f.setVisible(true);
        //obtenemos el ltimo directorio
        File directorio = f.getArchivoAbierto();
        if (directorio != null) {
            this.ultimoDirectorioAbierto = directorio.getAbsolutePath();
        }

    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return (gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR) ;
    }

}
