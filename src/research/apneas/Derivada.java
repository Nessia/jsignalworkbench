package research.apneas;


import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

public class Derivada extends AlgorithmAdapter {

    @Override
    public String getName() {
        return "Derivada";
    }

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        float[] derivada = null;
        float[] datos;
        for (SignalIntervalProperties si : signals) {
            Signal senhalOriginal = si.getSignal();
            datos = senhalOriginal.getValues();
            derivada = new float[datos.length];
            for (int i = 1; i < datos.length; i++) {
               derivada[i] = datos[i] - datos[i - 1];
           }


        Signal senhalDerivada = new Signal(senhalOriginal.getName() + "'",
                       derivada,
                       senhalOriginal.getSRate(), senhalOriginal.getStart(), "");
        sm.addSignal(senhalDerivada);
        senhalDerivada.setVisibleRange(0, 10, 400);
        }

    }

}
