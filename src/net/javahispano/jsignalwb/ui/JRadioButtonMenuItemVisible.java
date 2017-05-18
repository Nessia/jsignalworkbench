/*
 * JMenuVisible.java
 *
 * Created on 11 de mayo de 2007, 13:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */

class JRadioButtonMenuItemVisible extends JRadioButtonMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = 6270914735608425887L;

    /** Creates a new instance of JMenuVisible */
    JRadioButtonMenuItemVisible(JSWBManager jswbManager, String signalName) {
        super(new VisibleAction(jswbManager, signalName));

        if (JSWBManager.getSignalManager().getSignal(signalName).getProperties().
            isVisible()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    @Override
    public String getActionCommand() {
        return isSelected()? "true" : "false";
    }

}
