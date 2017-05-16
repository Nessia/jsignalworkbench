package research.beats.asistencia;

import java.util.*;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.JSMProperties;
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
public class BorrarMarcasEnIntervalo extends AlgorithmAdapter implements IntervalSelectedListener {

    /**
     *
     */
    private static final long serialVersionUID = 611807130165011736L;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        JSWBManager jsw = JSWBManager.getJSWBManagerInstance();
        JSMProperties jsm = JSWBManager.getJSignalMonitor().getJSMProperties();
        jsm.setMarkCreation(false);
        jsm.setClicked(false);
        jsw.selectInterval(this);

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
        return new javax.swing.ImageIcon(getClass().getResource("erase.gif"));
    }

    @Override
    public String getName() {
        return "Borrar marcas";
    }

    @Override
    public String getDescription() {
        return getName();
    }

    @Override
    public String getShortDescription() {
        return getName();
    }

    @Override
    public void intervalSelectedActionPerformed(IntervalSelectedEvent evt) {
        SignalManager sm = JSWBManager.getSignalManager();
        Signal signal = sm.getSignal(evt.getChannelName());
        List<MarkPlugin> listMarks = signal.getAllMarks();
        TreeSet<MarkPlugin> treeMarks = new TreeSet<MarkPlugin>(listMarks);
        DefaultIntervalMark begining = new DefaultIntervalMark();
        begining.setMarkTime(evt.getStartTime());
        begining.setEndTime(evt.getStartTime());
        DefaultIntervalMark end = new DefaultIntervalMark();
        end.setMarkTime(evt.getEndTime());
        end.setEndTime(evt.getEndTime());
        SortedSet<MarkPlugin> eraseMarks = treeMarks.subSet(begining, end);

        for (MarkPlugin m : eraseMarks) {
            signal.removeMark(m);
        }

        JSWBManager.getJSignalMonitor().repaintChannels();

        JSWBManager.getJSignalMonitor().setMarksSelectionMode(true);
    }
}
