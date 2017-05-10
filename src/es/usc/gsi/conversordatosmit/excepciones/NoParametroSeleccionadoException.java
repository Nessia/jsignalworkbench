package es.usc.gsi.conversordatosmit.excepciones;


public class NoParametroSeleccionadoException extends NoExportableException {

    /**
    *
    */
   private static final long serialVersionUID = 551057132765758442L;

   public NoParametroSeleccionadoException() {
        super("No se ha seleccionado ningun parametro para exportar");
   }
}