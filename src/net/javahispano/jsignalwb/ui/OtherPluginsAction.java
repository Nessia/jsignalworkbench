package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author roman.segador.torre
 */
public class OtherPluginsAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 9115517509132082982L;

    private String pluginName;
    private String pluginType;

    public OtherPluginsAction(String pluginName, String pluginType) {
        this.pluginName = pluginName;
        this.pluginType = pluginType;
        this.putValue(NAME, "Configure");
        this.putValue(SHORT_DESCRIPTION, "Launch plugin configuration");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JSWBManager.getJSWBManagerInstance().showPluginConfiguration(
                pluginType, pluginName);
    }
}
