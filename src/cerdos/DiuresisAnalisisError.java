package cerdos;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.JSWBManager;
import javax.swing.*;
import net.javahispano.jsignalwb.SignalManager;
import java.awt.Color;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class DiuresisAnalisisError extends AlgorithmAdapter {

    private static final Logger LOGGER = Logger.getLogger(DiuresisAnalisisError.class.getName());

    //private String droga = "Presión arterial";

    //private String parametro = "Presión arterial";
    //private float peso = 10;
    //private boolean caida = true;
    //private static int indiceDroga = 0;
    //private static int indiceParametro = 0;
//    protected MedidaDroga medidaActual;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        Signal diuresisSignal = signals.get(0).getSignal();

        float[] diuresis = diuresisSignal.getValues();
        float[] diuresisAcumulada = new float[diuresis.length];
        float[] diuresisAcumuladaIdeal = new float[diuresis.length];
        float[] diuresisHora = new float[diuresis.length];
        int contador = 0;
        for (int i = 0; i < diuresis.length; i++) {
            diuresisAcumulada[i] = diuresis[i];
            contador = 0;
            i++;
            for (int j = 1; j < 60 && i < diuresis.length; j++, i++) {
                diuresisAcumulada[i] = diuresisAcumulada[i - 1] + diuresis[i];
                contador++;
            }

            i--;

            for (int j = i - contador; j <= i && j < diuresisHora.length; j++) {
                diuresisHora[j] = diuresisAcumulada[i];
            }

            int contador2 = 1;

            for (int j = i - contador; j <= i && j < diuresisHora.length; j++) {
                diuresisAcumuladaIdeal[j] = diuresisAcumulada[i] * (contador2) / (contador);
                contador2++;
            }

        }
        Signal diuresisSignalAcumulada = new Signal("Diuresis Acumulada", diuresisAcumulada,
                diuresisSignal.getSRate(), diuresisSignal.getStart(), "");
        sm.addSignal(diuresisSignalAcumulada);

        Signal diuresisSignalHora = new Signal("Diuresis Hora", diuresisHora,
                                               diuresisSignal.getSRate(), diuresisSignal.getStart(), "");
        sm.addSignal(diuresisSignalHora);
        Signal diuresisSignalHoraIdeal = new Signal("Diuresis Acumulada ideal", diuresisAcumuladaIdeal,
                diuresisSignal.getSRate(), diuresisSignal.getStart(), "");
        sm.addSignal(diuresisSignalHoraIdeal);

        sm.adjustVisibleRange();

        calculaError(diuresisAcumulada, diuresisAcumuladaIdeal,diuresis);
        float[] d = diuresisAcumulada.clone();
        float[] d2 = diuresisAcumuladaIdeal.clone();
        for (int i = 0; i < diuresis.length; i++) {
            d[i]*=60;
            d2[i]*=60;
        }
     //   calculaError(d, d2);

        JSWBManager.getJSignalMonitor().setFrecuency(0.083F);

    }

    private void calculaError(float[] diuresisAcumulada, float[] diuresisAcumuladaIdeal, float[]diuresis) {
        StringBuilder stringBuilderCVRMSE = new StringBuilder();
        //StringBuilder stringBuilderrm = new StringBuilder();
        StringBuilder stringBuilderMeanPerMinute = new StringBuilder();
        StringBuilder stringBuilderVarianza = new StringBuilder();
        for (int i = 0; i < diuresisAcumulada.length; ) {
            //int hora = 0;
            int contador = 0;
            double rmse = 0;
            int inicio = i;
            for (int j = 0; j < 60 && i < diuresisAcumulada.length; j++, i++) {
                rmse += Math.pow(diuresisAcumulada[i] - diuresisAcumuladaIdeal[i], 2);
                contador++;
            }

            assert contador != 0;
            final float meanUOPerMinute = diuresisAcumulada[i - 1]/contador;
            i=inicio;
            float varianza = 0;
            for (int j = 0; j < 60 && i < diuresisAcumulada.length; j++, i++) {
                varianza += Math.pow(diuresis[i], 2);
            }
            varianza = varianza/contador;//Valor esperado
            varianza = varianza - meanUOPerMinute*meanUOPerMinute;

            rmse = Math.sqrt(rmse / contador);
            assert diuresisAcumulada[i - 1] == diuresisAcumuladaIdeal[i - 1];
            double cvrmse = contador * rmse / (diuresisAcumulada[i - 1]);
            stringBuilderCVRMSE.append((float)cvrmse + "; ");// + rmse + ", ");
            stringBuilderMeanPerMinute.append(meanUOPerMinute + ", ");
            stringBuilderVarianza.append(varianza + ", ");
            //hora ++;
        }
        LOGGER.log(Level.INFO, "%", stringBuilderCVRMSE.toString());
        LOGGER.log(Level.INFO, "%", stringBuilderMeanPerMinute.toString());
        LOGGER.log(Level.INFO, "%", stringBuilderVarianza.toString());
       // LOGGER.info(stringBuilderrm.toString());
    }

    @Override
    public String getName() {
        return "Calculo Errores";
    }

    @Override
    public String getPluginVersion() {
        return "0";
    }

    @Override
    public String getShortDescription() {
        return "Cálculo de Errores";
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 1;
    }

    @Override
    public Icon getIcon() {
        return super.generateImageSimple("E", Color.blue);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return true;
    }

}
