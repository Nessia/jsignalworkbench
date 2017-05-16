package net.javahispano.testplugins;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

public class PruebaDeConcurrenciaParaSwing2 extends AlgorithmAdapter {
    /**
     *
     */
    private static final long serialVersionUID = -189478206134488330L;

    String[] nombres = {"1", "2", "3", "4*", "5", "6", "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30"};
    int i;

    public PruebaDeConcurrenciaParaSwing2() {
        // Vacio
    }

    @Override
    public String getName() {
        return "Concurrencia2";
    }

    @Override
    public void runAlgorithm(final SignalManager sm,
                             List<SignalIntervalProperties> signals,
                             AlgorithmRunner ar) {
        final Signal s = signals.get(0).getSignal();
        final float[] datos = s.getValues();

        for (i = 0; i < nombres.length; i++) {

            Signal nueva = new Signal(nombres[i], datos, s.getSRate(),
                                      s.getStart(),
                                      "magnitud");

            sm.addSignal(nueva);

            esperar(100);
        }

        for (i = 0; i < nombres.length; i++) {

            sm.setSignalVisibleRange(nombres[i], -5, 6);

            esperar(100);
        }

        for (i = 0; i < nombres.length; i++) {

            sm.setSignalVisible(nombres[i], false);

            esperar(100);
        }
        for (i = 0; i < nombres.length; i++) {

            sm.setSignalVisible(nombres[i], true);

            esperar(100);
        }

        for (i = 0; i < nombres.length; i++) {

            sm.removeSignal(nombres[i]);

            esperar(100);
        }

    }

    private void esperar(int tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
        }
    }


}
