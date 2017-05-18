package net.javahispano.plugins.basicstats;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import net.javahispano.plugins.basicstats.ui.NewStatisticsDialog;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BasicStatisticsPlugin extends AlgorithmAdapter implements Plugin {

    private static final Logger LOGGER = Logger.getLogger(BasicStatisticsPlugin.class.getName());
    public static final String EL_PERCENTIL = "Percentil";
    public static final String ATTR_PERCENTIL = "Percentil";

    /*
     * Atributos
     */

    private Map<String, ResultadosEstadisticos> statisticsCollection;
    private Estadistico statistics = null;
    /**
     * I have a String that consists of XML data & tags. I need to convert the String to a JDOM Document Object. Does anyone know how?
     I have tried the following with no success:
     public void setXMLResultSetString(String pResSetString)
     {
     try
     {
     System.out.println(pResSetString);
     SAXBuilder db = new SAXBuilder();
     StringReader sr = new StringReader (pResSetString);
     Document doc = db.build((Reader)sr);
     System.out.println(doc);

     */
    public BasicStatisticsPlugin() {
        statisticsCollection = new HashMap<String, ResultadosEstadisticos>();
    }

    @Override
    public String getDescription() {
        return "Calcula un conjunto de estadisticos basicos";
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(net.javahispano.plugins.basicstats.
                             BasicStatisticsPlugin.class.getResource(
                                     "estadisticos.jpg"));
    }

    @Override
    public String getName() {
        return "Estadistico Basico";
    }

    @Override
    public String getPluginVersion() {
        return "0.5";
    }

    @Override
    public String getShortDescription() {
        return "Calcula un conjunto de estadisticos basicos de un modo aproximado.";
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 1;
    }

    /*
     * @TODO averiguar por que no se puede usar un cuadro de dialogo modal
     */
    @Override
    public void runAlgorithm(SignalManager sm,
                             List<SignalIntervalProperties> signals,
                             AlgorithmRunner ar) {
        //Signal signal = sm.getSignal((String) signals.nextElement());
        Signal signal = signals.get(0).getSignal();
        SignalIntervalProperties in = signals.get(0);
        int ini = in.getFirstArrayPosition();
        int fi = in.getLastArrayPosition();
        float[] data = signal.getValues();
        float [] datos= new float[fi-ini];
        for (int i = ini; i < fi; i++) {
            datos [i-ini]=data [i];
        }
        data= datos;
        long principio = signal.getStart();
        String inicio = TimeRepresentation.timeToString(principio, true, true, false);
        String fin = TimeRepresentation.timeToString(principio +
                signal.getValues().length);
        statistics = new Estadistico(data, null, signal.getName(),
                                     inicio, fin);
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return false;
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        // Vacio
    }

    @Override
    public String getDataToSave() {
        XMLOutputter r = new XMLOutputter();
        return r.outputString(this.guardaEstadisticos());
    }

    @Override
    public void setSavedData(String data) {
        SAXBuilder db = new SAXBuilder();
        StringReader sr = new StringReader(data);
        Document doc;
        try {
            doc = db.build((Reader) sr);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Ha sucedido un error al recuperar la informacion de sesiones pasadas en el plugin estadistico basico",
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (JDOMException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                    "Ha sucedido un error al recuperar la informacion de sesiones pasadas en el plugin estadistico basico",
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<ResultadosEstadisticos> estadisticos = recuperaEstadisticos(doc.getRootElement());
        Iterator<ResultadosEstadisticos> it = estadisticos.iterator();
        while (it.hasNext()) {
            ResultadosEstadisticos r = it.next();
            statisticsCollection.put(r.getKey() + "a", r);
        }
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    /**
     * Almacena los estadisticos, si los hay.
     * @param root
     */
    private Element guardaEstadisticos() {
        Element root = new Element("Estadisticos");
        //Si no hay nada => no se hace nada
        if (statisticsCollection == null) {
            return root;
        }

        Iterator<ResultadosEstadisticos> it = statisticsCollection.values().iterator();
        while (it.hasNext()) {
            ResultadosEstadisticos estadistico = it.next();
            //Sacamos la informaicion del estadistico
            float media = estadistico.getMediaAritmetica();
            float mediana = estadistico.getMediana();
            float varianza = estadistico.getVarianza();
            float desviacion_tipica = estadistico.getDesviacionTipica();
            float error_estandar = estadistico.getErrorEstandar();
            float[] intervalo_de_confianza = estadistico.
                                             getIntervaloDeConfianza();
            float cociente_de_variacion = estadistico.getCocienteDeVariacion();
            String fecha_inicio = estadistico.getFechaInicio();
            String fecha_fin = estadistico.getFechaFin();
            Map<String, String> percentiles_map = estadistico.getPercentiles();
            String comentario = estadistico.getComentario();
            //Creamos el elemeto estaditico y le ponemos sus atributos
            Element estadistico_xml = new Element("Estadistico");
            estadistico_xml.setAttribute("Nombre", estadistico.getNombreSenhal());
            estadistico_xml.setAttribute("Media", Float.toString(media));
            estadistico_xml.setAttribute("Mediana", Float.toString(mediana));
            estadistico_xml.setAttribute("Varianza", Float.toString(varianza));
            estadistico_xml.setAttribute("DesviacionTipica",
                                         Float.toString(desviacion_tipica));
            estadistico_xml.setAttribute("ErrorEstandar",
                                         Float.toString(error_estandar));
            estadistico_xml.setAttribute("IntervaloDeConfianzaInicio",
                                         Float.
                                         toString(intervalo_de_confianza[0]));
            estadistico_xml.setAttribute("IntervaloDeConfianzaFin",
                                         Float.
                                         toString(intervalo_de_confianza[1]));
            estadistico_xml.setAttribute("CocienteDeVariacion",
                                         Float.toString(cociente_de_variacion));
            estadistico_xml.setAttribute("FechaInicio", fecha_inicio);
            estadistico_xml.setAttribute("FechaFin", fecha_fin);

            //CReamos los elementos Percentil, que seran hijos del elemento estadistico
            if (percentiles_map != null) {
                Iterator<String> it2 = percentiles_map.keySet().iterator();
                while (it2.hasNext()) {
                    String percentil_str = it2.next();
                    String valor_percentil = percentiles_map.get(
                            percentil_str);
                    Element percentil_xml = new Element(EL_PERCENTIL);
                    percentil_xml.setAttribute(ATTR_PERCENTIL, percentil_str);
                    percentil_xml.setAttribute("ValorPercentil",
                                               valor_percentil);
                    estadistico_xml.addContent(percentil_xml);
                }
            }
            //Por ultimo creamos y anhadimos el elemento comentaio
            Element comentario_xml = new Element("Comentario");
            comentario_xml.setText(comentario);
            estadistico_xml.addContent(comentario_xml);

            //Anhadimos el elemento estadistico creado a la raiz
            root.addContent(estadistico_xml);
        }
        return root;
    }

    private List<ResultadosEstadisticos> recuperaEstadisticos(Element root) {
        LinkedList<ResultadosEstadisticos> resultado = new LinkedList<ResultadosEstadisticos>();
        @SuppressWarnings("unchecked")
        Iterator<Element> it = root.getChildren("Estadistico").iterator();
        while (it.hasNext()) {
            try {
                Element estadisticoXml = it.next();
                float media = estadisticoXml.getAttribute("Media").getFloatValue();
                float mediana = estadisticoXml.getAttribute("Mediana").getFloatValue();
                float varianza = estadisticoXml.getAttribute("Varianza").getFloatValue();
                float desviacion_tipica = estadisticoXml.getAttribute("DesviacionTipica").getFloatValue();
                float error_estandar = estadisticoXml.getAttribute("ErrorEstandar").getFloatValue();
                float cociente_de_variacion = estadisticoXml.getAttribute("CocienteDeVariacion").getFloatValue();
                String fecha_inicio = estadisticoXml.getAttribute("FechaInicio").getValue();
                String fecha_fin = estadisticoXml.getAttribute("FechaFin").getValue();
                String nombre = estadisticoXml.getAttribute("Nombre").getValue();
                float[] intervalo_de_confianza = new float[2];
                intervalo_de_confianza[0] = estadisticoXml.getAttribute("IntervaloDeConfianzaInicio").getFloatValue();
                intervalo_de_confianza[1] = estadisticoXml.getAttribute("IntervaloDeConfianzaFin").getFloatValue();
                String comentario = estadisticoXml.getChild("Comentario").getText();

                //Cojemos la lista de percentiles
                @SuppressWarnings("unchecked")
                List<Element> list_percentiles = estadisticoXml.getChildren("Percentiles");
                Iterator<Element> it2 = list_percentiles.iterator();
                int num_percentiles = list_percentiles.size();
                int[] percentiles_float = new int[num_percentiles];
                float[] valores_percentiles = new float[num_percentiles];
                int cuantos_van = 0;
                while (it2.hasNext()) {
                    Element percentil_xml = it2.next();
                    percentiles_float[cuantos_van] = percentil_xml.getAttribute(ATTR_PERCENTIL).getIntValue();
                    valores_percentiles[cuantos_van] = percentil_xml.getAttribute("ValorPercentil").getFloatValue();
                    cuantos_van++;
                }
                ResultadosEstadisticos estadistico = new ResultadosEstadisticos(media, mediana, varianza,
                        desviacion_tipica, error_estandar, cociente_de_variacion, intervalo_de_confianza,
                        percentiles_float,
                        valores_percentiles, fecha_inicio, fecha_fin, nombre);
                estadistico.setComentario(comentario);
                resultado.add(estadistico);
            } catch (NotPercentilException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            } catch (DataConversionException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return resultado;
    }

    @Override
    public boolean hasOwnExecutionGUI() {
        return false;
    }

    @Override
    public boolean hasResultsGUI() {
        return true;
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }

    @Override
    public void launchResultsGUI(JSWBManager jswbManager) {
        NewStatisticsDialog d = new NewStatisticsDialog(this,
                statistics.getResultados(), JSWBManager.getParentWindow(), true);
        d.setVisible(true);
    }

    public List<ResultadosEstadisticos> getStatisticsCollection() {
        return new ArrayList<ResultadosEstadisticos>(this.statisticsCollection.values());
    }

    public void addStatistics(ResultadosEstadisticos c) {
        this.statisticsCollection.put(c.getKey(), c);
    }

    public void eraseAllStatistics() {
        this.statisticsCollection.clear();
    }

    public void eraseStatistics(String key) {
        this.statisticsCollection.remove(key);
    }

}
