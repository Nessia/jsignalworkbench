/*
 * LoaderRunner.java
 *
 * Created on 11 de septiembre de 2007, 5:19
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Loader;

/**
 *
 * @author Roman Segador
 */
public class LoaderRunner extends SwingWorker<Boolean, Void> {

    private static final Logger LOGGER = Logger.getLogger(LoaderRunner.class.getName());

    private Loader loader;
    private File file;

    public LoaderRunner(Loader loader, File file) {
        this.loader = loader;
        this.file = file;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            loader.load(file);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(
                    JSWBManager.getParentWindow(),
                    "Error loading..." + ex.getMessage());
        }
        return Boolean.valueOf(true);
    }

    @Override
    protected void done() {
        //super.done();
        Boolean end = Boolean.valueOf(false);
        try {
            end = get();
        } catch (Exception ex) {
            if (!isCancelled()) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                JOptionPane.showMessageDialog(null,
                                              "Ha sucedido un error al ejecutar el cargador " +
                                              loader.getName() + " version " +
                                              loader.getPluginVersion(),
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

        }
        /*if (algorithm.hasResultsGUI()) {
            algorithm.launchResultsGUI(jswbManager);
                 }*/
        if (end.booleanValue()) {
            JSWBManager.getJSWBManagerInstance().refreshJSM(false);
        }
    }

}
