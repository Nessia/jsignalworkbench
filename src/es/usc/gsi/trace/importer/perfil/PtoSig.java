//Source file: E:\\Perfil\\Perfil\\PtoSig.java

package es.usc.gsi.trace.importer.perfil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class PtoSig implements PtoSigInterface, Serializable {

   private static final Logger LOGGER = Logger.getLogger(PtoSig.class.getName());
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1235L;

    private String D;
    private String L;
    //private int numeroRestricciones;
    private int ptb;
    private int numeroDePtoSig = 0;
//    public RestriccionInterface[] theRestriccionInterface;
    private List<Restriccion> vectorRestricciones = new ArrayList<Restriccion>();
//    private boolean es_flotante = true;


    /**
     * @param V
     * @param T
     * @param ptb
     * @roseuid 37870819002E
     */
    PtoSig(String V, String T, int ptb) {
        D = V;
        L = T;

        numeroDePtoSig = 0;
        this.ptb = ptb;

    }

    /**
     * Siempre se usa para el primer PtoSIg
     * @param V
     * @param T
     * @param ptb
     * @param es_flotante
     */

    PtoSig(String V, String T, int ptb, boolean es_flotante) {
        D = V;
        L = T;

        numeroDePtoSig = 1;
        this.ptb = ptb;
//        this.es_flotante = es_flotante;
    }

    /**
     * @param restriccion
     * @param ptb
     * @param ptosig
     * @roseuid 37870819002A
     */
    public PtoSig(Restriccion restriccion, int ptb, int ptosig) {
        this.ptb = ptb;
        this.numeroDePtoSig = ptosig;
        vectorRestricciones.add(restriccion);
        //numeroRestricciones++;
    }

    /**
     * @return java.lang.String
     * @roseuid 378708190032
     */
    @Override
    public String getL() {
        return L;
    }

    /**
     * @return java.lang.String
     * @roseuid 378708190033
     */
    @Override
    public String getD() {
        return D;
    }

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param seleccion
     * @roseuid 378708190034
     */
    @Override
    public void anhadeRestriccion(int ptb, int ptosig, Restriccion restriccion,
                                  Restriccion restriccion_vieja, PTBInterface.Acciones seleccion) {
       switch(seleccion){
       case ANHADIR:
          vectorRestricciones.add(restriccion);
          break;
       case MODIFICAR:
          int index = vectorRestricciones.indexOf(restriccion_vieja);
          vectorRestricciones.remove(index);//
          vectorRestricciones.add(index,restriccion);
          break;
       case BORRAR:
          vectorRestricciones.remove(vectorRestricciones.indexOf(restriccion_vieja));
          DecrementaNumeroRestricciones();
          break;
      default:
         LOGGER.warning("seleccion no puede tomar el valor crear en esta funcion" );
         break;
       }
    }

    /**
     * @roseuid 37870819003B
     */
    @Override
    public void DecrementaNumeroRestricciones() {
        //numeroRestricciones--;
    }

    /**
     * @return int
     * @roseuid 37870819003C
     */
    @Override
    public int getNumeroDeRestricciones() {
        return vectorRestricciones.size();
    }

    /**
     * @return int
     * @roseuid 37870819003D
     */
    @Override
    public int getNumeroDePtoSig() {
        return numeroDePtoSig;
    }

    /**
     * @param num
     * @roseuid 37870819003E
     */
    @Override
    public void setNumeroDePtoSig(int num) {
        numeroDePtoSig = num;
    }

    /**
     * @return int
     * @roseuid 378708190040
     */
    @Override
    public int getNumeroDePTB() {
        return ptb;
    }

    /**
     * @param ptb
     * @roseuid 378708190041
     */
    @Override
    public void setNumeroDePTB(int ptb, int num_ptb_borrado) {

        for (int i = 0; i < vectorRestricciones.size(); i++) {
            if (vectorRestricciones.get(i).getNumeroDePTB() == this.ptb) {
                vectorRestricciones.get(i).setNumeroDePTB(ptb);
            } else if (vectorRestricciones.get(i).getNumeroDePTB() > num_ptb_borrado) {
                int nuevo_numero = vectorRestricciones.get(i).getNumeroDePTB() - 1;
                vectorRestricciones.get(i).setNumeroDePTB(nuevo_numero);
            }
            // Nunca deberia ejecutarse esto, pero es necesio . Chapuzas S.A.
//            else if (vectorRestricciones.get(i).getNumeroDePTB() > num_ptb_borrado) {
//                vectorRestricciones.remove(i);
//            }
        }
        this.ptb = ptb;
    }

    public void setNumeroDePTB(int ptb) {
        for (int i = 0; i < vectorRestricciones.size(); i++) {
            if (vectorRestricciones.get(i).getNumeroDePTB() == ptb) {
                vectorRestricciones.get(i).setNumeroDePTB(ptb);
            }
        }
        this.ptb = ptb;
    }

    /**
     * Metodo que solo se debe emplkear cuando se carga una plantilla
     * @param ptb
     */
    public void setNumeroDePTBCargaPlantilla(int ptb) {
        for (int i = 0; i < vectorRestricciones.size(); i++) {
            vectorRestricciones.get(i).setNumeroDePTB(ptb);
        }
        this.ptb = ptb;
    }

    /**
     * @param ptosig
     * @roseuid 378708190043
     */
    @Override
    public void revisaRestricciones(PtoSig ptosig) {
        for (int i = 0; i < vectorRestricciones.size(); i++) {
            if (ptosig.getNumeroDePTB() == vectorRestricciones.get(i).getNumeroDePTB()
                && ptosig.getNumeroDePtoSig() == vectorRestricciones.get(i).getNumeroDePtoSig()) {
                vectorRestricciones.remove(i);
            }
        }
    }

    /**
     * @return Restriccion[]
     * @roseuid 378708190045
     */
    @Override
    public Restriccion[] getRestricciones() {
        Restriccion[] vrestricciones = new Restriccion[vectorRestricciones.size()];
        for (int i = 0; i < vectorRestricciones.size(); i++) {
            vrestricciones[i] = vectorRestricciones.get(i);
        }
        return vrestricciones;
    }

    /**
     * @param vrestricciones
     * @param numrestricciones
     * @roseuid 378708190046
     */
    @Override
    public void setRestricciones(Restriccion[] vrestricciones, int numrestricciones) {
        vectorRestricciones.clear();
        for (int i = 0; i < numrestricciones; i++) {
            vectorRestricciones.add(vrestricciones[i]);
        }
    }

    /*
     * Estos metodos nos valdran para averiguar si el PTBM es "flotante" o tiene algun
     * tipo de restriccion con el primer PtoSig.
     */

//    void setEsFlotante(boolean b) {
//        this.es_flotante = b;
//    }
//
//    boolean getEsFlotante() {
//        return es_flotante;
//    }

}
