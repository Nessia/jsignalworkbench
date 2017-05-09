package net.javahispano.plugins.temporalseries.demos;

import java.util.Iterator;
import java.util.List;

import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.plugins.temporalseries.TemporalSeries;
import net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm;

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
public class Untitled1 extends TemporalSeriesAlgorithm {

    public Untitled1() {
        // Vacio
    }

    @Override
    public String getName() {
        return "Ejemplo";
    }

    /*
     * (non-Javadoc)
     * @see net.javahispano.plugins.temporalseries.TemporalSeriesAlgorithm#processTemporalSeries(net.javahispano.jsignalwb.SignalManager, java.util.List)
     */
    @Override
    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        Iterator<TemporalSeries> it = signals.iterator();
        TemporalSeries s = it.next();
        TemporalSeries s2 = it.next();
        TemporalSeries t;
        if (!s.getName().equals("5")) {
            t = s;
            s = s2;
            s2 = t;
        }

        int m = Math.min(s.getMinIndex(), s2.getMinIndex());
        int M = Math.max(s.getMaxIndex(), s2.getMaxIndex());
        for (int i = m;
                     i < M; i++) {
            s2.setValueAt(i, s2.getValueAt(i) + s.getValueAt(i));
        }
    }
}
