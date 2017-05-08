package es.usc.gsi.conversorDatosMIT.excepciones;

public class FechaFinalIncorrectaException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 7286025407257146992L;

   public FechaFinalIncorrectaException() {
        super("La fecha final tiene un formato incorrecto");
    }
}