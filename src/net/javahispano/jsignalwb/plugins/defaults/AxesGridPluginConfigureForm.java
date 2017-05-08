/*
 * AxesGridPluginConfigureForm.java
 *
 * Created on 13 de octubre de 2007, 20:53
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.Window;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

/**
 *
 * @author  Compaq_Propietario
 */
public class AxesGridPluginConfigureForm extends javax.swing.JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -6855596227413769996L;

    private String value = "1";
    private String unit = "s";
    private long time;
    private long distance;
    private AxesGridPlugin grid;
    private JWindow jw;
    /** Creates new form AxesGridPluginConfigureForm */
    public AxesGridPluginConfigureForm(AxesGridPlugin grid) {
        this.grid = grid;
        initComponents();
        time = grid.getYAxePosition();
        distance = grid.getDistance();
        getTimeAdapted(grid.getDistance());
        jTextField1.setText(value.replace(",", "."));
        jComboBox1.setSelectedItem(unit);
        jTextFieldDate2.setText(TimeRepresentation.timeToString(time));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Codigo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDate2 = new net.javahispano.jsignalwb.ui.JTextFieldDate();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 4, true));
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Axe position:");

        jTextFieldDate2.setForeground(java.awt.Color.blue);
        jTextFieldDate2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField1.setForeground(java.awt.Color.blue);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("0");

        jComboBox1.setForeground(java.awt.Color.blue);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"h", "m", "s", "ms"}));

        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Leyend Separation");

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                              .addComponent(jTextField1)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextFieldDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 172,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                          .addContainerGap(160, Short.MAX_VALUE)
                          .addComponent(jButton1)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jButton2)
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDate2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                          .addGap(7, 7, 7)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                           javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1))
                          .addContainerGap())
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        time = TimeRepresentation.stringToMillis(jTextFieldDate2.getText());
        distance = getDistance();
        grid.setYAxePosition(time);
        grid.setDistance(distance);
        hideJWindow();
    } //GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        hideJWindow();
    } //GEN-LAST:event_jButton2ActionPerformed

    private String getTimeAdapted(long time) {
        float f = (float) time;
        if (f >= 1000) {
            f = f / 1000f;
            if (f >= 60) {
                f = f / 60;
                if (f >= 60) {
                    f = f / 60;
                    value = String.format("%.2f", f);
                    unit = "h";
                    return String.format("%.2f h", f);
                } else {
                    value = String.format("%.2f", f);
                    unit = "m";
                    return String.format("%.2f m", f);
                }
            } else {
                value = String.format("%.2f", f);
                unit = "s";
                return String.format("%.2f s", f);
            }
        } else {
            value = String.format("%.2f", f);
            unit = "ms";
            return String.format("%.2f ms", f);
        }
    }

    public void showJWindow(Window owner) {
        jw = new JWindow(owner);
        //jw.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
        jw.add(this);
        jw.setSize(this.getPreferredSize());
        jw.setLocationRelativeTo(owner);
        jw.setVisible(true);
    }

    private void hideJWindow() {
        JSWBManager.getJSWBManagerInstance().refreshJSM(false);
        jw.dispose();
    }

    private long getDistance() {
        String text = jTextField1.getText();
        text = text.replace(",", ".");
        long value = 0;
        if ("ms".equals(jComboBox1.getSelectedItem())) {
            value = (long) Float.parseFloat(text);
        } else if ("s".equals(jComboBox1.getSelectedItem())) {
            value = (long) (1000 * Float.parseFloat(text));
        } else if ("m".equals(jComboBox1.getSelectedItem())) {
            value = (long) (60000 * Float.parseFloat(text));
        } else if ("h".equals(jComboBox1.getSelectedItem())) {
            value = (long) (3600000 * Float.parseFloat(text));
        }
        return value;
    }

    // Declaracion de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private net.javahispano.jsignalwb.ui.JTextFieldDate jTextFieldDate2;
    // Fin de declaracion de variables//GEN-END:variables

}
