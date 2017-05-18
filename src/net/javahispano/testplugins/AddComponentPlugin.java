/*
 * AddComponentPlugin.java
 *
 * Created on 2 de octubre de 2007, 19:14
 */

package net.javahispano.testplugins;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JToolBar;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class AddComponentPlugin extends GenericPluginAdapter {

    private static final Logger LOGGER = Logger.getLogger(AddComponentPlugin.class.getName());

    /*
     * Atributos
     */

    private int count = -1;

    /*
     * Constructores
     */

    public AddComponentPlugin() {
        // Vacio
    }

    /*
     * Métodos
     */

    /*
     * (non-Javadoc)
     * @see net.javahispano.jsignalwb.plugins.Plugin#getName()
     */
    @Override
    public String getName() {
        return "AddComponentPlugin";
    }

    @Override
    public void launch(JSWBManager jswbManager) {
        JSWBManager jswb = JSWBManager.getJSWBManagerInstance();
        count++;

        if (count > 10 || count == 0) {
            jswb.removeLeftComponent();
            jswb.removeRightComponent();
            jswb.removeLowerComponent();
            jswb.removeUpperComponent();
        }else{
            JToolBar bar = null;
            if (count % 4 == 0 || count % 4 == 2) {
                bar = new JToolBar(JToolBar.HORIZONTAL);
            } else if (count % 4 == 1 || count % 4 == 3) {
                bar = new JToolBar(JToolBar.VERTICAL);
            }
            if (bar != null){
               bar.setFloatable(false);
               bar.add(new JButton(Integer.toString(count)));
               jswb.setRightComponent(bar);
            }
        }
        LOGGER.log(Level.INFO, "%s", "--->" + jswb.hasLeftComponent() + "---" +
                           jswb.hasUpperComponent() + "---" +
                           jswb.hasRightComponent() + "---" +
                           jswb.hasLowerComponent());
    }

}
