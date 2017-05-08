package es.usc.gsi.conversorDatosMIT.ficheros;


import java.io.File;

public class FicheroDat extends File {

    /**
    *
    */
   private static final long serialVersionUID = 7200314004530591915L;

   public FicheroDat(String nombreFichero) {
        super(nombreFichero);
    }

    public FicheroDat(File f) {
        this(f.getAbsolutePath());
    }
} // Fin clase FicheroDat