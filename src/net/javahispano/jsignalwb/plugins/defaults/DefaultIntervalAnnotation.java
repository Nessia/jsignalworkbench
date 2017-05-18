/*
 * DefaultIntervalAnnotation.java
 *
 * Created on 18 de julio de 2007, 13:06
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
public class DefaultIntervalAnnotation extends AnnotationPluginAdapter {

    private static final Logger LOGGER = Logger.getLogger(DefaultIntervalAnnotation.class.getName());

    private long annotationTime;
    private long endTime;
    private String title;
    private String commentary;
    private String category;
    private Color color;
    private Image image;
    private BufferedImage im;
    private boolean isImage;
    private String imagePath;
    private JSWBManager jswbManager;

    private int height = 1;
    private int width = 1;
    private Graphics2D g2d;
    private int barHeight = 4;
    private Color fontColor = Color.blue;

    public DefaultIntervalAnnotation() {
        annotationTime = 0;
        title = "Write here the annotation title...";
        commentary = "Write here your commentary....";
        color = Color.RED;
        imagePath = "default";
        image = getDefaultImage();
        jswbManager = null;
        isImage = false;
        category = "Interval";
        this.refreshBufferedImage();
    }

    @Override
    public String getName() {
        return "Default Interval Annotation";
    }

    @Override
    public long getAnnotationTime() {
        return annotationTime;
    }

    @Override
    public Image getImage() {
        return im;
    }

    @Override
    public void setAnnotationTime(long annotationTime) {
        this.annotationTime = annotationTime;
    }

    @Override
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public long getEndTime() {
        return endTime;
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
    public boolean isInterval() {
        return true;
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultIntervalAnnotationInfoPanel(this).showJWindow(owner);
    }

    public JSWBManager getJSWBManager() {
        return jswbManager;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCommentary() {
        return commentary;
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
        return "title:" + title + "|| commentary:" + commentary + " || icon:" +
                imagePath + " || isImage:" + isImage + "|| color:" +
                color.getRGB() + "|| category: " + category;
    }

    @Override
    public void setSavedData(String d) {
        String data = d;
        data = data.substring(data.indexOf("title:") + 6);
        title = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("commentary:") + 10);
        commentary = data.substring(0, data.indexOf("||"));
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

    public Color getColor() {
        return color;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setColor(Color color) {
        this.color = color;
        this.refreshBufferedImage();
    }

    public Image getImageToShow() {
        return image;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        refreshBufferedImage();
    }

    public void setWidth(int width) {
        this.width = width;
        refreshBufferedImage();
    }

    public void setHeight(int height) {
        this.height = height;
        refreshBufferedImage();
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        refreshBufferedImage();
    }

    public String getTextToShow() {
        return title;
    }

    private void refreshBufferedImage() {
        if (!isImage) {
            isImage = false;
            im = new BufferedImage(width, height,
                                              BufferedImage.TYPE_INT_RGB);
            g2d = im.createGraphics();
        }
        //borramos t.odo lo antiguo
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(color);
        //si es suficientemente largo para pintar el intervalo
        if (this.width > 15) {
            g2d.fillRect(0, 0, 4, height);
            g2d.fillRect(width - 4, 0, 4, height);
            int barYPosition = Math.min(2 * this.height / 3 - barHeight / 2,
                                        this.height - this.barHeight);
            g2d.fillRect(4, barYPosition, this.width - 8,
                         barHeight);
            if (height > 15) {
                g2d.setColor(fontColor);
                g2d.drawString(getTextToShow(), 6, barYPosition - 2);
            }
        } else {
            g2d.fillRect(0, 0, this.width, height);
        }
    }

    @Override
    public boolean isOwnPainted() {
        return true;
    }

    @Override
    public void paint(Graphics2D g2d, Point p, int height, int width) {
        if (height > 0 && width > 0 && (this.height != height || this.width != width)) {
            this.height = height;
            this.width = width;
            refreshBufferedImage();
        }
        g2d.drawImage(im, p.x, p.y, null);
    }

    public Image getDefaultImage() {
        return new ImageIcon(DefaultIntervalAnnotation.class.getResource(
                "images/defaultIconMark.png")).getImage();
    }

}
