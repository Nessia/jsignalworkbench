package research.beats.asistencia;

import java.util.Collection;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.SignalConstants;
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
public class Paso2 extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 3131758535666927783L;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Collection<Signal> l = sm.getSignals();
        for (Signal s : l) {
            sm.setSignalVisible(s.getName(), false);
        }
        sm.setSignalVisible(SignalConstants.SENAL_FLUJO, true);
        sm.setSignalVisible(SignalConstants.SENAL_SATURACION_02, true);
        sm.setSignalVisibleRange(SignalConstants.SENAL_SATURACION_02, 80, 100);
        Signal sat = sm.getSignal(SignalConstants.SENAL_SATURACION_02);
        sat.getProperties().setInvadeNearChannels(false);
        float[] d = sat.getValues();
        for (int i = 1; i < d.length; i++) {
            if (d[i] >= 103) {
                d[i] = d[i - 1];
            }
        }
        sm.setSignalVisible(SignalConstants.SENAL_MOVIMIENTO_ABDOMINAL, true);
        sm.setSignalVisible(SignalConstants.SENAL_MOVIMIENTO_TORACICO, true);
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setRepresentingXYValues(true);
        js.setMarksSelectionMode(true);
        js.setFrecuency(2);
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return true;
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
        return super.generateImage("22");
    }

    @Override
    public String getName() {
        return "Anotando eventos respiratorios";
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
