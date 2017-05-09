package es.usc.gsi.conversordatosmit.excepciones;

public class FechaInicialMenorOriginalException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = -6753568540680629719L;

   public FechaInicialMenorOriginalException() {
        super("La fecha inicial es menor que la original");
   }
}