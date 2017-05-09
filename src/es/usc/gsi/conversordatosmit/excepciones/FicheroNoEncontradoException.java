package es.usc.gsi.conversordatosmit.excepciones;

public class FicheroNoEncontradoException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 4232365781169349595L;

   public FicheroNoEncontradoException() {
        super("Error: no se encuentra el fichero");
   }
}