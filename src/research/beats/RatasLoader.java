package research.beats;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.JSWBManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.File;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.io.BasicLoader;

public class RatasLoader extends BasicLoader {

    private String[] nombres = {"I", "II", "III"};

    @Override
    public String getName() {
        return "RataLoader";
    }

    @Override
    public String getShortDescription() {
        return "Rata Loader";
    }

    @Override
    public List<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("txt");
        return ext;
    }

    @Override
    protected boolean load(File f, SignalManager sm) throws Exception {
        super.load(f, sm);
        int numberOfSignals = sm.getSignalsSize();
        for (int i = 0; i < numberOfSignals; i++) {
            Signal s = sm.getSignal("Signal" + i);
            if (s == null) {
                return false;
            }
            Signal newSignal = new Signal(nombres[i], s.getValues());
            newSignal.setFrecuency(500);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 100);
            cal.set(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);

            Date baseDate = cal.getTime();
            newSignal.setStart(baseDate.getTime());
            //alinearConECG(sm,newSignal,s);
            sm.removeSignal(s.getName());
            sm.addSignal(newSignal);
            newSignal.adjustVisibleRange();
        }
        JSWBManager jsw = JSWBManager.getJSWBManagerInstance();
        if (jsw.isDeleteSignalsInNextLoad()) {
            jsw.setJSMFrecuency(500);
        }
        return true;
    }


}
