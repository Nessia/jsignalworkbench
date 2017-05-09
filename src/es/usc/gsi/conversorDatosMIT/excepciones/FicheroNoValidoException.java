package es.usc.gsi.conversorDatosMIT.excepciones;


public class FicheroNoValidoException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = -8576099556109885615L;

   public FicheroNoValidoException() {
        super("Formato de fichero incorrecto o fichero danhado");
   }
}
