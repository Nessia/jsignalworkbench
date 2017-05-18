package es.usc.gsi.conversordatosmit.ficheros.escritura;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.conversordatosmit.ficheros.FicheroHead;
import es.usc.gsi.conversordatosmit.ficheros.Parametro;
import es.usc.gsi.conversordatosmit.utilidades.ParseadorCadena;

class EscribeCabeceraHead_ASCII {

    private static final Logger LOGGER = Logger.getLogger(EscribeCabeceraHead_ASCII.class.getName());

    private List<FicheroHead> vectorFicherosHead;
    private File ficheroDestinoCabecera;

    EscribeCabeceraHead_ASCII(List<FicheroHead> vectorFicherosHead, File ficheroDestino) {

        // Preprocesado del nombre de fichero para generar el nombre del .cxp
        if (ficheroDestino.getName().indexOf(".txt") != -1) { // Si ya tiene extension .txt
            // SOLUCION PROVISIONAL: FALLARA SI EL FICHERO TIENE .ALGO Y .TXT AL FINAL.
            String[] nombreSinExtension = ParseadorCadena.split(ficheroDestino.getName(), ".");
            String pathSinExtension = ficheroDestino.getParent() + File.separator + nombreSinExtension[0];
            this.ficheroDestinoCabecera = new File(pathSinExtension + ".cxp");
        } else { // Si no tiene extension .txt
            this.ficheroDestinoCabecera = new File(ficheroDestino.getAbsolutePath() + ".cxp");
        }

        // Vector de ficheros de cabecera
        this.vectorFicherosHead = vectorFicherosHead;

        this.vuelcaDatos();

    }

    private void vuelcaDatos() {

        FileWriter fw = null;
        BufferedWriter salida = null;
        int numColumna = 0;

        try {
            fw = new FileWriter(ficheroDestinoCabecera);
            salida = new BufferedWriter(fw);
        } catch (Exception e) {
           LOGGER.log(Level.WARNING, "Error al crear fichero de cabecera en exportacion", e);
        }

        try {
            // Lectura de los ficheros.
            for (FicheroHead fh : vectorFicherosHead) {
                Parametro[] parG = fh.getParametros();

                for (int j = 0; j < parG.length; j++) {
                    if (parG[j].getActivado()) {
                        numColumna++;
                        salida.write("[Columna" + numColumna + "]\n");
                        salida.write("nombreMarco=" + fh.getNombreFrame() +
                                     "\n");
                        //    salida.write("frecuenciaMuestreoMarco=" + fh.getFrecuenciaMuestreoFrame() + "\n");
                        salida.write("fechaInicio=" + fh.getFechaInicio() +
                                     "\n");
                        // salida.write("horaInicio=" + fh.getHoraInicio() + "\n");
                        salida.write("ficheroDatos=" + fh.getAbsolutePath() +
                                     "\n");
                        salida.write("nombreVariable=" +
                                     parG[j].getNombreParametro() + "\n");
                        salida.write(
                                "frecuenciaMuestreoOriginal=" +
                                Float.toString(parG[j].
                                               getBackupFrecuenciaMuestreo()) +
                                "\n"
                                );
                        salida.write(
                                "frecuenciaMuestreoVolcado=" +
                                Float.toString(parG[j].getFrecuenciaMuestreo()) +
                                "\n"
                                );
                        salida.write("\n");
                        salida.flush();

                    } // Fin if

                } // Fin bucle j

            } // Fin bucle i

        } catch (Exception e) {
           LOGGER.log(Level.SEVERE, "Error al volcar datos de fichero de cabecera exportacion", e);
        }

        try {
            salida.close();
            fw.close();
        } catch (Exception e) {
           LOGGER.log(Level.WARNING, "Error al cerrar fichero de cabecera", e);
        }

    }

} // Fin EscribeCabeceraHead
