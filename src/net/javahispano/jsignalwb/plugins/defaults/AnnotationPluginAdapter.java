/*
 * AnnotationPluginAdapter.java
 *
 * Created on 17 de julio de 2007, 19:25
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.plugins.*;

/**
 *
 * @author Roman Segador
 */
public abstract class AnnotationPluginAdapter extends PluginAdapter implements AnnotationPlugin {

    /**
     *
     */
    private static final long serialVersionUID = 3360694961821151744L;

    @Override
    public boolean isInterval() {
        return false;
    }

    @Override
    public long getEndTime() {
        throw new UnsupportedOperationException("Try to access a mark end time in a instant Annotation");
    }

    @Override
    public void setEndTime(long endTime) {
        throw new UnsupportedOperationException("Try to set a mark end time in a instant Annotation");
    }

    @Override
    public void showMarkInfo(Window owner) {
        JOptionPane.showMessageDialog(owner, TimeRepresentation.timeToString(getAnnotationTime()));
    }

    @Override
    public String getToolTipText() {
        return getName();
    }

    @Override
    public String getCategory() {
        return "Unknown";
    }

    @Override
    public void setJSWBManager(JSWBManager jswbManager) {
        // Vacio
    }

    @Override
    public boolean isOwnPainted() {
        return false;
    }

    @Override
    public void paint(Graphics2D g2d, Point p, int height, int widht) {
        throw new UnsupportedOperationException("Try to paint a mark that isn't ownPainted. " +
                                                "Set isOwnPainted false or override the paint method");
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions.equals(GUIPositions.MENU);
    }

}
