/*
 * RemoveAction.java
 *
 * Created on 16 de mayo de 2007, 13:31
 *
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman
 */
public class RemoveAction extends AbstractAction {
    /**
    *
    */
    private static final long serialVersionUID = -2730478127106698472L;

    /*
     * Atributos
     */

    private JSWBManager jswbManager;
    private String signalName;


    /*
     * Constructores
     */

    public RemoveAction(JSWBManager jswbManager, String signalName) {
        this.jswbManager = jswbManager;
        this.signalName = signalName;
        this.putValue(NAME, String.valueOf("Remove"));
    }

    /*
     * Métodos
     */

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(jswbManager.getParentWindow(), "Are you sure?",
                "Remove signal: " + signalName, JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            //boolean flag=jswbManager.getSignalManager().isSignalVisible(signalName);
            jswbManager.removeSignal(signalName);
        }

    }
}
