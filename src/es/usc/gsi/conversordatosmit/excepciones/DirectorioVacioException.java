package es.usc.gsi.conversordatosmit.excepciones;


public class DirectorioVacioException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = -1003376150451221667L;

   public DirectorioVacioException() {
        super("Directorio vacio");
   }

}