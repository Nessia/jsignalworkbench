/*
 * SetSignalImaginaryAction.java
 *
 * Created on 11 de octubre de 2007, 13:11
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class SetSignalImaginaryAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = -1815094345499692409L;
    private String signalName;
    public SetSignalImaginaryAction(String signalName) {
        this.signalName = signalName;
        this.putValue(NAME, "Imaginary signal");
        this.putValue(SHORT_DESCRIPTION, "Make imaginary(or not) the signal");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_I);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JSWBManager jSWBManager = JSWBManager.getJSWBManagerInstance();
        if (e.getActionCommand().toLowerCase().equals("true")) {
            JSWBManager.getSignalManager().setSignalImaginary(signalName, true);
            jSWBManager.refreshJSM(false);
        } else if (e.getActionCommand().toLowerCase().equals("false")) {
            JSWBManager.getSignalManager().setSignalImaginary(signalName, false);
            jSWBManager.refreshJSM(false);
        }
    }


}
