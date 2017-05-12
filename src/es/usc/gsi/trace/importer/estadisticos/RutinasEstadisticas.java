package es.usc.gsi.trace.importer.estadisticos;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class RutinasEstadisticas {
    //public static final int MAYOR_QUE_90 = 1, MAYOR_QUE_95 = 2, MAYOR_QUE_975 =
    //        3, MAYOR_QUE_99 = 4, MAYOR_QUE_995 = 5, MENOR_QUE_90 = 0;
     public enum MAYORES { MENOR_QUE_90, MAYOR_QUE_90, MAYOR_QUE_95, MAYOR_QUE_975, MAYOR_QUE_99, MAYOR_QUE_995 }

    /**
     * Simple calculo de una media artimetica.
     * @param datos
     * @return
     */
    public final static float calculaMedia(float[] datos) {
        double suma = 0;
        for (int i = 0; i < datos.length; i++) {
            suma += datos[i];
        }
        return (float) suma / datos.length;
    }

    /**
     * Calculo de una mediana y de los percentiles. Se devueleven en un mimo arrya de
     * floats, el primero es la mediana, y el resto los percentiles que se solicitarion.
     * @param datos
     * @return
     */
    public final static float[] calculaMediana(float[] datos, int[] percentiles) {
       int[] percentilesClon = (int[]) percentiles.clone();
        TreeSet<Float> numOrdenados = new TreeSet<Float>();

        percentilesClon = ordenaPercentiles(percentilesClon);

        for (int i = 0; i < datos.length; i++) {
            Float dato = datos[i];
            while (numOrdenados.contains(dato)) {
                //Megachapuza, como si hay dos iguales => los fund een uno => los hago diferentes
                float datoActual = dato.floatValue();
                //Si no el truquillo me da = + 0/10000000 = 0, y asi hasta el infinito
                if (datoActual == 0) {
                    datoActual = Float.MIN_VALUE;
                }
                //Mas chapuzas. Supongo que nunca pasara. si lo de abajo no actualiza el numero =>
                if (Math.abs(datoActual) < Float.MIN_VALUE * 10000000) {
                    datoActual = datoActual + Float.MIN_VALUE;
                    dato = datoActual;
                } else {
                    dato = datoActual + datoActual / 10000000;
                }
            }
            numOrdenados.add(dato);
        }

        int num_datos = datos.length;

        //Divido entre dos todos los datos de los percentiles, ya que la distribucion es supuestamente simetrica,
        //Y los multiplico por el numero de datos totales, por el percentil en cuestion y divido por 100
        for (int i = 0; i < percentilesClon.length; i++) {
            percentilesClon[i] = percentilesClon[i] * num_datos / (100);
        }

        return percentiles(num_datos, percentilesClon, numOrdenados);
    }

    private static float[] percentiles(int num_datos, int[] percentilesClon, TreeSet<Float> numOrdenados){
       float mediana = 0;
       int cuantos_van = 0;
       boolean numero_datos_par;
       int mitad_de_los_datos = num_datos / 2;
       //Miro si el numero de datos es par
       numero_datos_par = (num_datos % 2 == 0);
       //Esta varibale me permitira ir almacenando correctamente los percentiles
       int cuantos_percentiles_van = 0;
       float[] valor_de_los_percentiles = new float[percentilesClon.length];
       //Este flg nos indica cuando hemosm hallado la media
       boolean aun_no_hallada_mediana = true;
       boolean avanzo = false;
        Iterator<Float> it = numOrdenados.iterator();
       //Cuando hallemos la media tambien hemos tenido que hallar todos los percentiles, por lo que acabasmos.
       while (it.hasNext() && (aun_no_hallada_mediana ||
               cuantos_percentiles_van < percentilesClon.length)) {
           cuantos_van++;
           //Para saber si el valor fue empleado por un percentil, la mediana o no fue empleado
           avanzo = false;
           for (int i = cuantos_percentiles_van; !avanzo && i < percentilesClon.length; i++) {
               if (cuantos_van == percentilesClon[i]) {
                   valor_de_los_percentiles[i] = it.next().floatValue();
                   cuantos_percentiles_van++;
                   avanzo = true;
               }
           }
           if (numero_datos_par && mitad_de_los_datos == cuantos_van) {
               mediana = it.next();
               aun_no_hallada_mediana = false;
           } else if (!numero_datos_par && mitad_de_los_datos == cuantos_van) {
               mediana = it.next();
               mediana += it.next();
               mediana /= 2;
               aun_no_hallada_mediana = false;
           }
           //Si nadie cogio este valor para nada
           else if (!avanzo) {
               it.next();
           }

       }
       float[] resultado = new float[percentilesClon.length + 1];
       resultado[0] = mediana;
       for (int i = 0; i < percentilesClon.length; i++) {
           resultado[i + 1] = valor_de_los_percentiles[i];
       }

       return resultado;
    }

    /**
     * ordena el array que se le pasa de menor a mayor, devolviendo un nuevo array, sin haber alterado el inicial.
     * @param percentiles
     * @return
     */
    public static final int[] ordenaPercentiles(int[] percentiles) {
        boolean cambio;
        int[] percentilesClon = (int[]) percentiles.clone();

        do {
            cambio = false;
            for (int i = 1; i < percentilesClon.length; i++) {
                if (percentilesClon[i - 1] > percentilesClon[i]) {
                    int tmp = percentilesClon[i - 1];
                    percentilesClon[i - 1] = percentilesClon[i];
                    percentilesClon[i] = tmp;
                    cambio = true;
                }
            }
        } while (cambio);
        return percentilesClon;
    }

    /**
     * Se le pasan los datos y  su media y calcula la vaianza
     * @param datos
     * @param media
     * @return
     */
    public static final float calculaVarianza(float[] datos, float media) {
        double sumatorio = 0;
        int num_datos = datos.length;
        for (int i = 0; i < datos.length; i++) {
            sumatorio += Math.pow(datos[i] - media, 2) / num_datos;
        }
        return (float) sumatorio;
    }

    /**
     * Se le pasa la varianza y calcula la desviacion tipica.
     * @param varianza
     * @return
     */
    public static final float calculaDesviacionTipica(float varianza) {
        return (float) Math.sqrt(varianza);
    }

    /**
     * Se le pasa la varianza y el numero de datos calcula el error estandar.
     * @param varianza
     * @param num_datos
     * @return
     */
    public static final float calculaErrorEstandar(float varianza,
            int num_datos) {
        return (float) Math.sqrt(varianza / (num_datos - 1));
    }

    /**
     * Se le pasa la varianza, numero de datios y la media, y calcual el intervalo de confianza del 95%.
     * @param media
     * @param varianza
     * @param num_datos
     * @return
     */
    public static final float[] calculaIntervaloDeConfianza(float varianza,
            int num_datos, float media) {
        //1.96 es para un intervalo de confianza del 95%
        return calculaIntervaloDeConfianza(varianza, num_datos, media, 1.96F);
    }

    /**
     * Se le pasa la varianza, numero de datios, la media, y el parametro z sub alfa
     * y calcual el intervalo de confinaza del parametro z sub alfa correspondiente.
     * @param media
     * @param varianza
     * @param num_datos
     * @return
     */
    public static final float[] calculaIntervaloDeConfianza(float varianza,
            int num_datos,
            float media, float Z_alfa_medios) {

        float[] resultado = new float[2];
        resultado[0] = (float) (media -
                                Math.sqrt(varianza) * Z_alfa_medios /
                                (Math.sqrt(num_datos)));
        resultado[1] = (float) (media +
                                Math.sqrt(varianza) * Z_alfa_medios /
                                (Math.sqrt(num_datos)));
        return resultado;
    }

    /**
     * Se le pasa la varianza y el la media y calcula el cociente de variacion.
     * @param varianza
     * @param media
     * @return
     */
    public static final float calculaCocienteDeVariacion(float varianza,
            float media) {
        return varianza / media;
    }

    /**
     * Calcula la correlacion entre los dos vectores que se le pasan. Si el boolean es true
     * se fuerza a calcualr la correlacion aun cuando los vectores sean de distintas
     * longitudes.
     * @param datos1
     * @param datos2
     * @param fuerza
     * @return
     * @throws VarianzaException
     */
    public static final float calculaCorrelacion(float[] datos1, float[] datos2,
                                                 boolean fuerza) throws
            CorrelacionException {
        //Punteros que empleare para denominar a datos 1 y dos una vez que, en caso de ser necesrio,
        //Los halla ajustado para tener el mismo tamanho
        float[] datos_t_1;
        float[] datos_t_2;
        int longitud1 = datos1.length;
        int longitud2 = datos2.length;
        //Si no son iguales los vextores y no hayq ue formzar => se lanza excepcion.
        if (longitud1 != longitud2 && !fuerza) {
            throw new CorrelacionException(
                    "Los arrays tienen longitudes diferentes: " + datos1.length +
                    " " +
                    datos2.length, datos1.length, datos2.length);
        }

        //Si son distintos y hay que forzar:
        if (longitud1 != longitud2) {
            if (longitud1 > longitud2) {
                //datos_t_2 se queda tal cual
                datos_t_2 = datos2;
                //datos_t_1 seran los primeros elementos de datos1
                datos_t_1 = new float[datos2.length];
                for (int i = 0; i < datos2.length; i++) {
                    datos_t_1[i] = datos1[i];
                }
                //si no => al reves
            } else {
                //datos_t_1 se queda tal cual
                datos_t_1 = datos1;
                //datos_t_1 seran los primeros elementos de datos1
                datos_t_2 = new float[datos1.length];
                for (int i = 0; i < datos1.length; i++) {
                    datos_t_2[i] = datos2[i];
                }
            }
        }
        //Si los vectores ya son iguales => simplemente les cambio el nombre
        else {
            datos_t_1 = datos1;
            datos_t_2 = datos2;
        }
        return calculaCorrelacion(datos_t_1, datos_t_2);
    }

    /**
     * Calcula la correlacion entre los dos vectores que se le pasan. Han de ser del mimo tamanho.
     * longitudes.
     * @param datos1
     * @param datos2
     * @return
     */
    private static final float calculaCorrelacion(float[] datos1,
                                                  float[] datos2) {
        float media1 = calculaMedia(datos1);
        float media2 = calculaMedia(datos2);
        int nunm_datos = datos1.length;
        //Hacemos el calculo
        double divisor = 0;
        double primer_cuadrado = 0;
        double segundo_cuadrado = 0;
        for (int i = 0; i < nunm_datos; i++) {
            divisor += (datos1[i] * datos2[i]);
            primer_cuadrado += datos1[i] * datos1[i];
            segundo_cuadrado += datos2[i] * datos2[i];
        }
        divisor = divisor - nunm_datos * media1 * media2;

        double dividendo = Math.sqrt((primer_cuadrado -
                                      nunm_datos * media1 * media1) *
                                     (segundo_cuadrado -
                                      nunm_datos * media2 * media2));
        double coeficiente_r = divisor / dividendo;
        return (float) coeficiente_r;

    }

    /**
     * Elimina los 0 finales de las senhales
     * @param datos
     * @return
     */
    public static float[] elimina0Finales(float[] datos) {
        int dato_de_corte = datos.length;
        for (int i = datos.length - 1; i >= 0; i--) {
            if (datos[i] != 0) {
                break;
            }
            dato_de_corte--;
        }
        if (dato_de_corte != datos.length) {
            float[] resultado = new float[dato_de_corte];
            for (int i = 0; i < dato_de_corte; i++) {
                resultado[i] = datos[i];
            }
            return resultado;
        }
        return (float[]) datos.clone();
    }

    /**
     * Devuelve un entero que indica el nivel de significacion de la correlacion.
     * @param correlacion
     * @param NUM_DATOS EL MINIMO NUMERO DE DATOS DE LOS DOS NUMEROS DE DATOS CON LOS QUE SE CALCULO LA CORRELACION
     * @return
     */
    public static MAYORES caclulaNivelesDeSignificacion(float correlacion,
            int num_datos) {
        float logaritmo = (float) Math.log((1 + correlacion) / (1 - correlacion));
        double coef = Math.sqrt(num_datos - 3.0) / 2 * logaritmo;
        float coeficiente = (float) Math.abs(coef);

        if (coeficiente > 2.58) {
            return MAYORES.MAYOR_QUE_995;
        } else if (coeficiente > 2.33) {
            return MAYORES.MAYOR_QUE_99;
        } else if (coeficiente > 1.96) {
            return MAYORES.MAYOR_QUE_975;
        } else if (coeficiente > 1.64) {
            return MAYORES.MAYOR_QUE_95;
        } else if (coeficiente > 1.28) {
            return MAYORES.MAYOR_QUE_90;
        } else {
            return MAYORES.MENOR_QUE_90;
        }

    }

    public static String getTextoDeSignificacion(MAYORES significacion) {
        switch (significacion) {
        case MENOR_QUE_90:
            return "no es significativo";
        case MAYOR_QUE_90:
            return "con un nivel de 90%";
        case MAYOR_QUE_95:
            return "con un nivel de 95%";
        case MAYOR_QUE_975:
            return "con un nivel de 97.5%";
        case MAYOR_QUE_99:
            return "con un nivel de 99%";
        default:
            return "con un nivel de 99.5%";
        }

    }

}
