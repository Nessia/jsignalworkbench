package net.javahispano.jsignalwb.ui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPlugin;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JMenuGenericPlugin extends JMenu {
    /**
     *
     */
    private static final long serialVersionUID = 4751625944193702692L;

    public JMenuGenericPlugin(String genericPluginName, JSWBManager jswbManager) {
        super(genericPluginName);
        PluginManager pm = JSWBManager.getPluginManager();
        JMenuItem configure = new JMenuItem(new GenericPluginAction(jswbManager,
                genericPluginName, GenericPluginAction.ACTIONS.CONFIGURE));
        if (pm.isPluginLoaded("generic", genericPluginName)) {
            GenericPlugin gp = pm.getGeneric(genericPluginName);
            if (!gp.hasOwnConfigureGUI()) {
                configure.setEnabled(false);
            }
        }
        add(configure);
        add(new JMenuItem(new GenericPluginAction(jswbManager, genericPluginName,
                GenericPluginAction.ACTIONS.LAUNCH)));
    }
}
