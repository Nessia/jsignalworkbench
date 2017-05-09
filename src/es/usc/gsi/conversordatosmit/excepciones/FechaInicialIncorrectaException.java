package es.usc.gsi.conversordatosmit.excepciones;


public class FechaInicialIncorrectaException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 4400268190698879391L;

   public FechaInicialIncorrectaException() {
        super("Fecha inicial con formato incorrecto");
   }
}