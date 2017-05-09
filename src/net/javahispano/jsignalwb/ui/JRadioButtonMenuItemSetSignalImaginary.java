/*
 * JRadioButtonMenuItemSetSignalImaginary.java
 *
 * Created on 11 de octubre de 2007, 13:16
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemSetSignalImaginary extends JRadioButtonMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = 4585519818125960699L;

    public JRadioButtonMenuItemSetSignalImaginary(String signalName) {
        super(new SetSignalImaginaryAction(signalName));

        if (JSWBManager.getSignalManager().getSignal(signalName).isImaginary()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    @Override
    public String getActionCommand() {
        if (isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }
}
