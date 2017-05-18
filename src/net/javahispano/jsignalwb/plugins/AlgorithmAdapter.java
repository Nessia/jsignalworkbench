package net.javahispano.jsignalwb.plugins;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultAlgorithmConfiguration;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/*
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public abstract class AlgorithmAdapter extends PluginAdapter implements Algorithm {

    private boolean executionCanceled = false;

    /**
     * Por defecto no proporciona interfaz propia de ejecucion.
     *
     * @return boolean
     */
    @Override
    public boolean hasOwnExecutionGUI() {
        return false;
    }

    @Override
    public void launchExecutionGUI(JSWBManager jswbManager) {
        JDialog conf = new JDialog(JSWBManager.getParentWindow(), "Execution GUI");

        conf.setModal(true);
        JPanel jPane = new DefaultAlgorithmConfiguration(this,
                jswbManager, conf);
        conf.getContentPane().add(jPane);
        conf.setSize(jPane.getPreferredSize());
        conf.setResizable(false);
        conf.setLocationRelativeTo(conf.getParent());
        conf.setVisible(true);
    }

    /**
     * Por defecto no proporciona interfaz de usuario.
     *
     * @return boolean
     */
    @Override
    public boolean hasResultsGUI() {
        return false;
    }

    @Override
    public void launchResultsGUI(JSWBManager jswbManager) {
        throw new UnsupportedOperationException("No results GUI defined");
    }

    /**
     * El usuario que implemente esta clase puede elegir sobre escribir solo este metodo para
     * procesar su senhal. A este metodo se le pasa el array de datos
     * de la primera senhal que haya seleccionado el usuario.
     *
     * @param sm SignalManager
     * @param signals List
     */
    public void runAlgorithm(SignalManager sm,
                             float[] signal) {
    }

    @Override
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
                             AlgorithmRunner ar) {
        if (signals.size() == 1) {
            this.runAlgorithm(sm, signals.get(0).getSignal().getValues());
        }

    }

    /**
     * Devuelve 0; este valor indica que el algoritmo no tiene un numero maximo
     * de senhales/intervalos a procesar.
     *
     * @return int
     */
    @Override
    public int numberOfSignalsNeeded() {
        return 0;
    }

    @Override
    public void cancelExecution() {
        executionCanceled = true;
    }

    /**
     * Devuelve true si la ejecucion del algoritmo ha sido cancelada.
     *
     * @return boolean
     */
    public boolean isExecutionCanceled() {
        return executionCanceled;
    }

    /**
     * Por defecto los algoritmo se muestran en el menu de plugins y en la barra
     * de tareas. Este comportamiento puede cambiarse sobreescribiendo este
     * metodo.
     *
     * @param gUIPositions GUIPositions
     * @return boolean
     */
    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU;
    }

}
