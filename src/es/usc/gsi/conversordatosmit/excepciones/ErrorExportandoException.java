package es.usc.gsi.conversordatosmit.excepciones;


public class ErrorExportandoException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 5336288765156452029L;

   public ErrorExportandoException() {
        super("Error exportando archivos");
   }
}