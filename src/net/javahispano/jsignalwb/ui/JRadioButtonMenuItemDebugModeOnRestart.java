package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author roman.segador.torre
 */
class JRadioButtonMenuItemDebugModeOnRestart extends JRadioButtonMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = -9078963111596308637L;


    public JRadioButtonMenuItemDebugModeOnRestart() {
        super(new DebugModeOnRestartAction());
        if (JSWBManager.getJSWBManagerInstance().isDebugModeOnRestart()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
    }

    @Override
    public String getActionCommand() {
       return isSelected()? "true" : "false";
    }

}
