package net.javahispano.jsignalwb.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GenericPluginAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 6005424833739530627L;
//    public final static int CONFIGURE_ACTION = 1;
//    public final static int LAUNCH_ACTION = 2;
    public enum ACTIONS {CONFIGURE, LAUNCH}

    private ACTIONS action;
    private String genericPluginName;
    private JSWBManager jswbm;

    public GenericPluginAction(JSWBManager jswbManager,
                               String genericPluginName, ACTIONS action) {
        this(jswbManager, genericPluginName, action, 20, 20);
    }

    public GenericPluginAction(JSWBManager jswbManager, String genericPluginName,
            ACTIONS action, int iconWidth, int iconHeight) {
        this.action = action;
        this.genericPluginName = genericPluginName;
        this.jswbm = jswbManager;
        this.putValue(SHORT_DESCRIPTION, genericPluginName);
        if (action == ACTIONS.CONFIGURE) {
            this.putValue(NAME, "Configure");
        } else if (action == ACTIONS.LAUNCH) {
            this.putValue(NAME, "Launch");
            Icon smallIcon = JSWBManager.getPluginManager().getIconDefaultSize("generic", genericPluginName,
                    iconWidth, iconHeight);
            this.putValue(SMALL_ICON, smallIcon);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (action == ACTIONS.CONFIGURE) {
            jswbm.showPluginConfiguration("generic", genericPluginName);
        } else if (action == ACTIONS.LAUNCH) {
            jswbm.showPluginExecution("generic", genericPluginName);
        }
    }
}
