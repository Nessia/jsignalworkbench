package es.usc.gsi.conversordatosmit.utilidades;


import java.util.StringTokenizer;

public class ParseadorCadena {

    private ParseadorCadena(){
       // Esconder constructor
    }

    public static String[] split(String cadena, String separador) {

        // Rompemos la cadena en tokens
        StringTokenizer strToken = new StringTokenizer(cadena, separador);
        String[] resultado = new String[strToken.countTokens()];

        ///for (int i = 0; i<resultado.length; strToken.hasMoreTokens(); i++) {
        for (int i = 0; i<resultado.length; i++) {
            resultado[i] = strToken.nextToken();
        }

        return resultado;
    }
} // Fin ParseadorCadena