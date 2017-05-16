/* TODO Esta clase esta bastante mal planteada: REFORMAR.
 Tiene una estructura "rara": todo se hace desde el constructor
 */
package es.usc.gsi.conversordatosmit.ficheros.escritura;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import es.usc.gsi.conversordatosmit.ficheros.*;
import es.usc.gsi.conversordatosmit.ficheros.lectura.LeeFicheroDat;
import es.usc.gsi.conversordatosmit.interfaz.ControladorInterfaz;

public class EscribeHead_ASCII extends Thread implements Cancelar {

    /**
     *
     */
    private static final long serialVersionUID = 280327071583524748L;

    private static final Logger LOGGER = Logger.getLogger(EscribeHead_ASCII.class.getName());

    private List<FicheroHead> vectorFicherosHead;
    private File ficheroDestino;
    private LeeFicheroDat[] arrayLectores;
    private ControladorFicheros controlFicheros = ControladorFicheros.getControlador();
    private ControladorInterfaz controlInterfaz = ControladorInterfaz.getControlador();
    private boolean cancel = false;

//*******************************************************************************

    // FALTA: CREAR UN CONSTRUCTOR QUE ADMITA UNA FRECUENCIA DE REMUESTREO GLOBAL.
    public EscribeHead_ASCII(List<FicheroHead> vectorFicherosHead, File ficheroDestino) {
         this.vectorFicherosHead = vectorFicherosHead;

         // Correccion del nombre de fichero
         if (ficheroDestino.getName().endsWith(".txt")) {
             this.ficheroDestino = ficheroDestino;
         } else {
             this.ficheroDestino = new File(ficheroDestino.getAbsolutePath() + ".txt");
         }
     }

//*******************************************************************************

    @Override
    public void cancelar() {
        cancel = true;
    }

//*******************************************************************************

    @Override
    public void run() {
        new EscribeCabeceraHead_ASCII(vectorFicherosHead, ficheroDestino);
        this.creaLectores();
        if (this.vuelcaDatos()) {
            return;
        }
        this.cierraFicheros();
        controlFicheros.cierraIndicadorProgreso();
    }

//*******************************************************************************

     private void creaLectores() {
         FicheroHead fhTemp;
         List<LeeFicheroDat> vectorLectores = new ArrayList<LeeFicheroDat>();

         for (int i = 0; i < vectorFicherosHead.size(); i++) {
             fhTemp = vectorFicherosHead.get(i);
             Parametro[] parG = fhTemp.getParametros();

             for (int j = 0; j < parG.length; j++) {
                 if (parG[j].getActivado()) { // Solo se crean lectores para ficheros con algun parametro activado para volcar.
                     LeeFicheroDat lfd = new LeeFicheroDat(fhTemp, parG[j]);
                     //      LeeFicheroDat lfd = new LeeFicheroDat( fhTemp, parG[j], 250.0F );
                     vectorLectores.add(lfd);
                 }
             }
         }

         // Creacion del array, una vez seleccionados todos los ficheros y parametros para volcar.
         arrayLectores = new LeeFicheroDat[vectorLectores.size()];

         for (int i = 0; i < arrayLectores.length; i++) {
             arrayLectores[i] = vectorLectores.get(i);
         }

     }

//*******************************************************************************

     private boolean vuelcaDatos() {
         //LeeFicheroDat lfdTemp = null;
         StringBuilder lineaVolcado = new StringBuilder("");
         BufferedWriter salida = null;
         FileWriter fw = null;
         String datoLeido = "";
         int numeroLectoresFinalizados = 0;
         String separador;

         try {
             fw = new FileWriter(ficheroDestino);
             salida = new BufferedWriter(fw);
         } catch (Exception e) {
             LOGGER.log(Level.SEVERE,"Error al crear fichero de exportacion", e);
             controlFicheros.cierraIndicadorProgreso();
             JOptionPane.showMessageDialog(controlInterfaz.getPanelPrincipal(),
                                           "Error al crear fichero de exportacion");
//      throw new ErrorExportandoException();
         }
         // Abre todos los ficheros.
         for (int i = 0; i < arrayLectores.length; i++) {
             arrayLectores[i].abreFichero();
         }

         // Comienza la lectura de datos y su volcado.

         for (int j = 0; numeroLectoresFinalizados < arrayLectores.length; j++) {
             if (cancel) {
                 controlFicheros.cierraIndicadorProgreso();
                 try {
                     salida.close();
                     fw.close();
                 } catch (Exception e) {
                     LOGGER.log(Level.SEVERE, "Error al cerrar fichero de salida", e);
                 }
                 /*boolean res = */ficheroDestino.delete();
                 String fichName = ficheroDestino.getName();
                 File fichCXP = new File(ficheroDestino.getParent() +
                                         File.separator +
                                         fichName.substring(0,
                         fichName.indexOf(".txt")) + ".cxp");
                 /*res = */fichCXP.delete();
                 return true;
             }
//    controlFicheros.notificaProgreso(j);
             numeroLectoresFinalizados = 0; // Si no han finalizado todos, empezamos desde 0.
             for (int i = 0; i < arrayLectores.length; i++) {
                 if (i < (arrayLectores.length - 1)) {
                     separador = "\t";
                 } else {
                     separador = "\n";
                 }

                 try {

                     datoLeido = arrayLectores[i].getSiguiente();
                     if (datoLeido == null) {
                         lineaVolcado.append(separador);
                         numeroLectoresFinalizados++;
                     } else {
                         lineaVolcado.append(datoLeido + separador);
                     }

                     //  System.out.println(lineaVolcado);
                 } catch (Exception e) {
                     LOGGER.log(Level.SEVERE, "Error al volcar linea", e);
                 }

             } // Fin for i

             try {
                 salida.write(lineaVolcado.toString());
                 salida.flush();
                 lineaVolcado = new StringBuilder(""); //lineaVolcado = "";
             } catch (Exception e) {
                 LOGGER.log(Level.SEVERE, "Error al escribir linea", e);
             }

             controlFicheros.notificaProgreso(j);
//System.out.println("Progreso: " + j);
         } // Fin for j

         // Cerrado de archivos de lectura y escritura.
//  for(int i=0; i< arrayLectores.length; i++) arrayLectores[i].cierraFichero();

         try {
             salida.close();
             fw.close();
         } catch (Exception e) {
             LOGGER.log(Level.SEVERE, "Error al cerrar fichero de salida", e);
         }

         return false;
     } // Fin vuelcaDatos


//*******************************************************************************

// INCLUIR METODO PARA CERRAR TODOS LOS FICHEROS ABIERTOS
    public void cierraFicheros() {
        for (int i = 0; i < arrayLectores.length; i++) {
             arrayLectores[i].cierraFichero();
        }
    }

} // Fin clase EscribeHead_ASCII
