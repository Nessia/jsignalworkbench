package es.usc.gsi.trace.importer.monitorizacion.dataio;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.*;
import org.jdom.filter.ContentFilter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import es.usc.gsi.trace.importer.perfil.*;

class MyFloat {

    private static final Logger LOGGER = Logger.getLogger(MyFloat.class.getName());

    public final static int MAS_INFINITO = Integer.MAX_VALUE / 1000;
    public final static int MENOS_INFINITO = Integer.MIN_VALUE / 1000;


    private static boolean hayParser = false;
    private static DecimalFormat decimalFormat;
    private static int numeroDecimalesActuales = 1;


    private MyFloat(){
       // Hide constructor
    }

    private static void contruyePraser() {
        if (!hayParser) {
            Locale default_locale = Locale.getDefault();
            //Ponemos como localidad la inglesa, pa que pille . en vez de ,
            Locale.setDefault(new Locale("en", "GB"));
            decimalFormat = new DecimalFormat("###.#");
            //Ahora que ya tengo un parseador "A la inglesa" volvemos pa espananha:
            Locale.setDefault(default_locale);
            hayParser = true;
        }

    }

    /**
     *Se emplea para formatear los numeros que seguro estan bien,
     * es decir los que ya se han chequeado y se vuelven a mostrar al usuario.
     * @param numero
     * @return
     */
    public static float parseFloatSeguro(String numero) {
        if (!("&".equals(numero)) && !("-&".equals(numero))) {
            return Float.parseFloat(numero);
        } else if ("&".equals(numero)) {
            return Integer.MAX_VALUE / 1000F;
        } else {
            return Integer.MIN_VALUE / 1000F; //Por que si no en validar dan overflow
        }
    }

    /**
     *
     * @param numero
     * @return
     * @throws Exception
     */
    public static float parseFloat(String numero) throws NumberFormatException {
//        try {
            if (!("&".equals(numero)) && !("-&".equals(numero))) {
                return Float.parseFloat(numero);
            } else if ("&".equals(numero)) {
                return MAS_INFINITO;
            } else {
                return MENOS_INFINITO; //Por que si no en validar dan overflow
            }
//        } catch (NumberFormatException ex) {
//            throw ex;
//        }
    }

    /**
     *
     * @param f
     * @return
     */
    public static String formateaNumero(float f) {
        //Si es infinito
        if (f == MAS_INFINITO) {
            return "&";
        } else if (f == MENOS_INFINITO) {
            return "-&";
        }

        return formateaNumero(Float.toString(f));
    }

    /**
     * Devuelve null si No se pudo completar la operacion
     * @param numero
     * @return
     */
    public static String formateaNumero(String numero) {
        if (!hayParser) {
            contruyePraser();
        }
        String valor = numero.trim();
        if ("&".equals(valor) || "-&".equals(valor)) {
            return valor;
        }

        try {
            return decimalFormat.format(parseFloat(valor));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static String toString(float numero) {
        return formateaNumero(numero);
    }

    /**
     *
     * @param M
     * @return
     */
//    private static String convierteAString(float M) {
//        if (M < MAS_INFINITO && M > MENOS_INFINITO) {
//            return Float.toString(M);
//        } else if (M == MENOS_INFINITO) {
//            return "-&";
//        } else {
//            return "&";
//        }
//    }

    /**
     * Emplear para cambiar el numero de digitos decimales del patron
     * @param numero_decimaales
     */
    public static void setNumeroDecimales(int numeroDecimales) {
        if (!hayParser) {
            contruyePraser();
        }
        StringBuilder pattern = new StringBuilder("###");
        if (numeroDecimales > 0) {
            pattern.append(".");//pattern = pattern + ".";
            for (int i = 0; i < numeroDecimales; i++) {
                pattern.append("."); //pattern = pattern + "#";
            }

        }
        numeroDecimalesActuales = numeroDecimales;
        decimalFormat.applyPattern(pattern.toString());
    }

    /**
     *
     * @return
     */
    public static int getNumeroDecimalesActuales() {
        return numeroDecimalesActuales;
    }
}


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class PTBM2XML {
    private final static Logger LOGGER = Logger.getLogger(PTBM2XML.class.getName());

    public final static String EL_UNIDADES_TEMPORALES = "UnidadesTemporales";
    public final static String EL_INICIO_SOPORTE = "InicioSoporte";
    public final static String EL_INICIO_CORE = "InicioCore";
    public final static String EL_FIN_CORE = "FinCore";
    public final static String EL_FIN_SOPORTE = "FinSoporte";
    public final static String EL_LONGITUD_VENTANA_TEMPORAL = "LongitudVentanaTemporal";

    private static PTBM2XML ptbm2XML = null;

    private PTBM2XML() {
//        if (ptbm2XML != null) {
//            //System.out.println("ERROR, intanciado dos veces un singleton");
//           LOGGER.log(Level.SEVERE, "ERROR, intanciado dos veces un singleton");
//        }
    }

    /**
     * Dada una cadena de caracteres correspondiente a un archivo y un ptbm almacena el
     * objeto ptbm en el archivo correpondiente en formato XML.
     * @param ptbm
     * @param archivo
     * @return
     */
    public boolean GuardaPTBM(PTBMInterface ptbm, String archivo) {
        if (ptbm == null) {
            return true;
        }

        //Creo el documento XML con el nodo raiz
        Element root = new Element("PTBM");
        //  DocType doc_type = new DocType("PTBM","file:///C:/ptbm.dtd");
        Document documento = new Document(root /*,doc_type*/);
        root.setAttribute("NumeroPTB", Integer.toString(ptbm.getPTB().length));
        root.setAttribute("ComentarioPTBM", ptbm.getComentario());
        root.setAttribute("NombrePTBM", ptbm.getTitulo());
        //Bucle que ira anhadiendo los nodos PTB
        PTBInterface[] ptb_array = ptbm.getPTB();
        for (int i = 0; i < ptb_array.length; i++) {
            //Creo el nodo PTB con sus atributos
            Element ptb = new Element("PTB");
            ptb.setAttribute("NombrePTB", ptb_array[i].getNombre());
            ptb.setAttribute("NumeroPtoSig",
                  Integer.toString(ptb_array[i].getNumeroDePtoSig()));
            ptb.setAttribute("ComentarioPTB", ptb_array[i].getComentario());
            ptb.setAttribute("Unidades", ptb_array[i].getUnidades());
            ptb.setAttribute("Parametro", ptb_array[i].getParametro());
            ptb.setAttribute(EL_UNIDADES_TEMPORALES, ptb_array[i].getUnidadesTemporales());
            ptb.setAttribute("BuscarEnValorAbsoluto",
                             Boolean.toString(ptb_array[i].isBuscarEnValorAbsoluto()));
            //Anhado el nodo creado a la raiz del documebnto
            root.addContent(ptb);
            //Continuo Anhadiendo los hijos al nodo PTB
            PtoSigInterface[] pto_sig_array = ptb_array[i].getPtoSig();
            for (int j = 0; j < pto_sig_array.length; j++) {
                //Creo el nodo PtoSig con sus atributos
                Element pto_sig = new Element("PtoSig");
                Restriccion[] restricciones_array = pto_sig_array[j].
                        getRestricciones();
                pto_sig.setAttribute("NumeroDePtoSig", Integer.toString(j));
                pto_sig.setAttribute("NumeroDeRestricciones", Integer.toString(restricciones_array.length));
                //Anhado el nodo creado al nodo del cual culega
                ptb.addContent(pto_sig);
                //Continuo anhadiendo los nodos hijos
                for (int k = 0; k < restricciones_array.length; k++) {
                    //Creo el nodo restriicon con sus atributos
                    Element restriccion = new Element("Restriccion");
                    restriccion.setAttribute("PTBOrigen", Integer.toString(restricciones_array[k].getNumeroDePTB()));
                    restriccion.setAttribute("PtoSigOrigen", Integer.toString(restricciones_array[k].getNumeroDePtoSig()));
                    restriccion.setAttribute("PTBDestino", Integer.toString(i));
                    restriccion.setAttribute("PtoSigDestino", Integer.toString(j));
                    restriccion.setAttribute("RelativaAlBasal", Boolean.toString(restricciones_array[k].isRelativaAlNivelBasal()));
                    //Anhado el nod ala padre
                    pto_sig.addContent(restriccion);
                    //Continuo anhadiendole los hijos al nodo
                    String[] D = restricciones_array[k].getD();
                    String[] L = restricciones_array[k].getL();
                    String[] M = restricciones_array[k].getM();
                    int sintaxis_int = restricciones_array[k].getSemantica().ordinal();
                    int cunatificadorInt = restricciones_array[k].
                                           getCuantificadorSemantica();
                    int unidadesTemporalesInt = restricciones_array[k].
                                                getUnidadesTemporales();
                    float magnitudPrimerPtoSigFloat = restricciones_array[k].
                            getMagnitudPrimerPtoSig();
                    float magnitudSegundoPtoSigFloat = restricciones_array[k].
                            getMagnitudSegundoPtoSig();
                    int distanciaTemporalEntrePtoSig = restricciones_array[k].
                            getDistanciaTemporalEntrePtoSig();

                    //Elemento restricion magnitud:
                    Element restriccon_magnitud = new Element("Magnitud");
                    restriccon_magnitud.setAttribute(EL_INICIO_SOPORTE, D[0]);
                    restriccon_magnitud.setAttribute(EL_INICIO_CORE, D[1]);
                    restriccon_magnitud.setAttribute(EL_FIN_CORE, D[2]);
                    restriccon_magnitud.setAttribute(EL_FIN_SOPORTE, D[3]);
                    //Anhadimos este elemnto al padre:
                    restriccion.addContent(restriccon_magnitud);

                    //Elemento restricion Temporal:
                    Element restriccon_temporal = new Element("Temporal");
                    restriccon_temporal.setAttribute(EL_INICIO_SOPORTE, L[0]);
                    restriccon_temporal.setAttribute(EL_INICIO_CORE, L[1]);
                    restriccon_temporal.setAttribute(EL_FIN_CORE, L[2]);
                    restriccon_temporal.setAttribute(EL_FIN_SOPORTE, L[3]);
                    //Anhadimos este elemnto al padre:
                    restriccion.addContent(restriccon_temporal);

                    //Elemento unidades temporales:
                    Element unidadesTemporales = new Element(EL_UNIDADES_TEMPORALES);
                    unidadesTemporales.setAttribute("Tipo", Integer.toString(unidadesTemporalesInt));
                    restriccion.addContent(unidadesTemporales);
                    //Elemento restricion Pendiente:
                    Element restriccon_pendiente = new Element("Pendiente");
                    restriccon_pendiente.setAttribute(EL_INICIO_SOPORTE, M[0]);
                    restriccon_pendiente.setAttribute(EL_INICIO_CORE, M[1]);
                    restriccon_pendiente.setAttribute(EL_FIN_CORE, M[2]);
                    restriccon_pendiente.setAttribute(EL_FIN_SOPORTE, M[3]);
                    //Anhadimos este elemnto al padre:
                    restriccion.addContent(restriccon_pendiente);

                    //Semantica
                    Element semantica = new Element("Semantica");
                    semantica.setAttribute("Tipo", Integer.toString(sintaxis_int));
                    semantica.setAttribute("Cuantificador", Integer.toString(cunatificadorInt));
                    restriccion.addContent(semantica);

                    //Informacion para ayudar al usuario
                    Element infoUsuario = new Element("InformacionUsuario");
                    infoUsuario.setAttribute("MagnitudPrimerPtoSig", Float.toString(magnitudPrimerPtoSigFloat));
                    infoUsuario.setAttribute("MagnitudSegundoPtoSig", Float.toString(magnitudSegundoPtoSigFloat));
                    infoUsuario.setAttribute("DistanciaTemporal", Float.toString(distanciaTemporalEntrePtoSig));
                    restriccion.addContent(infoUsuario);
                }

            }
            //Distancia de separacion entre PTB y longitud de la ventana temporal
            Element distanciaEntrePTB = new Element("DistanciaEntrePTB");
            distanciaEntrePTB.setAttribute(EL_INICIO_SOPORTE,
                  Float.toString(ptb_array[i].getIntInicioSoporteSeparacion()));
            distanciaEntrePTB.setAttribute(EL_INICIO_CORE,
                  Float.toString(ptb_array[i].getIntInicioCoreSeparacion()));
            distanciaEntrePTB.setAttribute(EL_FIN_CORE, Float.toString(ptb_array[i].getIntFinCoreSeparacion()));
            distanciaEntrePTB.setAttribute(EL_FIN_SOPORTE, Float.toString(ptb_array[i].getIntFinSoporteSeparacion()));
            ptb.addContent(distanciaEntrePTB);
            Element longitudVentanaTemporal = new Element(EL_LONGITUD_VENTANA_TEMPORAL);
            longitudVentanaTemporal.setAttribute(EL_LONGITUD_VENTANA_TEMPORAL,
                  Float.toString(ptb_array[i].getLongitudVentana()));
            ptb.addContent(longitudVentanaTemporal);
        }
        Element distanciaEntrePTBM = new Element("DistanciaEntrePTBM");
        distanciaEntrePTBM.setAttribute(EL_INICIO_SOPORTE, Float.toString(ptbm.getInicioSoporteSeparacion()));
        distanciaEntrePTBM.setAttribute(EL_INICIO_CORE,
              Float.toString(ptbm.getInicioCoreSeparacion()));
        distanciaEntrePTBM.setAttribute(EL_FIN_CORE,
              Float.toString(ptbm.getFinCoreSeparacion()));
        distanciaEntrePTBM.setAttribute(EL_FIN_SOPORTE,
              Float.toString(ptbm.getFinSoporteSeparacion()));
        root.addContent(distanciaEntrePTBM);
        // El documento entero deberia estar creado, asi que vamos a Almacenarlo:
        XMLOutputter xml_outputter = new XMLOutputter();
        File file = new File(archivo);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            xml_outputter.output(documento, out);
            return true;
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }

    }


    /**
     * Dada una cadena de caracteres que representa un archivo devuelve el PTBM contenido
     * en el archivo. En este debe haber una PTBM en formato XML.
     * @param archivo
     * @return
     */
    public PTBMInterface cargaPTBM(String archivo) {
        //Cargamos el documento validando
        SAXBuilder bulder = new SAXBuilder( /*true*/false);
        Document documento = null;
        try {
            documento = bulder.build(archivo);
        } catch (IOException ex3) {
           LOGGER.log(Level.SEVERE, ex3.getMessage(), ex3);
        } catch (JDOMException ex3) {
           LOGGER.log(Level.SEVERE, ex3.getMessage(), ex3);
        }

        //Obtenemos el elemento raiz, el PTBM, y cojemos sus atributos.
        Element root = documento.getRootElement();
        String comentarioPTBM = root.getAttribute("ComentarioPTBM").getValue();
        String nombrePTBM = root.getAttribute("NombrePTBM").getValue();
        //Empleamos un filtro para deshacernos de los nodos de texto
        ContentFilter filtro = new ContentFilter(false);
        filtro.setElementVisible(true);

        @SuppressWarnings("unchecked")
        Iterator<Element> it = root.getContent(filtro).iterator();
        //Empleamos un floag para marcar el primer PTB, que se le pasara en el constructor
        //Al PTBM, de los demas que se le anhadiran
        PTBM ptbm = null;
        boolean primer_ptb = true;
        int num_PTB = -1;
        Element ultimoNodoDelPTBM = null;
        while (it.hasNext()) {
            //Contador de PTB
            num_PTB++;
            //Cojo el primer elemento ptb y extraigo sus atributos
            Element ptb_xml = it.next();
            Attribute comentario = ptb_xml.getAttribute("ComentarioPTB");
            //Si no hay comentario => es ya la separacion entre PTB
            if (comentario == null) {
                //Este nodo realemente es la distancia
                ultimoNodoDelPTBM = ptb_xml;
                break;
            }
            String comentario_PTB = comentario.getValue();
            String parametro = ptb_xml.getAttribute("Parametro").getValue();
            String unidades = ptb_xml.getAttribute("Unidades").getValue();
            String unidades_temporales = ptb_xml.getAttribute(EL_UNIDADES_TEMPORALES).getValue();
            String nombre_PTB = ptb_xml.getAttribute("NombrePTB").getValue();
            //Miro si hay atributo de usar en valor absoluto
            Attribute atrUsarEnValorAbsoluto = ptb_xml.getAttribute(
                    "BuscarEnValorAbsoluto");
            boolean usarEnValorAbsoluto = false;
            if (atrUsarEnValorAbsoluto != null) {
                try {
                    usarEnValorAbsoluto = atrUsarEnValorAbsoluto.getBooleanValue();
                } catch (DataConversionException ex2) {
                   LOGGER.log(Level.WARNING, ex2.getMessage(), ex2);
                }
            }
            //Creo el PTB
            PTB ptb = new PTB(nombre_PTB, parametro, unidades,
                              unidades_temporales, comentario_PTB, num_PTB);
            ptb.setBuscarEnValorAbsoluto(usarEnValorAbsoluto);
            if (primer_ptb) {
                ptbm = new PTBM(nombrePTBM, comentarioPTBM, ptb);
                primer_ptb = false;
            } else {
                ptbm.anhadePTB(ptb, num_PTB, PTBInterface.Acciones.ANHADIR);
            }

            //Comenzamos a RECOPILAR PUNTOS SIGNIFICATIVOS:
            @SuppressWarnings("unchecked")
            Iterator<Element> it2 = ptb_xml.getContent(filtro).iterator();
            int num_PtoSig = -1;
            while (it2.hasNext()) {
                num_PtoSig++;
                Element PtoSig_xml = it2.next();
                //Los puntos significativos = y ! se anhaden automaticamente al PTB, solo tendremos
                //que anhadir las restricciones que tengan
                if (num_PtoSig < 2 && PtoSig_xml.getChildren().isEmpty()) {
                    continue;
                }
                Restriccion[] restriciones_de_un_PToSig = generaRestricciones(PtoSig_xml);
                for (int i = 0; i < restriciones_de_un_PToSig.length; i++) {
                    //Si es el primer o segundo Pto Sig ya esta anhadido al PTB
                    if (num_PtoSig < 2) {
                        ptb.anhadeRestriccion(0, num_PtoSig,
                                              restriciones_de_un_PToSig[i], null,
                                              PTBInterface.Acciones.ANHADIR);
                    }
                    //Si no lo era y si es la primera restriccion => preimero anhadir el PtoSig al PTB:
                    else if (i == 0) {
                        PtoSig pto_sig = new PtoSig(restriciones_de_un_PToSig[0], num_PTB, num_PtoSig);
                        ptb.anhadePtoSig(pto_sig);
                    }
                    //Si no es que ya esta anhadido el PtoSig => anhadimos solo la restricion
                    else {
                        ptb.anhadeRestriccion(num_PTB, num_PtoSig,
                                              restriciones_de_un_PToSig[i], null,
                                              PTBInterface.Acciones.ANHADIR);
                    }
                }
            }
            //Leemos la distancia entre PTB y la ventana temporal
            Element distanciaEntrePTB_XML = ptb_xml.getChild("DistanciaEntrePTB");
            Element ventanaTemporal_XML = ptb_xml.getChild(EL_LONGITUD_VENTANA_TEMPORAL);

            //Por motivos de compatibilidad hacia atas chequeamos si este PYB
            //No tiene asociada esta informacion
            if (distanciaEntrePTB_XML == null || ventanaTemporal_XML == null) {
                continue;
            }
            try {
                ptb.setIntInicioSoporteSeparacion(distanciaEntrePTB_XML.
                                                  getAttribute(EL_INICIO_SOPORTE).
                                                  getFloatValue());
                ptb.setIntInicioCoreSeparacion(distanciaEntrePTB_XML.
                                               getAttribute(EL_INICIO_CORE).
                                               getFloatValue());
                ptb.setIntFinCoreSeparacion(distanciaEntrePTB_XML.getAttribute(
                        EL_FIN_CORE).getFloatValue());
                ptb.setIntFinSoporteSeparacion(distanciaEntrePTB_XML.
                                               getAttribute(EL_FIN_SOPORTE).
                                               getFloatValue());
                ptb.setLongitudVentana(ventanaTemporal_XML.getAttribute(EL_LONGITUD_VENTANA_TEMPORAL).getFloatValue());
            } catch (DataConversionException ex1) {
               LOGGER.log(Level.WARNING, ex1.getMessage(), ex1);
            }
        }
        //Leemos la distancia entre PTB y la ventana temporal
        Element distanciaEntrePTBM_XML = ultimoNodoDelPTBM; //root.getChild("DistanciaEntrePTBM");
        //Por motivos de compatibilidad hacia atas chequeamos si este PTBM
        //No tiene asociada esta informacion
        if (distanciaEntrePTBM_XML == null) {
            return ptbm;
        }

        try {
            ptbm.setInicioSoporteSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    EL_INICIO_SOPORTE).getFloatValue());
            ptbm.setInicioCoreSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    EL_INICIO_CORE).getFloatValue());
            ptbm.setFinCoreSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    EL_FIN_CORE).getFloatValue());
            ptbm.setFinSoporteSeparacion(distanciaEntrePTBM_XML.getAttribute(
                    EL_FIN_SOPORTE).getFloatValue());
        } catch (DataConversionException ex1) {
            LOGGER.log(Level.WARNING, ex1.getMessage(), ex1);
        }

        return ptbm;
    }

    /**
     * Devuelve un array con las restricciones de un Nodo Ptuo significativo.
     * @param PtoSig_xml
     * @return
     */
    private Restriccion[] generaRestricciones(Element PtoSig_xml) {
        //Obtengo la lista de elementos restricciones
        //Empleamos un filtro para deshacernos de los nodos de texto
        ContentFilter filtro = new ContentFilter(false);
        filtro.setElementVisible(true);
        @SuppressWarnings("unchecked")
        List<Element> lista_restriciones = PtoSig_xml.getContent(filtro);
        Iterator<Element> it = lista_restriciones.iterator();
        Restriccion[] restriccion_array = new Restriccion[lista_restriciones.size()];
        int num_restriciones = -1;
        while (it.hasNext()) {
            num_restriciones++;
            Element restriccion_xml = it.next();
            //Obtenemos los atributos de la restriccion
            int ptb_origen;
            int PtoSig_origen;
            boolean relativaAlBasal = false;
            try {
                ptb_origen = restriccion_xml.getAttribute("PTBOrigen").getIntValue();
                PtoSig_origen = restriccion_xml.getAttribute("PtoSigOrigen").getIntValue();
                //ptb_destino = restriccion_xml.getAttribute("PTBDestino").getIntValue();
                //Ptosig_destino = restriccion_xml.getAttribute("PtoSigDestino").getIntValue();
                Attribute atributoBoolean = restriccion_xml.getAttribute("RelativaAlBasal");
                if (atributoBoolean != null) {
                    relativaAlBasal = atributoBoolean.getBooleanValue();
                }
            } catch (DataConversionException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                return null;
            }

            //Empzamos a procesar las restricciones
            String[] D = new String[4];
            String[] L = new String[4];
            String[] M = new String[4];
            Restriccion.Semantica sintaxis_int;
            int unidadesTemporales;
            int cunatificadoSemantica;
            int distanciaEntrePtoSig;
            float magnitudPrimerPtoSig;
            float magnitudSegundoPtoSig;
            Element magnitud = restriccion_xml.getChild("Magnitud");
            Element temporal = restriccion_xml.getChild("Temporal");
            Element unidadesTemporalesElemento = restriccion_xml.getChild(EL_UNIDADES_TEMPORALES);
            Element pendiente = restriccion_xml.getChild("Pendiente");
            Element sintaxis = restriccion_xml.getChild("Semantica");
            Element infoUsuario = restriccion_xml.getChild("InformacionUsuario");
            try {
                D[0] = magnitud.getAttribute(EL_INICIO_SOPORTE).getValue();
                D[1] = magnitud.getAttribute(EL_INICIO_CORE).getValue();
                D[2] = magnitud.getAttribute(EL_FIN_CORE).getValue();
                D[3] = magnitud.getAttribute(EL_FIN_SOPORTE).getValue();

                L[0] = temporal.getAttribute(EL_INICIO_SOPORTE).getValue();
                L[1] = temporal.getAttribute(EL_INICIO_CORE).getValue();
                L[2] = temporal.getAttribute(EL_FIN_CORE).getValue();
                L[3] = temporal.getAttribute(EL_FIN_SOPORTE).getValue();

                M[0] = pendiente.getAttribute(EL_INICIO_SOPORTE).getValue();
                M[1] = pendiente.getAttribute(EL_INICIO_CORE).getValue();
                M[2] = pendiente.getAttribute(EL_FIN_CORE).getValue();
                M[3] = pendiente.getAttribute(EL_FIN_SOPORTE).getValue();

                sintaxis_int = Restriccion.Semantica.values()[sintaxis.getAttribute("Tipo").getIntValue()];
                Attribute cunatificadoSemanticaAtr = sintaxis.getAttribute("Cuantificador");
                //Chequeo introduciodo para mantener la compatibilidad hacia atras
                if (cunatificadoSemanticaAtr != null) {
                    cunatificadoSemantica = cunatificadoSemanticaAtr.getIntValue();
                } else {
                    cunatificadoSemantica = Restriccion.Cuantificador.TODO.ordinal();
                }
                if (unidadesTemporalesElemento != null) {
                    unidadesTemporales = unidadesTemporalesElemento.getAttribute("Tipo").getIntValue();
                } else {
                    //Asumimos que es un fichero antiguo y que estab en segundos
                    unidadesTemporales = Restriccion.UNIDADES.SEGUNDOS.ordinal();
                    //Pasamos a milisegundos la restriccion
                    for (int i = 0; i < L.length; i++) {
                        L[i] = Float.toString(MyFloat.parseFloatSeguro(L[i]) * 1000);
                        M[i] = Float.toString(MyFloat.parseFloatSeguro(M[i]) / 1000);
                    }

                }
                //Infomacion del usuario
                if (infoUsuario != null) {
                    magnitudPrimerPtoSig = infoUsuario.getAttribute("MagnitudPrimerPtoSig").getFloatValue();
                    magnitudSegundoPtoSig = infoUsuario.getAttribute("MagnitudSegundoPtoSig").getFloatValue();
                    distanciaEntrePtoSig = infoUsuario.getAttribute("DistanciaTemporal").getIntValue();
                } else {
                    magnitudPrimerPtoSig = 0;
                    magnitudSegundoPtoSig = 0;
                    distanciaEntrePtoSig = 0;
                }

            } catch (DataConversionException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                return null;
            }
            //Creamos la restriccion con la informacion
            restriccion_array[num_restriciones] = new Restriccion(ptb_origen,
                    PtoSig_origen, D, L, M, sintaxis_int, cunatificadoSemantica,
                    unidadesTemporales);
            restriccion_array[num_restriciones].setMagnitudPrimerPtoSig(magnitudPrimerPtoSig);
            restriccion_array[num_restriciones].setMagnitudSegundoPtoSig(magnitudSegundoPtoSig);
            restriccion_array[num_restriciones].setDistanciaTemporalEntrePtoSig(distanciaEntrePtoSig);
            restriccion_array[num_restriciones].setRelativaAlNivelBasal(relativaAlBasal);
        }

        return restriccion_array;
    }

    public static void main(String[] args) {
        PTBM2XML PTBM2XML1 = new PTBM2XML();

        javax.swing.JFileChooser fch = new javax.swing.JFileChooser();
        fch.showOpenDialog(null);

        /*
         Serializador serial = new Serializador();
         PTBMInterface ptbm = serial.cargaPTBM(fch.getSelectedFile().toString());

             PTBM2XML1.GuardaPTBM(ptbm, "C:/ptbm");
         */
        /**/
        PTBM2XML1.GuardaPTBM(PTBM2XML1.cargaPTBM("C:/ptbm"), "C:/ptbm2");
        /**/
    }

    public static PTBM2XML getInstancia() {
        if (ptbm2XML == null) {
            ptbm2XML = new PTBM2XML();
        }
        return ptbm2XML;
    }
}
