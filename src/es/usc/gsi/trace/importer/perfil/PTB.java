//Source file: E:\\Perfil\\Perfil\\PTB.java

package es.usc.gsi.trace.importer.perfil;

import java.io.Serializable;
import java.util.*;

/**
 * @author Abraham Otero Quintana
 * @version 0.4
 */
public class PTB implements PTBInterface, Serializable {
    /**
     * Esto nos hara a nosotros responsables del versionamiento de los ficheros Serializados.
     * Siempre podemos volver a leerlos, pero depende de nosotros que se haga de un modo correcto.
     */

    static final long serialVersionUID = 1231L;

    private static final String PRIMER_D = "0.0";
    private static final String PRIMER_T2 = "20.0";
    private static final String PRIMER_T = "0.0";


    private float intInicioSoporteSeparacion = 10;
    private float intInicioCoreSeparacion = 15;
    private float intFinCoreSeparacion = 20;
    private float intFinSoporteSeparacion = 25;
    private float longitudVentana = 4;


    private String nombre;
    private String parametro;
    private String unidades;
    private String unidadesTemporales;
    private String comentario;
    private int numPTB;
    //private int numeroPtoSig = 0;

    //private Vector parametros;
    private List<PtoSig> vectorPtoSig = new ArrayList<PtoSig>();
    private PTBMInterface ptbm;
    private boolean esFlotante = true;
    private HashMap<String,AuxiliarOffset> almacenOffset = new HashMap<String,AuxiliarOffset>();
    private boolean buscarEnValorAbsoluto = false;

    /**
     * Es el constructor de un PTB
     * @param nombre
     * @param parametro
     * @param unidades
     * @param comentario
     * @param numPTB
     * @roseuid 378708190062
     */
    public PTB(String nombre, String parametro, String unidades,
               String unidades_temporales, String comentario, int numPTB) {
        this.nombre = nombre;
        this.parametro = parametro;
        this.unidades = unidades;
        this.comentario = comentario;
        this.numPTB = numPTB;
        this.unidadesTemporales = unidades_temporales;
        //numeroPtoSig++;
        //numeroPtoSig++;
        vectorPtoSig.add(new PtoSig(PRIMER_D, PRIMER_T, numPTB, true));
        vectorPtoSig.add(new PtoSig(PRIMER_D, PRIMER_T2, numPTB));

    }

    /**
     * @param ptbm
     * @roseuid 378708190068
     */
    @Override
    public void setPTBM(PTBMInterface ptbm) {
        this.ptbm = ptbm;
    }

    /**
     * @param ptosig
     * @roseuid 37870819006A
     */
    @Override
    public void anhadePtoSig(PtoSig ptosig) {
        vectorPtoSig.add(ptosig);
        //numeroPtoSig++;
    }

    /**
     * @return int
     * @roseuid 37870819006C
     */
    @Override
    public int getNumeroDePtoSig() {
        return vectorPtoSig.size();
    }

    /**
     * @roseuid 37870819006D
     */
    @Override
    public void DecrementaNumeroDePtoSig() {
        //numeroPtoSig--;
    }

    /**
     * @return int
     * @roseuid 37870819006E
     */
    @Override
    public int getNumeroDePTB() {
        return numPTB;
    }

    /**
     * @param tmp
     * @roseuid 37870819006F
     */
    @Override
    public void setNumeroDePTB(int tmp, int ptb_borrado) {
        numPTB = tmp;
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            vectorPtoSig.get(i).setNumeroDePTB(tmp, ptb_borrado);
        }

    }

    /**
     * @param tmp
     * @roseuid 37870819006F
     */
    public void setNumeroDePTB(int tmp) {
        numPTB = tmp;
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            vectorPtoSig.get(i).setNumeroDePTB(tmp);
        }

    }

    /**
     * Metodo que solo se debe emplear cunado se carga una plantiila
     * @param tmp
     */
    public void setNumeroDePTBCargaPlantilla(int tmp) {
        numPTB = tmp;
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            vectorPtoSig.get(i).setNumeroDePTBCargaPlantilla(tmp);
        }

    }

    /**
     * @param ptosig
     * @roseuid 378708190071
     */
    @Override
    public void revisaRestricciones(PtoSig ptosig) {
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            vectorPtoSig.get(i).revisaRestricciones(ptosig);
        }
    }

    /**
     * @return PtoSig[]
     * @roseuid 378708190073
     */
    @Override
    public PtoSig[] getPtoSig() {
        PtoSig[] vptosig = new PtoSig[vectorPtoSig.size()];
        for (int i = 0; i < vectorPtoSig.size(); i++) {
            vptosig[i] = vectorPtoSig.get(i);
        }
        return vptosig;
    }

    /**
     * @param vptosig
     * @param nptosig
     * @roseuid 378708190074
     */
    @Override
    public void setPtoSig(PtoSig[] vptosig, int nptosig) {
        vectorPtoSig.clear();
        for (int i = 0; i < nptosig; i++) {
            vectorPtoSig.add(vptosig[i]);
        }
    }

    /**
     * @param nombre
     * @param parametro
     * @param unidades
     * @param comentario
     * @param unidades_tiempo
     * @roseuid 378708190077
     */
    @Override
    public void modificar(String nombre, String parametro, String unidades,
                          String unidades_tiempo, String comentario,
                          float intInicioSoporteSeparacion,
                          float intInicioCoreSeparacion,
                          float intFinCoreSeparacion,
                          float intFinSoporteSeparacion, float longitudVentana,
                          boolean buscarEnValorAbsoluto) {
        this.nombre = nombre;
        this.parametro = parametro;
        this.unidades = unidades;
        this.comentario = comentario;
        this.unidadesTemporales = unidades_tiempo;
        this.intFinCoreSeparacion = intFinCoreSeparacion;
        this.intFinSoporteSeparacion = intFinSoporteSeparacion;
        this.intInicioCoreSeparacion = intInicioCoreSeparacion;
        this.intInicioSoporteSeparacion = intInicioSoporteSeparacion;
        this.longitudVentana = longitudVentana;
        this.buscarEnValorAbsoluto = buscarEnValorAbsoluto;
    }

    @Override
    public void modificar(String nombre, String parametro, String unidades,
                          String unidades_tiempo, String comentario) {
        this.nombre = nombre;
        this.parametro = parametro;
        this.unidades = unidades;
        this.comentario = comentario;
        this.unidadesTemporales = unidades_tiempo;
    }


    /**
     * @param ptosig
     * @return Restriccion[]
     * @roseuid 37870819007C
     */
    @Override
    public Restriccion[] getRestricciones(int ptosig) {
        return vectorPtoSig.get(ptosig).getRestricciones();
    }

    /**
     * @param ptosig
     * @param numeroPtoSig
     * @param seleccion
     * @roseuid 37870819007E
     */
    @Override
    public void anhadePtoSig(PtoSig ptosig, int numeroPtoSig, Acciones seleccion) {
        if (seleccion == Acciones.ANHADIR) {
            vectorPtoSig.add(ptosig);
//            numeroPtoSig++;
        } else if (seleccion == Acciones.BORRAR) {
            ptbm.revisaRestricciones(vectorPtoSig.get(numeroPtoSig));
            int numero_ptos_inicilaes = vectorPtoSig.size();
            for (int i = numero_ptos_inicilaes - 1; i >= numeroPtoSig; i--) {
                PtoSig pto = vectorPtoSig.get(i);
                //   pto.setNumeroDePtoSig(pto.getNumeroDePtoSig() -1);
                for (int j = 0; j < numeroPtoSig; j++) {
                    PtoSig pto2 = vectorPtoSig.get(j);
                    pto2.revisaRestricciones(pto);
                    ptbm.revisaRestricciones(pto);
                }
                vectorPtoSig.remove(i);
                //this.numeroPtoSig--;

            }
        } else if (seleccion == Acciones.MODIFICAR) {
            vectorPtoSig.set(numeroPtoSig, ptosig);
        }
    }

    /**
     * @param ptb
     * @param ptosig
     * @param restriccion
     * @param restriccion_vieja
     * @param numeroPtoSig
     * @param seleccion
     * @roseuid 378708190082
     */
    @Override
    public void anhadeRestriccion(int ptb, int ptosig, Restriccion restriccion,
                                  Restriccion restriccion_vieja, PTBInterface.Acciones seleccion) {
        vectorPtoSig.get(ptosig).anhadeRestriccion(ptb, ptosig, restriccion, restriccion_vieja,
                                   /*numeroPtoSig,*/seleccion);
    }

    /**
     * @return java.lang.String
     * @roseuid 378708190089
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @return java.lang.String
     * @roseuid 37870819008A
     */
    @Override
    public String getComentario() {
        return comentario;
    }

    /**
     * @return java.lang.String
     * @roseuid 37870819008B
     */
    @Override
    public String getParametro() {
        return parametro;
    }

    /**
     * @return java.lang.String
     * @roseuid 37870819008C
     */
    @Override
    public String getUnidades() {
        return unidades;
    }

    /**
     * @return String
     * @roseuid 3788C7170157
     */
    @Override
    public String getUnidadesTemporales() {
        return unidadesTemporales;
    }


    /**
     * Estos metodos nos valdran para averiguar si el PTBM es "flotante" o tiene algun
     * tipo de restriccion con el primer PtoSig.
     */

    public void setEsFlotante(boolean b) {
        this.esFlotante = b;
    }

    public boolean getEsFlotante() {
        return esFlotante;
    }

    /**
     * @param parametro
     * @return Object
     * @roseuid 3788D4B30355
     */
    @Override
    public Object getParametro(int parametro) {
        return null;
    }

    /**
     * @param parametro
     * @roseuid 3788D4B303C3
     */
    @Override
    public void setParametro(int parametro) {
       // Empty
    }


    public float[] getOffset() {
        if (!almacenOffset.isEmpty()) {
            Collection<AuxiliarOffset> c = almacenOffset.values();
            Iterator<AuxiliarOffset> it = c.iterator();
            return it.next().getOffset();
        } else {
            return new float[]{0, 0};
        }
    }

    @Override
    public float getIntInicioSoporteSeparacion() {
        return intInicioSoporteSeparacion;
    }

    @Override
    public void setIntInicioSoporteSeparacion(float intInicioSoporteSeparacion) {
        this.intInicioSoporteSeparacion = intInicioSoporteSeparacion;
    }

    @Override
    public void setIntInicioCoreSeparacion(float intInicioCoreSeparacion) {
        this.intInicioCoreSeparacion = intInicioCoreSeparacion;
    }

    @Override
    public void setIntFinSoporteSeparacion(float intFinSoporteSeparacion) {
        this.intFinSoporteSeparacion = intFinSoporteSeparacion;
    }

    @Override
    public void setIntFinCoreSeparacion(float intFinCoreSeparacion) {
        this.intFinCoreSeparacion = intFinCoreSeparacion;
    }

    @Override
    public float getIntFinCoreSeparacion() {
        return intFinCoreSeparacion;
    }

    @Override
    public float getIntFinSoporteSeparacion() {
        return intFinSoporteSeparacion;
    }

    @Override
    public float getIntInicioCoreSeparacion() {
        return intInicioCoreSeparacion;
    }

    @Override
    public float getLongitudVentana() {
        return longitudVentana;
    }

    @Override
    public void setLongitudVentana(float longitudVentana) {
        this.longitudVentana = longitudVentana;
    }

    @Override
    public float getSeparacionCrisp() {
        return intInicioSoporteSeparacion;
    }

    @Override
    public boolean isBuscarEnValorAbsoluto() {
        return buscarEnValorAbsoluto;
    }

    public void setBuscarEnValorAbsoluto(boolean buscarEnValorAbsoluto) {
        this.buscarEnValorAbsoluto = buscarEnValorAbsoluto;
    }

    private class AuxiliarOffset implements Serializable {
        static final long serialVersionUID = 12311L;

        private String has;
        private float[] offset;


        @Override
        public int hashCode() {
            return Integer.parseInt(has);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof AuxiliarOffset){
               AuxiliarOffset i = (AuxiliarOffset)obj;
               if(i.has == null && has == null && Arrays.equals(offset, i.offset)){
                  return false;
               }
               return (has != null && has.equals(i.has)) && Arrays.equals(offset, i.offset);
            }
            return false;
        }

        public float[] getOffset() {
            return offset;
        }
    }


}
