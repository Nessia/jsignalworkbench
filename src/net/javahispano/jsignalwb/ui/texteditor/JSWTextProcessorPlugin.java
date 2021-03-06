package net.javahispano.jsignalwb.ui.texteditor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;

import javax.swing.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.io.SessionNotSavedException;
import net.javahispano.jsignalwb.plugins.GenericPlugin;

public class JSWTextProcessorPlugin extends WindowAdapter implements GenericPlugin, ActionListener, SessionListener {

    private File file = null;
    private ByteArrayOutputStream inMemoryDucyment;
    private JSWTextProcessor jSWTextProcessor = null;
    private Rectangle jSWTextProcessorBounds = null;
    private boolean fileIsInvalid = true;
    private boolean hasSetFileBeenInvokedBeforeThisNewSessionCreatedEvent = false;

    //Construct the application
    public JSWTextProcessorPlugin() {
        JSWBManager.getJSWBManagerInstance().addSessionListener(this);
    }

    @Override
    public String getName() {
        return "Text Processor";
    }

    @Override
    public String getShortDescription() {
        return "Basic editor of rtf documents";
    }

    @Override
    public String getDescription() {
        return "Basic editor of rtf documents";
    }

    @Override
    public String getPluginVersion() {
        return "1.0";
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                JSWTextProcessor.class.getResource("comentario.gif")));
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return false;
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        // vacio
    }

    @Override
    public String getDataToSave() {
        if (jSWTextProcessorBounds == null) {
            return "";
        }
        return ((int) jSWTextProcessorBounds.getX()) + "*" + ((int) jSWTextProcessorBounds.getY()) + "*" +
                ((int) jSWTextProcessorBounds.getWidth()) + "*" + ((int) jSWTextProcessorBounds.getHeight());
    }

    @Override
    public void setSavedData(String data) {
        if (!data.equals("")) {
            StringTokenizer st = new StringTokenizer(data, "*");
            jSWTextProcessorBounds = new Rectangle(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public boolean createFile() {
        return true;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
        jSWTextProcessor = new JSWTextProcessor(file, this, this);
        setFileIsInvalid(false, true);
        hasSetFileBeenInvokedBeforeThisNewSessionCreatedEvent = true;
    }

    private void fileNotSavedWarning(String message) {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(), message, "About", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void launch(JSWBManager jswbManager) {
        if (this.fileIsInvalid) {
            try {
                file = JSWBManager.getIOManager().createNameForPlugin(this);
            } catch (IOException ex1) {
                fileNotSavedWarning("It is not possible to create the file for the text processor.");
                ex1.printStackTrace();
            } catch (SessionNotSavedException ex) {
                fileNotSavedWarning("You have to save the work sesion before launching the text processor.");
                return;
            }
        }
        if (jSWTextProcessor == null) {
            jSWTextProcessor = new JSWTextProcessor(file, this, this);
        }
        if (inMemoryDucyment != null) {
            this.jSWTextProcessor.setInMemoryDucyment(inMemoryDucyment);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jSWTextProcessor.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        jSWTextProcessor.setLocation((screenSize.width - frameSize.width) / 2,
                                     (screenSize.height - frameSize.height) / 2);
        if (jSWTextProcessorBounds != null) {
            jSWTextProcessor.setBounds(jSWTextProcessorBounds);
        } else {
            jSWTextProcessor.setSize(400, 500);
            jSWTextProcessor.setLocationRelativeTo(JSWBManager.getParentWindow());
        }
        jSWTextProcessor.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        inMemoryDucyment = this.jSWTextProcessor.getInMemoryDucyment();
        jSWTextProcessorBounds = jSWTextProcessor.getBounds();
        JSWBManager.getJSWBManagerInstance().getSessionInfo().setSessionSaved(false);
        System.out.println("Intentar cerrandose");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        windowClosing(null);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return false;
    }

    /**
     * cuando se abre un nuevo archivo que no tiene informacion del plugin
     * no se ejecuta el mUtodo de carga de datos y este mtodo tampoco hace su
     * trabajo apropiadamente.
     * Hay que gestionar ese caso.
     * @param event SessionEvent
     */
    @Override
    public void sessionCreated(SessionEvent event) {
        if (hasSetFileBeenInvokedBeforeThisNewSessionCreatedEvent) {
            hasSetFileBeenInvokedBeforeThisNewSessionCreatedEvent = false;
        }
        this.setFileIsInvalid(true, !event.isSaveAs());
    }

    @Override
    public void sessionDestroyed(SessionEvent event) {
        setFileIsInvalid(true, true);
    }

    public boolean isFileIsInvalid() {
        return fileIsInvalid;
    }

    public void setFileIsInvalid(boolean fileIsInvalid, boolean reset) {
        this.fileIsInvalid = fileIsInvalid;
        file = null;
    }

    @Override
    public void sessionSaved(SessionEvent event) {
        if (this.isFileIsInvalid()) {
            try {
                this.setFileIsInvalid(false, false);
                file = JSWBManager.getIOManager().createNameForPlugin(this);
            } catch (IOException ex) {
            } catch (SessionNotSavedException ex) {
            }
        }
        if (jSWTextProcessor != null && file != null) {
            jSWTextProcessor.setCurrFileName(file.toString());
            jSWTextProcessor.saveFile();
        }
    }
}
