//Source file: E:\\Perfil\\Perfil\\PTBM.java

package es.usc.gsi.trace.importer.perfil;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class PTBM implements PTBMInterface, Serializable {

    private static final Logger LOGGER = Logger.getLogger(PTBM.class.getName());
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    private static final long serialVersionUID = 1233L;


//Separacion entre PTBM
    private float inicioSoporteSeparacion = 10;
    private float inicioCoreSeparacion = 15;
    private float finCoreSeparacion = 20;
    private float finSoporteSeparacion = 25;
    /**
     * //////////////////VARIABLES////////////////////////////////////
     */
    private static int numeroPTB;
    private String nombre;
    private String comentario;
    private transient String fichero;
    private int numeroPTBnoEstatico = 0;
    private transient boolean tieneficheroasociado = false;
    private transient boolean guardado = false;
    //private Vector parametros;
    private List<PTB> vectorPTB = new Vector<PTB>();
    public PTBInterface[] thePTBInterface;


    /**
     * @param nombre
     * @param comentario
     * @param ptb
     * @roseuid 37870819009D
     */
    public PTBM(String nombre, String comentario, PTB ptb) {
        this.nombre = nombre;
        this.comentario = comentario;
        vectorPTB.add(ptb);
        numeroPTB = 1;
        numeroPTBnoEstatico = 1;
    }

    /**
     * @param ptb
     * @param numeroPTB
     * @param seleccion
     * @roseuid 3787081900A1
     */
    @Override
    public void anhadePTB(PTB ptb, int numeroPTB, PTBInterface.Acciones seleccion) {
       switch(seleccion){
       case ANHADIR:
          vectorPTB.add(ptb);
          PTBM.incrementaNumeroPTB();
          numeroPTBnoEstatico++;
          break;
       case MODIFICAR:
          vectorPTB.get(numeroPTB).modificar(ptb.getNombre(),
                ptb.getParametro(),
                ptb.getUnidades(), ptb.getComentario(), "OJO");
          break;
       case BORRAR:
          revisaRestricciones(ptb);
          vectorPTB.remove(numeroPTB);
          PTBM.decrementaNumeroPTB();
          numeroPTBnoEstatico--;
          //Esto es para borrar restricciones que no se borran donde devieran
          for (int i = 0; i < numeroPTB; i++) {
              vectorPTB.get(i).setNumeroDePTB(i, numeroPTB);
          }

          for (int i = (numeroPTB); i < getNumeroPTB(); i++) {
              int num = vectorPTB.get(i).getNumeroDePTB();
              vectorPTB.get(i).setNumeroDePTB(num - 1, numeroPTB);
          }
          break;
       case CREAR:
          LOGGER.warning("No se debe crear en este punto");
          break;
       }
    }

    /**
     * @return boolean
     * @roseuid 3787081900A5
     */
    @Override
    public boolean tieneFicheroAsicoado() {
        return tieneficheroasociado;
    }

    /**
     * @param b
     * @roseuid 3787081900A6
     */
    @Override
    public void setTieneFicheroAsociado(boolean b) {
        tieneficheroasociado = b;
    }

    /**
     * @return java.lang.String
     * @roseuid 3787081900A8
     */
    @Override
    public String getFicheroAsociado() {
        return fichero;
    }

    /**
     * @param fichero
     * @roseuid 3787081900A9
     */
    @Override
    public void setFicheroAsociado(String fichero) {
        this.fichero = fichero;
    }

    /**
     * @return int
     * @roseuid 3787081900AB
     */
    public static int getNumeroPTB() {
        return numeroPTB;
    }

    /**
     * @param i
     * @roseuid 3787081900AC
     */
    public static void setNumeroPTB(int i) {
        numeroPTB = i;
    }

    /**
     * @roseuid 3787081900AE
     */
    public static void incrementaNumeroPTB() {
        numeroPTB++;
    }

    /**
     * @roseuid 3787081900AF
     */
    public static void decrementaNumeroPTB() {
        numeroPTB--;
    }

    /**
     * @return PTB[]
     * @roseuid 3787081900B0
     */
    @Override
    public PTB[] getPTB() {
        PTB[] vptb = new PTB[numeroPTBnoEstatico];
        for (int i = 0; i < (numeroPTBnoEstatico); i++) {
            vptb[i] = vectorPTB.get(i);
        }
        return vptb;
    }

    /**
     * @param vptb
     * @param nptb
     * @roseuid 3787081900B1
     */
    @Override
    public void setPTB(PTB[] vptb, int nptb) {
        vectorPTB.clear();
        for (int i = 0; i < nptb; i++) {
            vectorPTB.add(vptb[i]);
        }
        numeroPTB = nptb;
        this.numeroPTBnoEstatico = nptb;
    }

    /**
     * @return int[]
     * @roseuid 3787081900B4
     */
    @Override
    public int[] getNumeroPtoSig() {
        int[] v = new int[numeroPTB];
        for (int i = 0; i < vectorPTB.size(); i++) {
            v[i] = vectorPTB.get(i).getNumeroDePtoSig();
        }
        return v;
    }

    /**
     * @param ptb
     * @param ptosig
     * @param numeroPtoSig
     * @param selecion
     * @roseuid 3787081900B5
     */
    @Override
    public void anhadePtoSig(PTB ptb, PtoSig ptosig, int numeroPtoSig,
          PTBInterface.Acciones selecion) {
        vectorPTB.get(vectorPTB.indexOf(ptb)).anhadePtoSig(ptosig,
                numeroPtoSig, selecion);
    }

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param selecion
     * @roseuid 3787081900BA
     */
    @Override
    public void anhadeRestriccion(PTB ptb, PtoSig ptosig,
                                  Restriccion restriccion,
                                  Restriccion restriccion_vieja,
                                  int numeroPtoSig, PTBInterface.Acciones selecion) {
        vectorPTB.get(vectorPTB.indexOf(ptb)).anhadeRestriccion(
                ptb.getNumeroDePTB(),
                ptosig.getNumeroDePtoSig(), restriccion,
                restriccion_vieja, /*numeroPtoSig,*/ selecion);

    }

    /**
     * @return boolean
     * @roseuid 3787081900C1
     */
    @Override
    public boolean isGuardado() {
        return guardado;
    }

    /**
     * @param b
     * @roseuid 3787081900C2
     */
    @Override
    public void setGuardado(boolean b) {
        guardado = b;
    }

    /**
     * @param ptb
     * @param ptosig
     * @return Restriccion[]
     * @roseuid 3787081900C4
     */
    @Override
    public Restriccion[] getRestricciones(int ptb, int ptosig) {
        return vectorPTB.get(ptb).getRestricciones(ptosig);
    }

    /**
     * @param ptosig
     * @roseuid 3787081900C7
     */
    @Override
    public void revisaRestricciones(PtoSig ptosig) {
        for (int i = 0; i < numeroPTB; i++) {
            vectorPTB.get(i).revisaRestricciones(ptosig);
        }
    }

    /**
     * @param ptb
     * @roseuid 3787081900C9
     */
    @Override
    public void revisaRestricciones(PTB ptb) {
        PTB ptb2 = vectorPTB.get(vectorPTB.indexOf(ptb));
        int numptosig = ptb2.getNumeroDePtoSig();
        PtoSig[] vptosig = ptb2.getPtoSig();

        for (int i = 0; i < numeroPTB; i++) {
            for (int j = 0; j < numptosig; j++) {
                vectorPTB.get(i).revisaRestricciones(vptosig[j]);
            }
        }
    }

    /**
     * @roseuid 3787081900CB
     */
    @Override
    public void almacenaNumeroPTB() {
        numeroPTBnoEstatico = PTBM.getNumeroPTB();
    }

    /**
     * @return int
     * @roseuid 3787081900CC
     */
    @Override
    public int getnumeroPTBnoEstatico() {
        return numeroPTBnoEstatico;
    }

    @Override
    public String getTitulo() {
        return nombre;
    }

    /**
     * Este metodo devuelve el comentario del PTBM
     * @roseuid 3788BA0903A7
     */
    @Override
    public String getComentario() {
        return comentario;
    }

    /**
     * Este metodo modifica el titulo y el comentario del PTBM
     * @roseuid 3788BA0903A7
     * @param titulo
     * @param comentario
     */
    @Override
    public void modificar(String titulo, String comentario) {
        this.nombre = titulo;
        this.comentario = comentario;
    }


    /**
     * @param parametro
     * @return Object
     * @roseuid 3788D4B4020C
     */
    @Override
    public Object getParametro(int parametro) {
        return null;
    }

    /**
     * @param parametro
     * @roseuid 3788D4B4027A
     */
    @Override
    public void setParametro(int parametro) {
       // Empty
    }

    @Override
    public float getFinCoreSeparacion() {
        return finCoreSeparacion;
    }

    @Override
    public float getFinSoporteSeparacion() {
        return finSoporteSeparacion;
    }

    @Override
    public void setFinCoreSeparacion(float finCoreSeparacion) {
        this.finCoreSeparacion = finCoreSeparacion;
    }

    @Override
    public void setFinSoporteSeparacion(float finSoporteSeparacion) {
        this.finSoporteSeparacion = finSoporteSeparacion;
    }

    @Override
    public float getInicioSoporteSeparacion() {
        return inicioSoporteSeparacion;
    }

    @Override
    public float getInicioCoreSeparacion() {
        return inicioCoreSeparacion;
    }

    @Override
    public void setInicioCoreSeparacion(float inicioCoreSeparacion) {
        this.inicioCoreSeparacion = inicioCoreSeparacion;
    }

    @Override
    public void setInicioSoporteSeparacion(float inicioSoporteSeparacion) {
        this.inicioSoporteSeparacion = inicioSoporteSeparacion;
    }

    /**
     * getSeparacionCrips
     *
     * @return float
     */
    @Override
    public float getSeparacionCrips() {
        return inicioSoporteSeparacion;
    }

}
