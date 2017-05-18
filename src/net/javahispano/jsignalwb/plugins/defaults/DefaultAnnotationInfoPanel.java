package net.javahispano.jsignalwb.plugins.defaults;

import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.michaelbaranov.microba.calendar.CalendarPane;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

abstract class DefaultAnnotationInfoPanel<T extends AnnotationPluginAdapter> extends DefaultInfoPanel {

    /**
     *
     */
    private static final long serialVersionUID = -8731900230204368758L;

private static final Logger LOGGER = Logger.getLogger(DefaultAnnotationInfoPanel.class.getName());

    protected transient T dim;

    DefaultAnnotationInfoPanel(T dim){
        this.dim = dim;
        initComponents();
        markTitleTextField.setText(dim.getTitle());
        //markTimeTextField.setText(TimeRepresentation.timeToString(dim.getMarkTime()));
        jTextFieldDate1.setText(TimeRepresentation.timeToString(dim.getAnnotationTime()));
        datePicker1.showButtonOnly(true);
        try {
            datePicker1.setDate(new Date(dim.getAnnotationTime()));
        } catch (PropertyVetoException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }
        datePicker1.addPropertyChangeListener(CalendarPane.PROPERTY_NAME_DATE, this);
        commentaryTextArea.setText(dim.getCommentary());
        kindLabel.setText("Kind of Annotation: " + dim.getName());
    }

    protected abstract void initComponents();

    protected void hideJWindow() {
       JSWBManager.getJSWBManagerInstance().refreshJSM(false);
       jw.dispose();
    }

    protected void jButton2ActionPerformed() { //GEN-FIRST:event_jButton2ActionPerformed
       if (JOptionPane.showConfirmDialog(jw.getParent(), "Are you sure?",
                                         "Delete Annotation",
                                         JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
           JSWBManager.getJSWBManagerInstance().removeAnnotation(dim);
           hideJWindow();
       }
   } //GEN-LAST:event_jButton2ActionPerformed

}
