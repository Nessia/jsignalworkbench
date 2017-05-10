package es.usc.gsi.conversordatosmit.excepciones;


public class FechaFinalIgualInicialException extends FechasIncorrectasException {

    /**
    *
    */
   private static final long serialVersionUID = 2572177989601076204L;

   public FechaFinalIgualInicialException() {
        super("Las fechas inicial y final son iguales");
   }
}