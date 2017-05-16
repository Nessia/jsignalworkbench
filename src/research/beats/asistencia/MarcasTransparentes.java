package research.beats.asistencia;

import java.util.Collection;
import java.util.List;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
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
public class MarcasTransparentes extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 5202548249676263628L;

    private boolean transparente = false;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
       Collection<Signal> allSignals = sm.getSignals();
        for (Signal signal : allSignals) {
            Collection<MarkPlugin> listaMarcas = signal.getAllMarks();
            for (MarkPlugin s : listaMarcas) {
                DefaultIntervalMark marca = (DefaultIntervalMark) s;
                if (!transparente) {
                    marca.setInnerTransparencyLevel(0);
                    marca.setBorderTransparencyLevel(0);
                } else {
                    marca.setInnerTransparencyLevel(50);
                    marca.setBorderTransparencyLevel(150);
                }
            }
        }

        transparente = !transparente;
        JSWBManager.getJSignalMonitor().repaintChannels();
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
        return super.generateImage("IN");
    }

    @Override
    public String getName() {
        return "MarcasTransparentes";
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
