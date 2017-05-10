/*
 * JRadioButtonMenuItemShowEmphasisLevel.java
 *
 * Created on 11 de septiembre de 2007, 19:57
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemShowEmphasisLevel extends JRadioButtonMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = -111040378654277061L;

    public JRadioButtonMenuItemShowEmphasisLevel(String signalName) {
        super(new ShowEmphasisLevelAction(signalName));
        if (JSWBManager.getJSWBManagerInstance().getSignalHasEmphasis(signalName)) {
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
