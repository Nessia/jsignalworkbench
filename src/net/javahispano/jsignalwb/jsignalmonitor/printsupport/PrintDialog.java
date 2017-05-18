package net.javahispano.jsignalwb.jsignalmonitor.printsupport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import net.javahispano.jsignalwb.jsignalmonitor.JSignalMonitor;

/**
 *No esta disenhado para formar parte del API publica.
 * @author Abraham Otero
 * @version 0.5
 */
public class PrintDialog extends JDialog {

    private static final Logger LOGGER = Logger.getLogger(PrintDialog.class.getName());
    /**
     *
     */
    private static final long serialVersionUID = 5244218000610598054L;

    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JPanel jPanel2 = new JPanel();
    private ButtonGroup buttonGroup2 = new ButtonGroup();
    private ButtonGroup buttonGroup1 = new ButtonGroup();
    private JRadioButton portraitRadioButton = new JRadioButton();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JPanel jPanel3 = new JPanel();
    private JRadioButton landscapeRadioButton = new JRadioButton();
    private JPanel jPanel4 = new JPanel();
    private JRadioButton everithingRadioButton = new JRadioButton();
    private JRadioButton signalsRadioButton = new JRadioButton();
    private JPanel jPanel5 = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private FlowLayout flowLayout2 = new FlowLayout();
    private FlowLayout flowLayout3 = new FlowLayout();
    private JLabel jLabel1 = new JLabel();
    private Color color;
    private Font tipoFuente;
    private JSignalMonitor jsm;

    private JLabel jLabel2 = new JLabel();
    public PrintDialog(Window owner, String title, JSignalMonitor jsm) {
        super(owner, title);
        this.jsm = jsm;
        color = jsm.getJSMProperties().getLookAndFeelConfiguration().getColorFont();
        tipoFuente = jsm.getJSMProperties().getLookAndFeelConfiguration().getMediumFont();
        try {
            jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 200);
        this.setLocationRelativeTo(owner);
        this.setModal(true);

    }

    private void jbInit() throws Exception {
        everithingRadioButton.setFont(tipoFuente);
        signalsRadioButton.setFont(tipoFuente);
        signalsRadioButton.setSelected(true);
        landscapeRadioButton.setFont(tipoFuente);
        landscapeRadioButton.setSelected(true);
        portraitRadioButton.setFont(tipoFuente);
        portraitRadioButton.setSelected(true);
        panel1.setLayout(borderLayout1);
        jButton1.setText("Acept");
        jButton1.addActionListener(new PrintDialog_jButton1_actionAdapter(this));
        jButton2.setText("Cancel");
        jButton2.addActionListener(new PrintDialog_jButton2_actionAdapter(this));
        portraitRadioButton.setText("Portrait");
        portraitRadioButton.setFont(this.tipoFuente);
        portraitRadioButton.setForeground(color);
        jPanel2.setLayout(flowLayout1);
        landscapeRadioButton.setText("Landscape");
        landscapeRadioButton.setFont(this.tipoFuente);
        landscapeRadioButton.setForeground(color);
        jPanel4.setPreferredSize(new Dimension(195, 37));
        jPanel4.setLayout(flowLayout2);
        jPanel5.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        gridLayout1.setRows(1);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(25);
        jPanel3.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setHgap(25);
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        jLabel1.setText("Print");
        jLabel1.setFont(this.tipoFuente);
        jLabel1.setForeground(color);
        jLabel2.setText("Print orientation");
        jLabel2.setFont(this.tipoFuente);
        jLabel2.setForeground(color);
        buttonGroup1.add(portraitRadioButton);
        buttonGroup1.add(landscapeRadioButton);
        jPanel3.setBorder(BorderFactory.createEtchedBorder());
        jPanel4.setBorder(BorderFactory.createEtchedBorder());
        everithingRadioButton.setText("Everithing");
        everithingRadioButton.setFont(this.tipoFuente);
        everithingRadioButton.setForeground(color);
        signalsRadioButton.setText("Only the signals");
        signalsRadioButton.setFont(this.tipoFuente);
        signalsRadioButton.setForeground(color);
        buttonGroup2.add(everithingRadioButton);
        buttonGroup2.add(signalsRadioButton);
        getContentPane().add(panel1);
        panel1.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel3.add(jLabel2);
        jPanel3.add(jLabel2);
        jPanel3.add(landscapeRadioButton);
        jPanel3.add(portraitRadioButton);
        jPanel4.add(jLabel1);
        jPanel4.add(signalsRadioButton);
        jPanel4.add(everithingRadioButton);
        jPanel5.add(jPanel4);
        jPanel5.add(jPanel3);
        panel1.add(jPanel2, java.awt.BorderLayout.NORTH);
        panel1.add(jPanel5, java.awt.BorderLayout.CENTER);
    }

    void jButton2_actionPerformed() {
        this.dispose();
    }

    void jButton1_actionPerformed() {
        int orientation;
        if (portraitRadioButton.isSelected()) {
            orientation = PrintUtilities.PORTRAIT;
        } else {
            orientation = PrintUtilities.LANDSCAPE;
        }
        this.dispose();
        if (this.everithingRadioButton.isSelected()) {
            jsm.printAll(orientation);
        } else {
            jsm.printSignals(orientation);
        }
    }
}


class PrintDialog_jButton1_actionAdapter implements ActionListener {
    private PrintDialog adaptee;
    PrintDialog_jButton1_actionAdapter(PrintDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed();
    }
}


class PrintDialog_jButton2_actionAdapter implements ActionListener {
    private PrintDialog adaptee;
    PrintDialog_jButton2_actionAdapter(PrintDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed();
    }
}
