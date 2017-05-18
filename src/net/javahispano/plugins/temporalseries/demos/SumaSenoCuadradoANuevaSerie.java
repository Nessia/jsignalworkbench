/*
 * NewClass.java
 *
 * Created on 21-oct-2007, 18:22:30
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class SumaSenoCuadradoANuevaSerie extends TemporalSeriesAlgorithm {


    private static final Logger LOGGER = Logger.getLogger(SumaSenoCuadradoANuevaSerie.class.getName());

    public SumaSenoCuadradoANuevaSerie() {
        // Vacio
    }

    @Override
    public String getName() {
        return "Suma de seno y cuadrado2";
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

        TemporalSeries nuevaSerie = new TemporalSeries("Suma", seno);
        int inicio = Math.min(seno.getMinIndex(), cuadrado.getMinIndex());
        int fin = Math.max(seno.getMaxIndex(), cuadrado.getMaxIndex());
        for (int i = inicio; i < fin; i++) {
            nuevaSerie.setValueAt(i, cuadrado.getValueAt(i) + seno.getValueAt(i));
        }
        sm.addSignal(nuevaSerie);
        nuevaSerie.adjustVisibleRange();
    }
}
