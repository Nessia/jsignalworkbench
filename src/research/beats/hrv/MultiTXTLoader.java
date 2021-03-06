package research.beats.hrv;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.BasicLoader;
import net.javahispano.jsignalwb.jsignalmonitor.Resample;

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
public class MultiTXTLoader extends BasicLoader {
    private String[] nombres = {"Flujo", "Sat02", "Movimiento abdominal", "Movimiento toracico"};

    //float[][] s ={ecg,flujo,sp02,a,t};

    @Override
    public String getName() {
        return "MultiTXTLoader";
    }

    @Override
    public String getShortDescription() {
        return "Cargador inicial";
    }

    /**
     * La extension que soporta es "txt". // TODO ??? @vanesa
     *
     * @return ArrayList
     */
    @Override
    public ArrayList<String> getAvalaibleExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("Carpetas");
        return ext;
    }

    @Override
    protected boolean load(File f, SignalManager sm) throws Exception {
        float[] flujo = super.loadSignals(new File(f.getAbsoluteFile() + "\\Flow.txt"))[0];
        float[] a = super.loadSignals(new File(f.getAbsoluteFile() + "\\Abdom.txt"))[0];
        float[] t = super.loadSignals(new File(f.getAbsoluteFile() + "\\Thorax.txt"))[0];
        float[] sp02 = super.loadSignals(new File(f.getAbsoluteFile() + "\\SPO2.txt"))[0];
        flujo = Resample.resampleFs(flujo, 32, 4, true);
        a = Resample.resampleFs(a, 32, 4, true);
        t = Resample.resampleFs(t, 32, 4, true);
        float[] fs = {4, 4, 4, 4};
        float[][] s = {flujo, sp02, a, t};
        for (int i = 0; i < 4; i++) {
            Signal newSignal = new Signal(nombres[i], s[i]);
            newSignal.setFrecuency(fs[i]);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 100);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            newSignal.setStart(cal.getTime().getTime());
            sm.addSignal(newSignal);
            newSignal.adjustVisibleRange();
        }
        JSWBManager.getJSWBManagerInstance().setJSMFrecuency(fs[0]);
        return true;
    }

}
