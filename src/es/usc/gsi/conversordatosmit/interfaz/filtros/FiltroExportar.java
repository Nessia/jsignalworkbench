package es.usc.gsi.conversordatosmit.interfaz.filtros;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroExportar extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f.getName().indexOf(".txt") == -1) {
            return false;
        }
        return true;

    }

    @Override
    public String getDescription() {
        return "Ficheros .txt";
    }

} // Fin clase FiltroExportar
