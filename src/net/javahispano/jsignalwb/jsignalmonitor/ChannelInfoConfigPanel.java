/*
 * ChannelInfoConfigPanel.java
 *
 * Created on 29 de junio de 2007, 19:45
 */

package net.javahispano.jsignalwb.jsignalmonitor;

import java.awt.Component;

import javax.swing.*;

/**
 *
 * @author  Compaq_Propietario
 */
class ChannelInfoConfigPanel extends javax.swing.JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 8895306506813565316L;

    private LeftPanelConfiguration configuration;
    private JSignalMonitorPanel jsmp;
    /** Creates new form ChannelInfoConfigPanel */
    public ChannelInfoConfigPanel(JSignalMonitorPanel jsmp) {
        this.jsmp = jsmp;
        this.configuration = jsmp.getJSignalMonitorProperties().getLeftPanelConfiguration();
        initComponents();
        checkFields();

    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Codigo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        arrowsCheckBox = new javax.swing.JCheckBox();
        nameCheckBox = new javax.swing.JCheckBox();
        magnitudeCheckBox = new javax.swing.JCheckBox();
        zoomHCheckBox = new javax.swing.JCheckBox();
        zoomVCheckBox = new javax.swing.JCheckBox();
        pointCheckBox = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 3, true));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Configuration");

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Select visible fields"));
        arrowsCheckBox.setText("Control Arrows");
        arrowsCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        arrowsCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        nameCheckBox.setText("Name");
        nameCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        nameCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        magnitudeCheckBox.setText("Magnitude");
        magnitudeCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        magnitudeCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        zoomHCheckBox.setText("Horizontal Zoom");
        zoomHCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        zoomHCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        zoomVCheckBox.setText("Vertical Zoom");
        zoomVCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        zoomVCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        pointCheckBox.setText("Point (Whenever possible)");
        pointCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pointCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pointCheckBox, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(zoomVCheckBox, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(zoomHCheckBox, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(magnitudeCheckBox, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(nameCheckBox, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(arrowsCheckBox, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                          .addContainerGap())
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(arrowsCheckBox)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(nameCheckBox)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(magnitudeCheckBox)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(zoomHCheckBox)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(zoomVCheckBox)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(pointCheckBox)
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addGap(10, 10, 10)
                          .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                          .addContainerGap(64, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jLabel1)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(okButton)
                                    .addComponent(jButton1))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        jw.dispose();
    } //GEN-LAST:event_jButton1ActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_okButtonActionPerformed
        configuration.setArrowsVisible(arrowsCheckBox.isSelected());
        configuration.setNameVisible(nameCheckBox.isSelected());
        configuration.setMagnitudeVisible(magnitudeCheckBox.isSelected());
        configuration.setFrecuencyVisible(zoomHCheckBox.isSelected());
        configuration.setZoomVisible(zoomVCheckBox.isSelected());
        configuration.setPointVisible(pointCheckBox.isSelected());
        jsmp.refreshLeftPanel();
        //channelInfoPanel.setInvadeNearChannels(invadeNearChannelsCheckBox.isSelected());
        //channelInfoPanel.validate();
        //channelInfoPanel.repaint();*/
       jw.dispose();
    } //GEN-LAST:event_okButtonActionPerformed

    public void showJWindow(Component owner) {
        if (jw == null) {
            jw = new JWindow();
            jw.add(this);
            jw.setSize(this.getPreferredSize());
            jw.setLocationRelativeTo(owner);
        }
        checkFields();
        jw.setVisible(true);
    }

//    private void hideJWindow() {
//        jw.dispose();
//    }

    private void checkFields() {
        arrowsCheckBox.setSelected(configuration.isArrowsVisible());
        magnitudeCheckBox.setSelected(configuration.isMagnitudeVisible());
        nameCheckBox.setSelected(configuration.isNameVisible());
        zoomHCheckBox.setSelected(configuration.isFrecuencyVisible());
        zoomVCheckBox.setSelected(configuration.isZoomVisible());
        pointCheckBox.setSelected(configuration.isPointVisible());
        //invadeNearChannelsCheckBox.setSelected(channelInfoPanel.isInvadeNearChannels());
    }

    // Declaracion de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JCheckBox arrowsCheckBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox magnitudeCheckBox;
    private javax.swing.JCheckBox nameCheckBox;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox pointCheckBox;
    private javax.swing.JCheckBox zoomHCheckBox;
    private javax.swing.JCheckBox zoomVCheckBox;
    // Fin de declaracion de variables//GEN-END:variables
    JWindow jw;
}
