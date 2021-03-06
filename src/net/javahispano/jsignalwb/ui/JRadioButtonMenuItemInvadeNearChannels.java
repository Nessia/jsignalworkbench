/*
 * JRadioButtonMenuItemInvadeNearChannels.java
 *
 * Created on 30 de agosto de 2007, 7:47
 */

package net.javahispano.jsignalwb.ui;

import javax.swing.JRadioButtonMenuItem;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.ChannelProperties;

/**
 *
 * @author Roman Segador
 */
public class JRadioButtonMenuItemInvadeNearChannels extends JRadioButtonMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = -4690496177120502016L;

    public JRadioButtonMenuItemInvadeNearChannels(JSWBManager jswbManager, ChannelProperties channelProperties) {
        super(new InvadeNearChannelsAction(jswbManager, channelProperties));

        if (channelProperties.isInvadeNearChannels()) {
            setSelected(true);
        } else {
            setSelected(false);
        }
        if (!JSWBManager.getSignalManager().isSignalVisible(channelProperties.getName())) {
            setEnabled(false);
        }
    }

    @Override
    public String getActionCommand() {
       return isSelected()? "true" : "false";
    }

}
