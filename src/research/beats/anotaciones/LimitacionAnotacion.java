package research.beats.anotaciones;

import java.awt.Color;
import java.awt.Window;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalConstants;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.jsignalmonitor.marks.MarkPaintInfo;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.UIManager;
import javax.swing.ToolTipManager;

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
public class LimitacionAnotacion extends DefaultIntervalMark {

    /**
     *
     */
    private static final long serialVersionUID = -3924443767402513342L;

    public static final int APNEA = 1;
    public static final int HIPOAPNEA = 2;
    public static final int DESATURACION = 3;

    public static final int N = 0;
    public static final int A = -1;
    public static final int V = -2;
    public static final int P = -3;
    public static final int TV = -4;
    public static final int Vrt = -5;
    public static final int Prt = -6;

//    public enum SENALES { Prt, Vrt, TV, P, V, A, N, APNEA, HIPOPNEA, DESATURACION }

    private int tipo = 1;
    private boolean automatica = false;

    public LimitacionAnotacion() {
        super();
    }

    private List<LimitacionAnotacion> toraxList = new LinkedList<LimitacionAnotacion>();
    private List<LimitacionAnotacion> abdomenList = new LinkedList<LimitacionAnotacion>();

    public void addToraxLimitation(LimitacionAnotacion l) {
        this.toraxList.add(l);
    }

    public void addAbdomenLimitation(LimitacionAnotacion l) {
        this.abdomenList.add(l);
    }

    public List<LimitacionAnotacion> getAbdomenList() {
        return new LinkedList<LimitacionAnotacion>(abdomenList);
    }

    public List<LimitacionAnotacion> getToraxList() {
        return toraxList;
    }

    @Override
    public String getName() {
        return "Limitacion de flujo";
    }

    @Override
    public void showMarkInfo(Window owner) {
        new LimitacionesDialog((JFrame) JSWBManager.getParentWindow(), "Marca:", true, this);
    }

    public int getTipo() {
        return tipo;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    @Override
    public String getToolTipText() {
        UIManager.put("ToolTip.foreground", new ColorUIResource(Color.black));
            UIManager.put("ToolTip.background", new ColorUIResource(Color.CYAN ));

            ToolTipManager.sharedInstance().setDismissDelay(10000);
         // Show tool tips immediately
    ToolTipManager.sharedInstance().setInitialDelay(600);
        return "<html>Desaturation<br>Possibility: 1.0<br>"+
                "<ul><li>Drop: 18</li><li>Span: 49 sec.</li><li>Maximum value: 98</li>"+
                "<li>Minimum value: 80</li><li>Mean value: 89</li></ul></html>";
    }

    @Override
  /* */ public void paint(Graphics2D g2d, MarkPaintInfo markPaintInfo) {
        super.paint(g2d, markPaintInfo);
        if (!isAutomatica()) {
            g2d.setColor(Color.GREEN);
            Point p = markPaintInfo.getPoint();
            int radio = 10;
            int x = p.x + markPaintInfo.getWidth()/2 -  radio/2;
            int y = p.y +  2*markPaintInfo.getHeight()/3 - radio;
            g2d.fillOval(x, y, radio, radio);
        }
        if (SignalConstants.SENAL_FLUJO.equals(this.getSignal().getName()) ) {
            g2d.setColor(Color.BLACK);
            //Point p = markPaintInfo.getPoint();
            //int maxY = (int) Math.max(markPaintInfo.getPoint().getY(),
            //                      markPaintInfo.getMaxValueY());
            //int x = p.x + markPaintInfo.getWidth()/2 - 2;
            //int y = maxY  -16;
            if (this.tipo == APNEA) {
                //g2d.drawString("A", x, y);
            }
            if (this.tipo == HIPOAPNEA) {
                //g2d.drawString("H", x, y);
            }

        }
    }/**/

    public void setTipo(int tipo) {
        this.tipo = tipo;
        this.setComentary(Integer.toString(tipo));
        if (tipo == LimitacionAnotacion.APNEA ||
            tipo <= 0) {
          //  this.setColor(Color.RED);
        } else if (tipo == LimitacionAnotacion.HIPOAPNEA) {
          //  this.setColor(Color.YELLOW);
        } else {
          //  this.setColor(Color.BLUE);
        }

    }

    public void setAutomatica(boolean automatica) {
        this.automatica = automatica;
    }

    @Override
    public String getDataToSave() {
        return this.automatica + "*" + super.getDataToSave();
    }

    @Override
    public void setSavedData(String data) {
        String a = data.substring(0, data.indexOf('*'));
        this.automatica = Boolean.parseBoolean(a);
        super.setSavedData(data.substring(data.indexOf('*') + 1, data.length()));
        if ("2".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.HIPOAPNEA);
        }
        if ("3".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.DESATURACION);
        }
        if ("1".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.APNEA);
        }
        if ("0".equalsIgnoreCase(this.getComentary())) {
           this.setTipo(LimitacionAnotacion.N);
        }
        if ("-1".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.A);
        }
        if ("-2".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.V);
        }
        if ("-3".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.P);
        }
        if ("-4".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.TV);
        }
        if ("-5".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.Vrt);
        }
        if ("-6".equalsIgnoreCase(this.getComentary())) {
            this.setTipo(LimitacionAnotacion.Prt);
        }
    }


    /* public int compareTo(Object o) {
     DefaultIntervalMark i = (DefaultIntervalMark) o;
     if (i.getMarkTime() < this.getMarkTime()) {
         return 1;
     } else if (i.getMarkTime() > this.getMarkTime()) {
         return -1;
     }
     return 0;//chapuza para que no haya problemas con duplicados
     }*/

}
