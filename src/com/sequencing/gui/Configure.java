package com.sequencing.gui;

import com.sequencing.Sequencing;
import java.awt.Color;
import javax.swing.*;
import static java.lang.Boolean.FALSE;
import net.javahispano.jsignalwb.JSWBManager;

public class Configure extends javax.swing.JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 4451763633903213420L;

    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private JButton chooseButton;
    private JDialog colourDialog;
    private JPanel colourPanel;
    private JColorChooser colourWindow;
//    private JButton confirmButton;
    private JSlider heightSlider;
    private JTextField heightText;
//    private JLabel jLabel1;
//    private JLabel jLabel3;
//    private JPanel jPanel1;
//    private JPanel jPanel2;
//    private JButton modifyButton;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form Configure
     */
    public Configure() {
        initComponents();
        heightText.setText(Integer.toString(Sequencing.getHeight()));
        heightSlider.setValue(Sequencing.getHeight());
        this.setLocationRelativeTo(JSWBManager.getParentWindow());
        setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        colourDialog = new JDialog();
        colourWindow = new JColorChooser();
        JButton confirmButton = new JButton();
        JLabel jLabel1 = new JLabel();
        JPanel jPanel1 = new JPanel();
        heightSlider = new JSlider();
        heightText = new JTextField();
        JLabel jLabel3 = new JLabel();
        JPanel jPanel2 = new JPanel();
        colourPanel = new JPanel();
        JButton chooseButton = new JButton();
        JButton modifyButton = new JButton();

        colourDialog.setMinimumSize(new java.awt.Dimension(500, 250));

        colourWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colourWindowMouseClicked();
            }
        });
        colourDialog.getContentPane().add(colourWindow, java.awt.BorderLayout.CENTER);

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed();
            }
        });
        colourDialog.getContentPane().add(confirmButton, java.awt.BorderLayout.PAGE_END);

        getContentPane().setLayout(new java.awt.GridLayout(0, 2));

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("HEIGHT:");
        getContentPane().add(jLabel1);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        heightSlider.setMaximum(1000);
        heightSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightSliderStateChanged();
            }
        });
        jPanel1.add(heightSlider);

        heightText.setHorizontalAlignment(JTextField.CENTER);
        heightText.setMaximumSize(new java.awt.Dimension(200, 60));
        heightText.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightTextActionPerformed();
            }
        });
        jPanel1.add(heightText);

        getContentPane().add(jPanel1);

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("CHOOSE COLOUR:");
        getContentPane().add(jLabel3);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        colourPanel.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.add(colourPanel);

        chooseButton.setText("Choose");
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButtonActionPerformed();
            }
        });
        jPanel2.add(chooseButton);

        getContentPane().add(jPanel2);

        modifyButton.setText("MODIFY");
        modifyButton.setMargin(new java.awt.Insets(10, 20, 10, 20));
        modifyButton.setMaximumSize(new java.awt.Dimension(50, 15));
        modifyButton.setMinimumSize(new java.awt.Dimension(50, 15));
        modifyButton.setPreferredSize(new java.awt.Dimension(50, 15));
        modifyButton.setRequestFocusEnabled(false);
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed();
            }
        });
        getContentPane().add(modifyButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void heightSliderStateChanged() {//GEN-FIRST:event_heightSliderStateChanged
        heightText.setText(Integer.toString(heightSlider.getValue()));
    }//GEN-LAST:event_heightSliderStateChanged

    private void heightTextActionPerformed() {//GEN-FIRST:event_heightTextActionPerformed
        heightSlider.setValue(Integer.parseInt(heightText.getText()));    }//GEN-LAST:event_heightTextActionPerformed

    private void modifyButtonActionPerformed() {//GEN-FIRST:event_modifyButtonActionPerformed
        Sequencing.setHeight(Integer.parseInt(heightText.getText()));

        Color colour = colourPanel.getBackground();
        Sequencing.setColour(colour);

        this.setVisible(FALSE);
    }//GEN-LAST:event_modifyButtonActionPerformed

    private void chooseButtonActionPerformed() {//GEN-FIRST:event_chooseButtonActionPerformed
        colourDialog.setVisible(true);
    }//GEN-LAST:event_chooseButtonActionPerformed

    private void colourWindowMouseClicked() {//GEN-FIRST:event_colourWindowMouseClicked

    }//GEN-LAST:event_colourWindowMouseClicked

    private void confirmButtonActionPerformed() {//GEN-FIRST:event_confirmButtonActionPerformed
        colourPanel.setBackground(colourWindow.getColor());
        colourDialog.update(null);

        this.colourDialog.setVisible(FALSE);
    }//GEN-LAST:event_confirmButtonActionPerformed


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Configure().setVisible(true);
            }
        });
    }


}
