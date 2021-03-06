/*
 * JMenuAlgorithm.java
 *
 * Created on 13 de junio de 2007, 13:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Algorithm;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;


/**
 *
 * @author Roman
 */
public class JMenuAlgorithm extends JMenu {
    /**
     *
     */
    private static final long serialVersionUID = -7899503069823533186L;

    //private String algorithmName;
    /** Creates a new instance of JMenuAlgorithm */
    public JMenuAlgorithm(String algorithmName, JSWBManager jswbManager) {
        super(algorithmName);

        //this.algorithmName = algorithmName;
        PluginManager pm = JSWBManager.getPluginManager();
        JMenuItem results = new JMenuItem(new AlgorithmAction(algorithmName, AlgorithmAction.ACTIONS.RESULTS_ACTION,
                jswbManager));
        JMenuItem configure = add(new JMenuItem(new AlgorithmAction(algorithmName, AlgorithmAction.ACTIONS.CONFIGURE_ACTION,
                jswbManager)));
        if (pm.isPluginLoaded("algorithm", algorithmName)) {
            Algorithm alg = pm.getAlgorithm(algorithmName);
            if (!alg.hasOwnConfigureGUI()) {
                configure.setEnabled(false);
            }
            if (!alg.hasResultsGUI()) {
                results.setEnabled(false);
            }
        }
        //Icon icon=jswbManager.getPluginManager().getIcon("algorithm",algorithmName,15,15);
        add(configure);
        add(new JMenuItem(new AlgorithmAction(algorithmName, AlgorithmAction.ACTIONS.RUN_ACTION, jswbManager)));
        add(results);
        //setIcon(icon);
    }

}
