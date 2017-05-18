/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.javahispano.plugins.temporalseries.demos;

import java.util.List;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.plugins.temporalseries.TemporalSeries;
import net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm;

/**
 *
 * @author b
 */
public class TransformacionLineal extends TemporalSeriesAlgorithm {


    private static final Logger LOGGER = Logger.getLogger(TransformacionLineal.class.getName());

    /*
     * Atributos
     */

    private int b;
    private float a;


    /*
     * Métodos
     */

    /*
     * (non-Javadoc)
     * @see net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm#processTemporalSeries(net.javahispano.jsignalwb.SignalManager, java.util.List)
     */
    @Override
    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        if (signals.size() != 1) {
            LOGGER.severe(" Error en el numero de senhales seleccionadas");
            return;
        }
        TemporalSeries senal = signals.get(0);
        float[] datos = senal.getValues();
        for (int i = 0; i < datos.length; i++) {
            datos[i] = a * datos[i] + b;
        }
    }

    @Override
    public String getName() {
        return "Transformacion Lineal";
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        TransformacionLinealGUI gui = new TransformacionLinealGUI(null, true);
        gui.setVisible(true);
        a = gui.getA();
        b = gui.getB();
    }
}
