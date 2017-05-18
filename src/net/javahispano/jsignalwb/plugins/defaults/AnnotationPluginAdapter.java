/*
 * AnnotationPluginAdapter.java
 *
 * Created on 17 de julio de 2007, 19:25
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.plugins.*;

/**
 *
 * @author Roman Segador
 */
public abstract class AnnotationPluginAdapter extends PluginAdapter implements AnnotationPlugin {


    private static final Logger LOGGER = Logger.getLogger(AnnotationPluginAdapter.class.getName());

    protected long annotationTime;
    protected String title;
    protected String commentary;
    protected String category;
    protected Color color;
    protected Image image;
    protected BufferedImage im;
    protected boolean isImage;
    protected String imagePath;
    protected JSWBManager jswbManager;

    protected AnnotationPluginAdapter(){
       annotationTime = 0;
       title = "Write here the annotation title...";
       commentary = "Write here your commentary....";
       color = Color.RED;
       imagePath = "default";
       image = getDefaultImage();
       jswbManager = null;
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
    public long getEndTime() {
        throw new UnsupportedOperationException("Try to access a mark end time in a instant Annotation");
    }

    @Override
    public void setEndTime(long endTime) {
        throw new UnsupportedOperationException("Try to set a mark end time in a instant Annotation");
    }

    @Override
    public void showMarkInfo(Window owner) {
        JOptionPane.showMessageDialog(owner, TimeRepresentation.timeToString(getAnnotationTime()));
    }

    @Override
    public boolean isOwnPainted() {
        return false;
    }

    @Override
    public void paint(Graphics2D g2d, Point p, int height, int widht) {
        throw new UnsupportedOperationException("Try to paint a mark that isn't ownPainted. " +
                                                "Set isOwnPainted false or override the paint method");
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions.equals(GUIPositions.MENU);
    }

    @Override
    public String getToolTipText() {
        return title;
    }

    public void setColor(Color color) {
       this.color = color;
       this.refreshBufferedImage();
    }

    public Color getColor() {
       return color;
    }

    public Image getImageToShow() {
       return image;
    }

    public boolean isImage() {
        return isImage;
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

   public void setIsImage(boolean isImage) {
       this.isImage = isImage;
       refreshBufferedImage();
   }

   public void setImagePath(String imagePath) {
       this.imagePath = imagePath;
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


   public Image getDefaultImage() {
      return new ImageIcon(AnnotationPluginAdapter.class.getResource(
              "images/defaultIconMark.png")).getImage();
   }

   protected abstract void refreshBufferedImage();

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

}
