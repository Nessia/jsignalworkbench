package net.javahispano.plugins.basicstats.ui;

import java.awt.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import net.javahispano.plugins.basicstats.MyFloat;
import net.javahispano.plugins.basicstats.ResultadosEstadisticos;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

class PanelMostrarEstadisticos extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -5354805954330260351L;
    private static final Logger LOGGER = Logger.getLogger(PanelMostrarEstadisticos.class.getName());
    private static final String FONT_FAMILY = "Dialog";
    private static final Font REGULAR_FONT = new Font(FONT_FAMILY, 1, 14);
    private static final Font BIG_FONT = new Font(FONT_FAMILY, 1, 16);
    private static final Color COLOR1 = new Color(78, 110, 255);
    /*
     * Atribute
     */

    private ResultadosEstadisticos resultados_estadisticos;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanel panel_percentiles = new JPanel();
    private JCheckBox checkbox_o_label_percentiles = new JCheckBox();
    private JScrollPane scroll_panel_percentiles = new JScrollPane();
    //Codigo mio
    private String[] tmp = {"Percentil", "Valor"};
    private JTable table = new JTable(new String[6][2], tmp);
    private GridLayout gridLayout1 = new GridLayout();
    private JLabel jLabel3 = new JLabel();
    private JLabel media = new JLabel();
    private JLabel mediana = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel varianza = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel desviacion_tipica = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JLabel error_estandar = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JLabel cociente_vaiacion = new JLabel();
    private JLabel intervalo_de_confianza_checkbox = new JLabel();
    private JPanel panel_intervalos = new JPanel();
    private JLabel intervalo_de_confianza_menor = new JLabel();
    private JLabel intervalo_de_confianza_mayor = new JLabel();
    private JPanel jPanel6 = new JPanel();
//    private TitledBorder titledBorder1;
    private JPanel jPanel7 = new JPanel();
    private BorderLayout borderLayout4 = new BorderLayout();
    private JPanel jPanel8 = new JPanel();
    private JLabel jLabel4 = new JLabel();
    private JScrollPane jPanel9 = new JScrollPane();
    private JTextArea comentario = new JTextArea();
    private BorderLayout borderLayout6 = new BorderLayout();
    private JPanel jPanel10 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel11 = new JPanel();
    private GridLayout gridLayout2 = new GridLayout();
    private JLabel jLabel5 = new JLabel();
    private JTextField nombreSenal = new JTextField();
    private JLabel fechaInicio = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel fecha_fin = new JLabel();
    private GridLayout gridLayout3 = new GridLayout();

    /**
     * Si se le pasa false no permite borrar los percentiles, ya que cambia
     * el checbox por un label.
     * @param resultados_estadisticos
     * @param puede_borrar_percentiles
     */
    PanelMostrarEstadisticos(ResultadosEstadisticos
                                    resultados_estadisticos,
                                    boolean puede_borrar_percentiles) {
        this(resultados_estadisticos);
        if (!puede_borrar_percentiles) {
            panel_percentiles.remove(checkbox_o_label_percentiles);
            JLabel label_percentiles = new JLabel();
            label_percentiles.setFont(REGULAR_FONT);
            label_percentiles.setForeground(Color.blue);
            label_percentiles.setToolTipText("");
            label_percentiles.setText("Percentiles:");
            panel_percentiles.add(label_percentiles);
        }

    }

    PanelMostrarEstadisticos(ResultadosEstadisticos
                                    resultadosEstadisticos) {
        this.resultados_estadisticos = resultadosEstadisticos;
        try {
            jbInit();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        nombreSenal.setText(resultadosEstadisticos.getNombreSenhal());
        fecha_fin.setText(resultadosEstadisticos.getFechaFin());
        fechaInicio.setText(resultadosEstadisticos.getFechaInicio());
        media.setText(MyFloat.formateaNumero(resultadosEstadisticos.
                                             getMediaAritmetica()));
        mediana.setText(MyFloat.formateaNumero(resultadosEstadisticos.
                                               getMediana()));
        varianza.setText(MyFloat.formateaNumero(resultadosEstadisticos.
                                                getVarianza()));
        desviacion_tipica.setText(MyFloat.formateaNumero(
                resultadosEstadisticos.getDesviacionTipica()));
        error_estandar.setText(MyFloat.formateaNumero(resultadosEstadisticos.
                getErrorEstandar()));
        cociente_vaiacion.setText(MyFloat.formateaNumero(
                resultadosEstadisticos.getCocienteDeVariacion()));
        float[] tmp = new float[2];
        tmp[0] = resultadosEstadisticos.getIntervaloDeConfianza()[0];
        tmp[1] = resultadosEstadisticos.getIntervaloDeConfianza()[1];
        this.setIntervaloDeConfianza(tmp);
        if (resultadosEstadisticos.getPercentiles() != null) {
            Map<String,String> percentiles_has_map = resultadosEstadisticos.getPercentiles();
            Set<Entry<String, String>> percentiles_set = percentiles_has_map.entrySet();
            Iterator<Entry<String, String>> it = percentiles_set.iterator();
            int num_percentiles = 0;
            //Ponemos todos los percentiles
            while (it.hasNext()) {
                Map.Entry<String, String> percentil_map_entry = it.next();
                Object percentil = percentil_map_entry.getKey();
                Object percentil_valor = percentil_map_entry.getValue();
                percentil_valor = MyFloat.formateaNumero((String)
                        percentil_valor);
                table.setValueAt(percentil, num_percentiles, 0);
                table.setValueAt(percentil_valor, num_percentiles, 1);
                num_percentiles++;
            }
        } else {
            jPanel3.remove(panel_percentiles);
            jPanel3.remove(scroll_panel_percentiles);
            jPanel3.setPreferredSize(new Dimension(72, 200));
            jPanel7.setPreferredSize(new Dimension(72, 170));
            validate();
        }

        comentario.setText(resultadosEstadisticos.getComentario());

    }

    private void jbInit() throws Exception {
        checkbox_o_label_percentiles.setSelected(true);
        TitledBorder titledBorder1 = new TitledBorder("");
        this.setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout3);
        checkbox_o_label_percentiles.setFont(REGULAR_FONT);
        checkbox_o_label_percentiles.setForeground(Color.blue);
        checkbox_o_label_percentiles.setToolTipText("");
        checkbox_o_label_percentiles.setText("Percentiles:");
        jPanel3.setPreferredSize(new Dimension(72, 250));
        jPanel2.setLayout(gridLayout1);
        gridLayout1.setColumns(2);
        gridLayout1.setHgap(2);
        gridLayout1.setRows(7);
        gridLayout1.setVgap(2);
        jLabel3.setFont(REGULAR_FONT);
        jLabel3.setForeground(Color.blue);
        jLabel3.setText("Media:");
        media.setFont(REGULAR_FONT);
        media.setText("jLabel4");
        mediana.setFont(REGULAR_FONT);
        mediana.setText("jLabel5");
        jLabel6.setFont(REGULAR_FONT);
        jLabel6.setForeground(Color.blue);
        jLabel6.setText("Varianza:");
        varianza.setFont(REGULAR_FONT);
        varianza.setText("jLabel7");
        jLabel8.setFont(REGULAR_FONT);
        jLabel8.setForeground(Color.blue);
        jLabel8.setText("Desviacion tipica:");
        desviacion_tipica.setFont(REGULAR_FONT);
        desviacion_tipica.setText("jLabel9");
        jLabel10.setFont(REGULAR_FONT);
        jLabel10.setForeground(Color.blue);
        jLabel10.setText("Mediana:");
        jLabel11.setFont(REGULAR_FONT);
        jLabel11.setForeground(Color.blue);
        jLabel11.setText("Error estandar:");
        error_estandar.setFont(REGULAR_FONT);
        error_estandar.setText("jLabel12");
        jLabel13.setFont(REGULAR_FONT);
        jLabel13.setForeground(Color.blue);
        jLabel13.setText("Cociente de variacion:");
        cociente_vaiacion.setFont(REGULAR_FONT);
        cociente_vaiacion.setText("jLabel14");
        intervalo_de_confianza_checkbox.setFont(REGULAR_FONT);
        intervalo_de_confianza_checkbox.setForeground(Color.blue);
        intervalo_de_confianza_checkbox.setToolTipText("");
        intervalo_de_confianza_checkbox.setText("Intervalo de confianza del 95%:");
        intervalo_de_confianza_menor.setFont(REGULAR_FONT);
        intervalo_de_confianza_mayor.setFont(REGULAR_FONT);
        panel_intervalos.setLayout(gridLayout3);
        jPanel2.setBorder(titledBorder1);
        this.setFont(REGULAR_FONT);
        jPanel7.setLayout(borderLayout4);
        jLabel4.setFont(REGULAR_FONT);
        jLabel4.setForeground(Color.blue);
        jLabel4.setText("Comentario:");
        jPanel6.setLayout(borderLayout6);
        jLabel1.setFont(BIG_FONT);
        jLabel1.setForeground(Color.red);
        jLabel1.setText("Resultado de los estadisticos:");
        jPanel11.setLayout(gridLayout2);
        gridLayout2.setColumns(2);
        gridLayout2.setRows(3);
        jLabel5.setFont(REGULAR_FONT);
        jLabel5.setForeground(COLOR1);
        jLabel5.setToolTipText("Nombre de la senhal sobre la cual se efectuo el calculo.");
        jLabel5.setText("Nombre de la senhal:");
        nombreSenal.setToolTipText("Nombre de la senhal sobre la cual se efectuo el calculo.");
        nombreSenal.setText("jTextField1");
        fechaInicio.setFont(REGULAR_FONT);
        fechaInicio.setToolTipText("Instante de esa senhal a partir del cual se realizo el calculo.");
        fechaInicio.setText("jLabel7");
        jLabel9.setFont(REGULAR_FONT);
        jLabel9.setForeground(COLOR1);
        jLabel9.setToolTipText("Instante de esa senhal a partir del cual se realizo el calculo.");
        jLabel9.setText("Tiempo de incio:");
        jLabel12.setFont(REGULAR_FONT);
        jLabel12.setForeground(COLOR1);
        jLabel12.setToolTipText("Instante de esa senhal hasta donde se realizo el calculo.");
        jLabel12.setText("Tiempo de fin:");
        fecha_fin.setFont(REGULAR_FONT);
        fecha_fin.setToolTipText("Instante de esa senhal hasta donde se realizo el calculo.");
        fecha_fin.setText("jLabel14");
        jPanel11.setBorder(titledBorder1);
        jPanel7.setFont(REGULAR_FONT);
        jPanel7.setPreferredSize(new Dimension(446, 130));
        table.setToolTipText("Si desea anhadir mas percentiles simplemente edite las columans de la izquierda.");
        gridLayout3.setColumns(2);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(panel_percentiles, BorderLayout.NORTH);
        panel_percentiles.add(checkbox_o_label_percentiles, null);
        jPanel3.add(jPanel7, BorderLayout.SOUTH);
        jPanel7.add(jPanel8, BorderLayout.NORTH);
        jPanel8.add(jLabel4, null);
        jPanel7.add(jPanel9, BorderLayout.CENTER);
        jPanel3.add(scroll_panel_percentiles, BorderLayout.CENTER);
        scroll_panel_percentiles.getViewport().add(table, null);
        jPanel9.getViewport().add(comentario, null);
        jPanel1.add(jPanel6, BorderLayout.NORTH);
        jPanel6.add(jPanel10, BorderLayout.NORTH);
        jPanel10.add(jLabel1, null);
        jPanel6.add(jPanel11, BorderLayout.CENTER);
        jPanel11.add(jLabel5, null);
        jPanel11.add(nombreSenal, null);
        jPanel11.add(jLabel9, null);
        jPanel11.add(fechaInicio, null);
        jPanel11.add(jLabel12, null);
        jPanel11.add(fecha_fin, null);
        jPanel2.add(jLabel3, null);
        jPanel2.add(media, null);
        jPanel2.add(intervalo_de_confianza_checkbox, null);
        jPanel2.add(panel_intervalos, null);
        jPanel2.add(jLabel10, null);
        jPanel2.add(mediana, null);
        jPanel2.add(jLabel6, null);
        jPanel2.add(varianza, null);
        jPanel2.add(jLabel8, null);
        jPanel2.add(desviacion_tipica, null);
        jPanel2.add(jLabel11, null);
        jPanel2.add(error_estandar, null);
        jPanel2.add(jLabel13, null);
        jPanel2.add(cociente_vaiacion, null);
        panel_intervalos.add(intervalo_de_confianza_menor, null);
        panel_intervalos.add(intervalo_de_confianza_mayor, null);

    }


///////////SIMPLES METODOS GET SET ///////////////////////////////////////////////////7
    public String getMediaAritmetica() {
        return media.getText();
    }

    public String getMediana() {
        return mediana.getText();
    }

    public String getVarianza() {
        return varianza.getText();
    }

    public String getDesviacionTipica() {
        return desviacion_tipica.getText();
    }

    public String getErrorEstandar() {
        return error_estandar.getText();
    }

    public String getCocienteDeVariacion() {
        return cociente_vaiacion.getText();
    }

    public String getIntervaloDeConfianzaMayor() {
        String full_text = intervalo_de_confianza_mayor.getText();
        full_text = full_text.substring(1, full_text.length() - 1);
        return full_text;
    }

    public String getIntervaloDeConfianzaMenor() {
        return intervalo_de_confianza_menor.getText();
    }

    public void setMediaAritmetica(float _media_aritmetica) {
        media.setText(Float.toString(_media_aritmetica));
    }

    public void setMediana(float _mediana) {
        mediana.setText(Float.toString(_mediana));
    }

    public void setVarianza(float _varianza) {
        varianza.setText(Float.toString(_varianza));
    }

    public void setDesviacion_tipica(float _desviacion_tipica) {
        desviacion_tipica.setText(Float.toString(_desviacion_tipica));
    }

    public void setErrorEstandar(float _error_estandar) {
        error_estandar.setText(Float.toString(_error_estandar));
    }

    public void setCocienteDeVariacion(float _cociente_de_variacion) {
        cociente_vaiacion.setText(Float.toString(_cociente_de_variacion));
    }

    public void setIntervaloDeConfianza(float[] _intervalo_de_confianza) {
        intervalo_de_confianza_menor.setText("[" + MyFloat.formateaNumero(_intervalo_de_confianza[1]) + ",");
        intervalo_de_confianza_mayor.setText(MyFloat.formateaNumero(_intervalo_de_confianza[0]) + "]");
    }

    public String getComentario() {
        return comentario.getText();
    }

    public String getFechaInicio() {
        return fechaInicio.getText();
    }

    public String getFechaFin() {
        return fecha_fin.getText();
    }

    public String getNombreSenhal() {
        return nombreSenal.getText();
    }

    public void setComentario(String _comentario) {
        comentario.setText(_comentario);
    }

    public void setFechaInicio(String _fecha_inicio) {
        fechaInicio.setText(_fecha_inicio);
    }

    public void setFechaFin(String _fecha_fin) {
        fecha_fin.setText(_fecha_fin);
    }

    public void setNombreSenhal(String nombreSenhal) {
        this.nombreSenal.setText(nombreSenhal);
    }

    public ResultadosEstadisticos getResultadosEstadisticos() {
        return resultados_estadisticos;
    }

    ///////////SIMPLES METODOS GET SET ///////////////////////////////////////////////////7

    /**
     * Indica si hay que invalidar los percentiles.
     * @return
     */
    boolean invalidarPercentiles() {
        return !checkbox_o_label_percentiles.isSelected();
    }
}
