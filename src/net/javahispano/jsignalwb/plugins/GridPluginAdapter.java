package net.javahispano.jsignalwb.plugins;

import java.awt.Window;

import net.javahispano.jsignalwb.Signal;

public abstract class GridPluginAdapter extends PluginAdapter implements GridPlugin {
    /**
     * Contiene la senhal sobre la cual se esta mostrando este grid.
     */
    protected Signal signal;

    public GridPluginAdapter() {
        // Vacio
    }

    @Override
    public void setSignal(Signal s) {
        signal = s;
    }

    @Override
    public void launchConfigureGridGUI(Window owner) {
        // Vacoi
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

    @Override
    public int getLeyendHeight() {
        return 0;
    }

    @Override
    public int getLeyendWidth() {
        return 0;
    }

}
