package es.usc.gsi.conversordatosmit.ficheros;


import java.io.File;

class FicheroDat extends File {

    /**
    *
    */
    private static final long serialVersionUID = 7200314004530591915L;

    FicheroDat(String nombreFichero) {
        super(nombreFichero);
    }

//    FicheroDat(File f) {
//        this(f.getAbsolutePath());
//    }
} // Fin clase FicheroDat