/*
 * PluginDetailAction.java
 *
 * Created on 13 de septiembre de 2007, 17:59
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.event.ActionEvent;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.defaults.DetailPluginInfoPanel;

/**
 *
 * @author Roman Segador
 */
public class PluginDetailAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 5425329319505537010L;

    private String pluginKey;
    private PluginManagerPanel pmPanel;
    public PluginDetailAction(String pluginKey, PluginManagerPanel pmPanel) {
        this.pluginKey = pluginKey;
        this.pmPanel = pmPanel;
        ImageIcon icon = new ImageIcon(PluginDetailAction.class.getResource("images/more.png"));
        this.putValue(NAME, "...");
        this.putValue(SHORT_DESCRIPTION, "Show a window with detailed info");
        this.putValue(SMALL_ICON, icon);
    }

    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.YES_OPTION;
        JSWBManager.getJSWBManagerInstance();
        PluginManager pm = JSWBManager.getPluginManager();
        if (!pm.isPluginLoaded(pluginKey)) {
            option = JOptionPane.showConfirmDialog(
                    JSWBManager.getParentWindow(),
                    "Do you want to load the plugin?", "The plugin isn't loaded",
                    JOptionPane.YES_NO_OPTION);
        }

        if (option == JOptionPane.YES_OPTION) {
            JSWBManager.getJSWBManagerInstance();
                Plugin plug =
                    JSWBManager.getPluginManager().getPlugin(pluginKey);
            pmPanel.refreshJTable();
            new DetailPluginInfoPanel(JSWBManager.getJSWBManagerInstance(),
                    plug).showJWindow(JSWBManager.getParentWindow());
        }
    }


}
