/*
 * SaverRunner.java
 *
 * Created on 11 de septiembre de 2007, 18:44
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Saver;

/**
 *
 * @author Roman Segador
 */
public class SaverRunner extends SwingWorker<Boolean, Void> {

    private static final Logger LOGGER = Logger.getLogger(SaverRunner.class.getName());

    private Saver saver;
    private File file;

    public SaverRunner(Saver saver, File file) {
        this.saver = saver;
        this.file = file;
    }

    protected Boolean doInBackground() throws Exception {
        try {
            saver.save(file);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                                          "Error ejecutando el plugin " +
                                          saver.getName() + "\n" + ex.getMessage());
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
                                              saver.getName() + " version " +
                                              saver.getPluginVersion(),
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
