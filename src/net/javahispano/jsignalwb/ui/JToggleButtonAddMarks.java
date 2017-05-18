/*
 * JRadioButtonAddMarks.java
 *
 * Created on 5 de julio de 2007, 18:11
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JToggleButton;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman Segador
 */
class JToggleButtonAddMarks extends JToggleButton implements JSignalMonitorModeListener {

    /**
     *
     */
    private static final long serialVersionUID = 5128032825995871143L;

    JToggleButtonAddMarks(JSignalMonitor jsm) {
        super(new SelectMarksAction(jsm));
        setFocusable(false);
        this.setText("");
        setSelected(jsm.isMarkSelectionMode());
        jsm.addModeListener(this);
    }

    @Override
    public String getActionCommand() {
        if (isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public void jSignalMonitorModeActionPerformed(JSignalMonitorModeEvent e) {
        if (e.getMode() == JSignalMonitorModeEvent.Modo.MARK_CREATION) {
            setSelected(e.getValue());
        }
    }

}
