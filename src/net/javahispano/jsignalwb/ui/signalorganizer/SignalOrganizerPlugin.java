package net.javahispano.jsignalwb.ui.signalorganizer;

import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;
import net.javahispano.jsignalwb.plugins.GenericPluginAdapter;

public class SignalOrganizerPlugin extends GenericPluginAdapter implements SignalSizeListener {
    /**
     *
     */
    private static final long serialVersionUID = 1739598967455906361L;

    private static final Logger LOGGER = Logger.getLogger(SignalOrganizerPlugin.class.getName());
    private SignalManager signalManager = JSWBManager.getSignalManager();

    public SignalOrganizerPlugin() {
        signalManager.addListener(this);
    }

    //se ejecuta en el EDT
    @Override
    public void launch(JSWBManager jswbManager) {
        JSignalMonitor jSignalMonitor = JSWBManager.getJSignalMonitor();
        for (String signalName : signalManager.getSignalsNames()) {
            Signal signal = signalManager.getSignal(signalName);
            LOGGER.log(Level.INFO, "%s", signal.getName() + " visible: " + signal.isVisible()
                               + "Posicion en pantalla: " + jSignalMonitor.getChannelPosition(signalName));
        }

        signalManager.hideAllSignals();

        for (String signalName : signalManager.getSignalsNames()) {
            signalManager.setSignalVisible(signalName, true);
        }
    }

    @Override
    public String getDataToSave() {
        return "Datos que quiero guardar en la sesion de trabajo";
    }

    @Override
    public void setSavedData(String dataSavedOnTheWorkingSession) {
        LOGGER.log(Level.INFO, "%s", dataSavedOnTheWorkingSession);
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public void signalSizeActionPerformed(SignalSizeEvent event) {
        if (event.isSignalsReset()) {
            return;
        }
       if (event.isSignalAdded()) {
           LOGGER.log(Level.INFO, "%s", "Anhadiendo senhal con nombre: " + event.getSignal().getName());
        } else {
           LOGGER.log(Level.INFO, "%s", "Eliminando senhal con nombre: " + event.getSignal().getName());
        }

    }

//no debera ser necesario tocarlo de aqui abajo
    @Override
    public String getName() {
        return "Signal Organizer";
    }

    @Override
    public String getShortDescription() {
        return "Configures the display order of sigals";
    }

    @Override
    public String getDescription() {
        return this.getShortDescription();
    }

    @Override
    public String getPluginVersion() {
        return "1.0";
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SignalOrganizerPlugin.class.getResource("organize.gif")));
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }
}
