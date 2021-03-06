/*
 * SetGridAction.java
 *
 * Created on 7 de agosto de 2007, 1:51
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class SetGridAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 2846680920810661619L;

    //private JSWBManager jswbManager;
    private String signalName;
    private String gridName;

    public SetGridAction(String signalName, String gridName) {
        //this.jswbManager = jswbManager;
        this.signalName = signalName;
        this.gridName = gridName;
        this.putValue(NAME, gridName);
        Icon smallIcon = JSWBManager.getPluginManager().getIconDefaultSize("grid", gridName);
        Icon icon = JSWBManager.getPluginManager().getIconDefaultSize("grid", gridName);
        this.putValue(SMALL_ICON, smallIcon);
        this.putValue(LARGE_ICON_KEY, icon);
        this.putValue(SHORT_DESCRIPTION, "Set the " + gridName + " grid to signal: " + signalName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JSWBManager.setSignalGrid(signalName, gridName);
    }

}
