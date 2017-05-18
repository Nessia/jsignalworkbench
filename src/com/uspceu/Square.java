package com.uspceu;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.MarkPlugin;

class Square extends SimpleAlgorithm{
    private static final Logger LOGGER = Logger.getLogger(Square.class.getName());

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal,
                             float[] datos, float samplingFrquency) {
       for (int i = 0; i < datos.length; i++) {
           datos[i] = datos[i]*datos[i];
       }
       List<MarkPlugin> latidos = signal.getAllMarks();
       for (MarkPlugin latido : latidos) {
           LOGGER.log(Level.INFO, "Duracion %s", Long.toString(latido.getMarkTime()- signal.getStart()));
       }
    }

    @Override
    public String getName() {
        return "Cuadrado";
    }
}
