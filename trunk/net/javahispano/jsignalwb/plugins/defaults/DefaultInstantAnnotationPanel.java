/*
 * DefaultInstantAnnotationPanel.java
 *
 * Created on 18 de julio de 2007, 14:28
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.*;
import java.beans.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import org.joda.time.DateTime;

/**
 *
 * @author  Compaq_Propietario
 */
public class DefaultInstantAnnotationPanel extends javax.swing.JPanel implements PropertyChangeListener {
    DefaultInstantAnnotation dim;
    JFileChooser jfc;
    JColorChooser jColorChooser1;
    JWindow jw;
    /** Creates new form DefaultInstantAnnotationPanel */
    public DefaultInstantAnnotationPanel(DefaultInstantAnnotation dia) {
        this.dim = dia;
        initComponents();
        if (dim.isImage()) {
            jRadioButton2.setSelected(true);
            jRadioButton1.setSelected(false);
            jButton4.setEnabled(true);
            jButton3.setEnabled(false);
        } else {
            jRadioButton2.setSelected(false);
            jRadioButton1.setSelected(true);
            jButton4.setEnabled(false);
            jButton3.setEnabled(true);
        }
        markTitleTextField.setText(dim.getTitle());
        //markTimeTextField.setText(TimeRepresentation.timeToString(dim.getMarkTime()));
        jTextFieldDate1.setText(TimeRepresentation.timeToString(
                dim.getAnnotationTime()));
        datePicker1.showButtonOnly(true);
        try {
            datePicker1.setDate(new Date(dim.getAnnotationTime()));
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        datePicker1.addPropertyChangeListener(datePicker1.PROPERTY_NAME_DATE, this);
        comentaryTextArea.setText(dim.getComentary());
        kindLabel.setText("Kind of Annotation: " + dim.getName());
        jColorChooser1 = new JColorChooser();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            Date date = datePicker1.getDate();
            if (date != null) {
                jTextFieldDate1.setText(TimeRepresentation.timeToString(
                        swapDateNoChangeTime(
                                dim.getAnnotationTime(), date.getTime())));
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        markTitleTextField = new javax.swing.JTextField();
        jTextFieldDate1 = new net.javahispano.jsignalwb.ui.JTextFieldDate();
        datePicker1 = new com.michaelbaranov.microba.calendar.DatePicker();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comentaryTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        kindLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        iconLabel = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        colorTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 0), 5, true));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Instant time:");

        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Title:");

        markTitleTextField.setForeground(java.awt.Color.blue);
        markTitleTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        markTitleTextField.setText("jTextField1");

        jTextFieldDate1.setForeground(java.awt.Color.blue);
        jTextFieldDate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Category:");

        jTextField1.setForeground(java.awt.Color.blue);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText(dim.getCategory());

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
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                              jPanel2Layout.createSequentialGroup()
                                              .addComponent(jTextFieldDate1, javax.swing.GroupLayout.PREFERRED_SIZE,
                172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addComponent(markTitleTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 200,
                                                  Short.MAX_VALUE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200,
                                                  Short.MAX_VALUE))
                          .addContainerGap())
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
                                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                BASELINE)
                                              .addComponent(jTextFieldDate1, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addComponent(jLabel2)))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                           javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1hideJWindow(evt);
            }
        });

        kindLabel.setForeground(java.awt.Color.blue);
        kindLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kindLabel.setText("Kind of Annotation:");
        kindLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Icon");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2radioButtons(evt);
            }
        });

        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setIcon(new ImageIcon(dim.getImageToShow().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));

        jButton4.setText("Choose Icon");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Color");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1radioButtons(evt);
            }
        });

        colorTextField.setBackground(dim.getColor());
        colorTextField.setEditable(false);
        colorTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        colorTextField.setText("Annotation Color");

        jButton3.setText("Choose color");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                              .addComponent(jRadioButton1)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(colorTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 116,
                Short.MAX_VALUE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                              .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 41,
                Short.MAX_VALUE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(iconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 117,
                Short.MAX_VALUE)
                                              .addGap(9, 9, 9)))
                          .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addContainerGap())
                );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                BASELINE)
                                              .addComponent(jRadioButton2)
                                              .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton4))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                           javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                              jPanel4Layout.
                                              createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                              .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addComponent(colorTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap())
                );

        jButton5.setText("Cancel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kindLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                              jPanel1Layout.createSequentialGroup()
                                              .addComponent(jButton2)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98,
                Short.MAX_VALUE)
                                              .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(jButton5))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
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
                                    .addComponent(jButton5)
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

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton5ActionPerformed
        hideJWindow();
    } //GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton3ActionPerformed
        Color c = jColorChooser1.showDialog(this, "SelectColor", dim.getColor());
        if (c != null) {
            colorTextField.setBackground(c);
        }
    } //GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton1radioButtons(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jRadioButton1radioButtons
        if ("Color".equals(evt.getActionCommand())) {
            System.out.println("image false");
            dim.setIsImage(false);
            jButton4.setEnabled(false);
            jButton3.setEnabled(true);
        }
        if ("Icon".equals(evt.getActionCommand())) {
            System.out.println("image true");
            dim.setIsImage(true);
            jButton4.setEnabled(true);
            jButton3.setEnabled(false);
        }
    } //GEN-LAST:event_jRadioButton1radioButtons

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton4ActionPerformed
        if (jfc == null) {
            jfc = new JFileChooser();

            FileFilter ff = new FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String path = f.getPath().toLowerCase();
                    if (path.endsWith(".gif")) {
                        return true;
                    }
                    if (path.endsWith(".jpg")) {
                        return true;
                    }
                    if (path.endsWith(".jpeg")) {
                        return true;
                    }
                    if (path.endsWith(".bmp")) {
                        return true;
                    }
                    if (path.endsWith(".png")) {
                        return true;
                    }
                    return false;
                }

                // return a description of files
                public String getDescription() {
                    return "Image file (*.gif,*.jpg,*.jpeg,*.bmp,*.png)";
                }
            };
            jfc.setFileFilter(ff);
        }
        if (jfc.showOpenDialog(this) == jfc.APPROVE_OPTION) {
            ;
        }
        {
            try {
                File f = jfc.getSelectedFile();
                if (f != null) {
                    dim.setImageToShow(new ImageIcon(f.getCanonicalPath()).getImage());
                    dim.setImagePath(f.getAbsolutePath());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            iconLabel.setIcon(new ImageIcon(dim.getImageToShow().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        }
    } //GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton2radioButtons(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jRadioButton2radioButtons
        if ("Color".equals(evt.getActionCommand())) {
            System.out.println("image false");
            //dim.setIsImage(false);
            jButton4.setEnabled(false);
            jButton3.setEnabled(true);
        }
        if ("Icon".equals(evt.getActionCommand())) {
            System.out.println("image true");
            //dim.setIsImage(true);
            jButton4.setEnabled(true);
            jButton3.setEnabled(false);
        }
    } //GEN-LAST:event_jRadioButton2radioButtons

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        if (JOptionPane.showConfirmDialog(jw.getParent(), "Are you sure?",
                                          "Delete Annotation",
                                          JOptionPane.YES_NO_OPTION,
                                          JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            JSWBManager.getJSWBManagerInstance().removeAnnotation(dim);
            hideJWindow();
        }
    } //GEN-LAST:event_jButton2ActionPerformed

    private void jButton1hideJWindow(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1hideJWindow
        dim.setTitle(markTitleTextField.getText());
        dim.setComentary(comentaryTextArea.getText());
        dim.setAnnotationTime(TimeRepresentation.stringToMillis(jTextFieldDate1.getText()));
        dim.setCategory(jTextField1.getText());
        dim.setColor(colorTextField.getBackground());
        dim.setIsImage(jRadioButton2.isSelected());
        hideJWindow();
    } //GEN-LAST:event_jButton1hideJWindow

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField colorTextField;
    private javax.swing.JTextArea comentaryTextArea;
    private com.michaelbaranov.microba.calendar.DatePicker datePicker1;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private net.javahispano.jsignalwb.ui.JTextFieldDate jTextFieldDate1;
    private javax.swing.JLabel kindLabel;
    private javax.swing.JTextField markTitleTextField;
    // End of variables declaration//GEN-END:variables

}
