/*
 * JRadioButtonMenuItemAddMarks.java
 *
 * Created on 1 de agosto de 2007, 13:59
 */
package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman Segador
 */
class JRadioButtonMenuItemAddMarks extends JRadioButtonMenuItem implements JSignalMonitorModeListener {

    /**
     *
     */
    private static final long serialVersionUID = 5113296987373845215L;

    JRadioButtonMenuItemAddMarks(JSignalMonitor jsm) {
        super(new SelectMarksAction(jsm));
        setSelected(jsm.isMarkSelectionMode());
        jsm.addModeListener(this);
    }

    @Override
    public String getActionCommand() {
       return isSelected()? "true" : "false";
    }

    @Override
    public void jSignalMonitorModeActionPerformed(JSignalMonitorModeEvent e) {
        if (e.getMode() == JSignalMonitorModeEvent.Modo.MARK_CREATION) {
            setSelected(e.getValue());
        }
    }

}
