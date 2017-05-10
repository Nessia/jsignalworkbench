/*
 * JRadioButtonXY.java
 *
 * Created on 21 de junio de 2007, 15:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JToggleButton;

import net.javahispano.jsignalwb.jsignalmonitor.*;

/**
 *
 * @author Roman
 */
public class JToggleButtonXY extends JToggleButton implements JSignalMonitorModeListener {

    /**
     *
     */
    private static final long serialVersionUID = -6640695470505816580L;

    /**
     * Creates a new instance of JRadioButtonXY
     */
    public JToggleButtonXY(JSignalMonitor jsm) {
        super(new ShowXYPointsAction(jsm));
        setFocusable(false);
        setText("");
        setSelected(jsm.isRepresentingXYValues());
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
        if (e.getMode() == JSignalMonitorModeEvent.Modo.REPRESENT_XY_VALUES) {
            setSelected(e.getValue());
        }
    }


}
