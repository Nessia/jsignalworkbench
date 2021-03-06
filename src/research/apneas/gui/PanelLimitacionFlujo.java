/*
 * PanelLimitacionFlujo.java
 *
 * Created on 28 de enero de 2008, 15:18
 */

package research.apneas.gui;

import java.awt.Window;


import research.apneas.LimitacionFlujo;

/**
 *
 * @author  b
 */
public class PanelLimitacionFlujo extends javax.swing.JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 2832977794177141800L;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel4;
    private research.apneas.gui.PanelDatosComunes panelDatosComunes1;
    private javax.swing.JTextField textFieldReduction;
    // End of variables declaration//GEN-END:variables

    private Window jw;
    private LimitacionFlujo limitacionFlujo;

    /** Creates new form PanelLimitacionFlujo */
    public PanelLimitacionFlujo(LimitacionFlujo limitacionFlujo) {

        this.limitacionFlujo = limitacionFlujo;
        initComponents();
        this.setDuracion(limitacionFlujo.getDuracion());
        setEvento("Airflow limitation");
        setFin(limitacionFlujo.getFinAbsoluto());
        setPosibilidad((byte) limitacionFlujo.getPosibilidad());
        setPrincipio(limitacionFlujo.getPrincipioAbsoluto());
        setReduction(limitacionFlujo.getPorcentajeReduccion());
        setUsarEnEstadisticas(limitacionFlujo.isUsarEnEstadisticas());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDatosComunes1 = new research.apneas.gui.PanelDatosComunes(limitacionFlujo);
        jLabel4 = new javax.swing.JLabel();
        textFieldReduction = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 0), 5, true));

        panelDatosComunes1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelDatosComunes1.setMinimumSize(new java.awt.Dimension(318, 181));
        panelDatosComunes1.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Reduction:");

        textFieldReduction.setForeground(java.awt.Color.blue);
        textFieldReduction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldReduction.setText("jTextField1");

        jButton1.setForeground(java.awt.Color.blue);
        jButton1.setText("Acept changes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setForeground(java.awt.Color.blue);
        jButton2.setText("Discard changes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addGap(26, 26, 26)
                          .addComponent(panelDatosComunes1, javax.swing.GroupLayout.PREFERRED_SIZE, 295,
                                        Short.MAX_VALUE)
                          .addGap(22, 22, 22))
                .addGroup(layout.createSequentialGroup()
                          .addGap(43, 43, 43)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jLabel4))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(textFieldReduction, javax.swing.GroupLayout.Alignment.TRAILING,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE, 146,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap(35, Short.MAX_VALUE))
                );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(panelDatosComunes1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(textFieldReduction, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addGap(18, 18, 18)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                          .addGap(37, 37, 37))
                );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

    } // </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        jw.dispose();
    } //GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        jw.dispose();
    } //GEN-LAST:event_jButton2ActionPerformed

    public long getPrincipio() {
        return this.panelDatosComunes1.getPrincipio();
    }

    public void setPrincipio(long principio) {
        panelDatosComunes1.setPrincipio(principio);
    }

    public long getFin() {
        return panelDatosComunes1.getFin();
    }

    public void setFin(long fin) {
        panelDatosComunes1.setFin(fin);
    }

    public void setPosibilidad(byte posibilidad) {
        panelDatosComunes1.setPosibilidad(posibilidad);
    }

    public void setEvento(String evento) {
        panelDatosComunes1.setEvento(evento);
    }

    public float getDuracion() {
        return panelDatosComunes1.getDuracion();
    }

    public void setDuracion(float duracion) {
        panelDatosComunes1.setDuracion(duracion);
    }

    public boolean isUsarEnEstadisticas() {
        return panelDatosComunes1.isUsarEnEstadisticas();
    }

    public void setUsarEnEstadisticas(boolean usarEnEstadisticas) {
        panelDatosComunes1.setUsarEnEstadisticas(usarEnEstadisticas);
    }

    public boolean validateData() {
        try {
            Float.parseFloat(textFieldReduction.getText());
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public float getFallInSpO2() {
        final String text = textFieldReduction.getText();
        return Float.parseFloat(text.substring(0, text.length() - 1));
    }

    public void setReduction(float fallInSpO2) {
        textFieldReduction.setText(fallInSpO2 + "%");
    }

    public void showJWindow(Window owner) {
        jw = new Window(owner);
        jw.add(this);
        jw.setSize(400, 350);
        jw.setLocationRelativeTo(owner);
        jw.setVisible(true);
    }




}
