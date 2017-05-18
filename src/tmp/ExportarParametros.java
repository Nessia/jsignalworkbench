package tmp;

//import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.JSWBManager;
//import javax.swing.JOptionPane;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
//import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import java.awt.Color;
//import net.javahispano.jsignalwb.plugins.Plugin;
//import javax.swing.JDialog;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
//import net.javahispano.jsignalwb.SignalNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.io.BasicSaver;
import java.io.File;

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
public class ExportarParametros extends AlgorithmAdapter {

    private static final Logger LOGGER = Logger.getLogger(ExportarParametros.class.getName());

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        BasicSaver bs = new BasicSaver();

        float[][] sd = new float[32][];
        sd[0] = sm.getSignal("Diuresis").getValues();
        sd[1] = sm.getSignal("F. riñón suavizado").getValues();
        sd[2] = sm.getSignal("F. carotida suavizado").getValues();
        sd[3] = sm.getSignal("F. medula suavizado").getValues();
        sd[4] = sm.getSignal("F. corteza suavizado").getValues();
        sd[5] = sm.getSignal("P. arterial Media").getValues();
        sd[6] = sm.getSignal("P. pulmonar Media").getValues();
        sd[7] = sm.getSignal("Resistencia renal").getValues();

        sd[8] = sm.getSignal("Diuresis_Suave1_3").getValues();
        sd[9] = sm.getSignal("F. riñón suavizado_Suave1_3").getValues();
        sd[10] = sm.getSignal("F. carótida suavizado_Suave1_3").getValues();
        sd[11] = sm.getSignal("F. médula suavizado_Suave1_3").getValues();
        sd[12] = sm.getSignal("F. corteza suavizado_Suave1_3").getValues();
        sd[13] = sm.getSignal("P. arterial Media_Suave1_3").getValues();
        sd[14] = sm.getSignal("P. pulmonar Media_Suave1_3").getValues();
        sd[15] = sm.getSignal("Resistencia renal_Suave1_3").getValues();

        sd[16] = sm.getSignal("Diuresis_Suave1_5").getValues();
        sd[17] = sm.getSignal("F. riñón suavizado_Suave1_5").getValues();
        sd[18] = sm.getSignal("F. carótida suavizado_Suave1_5").getValues();
        sd[19] = sm.getSignal("F. médula suavizado_Suave1_5").getValues();
        sd[20] = sm.getSignal("F. corteza suavizado_Suave1_5").getValues();
        sd[21] = sm.getSignal("P. arterial Media_Suave1_5").getValues();
        sd[22] = sm.getSignal("P. pulmonar Media_Suave1_5").getValues();
        sd[23] = sm.getSignal("Resistencia renal_Suave1_5").getValues();

        sd[24] = sm.getSignal("Diuresis_Suave1_7").getValues();
        sd[25] = sm.getSignal("F. riñón suavizado_Suave1_7").getValues();
        sd[26] = sm.getSignal("F. carotida suavizado_Suave1_7").getValues();
        sd[27] = sm.getSignal("F. medula suavizado_Suave1_7").getValues();
        sd[28] = sm.getSignal("F. corteza suavizado_Suave1_7").getValues();
        sd[29] = sm.getSignal("P. arterial Media_Suave1_7").getValues();
        sd[30] = sm.getSignal("P. pulmonar Media_Suave1_7").getValues();
        sd[31] = sm.getSignal("Resistencia renal_Suave1_7").getValues();

        try {
            String n = JSWBManager.getIOManager().getCurrentFileName().getName();
            bs.save(new File("C:/" + n + ".txt"), sd);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }

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
        return super.generateImageSimple("X", Color.blue);
    }

    @Override
    public String getName() {
        return "Exportar parametros cerdo";
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
