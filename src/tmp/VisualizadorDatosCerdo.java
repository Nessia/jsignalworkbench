package tmp;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

import javax.swing.*;

import net.javahispano.jsignalwb.SignalManager;

import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.*;

import java.awt.*;


public class VisualizadorDatosCerdo extends AlgorithmAdapter {

    private boolean flujos = false, flujosDelRinhon = false, presionA = true, presionP = false,
    diuresis = true, flujosDelRinhonSuavizados = false, presionesASuavizadas = true,
    presionesPSuavizadas = false, flujosSuavizados = false;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        if (sm.getSignal("F. carótida suavizado") == null) {
            generarFlujos(sm, 200);
        }
        if (sm.getSignal("P. arterial Sist.") == null) {
            generarPresiones(sm, 200);
        }

        sm.hideAllSignals();
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setFrecuency(1);

        try {
            if (diuresis) {
                sm.setSignalVisible("Diuresis", true);
            }
            if (presionA) {
                sm.setSignalVisible("Presión arterial", true);
            }
            if (presionP) {
                sm.setSignalVisible("Presión pulmonar", true);

            }

            if (flujos) {
                sm.setSignalVisible("Flujo carótida", true);
                sm.setSignalVisible("Flujo riñón", true);
            }

            if (flujosDelRinhon) {
                sm.setSignalVisible("Flujo corteza", true);
                sm.setSignalVisible("Flujo médula", true);
            }
            if (presionesASuavizadas) {
                sm.setSignalVisible("P. arterial Sist.", true);
                sm.setSignalVisible("P. arterial Diast.", true);
                sm.setSignalVisible("P. arterial Media", true);
            }
            if (this.presionesPSuavizadas) {
                sm.setSignalVisible("P. pulmonar Sist.", true);
                sm.setSignalVisible("P. pulmonar Diast.", true);
                sm.setSignalVisible("P. pulmonar Media", true);

            }
            if (flujosSuavizados) {
                sm.setSignalVisible("F. carótida suavizado", true);
                sm.setSignalVisible("F. riñón suavizado", true);
            }
            if (flujosDelRinhonSuavizados) {
                sm.setSignalVisible("F. corteza suavizado", true);
                sm.setSignalVisible("F. médula suavizado", true);
            }

        } catch (SignalNotFoundException ex) {
            JOptionPane.showMessageDialog(js.getJSignalMonitorPanel(),
                                          "Error; alguna de las se�ales no tiene nombre correcto",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void generarPresiones(SignalManager sm, int ventana) {
        Signal signalOriginal = sm.getSignal("Presi�n arterial");
        float[] datosOriginales = signalOriginal.getValues();
        float[] datosNuevos = null;
        datosNuevos = calculaMaxMovil(datosOriginales, ventana);
        Signal nuevaSenhal = new Signal("P. arterial Sist.", datosNuevos, 100.0F / ventana, signalOriginal.getStart(),
                                        "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = calculaMinMovil(datosOriginales, ventana);
        nuevaSenhal = new Signal("P. arterial Diast.", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = new float[datosNuevos.length];
        float[] d = sm.getSignal("P. arterial Diast.").getValues();
        float[] s = sm.getSignal("P. arterial Sist.").getValues();
        for (int i = 0; i < d.length; i++) {
            datosNuevos[i] = d[i] + (s[i] - d[i]) / 3;
        }
        nuevaSenhal = new Signal("P. arterial Media", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        signalOriginal = sm.getSignal("Presi�n pulmonar");
        datosOriginales = signalOriginal.getValues();
        datosNuevos = calculaMaxMovil(datosOriginales, ventana);
        nuevaSenhal = new Signal("P. pulmonar Sist.", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = calculaMinMovil(datosOriginales, ventana);
        nuevaSenhal = new Signal("P. pulmonar Diast.", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = new float[datosNuevos.length];
        d = sm.getSignal("P. pulmonar Diast.").getValues();
        s = sm.getSignal("P. pulmonar Sist.").getValues();
        for (int i = 0; i < d.length; i++) {
            datosNuevos[i] = d[i] + (s[i] - d[i]) / 3;
        }
        nuevaSenhal = new Signal("P. pulmonar Media", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

    }


    static void generarFlujos(SignalManager sm, int ventana) {
        Signal signalOriginal = sm.getSignal("Flujo car�tida");

        float[] datosOriginales;
        float[] datosNuevos;
        Signal nuevaSignal;

        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = null;
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. car�tida suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(),
                                     "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

        signalOriginal = sm.getSignal("Flujo ri��n");
        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = null;
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. ri��n suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

        signalOriginal = sm.getSignal("Flujo corteza");
        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = null;
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. corteza suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(),
                                     "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

        signalOriginal = sm.getSignal("Flujo m�dula");
        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = null;
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. m�dula suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(),
                                     "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

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

    public static float[] calculaMaxMovil(float[] datos, int ventanaEnMuestras) {
        float[] datosValorMax = new float[datos.length / ventanaEnMuestras];
        for (int i = 0; i < datosValorMax.length; i++) {
            float tmp = 0;
            int inicio = Math.max(i * ventanaEnMuestras - ventanaEnMuestras / 2, 0);
            int fin = Math.min((i + 1) * ventanaEnMuestras + ventanaEnMuestras / 2, datos.length);
            for (int j = inicio; j < fin; j++) {
                tmp = Math.max(tmp, datos[j]);
            }
            datosValorMax[i] = tmp;

        }
        return datosValorMax;
    }

    public static float[] calculaMinMovil(float[] datos, int ventanaEnMuestras) {
        float[] datosValorMin = new float[datos.length / ventanaEnMuestras];
        for (int i = 0; i < datosValorMin.length; i++) {
            float tmp = 10000;
            int inicio = Math.max(i * ventanaEnMuestras - ventanaEnMuestras / 2, 0);
            int fin = Math.min((i + 1) * ventanaEnMuestras + ventanaEnMuestras / 2, datos.length);
            for (int j = inicio; j < fin; j++) {
                tmp = Math.min(tmp, datos[j]);
            }

            datosValorMin[i] = tmp;
        }
        return datosValorMin;
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return true;
    }

    @Override
    public void launchExecutionGUI(JSWBManager jswbManager) {
        JDialog f = new JDialog(JSWBManager.getParentWindow(), "Configurar visualización");
        f.setModal(true);
        PanelVisualizadorCerdo p = new PanelVisualizadorCerdo();
        p.setFlujos(flujos);
        p.setFlujosDelRinhon(this.flujosDelRinhon);
        p.setPresiones(presionA);
        p.setDiuresis(diuresis);
        p.setFlujosDelRinhonSuavizados(this.flujosDelRinhonSuavizados);
        p.setPresionesSuavizadas(this.presionesASuavizadas);
        p.setFlujosSuavizados(this.flujosSuavizados);
        p.setPresionP(this.presionP);
        p.setPresionesPSuavizadas(this.presionesPSuavizadas);
        p.setFrame(f);
        f.add(p);
        f.pack();
        f.setLocationRelativeTo(JSWBManager.getParentWindow());
        f.setVisible(true);
        flujos = p.isFlujos();
        flujosDelRinhon = p.isFlujosDelRinhon();
        presionA = p.isPresiones();
        diuresis = p.isDiuresis();
        flujosDelRinhonSuavizados = p.isFlujosDelRinhonSuavizados();
        presionesASuavizadas = p.isPresionesSuavizadas();
        flujosSuavizados = p.isFlujosSuavizados();
        this.presionesPSuavizadas = p.isPresionesPSuavizadas();
        this.presionP = p.isPresionP();
        this.runAlgorithm(JSWBManager.getSignalManager(), null, null);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        }
        if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    @Override
    public Icon getIcon() {
        return super.generateImageSimple("C", Color.blue);
    }

    @Override
    public String getName() {
        return "Visualizador de datos del cerdo";
    }

    @Override
    public String getDescription() {
        return "Visualizador de datos del cerdo";
    }

    @Override
    public String getShortDescription() {
        return "Visualizador de datos del cerdo";
    }
}

