package net.javahispano.plugins.basicstats;

import java.util.*;

import javax.swing.JFrame;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

import javax.swing.Icon;

import java.awt.Color;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MobileMeanPlugin extends AlgorithmAdapter {
    /**
     *
     */
    private static final long serialVersionUID = 5693229083648676362L;
    private int window = 4;
    private boolean mediana = false;
    private boolean eliminarHuecos = true;
    private float valorHuecos = 50;
    protected int deslizamientoParaCadaMediana = 500;
 // TODO está a true en el otro codigo @vanesa
    protected boolean resample = false;
    protected int ventanaResampleEnSegundos = 300;

    @Override
    public String getDataToSave() {
        return window + " " + mediana + " " + eliminarHuecos + " " + valorHuecos;
    }

    @Override
    public void setSavedData(String data) {
        StringTokenizer t = new StringTokenizer(data);
        try {
            window = Integer.parseInt(t.nextToken());
            mediana = Boolean.parseBoolean(t.nextToken());
            eliminarHuecos = Boolean.parseBoolean(t.nextToken());
            valorHuecos = Float.parseFloat(t.nextToken());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 9;
    }

    /*
     * @TODO parece que no anhade la nueva senhal
     */
    @Override
    public void runAlgorithm(SignalManager sm,  List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        sm.hideAllSignals();
        for (SignalIntervalProperties signali : signals) {
            Signal s = signali.getSignal();
            Signal newSignal = generateSmoothSignal(sm, s);
            sm.addSignal(newSignal);
        }
    }

    private Signal generateSmoothSignal(SignalManager sm, Signal signal) {
        float[] data = signal.getValues();
        float[] newData;
        int numberOfSamples = this.window;

        if (!this.mediana) {
            try {
                newData = MediaMovil.calculaMediaMovilClone(data, numberOfSamples);
            } catch (MediaMovilException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            int medium;
            float[] dataOnWindow;
            if (numberOfSamples % 2 != 0) {
                medium = numberOfSamples / 2;
                dataOnWindow = new float[numberOfSamples];
            } else {
                medium = (numberOfSamples + 1) / 2;
                dataOnWindow = new float[numberOfSamples + 1];
            }
            newData = new float[data.length];
            for (int i = 0; i < medium; i++) {
                newData[i] = data[i];
            }

            for (int i = medium; i < data.length - medium; i += deslizamientoParaCadaMediana) {
                int c = 0;
                for (int j = i - medium; j < i - medium + dataOnWindow.length; j++) {
                    dataOnWindow[c++] = data[j];
                }
                Arrays.sort(dataOnWindow);
                for (int j = i; j < i + deslizamientoParaCadaMediana; j++) {
                    newData[j] = dataOnWindow[medium];
                }

            }
        }
        //si hay que eliminar los huecos
        if (eliminarHuecos) {
            for (int i = 2; i < newData.length; i++) {
                if (newData[i] < valorHuecos) {
                    newData[i] = (newData[i - 1] > newData[i - 2]) ? newData[i - 1] : newData[i - 2];
                }
            }
        }

        Signal newSignal;

        if (resample) {
            int ventana = (int) (ventanaResampleEnSegundos * signal.getSRate() + 1);
            float[] resampledData = new float[newData.length / ventana];
            for (int i = 0; i < resampledData.length; i++) {
                float media = 0;
                for (int j = i * ventana; j < (i + 1) * ventana; j++) {
                    media += newData[j];
                }
                media /= ventana;
                resampledData[i] = media;
            }
            float nuevaFs = 1.0F / ventanaResampleEnSegundos;
            newSignal = new Signal(signal.getName() + "_Suave1_" + this.window, resampledData,
                                   nuevaFs, signal.getStart(),
                                   signal.getMagnitude());
        } else {
            newSignal = new Signal(signal.getName() + "_Suave1_" + this.window, newData,
                                   signal.getSRate(), signal.getStart(),
                                   signal.getMagnitude());
        }
        return newSignal;
    }

    @Override
    public boolean hasDataToSave() {
        return false;
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
//        if (gUIPositions == GUIPositions.MENU) {
//            return true;
//        }
//        if (gUIPositions == GUIPositions.TOOLBAR) {
//            return false;
//        }
//        return false;
        return gUIPositions == GUIPositions.MENU;
    }

    @Override
    public Icon getIcon() {
        return super.generateImageSimple("AV", Color.blue);
    }

    @Override
    public String getName() {
        return "Media movil";
    }

    @Override
    public String getShortDescription() {
        return "Calcula la media movil de una senhal";
    }

    @Override
    public String getDescription() {
        return "Calcula la media movil de una senhal";
    }

    @Override
    public String getPluginVersion() {
        return "1.0";
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return true;
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        DialogMobileMeanPlugin c = new DialogMobileMeanPlugin((JFrame) JSWBManager.getParentWindow(), "Mobile Mean", true,
                window);
        c.setRellenar(this.eliminarHuecos);
        c.setValorRelleno(this.valorHuecos);
        c.setMediana(this.mediana);
        c.setVisible(true);
        this.window = c.getWindow();
        this.mediana = c.isMediana();
        this.valorHuecos = c.getValorRelleno();
        this.eliminarHuecos = c.isRellenar();
    }

}
