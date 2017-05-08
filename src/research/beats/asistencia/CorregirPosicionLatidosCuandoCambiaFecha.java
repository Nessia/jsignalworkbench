package research.beats.asistencia;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import javax.swing.Icon;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.SignalManager;

import java.util.*;

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
public class CorregirPosicionLatidosCuandoCambiaFecha extends AlgorithmAdapter {

    //private boolean transparente = false;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000 - 1900); //FIXME chapuza
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaOriginalDeInicioDelRegistro = cal.getTime();
        Collection<Signal> allSignals = sm.getSignals();
        for (Signal signal : allSignals) {
            Collection<MarkPlugin> listaMarcas = signal.getAllMarks();
            long diferencia = signal.getStart() - fechaOriginalDeInicioDelRegistro.getTime();
            for (MarkPlugin s : listaMarcas) {
                DefaultIntervalMark marca = (DefaultIntervalMark) s;
                marca.setMarkTime(marca.getMarkTime() + diferencia);
                marca.setEndTime(marca.getEndTime() + diferencia);
            }
        }
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
        if (gUIPositions == GUIPositions.MENU) {
            return true;
        } else if (gUIPositions == GUIPositions.TOOLBAR) {
            return true;
        }
        return false;
    }

    @Override
    public Icon getIcon() {
        return super.generateImage("CF");
    }

    @Override
    public String getName() {
        return "Corregir la posicion cuando cambia fecha";
    }

    @Override
    public String getDescription() {
        return "Corrige la posicin de los latidos al cambiar la fecha";
    }

    @Override
    public String getShortDescription() {
        return "Corrige la posición de los latidos al cambiar la fecha";
    }
}
