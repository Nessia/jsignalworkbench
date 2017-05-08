package es.usc.gsi.conversorDatosMIT.excepciones;


public class NoParametroSeleccionadoException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = 551057132765758442L;

   public NoParametroSeleccionadoException() {
        super("No se ha seleccionado ningun parametro para exportar");
    }
}