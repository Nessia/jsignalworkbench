package es.usc.gsi.trace.importer.perfil.auxiliares;

import java.io.*;
import java.util.jar.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.trace.importer.perfil.PTBMInterface;


public class Serializador {

    private static final Logger LOGGER = Logger.getLogger(Serializador.class.getName());

    public void guardaPTBM(String archivo, PTBMInterface ptbm) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(archivo);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(ptbm);
            out.close();

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (Exception e) {
                    LOGGER.log(Level.FINEST, e.getMessage(), e);
                }
            }
        }

    }

    public PTBMInterface cargaPTBM(String archivo) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(archivo);
            ObjectInputStream in = new ObjectInputStream(fis);
            PTBMInterface ptbm = (PTBMInterface) in.readObject();
            in.close();
            return ptbm;

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
           if(fis != null){
              try {
                  fis.close();
              } catch (Exception e) {
                  LOGGER.log(Level.FINEST, e.getMessage(), e);
              }
          }
      }
    }

//    public PTBMInterface cargaPTBM(String path, String archivo/*, boolean es_jar*/) {
//        if (path == null) {
//            return cargaPTBM(archivo);
//        }
//        JarInputStream jar = null;
//        JarFile jf = null;
//        try {
//            jar = new JarInputStream(new FileInputStream( path /*"Herramienta_Monitorizacion.jar"*/));
//            JarEntry entry = jar.getNextJarEntry();
//            while (!entry.getName().endsWith(archivo)) {
//                entry = jar.getNextJarEntry();
//            }
//
//            jf = new JarFile(path/*"Herramienta_Monitorizacion.jar"*/);
//            ObjectInputStream in = new ObjectInputStream(jf.getInputStream(
//                    entry));
//            PTBMInterface ptbm = (PTBMInterface) in.readObject();
//            in.close();
//            return ptbm;
//
//        } catch (Exception e) {
//            LOGGER.log(Level.WARNING, e.getMessage(), e);
//            return null;
//        } finally {
//            try {
//                if(jar != null){ jar.close();}
//            } catch (IOException e) {
//                LOGGER.log(Level.FINEST, e.getMessage(), e);
//            }
//
//            try {
//                if(jf != null){ jf.close();}
//            } catch (IOException e) {
//                LOGGER.log(Level.FINEST, e.getMessage(), e);
//            }
//        }
//
//    }

//    public PTBMInterface cargaPTBM(String archivo, boolean es_jar) {
//        JarInputStream jar = null;
//       JarFile jf = null;
//        try {
//            jar = new JarInputStream(new FileInputStream(
//                    "Herramienta_Monitorizacion.jar"));
//            JarEntry entry = jar.getNextJarEntry();
//            while (!entry.getName().endsWith(archivo)) {
//                entry = jar.getNextJarEntry();
//            }
//
//            jf = new JarFile("Herramienta_Monitorizacion.jar");
//            ObjectInputStream in = new ObjectInputStream(jf.getInputStream(
//                    entry));
//            PTBMInterface ptbm = (PTBMInterface) in.readObject();
//            in.close();
//            return ptbm;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                if(jar != null){ jar.close();}
//            } catch (IOException e) {}
//
//            try {
//                if(jf != null){ jf.close();}
//            } catch (IOException e) {}
//        }
//    }

}