package research.beats.hrv;

import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalMark;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.jsignalwb.utilities.TimePositionConverter;
import research.beats.anotaciones.LimitacionAnotacion;
import javax.swing.JFrame;
import net.javahispano.jsignalwb.plugins.defaults.DefaultIntervalAnnotation;
import java.awt.Color;
import java.io.PrintWriter;
import java.awt.*;


public class ApneaEpisodeGenerator extends AlgorithmAdapter {
    private static final Logger LOGGER = Logger.getLogger(ApneaEpisodeGenerator.class.getName());
    private static final String ERROR = "Error";

    static int tamanoVentana = 300;
    static float pesoApnea = 1;
    static float pesoHipoapnea = 0.5f;
    static float limitePorcentaje = 5;
    static float limitePorcentaje2 = 20;
    static StringBuilder stringBuilder;

    //private JFileChooser jf = new JFileChooser();
//    private float[] pesos;


    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {
        Signal flujo = sm.getSignal("Flujo");
        JSWBManager.getSignalManager().removeAllAnnotations();
        if (flujo == null) {
            advertirSenalNoEncontrada();
            return;
        }

        // TODO se modifica un valor estático al instanciar un objeto
        pr(flujo);
        float tmp=  pesoHipoapnea;
        pesoHipoapnea = 1;
        pr(flujo);
        pesoHipoapnea = tmp;
    }

    private void pr(Signal flujo) throws RuntimeException {
        TreeSet<DefaultIntervalMark> apneaTree = getMarksAsTree(flujo);
        float[] pesos = new float[flujo.getValues().length];
        int procesadas = 0;
        for (DefaultIntervalMark posibleLimitacion : apneaTree) {
            long inicioLong = posibleLimitacion.getMarkTime();
            long finLong = posibleLimitacion.getEndTime();
            int inicio = TimePositionConverter.timeToPosition(inicioLong, flujo);
            int fin = TimePositionConverter.timeToPosition(finLong, flujo);
            float valor = 0;
            if (posibleLimitacion instanceof LimitacionAnotacion) {
                LimitacionAnotacion apneaOHipoapnea = (LimitacionAnotacion) posibleLimitacion;
                if (apneaOHipoapnea.getTipo() == LimitacionAnotacion.APNEA) {
                    valor = pesoApnea;
                    procesadas++;
                } else if (apneaOHipoapnea.getTipo() == LimitacionAnotacion.HIPOAPNEA) {
                    valor = pesoHipoapnea;
                    procesadas++;
                }
            }
            for (int i = inicio; i <= fin; i++) {
                pesos[i] = valor;
            }
        }
        if (procesadas != apneaTree.size()) {
            advertirMarcaNoEsperada();
        }
        if ( pesoHipoapnea != 1) {
            clasificarIntervalos(flujo, pesos);
        }

        guardarArchivo();
    }

    private static void clasificarIntervalos(Signal flujo, float[] pesos) {
        stringBuilder = new StringBuilder();
        float fs = flujo.getSRate();
        int ventana = (int) (fs * tamanoVentana);
        for (int i = 0; i < pesos.length - ventana; i = i + ventana) {
            float acumulado = 0;
            for (int j = i; j < i + ventana; j++) {
                acumulado += pesos[j];
            }
            float porcentaje = 100 * acumulado / ventana;

            DefaultIntervalAnnotation defaultIntervalAnnotation = new DefaultIntervalAnnotation();
            defaultIntervalAnnotation.setAnnotationTime(TimePositionConverter.positionToTime(i, flujo));
            defaultIntervalAnnotation.setEndTime(TimePositionConverter.positionToTime(i + ventana, flujo));
            defaultIntervalAnnotation.setHeight(25);
            defaultIntervalAnnotation.setCategory("Apnea");
            String texto;

            if (porcentaje <= limitePorcentaje) {
                texto = (int) (i / fs) + ";" + tamanoVentana + ";" + "NOR;"+ porcentaje+ "\n";
                defaultIntervalAnnotation.setColor(Color.blue);
                defaultIntervalAnnotation.setFontColor(Color.blue);
                defaultIntervalAnnotation.setTitle("Normal: " + (int) (porcentaje) + "%");
            } else if (porcentaje <= limitePorcentaje2){
                texto = (int) (i / fs) + ";" + tamanoVentana + ";" + "NOR;"+ porcentaje+ "\n";
                defaultIntervalAnnotation.setColor(Color.GREEN);
                defaultIntervalAnnotation.setFontColor(Color.GREEN);
                defaultIntervalAnnotation.setTitle("Bordeline: " + (int) (porcentaje) + "%");
            }else {
                texto = (int) (i / fs) + ";" + tamanoVentana + ";" + "HYP;"+ porcentaje+ "\n";
                defaultIntervalAnnotation.setColor(Color.red);
                defaultIntervalAnnotation.setFontColor(Color.red);
                defaultIntervalAnnotation.setTitle("Apnea: " + (int) (porcentaje) + "%");
            }

            LOGGER.info(texto);
            stringBuilder.append(texto);
            JSWBManager.getSignalManager().addAnnotation(defaultIntervalAnnotation);
            JSWBManager.getJSignalMonitor().repaintAll();
        }
    }

    private void guardarArchivo() throws HeadlessException {
     String nombre = JSWBManager.getIOManager().getCurrentFileName().getName();
     nombre = "C:/hrv/" + pesoHipoapnea + "/"+ nombre;
            PrintWriter p = null;
            try {
                p = new PrintWriter(nombre+"_"+pesoApnea +"_"+ pesoHipoapnea+".episodios");
                p.write(stringBuilder.toString());
                p.close();
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                advertirProblemaEscribiendo();
            } finally {
               if(p != null){
                  p.close();
               }
            }

    }

    private TreeSet<DefaultIntervalMark> getMarksAsTree(Signal sato2) {
        List<MarkPlugin> listMarkPlugins = sato2.getAllMarks();
        TreeSet<DefaultIntervalMark> apneaTree = new TreeSet<DefaultIntervalMark>();
        for (Object elem : listMarkPlugins) {
            if (elem instanceof DefaultIntervalMark) {
                apneaTree.add((DefaultIntervalMark) elem);
            } else {
                advertirMarcaNoEsperada();
            }
        }
        assert (listMarkPlugins.size() == apneaTree.size());
        return apneaTree;
    }


    private void advertirMarcaNoEsperada() {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                      "Hay al menos una marca que no es una manera o hipoapnea",
                                      ERROR, JOptionPane.OK_OPTION);
    }

    private void advertirSenalNoEncontrada() {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                      "No se encontro una señal con nombre \"Flujo\"",
                                      ERROR, JOptionPane.OK_OPTION);
    }

    private void advertirProblemaEscribiendo() {
        JOptionPane.showMessageDialog(JSWBManager.getParentWindow(),
                                      "No se pudo escribir el fichero",
                                      ERROR, JOptionPane.OK_OPTION);
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return true;
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        DialogApneaEpisodeGenerator dialog = new DialogApneaEpisodeGenerator(
                (JFrame) JSWBManager.getParentWindow(), "Configuracion", true);
        dialog.setLocationRelativeTo(JSWBManager.getParentWindow());
        dialog.setTamanoVentana(tamanoVentana);
        dialog.setPesoApnea(pesoApnea);
        dialog.setPesoHipoapnea(pesoHipoapnea);
        dialog.setLimitePorcentaje(limitePorcentaje);
        dialog.setVisible(true);
        tamanoVentana = dialog.getTamanoVentana();
        pesoApnea = dialog.getPesoApnea();
        pesoHipoapnea = dialog.getPesoHipoapnea();
        limitePorcentaje = dialog.getLimitePorcentaje();
        LOGGER.log(Level.INFO, "Tamaño ventana, pesoApnea, pesoHiponea, limitePorcentaje: {0}, {1}, {2}, {3}",
              new String[]{ Integer.toString(tamanoVentana), Float.toString(pesoApnea),
                    Float.toString(pesoHipoapnea), Float.toString(limitePorcentaje)});
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return true;
    }

    @Override
    public void launchExecutionGUI(JSWBManager jswbManager) {
        this.runAlgorithm(JSWBManager.getSignalManager(), null, null);
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return (gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR);
    }

    @Override
    public Icon getIcon() {
        return super.generateImage("HR");
    }

    @Override
    public String getName() {
        return "Generador de episodios de apnea";
    }

}
