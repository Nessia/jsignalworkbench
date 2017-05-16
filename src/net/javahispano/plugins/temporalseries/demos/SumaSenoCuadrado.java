package net.javahispano.plugins.temporalseries.demos;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

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
public class SumaSenoCuadrado extends TemporalSeriesAlgorithm {

    /**
     *
     */
    private static final long serialVersionUID = -2585588053053948289L;

    private static final Logger LOGGER = Logger.getLogger(SumaSenoCuadrado.class.getName());

    public SumaSenoCuadrado() {
        // Vacio
    }

    @Override
    public String getName() {
        return "Suma de seno y cuadrado";
    }

    @Override
    public void processTemporalSeries(SignalManager sm, List<TemporalSeries> signals) {
        Iterator<TemporalSeries> it = signals.iterator();
        if (signals.size() != 2) {
            LOGGER.severe("Error en el numero de senhales seleccionadas");
            return;
        }
        TemporalSeries seno = it.next();
        TemporalSeries cuadrado = it.next();
        TemporalSeries tmp;
        if (!seno.getName().toLowerCase().contains("seno")) {
            tmp = seno;
            seno = cuadrado;
            cuadrado = tmp;
        }
        for (int i = Math.min(seno.getMinIndex(), cuadrado.getMinIndex());
                     i < Math.max(seno.getMaxIndex(), cuadrado.getMaxIndex()); i++) {
            cuadrado.setValueAt(i, cuadrado.getValueAt(i) + seno.getValueAt(i));
        }
    }
}
