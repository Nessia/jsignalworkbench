/*
 * DefaultInstantMark.java
 *
 * Created on 4 de julio de 2007, 15:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;

//import net.javahispano.jsignalwb.Signal;

/**
 *
 * @author Roman
 */
public class DefaultInstantMark extends MarkPluginAdapter {


    /**
     *
     */
    private static final long serialVersionUID = 7265474046307101524L;

    private long markTime;
    private String title;
    private String comentary;
//    private Signal signal;
    private Color color;
    private Image image;
    private BufferedImage bufferedImage;
    private boolean isImage;
    private String imagePath;
    private JSWBManager jswbManager;
    /** Creates a new instance of DefaultInstantMark */
    public DefaultInstantMark() {
        markTime = 0;
        title = "Write here the mark title...";
        comentary = "Write here your comentary....";
        color = Color.RED;
        imagePath = "default";
        image = getDefaultImage();
        jswbManager = null;
        setIsImage(false);
    }

    @Override
    public String getName() {
        return "Default Instant Mark";
    }

    @Override
    public void setMarkTime(long markTime) {
        this.markTime = markTime;
    }

    @Override
    public long getMarkTime() {
        return markTime;
    }

    @Override
    public Image getImage() {
        return bufferedImage;
    }

//    public void setSignal(Signal signal){
//        this.signal=signal;
//    }

    public void setJSWBManager(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultInstantMarkInfoPanel(signal, this).showJWindow(owner);
    }

    public JSWBManager getJSWBManager() {
        return jswbManager;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public String getComentary() {
        return comentary;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public String getDataToSave() {
        return "title:" + title + "|| comentary:" + comentary + " || icon:" +
                imagePath + " || isImage:" + isImage + "|| color:" + color.getRGB();
    }

    @Override
    public void setSavedData(String d) {
        String data = d;
        data = data.substring(data.indexOf("title:") + 6);
        title = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("comentary:") + 10);
        comentary = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("icon:") + 5);
        imagePath = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("isImage:") + 8);
        isImage = Boolean.parseBoolean(data.substring(0, data.indexOf("||")));
        data = data.substring(data.indexOf("color:") + 6);
        color = new Color(Integer.parseInt(data));
        if (!"default".equals(imagePath.trim())) {
            image = new ImageIcon(imagePath).getImage();
        } else {
            image = getDefaultImage();
        }
        refreshBufferedImage();
    }

    @Override
    public String getToolTipText() {
        return title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        //refreshBufferedImage();
        //bufferedImage=new BufferedImage(5,15,BufferedImage.TYPE_INT_RGB);
        //Graphics2D g2d=bufferedImage.createGraphics();
        //g2d.setColor(color);
        //g2d.fillRect(0,0,5,15);
    }

    public void setImageToShow(Image image) {
        this.image = image;
        //refreshBufferedImage();
    }

    public Image getImageToShow() {
        return image;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
        refreshBufferedImage();
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private void refreshBufferedImage() {
        if (isImage) {
            bufferedImage = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, 15, 15, null);
        } else {
            bufferedImage = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(color);
            g2d.fillRect(0, 0, 5, 15);
        }
    }

    public Image getDefaultImage() {
        return new ImageIcon(DefaultInstantMark.class.getResource(
                "images/defaultIconMark.png")).getImage();
    }
}
