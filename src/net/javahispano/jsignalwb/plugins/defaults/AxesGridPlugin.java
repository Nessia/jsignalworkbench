/*
 * AxesGridPlugin.java
 *
 * Created on 12 de octubre de 2007, 11:54
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;

/**
 *
 * @author Roman Segador
 */
public class AxesGridPlugin extends GridPluginAdapter {
    /**
     *
     */
    private static final long serialVersionUID = 5326250131184943761L;
    private long yAxePosition = -1;
    private long distance = -1;
    private transient final Stroke stroke = new BasicStroke(2);
    //private Signal signal = null;
    private String signalName;

    public AxesGridPlugin() {
        //stroke = new BasicStroke(2);
        // XXX que chapuza es esta? @vanesa
        //this.setYAxePosition((new Date(2008 - 1900, 1, 1)).getTime());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2008 - 1900);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        this.setYAxePosition(date.getTime());
    }

    public AxesGridPlugin(Signal s) {
        this();
        setSignal(s);
    }

    @Override
    public void setSignal(Signal s) {
        signal = s;
    }

    @Override
    public String getName() {
        return "Temporal Axis Grid";
    }

    @Override
    public int getLeyendHeight() {
        return 0;
    }

    @Override
    public int getLeyendWidth() {
        return 0;
    }

    @Override
    public void launchConfigureGridGUI(Window owner) {
        new AxesGridPluginConfigureForm(this).showJWindow(JSWBManager.getParentWindow());
    }

    @Override
    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        long scrollValue = JSWBManager.getJSWBManagerInstance().getJSMScrollValue();
        if (yAxePosition < 0) {
            yAxePosition = scrollValue;
        }
        if (distance < 0) {
            distance = getTimeAtLocation(width / 5, gridconfig.getFrec(), 0);
        }

        float min = gridconfig.getMinValue();
        float max = gridconfig.getMaxValue();
        float range;
        String yLabel;
        //calculamos el rango de representacion
        if (max * min > 0) {
            range = Math.abs(max - min);
        } else {
            range = Math.abs(max) + Math.abs(min);
        }

        float medium;
        int positionYAxe;
        //Si el cero se estuviese mostrando lo dibujamos
        if (min < 0 && max > 0) {
            medium = Math.abs(min);
            yLabel = "0";
        } else {
            medium = range / 2;
            yLabel = Float.toString(min + medium);
            yLabel = yLabel.substring(0, Math.min(5, yLabel.length()));
        }
        positionYAxe = height - (int) (medium * height / range);

        Stroke oldStroke = g2d.getStroke();
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.blue);
        g2d.setStroke(stroke);
        g2d.drawLine(p.x, p.y + positionYAxe, p.x + width, p.y + positionYAxe);
        g2d.drawLine(p.x + width - 10, p.y + positionYAxe - 5, p.x + width,
                     p.y + positionYAxe);
        g2d.drawLine(p.x + width - 10, p.y + positionYAxe + 5, p.x + width,
                     p.y + positionYAxe);
        g2d.drawString(yLabel, p.x - 7 * yLabel.length() - 5,
                       p.y + positionYAxe);
        if (yAxePosition >= 0) {
            int loc = getLocationAtTime(yAxePosition, gridconfig.getFrec(),
                                        scrollValue);
            loc = loc + p.x;
            int y1 = p.y + positionYAxe - 5;
            int y2 = p.y + positionYAxe + 5;
            if (yAxePosition >= scrollValue &&
                yAxePosition <=
                getTimeAtLocation(width, gridconfig.getFrec(), scrollValue)) {
                g2d.drawLine(loc, p.y + height + 5, loc, p.y);
                g2d.drawLine(loc - 5, p.y + 10, loc, p.y);
                g2d.drawLine(loc + 5, p.y + 10, loc, p.y);
            }
            int dist = getLocationAtTime(distance, gridconfig.getFrec(), 0);
            float segTime = distance / 1000f;
            for (int index = loc, i = 0; index <= p.x + width; index += dist, i++) {
                if (index >= p.x) {
                    g2d.drawLine(index, y1, index, y2);
                    g2d.drawString(String.format("%.2f", segTime * i) + "s",
                                   index, y2 + 10);
                }
            }
            for (int index = loc - dist, i = -1; index >= p.x; index -= dist, i--) {
                if (index <= p.x + width) {
                    g2d.drawLine(index, y1, index, y2);
                    g2d.drawString(String.format("%.2f", segTime * i) + "s",
                                   index, y2 + 10);
                }
            }
        }
        g2d.setStroke(oldStroke);
        g2d.setColor(oldColor);
    }

    @Override
    public boolean hasDataToSave() {
        return signal != null;
    }

    @Override
    public void setSavedData(String d) {
        String data = d;
        yAxePosition = Long.parseLong(data.substring(0, data.indexOf('|')));
        data = data.substring(data.indexOf('|'), data.length());
        distance = Long.parseLong(data.substring(1, data.indexOf('|', 1)));
        data = data.substring(data.indexOf('|', 1), data.length());
        this.signalName = data.substring(1, data.length());
    }

    @Override
    public String getDataToSave() {
        return yAxePosition + "|" + distance + "|" + signal.getName();
    }

    public long getYAxePosition() {
        return yAxePosition;
    }

    public void setYAxePosition(long yAxe) {
        this.yAxePosition = yAxe;
        //si hace falta, y se inicializa la senhal
        if (signal == null) {
            signal = JSWBManager.getSignalManager().getSignal(signalName);
        }
    }

    private long getTimeAtLocation(int width, float frec, long scroll) {
        return scroll + (long) (1000 * ((width / frec)));
    }

    private int getLocationAtTime(long time, float frec, long scroll) {
        return Math.round((frec * (time - scroll)) / 1000f);
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }


}
