package research.beats;

import java.awt.HeadlessException;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.BasicSaver;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;

public class ExportarLatidos extends AlgorithmAdapter {

    private static final Logger LOGGER = Logger.getLogger(ExportarLatidos.class.getName());
    private static final String EXTENSION_BEATS = ".beats";
    private static final String EXTENSION_DAT = ".dat";

    private String ultimoDirectorioAbierto = null;
    private JFileChooser jf;
    private BasicSaver basicSaver;
    private float[] rr;
    private boolean error = true;
    private boolean exportRHRV = true;

    public ExportarLatidos() {
        basicSaver = new BasicSaver();
        jf = new JFileChooser(ultimoDirectorioAbierto);
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 0;
    }

    @Override
    public String getDataToSave() {
        return ultimoDirectorioAbierto;
    }

    @Override
    public String getDescription() {
        return "Permite Generar una serie de intervalos RR a partir de anotaciones de latido";
    }

    @Override
    public Icon getIcon() {
        return this.generateImage("RR");
    }

    @Override
    public String getName() {
        return "ExportarLatidos";
    }

    @Override
    public String getPluginVersion() {
        return "0.5";
    }

    @Override
    public String getShortDescription() {
        return "Genera una serie de intervalos RR";
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return false;
    }

    @Override
    public void setSavedData(String data) {
        ultimoDirectorioAbierto = data;
        jf = new JFileChooser(ultimoDirectorioAbierto);
        jf.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(EXTENSION_BEATS);
            }

            @Override
            public String getDescription() {
                return "Archivos RR";
            }
        });
    }

    @Override
    public void runAlgorithm(SignalManager sm,
            List<SignalIntervalProperties> signals, AlgorithmRunner ar) {
        Signal signal;
        List<MarkPlugin> l;
        SignalIntervalProperties interval = signals.get(0);
        signal = interval.getSignal();
        if (signals.get(0).isFullSignal()) {
            l = signal.getAllMarks();
        } else {
            l = new LinkedList<MarkPlugin>();
            for (SignalIntervalProperties e : signals) {
                List<MarkPlugin> kk = signal.getMarks(e.getStartTime(), e.getEndTime());
                l.addAll(kk);
            }
        }
        List<DefaultIntervalMark> beatMarks = new LinkedList<DefaultIntervalMark>();
        for (MarkPlugin mark : l) {
            if (mark instanceof DefaultIntervalMark && "0".equals(((DefaultIntervalMark) mark).getCommentary())) {
                beatMarks.add((DefaultIntervalMark) mark);
            }
        }
        if (beatMarks.isEmpty()) {
            this.errorMensaje();
            error = true;
            return;
        }
        Collections.sort(beatMarks);
        generateRR(signal, beatMarks);
        error = false;
    }

    private void generateRR(Signal signal, List<DefaultIntervalMark> beatMarks) {
        rr = new float[beatMarks.size()];
        boolean useMax = decideOnMaxMin(beatMarks, signal);
        int i = 0;
        for (MarkPlugin m : beatMarks) {
            float refinedRR = ajustarPrincipios(m, useMax, signal);
            rr[i] = refinedRR - signal.getStart();
            if (Math.random()>0.98) rr[i]+=60;
            rr[i] /= 1000;
            i++;
        }
        if (!this.exportRHRV) {
            for (int j = rr.length - 1; j > 0; j--) {
                rr[j] = rr[j] - rr[j - 1];
            }
            float[] nrr = new float[rr.length - 1];
            for (int j = 0; j < nrr.length; j++) {
                nrr[j] = rr[j + 1];
            }
            rr = nrr;
        }
        //@chapuza
      /*  else{
         for (int j = 0; j < rr.length; j++) {
         rr[j] /= 2F;
         }

         }*/
    }

    private boolean decideOnMaxMin(List<DefaultIntervalMark> beatMarks, Signal e) {
        int counter = 0;
        float[] ec = e.getValues();
        float min = 0;
        float max = 0;
        for (MarkPlugin m : beatMarks) {
            int beging = TimePositionConverter.timeToPosition(m.getMarkTime(), e);
            int end = TimePositionConverter.timeToPosition(m.getEndTime(), e);
            float minValue = Float.POSITIVE_INFINITY;
            float maxValue = Float.NEGATIVE_INFINITY;
            if (beging < 0) {
                continue;
            }
            for (int j = beging; j < end; j++) {
                if (ec[j] < minValue) {
                    minValue = ec[j];
                }
                if (ec[j] > maxValue) {
                    maxValue = ec[j];
                }
            }
            min += minValue;
            max += maxValue;
            counter++;
            if (counter > 1000) {
                break;
            }
        }
        return Math.abs(min) <= max;
    }

    private long ajustarPrincipios(MarkPlugin m, boolean useMax, Signal e) {
        float[] ec = e.getValues();
        int begining = TimePositionConverter.timeToPosition(m.getMarkTime(), e);
        int end = TimePositionConverter.timeToPosition(m.getEndTime(), e);
        int selectedIndex = 0;
        if (useMax) {
            float mv = Float.NEGATIVE_INFINITY;
            for (int i = begining; i < end; i++) {
                if (ec[i] > mv) {
                    mv = ec[i];
                    selectedIndex = i;
                }
            }
        } else {
            float mv = Float.POSITIVE_INFINITY;
            for (int i = begining; i < end; i++) {
                if (ec[i] < mv) {
                    mv = ec[i];
                    selectedIndex = i;
                }
            }
        }

        return TimePositionConverter.positionToTime(selectedIndex, e);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public boolean hasResultsGUI() {
        return true;
    }

    @Override
    public void launchResultsGUI(JSWBManager jswbManager) {
        if (error) {
            return;
        }
        if (ultimoDirectorioAbierto != null) {
            jf.setCurrentDirectory(new File(ultimoDirectorioAbierto));
        }
        if (jf.showSaveDialog(JSWBManager.getParentWindow())
                == JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();
            ultimoDirectorioAbierto = jf.getCurrentDirectory().getAbsolutePath();
            LOGGER.log(Level.INFO, ultimoDirectorioAbierto);
            String n = f.getAbsolutePath();
            if (this.exportRHRV) {
                if (!n.endsWith(EXTENSION_BEATS)) {
                    n = n.concat(EXTENSION_BEATS);
                }

            } else {
                if (!n.endsWith(EXTENSION_DAT)) {
                    n = n.concat(EXTENSION_DAT);
                }
            }
            f = new File(n);
            float[][] tmp = new float[1][rr.length];
            tmp[0] = rr;
            try {
                if (!basicSaver.save(f, tmp, false)) {
                    errorGuardarMensaje();
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                errorGuardarMensaje();
            }
        }
    }

    /**
     * Por defecto no proporciona interfaz propia de ejecucion.
     *
     * @return boolean
     */
    @Override
    public boolean hasOwnExecutionGUI() {
        return true;
    }

    @Override
    public void launchExecutionGUI(JSWBManager jswbManager) {
        DialogKubiosRHRV d = new DialogKubiosRHRV(null, "Seleciona Formato", true);
        d.setVisible(true);
        exportRHRV = d.isExportRHRV();
        super.launchExecutionGUI(jswbManager);

    }

    private void errorGuardarMensaje() throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                "Error, no se pudo exportar los latidos",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void errorMensaje() throws HeadlessException {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                "Error, no pudo generar los RR ?Se han detectado los latidos previamente?",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

}
