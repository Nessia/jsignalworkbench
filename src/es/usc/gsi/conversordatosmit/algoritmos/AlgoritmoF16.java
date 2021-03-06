/*
 * Created on Nov 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.usc.gsi.conversordatosmit.algoritmos;

/**
 * @author carlos
 *
 */
public class AlgoritmoF16 implements InterfazConversion {

    private int arraySize = 2; // Numero de bytes necesarios para poder extraer una muestra del fichero.
    private float step = 16.0F / 8.0F; // Numero de bytes que ocupa una muestra: 16 bits / 8 bits por byte

    @Override
    public int convierteBytes(byte[] b, int numMuestra) {
        // Algoritmo de conversion especifico para formato 61
        return (((b[1] & 0xff) << 24) >> 16) | (b[0] & 0xff);
    }

    @Override
    public float getStep() {
        return step;
    }

    @Override
    public int getArraySize() {
        return arraySize;
    }


} // Fin AlgoritmoF61
