package es.usc.gsi.conversordatosmit.excepciones;


public class FechaFinalMayorOriginalException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 899902820273399709L;

   public FechaFinalMayorOriginalException() {
        super("La fecha final es mayor que la original");
   }
}