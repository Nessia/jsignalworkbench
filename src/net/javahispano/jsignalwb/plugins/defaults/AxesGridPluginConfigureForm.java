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
import net.javahispano.jsignalwb.ui.JTextFieldDate;

/**
 *
 * @author  Compaq_Propietario
 */
public class AxesGridPluginConfigureForm extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -6855596227413769996L;

    // Declaracion de varibales -no modificar//GEN-BEGIN:variables
//    private JButton jButton1;
//    private JButton jButton2;
    private JComboBox<String> jComboBox1;
//    private JLabel jLabel1;
//    private JLabel jLabel2;
    private JTextField jTextField1;
    private JTextFieldDate jTextFieldDate2;
    // Fin de declaracion de variables//GEN-END:variables

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
        JLabel jLabel2 = new JLabel();
        jTextFieldDate2 = new net.javahispano.jsignalwb.ui.JTextFieldDate();
        jTextField1 = new JTextField();
        jComboBox1 = new JComboBox<String>();
        JLabel jLabel1 = new JLabel();
        JButton jButton1 = new JButton();
        JButton jButton2 = new JButton();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 51), 4, true));
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Axe position:");

        jTextFieldDate2.setForeground(java.awt.Color.blue);
        jTextFieldDate2.setHorizontalAlignment(JTextField.CENTER);

        jTextField1.setForeground(java.awt.Color.blue);
        jTextField1.setHorizontalAlignment(JTextField.CENTER);
        jTextField1.setText("0");

        jComboBox1.setForeground(java.awt.Color.blue);
        jComboBox1.setModel(new DefaultComboBoxModel<String>(new String[] {"h", "m", "s", "ms"}));

        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Leyend Separation");

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed();
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE,
                                                  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE,
                                                  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                              .addComponent(jTextField1)
                                              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextFieldDate2, GroupLayout.PREFERRED_SIZE, 172,
                                                  GroupLayout.PREFERRED_SIZE))
                          .addContainerGap())
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                          .addContainerGap(160, Short.MAX_VALUE)
                          .addComponent(jButton1)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jButton2)
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDate2, GroupLayout.PREFERRED_SIZE,
                                                  GroupLayout.DEFAULT_SIZE,
                                                  GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                          .addGap(7, 7, 7)
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE,
                                                  GroupLayout.DEFAULT_SIZE,
                                                  GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE,
                                                  GroupLayout.DEFAULT_SIZE,
                                                  GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                           GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1))
                          .addContainerGap())
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed() { //GEN-FIRST:event_jButton1ActionPerformed
        time = TimeRepresentation.stringToMillis(jTextFieldDate2.getText());
        distance = getDistance();
        grid.setYAxePosition(time);
        grid.setDistance(distance);
        hideJWindow();
    } //GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed() { //GEN-FIRST:event_jButton2ActionPerformed
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



}
