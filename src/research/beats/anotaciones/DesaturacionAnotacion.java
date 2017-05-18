package research.beats.anotaciones;

import java.awt.Color;
import java.awt.Window;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;

import net.javahispano.jsignalwb.JSWBManager;

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
public class DesaturacionAnotacion extends LimitacionAnotacion {


    private List<LimitacionAnotacion> limitationsList = new LinkedList<LimitacionAnotacion>();
    private Map<LimitacionAnotacion, Color> oldColorLimitations = new HashMap<LimitacionAnotacion, Color>();
    private Map<LimitacionAnotacion, Color> oldColorTorax = new HashMap<LimitacionAnotacion, Color>();
    private Map<LimitacionAnotacion, Color> oldColorAbdomen = new HashMap<LimitacionAnotacion, Color>();


    public DesaturacionAnotacion() {
        super();
        this.setTipo(SENALES.DESATURACION);
    }

    @Override
    public String getName() {
        return "Desaturación";
    }

    @Override
    public void showMarkInfo(Window owner) {
        for (LimitacionAnotacion limitation : limitationsList) {
            Color c = limitation.getColor();
            oldColorLimitations.put(limitation, c);
            limitation.setColor(Color.GREEN);

            for (LimitacionAnotacion e : limitation.getToraxList()) {
                oldColorTorax.put(e, e.getColor());
                e.setColor(Color.GREEN);
            }
            for (LimitacionAnotacion e : limitation.getAbdomenList()) {
                oldColorAbdomen.put(e, e.getColor());
                e.setColor(Color.GREEN);
            }
        }
        JSWBManager.getJSignalMonitor().repaintAll();
        new LimitacionesDialog((JFrame) JSWBManager.getParentWindow(), "Marca:", false, this);

    }

    void resetColors() {
        for (LimitacionAnotacion limitation : limitationsList) {
            limitation.setColor(oldColorLimitations.get(limitation));

            for (LimitacionAnotacion e : limitation.getAbdomenList()) {
                e.setColor(oldColorAbdomen.get(e));
            }
            for (LimitacionAnotacion elem : limitation.getToraxList()) {
                elem.setColor(oldColorTorax.get(elem));
            }
        }
    }

    public List<LimitacionAnotacion> getLimitationsList() {
        return new LinkedList<LimitacionAnotacion>(limitationsList);
    }


}
