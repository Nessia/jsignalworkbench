package tmp;

import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;

import java.awt.Color;
import java.awt.Window;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class PicoCerdo extends DefaultIntervalMark {

    /*
     * Constructores
     */

    public PicoCerdo() {
        //borderTransparencyLevel = 200;
        setTitle("Write here the mark title...");
        //comment = "Write here your comment....";
        color = Color.RED;
        refreshBufferedImage();
    }

    @Override
    public String getName() {
        return "Pico";
    }

    @Override
    public void showMarkInfo(Window owner) {
       //  new DefaultIntervalMarkInfoPanel(signal, this).showJWindow(owner);
    }

//    @Override
//    public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
//        //if(this.markPaintInfo==null || !this.markPaintInfo.equals(markPaintInfo)){
//        //this.markPaintInfo = markPaintInfo;
//        Stroke oldStroke = g2d.getStroke();
//        Color color2 = new Color(color.getRed(), color.getGreen(),
//                                 color.getBlue(), innerTransparencyLevel);
//        Color color3 = new Color(color.getRed(), color.getGreen(),
//                                 color.getBlue(), borderTransparencyLevel);
//        int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
//                                  markPaintInfo.getMaxValueY());
//        int minY = (int) Math.min(markPaintInfo.getPoint().getY() +
//                                  markPaintInfo.getHeight(),
//                                  markPaintInfo.getMinValueY());
//        //g2d.fillRect((int)markPaintInfo.getPoint().getX(),maxY,markPaintInfo.getWidth(),minY-maxY);
//        g2d.setColor(color3);
//
//        g2d.setStroke(new BasicStroke(2));
//
//        int x = markPaintInfo.getPoint().x;
//        int y = maxY - extraHeightPixels - 2;
//        int width = markPaintInfo.getWidth();
//        int height = minY - maxY + 2 * extraHeightPixels + 3;
//        g2d.draw(new java.awt.geom.RoundRectangle2D.Float(x, y, width,
//                height, 15, 15));
//        g2d.setColor(color2);
//        g2d.fillRect(x, y, width, height);
//        //dejamos las cosas tal y como estaban
//        g2d.setStroke(oldStroke);
//        // }
//    }

    @Override
    public boolean hasDataToSave() {
        return false;
    }



}
