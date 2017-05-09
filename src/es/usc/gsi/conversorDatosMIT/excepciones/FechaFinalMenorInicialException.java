package es.usc.gsi.conversorDatosMIT.excepciones;


public class FechaFinalMenorInicialException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = -8700152627110279820L;

   public FechaFinalMenorInicialException() {
        super("La fecha final es menor que la inicial");
   }
}