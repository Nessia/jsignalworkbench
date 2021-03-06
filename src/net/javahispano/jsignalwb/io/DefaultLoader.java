package net.javahispano.jsignalwb.io;

import java.awt.Color;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.*;

import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.framework.ExceptionsCollector;
import net.javahispano.jsignalwb.plugins.*;
import net.javahispano.jsignalwb.plugins.framework.PluginManager;

import org.jdom.*;
import org.jdom.input.SAXBuilder;

/**
 * {@link Saver} que utiliza JSignalWorkbench por defecto. Implementa el patron
 * template method permitiendo que el usuario defina solo un formato diferente
 * para el archivo que contiene las senhales muestreadas y conserve el formato
 * XML de JSignalWorkbench donde se almacena la configuracion del entorno,
 * anotaciones, informacion de los plugin y demas; o viceversa.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public class DefaultLoader extends LoaderAdapter {
     public static final String ATTR_FREQUENCY = "Frecuency";
     public static final String ATTR_VERSION = "Version";

    private final List<String> extensions; //Almacena las extensiones en que es capaz de almacenar senales el plugin
    private float frecuency;
    private long scrollValue;
    private String leftPanelConfig;

    /** crea una nueva instancia del plugin */

    public DefaultLoader() {
        extensions = new ArrayList<String>();
        extensions.add("jsw");
        frecuency = -1;
        scrollValue = -1;
    }

    @Override
    public String getName() {
        return "defaultLoader";
    }

    @Override
    public String getShortDescription() {
        return "Proyectos JSignalWorkBench";
    }

    @Override
    public String getDescription() {
        return "Loader destinado a cargar todos aquellos proyectos que hayan sido " +
                "guardados previamente mediante el plugin por defecto.\nCargara carpetas " +
                "cuyo contenido ha de ser al menos un fichero con extension .jsw con las " +
                "propiedades del proyecto y un .txt con los datos de las senhales a cargar.\n" +
                "Adicionalmente puede contener fichero generados por los plugins.";
    }

    /**
     * Este metodo esta implementado siguiendo el patron template. Los otros
     * metodos involucrados en el patron son {@link loadXML(File
     * f,ArrayList<Signal> signals,SignalManager sm,PluginManager pm)} y {@link
     * loadValues(File f, ArrayList<Signal> signals)}. El primero se encarga de
     * cargar la informacion relativa a la configuracion del entorno, la
     * informacion de los plugins y los nombres, frecuencias de muestreo,
     * unidades, etc. de las senhales. El segundo es el que se encarga de cargar
     * los valores (arrays de datos) de las senhales.
     *
     * @param fichero File
     * @param sm {@link SignalManager}.
     * @param pm {@link PluginManager}.
     * @throws Exception
     * @return true si la carga se realiza correctamente, fase en caso
     *   contrario.
     */
    @Override
    public boolean load(File file) throws Exception {
         File fichero = file;
        //PluginManager pm=jswbManager.getPluginManager();
        SignalManager sm = JSWBManager.getSignalManager();
        if (fichero != null && fichero.getName().toLowerCase().endsWith(".jsw")) {
            fichero = fichero.getParentFile();
        }
        FileFilter ff = new FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jsw");
            }
        };
        if (fichero != null && fichero.isDirectory() && fichero.listFiles(ff) != null && fichero.listFiles(ff).length > 0) {
            fichero = fichero.listFiles(ff)[0];
            ArrayList<Signal> signals = new ArrayList<Signal>();
            if (!loadValues(loadXml(fichero, signals), signals)) {
                return false;
            }
            boolean flag = true;
            for (Signal s : signals) {
                if (!sm.addSignal(s)) {
                    flag = false;
                }
            }
            if (leftPanelConfig != null) {
                // @TODO bug ahora este codigo se ejecuta fuera del EDT

                JSWBManager.getJSWBManagerInstance().setJSMLeftPanelConfigurationString(leftPanelConfig);
            }
            JSWBManager.getJSWBManagerInstance().setJSMFrecuency(frecuency);
            //genera eventos que pueden afectar a componentes Swing
            JSWBManager.getJSWBManagerInstance().setJSMScrollValue(scrollValue);
            //refresca JSignalMonitor
            JSWBManager.getJSWBManagerInstance().refreshJSM(false);

            //JSWBManager.getJSWBManagerInstance().getJSM().repaintAll();
            return flag;
        }
        return false;
    }

    @Override
    public List<String> getAvalaibleExtensions() {
        return extensions;
    }

    /**
     *  <p>Objetivo --> Cargar todas las propiedades de cada una de las senales que
     * se desean obtener, salvo los valores en si mismo de la senal. Tambien
     * indicara la ubicacion del archivo que almacena los valores.</p>
     *
     * <p>Precondiciones --> El argumento f debe corresponder a un fichero en
     * formato XML especifico de la aplicacion. </p>
     *
     * <p> Postcondiciones --> El metodo
     * almacenara en el array signals proporcionado las senales con sus
     * caracteristicas, ademas proporcionara una variable file que indica el
     * fichero en el cual estan contenidos los valores de las senales. </p>
     *
     * @param f File
     * @param signals ArrayList
     * @param jswbManager {@link JSWBManager}
     * @throws Exception
     * @return archivo que contiene los datos.
     */
   @SuppressWarnings("unchecked")
   protected File loadXml(File f, ArrayList<Signal> signals) throws Exception {
        try {
            ExceptionsCollector ec = new ExceptionsCollector(JSWBManager.getParentWindow());
            PluginManager pm = JSWBManager.getPluginManager();
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(f);
            Element root = doc.getRootElement();

            File fileValues = new File(f.getParent(), root.getAttribute("path").getValue());
            List<Element> signalsXML = (List<Element>) root.getChildren("Signal");

            Iterator<Element> i = signalsXML.iterator();
            while (i.hasNext()) {
                Element signal = i.next();
                Signal s;
                if (Boolean.valueOf(signal.getAttribute("HasPosibility").getValue())) {
                    s = new Signal(signal.getAttribute("Name").getValue(),
                                   new float[signal.getAttribute("Size").getIntValue()],
                                   signal.getAttribute(ATTR_FREQUENCY).getFloatValue(),
                                   signal.getAttribute("Start").getLongValue(),
                                   signal.getAttribute("Magnitude").getValue(),
                                   new short[signal.getAttribute("Size").getIntValue()]);
                    //s.setHasColors(true);
                } else {
                    s = new Signal(signal.getAttribute("Name").getValue(),
                                   new float[signal.getAttribute("Size").getIntValue()],
                                   signal.getAttribute(ATTR_FREQUENCY).getFloatValue(),
                                   signal.getAttribute("Start").getLongValue(),
                                   signal.getAttribute("Magnitude").getValue());
                    //s.setHasColors(false);
                }
                Element grid = signal.getChild("Grid");
                //tengo archivos y los cuales no se guardaba la informacion del club y con la senhal
                //probablemente podamos cambiar esto en el futuro pero ahora necesita abrir los puntos suspensivos
                if (grid != null) {
                    grid = grid.getChild("Plugin");
                    String key = grid.getAttribute("Key").getValue();
                    if (pm.isPluginRegistered(key)) {
                        GridPlugin gp = pm.createGridPlugin(key.substring(key.indexOf(':') + 1));
                        gp.setSignal(s);
                        if (gp.getPluginVersion().equals(grid.getAttribute(ATTR_VERSION).getValue())) {
                            if (gp.hasDataToSave()) {
                                gp.setSavedData(grid.getText());
                            }
                            s.setGrid(gp);

                        } else {
                            ec.addException(new Exception("The version of the plugin " + gp.getName() +
                                    " is not the same"));
                        }
                    } else {
                        ec.addException(new Exception("The grid plugin " + key + " is not registered"));
                    }
                }

                Iterator<Element> propertiesList = ((List<Element>) signal.getChildren("ChannelProperties")).iterator();
                Element properties;
                if (propertiesList.hasNext()) {
                    properties = propertiesList.next();
                    /*s.getProperties().setZoom(Float.valueOf(properties.getAttribute(
                            "Zoom").getValue()));*/
                    s.getProperties().setDataColor(new Color(Integer.valueOf(
                            properties.getAttribute("DataColor").getValue()), true));
                    /*s.getProperties().setAbscissaOffset(Float.valueOf(properties.
                            getAttribute("AbscissaOffset").getValue()));*/
                    s.getProperties().setVisible(Boolean.valueOf(properties.
                            getAttribute("Visible").getValue()));

                    //s.getProperties().setAbscissaValue(Float.valueOf(properties.
                    //      getAttribute("AbscissaValue").getValue()));
//                    float abs=Float.valueOf(properties.
//                            getAttribute("AbscissaValue").getValue());
                    float min = properties.getAttribute("MinValue").getFloatValue();
                    float max = properties.getAttribute("MaxValue").getFloatValue();
                    s.getProperties().setVisibleRange(min, max);
                }
                Iterator<Element> marksList = ((List<Element>)signal.getChildren("Mark")).iterator();
                Element mark;
                while (marksList.hasNext()) {
                    mark = marksList.next();
                    String pluginName = mark.getAttribute("Name").getValue();
                    if (pm.isPluginRegistered("mark", pluginName)) {
                        MarkPlugin mp = pm.createMarkPlugin(pluginName);
                        if (mp.getPluginVersion().equals(mark.getAttribute(ATTR_VERSION).getValue())) {
                            mp.setMarkTime(Long.parseLong(mark.getAttribute("MarkTime").getValue()));
                            if (Boolean.parseBoolean(mark.getAttribute("Interval").getValue())) {
                                mp.setEndTime(Long.parseLong(mark.getAttribute("EndTime").getValue()));
                            }
                            if (mp.hasDataToSave()) {
                                mp.setSavedData(mark.getText());
                            }
                            s.addMark(mp);
                            /*else
                                ec.addException(new Exception("Imposible to load data in plugin: "+p.getName()));*/
                        } else {
                            ec.addException(new Exception("The version of the plugin " + mp.getName() +
                                    " is not the same"));
                        }
                    } else {
                        ec.addException(new Exception("The mark plugin " + pluginName + " is not registered"));
                    }
                }
                Iterator<Element> propertyList = ((List<Element>) signal.getChildren("Property")).iterator();
                Element property;
                while (propertyList.hasNext()) {
                    property = propertyList.next();
                    String propertyName = property.getAttribute("Name").getValue();
                    String beanString = property.getText();
                    ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(beanString.getBytes());
                    XMLDecoder decoder = new XMLDecoder(byteArrayIS);
                    Object obj = decoder.readObject();
                    decoder.close();
                    s.setProperty(propertyName, obj);
                }
                signals.add(s);
            }
            Element annotations = root.getChild("Annotations");
            if (annotations != null) {
                Iterator<Element> annotationsList = ((List<Element>) annotations.getChildren("Annotation")).iterator();
                Element annotation;
                while (annotationsList.hasNext()) {
                    annotation = annotationsList.next();
                    String pluginName = annotation.getAttribute("Name").getValue();
                    if (pm.isPluginRegistered("annotation", pluginName)) {
                        AnnotationPlugin mp = pm.createAnnotationPlugin(pluginName);
                        if (mp.getPluginVersion().equals(annotation.getAttribute(ATTR_VERSION).getValue())) {
                            mp.setAnnotationTime(Long.parseLong(annotation.getAttribute("MarkTime").getValue()));
                            //mp.setJSWBManager(jswbManager);
                            if (Boolean.parseBoolean(annotation.getAttribute("Interval").getValue())) {
                                mp.setEndTime(Long.parseLong(annotation.getAttribute("EndTime").getValue()));
                            }
                            if (mp.hasDataToSave()) {
                                mp.setSavedData(annotation.getText());
                            }
                            JSWBManager.getSignalManager().addAnnotation(mp);
                            /*else
                                ec.addException(new Exception("Imposible to load data in plugin: "+p.getName()));*/
                        } else {
                            ec.addException(new Exception("The version of the plugin " + mp.getName() +
                                    " is not the same"));
                        }
                    } else {
                        ec.addException(new Exception("The annotation plugin " + pluginName + " is not registered"));
                    }
                }
            }
            Iterator<Element> jsmList = ((List<Element>) root.getChildren("JSignalMonitor")).iterator();
            if (jsmList.hasNext()) {
                Element jsm = jsmList.next();
                frecuency = Float.valueOf(jsm.getAttribute(ATTR_FREQUENCY).getValue());
                scrollValue = Long.valueOf(jsm.getAttribute("ScrollPosition").getValue());
//                leftPanelConfig=jsm.getAttribute("LeftPanelConfig").getValue();



                //JSWBManager.getJSWBManagerInstance().getJSM().repaintAll();
            }

            Iterator<Element> plugins = ((List<Element>) root.getChildren("Plugin")).iterator();
            while (plugins.hasNext()) {
                Element plugin = plugins.next();
                String pluginKey = plugin.getAttribute("Key").getValue();
                if (pm.isPluginRegistered(pluginKey)) {
                    Plugin p = pm.getPlugin(pluginKey);
                    if (p.getPluginVersion().equals(plugin.getAttribute(ATTR_VERSION).getValue())) {
                        if (p.hasDataToSave()) {
                            p.setSavedData(plugin.getText());
                        }
                        if (p.createFile()) {
                            File pf = JSWBManager.getIOManager().createNameForPlugin(p);
                            if (!pf.exists()) {
                                pf.createNewFile();
                            }
                            p.setFile(pf);
                        }
                    } else {
                        ec.addException(new Exception("The version of the plugin " + p.getName() + " is not the same"));
                    }
                } else {
                    ec.addException(new Exception("The plugin " + pluginKey + " is not registered"));
                }
            }
          //  ec.showExceptions("The next errors founded loading the plugins data");
            return fileValues;
        } catch (NumberFormatException ex) {
            throw new Exception("Error loading xml(invalid number format): " + ex.getMessage(), ex);
        } catch (JDOMException ex) {
            throw new Exception("Error loading xml(xml malformed): " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new Exception("Error loading xml: " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Objetivo --> Cargar los valores apropiados para cada una de las senales
     * desde fichero</p>
     *
     * <p> Precondiciones --> La variable signals debe contener el
     * numero de senales que se esperan encontrar en el fichero, inicializadas y
     * con todas sus propiedades ya cargadas a excepcion de los valores de la
     * senal(Aunque debera indicar cuantos valores va a encontrar. La variable f
     * debera indicar el fichero en formato de texto especifico del plugin que
     * contenga los valores de las senales.</p>
     *
     * <p> Postcondiciones--> Las senales
     * quedan almacenadas en la variable signals proporcionada. El metodo
     * devolvera true en caso de finalizar correctamente y false en caso de
     * encontrar algun problema</p>
     *
     * @param f File
     * @param signals ArrayList
     * @throws Exception
     * @return true si la carrera se realiza correctamente, fase en caso contrario.
     */
    protected boolean loadValues(File f, ArrayList<Signal> signals) throws Exception {
        try {

            //int numberOfSignals = signals.size();
            //String file = new String();
            FileReader fr = new FileReader(f);
            BufferedReader input = new BufferedReader(fr);
            try {
                String line;
                StringTokenizer st;
                int index1 = 0;
                //int signal = 0;
                int pos = 0;
                String temp = "";
                while ((line = input.readLine()) != null) {
                    index1 = 0;
                    st = new StringTokenizer(line, "\t", true);
                    boolean flag = false;
                    while (st.hasMoreTokens()) {
                        temp = st.nextToken();
                        /*if(temp.equals("\t")){
                            index1++;
                                             }
                                             else
                         signals.get(index1).getValues()[pos]=Float.parseFloat(temp);*/
                        if (!"\t".equals(temp)) {
                            /**
                             * @todo
                             * java.lang.IndexOutOfBoundsException: Index: 14, Size: 14
                                     at java.util.ArrayList.RangeCheck(ArrayList.java:547)
                                     at java.util.ArrayList.get(ArrayList.java:322)
                                     at net.javahispano.jsignalwb.io.DefaultLoader.loadValues(DefaultLoader.java:413)
                                     at net.javahispano.jsignalwb.io.DefaultLoader.load(DefaultLoader.java:111)
                             at net.javahispano.jsignalwb.plugins.LoaderRunner.doInBackground(LoaderRunner.java:28)
                             at net.javahispano.jsignalwb.plugins.LoaderRunner.doInBackground(LoaderRunner.java:18)
                                     at javax.swing.SwingWorker$1.call(SwingWorker.java:279)primer exception

                                     at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:303)
                                     at java.util.concurrent.FutureTask.run(FutureTask.java:138)
                                     at javax.swing.SwingWorker.run(SwingWorker.java:319)
                             at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:885)
                                     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:907)
                                     at java.lang.Thread.run(Thread.java:619)

                             */
                            while (signals.get(index1).getValues().length <= pos) {
                                index1++;
                            }
                            if (!flag) {
                                signals.get(index1).getValues()[pos] = Float.
                                        parseFloat(temp);
                                if (signals.get(index1).hasEmphasisLevel()) {
                                    flag = true;
                                } else {
                                    index1++;
                                }
                            } else {
                                signals.get(index1).getEmphasisLevel()[pos] = Short.
                                        parseShort(temp);
                                flag = false;
                                index1++;
                            }
                        }
                    }
                    pos++;
                }
            } finally {
                input.close();
                fr.close();
            }
            return true;

            /*for(int index3=0;index3<numberOfSignals;index3++) {
                signals.get(index3).setValues(values.get(index3));

                     }*/

        } catch (NumberFormatException ex) {
            throw new Exception("Error loading values(invalid number format): "
                                + ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            throw new Exception("Error loading values(file not found): "
                                + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new Exception("Error loading values: " + ex.getMessage(), ex);
        }
    }


}
