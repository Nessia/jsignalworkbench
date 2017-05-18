/*
 * JMenuPluginDetails.java
 *
 * Created on 13 de septiembre de 2007, 18:07
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Roman Segador
 */
class JButtonTableCellEditorAndRenderer extends AbstractCellEditor
            implements TableCellEditor, TableCellRenderer, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -3651849394491748179L;

    private JButton button;

    public JButtonTableCellEditorAndRenderer() {
        button = null;
    }

    JButtonTableCellEditorAndRenderer(Icon icon, String tooltipText) {
        button = new JButton(icon);
        button.setToolTipText(tooltipText);
        button.addActionListener(this);
    }

    @Override
    public Object getCellEditorValue() {
        return button;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            String toolTip = button.getToolTipText();
            button = (JButton) value;
            button.setToolTipText(toolTip);
            button.addActionListener(this);
            return (JButton) value;
        }
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value != null) {
            return button;
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }


}
