//Source file: E:\\Perfil\\Perfil\\Restriccion.java

package es.usc.gsi.trace.importer.perfil;

import java.io.Serializable;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class Restriccion implements RestriccionInterface, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1237L;

    public enum Semantica { PENDIENTE, PERSISTENCIA_EN_PENDIENTE,
                  RECTA_BORROSA, TUBO_BORROSO, SIN_SEMANTICA}
    public enum Cuantificador { TODO, CASI_TODO, MAYOR_PARTE, MUCHO, MITAD, POCO } // NO_UCD (unused code)
    public enum UNIDADES { MILISEGUNDOS, SEGUNDOS, MINUTOS, HORAS } // NO_UCD (unused code)


    private String[] D;
    private String[] L;
    private String[] M;
    private int ptb;
    private int ptosig;
    private Semantica semantica;
    private boolean es_referencia = false;
    private int cuantificadorSemantica;
    private int unidadesTemporales = 2;
    private float magnitudPrimerPtoSig = 0;
    private float magnitudSegundoPtoSig = 0;
    private int distanciaTemporalEntrePtoSig;
    private boolean relativaAlNivelBasal = false;
    private boolean valorAbsoluto = false;


    /**
     * @param ptb
     * @param ptosig
     * @param D
     * @param L
     * @param M
     * @param sintaxis
     * @roseuid 378708190005
     */
    private Restriccion(int ptb, int ptosig, String[] D, String[] L, String[] M,
          Semantica sintaxis) {
        this.D = D;
        this.L = L;
        this.M = M;
        this.ptb = ptb;
        this.ptosig = ptosig;
        this.semantica = sintaxis;
    }

    public Restriccion(int ptb, int ptosig, String[] D, String[] L, String[] M,
          Semantica sintaxis, int cunatificadorSemantica, int unidadesTemporales) {
        this(ptb, ptosig, D, L, M, sintaxis);
        this.cuantificadorSemantica = cunatificadorSemantica;
        this.unidadesTemporales = unidadesTemporales;
    }

    /**
     * @return int
     * @roseuid 37870819000C
     */
    public int getNumeroDePTB() {
        return ptb;
    }

    /**
     * @param i
     * @roseuid 37870819000D
     */
    public void setNumeroDePTB(int i) {
        ptb = i;
    }

    /**
     * @return int
     * @roseuid 37870819000F
     */
    public Semantica getSemantica() {
        return semantica;
    }

    /**
     * @param i
     * @roseuid 378708190010
     */
    public void setSintaxis(Semantica i) {
        semantica = i;
    }

    /**
     * @return int
     * @roseuid 378708190012
     */
    public int getNumeroDePtoSig() {
        return ptosig;
    }

    /**
     * @return String[]
     * @roseuid 378708190013
     */
    public String[] getD() {
        return D;
    }

    /**
     * @return String[]
     * @roseuid 378708190014
     */
    public String[] getL() {
        return L;
    }

    /**
     * @return String[]
     * @roseuid 378708190015
     */
    public String[] getM() {
        return M;
    }

    /**
     * @param D
     * @roseuid 378708190016
     */
    public void setD(String[] D) {
        this.D = D;
    }

    /**
     * En milisengundos!!!!!
     */
    public void setL(String[] L) {
        this.L = L;
    }

    /**
     * @param M
     * @roseuid 37870819001A
     */
    public void setM(String[] M) {
        this.M = M;
    }


    /**
     * Este metodo se emplea para saber si esta restriccion es la que se toma como
     * referencia para que el primer PtoSig deje de ser flotante
     */
    public boolean isReferencia() {
        return es_referencia;
    }

    public void setEsReferencia(boolean b) {
        es_referencia = b;
    }

    /**
     * @return int[]
     * @roseuid 3789C925033F
     */
    @Override
    public int[] getSintaxisGeneralizada() {
        return null;
    }

    /**
     * @param i
     * @roseuid 3789C9250368
     */
    @Override
    public void setSintaxisGeneralizada(int[] i) {
       // Empty
    }

    /**
     * @return String[][]
     * @roseuid 3789C92503AE
     */
    @Override
    public String[][] getDs() {
        return null;
    }

    /**
     * @return String[][]
     * @roseuid 3789C92503CC
     */
    @Override
    public String[][] getLs() {
        return null;
    }

    /**
     * @return String[][]
     * @roseuid 3789C926000C
     */
    @Override
    public String[][] getMs() {
        return null;
    }

    /**
     * @param D
     * @roseuid 3789C926002A
     */
    @Override
    public void setDs(String[][] D) {
       // Vacio
    }

    /**
     * @param L
     * @roseuid 3789C926007A
     */
    @Override
    public void setLs(String[][] L) {
       // Vacio
    }

    /**
     * @param M
     * @roseuid 3789C92600CA
     */
    @Override
    public void setMs(String[][] M) {
          // Vacio
    }

    /**
     * @return int[]
     * @roseuid 3789C9260124
     */
    @Override
    public int[] getNumerosDePTB() {
        return null;
    }

    /**
     * @param v_PTB
     * @return Void
     * @roseuid 3789C9260142
     */
    @Override
    public Void setNumerosDePTB(int[] v_PTB) {
        return null;
    }

    /**
     * @return int[]
     * @roseuid 3789C9260192
     */
    @Override
    public int[] getNumerosDePtoSig() {
        return null;
    }

    /**
     * @param v_PtoSig
     * @return Void
     * @roseuid 3789C92601BA
     */
    @Override
    public Void setNumerosDePtoSig(int[] v_PtoSig) {
        return null;
    }

    /**
     * @return float
     * @roseuid 3789C926021F
     */
    @Override
    public float resolver() {
        return 0;
    }

    /**
     * @return Void
     * @roseuid 3789C926023D
     */
    @Override
    public Void resetCauce() {
        return null;
    }

    public int getCuantificadorSemantica() {
        return cuantificadorSemantica;
    }

    public void setCuantificadorSemantica(int cuantificadorSemantica) {
        this.cuantificadorSemantica = cuantificadorSemantica;
    }

    public int getUnidadesTemporales() {
        return unidadesTemporales;
    }

    public void setUnidadesTemporales(int unidadesTemporales) {
        this.unidadesTemporales = unidadesTemporales;
    }

    public float getMagnitudPrimerPtoSig() {
        return magnitudPrimerPtoSig;
    }

    public void setMagnitudPrimerPtoSig(float magnitudPrimerPtoSig) {
        this.magnitudPrimerPtoSig = magnitudPrimerPtoSig;
    }

    public float getMagnitudSegundoPtoSig() {
        return magnitudSegundoPtoSig;
    }

    public void setMagnitudSegundoPtoSig(float magnitudSegundoPtoSig) {
        this.magnitudSegundoPtoSig = magnitudSegundoPtoSig;
    }

    public int getDistanciaTemporalEntrePtoSig() {
        return distanciaTemporalEntrePtoSig;
    }

    /**
     * Se mide en milisegundos
     * @param distanciaTemporalEntrePtoSig
     */
    public void setDistanciaTemporalEntrePtoSig(int distanciaTemporalEntrePtoSig) {
        this.distanciaTemporalEntrePtoSig = distanciaTemporalEntrePtoSig;
    }

    /**
     * Devuelve una distribuccion de posibilidad respresentado la restriccion temporal en
     * numero de muestras para una fs dada
     * @param fs
     * @return
     */
//    public String[] getLParaFs(float fs) {
//        String[] Lfs = new String[4];
//        for (int i = 0; i < L.length; i++) {
//            float tmp = MyFloat.parseFloatSeguro(L[i]);
//            tmp = tmp * fs / 1000;
//            Lfs[i] = Float.toString(tmp);
//        }
//        return Lfs;
//    }

    /**
     * Devuelve una distribuccion de posibilidad respresentado la restriccion temporal en
     * numero de muestras para una fs dada
     * @param fs
     * @return
     */
//    public String[] getMParaFs(float fss) {
//        //Si es semantica de tubo la multiplicamos por 1000 y putno, no tiene unidades temporales
//        //pero para procesrla de modo estandar se divide por 1000 al cargarla
//        float fs = fss;
//        if (semantica == Semantica.TUBO_BORROSO) {
//            fs = 1;
//        }
//
//        String[] Mfs = new String[4];
//        for (int i = 0; i < L.length; i++) {
//            float tmp = MyFloat.parseFloatSeguro(M[i]);
//            tmp = tmp * 1000 / fs;
//            Mfs[i] = Float.toString(tmp);
//        }
//        return Mfs;
//    }

    @Override
    public boolean isRelativaAlNivelBasal() {
        return relativaAlNivelBasal;
    }

    @Override
    public void setRelativaAlNivelBasal(boolean relativaAlNivelBasal) {
        this.relativaAlNivelBasal = relativaAlNivelBasal;
    }

    public boolean isValorAbsoluto() {
        return valorAbsoluto;
    }

    public void setValorAbsoluto(boolean valorAbsoluto) {
        this.valorAbsoluto = valorAbsoluto;
    }

}
