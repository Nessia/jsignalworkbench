package research.apneas.grid;

import java.awt.*;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import research.apneas.ReduccionFlujo;

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
public class ApneaGrid extends GridPluginAdapter {
    /**
     *
     */
    private static final long serialVersionUID = -6294553819385713088L;

    private static final int PASO_X = 20;
    private static final int PASO_X_1 = PASO_X - 1;

    private float[] valorBasal;
//  private float[] delta;
    private final transient DefaultGrid defaultGrid = new DefaultGrid();
    private TrapezoidalDistribution apnea = new TrapezoidalDistribution(0, 0, 0.1F, 0.25F);
    private TrapezoidalDistribution ha = new TrapezoidalDistribution(0, 0, 0.5F, 0.8F);
    private boolean pintarRalla = true;
    private int bigSpace;
    private int bigSpaceY;

    public ApneaGrid() {
        valorBasal = ReduccionFlujo.getValorBasal();
//        delta = ReduccionFlujo.getDelta();
    }

    @Override
    public int getLeyendWidth() {
        return bigSpace;
    }

    @Override
    public int getLeyendHeight() {
        return bigSpaceY;
    }

    @Override
    public void setSignal(Signal s) {
        signal = s;
        apnea = new TrapezoidalDistribution(0, 0, 0.1F, 0.25F);
        if ("R. Airflow".equals(s.getName())) {
            ha = new TrapezoidalDistribution(0, 0, 0.25F, 0.8F);
        } else {
            ha = new TrapezoidalDistribution(0, 0, 0.25F, 0.6F);
        }

    }

    @Override
    public String getName() {
        return "Grid Apnea";
    }

    @Override
    public void launchConfigureGridGUI(Window owner) {
        // Vacio
    }

    @Override
    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        bigSpace = Math.round((width - 5) / (float) 10);
        bigSpaceY = Math.round((height - 5) / (float) 4);
        Graphics2D g = (Graphics2D) g2d.create();
        defaultGrid.paintGrid(g, p, height, width, gridconfig);
        long start = JSWBManager.getJSignalMonitor().getScrollValue();
        long end = start + JSWBManager.getJSignalMonitor().getVisibleTime();
        int startIndex = TimePositionConverter.timeToPosition(start, signal);
        int endIndex = TimePositionConverter.timeToPosition(end, signal);
        float temporalStep = (endIndex - startIndex) / (float) width;
        //asumimos cero en el centro
        float magnitudeStep = (2 * gridconfig.getMaxValue()) / height;
        int van = -PASO_X;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f));
        for (float i = startIndex; i < endIndex; i += PASO_X * temporalStep) {
            van += PASO_X;
            for (int j = 0; j < height / 2; j++) {
                int altura = p.y + height / 2 - j;
                int altura2 = p.y + height / 2 + j;
                short pos = apnea.evaluatepossibilityAt(j * 2 * magnitudeStep / (valorBasal[(int) i]));
                if (pos == 0) {
                    j++;
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    paintFragm(g, p, van, altura, altura2, Color.BLACK);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f));
                    for (; j < height / 2; j++) {
                        altura = p.y + height / 2 - j;
                        altura2 = p.y + height / 2 + j;
                        pos = ha.evaluatepossibilityAt(j * 2 * magnitudeStep / (valorBasal[(int) i]));
                        paintFragm(g, p, van, altura, altura2, getColorYellow(pos));
                        if (pos == 0) {
                            break;
                        }
                    }
                    break;
                }
                paintFragm(g, p, van, altura, altura2, getColorRed(pos));
            }
        }

    }

    private void paintFragm(Graphics2D g2d, Point p, int van, int altura, int altura2, Color color) {
        if (!pintarRalla && color.equals(Color.black)) {
            return;
        }
        g2d.setColor(color);
        g2d.drawLine(p.x + van, altura, p.x + van + PASO_X_1, altura);
        g2d.drawLine(p.x + van, altura2, p.x + van + PASO_X_1, altura2);

    }

    @Override
    public boolean hasDataToSave() {
        return signal != null;
    }

    @Override
    public void setSavedData(String data) {
        // Vacio
    }

    public String getDataToSave() {
        return "";
    }

    private static Color getColorRed(short code) {
        return new Color(255, (int) (2.5F * (100 - code)), 0);
    }

    private static Color getColorYellow(short code) {
        return new Color(255 - (int) (2.5F * (100 - code)), 255, 0);
    }
}
