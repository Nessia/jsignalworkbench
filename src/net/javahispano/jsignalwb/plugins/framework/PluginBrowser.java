/*
 * PluginBrowser.java
 *
 * Created on 28 de marzo de 2007, 12:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.framework.ExceptionsCollector;

/**
 *
 * @author Roman
 */
public class PluginBrowser {

    private static final Logger LOGGER = Logger.getLogger(PluginBrowser.class.getName());

    /** Creates a new instance of PluginBrowser */
    private PluginBrowser() {
        // Empty
    }

    static public File[] searchSearch(File f) {
        if (f.isDirectory()) {
            return f.listFiles(new FileFilter() {
                @Override
                public boolean accept(File fileToBeFiltered) {
                    return fileToBeFiltered.getName().toLowerCase().endsWith(".jar");
                }
            });
        }
        return null;
    }

    /** Objetivo --> Buscar en el directorio f todos aquellos archivos con extension
     *               jar que deben corresponderse con plugins de la aplicacion.
     *               El metodo anhadira a pluginAsociation, para cada plugin encontrado,
     *               el par nombre del plugin, path de la clase.
     *               A su vez el metodo devuelve el classLoader del sistema con el
     *               classpath de los plugins anhadidos*/

    static public ClassLoader install(PluginManager pm, ClassLoader classLoader,
                                      File[] files, ExceptionsCollector ec) {

         if(files == null){
             return classLoader;
         }

         ArrayList<URL> urls = new ArrayList<URL>();
         for (int i = 0; i < files.length; i++) {
             try {
                 JarFile jar = validateJar(files[i], pm);
                 File file = new File(System.getProperty("user.home") + "/.JSignalWorkBench/" + files[i].getName());
                 if (!file.exists() && !file.createNewFile()){
                     throw new IOException("No se ha podido crear el fichero");
                 }
                 copy(files[i], file);
                 jar.close();
                 jar = new JarFile(file);
                 loadPlugin(jar, pm);
                 urls.add(file.toURI().toURL());
                 jar.close();
             } catch (IOException ex) {
                 ec.addException(ex);
             }
             /*catch (MalformedURLException ex) {
              ec.addException(new PluginLoadException(
                      "URL malformed on: )" + files[i].getPath(),
                      new RuntimeException()));
                                 }*/ catch (PluginLoadException ple) {
                ec.addException(ple);
            }
         }
         return new URLClassLoader(urls.toArray(new URL[urls.size()]), classLoader);


    }

    public static void loadPlugin(JarFile f, PluginManager pm) {
        try {
            Attributes att = f.getManifest().getMainAttributes();
            String pluginType = att.getValue("PluginType");
            String pluginName = att.getValue("PluginName");
            String pluginBaseClass = att.getValue("PluginBaseClass");
            if (pm.isPluginRegistered(pluginType, pluginName)) {
                throw new PluginLoadException("Another plugin with the name \"" +
                                              pluginName +
                                              "\" is already registered",
                                              new RuntimeException());
            }
            if (!pm.registerPlugin(pluginType, pluginName, pluginBaseClass)) {
                throw new PluginLoadException("Can't register plugin \"" +
                                              f.getName() + "\"",
                                              new RuntimeException());
            }
            if (att.getValue("Image") != null) {
                pm.registerIcon(pluginType, pluginName, att.getValue("Image"));
            }

        } catch (IOException ex) {
            throw new PluginLoadException("Error loading manifest file at: " +
                                          f.getName(), ex);
        }
    }

    static public JarFile validateJar(File f, PluginManager pm) throws
            PluginLoadException {
        JarFile currentFile = null;
        try {
            currentFile = new JarFile(f);
            Attributes attributes =
                    currentFile.getManifest().getMainAttributes();
            String pluginType =
                    attributes.getValue("PluginType");
            String pluginName =
                    attributes.getValue("PluginName");
            String pluginBaseClass =
                    attributes.getValue("PluginBaseClass");
            if (pluginName == null || pluginBaseClass == null || pluginType == null) {
                throw new PluginLoadException("The Manifest file of" +
                                              currentFile.getName() +
                                              " plugin is wrong",
                                              new RuntimeException());
            } else {
                if (pm.isPluginRegistered(pluginType, pluginName)) {
                    throw new PluginLoadException("Another plugin with the name " +
                                                  pluginName + " is already installed", new RuntimeException());
                }
            }
        } catch (IOException ex) {
            throw new PluginLoadException("Can`t load file: \" " + f +
                                          "\". Wrong Jar file", ex);
        } finally {
            try{
            if(currentFile != null) currentFile.close();
            } catch(Exception e){
                 LOGGER.log(Level.FINEST, e.getMessage(), e);
            }
        }
        return currentFile;
    }

    static public void copy(File source, File dest) throws IOException {
        FileInputStream fIn = null;
        FileOutputStream fOut = null;
        try {
            FileChannel in = null;
            FileChannel out = null;
            fIn = new FileInputStream(source);
            in = fIn.getChannel();
            fOut = new FileOutputStream(dest);
            out = fOut.getChannel();

            long size = in.size();
            MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);

            out.write(buf);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } finally {
            if (fIn != null) {
                try {
                    fIn.close();
                } catch (IOException e) {
                    LOGGER.log(Level.FINEST, e.getMessage(), e);
                }
            }
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    LOGGER.log(Level.FINEST, e.getMessage(), e);
                }
            }
        }
    }
}
