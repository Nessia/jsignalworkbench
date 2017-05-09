package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author roman.segador.torre
 */
public class DebugModeOnRestartAction extends AbstractAction {

    /**
    *
    */
   private static final long serialVersionUID = -5048969473756061769L;


    public DebugModeOnRestartAction() {
        this.putValue(NAME, "Debug mode");
        this.putValue(SHORT_DESCRIPTION, "Debug mode when restart the " +
                      "applicattion turn ON/OFF");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JSWBManager jswbManager = JSWBManager.getJSWBManagerInstance();
        if (e.getActionCommand().toLowerCase().equals("true")) {
            jswbManager.setDebugModeOnRestart(true);
            JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                          "Debug mode is ON now");
        } else if (e.getActionCommand().toLowerCase().equals("false")) {
            jswbManager.setDebugModeOnRestart(false);
            JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                          "When you restart the application the debug mode will be OFF");
        }
    }


}
