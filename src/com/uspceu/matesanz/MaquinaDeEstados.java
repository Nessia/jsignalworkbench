package com.uspceu.matesanz;

enum Estados { NO_ACTIVIDAD, TRANSICION, ACTIVIDAD }

class MaquinaDeEstados {

    private final float muestrasEsperaEnTransicion;
    private final float muestrasEsperaEnActividad;
    private float tiempoEsperaEnTransicion = 0.5F;
    private float tiempoEsperaEnActividad = 0.5F;
    private float muestrasEsperandoEnTransicion;
    private float muestrasEsperandoEnActividad;
    protected Estados estadoMaquina;
    protected Threshold threshold;

    MaquinaDeEstados(float fs) {
        muestrasEsperaEnTransicion = tiempoEsperaEnTransicion * fs;
        muestrasEsperaEnActividad = tiempoEsperaEnActividad * fs;
        estadoMaquina = Estados.NO_ACTIVIDAD;
    }

    public void funcionaMaquina(float dato) {
        final float THRESHOLD = 1.5f * threshold.getMediana();
        switch(estadoMaquina){
           case NO_ACTIVIDAD:
              if (dato >= THRESHOLD) {
                 estadoMaquina = Estados.TRANSICION;
              }
              break;
           case TRANSICION:
              if (dato < THRESHOLD) {
                 estadoMaquina = Estados.NO_ACTIVIDAD;
                 muestrasEsperandoEnTransicion = 0;
              } else{ // if (dato >= THRESHOLD) {
                 if (muestrasEsperandoEnTransicion >= muestrasEsperaEnTransicion) {
                     estadoMaquina = Estados.ACTIVIDAD;
                     muestrasEsperandoEnActividad=0;
                 } else {
                     muestrasEsperandoEnTransicion++;
                 }
              }
              break;
           case ACTIVIDAD:
              if (dato <= THRESHOLD && muestrasEsperandoEnActividad >= muestrasEsperaEnActividad) {
                 estadoMaquina = Estados.NO_ACTIVIDAD;//transición?
                 muestrasEsperandoEnActividad=0;
             }
             else if (dato >= THRESHOLD) {
                 muestrasEsperandoEnActividad=0;

             } else {
                 muestrasEsperandoEnActividad++;
             }
              break;
        }

    }

    public Estados getEstadoMaquina() {
        return estadoMaquina;
    }

    public void setEstadoMaquina(Estados estadoMaquina) {
        this.estadoMaquina = estadoMaquina;
    }

    public Threshold getThreshold() {
        return threshold;
    }

    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }

    public void setTiempoEsperaEnTransicion(float tiempo2) {
        this.tiempoEsperaEnTransicion = tiempo2;
    }

    public float getTiempoEsperaEnActividad() {
        return tiempoEsperaEnActividad;
    }

    public void setTiempoEsperaEnActividad(float tiempo3) {
        this.tiempoEsperaEnActividad = tiempo3;
    }

}
