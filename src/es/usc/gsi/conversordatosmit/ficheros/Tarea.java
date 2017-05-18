package es.usc.gsi.conversordatosmit.ficheros;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.usc.gsi.conversordatosmit.ficheros.lectura.LeeFicheroDat;
import es.usc.gsi.conversordatosmit.interfaz.ControladorInterfaz;

class Tarea extends Thread implements Cancelar {

    /**
     *
     */
    private static final long serialVersionUID = -2699156632036435382L;

    private static final Logger LOGGER = Logger.getLogger(Tarea.class.getName());

    // TODO transient okay? @vanesa
    private transient ControladorInterfaz controlInterfaz = ControladorInterfaz.getControlador();
    private Parametro[] parametros = null;
    private List<FicheroHead> ficherosHead;
    private boolean cancel = false;
    private boolean memoryError = false;

//**********************************************************************************************

     Tarea(List<FicheroHead> fh) {
         this.ficherosHead = fh;
         this.setPriority(Thread.MIN_PRIORITY);
     }

//**********************************************************************************************

     @Override
     public void cancelar() {
         cancel = true;
     }

//**********************************************************************************************

     public Parametro[] getParametros() {
         return parametros;
     }

//**********************************************************************************************

     protected boolean hasMemoryError() {
         return memoryError;
     }

//**********************************************************************************************

     @Override
     public void run() {
         List<Parametro> copiaParamTemp = new ArrayList<Parametro>();

         try {
             int total = 1;
             for(FicheroHead fh : ficherosHead){
             //for (int i = 0; i < ficherosHead.size(); i++) {
                 //FicheroHead fh = ficherosHead.elementAt(i);
                 Parametro[] p = fh.getParametros();
                 for (int j = 0; j < p.length; j++) {
                     if (p[j].getActivado()) {
                         if (cancel) {
                             controlInterfaz.cierraIndicadorProgreso();
                             return;
                         }
                         int[] pValorTemp = (new LeeFicheroDat(fh, p[j])).
                                            getTodosValores(); // Lectura de todos los valores
                         p[j].setValores(pValorTemp); // Asigna los valores al parametro.
                         copiaParamTemp.add(p[j]);
                         controlInterfaz.notificaProgreso(total++);
                     } // Fin if p[j]
                 } // Fin for j
             } // Fin for i

             // Conversion a array.
             parametros = new Parametro[copiaParamTemp.size()];

             for (int i = 0; i < parametros.length; i++) {
                 Parametro pTemp = copiaParamTemp.get(i);
                 parametros[i] = new Parametro(pTemp); // Creamos un array con copias de parametros
                 parametros[i].setValores(pTemp.getValores()); // Referenciamos los valores de un parametro hacia otro.
                 pTemp.setValores(null);
             }
         } catch (OutOfMemoryError err) {
             LOGGER.log(Level.SEVERE, err.getMessage(), err);
             parametros = null;
             memoryError = true;
             controlInterfaz.cierraIndicadorProgreso();
         }
         controlInterfaz.cierraIndicadorProgreso();
     } // End run()
}
