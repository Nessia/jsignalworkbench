/*
 * DefaultGridPlugin.java
 *
 * Created on 7 de agosto de 2007, 1:10
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;

import net.javahispano.jsignalwb.jsignalmonitor.*;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class DefaultGridPlugin extends GridPluginAdapter {

    /**
     *
     */
    private static final long serialVersionUID = -3897120822217764427L;
    private JSignalMonitorGrid jsmGrid;


    public DefaultGridPlugin() {
        jsmGrid = new DefaultGrid();
    }

    @Override
    public String getName() {
        return "Default grid plugin";
    }

    @Override
    public void launchConfigureGridGUI(Window owner) {
        jsmGrid.launchConfigureGridGUI(owner);
    }

    @Override
    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridConfig) {
        jsmGrid.paintGrid(g2d, p, height, width, gridConfig);
    }

    @Override
    public int getLeyendHeight() {
        return jsmGrid.getLeyendHeight();
    }

    @Override
    public int getLeyendWidth() {
        return jsmGrid.getLeyendWidth();
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public String getDataToSave() {
        return jsmGrid.getDataToSave();
    }

    @Override
    public void setSavedData(String data) {
        jsmGrid.setSavedData(data);
    }
}
