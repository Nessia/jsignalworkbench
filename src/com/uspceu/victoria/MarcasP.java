
package com.uspceu.victoria;

import com.uspceu.SimpleAlgorithm;
import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

public class MarcasP extends SimpleAlgorithm {
    private static final Logger LOGGER = Logger.getLogger(MarcasP.class.getName());


    @Override
    public void runAlgorithm (SignalManager manager, Signal signal, float[] datos,float freq ){
        List<MarkPlugin> latidos =  signal.getAllMarks();

        for (MarkPlugin latido : latidos) {

            int marca = (int)latido.getMarkTime();
            int tiempoIn = (int) ((marca - 220)-signal.getStart());
            int tiempoFin = (int) ((marca - 80)-signal.getStart());

            LOGGER.log(Level.INFO, "%s", Long.toString(latido.getMarkTime() - signal.getStart()));
            LOGGER.log(Level.INFO, "%s", Integer.toString(marca));
            LOGGER.log(Level.INFO, "%s", Integer.toString(tiempoIn));
            LOGGER.log(Level.INFO, "%s", Integer.toString(tiempoFin));

            DefaultIntervalMark mark = createIntervalMark((int)(tiempoIn*freq/1000), (int)(tiempoFin*freq/1000), signal);
            mark.setTitle("P");
            mark.setColor(Color.yellow);
            signal.addMark(mark);
        }
    }

    @Override
    public String getName() {
        return "OndaP";
    }

}
