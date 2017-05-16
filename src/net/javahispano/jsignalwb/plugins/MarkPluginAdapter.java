/*
 * MarkPluginAdapter.java
 *
 * Created on 4 de julio de 2007, 15:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.*;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;

/**
 *
 * @author Roman
 */
public abstract class MarkPluginAdapter extends PluginAdapter implements MarkPlugin {
    /**
     *
     */
    private static final long serialVersionUID = -5878389345856236748L;

    /*
     * Atributos
     */
    protected Signal signal = null;


    @Override
    public boolean isInterval() {
        return false;
    }

    @Override
    public long getEndTime() {
        throw new UnsupportedOperationException("Try to access a mark end time in a instant mark");
    }

    @Override
    public void setEndTime(long endTime) {
        throw new UnsupportedOperationException("Try to set a mark end time in a instant mark");
    }

    @Override
    public void showMarkInfo(Window owner) {
        JOptionPane.showMessageDialog(owner, TimeRepresentation.timeToString(getMarkTime()));
    }

    @Override
    public String getToolTipText() {
        return getName();
    }

    @Override
    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public Signal getSignal() {
        return signal;
    }

    @Override
    public boolean isOwnPainted() {
        return false;
    }

    @Override
    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        throw new UnsupportedOperationException("Try to paint a mark that isn't ownPainted. " +
                                                "Set isOwnPainted false or override the paint method");
    }

    @Override
    public Image getImage() {
        throw new UnsupportedOperationException("Try to get the image of a mark that is ownPaintd. " +
                                                "Set isOwnPainted true or override the getImage method.");
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

}
