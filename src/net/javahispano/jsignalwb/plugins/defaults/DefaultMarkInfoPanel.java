package net.javahispano.jsignalwb.plugins.defaults;

import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.plugins.MarkPluginAdapter;


import com.michaelbaranov.microba.calendar.CalendarPane;

public abstract class DefaultMarkInfoPanel<T extends MarkPluginAdapter> extends DefaultInfoPanel {

    /**
     *
     */
    private static final long serialVersionUID = 8328907234692277742L;

    private static final Logger LOGGER = Logger.getLogger(DefaultMarkInfoPanel.class.getName());


    /*
     * Atributos
     */

    protected JButton jButton2;

    protected JLabel signalNameLabel;
    protected Signal signal;
    protected transient T dim;

    public DefaultMarkInfoPanel(Signal signal, T dim) {
        this.signal = signal;
        this.dim = dim;
        initComponents();
        if (signal == null) {
           jButton2.setEnabled(false);
        }else{
           signalNameLabel.setText("Signal: " + signal.getName());
        }
        markTitleTextField.setText(dim.getTitle());
        jTextFieldDate1.setText(TimeRepresentation.timeToString(dim.getMarkTime()));
        datePicker1.showButtonOnly(true);
        try {
           datePicker1.setDate(new Date(dim.getMarkTime()));
       } catch (PropertyVetoException ex) {
           LOGGER.log(Level.WARNING, ex.getMessage(), ex);
       }
        datePicker1.addPropertyChangeListener(CalendarPane.PROPERTY_NAME_DATE, this);
        commentaryTextArea.setText(dim.getCommentary());
        kindLabel.setText("Kind of mark: " + dim.getName());
    }

    protected abstract void initComponents();



}
