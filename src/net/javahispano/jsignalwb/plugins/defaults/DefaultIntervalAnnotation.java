/*
 * DefaultIntervalAnnotation.java
 *
 * Created on 18 de julio de 2007, 13:06
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Roman Segador
 */
public class DefaultIntervalAnnotation extends AnnotationPluginAdapter {

    /*
     * Atributos
     */

    private long endTime;

    private int height = 1;
    private int width = 1;
    private Graphics2D g2d;
    private int barHeight = 4;
    private Color fontColor = Color.blue;

    /*
     * Constructor
     */

    public DefaultIntervalAnnotation() {
        super();
        category = "Interval";
        isImage = false;
        this.refreshBufferedImage();
    }

    /*
     * Métodos
     */


/**/@Override
    public boolean isInterval() {
        return true;
    }

    @Override
    public String getName() {
        return "Default Interval Annotation";
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultIntervalAnnotationInfoPanel(this).showJWindow(owner);
    }

    @Override
    protected void refreshBufferedImage() {
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
               g2d.drawString(title, 6, barYPosition - 2);
           }
       } else {
           g2d.fillRect(0, 0, this.width, height);
       }
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

    //  public Image getDefaultImage() {
    //  return new ImageIcon(DefaultIntervalAnnotation.class.getResource(
    //          "images/defaultIconMark.png")).getImage();
    //}

}
