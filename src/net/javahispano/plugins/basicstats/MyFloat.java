package net.javahispano.plugins.basicstats;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFloat {


    private static final Logger LOGGER = Logger.getLogger(MyFloat.class.getName());

    private static boolean hayParser = false;
    private static DecimalFormat decimalFormat;
//    private static int numeroDecimalesActuales = 3;

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
     *
     * @param numero
     * @return
     * @throws Exception
     */
    private static float parseFloat(String numero) throws Exception {
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
    public static String formateaNumero(String numeroStr) {
        if (!hayParser) {
            contruyePraser();
        }
        String numero = numeroStr.trim();
        if ("&".equals(numero) || "-&".equals(numero)) {
            return numero;
        }

        try {
            return decimalFormat.format(parseFloat(numero));

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
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
//    public static void setNumeroDecimales(int numero_decimales) {
//        if (!hayParser) {
//            contruyePraser();
//        }
//        StringBuilder pattern = new StringBuilder("###");
//        if (numero_decimales > 0) {
//            pattern.append(".");
//            for (int i = 0; i < numero_decimales; i++) {
//                pattern.append("#");
//            }
//
//        }
//        numeroDecimalesActuales = numero_decimales;
//        decimalFormat.applyPattern(pattern.toString());
//    }

    /**
     *
     * @return
     */
//    public static int getNumeroDecimalesActuales() {
//        return numeroDecimalesActuales;
//    }
}
