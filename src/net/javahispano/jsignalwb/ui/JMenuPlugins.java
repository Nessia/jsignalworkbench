/*
 * JMenuAlgorithms.java
 *
 * Created on 13 de junio de 2007, 13:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.ui;

import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuListener;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.Plugin.GUIPositions;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

/**
 *
 * @author Roman
 */
class JMenuPlugins extends JMenu {

    /**
    *
    */
    private static final long serialVersionUID = 8853864295113984067L;

    private JSWBManager jswbManager;

    /** Creates a new instance of JMenuAlgorithms */
    JMenuPlugins(JSWBManager jswbManager) {
        super("Plugins");
        setMnemonic(KeyEvent.VK_P);
        this.jswbManager = jswbManager;
        MenuListener ml = new MenuListenerAdapter() {
           @Override
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuSelected();
            }
        };

        addMenuListener(ml);
    }

    private void jMenuSelected() {
        this.removeAll();
        PluginManager pluginManager = JSWBManager.getPluginManager();
        Map<String, ArrayList<String>> plugins = pluginManager.getRegisteredPlugins();
        Set<String> kindPlugins = plugins.keySet();
        List<String> kind = new LinkedList<String>(kindPlugins);
        Collections.sort(kind);
        Iterator<String> it = kind.iterator();
        boolean flag = false;
        while (it.hasNext()) {
            String pluginType = it.next();
            flag = false;
            if ("algorithm".equals(pluginType) || "generic".equals(pluginType)) {
                JMenu jMenu = new JMenu(pluginType);
                ArrayList<String> plug = plugins.get(pluginType);
                for (String s : plug) {
                    Plugin plugin = pluginManager.getPlugin(pluginType + ":" + s);
                    if (plugin.showInGUIOnthe(GUIPositions.MENU)) {
                        if ("algorithm".equals(pluginType)) {
                            jMenu.add(new JMenuAlgorithm(s, jswbManager));
                            flag = true;
                        } else if ("generic".equals(pluginType)) {
                            jMenu.add(new JMenuGenericPlugin(s, jswbManager));
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    add(jMenu);
                }
            }
        }
        addSeparator();
        add(new JMenuItem(new ShowPluginManagerAction(/*jswbManager,*/ JSWBManager.getParentWindow())));
    }

}
