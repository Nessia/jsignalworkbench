package research.apneas.grid;

import java.awt.*;

import net.javahispano.jsignalwb.jsignalmonitor.DefaultGrid;
import net.javahispano.jsignalwb.jsignalmonitor.GridConfiguration;
import net.javahispano.jsignalwb.plugins.GridPluginAdapter;

public class GridGris extends GridPluginAdapter {


    /**
     *
     */
    private static final long serialVersionUID = 3790334641068965862L;
    private final transient DefaultGrid defaultGrid = new DefaultGrid();
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

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public void paintGrid(Graphics2D g2d, Point p, int height, int width,
                          GridConfiguration gridconfig) {
        bigSpace = Math.round((width - 5) / (float) 10);
        bigSpaceY = Math.round((height - 5) / (float) 4);
        Graphics2D g = (Graphics2D) g2d.create();

        defaultGrid.paintGrid(g, p, height, width, gridconfig);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g.setColor(Color.lightGray);
        g.fillRect(p.x, p.y, width, height);
    }

}
