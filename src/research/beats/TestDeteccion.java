package research.beats;

import java.awt.Color;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import research.beats.anotaciones.LatidoAnotacion;
import javax.swing.*;

/**
 *
 * @author Santiago Fernandez Dapena
 */
public class TestDeteccion extends AlgorithmAdapter {

    private Bdac bdac;
    private long sampleCount = 0;
    private int delay;
    private float[] ecg = null;
    static  boolean esRata = false;


    public TestDeteccion() {
        bdac = new Bdac();
    }

    public static void generarMarcas(Signal s, long posicion, Color color) {

        LatidoAnotacion m = new LatidoAnotacion();
        // m.setTitle("Latidos" + (s.getStart() + (SampleRate.getMsPerSample() * posicion)));
        m.setTitle("N");
        double msPerSample = SampleRate.getMsPerSample();
        if (esRata) {
           msPerSample=msPerSample/4;
       }

        final long time = (long) (s.getStart() + (msPerSample * (posicion - 4)));
        m.setMarkTime(time);
        m.setEndTime(s.getStart() + (int) ((msPerSample *(posicion + 4))));
        m.setColor(Color.RED);
        m.setAutomatica(true);
        s.addMark(m);
    }

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        //Asignar a SampleRate la frecuencia de la senhal actual
        int fs = (int) signals.get(0).getSignal().getSRate();
        if (esRata) {
            fs=fs/4;
        }

        SampleRate.setSampleRate(fs);

        // Initialize beat detection
        bdac.resetBdac();
        sampleCount = 0;
        ecg = signals.get(0).getSignal().getValues();
        long detectionTimeR;

        for (int i = 0; i < ecg.length - 1; i++) {
            ++sampleCount;
            // Pass sample to beat detection and classification.
            delay = bdac.beatDetect((int) ecg[i], i);

            // If a beat was detected, annotate the beat location
            // and type.
            if (delay != 0) {
                detectionTimeR = sampleCount - delay;
                generarMarcas(signals.get(0).getSignal(), detectionTimeR,
                              Color.GREEN);
            }
        }
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return true;
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
       DialogConfiguracionDetectorLatidos dialog =
               new DialogConfiguracionDetectorLatidos (
                 (JFrame)JSWBManager.getParentWindow(),"Configuracion",true);
          dialog.setVisible(true);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    @Override
    public Icon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("hearth.gif"));
    }

    @Override
    public String getName() {
        return "Detector de latidos";
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Un simple detector de latidos";
    }

    @Override
    public String getPluginVersion() {
        return "0.5";
    }

    @Override
    public String getShortDescription() {
        return "Un simple detector de latidos";
    }
}
