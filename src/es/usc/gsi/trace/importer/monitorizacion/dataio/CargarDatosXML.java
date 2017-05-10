package es.usc.gsi.trace.importer.monitorizacion.dataio;


import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.trace.importer.estadisticos.*;
import es.usc.gsi.trace.importer.jsignalmonold.SamplesToDate;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.*;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Attribute;
import es.usc.gsi.trace.importer.monitorizacion.data.AlmacenDatosFloat;
import es.usc.gsi.trace.importer.monitorizacion.data.GestorDatos;
import es.usc.gsi.trace.importer.perfil.PTBMInterface;

import org.jdom.*;
import org.jdom.filter.ContentFilter;
import org.jdom.input.SAXBuilder;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class CargarDatosXML extends CargarDatos {

    private static final Logger LOGGER = Logger.getLogger(CargarDatosXML.class.getName());

    private static final String ATTR_MIN = "Minimo";
    private static final String ATTR_MAX = "Maximo";
    private static final String ATTR_TIEMPO_INICIO = "TiempoInicio";
    private static final String ATTR_TIEMPO_FIN = "TiempoFin";
    private static final String ATTR_TIPO_EVENTO = "TipoEvento";
    private static final String ATTR_OFFSET = "Offset";
    private static final String ATTR_NOMBRE_SENAL = "NombreSenal";
    private static final String ATTR_FECHA_BASE = "FechaBase";
    private static final String ATTR_TEXTO = "Texto";
    private static final String ATTR_COMENTARIO = "Comentario";
    private static final String ATTR_ATRIBUTO = "Atributo";
    private static final String EL_SENAL = "Senal";
    private static final String EL_ATRIBUTO = "Atributo";
//    private static final String ATTR_VALUE_TRUE = "true";

    private byte[] posGlobal;

    public CargarDatosXML(String archivo) throws JDOMException {
        super(archivo);
        cargaDatos();

    }

    @SuppressWarnings("unchecked")
    private void cargaDatos() throws JDOMException {
        //Generamos el path de este archivo, lo necesitaremos para mas tarde:
        String arcivo_monitorizacion_path = (new File(archivo)).getParent() +
                                            File.separator;
        //Cargamos el documento validando
        SAXBuilder bulder = new SAXBuilder(false /*true*/);
        Document documento = null;
        try {
            documento = bulder.build(archivo);
        } catch (IOException ex1) {
            LOGGER.log(Level.WARNING, ex1.getMessage(), ex1);
        } catch (JDOMException ex) {
            throw (ex);
        }
        //Obtenemos el elemento raiz, el PTBM, y cojemos sus atributos.
        Element root = documento.getRootElement();
        String fecha_base = root.getAttributeValue(ATTR_FECHA_BASE);
        String tiene_pos_glogal_string = root.getAttributeValue("TienePosAsociada");
        boolean tiene_pos_glogal = Boolean.parseBoolean(tiene_pos_glogal_string);
//        boolean tiene_pos_gloga = false;
//        if ("true".equals(tiene_pos_glogal_string)) {
//            tiene_pos_gloga = true;
//        }

        String fichero_PTBM = root.getChildText("PTBM");
        //Empleamos un filtro para deshacernos de los nodos de texto
        ContentFilter filtro = new ContentFilter(false);
        filtro.setElementVisible(true);
        List<Element> lista_senales = root.getChildren(EL_SENAL);
        Iterator<Element> it = lista_senales.iterator();
        //Defino las extructuras que voy a emplear para almacenar la informacion
        int num_senales = lista_senales.size();
        float[][] datos = new float[num_senales][];
        byte[][] pos = new byte[num_senales][];
        float[][] rango;
        if (tiene_pos_glogal) {
            rango = new float[num_senales + 1][2];
            rango[num_senales][0] = 0;
            rango[num_senales][1] = 100;
        } else {
            rango = new float[num_senales][2];
        }

        float[] fs = new float[num_senales];
        String[] nombre_senales = new String[num_senales];
        String[] leyenda_temporal = new String[num_senales];
        String[] leyendas = new String[num_senales];
        TreeSet<Mark>[] marcas = new TreeSet[num_senales];
        for (int i = 0; i < marcas.length; i++) {
            marcas[i] = new TreeSet<Mark>();
        }

        boolean[] tien_pos_asociada = new boolean[num_senales];
        String nombre_archivo_senal;

        //Primero cargaremos la configuracion de las senhales
        int cont_senal = -1;
        while (it.hasNext()) {
            cont_senal++;
            Element senal_xml = it.next();
            nombre_senales[cont_senal] = senal_xml.getAttribute(ATTR_NOMBRE_SENAL).getValue();
            leyenda_temporal[cont_senal] = senal_xml.getAttribute("LeyendaTemporal").getValue();
            leyendas[cont_senal] = senal_xml.getAttribute("Leyenda").getValue();

            try {
                rango[cont_senal][0] = senal_xml.getAttribute(ATTR_MIN).getFloatValue();
                rango[cont_senal][1] = senal_xml.getAttribute(ATTR_MAX).
                                       getFloatValue();
                fs[cont_senal] = senal_xml.getAttribute("Fs").getFloatValue();
            } catch (DataConversionException ex) {
                LOGGER.log(Level.WARNING, "Exception de conversion en JDOM sin procesar", ex);
            }
            tien_pos_asociada[cont_senal] = senal_xml.getAttribute("TienePosibilidadAsociada").getBooleanValue();
//            if (tien_pos_string.equals("true")) {
//                tien_pos_asociada[cont_senal] = true;
//            } else {
//                tien_pos_asociada[cont_senal] = false;
//            }
            //Ahora leemos las marcas de esta senhal
            List<Element> lista_marcas = senal_xml.getChildren("Marca");
            Iterator<Element> it2 = lista_marcas.iterator();
            while (it2.hasNext()) {
                Element marca_xml = it2.next();
                String texto_marca = marca_xml.getAttributeValue(ATTR_TEXTO);
                String comentaio_marca = marca_xml.getAttributeValue(ATTR_COMENTARIO);
                int timepo_marca = 0;
                try {
                    timepo_marca = marca_xml.getAttribute("InstanteInicio").getIntValue();
                } catch (DataConversionException ex) {
                   LOGGER.log(Level.SEVERE, "Excepcion de conversion de JDOM sin procesar", ex);
                }
                Mark marca = new Mark();
                marca.setTexto(texto_marca);
                marca.setComentario(comentaio_marca);
                marca.setTiempo(timepo_marca);
                marcas[cont_senal].add(marca);
            }
        }

        //Ahora cargamos los datos
        nombre_archivo_senal = root.getChildText("FicheroDatos");
        nombre_archivo_senal = arcivo_monitorizacion_path +
                               (new File(nombre_archivo_senal)).getName();
        cargaDatos(nombre_archivo_senal, num_senales, tien_pos_asociada, datos,
                   pos, tiene_pos_glogal);

        //Crgamos el PTBM. si no habia ptbm lo ponemos a  null
        PTBMInterface ptbm;
        if (fichero_PTBM != null) {
            //Para cargar empleo path relativo, aunque para almacenar no
            fichero_PTBM = (new File(fichero_PTBM)).getName();
            fichero_PTBM = arcivo_monitorizacion_path + fichero_PTBM;
            ptbm = PTBM2XML.getInstancia().cargaPTBM(fichero_PTBM);
        } else {
            ptbm = null;
        }
        //Ahora nos quedan las anotaciones
        TreeSet<Annotation> tree_set = new TreeSet<Annotation>();
        List<Element> lista_terapia_xml = root.getChildren("Terapia");
        LinkedList<Therapy> lista_terapia = genraAnotacionesTerapia(lista_terapia_xml);
        List<Element> lista_diagnostico_xml = root.getChildren("Diagnostico");
        LinkedList<Diagnostic> lista_diagnostico = genraAnotacionesDiagnostico(lista_diagnostico_xml);
        List<Element> lista_manifestacion_xml = root.getChildren("Manifestacion");
        LinkedList<Manifestacion> lista_manifestacion = genraAnotacionesManifestacion(lista_manifestacion_xml);
        tree_set.addAll(lista_diagnostico);
        tree_set.addAll(lista_manifestacion);
        tree_set.addAll(lista_terapia);

        GestorDatos.getInstancia().setAlmacen(almacen);
        almacen = new AlmacenDatosFloat(datos, pos, tree_set, marcas, ptbm);
        almacen.setAnotaciones(tree_set);
        almacen.setMarcas(marcas);
        almacen.setRango(rango);
        for (int i = 0; i < num_senales; i++) {
            almacen.setNombreSenal(i, nombre_senales[i]);
            almacen.setLeyenda(i, leyendas[i]);
            almacen.setFs(fs[i], i);
        }
        GestorDatos gestor_datos = GestorDatos.getInstancia();
        gestor_datos.setAlmacen(almacen);

        //Y  los estadisticos
        LinkedList<ResultadosEstadisticos> estadisticos_list = this.cargaEstadisticos(root);
        //Anhadimos al alamcen todos los estadisticos
        Iterator<ResultadosEstadisticos> it2 = estadisticos_list.iterator();
        while (it2.hasNext()) {
            ResultadosEstadisticos estadsitico = it2.next();
            gestor_datos.anadeEstadistico(estadsitico);
        }

        //Las correlaciones
        LinkedList<ResultadoCorrelacion> correlaciones_list = this.cargaCorrelaciones(root);
        //Anhadimos al alamcen todos los estadisticos
        Iterator<ResultadoCorrelacion> it3 = correlaciones_list.iterator();
        while (it3.hasNext()) {
            ResultadoCorrelacion correlacion = it3.next();
            gestor_datos.anadeCorrelacion(correlacion);
        }
        if (posGlobal != null) {
            //Esto es una chapuza, lo hago para preparar el almacen ara contener una
            //Posibilidad mas
            GestorDatos.getInstancia().setPosibilidadTotal(0,
                    posGlobal.length - 1, (byte) 0);
            almacen.setPosibilidadTotal(posGlobal);
        }
        almacen.setFechaBase(fecha_base);
        SamplesToDate.getInstancia().setFechaBase(fecha_base);

        //Leemos las senales monitorizadas y las "monitorizamos"
        this.cargaConfiguracion(root);
        //cargamos el cometario:
        Element cometario_xml = root.getChild(ATTR_COMENTARIO);
        if (cometario_xml != null) {
            gestor_datos.setComentario(cometario_xml.getText());
        }

    }

    /**
     * Las leyendas por cmodidad las habiamos guardado todas en un solo String
     * sepradas por espacios en blanco. Aqui las recuperamos.
     * @param todas_las_leyendas
     * @return
     */
//    private String[] procesaLeyenda(String todas_las_leyendas) {
//        StringTokenizer tk = new StringTokenizer(todas_las_leyendas.trim());
//        int num_leyendas = tk.countTokens();
//        String[] leyendas = new String[num_leyendas];
//        int count = 0;
//        while (tk.hasMoreTokens()) {
//            leyendas[count] = tk.nextToken();
//            count++;
//        }
//        return leyendas;
//    }

    /**
     * Dada una lista con los nodos XML de terapia devuelve una LinkedList con los
     * objetos de tipo terapia crados a partir de esta.
     * @param lista_terapia_xml
     * @return
     */
    private LinkedList<Therapy> genraAnotacionesTerapia(List<Element> lista_terapia_xml) {
        LinkedList<Therapy> resultado = new LinkedList<Therapy>();
        Iterator<Element> it = lista_terapia_xml.iterator();
        //Para todas las anotaciones
        while (it.hasNext()) {
            //Obtenemos del nodo XML los componentes de la terapia
            Element terapia_xml = it.next();
            String texto = terapia_xml.getAttribute(ATTR_TEXTO).getValue();
            int tiempo_inicio;
            int tipo_evento;
            int offset;
            int tiempo_fin;
            try {
                tiempo_inicio = terapia_xml.getAttribute(ATTR_TIEMPO_INICIO).getIntValue();
                tipo_evento = terapia_xml.getAttribute(ATTR_TIPO_EVENTO).getIntValue();
                offset = terapia_xml.getAttribute(ATTR_OFFSET).getIntValue();
                tiempo_fin = terapia_xml.getAttribute(ATTR_TIEMPO_FIN).getIntValue();
            } catch (DataConversionException ex) {
                LOGGER.log(Level.SEVERE, "Excepcion de conversion numerica de JDOM no procesada", ex);
                //Devolvemos lo que tengamos
                return resultado;
            }
            String comentario = terapia_xml.getAttribute(ATTR_COMENTARIO).getValue();
            String nombre_farmaco = terapia_xml.getAttribute("NombreFarmaco").
                                    getValue();
            String fase_terapeutica = terapia_xml.getAttribute(
                    "FaseTerapeutica").getValue();
            String dosificacion = terapia_xml.getAttribute("Dosificacion").
                                  getValue();
            String tipo_terapia = terapia_xml.getAttribute("TipoTerapia").
                                  getValue();
            //Creamos el objeto de tipo terapia
            Therapy terapia = new Therapy(nombre_farmaco, fase_terapeutica,
                                          dosificacion, tipo_terapia);
            terapia.setOffset(offset);
            terapia.setTipo(tipo_evento);
            terapia.setTiempo(tiempo_inicio);
            terapia.setTipoTerapia(tipo_terapia);
            terapia.setTiempoFin(tiempo_fin);
            terapia.setTexto(texto);
            terapia.setComentario(comentario);
            resultado.add(terapia);
        }
        return resultado;
    }


    /**
     * Dada una lista con los nodos XML de Diagnostico devuelve una LinkedList con los
     * objetos de tipo Diagnostico crados a partir de esta.
     * @param lista_terapia
     * @return
     */
    private LinkedList<Diagnostic> genraAnotacionesDiagnostico(List<Element> lista_diagnostico_xml) {
        LinkedList<Diagnostic> resultado = new LinkedList<Diagnostic>();
        Iterator<Element> it = lista_diagnostico_xml.iterator();
        //Para todas las anotaciones
        while (it.hasNext()) {
            //Obtenemos del nodo XML los componentes de la terapia
            Element diagnostico_xml = it.next();
            String texto = diagnostico_xml.getAttribute(ATTR_TEXTO).getValue();
            int tiempo_inicio;
            int tipo_evento;
            int offset;
            int tiempo_fin;
            try {
                tiempo_inicio = diagnostico_xml.getAttribute(ATTR_TIEMPO_INICIO).getIntValue();
                tipo_evento = diagnostico_xml.getAttribute(ATTR_TIPO_EVENTO).getIntValue();
                offset = diagnostico_xml.getAttribute(ATTR_OFFSET).getIntValue();
                tiempo_fin = diagnostico_xml.getAttribute(ATTR_TIEMPO_FIN).getIntValue();
            } catch (DataConversionException ex) {
                LOGGER.log(Level.SEVERE, "Excepcion de conversion numerica de JDOM no procesada", ex);
                //Devolvemos lo que tengamos
                return resultado;
            }
            String comentario = diagnostico_xml.getAttribute(ATTR_COMENTARIO).
                                getValue();
            //Pedimos el elemento de tipo atributo
            Element atributo_xml = diagnostico_xml.getChild(EL_ATRIBUTO);
            String nombre_atributo = atributo_xml.getAttributeValue(ATTR_ATRIBUTO);
            String valor_atributo = atributo_xml.getAttributeValue("Valor");
            Attribute atributo = new Attribute(nombre_atributo, valor_atributo);
            //Creamos el objeto de tipo terapia
            Diagnostic diagnostico = new Diagnostic(texto, atributo);
            diagnostico.setOffset(offset);
            diagnostico.setTipo(tipo_evento);
            diagnostico.setTiempo(tiempo_inicio);
            diagnostico.setTiempoFin(tiempo_fin);
            diagnostico.setAtributo(atributo);
            diagnostico.setComentario(comentario);
            resultado.add(diagnostico);
        }
        return resultado;
    }

    /**
     * Dada una lista con los nodos XML de Manifestacion devuelve una LinkedList con los
     * objetos de tipo Manifestacion crados a partir de esta.
     * @param lista_terapia
     * @return
     */
    private LinkedList<Manifestacion> genraAnotacionesManifestacion(List<Element> lista_manifestacion) {
        LinkedList<Manifestacion> resultado = new LinkedList<Manifestacion>();
        Iterator<Element> it = lista_manifestacion.iterator();
        //Para todas las anotaciones
        while (it.hasNext()) {
            //Obtenemos del nodo XML los componentes de la terapia
            Element manifestacion_xml = it.next();
            String texto = manifestacion_xml.getAttribute(ATTR_TEXTO).getValue();
            int tiempo_inicio;
            int tipo_evento;
            int offset;
            int tiempo_fin;
            int tipo_manifestacion;
            try {
                tiempo_inicio = manifestacion_xml.getAttribute(ATTR_TIEMPO_INICIO).getIntValue();
                tipo_evento = manifestacion_xml.getAttribute(ATTR_TIPO_EVENTO).getIntValue();
                offset = manifestacion_xml.getAttribute(ATTR_OFFSET).getIntValue();
                tiempo_fin = manifestacion_xml.getAttribute(ATTR_TIEMPO_FIN).getIntValue();
                tipo_manifestacion = manifestacion_xml.getAttribute("TipoManifestacion").getIntValue();
            } catch (DataConversionException e) {
                LOGGER.log(Level.SEVERE, "Excepcion de conversion numerica de JDOM no procesada", e);
                //Devolvemos lo que tengamos
                return resultado;
            }
            String comentario = manifestacion_xml.getAttribute(ATTR_COMENTARIO).getValue();
            //Pedimos la lsita de elementos de tipo atributo
            @SuppressWarnings("unchecked")
                List<Element> lista_atributo_xml = manifestacion_xml.getChildren(EL_ATRIBUTO);
            Iterator<Element> it2 = lista_atributo_xml.iterator();
            LinkedList<Attribute> lista_atributo = new LinkedList<Attribute>();
            while (it2.hasNext()) {
                Element atributo_xml = it2.next();
                String nobre_atributo = atributo_xml.getAttributeValue(ATTR_ATRIBUTO);
                String valor_atributo = atributo_xml.getAttributeValue("Valor");
                Attribute atributo = new Attribute(nobre_atributo, valor_atributo);
                lista_atributo.add(atributo);
            }

            //Creamos el objeto de tipo terapia
            Manifestacion manifestacion = new Manifestacion(texto, comentario,
                    tipo_evento, lista_atributo);
            manifestacion.setOffset(offset);
            manifestacion.setTipo(tipo_evento);
            manifestacion.setTiempo(tiempo_inicio);
            manifestacion.setTiempoFin(tiempo_fin);
            manifestacion.setComentario(comentario);
            manifestacion.setTipoManifestacion(tipo_manifestacion);
            resultado.add(manifestacion);
        }
        return resultado;
    }

    /**
     * Carga los datos del fichcer de texto
     * @param file
     * @param datos
     * @param pos
     */
    @SuppressWarnings("unchecked")
    private void cargaDatos(String file, int num_senales,
                            boolean[] tien_pos_asociada,
                            float[][] datos, byte[][] pos,
                            boolean tiene_pos_gloabal) {
        BufferedReader bf = null;
        FileReader f = null;
        try {
            f = new FileReader(file);
            bf = new BufferedReader(f);
            File fich = new File(file);
            //Marcasmos el Buffer para poder resetearlo una vez averiguado el numero
            //de filas y columnas que hay en el
            bf.mark((int) fich.length() + 1);
            String line = bf.readLine();
            StringTokenizer tk = new StringTokenizer(line);
            int columnas = 0;
            //Contamos el numero de columnas que hay en el archivo
            while (tk.hasMoreTokens()) {
                columnas++;
                tk.nextToken();
            }
            //Contamos el numero de filas. Aunque ya llevamos 1 leida el contador
            //empieza en 0 ya que el bucle incrementara su valor la primera vez que lea null
            int filas = 0;
            while (line != null) {
                line = bf.readLine();
                filas++;
            }
            //Inicilizamos todas la variables
            for (int i = 0; i < num_senales; i++) {
                datos[i] = new float[filas];
                pos[i] = new byte[filas];
            }
            //Y la pos gloablal, si es necesaria
            if (tiene_pos_gloabal) {
                posGlobal = new byte[filas];
            }
            marcas = new TreeSet[num_senales];
            anotaciones = new TreeSet<Annotation>();
            for (int i = 0; i < num_senales; i++) {
                marcas[i] = new TreeSet<Mark>();
            }
            //reseteamos el buffer
            bf.reset();
            //Contadores de linea y columna
            int columna = 0;
            int linea = 0;
            do {
                line = bf.readLine();
                if (line != null) {
                    StringTokenizer tk2 = new StringTokenizer(line, "\t", true);
                    columna = 0;
                    while (tk2.hasMoreTokens() && columna != num_senales) {
                        String dato_fichero = tk2.nextToken();
                        //Si columna es mayor que numero de senhales => estamos leyendo la posibilidad global
                        if (columna == num_senales) {
                            posGlobal[linea] = Byte.parseByte(dato_fichero);
                        }

                        else if ("\t".equals(dato_fichero)) {
                            datos[columna][linea] = 0;
                        } else {
                            datos[columna][linea] = Float.parseFloat(
                                    dato_fichero);
                            //Si no estamos en la ultima columna //consuminos el \t adicional
                            if (tk2.hasMoreElements() && !("\t".equals(tk2.nextElement()))) {
                               LOGGER.log(Level.WARNING, "Se esta leyendo mal el fichero de entrada");
                            }
                            columna++;
                        }
                        //Si tiene posibilidad asociada leemos ya este dato:
                        //El primer condicional es porque hemos incrementado columna y puede que ya hallmos acabado.
                        if (tien_pos_asociada[columna - 1]) {
                            dato_fichero = tk2.nextToken();
                            if ("\t".equals(dato_fichero)) {
                                //pos[lin][col] = 0;
                            } else {
                                pos[columna - 1][linea] = Byte.parseByte(dato_fichero);
                            }
                            if (tk2.hasMoreElements() && !("\t".equals(tk2.nextElement()))) {
                                //consumimos el \t adicional
                               LOGGER.log(Level.WARNING, "Se esta leyendo mal el fichero de entrada");
                            }
                        }
                    }

                    //Si columna es mayor que numero de senhales => estamos leyendo la posibilidad global
                    if (columna == num_senales && tk2.hasMoreTokens()) {
                        String dato_fichero = tk2.nextToken();
                        posGlobal[linea] = Byte.parseByte(dato_fichero);
                    }
                    linea++;
                }

            } while (line != null);
            bf.close();

            //GestorIO gestor_io = GestorIO.getGestorIO();
            GestorIO.setNumDatos(filas);
            GestorIO.setNumSenales(columnas);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            marcas = null;
            anotaciones = null;
        } finally {
           if (f !=null){
              try{
                 f.close();
              }catch(Exception e){
                 LOGGER.log(Level.FINER, e.getMessage(), e);
              }
           }
        }
    }

    private LinkedList<ResultadosEstadisticos> cargaEstadisticos(Element root) {
        LinkedList<ResultadosEstadisticos> resultado = new LinkedList<ResultadosEstadisticos>();
        @SuppressWarnings("unchecked")
        List<Element> list_estadisticos = (List<Element>)root.getChildren("Estadistico");
        Iterator<Element> it = list_estadisticos.iterator();
        while (it.hasNext()) {
            try {
                Element estadistico_xml = it.next();
                float media = estadistico_xml.getAttribute("Media").getFloatValue();
                float mediana = estadistico_xml.getAttribute("Mediana").getFloatValue();
                float varianza = estadistico_xml.getAttribute("Varianza").getFloatValue();
                float desviacion_tipica = estadistico_xml.getAttribute(
                        "DesviacionTipica").getFloatValue();
                float error_estandar = estadistico_xml.getAttribute(
                        "ErrorEstandar").getFloatValue();
                float cociente_de_variacion = estadistico_xml.getAttribute(
                        "CocienteDeVariacion").getFloatValue();
                String fecha_inicio = estadistico_xml.getAttribute(
                        "FechaInicio").getValue();
                String fecha_fin = estadistico_xml.getAttribute("FechaFin").getValue();
                String nombre = estadistico_xml.getAttribute("Nombre").getValue();
                float[] intervalo_de_confianza = new float[2];
                intervalo_de_confianza[0] = estadistico_xml.getAttribute(
                        "IntervaloDeConfianzaInicio").getFloatValue();
                intervalo_de_confianza[1] = estadistico_xml.getAttribute(
                        "IntervaloDeConfianzaFin").getFloatValue();
                String comentario = estadistico_xml.getChild(ATTR_COMENTARIO).getText();

                //Cojemos la lista de percentiles
                @SuppressWarnings("unchecked")
                List<Element> list_percentiles = estadistico_xml.getChildren("Percentiles");
                Iterator<Element> it2 = list_percentiles.iterator();
                int num_percentiles = list_percentiles.size();
                int[] percentiles_float = new int[num_percentiles];
                float[] valores_percentiles = new float[num_percentiles];
                int cuantos_van = 0;
                while (it2.hasNext()) {
                    Element percentil_xml = it2.next();
                    percentiles_float[cuantos_van] = percentil_xml.getAttribute("Percentil").getIntValue();
                    valores_percentiles[cuantos_van] = percentil_xml.getAttribute("ValorPercentil").getFloatValue();
                    cuantos_van++;
                }
                ResultadosEstadisticos estadistico = new ResultadosEstadisticos(
                        media, mediana, varianza,
                        desviacion_tipica, error_estandar,
                        cociente_de_variacion, intervalo_de_confianza,
                        percentiles_float,
                        valores_percentiles, fecha_inicio, fecha_fin, nombre);
                estadistico.setComentario(comentario);
                resultado.add(estadistico);
            } catch (NotPercentilException e) {
               LOGGER.log(Level.SEVERE, e.getMessage(), e);
            } catch (DataConversionException e) {
               LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return resultado;
    }

    /**
     * Almacena los canales que se estaban visualizando en un determinado momento.
     * @param root
     */
    private void cargaConfiguracion(Element root) {
        GestorDatos gestor_datos = GestorDatos.getInstancia();
        Element configuracion = root.getChild("Configuracion");
        if (configuracion != null) {
            @SuppressWarnings("unchecked")
            List<Element> lista_senales = configuracion.getChildren("CanalMonitorizado");
            Iterator<Element> it = lista_senales.iterator();
            int[] senesles_monitorizadas = new int[lista_senales.size()];
            int count = 0;
            while (it.hasNext()) {
                try {
                    senesles_monitorizadas[count] = it.next().getAttribute("NumeroSenal").getIntValue();
                    count++;
                } catch (DataConversionException e) {
                   LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
            }
            gestor_datos.setSenalesQueSeMonitorizaronLaUltimaVez(
                    senesles_monitorizadas);
        }

    }

    /**
     *
     * @param root
     * @return
     */
    private LinkedList<ResultadoCorrelacion> cargaCorrelaciones(Element root) {
        LinkedList<ResultadoCorrelacion> resultado = new LinkedList<ResultadoCorrelacion>();
        @SuppressWarnings("unchecked")
        List<Element> list_correlaciones = root.getChildren("Correlacion");
        Iterator<Element> it = list_correlaciones.iterator();
        while (it.hasNext()) {
            try {
                Element correlacion_xml = it.next();
                float coef_correlacion = correlacion_xml.getAttribute("Correlacion").getFloatValue();
                int significacion = correlacion_xml.getAttribute("Significacion").getIntValue();
                String nombre_correlacion = correlacion_xml.getAttribute("NombreCorrelacion").getValue();
                String nombre_senhal1 = correlacion_xml.getAttribute("NombreSenhal1").getValue();
                String nombre_senhal2 = correlacion_xml.getAttribute("NombreSenhal2").getValue();
                String fecha_inicio1 = correlacion_xml.getAttribute("FechaInicio1").getValue();
                String fecha_inicio2 = correlacion_xml.getAttribute("FechaInicio2").getValue();
                String fecha_fin1 = correlacion_xml.getAttribute("FechaFin1").getValue();
                String fecha_fin2 = correlacion_xml.getAttribute("FechaFin2").getValue();
                String comentario = correlacion_xml.getChild(ATTR_COMENTARIO).getText();

                ResultadoCorrelacion correlacion = new ResultadoCorrelacion();
                correlacion.setNombre(nombre_correlacion);
                correlacion.setFechaFin1(fecha_fin1);
                correlacion.setFechaFin2(fecha_fin2);
                correlacion.setFechaInicio1(fecha_inicio1);
                correlacion.setFechaInicio2(fecha_inicio2);
                correlacion.setSenal1(nombre_senhal1);
                correlacion.setSenal2(nombre_senhal2);
                correlacion.setComentario(comentario);
                correlacion.setNivelDeSignificacion(coef_correlacion);
                correlacion.setNivelDeSignificacionDiscreto(significacion);
                resultado.add(correlacion);
            } catch (DataConversionException e) {
               LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return resultado;
    }

}
