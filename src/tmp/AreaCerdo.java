package tmp;

import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.framework.*;
import java.util.*;
import java.util.logging.Logger;

import javax.swing.Icon;
import java.awt.Color;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import net.javahispano.jsignalwb.plugins.defaults.DefaultAlgorithmConfiguration;
import javax.swing.JDialog;
import javax.swing.*;

public class AreaCerdo extends AlgorithmAdapter {

    private static final Logger LOGGER = Logger.getLogger(AreaCerdo.class.getName());

    private static int indiceDroga = 0;
    private static int indiceParametro = 0;

    private String droga = "Presión arterial";
    private String parametro = "Presión arterial";
    private float peso = 10;
    private boolean caida = true;
    protected MedidaDroga medidaActual;

    public static void retrocedeMedida() {
        indiceParametro--;
        if (indiceParametro == -1) {
            indiceParametro = 7;
            indiceDroga--;
            if (indiceDroga == -1) {
                indiceDroga = 5;
            }
        }
    }

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        if (sm.getSignal("F suavizado") == null) {
            VisualizadorDatosCerdo.generarFlujos(sm, 100);
        }
        if (sm.getSignal("P. arterial Sist.") == null) {
            VisualizadorDatosCerdo.generarPresiones(sm, 200);
        }

        medidaActual = new MedidaDroga();

        SignalIntervalProperties signalInterval = signals.get(0);
        Signal signal = signalInterval.getSignal();

        String n = signal.getName();
        if (!n.contains("P.") && !n.contains("suavizado")) {
            if (n.contains("Presión")) {
                JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                              "Por favor, seleccione la presion sistolica o diastolica", "Error",
                                              JOptionPane.ERROR_MESSAGE);
            } else {
                String nuevoNombre = "Flujo suavizado";
                LOGGER.info(nuevoNombre);
                Signal nuevaSignal = sm.getSignal(nuevoNombre);
                SignalIntervalProperties nuevoSignalInterval = new SignalIntervalProperties(nuevaSignal,
                        signalInterval.getStartTime(), signalInterval.getEndTime(),
                        signalInterval.getFirstArrayPosition() / 100,
                                signalInterval.getLastArrayPosition() / 100);
                signalInterval = nuevoSignalInterval;
                signal = nuevaSignal;
            }

        }

        calculaDuracionTotal(medidaActual, signalInterval);
        calculaMinimoYTiemporCaidaYRecuperacion(medidaActual, signalInterval, signal);
        calculaValorBasal(medidaActual, signalInterval, signal);
        medidaActual.setCaida(medidaActual.getValorMinimo() - medidaActual.getValorBasal());
        calculaArea(medidaActual, signalInterval, signal);

        medidaActual.setDroga(droga);
        medidaActual.setParametro(parametro);
    }

    private void calculaDuracionTotal(MedidaDroga medida, SignalIntervalProperties si) {
        long duracionTotal = si.getEndTime() - si.getStartTime();
        duracionTotal /= 1000.0F;
        medida.setDuracionTotal(duracionTotal);
    }


    private void calculaMinimoYTiemporCaidaYRecuperacion(MedidaDroga medida, SignalIntervalProperties si,
            Signal s) {
        float valorMinOMax = 1000;
        if (!caida) {
            valorMinOMax = -1000;
        }
        int indiceMinOMax = 0;
        float[] datos = s.getValues();
        for (int i = si.getFirstArrayPosition(); i <= si.getLastArrayPosition(); i++) {
            if (caida && datos[i] < valorMinOMax) {
                valorMinOMax = datos[i];
                indiceMinOMax = i;
            }
            if (!caida && datos[i] > valorMinOMax) {
                valorMinOMax = datos[i];
                indiceMinOMax = i;
            }
        }
        long medio = TimePositionConverter.positionToTime(indiceMinOMax, s);
        float tDuracionCaida = (float)medio - si.getStartTime();
        tDuracionCaida /= 1000.0F;

        float tDuracionRecuperacion = si.getEndTime() - (float)medio;
        tDuracionRecuperacion /= 1000.0F;

        medida.setDuracionCaida(tDuracionCaida);
        medida.setDuracionRecuperacion(tDuracionRecuperacion);
        medida.setValorMinimo(valorMinOMax);
    }

    private void calculaValorBasal(MedidaDroga medida, SignalIntervalProperties si, Signal s) {
        float frecuencia = s.getSRate();
        int ventana;
        if (frecuencia > 10) { //se trata de la señal original
            ventana = 200; //cogemos 20 segundos
        } else { //se trata de la señal filtrada
            ventana = 2; //cogemos 20 segundos
        }

        float valorBasal = 0;
        float[] datos = s.getValues();
        int i;
        int principioVentanaBasal = si.getFirstArrayPosition() - ventana;
        for (i = si.getFirstArrayPosition(); i >= principioVentanaBasal; i--) {
            if (datos[i] == 0) { //0, la señal esta cortada
                break;
            }
            valorBasal += datos[i];
        }
        int ventanaReal = si.getFirstArrayPosition() - i;
        valorBasal /= ventanaReal;
        medida.setValorBasal(valorBasal);
    }


    private void calculaArea(MedidaDroga medida, SignalIntervalProperties si, Signal s) {
        float areaCuadrado = medida.getValorBasal() * medida.getDuracionTotal();
        float areaReal = 0;
        float[] datos = s.getValues();
        for (int i = si.getFirstArrayPosition(); i < si.getLastArrayPosition(); i++) {
            areaReal += datos[i];
        }

        areaReal /= s.getSRate();
        if (areaCuadrado < 0 && areaReal < 0) {
            medida.setAreaRatio(areaCuadrado / areaReal);
        } else {
            medida.setAreaRatio(areaReal / areaCuadrado);
        }

        areaReal -= areaCuadrado;
        if (!s.getName().toLowerCase().contains("p")) { //si no es una de las presiones
            areaReal /= peso; //normalizamos por el peso
        }

        medida.setArea(areaReal);
    }

    @Override
    public String getName() {
        return "Calculo areas";
    }

    @Override
    public String getPluginVersion() {
        return "0";
    }

    @Override
    public String getShortDescription() {
        return "Cálculo de áreas";
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 1;
    }

    @Override
    public Icon getIcon() {
        return super.generateImageSimple("V", Color.blue);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return false;
    }

    @Override
    public void launchExecutionGUI(JSWBManager jswbManager) {
        DialogArea dialogArea = new DialogArea(null, "Realización de medida", true);
        dialogArea.jTextFieldPeso.setText(Float.toString(peso));
        dialogArea.jComboBoxDroga.setSelectedIndex(indiceDroga);
        dialogArea.jComboBoxParametro.setSelectedIndex(indiceParametro);
        dialogArea.setSize(600, 400);
        dialogArea.setLocationRelativeTo(JSWBManager.getParentWindow());

        dialogArea.setVisible(true);
        if (dialogArea.cancelado) {
            return;
        }
        peso = 1;//Float.parseFloat(dialogArea.jTextFieldPeso.getText());
        caida = dialogArea.jCheckBoxValle.isSelected();
        droga = (String) dialogArea.jComboBoxDroga.getSelectedItem();
        parametro = (String) dialogArea.jComboBoxParametro.getSelectedItem();
        indiceDroga = dialogArea.jComboBoxDroga.getSelectedIndex();
        indiceParametro = dialogArea.jComboBoxParametro.getSelectedIndex();
        indiceParametro++;
        if (indiceParametro >= 10) {
            indiceParametro = 0;
            indiceDroga++;
            if (indiceDroga >= 6) {
                indiceDroga = 0;
            }
        }

        JDialog conf = new JDialog(JSWBManager.getParentWindow(), "Execution GUI");

        conf.setModal(true);
        DefaultAlgorithmConfiguration jPane = new DefaultAlgorithmConfiguration(this,
                jswbManager, conf);
        jPane.setIntervalMode(true);
        jPane.jButton3ActionPerformed();
        conf.getContentPane().add(jPane);
        conf.setSize(jPane.getPreferredSize());
        conf.setResizable(false);
        conf.setLocationRelativeTo(conf.getParent());
        // conf.setVisible(true);
    }

    @Override
    public boolean hasResultsGUI() {
        return true;
    }

    @Override
    public void launchResultsGUI(JSWBManager jswbManager) {
        DialogResultadosMedida d = new DialogResultadosMedida(null, "Resultado", true);
        d.jTextAreaMedida.setText(medidaActual.toString(true));
        d.setSize(450, 400);
        d.setLocationRelativeTo(JSWBManager.getParentWindow());
        d.setVisible(true);
    }


}
