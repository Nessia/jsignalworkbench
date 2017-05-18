/*
 * DefaultInstantMark.java
 *
 * Created on 4 de julio de 2007, 15:57
 *
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;


/**
 *
 * @author Roman
 */
public class DefaultInstantMark extends MarkPluginAdapter {

    private Image image;
    private boolean isImage;
    private String imagePath;

    /** Creates a new instance of DefaultInstantMark */
    public DefaultInstantMark() {
        super();
        color = Color.RED;
        imagePath = "default";
        image = getDefaultImage();
        setIsImage(false);
    }

    @Override
    public String getName() {
        return "Default Instant Mark";
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultInstantMarkInfoPanel(signal, this).showJWindow(owner);
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
            im = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = im.createGraphics();
            g2d.drawImage(image, 0, 0, 15, 15, null);
        } else {
            im = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = im.createGraphics();
            g2d.setColor(color);
            g2d.fillRect(0, 0, 5, 15);
        }
    }

    public Image getDefaultImage() {
        return new ImageIcon(DefaultInstantMark.class.getResource(
                "images/defaultIconMark.png")).getImage();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
