package research.mining.tmp;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalConstants;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import javax.swing.Icon;
import net.javahispano.jsignalwb.SignalManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

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
public class CalcularValorBasalSpO2 extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 8142102131821153813L;
    private static final Logger LOGGER = Logger.getLogger(CalcularValorBasalSpO2.class.getName());

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
       Signal s = sm.getSignal(SignalConstants.SENAL_SATURACION_02);
       float[] f =s.getValues();
       float[] f2 =Arrays.copyOf(f,f.length);
       double media =0;
       int contador=0;
       for (Float dato : f2) {
          if (dato<= 100&&dato>20) {
              media+=dato;
              contador++;
          }
       }
       media/=contador;

       double basal =0;
       contador=0;

       Arrays.sort(f2,0,f2.length);
       for (int i = 8*f2.length/10; i < f2.length; i++) {
           if (f2[i]<= 100&&f2[i]>20) {
               basal+=f2[i];
               contador++;
          }
       }
       basal/= contador;

       LOGGER.log(Level.INFO, "%s", "Media: "+ media+ " Basal: "+ basal);
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
        return super.generateImage("mm");
    }

    @Override
    public String getName() {
        return "Media ";
    }

}
