/*
 * JRadioButtonMenuItemXY.java
 *
 * Created on 1 de agosto de 2007, 13:58
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman Segador
 */
class JRadioButtonMenuItemXY extends JRadioButtonMenuItem implements JSignalMonitorModeListener {

    /**
     *
     */
    private static final long serialVersionUID = -6212171549730623183L;

    JRadioButtonMenuItemXY(JSignalMonitor jsm) {
        super(new ShowXYPointsAction(jsm));
        setMnemonic(KeyEvent.VK_X);
        setSelected(jsm.isRepresentingXYValues());
        jsm.addModeListener(this);
    }

    @Override
    public String getActionCommand() {
        return isSelected()? "true" : "false";
    }

    @Override
    public void jSignalMonitorModeActionPerformed(JSignalMonitorModeEvent e) {
        if (e.getMode() == JSignalMonitorModeEvent.Modo.REPRESENT_XY_VALUES) {
            setSelected(e.getValue());
        }
    }
}
