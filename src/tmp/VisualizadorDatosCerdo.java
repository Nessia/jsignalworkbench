package tmp;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

import javax.swing.*;

import net.javahispano.jsignalwb.SignalManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.*;

import java.awt.*;


public class VisualizadorDatosCerdo extends AlgorithmAdapter {


    private static final Logger LOGGER = Logger.getLogger(VisualizadorDatosCerdo.class.getName());

    private static final String TITULO = "Visualizador de datos del cerdo";
    private static final String FLUJO_CAROTIDA_SUAVIAZADO = "F. carótida suavizado";
    private static final String PRESION_ARTERIAL_SISTOLICA = "P. arterial Sist.";
    private static final String PRESION_ARTERIAL_DIASTOLICA = "P. arterial Diast.";
    private static final String PRESION_PULMONAR_DIASTOLICA = "P. pulmonar Diast.";
    private static final String PRESION_PULMONAR_SISTOLICA = "P. pulmonar Sist.";

    private boolean flujos = false;
    private boolean flujosDelRinhon = false;
    private boolean presionA = true;
    private boolean presionP = false;
    private boolean diuresis = true;
    private boolean flujosDelRinhonSuavizados = false;
    private boolean presionesASuavizadas = true;
    private boolean presionesPSuavizadas = false;
    private boolean flujosSuavizados = false;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        if (sm.getSignal(FLUJO_CAROTIDA_SUAVIAZADO) == null) {
            generarFlujos(sm, 200);
        }
        if (sm.getSignal(PRESION_ARTERIAL_SISTOLICA) == null) {
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
                sm.setSignalVisible(PRESION_ARTERIAL_SISTOLICA, true);
                sm.setSignalVisible(PRESION_ARTERIAL_DIASTOLICA, true);
                sm.setSignalVisible("P. arterial Media", true);
            }
            if (this.presionesPSuavizadas) {
                sm.setSignalVisible(PRESION_PULMONAR_SISTOLICA, true);
                sm.setSignalVisible(PRESION_PULMONAR_DIASTOLICA, true);
                sm.setSignalVisible("P. pulmonar Media", true);

            }
            if (flujosSuavizados) {
                sm.setSignalVisible(FLUJO_CAROTIDA_SUAVIAZADO, true);
                sm.setSignalVisible("F. riñón suavizado", true);
            }
            if (flujosDelRinhonSuavizados) {
                sm.setSignalVisible("F. corteza suavizado", true);
                sm.setSignalVisible("F. médula suavizado", true);
            }

        } catch (SignalNotFoundException ex) {
             LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(js.getJSignalMonitorPanel(),
                                          "Error; alguna de las señales no tiene nombre correcto",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void generarPresiones(SignalManager sm, int ventana) {
        Signal signalOriginal = sm.getSignal("Presión arterial");
        float[] datosOriginales = signalOriginal.getValues();
        float[] datosNuevos = null;
        datosNuevos = calculaMaxMovil(datosOriginales, ventana);
        Signal nuevaSenhal = new Signal(PRESION_ARTERIAL_SISTOLICA, datosNuevos, 100.0F / ventana, signalOriginal.getStart(),
                                        "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = calculaMinMovil(datosOriginales, ventana);
        nuevaSenhal = new Signal(PRESION_ARTERIAL_DIASTOLICA, datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = new float[datosNuevos.length];
        float[] d = sm.getSignal(PRESION_ARTERIAL_DIASTOLICA).getValues();
        float[] s = sm.getSignal(PRESION_ARTERIAL_SISTOLICA).getValues();
        for (int i = 0; i < d.length; i++) {
            datosNuevos[i] = d[i] + (s[i] - d[i]) / 3;
        }
        nuevaSenhal = new Signal("P. arterial Media", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        signalOriginal = sm.getSignal("Presión pulmonar");
        datosOriginales = signalOriginal.getValues();
        datosNuevos = calculaMaxMovil(datosOriginales, ventana);
        nuevaSenhal = new Signal(PRESION_PULMONAR_SISTOLICA, datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = calculaMinMovil(datosOriginales, ventana);
        nuevaSenhal = new Signal(PRESION_PULMONAR_DIASTOLICA, datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

        datosNuevos = new float[datosNuevos.length];
        d = sm.getSignal(PRESION_PULMONAR_DIASTOLICA).getValues();
        s = sm.getSignal(PRESION_PULMONAR_SISTOLICA).getValues();
        for (int i = 0; i < d.length; i++) {
            datosNuevos[i] = d[i] + (s[i] - d[i]) / 3;
        }
        nuevaSenhal = new Signal("P. pulmonar Media", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "mmHg");
        sm.addSignal(nuevaSenhal);
        nuevaSenhal.adjustVisibleRange();

    }


    static void generarFlujos(SignalManager sm, int ventana) {
        Signal signalOriginal = sm.getSignal("Flujo carótida");

        float[] datosOriginales;
        float[] datosNuevos;
        Signal nuevaSignal;

        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal(FLUJO_CAROTIDA_SUAVIAZADO, datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

        signalOriginal = sm.getSignal("Flujo riñón");
        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. riñón suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

        signalOriginal = sm.getSignal("Flujo corteza");
        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. corteza suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

        signalOriginal = sm.getSignal("Flujo médula");
        if (signalOriginal != null) {
            datosOriginales = signalOriginal.getValues();
            datosNuevos = calculaMediaMovil(datosOriginales, ventana);
            nuevaSignal = new Signal("F. médula suavizado", datosNuevos, 100.0F / ventana, signalOriginal.getStart(), "");
            sm.addSignal(nuevaSignal);
            nuevaSignal.adjustVisibleRange();
        }

    }


    private static float[] calculaMediaMovil(float[] datos, int ventanaEnMuestras) {
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

    private static float[] calculaMaxMovil(float[] datos, int ventanaEnMuestras) {
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

    private static float[] calculaMinMovil(float[] datos, int ventanaEnMuestras) {
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
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public Icon getIcon() {
        return super.generateImageSimple("C", Color.blue);
    }

    @Override
    public String getName() {
        return TITULO;
    }

    @Override
    public String getDescription() {
        return TITULO;
    }

    @Override
    public String getShortDescription() {
        return TITULO;
    }
}

