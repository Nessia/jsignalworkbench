/*
 * addSinPlugin.java
 *
 * Created on 13 de octubre de 2007, 12:12
 */

package net.javahispano.testplugins;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.defaults.AxesGridPlugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
 *
 * @author Roman Segador
 */
public class AddSinPlugin extends AlgorithmAdapter {

    public AddSinPlugin() {
        // Vacio
    }

    @Override
    public String getName() {
        return "addSinPlugin";
    }

    @Override
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
                             AlgorithmRunner ar) {
        float[] data = new float[5000];
        for (int i = -2500; i < 2500; i++) {
            data[i + 2500] = (float) Math.sin(i);
        }
        Signal s = new Signal(Long.toString(System.currentTimeMillis()), data);
        AxesGridPlugin grid = new AxesGridPlugin();
        grid.setDistance(25000);
        grid.setYAxePosition(s.getStart() + (long) (2500000 * s.getSRate()));
        s.setGrid(grid);
        sm.addSignal(s);
    }
}
