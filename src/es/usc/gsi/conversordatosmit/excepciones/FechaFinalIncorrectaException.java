package es.usc.gsi.conversordatosmit.excepciones;

public class FechaFinalIncorrectaException extends FechasIncorrectasException {

    /**
    *
    */
   private static final long serialVersionUID = 7286025407257146992L;

   public FechaFinalIncorrectaException() {
        super("La fecha final tiene un formato incorrecto");
   }
}