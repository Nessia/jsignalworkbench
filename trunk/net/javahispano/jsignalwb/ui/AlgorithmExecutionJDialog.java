/*
 * AlgorithmExecutionJDialog.java
 *
 * Created on 15 de junio de 2007, 12:39
 */

package net.javahispano.jsignalwb.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import net.javahispano.jsignalwb.JSWBManager;
import net.javahispano.jsignalwb.SignalIntervalProperties;
import net.javahispano.jsignalwb.plugins.Algorithm;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;

/**
 *
 * No forma parte del API.
 */
public class AlgorithmExecutionJDialog extends javax.swing.JDialog
        implements PropertyChangeListener{
    private SwingWorker sw;
    private Algorithm alg;
    /**
     * Creates new form AlgorithmExecutionJDialog
     * @param alg {@link Algorithm} a ejecutar
     * @param signals lista de {@link SignalIntervalProperties} que indica los intervalos de las
     * se�ales, o se�ales completas, sobre las cuales se debe ejecutar el algoritmo.
     * @param jswbManager {@link JSWBManager} Manager general de la aplicacion
     */
    public AlgorithmExecutionJDialog(Algorithm alg,
            ArrayList<SignalIntervalProperties> signals,
            JSWBManager jswbManager) {
        super(jswbManager.getParentWindow(),"AlgorithmExecution");
        setModal(true);
        this.alg=alg;
        initComponents();
        jProgressBar1.setIndeterminate(true);
        jLabel1.setText(alg.getName()+" execution");
        jLabel2.setText("");
        jLabel3.setText("");
        AlgorithmRunner ar = new AlgorithmRunner(alg,jswbManager,signals);
        ar.addPropertyChangeListener(this);
        
        setLocationRelativeTo(this.getOwner());
        sw=(SwingWorker)ar;
        jswbManager.setJSMIgnoreRepaintMode(true);
        ar.execute();
        setVisible(true);
        jswbManager.setJSMIgnoreRepaintMode(false);
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName()+"---->"+evt.getNewValue());
        if ("progress".equals(evt.getPropertyName())) {
            
            jProgressBar1.setIndeterminate(false);
            
            jProgressBar1.setValue((Integer) evt.getNewValue());
        }else if("state".equals(evt.getPropertyName())){
            if(evt.getNewValue().equals(AlgorithmRunner.StateValue.DONE)){
                
                dispose();
                
            }
        }else if("task".equals(evt.getPropertyName())){
            jLabel2.setText("TASK: "+evt.getNewValue().toString());
            
            repaint();
            
            
        }else if("signal".equals(evt.getPropertyName())){
            jLabel3.setText("SIGNAL: "+evt.getNewValue().toString());
            
            repaint();
            
            
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");

        jButton1.setText("Stop");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        alg.cancelExecution();
        sw.cancel(false);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
    
}
