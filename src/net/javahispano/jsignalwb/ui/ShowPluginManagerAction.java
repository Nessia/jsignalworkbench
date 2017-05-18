/*
 * ShowPluginManagerAction.java
 *
 * Created on 23 de julio de 2007, 13:55
 */

package net.javahispano.jsignalwb.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.plugins.framework.PluginManagerPanel;

/**
 *
 * @author Roman Segador
 */
class ShowPluginManagerAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 6967299102945028245L;
    //private JSWBManager jswbManager;
    private Window owner;

    ShowPluginManagerAction(/*JSWBManager jswbManager,*/ Window owner) {
//        this.jswbManager = jswbManager;
        this.owner = owner;
        this.putValue(NAME, String.valueOf("Plugin Manager"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new PluginManagerPanel(/*jswbManager*/).showJWindow(owner);
    }

}
