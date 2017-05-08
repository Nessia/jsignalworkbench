package es.usc.gsi.conversorDatosMIT.excepciones;


public class NoPacienteAbiertoException extends Exception {

    /**
    *
    */
   private static final long serialVersionUID = -8468899839161666521L;

   public NoPacienteAbiertoException() {
        super("No se ha abierto la carpeta de un paciente");
    }
}