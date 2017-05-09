/*
 * AddToolBarElementsPlugins.java
 *
 * Created on 2 de octubre de 2007, 14:34
 */

package net.javahispano.testplugins;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class AddToolBarElementsPlugin extends GenericPluginAdapter implements ActionListener {

    JButton button;

    public AddToolBarElementsPlugin() {
        // Vacio
    }

    @Override
    public String getName() {
        return "AddToolBarElmentsPlugin";
    }

    @Override
    public void launch(JSWBManager jswbManager) {
        button = new JButton("Borrame");
        button.addActionListener(this);
        jswbManager.addJToolBarComponent(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JSWBManager.getJSWBManagerInstance().removeJToolBarComponent(button);
    }


}
