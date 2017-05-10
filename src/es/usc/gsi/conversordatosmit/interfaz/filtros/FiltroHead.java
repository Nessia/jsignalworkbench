package es.usc.gsi.conversordatosmit.interfaz.filtros;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroHead extends FileFilter {

    @Override
    public boolean accept(File f) {
//        if (f.isDirectory()) {
//            return true;
//        }
//        if (f.getName().indexOf(".hea") == -1 &&
//            f.getName().indexOf(".HEA") == -1 ) {
//            return false;
//        }
//        return true;
       return f.isDirectory() || f.getName().endsWith(".hea") ||
             f.getName().endsWith(".HEA");
    }

    @Override
    public String getDescription() {
        return "Ficheros .hea";
    }

} // Fin clase FiltroHead