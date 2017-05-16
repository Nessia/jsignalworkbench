/*
 * showSignalsPropertiesPlugin.java
 *
 * Created on 12 de octubre de 2007, 11:00
 */

package net.javahispano.testplugins;

import java.util.Iterator;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class ShowSignalsPropertiesPlugin extends GenericPluginAdapter {


    /**
     *
     */
    private static final long serialVersionUID = -6362170721413447042L;

    @Override
    public String getName() {
        return "ShowSignalsPropertiesPlugin";
    }

    @Override
    public void launch(JSWBManager jswbManager) {
        SignalManager sm = JSWBManager.getSignalManager();
        Iterator<String> names = sm.getSignalsNames().iterator();
        StringBuilder global = new StringBuilder("Propiedades de las senhales:\n");
        while (names.hasNext()) {
            String name = names.next();
            Signal signal = sm.getSignal(name);
            global.append("     Signal:" + name);
            Iterator<String> prop = signal.getAvailableProperties().iterator();
            while (prop.hasNext()) {
                String property = prop.next();
                Object obj = signal.getProperty(property);
                global.append("\n         " + property + "-->" + obj.getClass().getName());
                DebugBean db;
                if (obj instanceof DebugBean) {
                    db = (DebugBean) obj;
                    global.append(" (" + db.getVal1() + ", " + db.getVal2() + ", " + db.getVal3() + ")");
                }
            }
            global.append("\n");
        }
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(), global);
    }

}
