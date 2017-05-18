package tmp;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.calculator.CalculatorGUI.Operation;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
 *
 * @author aq
 */
public class NetWork extends AlgorithmAdapter {


    private static final Logger LOGGER = Logger.getLogger(NetWork.class.getName());

    protected String firstSignalName;
    protected String secondSignalName;
    protected String newSignalName;
    protected Operation operation = Operation.ADD;
    protected float[] firstSignalValues;
    protected float[] secondSignalValues;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
             Collection<Signal> allSignals = sm.getSignals();
        String[] names = new String[allSignals.size()];
        int i = 0;
        for (Signal signal : allSignals) {
            names[i] = signal.getName();
            LOGGER.info(names[i]);
        }
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return false;
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public Icon getIcon() {
        return super.generateImage("NT");
    }

    @Override
    public String getName() {
        return "Net";
    }

    @Override
    public String getDescription() {
        return "Net";
    }

    @Override
    public String getShortDescription() {
        return "Net";
    }
}
