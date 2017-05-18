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
class GenericPluginAction extends AbstractAction {
    /**
     *
     */
    private static final long serialVersionUID = 6005424833739530627L;
//    public final static int CONFIGURE_ACTION = 1;
//    public final static int LAUNCH_ACTION = 2;
    enum Action {CONFIGURE, LAUNCH}

    private Action action;
    private String genericPluginName;
    private JSWBManager jswbm;

    GenericPluginAction(JSWBManager jswbManager,
                               String genericPluginName, Action action) {
        this(jswbManager, genericPluginName, action, 20, 20);
    }

    private GenericPluginAction(JSWBManager jswbManager, String genericPluginName,
            Action action, int iconWidth, int iconHeight) {
        this.action = action;
        this.genericPluginName = genericPluginName;
        this.jswbm = jswbManager;
        this.putValue(SHORT_DESCRIPTION, genericPluginName);
        if (action == Action.CONFIGURE) {
            this.putValue(NAME, "Configure");
        } else if (action == Action.LAUNCH) {
            this.putValue(NAME, "Launch");
            Icon smallIcon = JSWBManager.getPluginManager().getIconDefaultSize("generic", genericPluginName,
                    iconWidth, iconHeight);
            this.putValue(SMALL_ICON, smallIcon);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (action == Action.CONFIGURE) {
            jswbm.showPluginConfiguration("generic", genericPluginName);
        } else if (action == Action.LAUNCH) {
            jswbm.showPluginExecution("generic", genericPluginName);
        }
    }
}
