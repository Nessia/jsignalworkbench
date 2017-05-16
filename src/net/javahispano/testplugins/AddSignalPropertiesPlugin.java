/*
 * AddSignalPropertiesPlugin.java
 *
 * Created on 12 de octubre de 2007, 10:36
 */

package net.javahispano.testplugins;

import java.util.List;

import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
 *
 * @author Roman Segador
 */
public class AddSignalPropertiesPlugin extends AlgorithmAdapter {

    /**
     *
     */
    private static final long serialVersionUID = -8839868272324883305L;
    int i = 0; //TODO un atributo debería tener un nombre más descriptivo @vanesa

    /*
     * Constructores
     */

    public AddSignalPropertiesPlugin() {
        //Vacio
    }

    @Override
    public String getName() {
        return "AddSignalPropertiesPlugin";
    }

    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties> signals, AlgorithmRunner ar) {

        for (SignalIntervalProperties sip : signals) {
            if (sip.isFullSignal()) {
                sip.getSignal().cleanProperties();
                for (int index = 0; index < 5; index++) {
                    DebugBean db = new DebugBean();
                    db.setVal1(i);
                    db.setVal2(i + 1.5f);
                    db.setVal3("bean numero" + i);
                    sip.getSignal().setProperty("prueba " + i, db);
                    i++;
                }
            }
        }
    }

}
