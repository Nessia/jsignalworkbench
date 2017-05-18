/*
 * JMenuSignals.java
 *
 * Created on 17 de mayo de 2007, 13:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuListener;

import net.javahispano.jsignalwb.JSWBManager;


/**
 *
 * @author Roman
 */
class JMenuSignals extends JMenu {
    /**
     *
     */
    private static final long serialVersionUID = -8798508394056148852L;
    private JSWBManager jswbManager;

    /** Creates a new instance of JMenuSignals */
    JMenuSignals(JSWBManager jswbManager) {
        super("Signals");
        setMnemonic(KeyEvent.VK_S);
        this.jswbManager = jswbManager;
        MenuListener ml = new MenuListenerAdapter() {
            @Override
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuSelected();
            }
        };
        addMenuListener(ml);
    }

    private void jMenuSelected() {
        List<String>
                signalNames = new ArrayList<String>(JSWBManager.getSignalManager().getSignalsNames());
        removeAll();
        if (signalNames.isEmpty()) {
            JMenuItem jmi = new JMenuItem("No signals Loaded");
            jmi.setEnabled(false);
            add(jmi);
        } else {
            Collections.sort(signalNames);
            for (String name : signalNames) {
                add(new JMenuSignal(jswbManager, name));
            }
        }
    }

}
