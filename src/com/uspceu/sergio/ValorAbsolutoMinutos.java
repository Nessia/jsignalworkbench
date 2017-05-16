package com.uspceu.sergio;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.uspceu.SimpleAlgorithm;
import net.javahispano.jsignalwb.Signal;
import net.javahispano.jsignalwb.SignalManager;

/**
 *
 * @author Sergio
 */
public class ValorAbsolutoMinutos extends SimpleAlgorithm {

    /**
     *
     */
    private static final long serialVersionUID = 2201108459471301236L;
    private static final Logger LOGGER = Logger.getLogger(ValorAbsolutoMinutos.class.getName());

    @Override
    public String getName() {
        return "Calculo del valor absoluto minuto a minuto";
    }

    @Override
    public void runAlgorithm(SignalManager signalManager, Signal signal, float[] datos, float fs) {

        Signal biometrixAcumulado = signalManager.getSignal("Minuto a minuto de Acumulado de Biometrix");
        Signal biometrix = signalManager.getSignal("Biometrix");
        Signal bascula = signalManager.getSignal("Minuto a minuto de Bascula");

        float[] arrayBiometrixAcumulado;
        float[] arrayBascula;

        float[] arrayBiometrix = biometrix.getValues();
        analizaOy100(arrayBiometrix);

        arrayBiometrixAcumulado = biometrixAcumulado.getValues();
        arrayBascula = bascula.getValues();
        float[] newData = new float[arrayBascula.length];
        float[] newData2 = new float[arrayBascula.length];

        for (int i = 0; i < arrayBascula.length; i++) {
            newData[i] = Math.abs(arrayBiometrixAcumulado[i] - arrayBascula[i]);
            newData2[i] = arrayBiometrixAcumulado[i] - arrayBascula[i];
        }

        Signal square = new Signal("Error absoluto minuto a minuto",
                newData, biometrixAcumulado.getSRate(), signal.getStart(), "Unidades");
        square.adjustVisibleRange();
        signalManager.addSignal(square);
        Signal square2 = new Signal("Error minuto a minuto",
                newData2, biometrixAcumulado.getSRate(), signal.getStart(), "Unidades");
        square2.adjustVisibleRange();
        signalManager.addSignal(square2);
    }

//    private enum Estados { SALTAR_CERO, SALTAR_CIEN, SALTAR_0_100, SALTAR_MAYOR_CIEN}

//    private void analizaOy100(float[] arrayBiometrix) {
//       int transicionDesdeCero = 0;
//       int transicionDesdeCien = 0;
//       float sumaCeros = 0;
//       float sumaCienes = 0;
//
//       Estados estado = Estados.SALTAR_CERO;
//       for(int i=0; i<arrayBiometrix.length; i++){
//           switch(estado){
//           case SALTAR_CERO:
//               if(arrayBiometrix[i] != 0){
//                   transicionDesdeCero++;
//                   sumaCeros += arrayBiometrix[i];
//                   LOGGER.log(Level.INFO, "%s","Sumando a Ceros " + arrayBiometrix[i]);
//                   estado = Estados.SALTAR_0_100;
//               }
//               break;
//           case SALTAR_0_100:
//               if(arrayBiometrix[i] == 0){
//                   estado = Estados.SALTAR_CERO;
//               } else if(arrayBiometrix[i]<0 || arrayBiometrix[i]>=100){
//                   estado = Estados.SALTAR_CIEN;
//               }
//               break;
//           case SALTAR_CIEN:
//               if(arrayBiometrix[i] == 0){
//                 estado = Estados.SALTAR_CERO;
//               } else if(arrayBiometrix[i] != 100){
//                   transicionDesdeCien++;
//                 sumaCienes += (arrayBiometrix[i] - 100);
//                 LOGGER.log(Level.INFO, "%s", "sumando a cienes " + (arrayBiometrix[i] - 100));
//                 estado = Estados.SALTAR_MAYOR_CIEN;
//               }
//               break;
//           case SALTAR_MAYOR_CIEN:
//               if(arrayBiometrix[i]<=100){
//                   estado = Estados.SALTAR_CERO;
//               }
//               break;
//           }
//       }
//
//      LOGGER.log(Level.INFO, "%s", "Ceros " + (transicionDesdeCero == 0? "infinito" : sumaCeros / transicionDesdeCero));
//      LOGGER.log(Level.INFO, "%s", "Cien " + (transicionDesdeCien == 0? "infinito" : sumaCienes / transicionDesdeCien));
//    }

    private void analizaOy100(float[] arrayBiometrix) {
        int transicionDesdeCero = 0;
        int transicionDesdeCien = 0;
        float sumaCeros = 0;
        float sumaCienes = 0;

        int i = 0;

        while (i < arrayBiometrix.length) {
            // Saltar ceros
            while (i < arrayBiometrix.length && arrayBiometrix[i] == 0) {
                i++;
            }

            // Salir si se ha llegado al final del array
            if (i == arrayBiometrix.length) {
                break;
            }

            transicionDesdeCero++;
            sumaCeros += arrayBiometrix[i];
            LOGGER.log(Level.INFO, "%s","Sumando a Ceros " + arrayBiometrix[i]);

            // Saltamos valores entre (0,100) no incluidos
            while (i < arrayBiometrix.length && arrayBiometrix[i] > 0 && arrayBiometrix[i] < 100) {
                i++;
            }

            // Salir si se ha llegado al final del array
            if (i == arrayBiometrix.length) {
                break;
            }

            if (arrayBiometrix[i] == 0) {//llegamos a 100
                continue;
            }

            // Si es 100 saltamos los valores
            while (i < arrayBiometrix.length && arrayBiometrix[i] == 100) {
                i++;
            }

            // Salir si se ha llegado al final del array
            if (i == arrayBiometrix.length) {
                break;
            }

            // Si hay un cero, empezamos de nuevo el bucle
            if (arrayBiometrix[i] == 0) {
                continue;
            }

            // Si llega a aquí encontramos uns cien sin un cero después con otro valor a continuación
            transicionDesdeCien++;
            sumaCienes += (arrayBiometrix[i] - 100);
            LOGGER.log(Level.INFO, "%s", "sumando a cienes " + (arrayBiometrix[i] - 100));


            // Saltamos valores mayores que cien
            while (i < arrayBiometrix.length && arrayBiometrix[i] > 100) {
                i++;
            }
        }

        LOGGER.log(Level.INFO, "%s", "Ceros " + (transicionDesdeCero == 0? "infinito" : sumaCeros / transicionDesdeCero));
        LOGGER.log(Level.INFO, "%s", "Cien " + (transicionDesdeCien == 0? "infinito" : sumaCienes / transicionDesdeCien));

    }

}
