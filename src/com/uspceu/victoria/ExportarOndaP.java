
package com.uspceu.victoria;

import com.uspceu.SimpleAlgorithm;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.io.BasicSaver;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import research.beats.DialogKubiosRHRV;

/**
 *
 * @author Victoria
 */
public class ExportarOndaP extends SimpleAlgorithm{

   private static final Logger LOGGER = Logger.getLogger(ExportarOndaP.class.getName());
   public static final String EXT_BEATS = ".beats";

    private String ultimoDirectorio = null;
    private JFileChooser jf;
    private BasicSaver basicSaver;
    private float[] pp;
    private boolean error = true;
    private boolean exportRHRV = true;


    public ExportarOndaP(){
        basicSaver = new BasicSaver();
        jf = new JFileChooser(ultimoDirectorio);
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 0;
    }

    @Override
    public String getDataToSave() {
        return ultimoDirectorio;
    }

    @Override
    public String getDescription() {
        return "Obtencion de intervalos PP de los latidos";
    }

    @Override
    public String getName() {
        return "ExporOndaP";
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
        jf.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getName().toLowerCase().endsWith(EXT_BEATS);
            }

            public String getDescription() {
                return "Archivos PP";
            }
        });
    }

    public List<DefaultIntervalMark> marcasP (/*SignalManager signalManager,*/ Signal signal){
        List<MarkPlugin> latidos = signal.getAllMarks();
        List<DefaultIntervalMark> ondasP = new LinkedList<DefaultIntervalMark>();

        for(MarkPlugin beat: latidos){
            DefaultIntervalMark beatInterval =(DefaultIntervalMark)beat;
            if(beatInterval.getTitle().contains("P")){
                ondasP.add(beatInterval);
            }
        }

        return ondasP;
    }

    private void generatePP(Signal signal, List<DefaultIntervalMark> ondasP) {
        pp = new float[ondasP.size()];
        int i = 0;
        for (DefaultIntervalMark m : ondasP) {

            long refinedPP = Long.parseLong(m.getComentary());
            pp[i] = (float)refinedPP - signal.getStart();
            if (Math.random()>0.98) pp[i]+=60;
            pp[i] /= 1000;
            i++;
        }
        if (!this.exportRHRV) {
            for (int j = pp.length - 1; j > 0; j--) {
                pp[j] = pp[j] - pp[j - 1];
            }
            float[] npp = new float[pp.length - 1];
            for (int j = 0; j < npp.length; j++) {
                npp[j] = pp[j + 1];
            }
            pp = npp;
        }

    }

    @Override
    public void runAlgorithm(SignalManager sm, Signal signal, float[] datos, float fs) {

        List<DefaultIntervalMark> marcasP = marcasP(/*sm,*/ signal);

        Collections.sort(marcasP);
        generatePP(signal, marcasP);
        error = false;
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
        if (ultimoDirectorio != null) {
            jf.setCurrentDirectory(new File(ultimoDirectorio));
        }
        if (jf.showSaveDialog(JSWBManager.getParentWindow()) != JFileChooser.APPROVE_OPTION) {
           return;
        }
        File f = jf.getSelectedFile();
        ultimoDirectorio = jf.getCurrentDirectory().getAbsolutePath();
        LOGGER.log(Level.INFO, "Ultimo directorio: %s", ultimoDirectorio);
        String n = f.getAbsolutePath();
        if (this.exportRHRV) {
            if (!n.endsWith(EXT_BEATS)) {
                n = n.concat(EXT_BEATS);
            }
        } else {
            if (!n.endsWith(".dat")) {
                n = n.concat(".dat");
            }
        }
        f = new File(n);
        float[][] tmp = new float[1][pp.length];
        tmp[0] = pp;
        try {
            if (!basicSaver.save(f, tmp, false)) {
                errorGuardarMensaje();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            errorGuardarMensaje();
        }
    }

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
//
//    private void errorMensaje() throws HeadlessException {
//        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
//                "Error, no pudo generar los RR ?Se han detectado los latidos previamente?",
//                "Error", JOptionPane.ERROR_MESSAGE);
//    }

}
