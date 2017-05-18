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
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;

/**
 *
 * @author Roman
 */
public abstract class MarkPluginAdapter extends PluginAdapter implements MarkPlugin, Comparable<MarkPlugin> {

    /*
     * Atributos
     */

    protected Signal signal = null;
    protected Color color;
    protected long markTime;
    protected String commentary;
    protected String title;
    protected BufferedImage im;
    protected JSWBManager jswbManager;


    protected MarkPluginAdapter(){
       markTime = 0;
       title = "Write here the mark title...";
       commentary = "Write here your commentary....";
       jswbManager = null;
    }

    public void setJSWBManager(JSWBManager jswbManager) {
       this.jswbManager = jswbManager;
    }

    public JSWBManager getJSWBManager() {
       return jswbManager;
    }

    public void setCommentary(String commentary) {
       this.commentary = commentary;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Image getImage() {
        return im;
    }

    @Override
    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    @Override
    public long getMarkTime() {
        return markTime;
    }

    public Color getColor() {
       return color;
   }

    public void setColor(Color color) {
       this.color = color;
    }

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
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

    @Override
    public int compareTo(MarkPlugin i) {
        if (i.getMarkTime() < this.getMarkTime()) {
            return 1;
        } else if (i.getMarkTime() > this.getMarkTime()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MarkPlugin){
            MarkPlugin i = (MarkPlugin) obj;
            return i.getMarkTime() == this.getMarkTime();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (int) (this.getMarkTime() | this.getEndTime());
    }
}
