package es.usc.gsi.conversorDatosMIT.excepciones;


public class FechaFinalIgualInicialException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 2572177989601076204L;

   public FechaFinalIgualInicialException() {
        super("Las fechas inicial y final son iguales");
   }
}