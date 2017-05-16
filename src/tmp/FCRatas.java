package tmp;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

public class FCRatas extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 5573874806873363991L;
    private int limiteDeteccionPicos = 5;
    private boolean debug = false;
    private int distanciaMinEntreLatidosEnMuestras = 15;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        Signal pa = sm.getSignal("ULF");
        Signal derivadaSignal = derivada(sm, pa, "Derivada");
        Signal picosSignal = quedarseSoloConLosPicos(sm, derivadaSignal);
        boolean[] inicioCadaPico = identificarIniciosPicos(picosSignal);

        int posicionPrimerPico = 0;
        posicionPrimerPico = encontrarElPrimerPico(inicioCadaPico);
        Signal fcS = generarFC(sm,pa,inicioCadaPico,posicionPrimerPico);

        Signal fcCada2Seg = new Signal("FC",calculaMediaMovil(fcS.getValues(), 200),
                            0.5F,pa.getStart(), "lat/min");
        fcCada2Seg.adjustVisibleRange();
            sm.addSignal(fcCada2Seg);

    }


    public static float[] calculaMediaMovil(float[] datos, int ventanaEnMuestras) {
        float[] datosDelValoMedio = new float[datos.length / ventanaEnMuestras];
        for (int i = 0; i < datosDelValoMedio.length; i++) {
            float tmp = 0;

            int inicio = Math.max(i * ventanaEnMuestras - 1 * ventanaEnMuestras, 0);
            int fin = Math.min((i + 1) * ventanaEnMuestras + 1 * ventanaEnMuestras, datos.length);

            for (int j = inicio; j < fin; j++) {
                tmp = tmp + datos[j];
            }
            tmp = tmp / (1 * (fin-inicio));
            datosDelValoMedio[i] = tmp;
        }
        return datosDelValoMedio;
    }


    private Signal generarFC(SignalManager sm, Signal pa, boolean[] inicioCadaPico, int posicionPrimerPico) {
        float[] fc = new float[inicioCadaPico.length];
        int inicio = posicionPrimerPico;
        for (int j = inicio + distanciaMinEntreLatidosEnMuestras; j < inicioCadaPico.length; j++) {
            if (inicioCadaPico[j]) {
                for (int k = inicio; k < j; k++) {
                    fc[k] = 60.0F / ((j - inicio) / pa.getSRate());
                }
                inicio = j;
            }
        }
        Signal fcSignal = new Signal("FC100", fc, pa.getSRate(), pa.getStart(), "");
        if (debug) {
            fcSignal.adjustVisibleRange();
            sm.addSignal(fcSignal);
        }
        return fcSignal;
    }

    private int encontrarElPrimerPico(boolean[] inicioCadaPico) {
        int posicionPrimerPico = 0;
        for (int i = 0; i < inicioCadaPico.length; i++) {
            if (inicioCadaPico[i]) {
                posicionPrimerPico = i;
                break;
            }
        }
        return posicionPrimerPico;
    }

    private boolean[] identificarIniciosPicos(Signal picosSignal) {
        float[] picos = picosSignal.getValues();
        boolean[] inicio = new boolean[picos.length];
        Arrays.fill(inicio, false);

        for (int i = 0; i < picos.length - 3; i++) {
            if (picos[i] > 0) {
                inicio[i] = true;
                i += distanciaMinEntreLatidosEnMuestras;
            }
        }
        return inicio;
    }


    private Signal derivada(SignalManager sm, Signal signal, String name) {
        float[] p = signal.getValues();
        float[] derivada = new float[p.length];
        for (int i = 0; i < p.length - 1; i++) {
            derivada[i] = p[i + 1] - p[i];
        }
        Signal d = new Signal(name, derivada, signal.getSRate(), signal.getStart(), "");
        if (debug) {
            d.adjustVisibleRange();
            sm.addSignal(d);
        }
        return d;
    }

    private Signal quedarseSoloConLosPicos(SignalManager sm, Signal derivadaSignal) {
        float[] derivadaValues = derivadaSignal.getValues();

        float[] picos = new float[derivadaValues.length];
        for (int i = 0; i < derivadaValues.length - 1; i++) {
            if (derivadaValues[i] < limiteDeteccionPicos) {
                picos[i] = 0;
            } else {
                picos[i] = derivadaValues[i];
            }
        }
        Signal derivadaCeros = new Signal("Picos Derivada", picos, derivadaSignal.getSRate(),
                                          derivadaSignal.getStart(), "");
        if (debug) {
            derivadaCeros.adjustVisibleRange();
            sm.addSignal(derivadaCeros);
        }

        return derivadaCeros;
    }

    @Override
    public void launchExecutionGUI(JSWBManager jswbManager) {
        this.runAlgorithm(JSWBManager.getSignalManager(), null, null);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public Icon getIcon() {
        return super.generateImageSimple("PR", Color.blue);
    }

    @Override
    public String getName() {
        return "Frecuencia cardiaca de las ratas";
    }

    @Override
    public String getDescription() {
        return getName();
    }

    @Override
    public String getShortDescription() {
        return getName();
    }
}
