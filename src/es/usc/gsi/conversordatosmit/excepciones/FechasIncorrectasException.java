package es.usc.gsi.conversordatosmit.excepciones;


public class FechasIncorrectasException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 5744317946076914412L;

   public FechasIncorrectasException() {
        super("Error: alguna de las dos fechas o ambas estan mal");
   }
}