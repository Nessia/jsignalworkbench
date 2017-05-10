//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\monitorizacion\\DataIO\\CargarDatosTxt.java

package es.usc.gsi.trace.importer.monitorizacion.dataio;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.trace.importer.jsignalmonold.annotations.Annotation;
import es.usc.gsi.trace.importer.jsignalmonold.annotations.Mark;

/**
 * Carga los todos de un archivo .txt, en el cual los datos se almacenan como
 * Strings organizados en columnas y seprados por tabulaciones.
 */
public class CargarDatosTxt extends CargarDatos {


    private static final Logger LOGGER = Logger.getLogger(CargarDatosTxt.class.getName());

    public CargarDatosTxt(String archivo) {
        super(archivo);
        cargaDatos();

    }

    /**
     * @todo: Javier Salas cambio el formato de almacenamiento de salida de la herramienta de Vila.
     * Cambi t por " ". Hice cambios con ese fin. Validarlo, no fijo que funcion siempre.
     */
    @SuppressWarnings("unchecked")
    private void cargaDatos() {
        BufferedReader bf = null;
        try {
            FileReader f = new FileReader(archivo);
            bf = new BufferedReader(f);
            File fich = new File(archivo);
            //Marcasmos el Buffer para poder resetearlo una vez averiguado el numero
            //de filas y columnas que hay en el
            bf.mark((int) fich.length() + 1);
            String line = bf.readLine();
            StringTokenizer tk = new StringTokenizer(line);
            int columnas = 0;
            //Contamos el numero de columnas que hay en el archivo
            while (tk.hasMoreTokens()) {
                columnas++;
                tk.nextToken();
            }
            //Contamos el numero de filas. Aunque ya llevamos 1 leida el contador
            //empieza en 0 ya que el bucle incrementara su valor la primera vez que lea null
            int filas = 0;
            while (line != null) {
                line = bf.readLine();
                filas++;
            }
            //Inicilizamos todas la variables
            datos = new float[columnas][filas];
            pos = new byte[columnas][filas];
            marcas = new TreeSet[columnas];
            anotaciones = new TreeSet<Annotation>();
            for (int i = 0; i < columnas; i++) {
                marcas[i] = new TreeSet<Mark>();
            }
            //reseteamos el buffer
            bf.reset();
            //Contadores de linea y columna
            int lin = 0;
            int col = 0;
            do {
                line = bf.readLine();
                if (line != null) {
                    StringTokenizer tk2 = new StringTokenizer(line, "\t ", true);
                    lin = 0;
                    while (tk2.hasMoreTokens()) {
                        String dato_fichero = tk2.nextToken();
                        if ("\t".equals(dato_fichero)) {
                            datos[lin][col] = 0;
                            lin++;
                            /*  //Si la primera columna es la que falta el dtao = \t 21 \t 23 \t...
                              //Por lo que en ese caso no hay que consumir ningun\t adicional
                              if (tk2.hasMoreElements() && lin != 1) {
                               //consuminos el \t adicional
                               if (!(tk2.nextElement().equals("\t"))) {
                             System.out.println("Se esta leyneod mal el fichero de entrada");
                               }
                              }*/

                        } else {
                            datos[lin][col] = Float.parseFloat(dato_fichero);
                            //Si no estamos en la ultima columna
                            if (tk2.hasMoreElements()) {
                                //consuminos el \t adicional
                                String ultimo_token = tk2.nextToken();
                                if (!("\t".equals(ultimo_token) || " ".equals(ultimo_token))) {
                                }
                            }
                            lin++;
                        }
                    }
                    col++;
                }
            } while (line != null);
            GestorIO.setNumDatos(filas);
            GestorIO.setNumSenales(columnas);

        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            datos = null;
            pos = null;
            marcas = null;
            anotaciones = null;
        } finally {
            if(bf != null){
                try {
                    bf.close();
                } catch (IOException ex) {
                   LOGGER.log(Level.FINER, ex.getMessage(), ex);
                }
            }
        }

    }
}
