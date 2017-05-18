/*
 * DefaultIntervalMarkInfoPanel.java
 *
 * Created on 9 de julio de 2007, 11:47
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.Color;
import java.awt.Window;
import java.beans.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

import org.joda.time.DateTime;

import com.michaelbaranov.microba.calendar.CalendarPane;

/**
 *
 * @author  Compaq_Propietario
 */
public class DefaultIntervalMarkInfoPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private static final Logger LOGGER = Logger.getLogger(DefaultIntervalMarkInfoPanel.class.getName());
    /**
     *
     */
    private static final long serialVersionUID = -6634476406106375360L;

 // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTextField colorTextField;
    private JTextArea comentaryTextArea;
    private com.michaelbaranov.microba.calendar.DatePicker datePicker1;
    private com.michaelbaranov.microba.calendar.DatePicker datePicker2;
    private JButton jButton2;
    private net.javahispano.jsignalwb.ui.JTextFieldDate jTextFieldDate1;
    private net.javahispano.jsignalwb.ui.JTextFieldDate jTextFieldDate2;
    private JLabel kindLabel;
    private JTextField markTitleTextField;
    private JLabel signalNameLabel;
    // End of variables declaration//GEN-END:variables
    private JWindow jw;

    private Signal signal;
    private DefaultIntervalMark dim;

    /** Creates new form DefaultIntervalMarkInfoPanel */
    public DefaultIntervalMarkInfoPanel(Signal signal, DefaultIntervalMark dim) {
        this.signal = signal;
        this.dim = dim;
        initComponents();
        if (signal == null) {
            jButton2.setEnabled(false);
        }else{
            signalNameLabel.setText("Signal: " + signal.getName());
        }
        markTitleTextField.setText(dim.getTitle());
        jTextFieldDate1.setText(TimeRepresentation.timeToString(
                dim.getMarkTime()));
        jTextFieldDate2.setText(TimeRepresentation.timeToString(
                dim.getEndTime()));
        datePicker1.showButtonOnly(true);
        datePicker2.showButtonOnly(true);
        try {
            datePicker1.setDate(new Date(dim.getMarkTime()));
            datePicker2.setDate(new Date(dim.getEndTime()));
        } catch (PropertyVetoException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }
        datePicker1.addPropertyChangeListener(CalendarPane.PROPERTY_NAME_DATE, this);
        datePicker2.addPropertyChangeListener(CalendarPane.PROPERTY_NAME_DATE, this);
        comentaryTextArea.setText(dim.getComentary());
        kindLabel.setText("Kind of mark: " + dim.getName());
        colorTextField.setBackground(dim.getColor());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            if (evt.getSource().equals(datePicker1)) {
                Date date = datePicker1.getDate();
                if (date != null) {
                    jTextFieldDate1.setText(TimeRepresentation.timeToString(
                            swapDateNoChangeTime(
                                    dim.getMarkTime(), date.getTime())));
                }
            } else if (evt.getSource().equals(datePicker2)) {
                Date date = datePicker2.getDate();
                if (date != null) {
                    jTextFieldDate2.setText(TimeRepresentation.timeToString(
                            swapDateNoChangeTime(
                                    dim.getEndTime(), date.getTime())));
                }
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        JPanel jPanel1 = new JPanel();
        signalNameLabel = new JLabel();
        JPanel jPanel2 = new JPanel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        markTitleTextField = new JTextField();
        JLabel jLabel1 = new JLabel();
        jTextFieldDate1 = new net.javahispano.jsignalwb.ui.JTextFieldDate();
        jTextFieldDate2 = new net.javahispano.jsignalwb.ui.JTextFieldDate();
        datePicker1 = new com.michaelbaranov.microba.calendar.DatePicker();
        datePicker2 = new com.michaelbaranov.microba.calendar.DatePicker();
        JPanel jPanel3 = new JPanel();
        JLabel jLabel4 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        comentaryTextArea = new JTextArea();
        JButton jButton1 = new JButton();
        kindLabel = new JLabel();
        jButton2 = new JButton();
        JPanel jPanel4 = new JPanel();
        colorTextField = new JTextField();
        JButton jButton3 = new JButton();

        setForeground(java.awt.Color.blue);
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 0), 5, true));
        signalNameLabel.setForeground(java.awt.Color.blue);
        signalNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signalNameLabel.setText("Signal:");
        signalNameLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Start time:");

        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Mark Title:");

        markTitleTextField.setForeground(java.awt.Color.blue);
        markTitleTextField.setHorizontalAlignment(JTextField.CENTER);
        markTitleTextField.setText("jTextField1");

        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("End time:");

        jTextFieldDate1.setForeground(java.awt.Color.blue);
        jTextFieldDate1.setHorizontalAlignment(JTextField.CENTER);

        jTextFieldDate2.setForeground(java.awt.Color.blue);
        jTextFieldDate2.setHorizontalAlignment(JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                          .addGap(15, 15, 15)
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                              .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.LEADING, false)
                .addComponent(jTextFieldDate2, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.LEADING, false)
                .addComponent(datePicker1, 0, 0, Short.MAX_VALUE)
                .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)))
                                    .addComponent(markTitleTextField))
                          .addContainerGap(16, Short.MAX_VALUE))
                );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(markTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                              .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jTextFieldDate1, javax.swing.GroupLayout.PREFERRED_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8,
                Short.MAX_VALUE)
                                              .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jTextFieldDate2, javax.swing.GroupLayout.PREFERRED_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                              .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap())
                );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Comment");

        comentaryTextArea.setColumns(20);
        comentaryTextArea.setRows(5);
        jScrollPane1.setViewportView(comentaryTextArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                          .addContainerGap(14, Short.MAX_VALUE))
                );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jLabel4)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                          .addContainerGap())
                );

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1hideJWindow();
            }
        });

        kindLabel.setForeground(java.awt.Color.blue);
        kindLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kindLabel.setText("Kind of Mark:");
        kindLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed();
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorTextField.setBackground(dim.getColor());
        colorTextField.setEditable(false);
        colorTextField.setHorizontalAlignment(JTextField.CENTER);
        colorTextField.setText("Mark Color");

        jButton3.setText("Choose color");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed();
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(colorTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jButton3)
                          .addContainerGap())
                );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(colorTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3))
                          .addContainerGap())
                );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(kindLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(signalNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(signalNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(kindLabel)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed() { //GEN-FIRST:event_jButton3ActionPerformed

        Color newColor = JColorChooser.showDialog(this, "SelectColor", dim.getColor());
        if (newColor != null) {
            dim.setColor(newColor);
            colorTextField.setBackground(newColor);
        }
    } //GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed() { //GEN-FIRST:event_jButton2ActionPerformed
        if (signal != null && JOptionPane.showConfirmDialog(jw.getParent(), "Are you sure?", "Delete mark", JOptionPane.YES_NO_OPTION,
                                              JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
             signal.removeMark(dim);
             JSWBManager.getJSWBManagerInstance().refreshJSM(false);
             hideJWindow();
        }
    } //GEN-LAST:event_jButton2ActionPerformed

    private void jButton1hideJWindow() { //GEN-FIRST:event_jButton1hideJWindow
        hideJWindow();
    } //GEN-LAST:event_jButton1hideJWindow

    public void showJWindow(Window owner) {
        jw = new JWindow(owner);
        jw.add(this);
        jw.setSize(this.getPreferredSize());
        jw.setLocationRelativeTo(owner);
        jw.setVisible(true);
    }

    private void hideJWindow() {
        dim.setTitle(markTitleTextField.getText());
        dim.setComentary(comentaryTextArea.getText());
        long start = TimeRepresentation.stringToMillis(jTextFieldDate1.getText());
        long end = TimeRepresentation.stringToMillis(jTextFieldDate2.getText());
        dim.setMarkTime(Math.min(start, end));
        dim.setEndTime(Math.max(start, end));
        jw.dispose();
    }

    /**
     * @param old long
     * @param newTime long
     * @return long
     */
    private static long swapDateNoChangeTime(long old, long newTime) {
        DateTime dt = new DateTime(old);
        DateTime newDateTime = new DateTime(newTime).withTime(dt.getHourOfDay(),
                dt.getMinuteOfHour(), dt.getSecondOfMinute(),
                dt.getMillisOfSecond());

        return newDateTime.getMillis();
    }


}
