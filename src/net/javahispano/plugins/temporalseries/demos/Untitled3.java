package net.javahispano.plugins.temporalseries.demos;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;

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
// TODO deberia extender a TemporalSeriesAlgorithm ?? @vanesa
public class Untitled3 extends AlgorithmAdapter {

    public Untitled3() {
        super();
    }

    @Override
    public String getName() {
        return "aa";
    }

    @Override
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals) {
        Signal s = signals.get(0).getSignal();
        float[] f = s.getValues();
        for (int i = 0; i < f.length; i++) {
            f[i] = 200 * f[i];
        }
    }

}
