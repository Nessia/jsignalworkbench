/*
 * DefaultIntervalMark.java
 *
 * Created on 5 de julio de 2007, 21:22
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
//import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class DefaultIntervalMark extends MarkPluginAdapter {

    private long endTime;
    private int extraHeightPixels = 10;
    //private MarkPaintInfo markPaintInfo;
    private int innerTransparencyLevel = 50;
    private int borderTransparencyLevel = 150;

    /*
     * Constructor
     */

    public DefaultIntervalMark() {
        super();
        endTime = 0;
        color = Color.GREEN;
        refreshBufferedImage();
    }

    @Override
    public String getName() {
        return "Default Interval Mark";
    }

    @Override
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean isInterval() {
        return true;
    }

    @Override
    public long getEndTime() {
        return endTime;
    }

    @Override
    public void showMarkInfo(Window owner) {
        new DefaultIntervalMarkInfoPanel(signal, this).showJWindow(owner);
    }

    @Override
    public boolean isOwnPainted() {
        return true;
    }

    @Override
    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        //if(this.markPaintInfo==null || !this.markPaintInfo.equals(markPaintInfo)){
        //this.markPaintInfo = markPaintInfo;
        Stroke oldStroke = g2d.getStroke();
        Color color2 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), innerTransparencyLevel);
        Color color3 = new Color(color.getRed(), color.getGreen(),
                                 color.getBlue(), borderTransparencyLevel);
        int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
                                  markPaintInfo.getMaxValueY());
        int minY = (int) Math.min(markPaintInfo.getPoint().getY() +
                                  markPaintInfo.getHeight(),
                                  markPaintInfo.getMinValueY());
        //g2d.fillRect((int)markPaintInfo.getPoint().getX(),maxY,markPaintInfo.getWidth(),minY-maxY);
        g2d.setColor(color3);

        g2d.setStroke(new BasicStroke(3));

        int x = markPaintInfo.getPoint().x;
        int y = maxY - extraHeightPixels - 2;
        int width = markPaintInfo.getWidth();
        int height = minY - maxY + 2 * extraHeightPixels + 3;
        g2d.draw(new java.awt.geom.RoundRectangle2D.Float(x - 2, y, width + 3,
                height, 15, 15));
        g2d.setColor(color2);
        g2d.fillRect(x, y, width, height);
        //dejamos las cosas tal y como estaban
        g2d.setStroke(oldStroke);
        // }
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public String getDataToSave() {
        return "title:" + title + "|| comentary:" + comentary + "|| color:" +
                color.getRGB();
    }

    @Override
    public void setSavedData(String d) {
        String data = d.substring(d.indexOf("title:") + 6);
        title = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("comentary:") + 10);
        comentary = data.substring(0, data.indexOf("||"));
        data = data.substring(data.indexOf("color:") + 6);
        color = new Color(Integer.parseInt(data));
        refreshBufferedImage();
    }

    @Override
    public String getToolTipText() {
        return title;
    }


    /**
     * getExtraheightPixels
     *
     * @return Numero de pixeles que se sumaran y restaran a los valores
     *   maximos y minimos, respectivamente, del intervalo de la marca.
     */
    public int getExtraheightPixels() {
        return extraHeightPixels;
    }

    public int getInnerTransparencyLevel() {
        return innerTransparencyLevel;
    }

    public int getBorderTransparencyLevel() {
        return borderTransparencyLevel;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        refreshBufferedImage();
    }

    @Override
    public Color getColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     *
     * @param extraheightPixels Numero de pixeles que se sumaran y restaran a
     *   los valores maximos y minimos, respectivamente, del intervalo de la
     *   marca.
     */
    public void setExtraheightPixels(int extraheightPixels) {
        this.extraHeightPixels = extraheightPixels;
    }

    /**
     * Un valor de transparencia para la marca entre cero y 255.
     *
     * @param innerTransparencyLevel int
     */
    public void setInnerTransparencyLevel(int innerTransparencyLevel) {
        this.innerTransparencyLevel = innerTransparencyLevel;
    }

    /**
     * Un valor de transparencia para el borde de la marca entre cero y 255.
     *
     * @param innerTransparencyLevel int
     */
    public void setBorderTransparencyLevel(int borderTransparencyLevel) {
        this.borderTransparencyLevel = borderTransparencyLevel;
    }

    protected void refreshBufferedImage() {
        im = new BufferedImage(5, 15, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = im.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 5, 15);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof DefaultIntervalMark){
            DefaultIntervalMark i = (DefaultIntervalMark)obj;
            if(i.endTime != this.endTime){
                return false;
            }
        }
        return super.equals(obj);
    }
}
