package research.beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalConstants;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;

import java.util.Collection;

import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

import javax.swing.Icon;

import net.javahispano.jsignalwb.SignalManager;

import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

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
public class Paso3  extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 5029254111057929342L;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Collection<Signal> l = sm.getSignals();
        for (Signal s : l) {
            sm.setSignalVisible(s.getName(), false);
        }
        sm.renameSignal("Sat0III", SignalConstants.SENAL_SATURACION_02);
        sm.renameSignal("Movimientoabdominal", SignalConstants.SENAL_MOVIMIENTO_ABDOMINAL);
        sm.renameSignal("Movimientotoracico", SignalConstants.SENAL_MOVIMIENTO_TORACICO);

        sm.setSignalVisible(SignalConstants.SENAL_FLUJO,true);
        sm.setSignalVisible(SignalConstants.SENAL_SATURACION_02,true);
        sm.setSignalVisible(SignalConstants.SENAL_MOVIMIENTO_ABDOMINAL,true);
        sm.setSignalVisible(SignalConstants.SENAL_MOVIMIENTO_TORACICO,true);

        sm.setSignalVisibleRange(SignalConstants.SENAL_SATURACION_02, 80, 100);
        sm.setSignalVisibleRange(SignalConstants.SENAL_FLUJO, -20,20);
        Signal sat = sm.getSignal(SignalConstants.SENAL_SATURACION_02);
        sat.getProperties().setInvadeNearChannels(false);
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
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
        return super.generateImage("33");
    }

    @Override
    public String getName() {
        return "As";
    }

    @Override
    public String getDescription() {
        return "As";
    }

    @Override
    public String getShortDescription() {
        return "As";
    }
}
