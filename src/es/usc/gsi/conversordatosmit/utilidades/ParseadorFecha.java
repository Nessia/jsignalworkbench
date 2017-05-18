package es.usc.gsi.conversordatosmit.utilidades;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ParseadorFecha {


   private static final Logger LOGGER = Logger.getLogger(ParseadorFecha.class.getName());
// Argumento con formato de fecha de tipo hispano: dd/mm/yyyy hh:mm:ss
// Devuelve el numero de segundos desde 1-1-1970
    private static final Locale FORMATO_FECHA_LOCAL = new Locale("es", "ES"); // Formato de fecha local a Espanha
    //"dd/MM/yyyy HH:mm:ss"
    private static final DateFormat CONVERSOR_A_DATE = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,
          DateFormat.MEDIUM, FORMATO_FECHA_LOCAL);

    private ParseadorFecha(){
       // Esconder constructor
    }


    // Devuelve el numero de segundos desde 1-1-1970 00:00:00
    private static long convierteASegundos(String fecha) {
        long res = 0;
        try {
            Date f = CONVERSOR_A_DATE.parse(fecha); // Convertimos en un objeto Date
            res = f.getTime() / 1000; // Convertimos el tiempo en milisegundos a segundos
        } catch (ParseException e) {
           LOGGER.log(Level.WARNING, "Imposible convertir a fecha", e);
        }

        return res;
    }

//////////////////

    public static String calculaFechaFinal(String fechaInicial,
                                           long segundosASumar) {
        long fechaInicialSegundos = convierteASegundos(fechaInicial);
        long totalSegundos = fechaInicialSegundos + segundosASumar;
        Date fechaFinal = new Date(totalSegundos * 1000); // Argumento en milisegundos
        return CONVERSOR_A_DATE.format(fechaFinal);
    }


    public static long calculaDiferencia(String fechaInicial, String fechaFinal) {
        long res = 0;
        try {
            Date fInicial = CONVERSOR_A_DATE.parse(fechaInicial);
            Date fFinal = CONVERSOR_A_DATE.parse(fechaFinal);
            res = (fFinal.getTime() - fInicial.getTime()) / 1000;
        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, "Imposible calcular la diferencia", e);
        }
        return res;

    }

// Elimina los milisegundos de la fecha y la devuelve en formato dd/mm/yyyy hh:mm:ss
// MEJORAR: DEBE DETECTAR SI SE LE PASA UNA FECHA QUE REALMENTE ES EN FORMATO MITDB
    public static String convierteAFormatoHispano(String fechaMITDB) {

        String res = "";

        String[] hora_dia = ParseadorCadena.split(fechaMITDB, " ");
        String dia = hora_dia[1];
        String hora = hora_dia[0];

        String[] horaParseada = ParseadorCadena.split(hora, "."); // Separamos los milisegundos
        String horaSinMilis = horaParseada[0]; // Hora sin milisegundos

        try {
            Date fFormateada = CONVERSOR_A_DATE.parse(dia + " " + horaSinMilis);
            res = CONVERSOR_A_DATE.format(fFormateada);
        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, "Imposible convertir a formato hispano", e);
        }

        return res;

    }

    ///////////77

    public static void verificaFecha(String fecha) throws ParseException {

        try {
            CONVERSOR_A_DATE.parse(fecha);
        } catch (ParseException e) {
            throw e;
        }

    }


} // Fin clase ParseadorFecha
