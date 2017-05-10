package es.usc.gsi.trace.importer.perfil.auxiliares;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFloat {

    private static final Logger LOGGER = Logger.getLogger(MyFloat.class.getName());

    private static boolean hayParser = false;
    private static DecimalFormat decimalFormat;
    private static int numeroDecimalesActuales = 3;

    private MyFloat(){
       // Hide constructor
    }

    /**
     *
     */
    private static void contruyePraser() {
        if (!hayParser) {
            Locale default_locale = Locale.getDefault();
            //Ponemos como localidad la inglesa, pa que pille . en vez de ,
            Locale.setDefault(new Locale("en", "GB"));
            decimalFormat = new DecimalFormat("###.###");
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
    public static float parseFloat(String numero) throws Exception {
        try {
            if (!("&".equals(numero)) && !("-&".equals(numero))) {
                return Float.parseFloat(numero);
            } else if ("&".equals(numero)) {
                return Integer.MAX_VALUE / 1000F;
            } else {
                return Integer.MIN_VALUE / 1000F; //Por que si no en validar dan overflow
            }
        } catch (NumberFormatException ex) {
            throw (new Exception("Numero Mal formado"));
        }
    }

    /**
     *
     * @param f
     * @return
     */
    public static String formateaNumero(float f) {
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

    /**
     *
     * @param M
     * @return
     */
//    private static String convierteAString(float M) {
//        if (M != Float.NEGATIVE_INFINITY && M != Float.POSITIVE_INFINITY) {
//            return Float.toString(M);
//        } else if (M == Float.NEGATIVE_INFINITY) {
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
