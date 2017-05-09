/*
 * JMenuGrids.java
 *
 * Created on 7 de agosto de 2007, 2:04
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenu;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JMenuGrids extends JMenu {

    /**
     *
     */
    private static final long serialVersionUID = 5286499359936364174L;

    public JMenuGrids(String signalName) {
        super("Change Grid");
        setMnemonic(KeyEvent.VK_G);
        List<String> grids = JSWBManager.getAvailableKindsOfGrids();
        if (grids != null) {
            for (String grid : grids) {
                add(new SetGridAction(signalName, grid));
            }
        } else {
            setEnabled(false);
        }
    }

}
