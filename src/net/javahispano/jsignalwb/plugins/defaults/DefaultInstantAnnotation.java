/*
 * DefaultInstantAnnotation.java
 *
 * Created on 17 de julio de 2007, 19:49
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Roman Segador
 */
public class DefaultInstantAnnotation extends AnnotationPluginAdapter {

    /*
     * Constructor
     */

    public DefaultInstantAnnotation() {
        super();
        category = "Instant";
        setIsImage(false);
    }


    /*
     * Métodos
     */

    @Override
    public boolean isInterval() {
        return false;
    }

    @Override
    public String getName() {
        return "Default Instant Annotation";
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultInstantAnnotationInfoPanel(this).showJWindow(owner);
    }

    @Override
    protected void refreshBufferedImage() {
        //System.out.println("refresco la imagen");
        if (isImage) {
            im = new BufferedImage(image.getHeight(null), image.getWidth(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = im.createGraphics();
            g2d.drawImage(image, 0, 0, image.getHeight(null), image.getWidth(null), null);
        } else {
            im = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = im.createGraphics();
            g2d.setColor(color);
            g2d.fillRect(0, 0, 5, 15);
        }
    }

    public void setImageToShow(Image image) {
        this.image = image;
        this.setIsImage(true);
    }

    //  public Image getDefaultImage() {
    //  return new ImageIcon(DefaultInstantAnnotation.class.getResource(
    //          "images/defaultIconMark.png")).getImage();
    //}
}
