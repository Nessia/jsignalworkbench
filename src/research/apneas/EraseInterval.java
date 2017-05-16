package research.apneas;

import java.util.List;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
 * Created by IntelliJ IDEA.
 * User: a
 * Date: 03-dic-2008
 * Time: 20:30:07
 *
 */
public class EraseInterval extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 6429105140246213348L;

    @Override
    public String getName() {
        return "Borrar intervalo";
    }

    @Override
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
            AlgorithmRunner ar) {
        SignalIntervalProperties i = signals.get(0);
        Signal s = i.getSignal();
        float[] d = s.getValues();
        int borrar = i.getLastArrayPosition() - i.getFirstArrayPosition();
        int j;
        for (j = i.getLastArrayPosition(); j < d.length; j++) {
            d[j - borrar] = d[j];

        }
        for (int k = j - borrar; k < d.length; k++) {
            d[k] = 0;
        }

    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

}
