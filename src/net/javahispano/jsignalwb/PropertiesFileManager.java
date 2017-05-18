/*
 * PropertiesFileManager.java
 *
 * Created on 30 de julio de 2007, 17:44
 */

package net.javahispano.jsignalwb;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Roman Segador
 */
class PropertiesFileManager {

    private static final Logger LOGGER = Logger.getLogger(PropertiesFileManager.class.getName());

    private File propertiesFile;
    public PropertiesFileManager() {

        try {
            File f = new File(System.getProperty("user.home") + "/.JSignalWorkBench");
            if (!f.exists()) {
                f.mkdir();
            }
            propertiesFile = new File(f, "jswbProperties.prop");
            if (!propertiesFile.exists()) {
                boolean creado = propertiesFile.createNewFile();
                if(!creado){
                   throw new IOException("No se pudo crear el fichero");
                }
                Document doc = new Document(new Element("root"));
                XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
                FileOutputStream file = new FileOutputStream(propertiesFile);
                out.output(doc, file);
                file.flush();
                file.close();
            }

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Imposible to create the properties file. The configuration data won't be remembered", ex);
            JOptionPane.showMessageDialog(null,
                    "Imposible to create the properties file. The configuration data won't be remembered");
        }
    }

    void deleteUninstallPlugins() {
        if (propertiesFile.exists()) {
            try {
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(propertiesFile);
                @SuppressWarnings("unchecked")
                Iterator<Element> it = doc.getRootElement().getChildren("PluginToDelete").iterator();
                while (it.hasNext()) {
                    Element elem = it.next();
                    File fd = new File(elem.getAttribute("path").getValue());
                    if (fd.exists() && !fd.delete()){
                        throw new IOException("No se ha podido borarr el fichero");
                    }
                }
            } catch (JDOMException ex) {
               LOGGER.log(Level.SEVERE, "No se han podido eliminar los plugins desinstalados", ex);
            } catch (IOException ex) {
               LOGGER.log(Level.SEVERE, "No se han podido eliminar los plugins desinstalados", ex);
            }
        }
    }

    void savePropertiesFile(SessionInfo sessionInfo) {
        try {
            if (propertiesFile.exists()) {
                Element root = new Element("root");
                Document doc = new Document(root);
                Element lastFile = new Element("LastFile");
                lastFile.setAttribute("path", sessionInfo.getLastFileOpenedPath());
                Element lastLoader = new Element("LastLoader");
                lastLoader.setAttribute("name", sessionInfo.getLastLoaderUsed());
                Element lastSaver = new Element("LastSaver");
                lastSaver.setAttribute("name", sessionInfo.getLastSaverUsed());
                Element debug = new Element("Debug");
                debug.setAttribute("value", String.valueOf(sessionInfo.isDebugMode()));
                root.addContent(lastFile);
                root.addContent(lastLoader);
                root.addContent(lastSaver);
                root.addContent(debug);
                List<String> plugins = sessionInfo.getPluginsToDelete();
                for (String s : plugins) {
                    Element element = new Element("PluginToDelete");
                    element.setAttribute("path", s);
                    root.addContent(element);
                }

                XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
                FileOutputStream file = new FileOutputStream(propertiesFile);

                out.output(doc, file);
                file.flush();
                file.close();

            }
        } catch (IOException ex) {
           LOGGER.log(Level.SEVERE, "No se han podido guardar las propiedades", ex);
        }
        EnvironmentConfiguration.getInstancia().almacenaADisco();
    }

    SessionInfo loadPropertiesFile() {
        SessionInfo sessionInfo = new SessionInfo();
        if (propertiesFile.exists()) {
            try {
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(propertiesFile);
                Element root = doc.getRootElement();
                Element lastFile = root.getChild("LastFile");
                Element lastLoader = root.getChild("LastLoader");
                Element lastSaver = root.getChild("LastSaver");
                Element debug = root.getChild("Debug");
                if (lastFile != null) {
                    sessionInfo.setLastFileOpenedPath(lastFile.getAttribute("path").getValue());
                }
                if (lastLoader != null) {
                    sessionInfo.setLastLoaderUsed(lastLoader.getAttribute("name").getValue());
                }
                if (lastSaver != null) {
                    sessionInfo.setLastSaverUsed(lastSaver.getAttribute("name").getValue());
                }
                if (debug != null) {
                    sessionInfo.setDebugMode(Boolean.valueOf(debug.getAttribute("value").getValue()));
                } else {
                    sessionInfo.setDebugMode(false);
                }
            } catch (JDOMException ex) {
               LOGGER.log(Level.SEVERE, "No se ha podido cargar el fichero de propiedades", ex);
            } catch (IOException ex) {
               LOGGER.log(Level.SEVERE, "No se ha podido cargar el fichero de propiedades", ex);
            }
        }
        EnvironmentConfiguration.getInstancia();
        return sessionInfo;
    }
}
