/*
 * PluginLoadAction.java
 *
 * Created on 14 de septiembre de 2007, 1:58
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
class PluginLoadAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(PluginLoadAction.class.getName());
    /**
     *
     */
    private static final long serialVersionUID = 3120985332176652511L;
    private String pluginKey;
    private PluginManagerPanel pmPanel;

    PluginLoadAction(String pluginKey, PluginManagerPanel pmPanel) {
        this.pluginKey = pluginKey;
        this.pmPanel = pmPanel;
        Image image = Toolkit.getDefaultToolkit().createImage(
                PluginLoadAction.class.getResource("images/load.jpg"));
        Icon smallIcon = new ImageIcon(
                image.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(NAME, "");
        this.putValue(SHORT_DESCRIPTION, "Load plugin");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOGGER.info("loading.....");
        JSWBManager.getPluginManager().getPlugin(pluginKey);
        if (!JSWBManager.getPluginManager().isPluginLoaded(pluginKey)) {
            JOptionPane.showMessageDialog(JSWBManager.getParentWindow(), "Ploblems loading the plugin");
        } else {
            pmPanel.refreshJTable();
        }
    }

}
