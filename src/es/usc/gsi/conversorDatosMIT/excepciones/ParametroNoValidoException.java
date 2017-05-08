package es.usc.gsi.conversorDatosMIT.excepciones;


public class ParametroNoValidoException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = -1387127575791147126L;

   public ParametroNoValidoException() {
        super("Parametro incorrecto");
    }
}