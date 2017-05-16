package net.javahispano.plugins.signalgeneration;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;
import net.javahispano.plugins.temporalseries.TemporalSeries;

public class SignalGenerationPlugin extends GenericPluginAdapter {
    /**
     *
     */
    private static final long serialVersionUID = 5103745015634630202L;

    public SignalGenerationPlugin() {
        // Empty
    }

    @Override
    public String getName() {
        return "Signal Generation";
    }

    @Override
    public void launch(JSWBManager jswbManager) {
        //antes de lanzar la interfaz gr\u2663fica debemos asegurarnos que
        //todas las senhales son series temporales
        TemporalSeries.convertSignalsToTemporalSeries(JSWBManager.getSignalManager());
        SignalGeneration d = new SignalGeneration();
        d.setSize(680, 420);
        d.setLocationRelativeTo(JSWBManager.getParentWindow());
        d.setVisible(true);
    }

    @Override
    public String getShortDescription() {
        return "Permite generar senhales artificiales para testeo";
    }

    @Override
    public String getDescription() {
        return "Plugin que permite generar una senhal artificial a partir de la suma de un conjunto de senos de amplitud, frecuencia y desfase configurable. Tambien permite anhadir ruido blanco de amplitud configurable a la senhal final.";
    }

    @Override
    public Icon getIcon() {
        Image logo = Toolkit.getDefaultToolkit().createImage(
                SignalGeneration.class.getResource("logo.gif"));
        return new ImageIcon(logo);
    }

    @Override
    public String getPluginVersion() {
        return "0.5";
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }
}
