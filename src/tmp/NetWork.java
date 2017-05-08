package tmp;

import java.util.Collection;
import java.util.List;

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


    String firstSignalName, secondSignalName, newSignalName;
    Operation operation = Operation.ADD;
    float[] firstSignalValues, secondSignalValues;

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
             Collection<Signal> allSignals = sm.getSignals();
        String[] names = new String[allSignals.size()];
        int i = 0;
        for (Signal signal : allSignals) {
            names[i] = signal.getName();
            System.out.println(names[i]);
        }
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return false;
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
