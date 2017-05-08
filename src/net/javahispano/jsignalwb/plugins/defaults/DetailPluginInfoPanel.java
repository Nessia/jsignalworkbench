/*
 * DetailPluginInfoPanel.java
 *
 * Created on 31 de agosto de 2007, 1:17
 */

package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.Window;
import java.util.ArrayList;

import javax.swing.*;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.*;


public class DetailPluginInfoPanel extends javax.swing.JPanel {
    private JSWBManager jswbManager;
    private String pluginType;
    private Plugin plugin;
    private JWindow jw;
    /** Creates new form DetailPluginInfoPanel */
    public DetailPluginInfoPanel(JSWBManager jswbManager, Plugin plugin) {
        this.jswbManager = jswbManager;
        this.plugin = plugin;
        if (plugin != null && (plugin instanceof Plugin)) {
            if (plugin instanceof AnnotationPlugin) {
                pluginType = "Annotation";
            } else if (plugin instanceof MarkPlugin) {
                pluginType = "Mark";
            } else if (plugin instanceof Loader) {
                pluginType = "Loader";
            } else if (plugin instanceof Saver) {
                pluginType = "Saver";
            } else if (plugin instanceof Algorithm) {
                pluginType = "Algorithm";
            } else if (plugin instanceof GridPlugin) {
                pluginType = "Grid";
            } else if (plugin instanceof GenericPlugin) {
                pluginType = "Generic";
            }

            initComponents();

            initPluginTypeInfo();

        }
    }

    public void initPluginTypeInfo() {
        loaderPane.setVisible(false);
        saverPane.setVisible(false);
        algorithmPane.setVisible(false);
        if (pluginType.equals("Loader")) {
            Loader loader = (Loader) plugin;
            jLabel18.setText("No");
            ArrayList<String> ext = loader.getAvalaibleExtensions();
            DefaultListModel dlm = new DefaultListModel();
            jList1.setModel(dlm);

            for (String temp : ext) {
                dlm.addElement(temp);
            }
            loaderPane.setVisible(true);
        }
        if (pluginType.equals("Saver")) {
            Saver saver = (Saver) plugin;
            jLabel19.setText(saver.getAvalaibleExtension().get(0));
            saverPane.setVisible(true);
        }
        if (pluginType.equals("Algorithm")) {
            Algorithm algorithm = (Algorithm) plugin;
            int number = algorithm.numberOfSignalsNeeded();
            if (number > 0) {
                jLabel22.setText("Maximum of " + number + " signals");
            } else if (number == 0) {
                jLabel22.setText("No limit of signals");
            } else {
                jLabel22.setText("Minimum of " + number * ( -1) + " signals");
            }
            if (algorithm.hasResultsGUI()) {
                jLabel23.setText("Yes");
            } else {
                jLabel23.setText("No");
            }
            algorithmPane.setVisible(true);
        }
    }

    public void showJWindow(Window owner) {
        jw = new JWindow(owner);
        jw.add(this);
        jw.setSize(this.getPreferredSize());
        jw.setLocationRelativeTo(owner);
        jw.setVisible(true);
    }

    private void hideJWindow() {
        jw.dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Codigo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        configureButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        loaderPane = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        saverPane = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        algorithmPane = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0,
                153, 51), 4, true), "Plugin Info"));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "General Info"));
        jLabel1.setText("Name:");

        jLabel8.setText(plugin.getName());

        jLabel2.setText("Type:");

        jLabel9.setText(pluginType);

        jLabel3.setText("Version:");

        jLabel10.setText(plugin.getPluginVersion());

        jLabel4.setText("BaseClass:");

        jLabel11.setText(plugin.getClass().getCanonicalName());

        jLabel5.setText("Create data file:");

        String value;
        if (plugin.hasDataToSave()) {
            value = "Yes";
        } else {
            value = "No";
        }
        jLabel12.setText(value);

        jLabel6.setText("Short description:");

        jLabel13.setText(plugin.getShortDescription());

        jLabel7.setText("Description:");

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 13));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(4);
        jTextArea1.setText(plugin.getDescription());
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel14.setIcon(plugin.getIcon());

        configureButton.setText("Configure Plugin");
        configureButton.setEnabled(false);
        if (plugin.hasOwnConfigureGUI()) {
            configureButton.setEnabled(true);
        }
        configureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addContainerGap()
                                              .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.LEADING)
                .addComponent(jLabel6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                          .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(10, 10, 10)
                                              .addComponent(jLabel5))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(10, 10, 10)
                                              .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(10, 10, 10)
                                              .addComponent(jLabel3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(10, 10, 10)
                                              .addComponent(jLabel2))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addGap(10, 10, 10)
                                              .addComponent(jLabel1)))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 358,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                          .addContainerGap(348, Short.MAX_VALUE)
                          .addComponent(configureButton)
                          .addContainerGap())
                );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2,
                               jLabel3, jLabel4, jLabel5, jLabel6, jLabel7});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11,
                               jLabel12, jLabel13, jLabel8, jLabel9, jScrollPane1});

        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel8))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel10))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel11))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel12))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel13))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                              .addComponent(jLabel7)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(configureButton)
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3,
                               jLabel4, jLabel5, jLabel6, jLabel7});

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        loaderPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Loader Info"));
        jLabel15.setText("Extensions:");

        jLabel16.setText("Can load partial signals:");

        jLabel18.setText("jLabel18");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {"No Items"};
            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout loaderPaneLayout = new javax.swing.GroupLayout(loaderPane);
        loaderPane.setLayout(loaderPaneLayout);
        loaderPaneLayout.setHorizontalGroup(
                loaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(loaderPaneLayout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jLabel16)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 65,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jLabel15)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(131, Short.MAX_VALUE))
                );
        loaderPaneLayout.setVerticalGroup(
                loaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(loaderPaneLayout.createSequentialGroup()
                          .addGroup(loaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(loaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                BASELINE)
                                              .addComponent(jLabel16)
                                              .addComponent(jLabel18)
                                              .addComponent(jLabel15))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        saverPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Saver Info"));
        jLabel17.setText("Extension:");

        jLabel19.setText("jLabel19");

        javax.swing.GroupLayout saverPaneLayout = new javax.swing.GroupLayout(saverPane);
        saverPane.setLayout(saverPaneLayout);
        saverPaneLayout.setHorizontalGroup(
                saverPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(saverPaneLayout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jLabel17)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 84,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(318, Short.MAX_VALUE))
                );
        saverPaneLayout.setVerticalGroup(
                saverPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(saverPaneLayout.createSequentialGroup()
                          .addGroup(saverPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel19))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        algorithmPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.
                createEtchedBorder(), "Algorithm Info"));
        jLabel20.setText("Number of signals needed:");

        jLabel21.setText("Has results window:");

        jLabel22.setText("jLabel22");

        jLabel23.setText("jLabel23");

        javax.swing.GroupLayout algorithmPaneLayout = new javax.swing.GroupLayout(algorithmPane);
        algorithmPane.setLayout(algorithmPaneLayout);
        algorithmPaneLayout.setHorizontalGroup(
                algorithmPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(algorithmPaneLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(algorithmPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(algorithmPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                          .addContainerGap(256, Short.MAX_VALUE))
                );
        algorithmPaneLayout.setVerticalGroup(
                algorithmPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(algorithmPaneLayout.createSequentialGroup()
                          .addGroup(algorithmPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel22))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(algorithmPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel23))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(algorithmPane, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1)
                                    .addComponent(loaderPane, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(saverPane, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(loaderPane, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(saverPane, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(algorithmPane, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                           javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addComponent(jButton1)
                          .addContainerGap())
                );
    } // </editor-fold>//GEN-END:initComponents

    private void configureButtonActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_configureButtonActionPerformed
        if (plugin.hasOwnConfigureGUI()) {
            plugin.launchConfigureGUI(jswbManager);
        }
    } //GEN-LAST:event_configureButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        hideJWindow();
    } //GEN-LAST:event_jButton1ActionPerformed


    // Declaracion de varibales -no modificar//GEN-BEGIN:variables
    private javax.swing.JPanel algorithmPane;
    private javax.swing.JButton configureButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel loaderPane;
    private javax.swing.JPanel saverPane;
    // Fin de declaracion de variables//GEN-END:variables

}
