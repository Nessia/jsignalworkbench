/*
 * DefaultInstantAnnotation.java
 *
 * Created on 17 de julio de 2007, 19:49
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import net.javahispano.jsignalwb.JSWBManager;

/**
 *
 * @author Roman Segador
 */
public class DefaultInstantAnnotation extends AnnotationPluginAdapter {

    private static final Logger LOGGER = Logger.getLogger(DefaultInstantAnnotation.class.getName());

    private long annotationTime;
    private String title;
    private String comentary;
    private String category;
    private Color color;
    private Image image;
    private BufferedImage bufferedImage;
    private boolean isImage;
    private String imagePath;
    private JSWBManager jswbManager;

    public DefaultInstantAnnotation() {
        annotationTime = 0;
        title = "Write here the annotation title...";
        comentary = "Write here your comentary....";
        color = Color.RED;
        imagePath = "default";
        image = getDefaultImage();
        jswbManager = null;
        category = "Instant";
        setIsImage(false);
    }

    @Override
    public String getName() {
        return "Default Instant Annotation";
    }

    @Override
    public long getAnnotationTime() {
        return annotationTime;
    }

    @Override
    public Image getImage() {
        return bufferedImage;
    }

    @Override
    public void setAnnotationTime(long annotationTime) {
        this.annotationTime = annotationTime;
    }

    @Override
    public void setJSWBManager(JSWBManager jswbManager) {
        this.jswbManager = jswbManager;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultInstantAnnotationPanel(this).showJWindow(owner);
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
                imagePath + " || isImage:" + isImage + "|| color:" +
                color.getRGB() + "|| category: " + category;
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
        color = new Color(Integer.parseInt(data.substring(0, data.indexOf("||"))));
        data = data.substring(data.indexOf("category:") + 9);
        category = data;
        LOGGER.log(Level.INFO, imagePath);
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

    public Image getDefaultImage() {
        return new ImageIcon(DefaultInstantAnnotation.class.getResource(
                "images/defaultIconMark.png")).getImage();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        refreshBufferedImage();
    }

    public void setImageToShow(Image image) {
        this.image = image;
        this.setIsImage(true);
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
        //System.out.println("refresco la imagen");
        if (isImage) {
            bufferedImage = new BufferedImage(image.getHeight(null), image.getWidth(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, image.getHeight(null), image.getWidth(null), null);
        } else {
            bufferedImage = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(color);
            g2d.fillRect(0, 0, 5, 15);
        }
    }

}
