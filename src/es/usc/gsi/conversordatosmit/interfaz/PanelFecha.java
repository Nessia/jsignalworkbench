package es.usc.gsi.conversordatosmit.interfaz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.*;

import es.usc.gsi.conversordatosmit.excepciones.*;
import es.usc.gsi.conversordatosmit.utilidades.ParseadorFecha;


public class PanelFecha extends JPanel implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(PanelFecha.class.getName());

    /**
    *
    */
    private static final long serialVersionUID = -3750954474803338557L;
    private String fechaInicio;
    private String fechaFin;

    private JTextField tfFechaInicio;
    private JTextField tfFechaFin;

    private JLabel lbFechaInicio = new JLabel("Fecha inicio remuestreo: ");
    private JLabel lbFechaFin = new JLabel("Fecha fin remuestreo: ");

    private JButton btnResetInicio = new JButton("Valor original");
    private JButton btnResetFin = new JButton("Valor original");

//*******************************************************************************

    public PanelFecha(String fechaInicio, String fechaFin) {
         this.setLayout(new GridLayout(2, 1));

         this.fechaInicio = fechaInicio;
         this.fechaFin = fechaFin;

         this.tfFechaInicio = new JTextField(fechaInicio);
         this.tfFechaFin = new JTextField(fechaFin);

//    this.setSize(400,100);

         this.btnResetInicio.setActionCommand("RESET_INICIO");
         this.btnResetInicio.addActionListener(this);
         this.btnResetFin.setActionCommand("RESET_FIN");
         this.btnResetFin.addActionListener(this);

         Dimension d = this.lbFechaInicio.getPreferredSize();
         this.lbFechaFin.setPreferredSize(d);

         JPanel inicioPanel = new JPanel();

         inicioPanel.add(lbFechaInicio);
         inicioPanel.add(tfFechaInicio);
         inicioPanel.add(btnResetInicio);

         JPanel finPanel = new JPanel();

         finPanel.add(lbFechaFin);
         finPanel.add(tfFechaFin);
         finPanel.add(btnResetFin);

         this.add(inicioPanel);
         this.add(finPanel);
     }

//*******************************************************************************

     public String getFechaInicio() throws FechasIncorrectasException {
         String fIni = tfFechaInicio.getText();
         String fFin = tfFechaFin.getText();

         try {
             ParseadorFecha.verificaFecha(fIni);
         } catch (ParseException e) {
             throw new FechaInicialIncorrectaException();
         }

         long diferencia = ParseadorFecha.calculaDiferencia(fIni, fFin);
         if (diferencia < 0) {
             throw new FechaFinalMenorInicialException();
         }
         if (diferencia == 0) {
             throw new FechaFinalIgualInicialException();
         }
         long diferenciaPropia = ParseadorFecha.calculaDiferencia(fIni,
                 fechaInicio);
         if (diferenciaPropia > 0) {
             throw new FechaInicialMenorOriginalException();
         }
         return tfFechaInicio.getText();
     }

//*******************************************************************************

     public String getFechaFin() throws FechasIncorrectasException {

         String fFin = tfFechaFin.getText();

         try {
             ParseadorFecha.verificaFecha(fFin);
         } catch (ParseException e) {
             throw new FechaFinalIncorrectaException();
         }

         long diferenciaPropia = ParseadorFecha.calculaDiferencia(fechaFin,
                 fFin);
         if (diferenciaPropia > 0) {
             throw new FechaFinalMayorOriginalException();
         }

         return this.tfFechaFin.getText();

     }

//*******************************************************************************

     public void reset() {
         this.tfFechaInicio.setText(fechaInicio);
         this.tfFechaFin.setText(fechaFin);
     }

//*******************************************************************************

     public void resetInicio() {
         this.tfFechaInicio.setText(fechaInicio);
     }

//*******************************************************************************

     public void resetFin() {
         this.tfFechaFin.setText(fechaFin);
     }

//*******************************************************************************

     // Eventos de los botones

     @Override
     public void actionPerformed(ActionEvent e) {
         LOGGER.info(e.getActionCommand());
         if ("RESET_INICIO".equals(e.getActionCommand())) {
             this.resetInicio();
         }
         if ("RESET_FIN".equals(e.getActionCommand())) {
             this.resetFin();
         }
     }

} // Fin clase PanelFecha
