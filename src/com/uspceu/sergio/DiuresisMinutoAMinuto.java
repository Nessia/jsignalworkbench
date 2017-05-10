package com.uspceu.sergio;

import com.uspceu.SimpleAlgorithm;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

/**
 *
 * @author Sergio
 */
public class DiuresisMinutoAMinuto extends SimpleAlgorithm  {

    @Override
    public String getName() {
           return "Calculo de la diuresis minuto a minuto";
    }

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {

        //minuto a minuto
        float[] newData = new float[datos.length];

        for (int i = 1; i < datos.length; i++) {

          newData[i] = datos[i]-datos[i-1];

          if(datos[i]<datos[i-1]){
            newData[i] = 0;
          }
        }

        Signal square = new Signal("Minuto a minuto de " + signal.getName(),
                              newData, fs , signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
    }
}
