/*
 * PluginManagerPanel.java
 *
 * Created on 23 de julio de 2007, 13:38
 */

package net.javahispano.jsignalwb.plugins.framework;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.plugins.Plugin;

/**
 *
 * @author  Compaq_Propietario
 */
public class PluginManagerPanel extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(PluginManagerPanel.class.getName());
    /**
     *
     */
    private static final long serialVersionUID = 5798449466901514533L;

    public enum Modes { ALL, LOADERS, SAVERS, ALGORITHMS, GENERICS, GRIDS, MARKS, ANNOTATIONS, FROM_JAR_FILE}

 // Declaracion de varibales -no modificar//GEN-BEGIN:variables
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private JRadioButton rbAlgorithms;
    private JRadioButton rbAll;
    private JRadioButton rbAnnotations;
    private JRadioButton rbFromJarFile;
    private JRadioButton rbGenerics;
    private JRadioButton rbGrids;
    private JRadioButton rbLoaders;
    private JRadioButton rbMarks;
    private JRadioButton rbSavers;
    // Fin de declaracion de variables//GEN-END:variables
    private JWindow jw;

    //private JSWBManager jswbManager;
    private HashMap<String, JarFile> pluginJarAssociation;
    private HashMap<JarFile, File> jarFileFileAssociation;
    private JFileChooser jfc;
    private Modes mode;

    /** Creates new form PluginManagerPanel */
    public PluginManagerPanel(/*JSWBManager jswbManager*/) {
        pluginJarAssociation = new HashMap<String, JarFile>();
        jarFileFileAssociation = new HashMap<JarFile, File>();
        initComponents();
        jfc = null;
        mode = Modes.ALL;

        jTable1.getColumnModel().getColumn(4).setCellEditor(new LoadedTableCellEditorAndRenderer());
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new LoadedTableCellEditorAndRenderer());
        Image image = Toolkit.getDefaultToolkit().createImage(
                PluginManagerPanel.class.getResource("images/more.png"));
        ImageIcon icon = new ImageIcon(
                image.getScaledInstance(12, 12, Image.SCALE_SMOOTH));
        jTable1.getColumnModel().getColumn(5).setCellEditor(new JButtonTableCellEditorAndRenderer(
                icon, "Click for detailed info"));
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new JButtonTableCellEditorAndRenderer(
                icon, "Click for detailed info"));
        Image image2 = Toolkit.getDefaultToolkit().createImage(
                PluginManagerPanel.class.getResource("images/trash.png"));
        ImageIcon icon2 = new ImageIcon(
                image2.getScaledInstance(12, 12, Image.SCALE_SMOOTH));
        jTable1.getColumnModel().getColumn(6).setCellEditor(new JButtonTableCellEditorAndRenderer(
                icon2, "Click to uninstall plugin"));
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new JButtonTableCellEditorAndRenderer(
                icon2, "Click to uninstall plugin"));
        jTable1.setRowHeight(20);
        refreshJTable();
    }

    void refreshJTable() {
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        File[] plugins = JSWBManager.getPluginManager().getInstalledPlugins();
        if (plugins != null) {
            JarFile jarFile;
            String name;
            String type;
            for (int index = 0; index < plugins.length; index++) {
                try {
                    jarFile = new JarFile(plugins[index]);
                    name = jarFile.getManifest().getMainAttributes().getValue("PluginName");
                    type = jarFile.getManifest().getMainAttributes().getValue("PluginType");
                    if (JSWBManager.getPluginManager().isPluginRegistered(type, name)) {
                        //dlm.addElement(name);
                        pluginJarAssociation.put(type + ":" + name, jarFile);
                        jarFileFileAssociation.put(jarFile, plugins[index]);
                    }
                    jarFile.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                }
            }
            Map<String, ArrayList<String>> registeredPlugins = JSWBManager.getPluginManager().getRegisteredPlugins();
            Iterator<String> it = registeredPlugins.keySet().iterator();
            ArrayList<String> temp;
            String kind;
            while (it.hasNext()) {
                kind = it.next();
//                if (mode == 0 || mode == 8
//                    || (kind.equals("loader") && mode == 1)
//                    || (kind.equals("saver") && mode == 2)
//                    || (kind.equals("algorithm") && mode == 3)
//                    || (kind.equals("generic") && mode == 4)
//                    || (kind.equals("grid") && mode == 5)
//                    || (kind.equals("mark") && mode == 6)
//                    || (kind.equals("annotation") && mode == 7)) {
                if (mode == Modes.ALL || mode == Modes.FROM_JAR_FILE
                      || ("loader".equals(kind) && mode == Modes.LOADERS)
                      || ("saver".equals(kind) && mode == Modes.SAVERS)
                      || ("algorithm".equals(kind) && mode == Modes.ALGORITHMS)
                      || ("generic".equals(kind) && mode == Modes.GENERICS)
                      || ("grid".equals(kind) && mode == Modes.GRIDS)
                      || ("mark".equals(kind) && mode == Modes.MARKS)
                      || ("annotation".equals(kind) && mode == Modes.ANNOTATIONS)) {
                    temp = registeredPlugins.get(kind);
                    for (String pluginName : temp) {
                        if (mode != Modes.FROM_JAR_FILE || pluginJarAssociation.containsKey(kind + ":" + pluginName)) {
                            if (JSWBManager.getPluginManager().isPluginLoaded(kind + ":" + pluginName)) {
                                //loadButton.setEnabled(false);
                                //detailsButton.setEnabled(true);
                                Plugin plug = JSWBManager.getPluginManager().getPlugin(kind + ":" + pluginName);
                                JButton bt = new JButton(new PluginDetailAction(kind + ":" + pluginName, this));
                                JButton uninstall = null;
                                if (pluginJarAssociation.containsKey(kind + ":" + pluginName)) {
                                    JarFile jar = pluginJarAssociation.get(kind + ":" + pluginName);
                                    File file = jarFileFileAssociation.get(jar);
                                    uninstall = new JButton(new PluginUninstallAction(file, kind + ":" + pluginName, this));
                                }
                                //bt.setFocusable(false);
                                dtm.addRow(new Object[] {pluginName, kind,
                                           plug.getPluginVersion(),
                                           plug.getShortDescription(), new JLabel("OK"), bt, uninstall});

//                            if(pluginJarAssociation.containsKey(name))
//                                jButton4.setEnabled(true);
//                            else
//                                jButton4.setEnabled(false);
                            } else {
//                                loadButton.setEnabled(true);
//                                detailsButton.setEnabled(false);
                                if (pluginJarAssociation.containsKey(kind + ":" + pluginName)) {
                                    //jButton4.setEnabled(true);
                                    try {
                                        Attributes att =
                                                pluginJarAssociation.get(kind + ":" + pluginName).getManifest().
                                                getMainAttributes();

                                        String version = att.getValue("PluginVersion");
                                        if (version == null) {
                                            version = "Try to load";
                                        }
                                        String shortDesc =
                                                att.getValue("PluginShortDescription");
                                        if (shortDesc == null) {
                                            shortDesc = "Try to load";
                                        }
                                        JarFile jar = pluginJarAssociation.get(kind + ":" + pluginName);
                                        File file = jarFileFileAssociation.get(jar);
                                        JButton uninstall = new JButton(new PluginUninstallAction(file,
                                                kind + ":" + pluginName, this));
                                        dtm.addRow(new Object[] {pluginName, kind,
                                                version,
                                                shortDesc,
                                                new JButton(new PluginLoadAction(kind + ":" + pluginName, this)),
                                                new JButton(new PluginDetailAction(kind + ":" + pluginName, this)),
                                                uninstall});
                                    } catch (IOException ex) {
                                        LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                                    }
                                } else {
                                    //jButton4.setEnabled(false);
                                    dtm.addRow(new Object[] {pluginName, kind,
                                               "Try to load",
                                               "Try to load",
                                               new JButton(new PluginLoadAction(kind + ":" + pluginName, this)),
                                               new JButton(new PluginDetailAction(kind + ":" + pluginName, this)), null});
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setColumnsWidth() {
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        int width = jScrollPane2.getSize().width;
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(65);

        jTable1.getColumnModel().getColumn(3).setPreferredWidth(width - 381);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(29);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(24);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(20);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Codigo Generado  ">//GEN-BEGIN:initComponents
    private void initComponents() {
        ButtonGroup buttonGroup1 = new ButtonGroup();
        JButton jButton2 = new JButton();
        JButton jButton3 = new JButton();
        jScrollPane2 = new JScrollPane();
        jTable1 = new JTable();
        JPanel jPanel1 = new JPanel();
        rbAll = new JRadioButton();
        rbLoaders = new JRadioButton();
        rbSavers = new JRadioButton();
        rbAlgorithms = new JRadioButton();
        rbGrids = new JRadioButton();
        rbGenerics = new JRadioButton();
        rbMarks = new JRadioButton();
        rbAnnotations = new JRadioButton();
        rbFromJarFile = new JRadioButton();

        setBorder(BorderFactory.createTitledBorder(new LineBorder(new java.awt.Color(255,
                153, 0), 5, true), "Plugin Manager"));
        jButton2.setText("Search new plugins");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed();
            }
        });

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed();
            }
        });

        jTable1.setModel(new DefaultTableModel(
                new Object[][] { },
                new String[] { "Name", "Kind", "Version", "ShortDescription", "Load", "Info", "" }
                ) {
                     private static final long serialVersionUID = -57287540408265881L;
                     Class<?>[] types = new Class[] {
                            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                            java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
                     };
                     boolean[] canEdit = new boolean[] {
                                         false, false, false, false, true, true, true
                     };

                     @Override
                     public Class<?> getColumnClass(int columnIndex) {
                         return types[columnIndex];
                     }

                     @Override
                     public boolean isCellEditable(int rowIndex, int columnIndex) {
                         return canEdit[columnIndex];
                     }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        buttonGroup1.add(rbAll);
        rbAll.setText("All");
        rbAll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbAll.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbAll.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbLoaders);
        rbLoaders.setText("Loaders");
        rbLoaders.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbLoaders.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbLoaders.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbSavers);
        rbSavers.setText("Savers");
        rbSavers.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbSavers.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbSavers.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbAlgorithms);
        rbAlgorithms.setText("Algorithms");
        rbAlgorithms.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbAlgorithms.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbAlgorithms.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbGrids);
        rbGrids.setText("Grids");
        rbGrids.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbGrids.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbGrids.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbGenerics);
        rbGenerics.setText("Generics");
        rbGenerics.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbGenerics.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbGenerics.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbMarks);
        rbMarks.setText("Marks");
        rbMarks.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbMarks.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbMarks.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbAnnotations);
        rbAnnotations.setText("Annotations");
        rbAnnotations.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbAnnotations.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbAnnotations.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbFromJarFile);
        rbFromJarFile.setText("From Jar File");
        rbFromJarFile.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rbFromJarFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rbFromJarFile.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonsActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(rbAll)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbLoaders)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbSavers)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbAlgorithms)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbGrids)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbGenerics)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbMarks)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbAnnotations)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(rbFromJarFile)
                          .addContainerGap(136, Short.MAX_VALUE))
                );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(rbAll)
                                    .addComponent(rbLoaders)
                                    .addComponent(rbSavers)
                                    .addComponent(rbAlgorithms)
                                    .addComponent(rbGrids)
                                    .addComponent(rbGenerics)
                                    .addComponent(rbMarks)
                                    .addComponent(rbAnnotations)
                                    .addComponent(rbFromJarFile))
                          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING,
                                                  GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
                                                  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                              .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 183,
                GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 425,
                Short.MAX_VALUE)
                                              .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 91,
                GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 346,
                                        GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton3))
                          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
    } // </editor-fold>//GEN-END:initComponents

    private void jRadioButtonsActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jRadioButtonsActionPerformed
        if (evt.getSource().equals(rbAll)) {
            mode = Modes.ALL;
        }
        if (evt.getSource().equals(rbLoaders)) {
            mode = Modes.LOADERS;
        }
        if (evt.getSource().equals(rbSavers)) {
            mode = Modes.SAVERS;
        }
        if (evt.getSource().equals(rbAlgorithms)) {
            mode = Modes.ALGORITHMS;
        }
        if (evt.getSource().equals(rbGenerics)) {
            mode = Modes.GENERICS;
        }
        if (evt.getSource().equals(rbGrids)) {
            mode = Modes.GRIDS;
        }
        if (evt.getSource().equals(rbMarks)) {
            mode = Modes.MARKS;
        }
        if (evt.getSource().equals(rbAnnotations)) {
            mode = Modes.ANNOTATIONS;
        }
        if (evt.getSource().equals(rbFromJarFile)) {
            mode = Modes.FROM_JAR_FILE;
        }
        refreshJTable();
//        refreshList();
    } //GEN-LAST:event_jRadioButtonsActionPerformed

    private void jButton2ActionPerformed() { //GEN-FIRST:event_jButton2ActionPerformed
        if (jfc == null) {
            jfc = new JFileChooser(".");
        }
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setDialogTitle("Select the directory that contents the plugins");
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            if (file.isDirectory()) {
                JSWBManager.getPluginManager().searchPlugins(file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Valid plugins from " + file.getAbsolutePath() + " installed");
            }
        }
        refreshJTable();
    } //GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed() { //GEN-FIRST:event_jButton3ActionPerformed
        hideJWindow();
    } //GEN-LAST:event_jButton3ActionPerformed

    public void showJWindow(Window owner) {
        jw = new JWindow(owner);
        //jw.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
        jw.add(this);
        jw.setSize(this.getPreferredSize());
        jw.setLocationRelativeTo(owner);

        jw.setVisible(true);
        setColumnsWidth();
    }

    private void hideJWindow() {
        jw.dispose();
    }


}
