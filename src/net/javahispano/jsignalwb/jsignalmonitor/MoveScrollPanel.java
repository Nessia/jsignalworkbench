/*
 * MoveScrollPanel.java
 *
 * Created on 3 de septiembre de 2007, 14:30
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.Color;

import javax.swing.*;


/**
 *
 * @author  Compaq_Propietario
 */
public class MoveScrollPanel extends javax.swing.JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -458399409807671860L;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton configButton;
    private JButton defaultButton;
    private JLabel infoLabel;
    private JButton jButton1;
    private JButton jButton2;
    private JComboBox<String> jComboBox1;
//    private JPanel jPanel1;
    private JTextField jTextField1;
    private JButton okButton;
    // End of variables declaration//GEN-END:variables

    private JSignalMonitor jsm;
    private long milisec = 1000;
    private String value = "1";
    private String unit = "s";

    /** Creates new form MoveScrollPanel */
    public MoveScrollPanel(JSignalMonitor jsm) {
        this.jsm = jsm;
        initComponents();
        refreshFields();
        setEditMode(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPanel jPanel1 = new JPanel();
        jButton1 = new JButton();
        jTextField1 = new JTextField();
        jComboBox1 = new JComboBox<String>();
        jButton2 = new JButton();
        infoLabel = new JLabel();
        okButton = new JButton();
        defaultButton = new JButton();
        configButton = new JButton();

        defaultButton = new JButton();
        infoLabel.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        infoLabel.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        okButton.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        okButton.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        configButton.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        configButton.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        defaultButton.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        defaultButton.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        jButton1.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        jButton1.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        jComboBox1.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        jComboBox1.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        jTextField1.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        jTextField1.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());
        jButton2.setFont(jsm.getJSMProperties().getLookAndFeelConfiguration().getSmallFont());
        jButton2.setForeground(jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont());

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
                );

        jButton1.setText("<<");
        jButton1.setFocusPainted(false);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("0");
        jComboBox1.setModel(new DefaultComboBoxModel<String>(new String[] {"h", "m", "s", "ms"}));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton2.setText(">>");
        jButton2.setFocusPainted(false);
        jButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        infoLabel.setForeground(new java.awt.Color(0, 0, 255));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setText("0 ms");

        okButton.setText("OK");
        okButton.setFocusPainted(false);
        okButton.setMargin(new java.awt.Insets(4, 4, 4, 4));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        defaultButton.setText("<-->");
        defaultButton.setFocusPainted(false);
        defaultButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        defaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultButtonActionPerformed(evt);
            }
        });

        configButton.setText("...");
        configButton.setFocusPainted(false);
        configButton.setMargin(new java.awt.Insets(4, 4, 4, 4));
        configButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(configButton)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(okButton)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(jButton1)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jButton2)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(defaultButton)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 57,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(21, Short.MAX_VALUE))
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(configButton)
                .addComponent(okButton)
                .addComponent(jButton1)
                .addComponent(infoLabel)
                .addComponent(jButton2)
                .addComponent(defaultButton)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {configButton, defaultButton,
                        infoLabel, jButton1, jButton2, jComboBox1, jTextField1, okButton});

    } // </editor-fold>//GEN-END:initComponents

    private void defaultButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_defaultButtonActionPerformed
        milisec = jsm.getVisibleTime();
        milisec = 98 * milisec / 100;
        refreshFields();
    } //GEN-LAST:event_defaultButtonActionPerformed

    private void configButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_configButtonActionPerformed
        setEditMode(true);
    } //GEN-LAST:event_configButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_okButtonActionPerformed
        try {
            String text = jTextField1.getText().replace(",", ".");
            if ("ms".equals(jComboBox1.getSelectedItem())) {
                milisec = (long) Float.parseFloat(text);
            } else if ("s".equals(jComboBox1.getSelectedItem())) {
                milisec = (long) (1000 * Float.parseFloat(text));
            } else if ("m".equals(jComboBox1.getSelectedItem())) {
                milisec = (long) (60000 * Float.parseFloat(text));
            } else if ("h".equals(jComboBox1.getSelectedItem())) {
                milisec = (long) (3600000 * Float.parseFloat(text));
            }
            refreshFields();
            jTextField1.setBackground(Color.WHITE);
            setEditMode(false);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            jTextField1.setBackground(Color.RED);
        }
    } //GEN-LAST:event_okButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
//        String number=jTextField1.getText();
//        try {
//            long longNumber=Long.parseLong(number);
//            if(jComboBox1.getSelectedIndex()==0)
//                longNumber*=60000;
//            else if(jComboBox1.getSelectedIndex()==1)
//                longNumber*=1000;
//            jsm.setScrollValue(jsm.getScrollValue()-longNumber);
//            jTextField1.setBackground(Color.WHITE);
//        } catch (NumberFormatException ex) {
//            jTextField1.setBackground(Color.RED);
//        }
        jsm.setScrollValue(jsm.getScrollValue() - milisec);
    } //GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
//
        jsm.setScrollValue(jsm.getScrollValue() + milisec);
    } //GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_jComboBox1ActionPerformed

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

    private void refreshFields() {
        infoLabel.setText(getTimeAdapted(milisec));
        jTextField1.setText(value);
        jComboBox1.setSelectedItem(unit);
    }

    private void setEditMode(boolean edit) {
        jButton1.setVisible(!edit);
        infoLabel.setVisible(!edit);
        jButton2.setVisible(!edit);
        jTextField1.setVisible(edit);
        jComboBox1.setVisible(edit);
        defaultButton.setVisible(true);

        if (edit) {
            jTextField1.grabFocus();
            configButton.setVisible(false);
            okButton.setVisible(true);
        } else {
            jButton2.grabFocus();
            okButton.setVisible(false);
            configButton.setVisible(true);
        }
    }



}
