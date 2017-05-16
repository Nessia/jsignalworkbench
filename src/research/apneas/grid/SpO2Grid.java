package research.apneas.grid;

import java.awt.*;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import research.apneas.DesatDetector;

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
public class SpO2Grid extends GridPluginAdapter {
    /**
     *
     */
    private static final long serialVersionUID = -3589322062643776819L;

    private static final int PASO_X = 5;
//  private static final int PASO_X_1 = pasoX - 1;

    private float[] valorBasal;

    private final transient DefaultGrid defaultGrid = new DefaultGrid();
    private final transient TrapezoidalDistribution dropSpO2 = new TrapezoidalDistribution(3, 30, 100, 100);
    private int bigSpace;
    private int bigSpaceY;

    @Override
    public int getLeyendWidth() {
        return bigSpace;
    }

    @Override
    public int getLeyendHeight() {
        return bigSpaceY;
    }

    public SpO2Grid() {
        // Vacio
    }

    @Override
    public void setSignal(Signal s) {
        signal = s;
        valorBasal = DesatDetector.getValorBasal();
    }

    @Override
    public String getName() {
        return "Grid SpO2";
    }

    @Override
    public void launchConfigureGridGUI(Window owner) {
        // Vacio
    }

    @Override
    public void paintGrid(Graphics2D g2di, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        bigSpace = Math.round((width - 5) / (float) 10);
        bigSpaceY = Math.round((height - 5) / (float) 4);
        Graphics2D g2d = (Graphics2D) g2di.create();
        defaultGrid.paintGrid(g2d, p, height, width, gridconfig);
        long start = JSWBManager.getJSignalMonitor().getScrollValue();
        long end = start + JSWBManager.getJSignalMonitor().getVisibleTime();
        int startIndex = TimePositionConverter.timeToPosition(start, signal);
        int endIndex = TimePositionConverter.timeToPosition(end, signal);
        float temporalStep = (endIndex - startIndex) / (float) width;
        //asumimos cero en el centro
        float magnitudeStep = (gridconfig.getMaxValue() - gridconfig.getMinValue()) / height;
        int van = -PASO_X;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f));
        for (float i = startIndex; i < endIndex; i += PASO_X * temporalStep) {
            van += PASO_X;
            for (int j = height; j > 0; j--) {
                int altura2 = p.y + j;
                short pos = dropSpO2.evaluatepossibilityAt(valorBasal[(int) i]
                        - (gridconfig.getMaxValue() - j * magnitudeStep));
                if (pos == 0) {
                    break;
                }
                paintFragm(g2d, p, van, altura2, getColorRed(pos));
                j -= 3;
            }
        }

    }

    private void paintFragm(Graphics2D g2d, Point p, int van, int altura, Color color) {
        g2d.setColor(color);
        g2d.fillRect(p.x + van, altura, PASO_X, 4);
//        g2d.drawLine(p.x + van, altura, p.x + van + pasoX_1, altura+4);
    }

    @Override
    public boolean hasDataToSave() {
        return signal != null;
    }

    @Override
    public void setSavedData(String data) {
        // Vacio
    }

    @Override
    public String getDataToSave() {
        return "";
    }

    public static Color getColorRed(short code) {
        if (code > 50) {
            return new Color(255, (int) (5F * (100 - code)), 0);
        }
        return new Color(255 - (int) (5F * (50 - code)), 255, 0);
    }
}
