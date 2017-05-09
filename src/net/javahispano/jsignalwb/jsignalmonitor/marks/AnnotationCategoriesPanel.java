/*
 * AnnotationCategoriesPanel.java
 *
 * Created on 17 de julio de 2007, 20:23
 */

package net.javahispano.jsignalwb.jsignalmonitor.marks;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 *
 * @author Roman Segador
 */
public class AnnotationCategoriesPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -4030911138345887129L;

    ArrayList<String> categories;
    public AnnotationCategoriesPanel() {
        categories = null;
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (categories != null) {
            for (String cat : categories) {
                g2d.drawString(cat, 5, (1 + categories.indexOf(cat)) * getFieldSize() - (getFieldSize() / 2));
                g2d.drawLine(0, (1 + categories.indexOf(cat)) * getFieldSize(), (int) getSize().getWidth(),
                             (1 + categories.indexOf(cat)) * getFieldSize());
            }
        }
    }

    public void refreshCategories(ArrayList<String> categories) {
        this.categories = categories;
        Runnable uiUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        };
        javax.swing.SwingUtilities.invokeLater(uiUpdateRunnable);

        //this.repaint();

    }

    private int getFieldSize() {
        return (int) ((getSize().getHeight()) / categories.size());
    }
}
