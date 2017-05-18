// PATRON DE DISENHO SINGLETON
package es.usc.gsi.conversordatosmit.interfaz;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import es.usc.gsi.conversordatosmit.excepciones.*;
import es.usc.gsi.conversordatosmit.ficheros.*;
import es.usc.gsi.conversordatosmit.interfaz.filtros.*;

public class ControladorInterfaz {


    private static final Logger LOGGER = Logger.getLogger(ControladorInterfaz.class.getName());

    protected static final int LISTA = 0;
    protected static final int ETIQUETAS = 1;

    private static ControladorFicheros controlFicheros;
    private static ControladorInterfaz controlador;
    private static boolean ini = false;

    private PanelPrincipal panelPrincipal = null;
    private JFileChooser dialogoAbrirGrabar; // QUITAR: HACER UN DIALOGO SOLO PARA ABRIR Y OTRO SOLO PARA GRABAR.
    private DialogoAbrir dialogoAbrir;

    public static ControladorInterfaz getControlador() {
       if (!ini) {
           controlador = new ControladorInterfaz();
           ini = true;
       }
       return controlador;
    }

    protected static void enlazaControladorFicheros() {
        controlFicheros = ControladorFicheros.getControlador();
    }

    private IndicadorProgreso progreso = null;

    private ControladorInterfaz() {
       dialogoAbrir = new DialogoAbrir();
       dialogoAbrirGrabar = new JFileChooser();
    }


    //////////
    public PanelPrincipal getPanelPrincipal() {
        if (panelPrincipal == null) {
            panelPrincipal = new PanelPrincipal();
        }
        return panelPrincipal;
    }

    protected File abrirFichero(File archivoAbierto) {

        FileFilter filtro = new FiltroHead(); //Solo se escogen ficheros head
        dialogoAbrirGrabar.resetChoosableFileFilters(); // Borra todos los filtros anteriores.
        dialogoAbrirGrabar.setFileFilter(filtro);
        dialogoAbrirGrabar.setFileSelectionMode(JFileChooser.
                                                FILES_AND_DIRECTORIES);
        dialogoAbrirGrabar.setSelectedFile(null);
        if (archivoAbierto != null) {
            this.dialogoAbrirGrabar.setCurrentDirectory(archivoAbierto);
        }

        int opc = dialogoAbrirGrabar.showOpenDialog(panelPrincipal);

        if (opc == JFileChooser.APPROVE_OPTION) {

            File f = dialogoAbrirGrabar.getSelectedFile();

            FicheroHead[] fhG = new FicheroHead[1];
            try {
                fhG[0] = new FicheroHead(f);
                this.cerrarFichero();
                panelPrincipal.abrirPaciente(fhG);
                controlFicheros.setNuevoHead(fhG[0]);
                return f;

            } catch (FicheroNoValidoException e) {
                // MOSTRAR DIALOGO INFORMATIVO
                LOGGER.log(Level.SEVERE, "Fichero .hea no valido", e);
                this.muestraDialogoError(
                        "Este fichero no es valido.\nPor favor, escoja otro fichero.");
                this.abrirFichero(null);
            }
        } // Fin if JFileChooser
        return null;
    }

    protected File abrirPaciente(File archivoAbierto) {

        FileFilter filtro = new FiltroDirectorio(); //Solo se escogen directorios
        dialogoAbrir.resetChoosableFileFilters(); // Borra todos los filtros anteriores.
        dialogoAbrir.setFileFilter(filtro);
        dialogoAbrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialogoAbrir.setSelectedFile(null);
        if (archivoAbierto != null) {
            this.dialogoAbrir.setCurrentDirectory(archivoAbierto);
        }

        int opc = dialogoAbrir.showOpenDialog(panelPrincipal);

        if (opc == JFileChooser.APPROVE_OPTION) {

            File f = dialogoAbrir.getSelectedFile();

            FicheroHead[] fhG;
            try {

                fhG = controlFicheros.getFicherosHeadDirectorio(f); //Obtiene ficheros .hea con formato valido para abrirlos.
                this.cerrarPaciente(); // Si no se genera una excepcion, podemos cerrar el paciente actual para crear uno nuevo
                for (int i = 0; i < fhG.length; i++) { // Almacena los ficheros en el vector y crea nuevas lenguetas en la ventana para verlos.
                    controlFicheros.setNuevoHead(fhG[i]);
                }

                panelPrincipal.abrirPaciente(fhG);
                return f;

            } catch (DirectorioVacioException e) {
                // MOSTRAR DIALOGO INFORMATIVO
                LOGGER.log(Level.SEVERE, "Directorio no tiene ficheros .hea validos.", e);
                this.muestraDialogoError(
                        "Esta carpeta no contiene ficheros validos.\nPor favor, escoja otra carpeta.");
                this.abrirPaciente(null);
            }
        } // Fin if JFileChooser
        return null;
    }

    protected void cambiaVista(int modoVista) {
        FicheroHead[] ficherosHead = controlFicheros.getFicherosHeadArray();

//        if (ficherosHead != null) {
            panelPrincipal.cambiaVista(ficherosHead, modoVista);
//        }

    }

    protected void cerrarPaciente() {
        controlFicheros.vaciaVectorFicheros();
        panelPrincipal.cerrarPaciente();
    }

    private void cerrarFichero() {

        this.cerrarPaciente(); // LO CIERRA TODO

        //IMPLEMENTAR COMPROBACION DE CAMBIO EN FICHERO
        //PARA SACAR UN CUADRO INFORMATIVO ACERCA DE SI SE DEBE GRABAR O NO.
        // ANTES: SE USABA PARA CERRAR LOS FICHEROS DE UNO EN UNO.
        /*  controlFicheros.eliminaHead( this.getFicheroHeadSeleccionado() );
          panelPrincipal.cerrarFichero(); */

    }

    public Parametro[] getParametrosSeleccionados() throws OutOfMemoryError {

        try {
            controlFicheros.esExportable(); // Comprobacion de condiciones de exportacion: este metodo
            // solo devuelve excepciones. Poco ortodoxo?
            this.actualizaParametros();
        } catch (NoPacienteAbiertoException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.muestraDialogoError("Debe abrir la carpeta de un paciente o un fichero antes de poder importar datos.");
            return null;
        } catch (NoParametroSeleccionadoException e) {
           LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.muestraDialogoError("Debe marcar alguna variable antes de poder importar datos.");
            return null;
        } catch (FechasIncorrectasException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.muestraDialogoError("Por favor, compruebe que:\n" +
                                     "- Las fechas tienen el formato dd/mm/aaaa hh:mm:ss\n" +
                                     "- La fecha inicial no es posterior a la fecha final\n" +
                                     "- Ambas fechas son distintas\n" +
                                     "- La fecha inicial que ha escrito es mayor que la fecha inicial original\n" +
                                     "- La fecha final que ha escrito es menor que la fecha final original"
                    );
            return null;

        } catch(NoExportableException e){
           LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        return controlFicheros.getParametrosSeleccionados();

    }

    protected void exportaFicheros() {

        try {
            controlFicheros.esExportable(); // Comprobacion de condiciones de exportacion: este metodo
            // solo devuelve excepciones. Poco ortodoxo?
            this.actualizaParametros();
        } catch (NoPacienteAbiertoException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.muestraDialogoError(
                    "Debe abrir la carpeta de un paciente o un fichero antes de poder exportar.");
            return;
        } catch (NoParametroSeleccionadoException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.muestraDialogoError(
                    "Debe marcar alguna variable antes de poder exportar.");
            return;
        } catch (FechasIncorrectasException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            this.muestraDialogoError("Por favor, compruebe que:\n" +
                                     "- Las fechas tienen el formato dd/mm/aaaa hh:mm:ss\n" +
                                     "- La fecha inicial no es posterior a la fecha final\n" +
                                     "- Ambas fechas son distintas\n" +
                                     "- La fecha inicial que ha escrito es mayor que la fecha inicial original\n" +
                                     "- La fecha final que ha escrito es menor que la fecha final original"
                    );
            return;

        } catch(NoExportableException e){
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        int opcionSobreescribir = JOptionPane.YES_OPTION;
        FileFilter filtro = new FiltroExportar();
        dialogoAbrirGrabar.resetChoosableFileFilters(); // Borra todos los filtros anteriores.
        dialogoAbrirGrabar.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialogoAbrirGrabar.setFileFilter(filtro);
        dialogoAbrirGrabar.setSelectedFile(null);

        int opc = dialogoAbrirGrabar.showSaveDialog(panelPrincipal); // opcion grabar/cancelar

        if (opc == JFileChooser.APPROVE_OPTION) { // Si pulsamos boton "Grabar"
            File f = dialogoAbrirGrabar.getSelectedFile();

            if (f.exists()) { // Si el fichero ya existe, preguntamos para sobreescribir.
                opcionSobreescribir = this.muestraDialogoConfirmacion(
                        "El archivo ya existe.\nDesea sobreescribirlo?",
                        "Sobreescribir archivo", JOptionPane.YES_NO_OPTION);
            }

            if (opcionSobreescribir == JOptionPane.YES_OPTION) {

                try {
                    controlFicheros.volcarFicherosHead_ASCII(f);
                } catch (ErrorExportandoException e) {
                    LOGGER.log(Level.WARNING, e.getMessage(), e);
                    this.muestraDialogoError(
                            "Error al exportar datos.\n" +
                            "Por favor, escoja otra ubicacion para el archivo de exportacion"
                            );
                    this.exportaFicheros(); // Si existe un error al exportar el archivo, empezamos de nuevo.
                }

            } // Fin if JOptionPane

            else {
                this.exportaFicheros(); // Si decidimos no sobreescribir, empezamos de nuevo el proceso de exportacion.
            }

        } // Fin if JFileChooser

    } // Fin metodo exportaFicheros

    /* ERA PARA OBTENER EL FICHEROHEAD ASOCIADO A LA LENGUETA SELECCIONADA
       public FicheroHead getFicheroHeadSeleccionado() {

       return panelPrincipal.getFicheroHeadSeleccionado();

       } */

    private void actualizaParametros() throws FechasIncorrectasException {

        try {
            panelPrincipal.actualizaFrecuencias(); // Cambia los valores de las frecuencias de exportacion al valor mas reciente
            panelPrincipal.actualizaFechas(); // Cambia los valores de las fechas de los parametros
        } catch (FechaFinalMenorInicialException e) {
                this.muestraDialogoError(
                        "Error: la fecha final es anterior a la inicial");
                throw e;
        } catch (FechaFinalIgualInicialException e) {
             this.muestraDialogoError(
                     "Error: las fechas inicial y final son la misma");
             throw e;
        } catch (FechaInicialIncorrectaException e) {
             this.muestraDialogoError(
                     "Error: la fecha inicial es incorrecta");
             throw e;
        } catch (FechaFinalIncorrectaException e) {
             this.muestraDialogoError("Error: la fecha final es incorrecta");
             throw e;
        } catch (FechaFinalMayorOriginalException e) {
             this.muestraDialogoError("Error: la fecha final de remuestreo es mayor que la fecha final original.\nEn la fecha que ha escrito el muestreo ya habia acabado.");
             throw e;
        } catch (FechaInicialMenorOriginalException e) {
             this.muestraDialogoError("Error: la fecha inicial de remuestreo es menor que la fecha inicial original.\nEn la fecha que ha escrito, el muestreo aun no habia empezado.");
             throw e;
        } catch(Exception e){
           LOGGER.log(Level.WARNING, e.getMessage(), e);
           throw new FechasIncorrectasException(e.getMessage());
        }
    }

    public void creaIndicadorProgreso(Cancelar h, String titulo,
                                      String textoPrincipal, int max, int min,
                                      boolean _stop) {
        // if (progreso==null) {
        progreso = new IndicadorProgreso(h, titulo, textoPrincipal, max, min,
                                         _stop);
        progreso.setVisible(true);
        // }
    }

    public void notificaProgreso(int nuevoValor) {
        if (progreso != null) {
            progreso.setProgreso(nuevoValor);
        }
    }

    public void cierraIndicadorProgreso() {
        if (progreso != null) {
            progreso.setVisible(false);
            progreso.dispose();
            progreso = null;
        }
    }

    private void muestraDialogoError(String mensaje) {
        JOptionPane.showMessageDialog(panelPrincipal, mensaje);
    }

    private int muestraDialogoConfirmacion(String mensaje, String titulo, int modo) {
        return JOptionPane.showConfirmDialog(panelPrincipal, mensaje, titulo, modo);
    }

} // Fin clase ControladorInterfaz
