/*
 * PluginAdapter.java
 *
 * Created on 23 de mayo de 2007, 19:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 * Clase que implementa practicamente todos los metodos de {@link  Plugin}
 * proporcionando un comportamiento por efecto. Se recomienda extender esta
 * clase en vez de incrementar {@link Plugin}, especialmente para la
 * construccion de prototipos que no esten muy integrados con JSignalWorkbench.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public abstract class PluginAdapter implements Plugin {


    /**
     * Proporciona una descripcion corta del plugin
     *
     * @return Emplea el propio nombre del club y como descripcion
     */
    @Override
    public String getShortDescription() {
        return getName();
    }

    /**
     * Proporciona una descripcion mas extensa del plugin
     *
     * @return Emplea el propio nombre del club y como descripcion
     */
    @Override
    public String getDescription() {
        return getName();
    }

    /**
     * Proporciona un icono por defecto en caso de que el algoritmo no contenga
     * uno propio.
     *
     * @return Icon
     */
    @Override
    public Icon getIcon() {
        return generateImage(getName());
    }

    /**
     * Por defecto no proporciona una interfaz de usuario de configuracion
     *
     * @return false
     */
    @Override
    public boolean hasOwnConfigureGUI() {
        return false;
    }


    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        throw new UnsupportedOperationException();
    }

    /**
     * Por defecto devuelve la version 1.0
     *
     * @return 1.0
     */
    @Override
    public String getPluginVersion() {
        return "1.0";
    }

    /**
     * hasDataToSave
     *
     * @return False
     */
    @Override
    public boolean hasDataToSave() {
        return false;
    }


    /**
     * Proporciona datos de configuracion del algoritmo que deben de ser
     * guardados por el entorno.
     *
     * @return cadena de caracteres con los datos que debe guardar el
     *   entorno. Si el entorno no debe de guardar ninguna informacion sobre
     *   el plugin el valor de retorno sera null.
     */
    @Override
    public String getDataToSave() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSavedData(String data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createFile() {
        return false;
    }

    @Override
    public void setFile(File file) {
        throw new UnsupportedOperationException();
    }

    /**
     * Genera un icono con la primera y ultima letra de la cadena de texto que
     * se le pasa.
     *
     * @param nombre String
     * @return Icon
     */
    protected Icon generateImage(String name) {
         String nombre = name;
        if ("".equals(nombre)) {
            nombre = "No icon";
        }
        nombre = nombre.toUpperCase();
        BufferedImage bufferedImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        char first = nombre.charAt(0);
        char last = nombre.charAt(nombre.length() - 1);
        Graphics2D g2d = bufferedImage.createGraphics();
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 13);
        g2d.setBackground(Color.LIGHT_GRAY);
        g2d.clearRect(0, 0, 20, 20);
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(first), 1, 10);
        g2d.drawString(String.valueOf(last), 10, 20);
        return new ImageIcon(bufferedImage);
    }
    /**
     * Genera un icono con la primera y ultima letra de la cadena de texto que
     * se le pasa.
     *
     * @param nombre String
     * @return Icon
     */
    protected Icon generateImageSimple(String name, Color color) {
        String nombre = name;
        if ("".equals(nombre)) {
            nombre = "No icon";
        }
        nombre = nombre.toUpperCase();
        BufferedImage bufferedImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        char first = nombre.charAt(0);
        //char last = name.charAt(name.length() - 1);
        Graphics2D g2d = bufferedImage.createGraphics();
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        g2d.setBackground(Color.LIGHT_GRAY);
        g2d.clearRect(0, 0, 20, 20);
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(String.valueOf(first), 3, 18);
        return new ImageIcon(bufferedImage);
    }


}
