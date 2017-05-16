package research.beats.asistencia;

import java.util.Collection;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

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
public class Paso1 extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = -105597268089357713L;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Collection<Signal> l = sm.getSignals();
        for (Signal s : l) {
            sm.setSignalVisible(s.getName(), false);
        }
        sm.setSignalVisible("ECG", true);
        JSignalMonitor js = JSWBManager.getJSignalMonitor();
        js.setRepresentingXYValues(true);
        js.setMarksSelectionMode(true);
        js.setFrecuency(128);
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
        return super.generateImage("11");
    }

    @Override
    public String getName() {
        return "Anotando latidos";
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
