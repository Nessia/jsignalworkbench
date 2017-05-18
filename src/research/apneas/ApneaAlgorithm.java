package research.apneas;

import java.awt.Color;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.fuzzyutilities.representation.TrapezoidalDistribution;
import net.javahispano.jsignalwb.*;
import net.javahispano.jsignalwb.plugins.AlgorithmAdapter;
import net.javahispano.jsignalwb.plugins.framework.AlgorithmRunner;
import research.apneas.spo2.DetectorDesaturacionesWrapper;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class ApneaAlgorithm extends AlgorithmAdapter {


    private static final Logger LOGGER = Logger.getLogger(ApneaAlgorithm.class.getName());


    //@todo estudiar permitir pendientes de 0. 05
    private TrapezoidalDistribution pendienteNormal = new
            TrapezoidalDistribution( -0.1F, -0.1F, 0.1F, 0.1F);
    private TrapezoidalDistribution pendienteAscenso = new
            TrapezoidalDistribution(0.1F, 0.5F, 100.0F, 250.0F);
    private TrapezoidalDistribution pendienteDescenso = new
            TrapezoidalDistribution( -20.0F, -6.0F, -0.5F, -0.1F);
    private TrapezoidalDistribution valorNormal = new
                                                  TrapezoidalDistribution(90, 94, 110, 110);
    private TrapezoidalDistribution descensoAdmisibleRespectoBasal = new
            TrapezoidalDistribution( -90F, -60F, 20F, 30F); //@todo cambio reciente(-15F, -8F, 20F, 30F)
    private TrapezoidalDistribution valorAdmisibleDesaturacionRespectoBasal = new
            TrapezoidalDistribution( -180F, -70F, -8F, -2F);

    private int ventanaPendientesSaO2 = 3;
    private int ventanaBasalSatO2 = 150;
    //Se permiten X segundos de gap con pos 0 y se sigue considerando
    //parte del mismo ascenso o descenso
    private int persistencia = 4;
    //se filtran los valores menores que este limite en el de posibilidades
    private int limiteArrayPosiblidades = 10;
    //si un episodio de desaturacion duran mas que este limite
    //se supone que "algo va mal" y se desprecia el comienzo actual
    //que se estaba considerando para el episodio
    private int duracionMaximaEpisodiosDesaturacion = 100;

    //parparametros ventilacion
    /**
     * se dan en tanto por ciento
    private TrapezoidalDistribution magnitudHipoapnea = new TrapezoidalDistribution(0F, 0F, 0.6F, 0.6F);
    private TrapezoidalDistribution magnitudApnea = new TrapezoidalDistribution(0F, 0F, 0.15F, 0.15F);

    private TrapezoidalDistribution duracionHipoapnea = new TrapezoidalDistribution(4, 4, 9.9F, 9.9F);
    private TrapezoidalDistribution duracionApnea = new TrapezoidalDistribution(4, 4, 9.9F, 9.9F);
     */
    private TrapezoidalDistribution magnitudHipoapnea = new TrapezoidalDistribution(0F, 0F, 0.75F, 0.75F);
    private TrapezoidalDistribution magnitudApnea = new TrapezoidalDistribution(0F, 0F, 0.2F, 0.2F);

    private TrapezoidalDistribution duracionHipoapnea = new TrapezoidalDistribution(5, 10, 60, 140);
    private TrapezoidalDistribution duracionApnea = new TrapezoidalDistribution(5, 10, 100, 140);

    //se usan para calcular el valor basal de la apnea
    private int principioVentanaBasalFlujoApnea = 200;
    private int finVentanaBasalFlujoApnea = 200;
    //se usa para determinar el ancho de la ventana para promediar la apnea
    //***tiene una gran influencia en los resultados****
     /*Borra del parametro de abajo. No se usa.
      */
     private int anchoVentanaValorMedioApnea = 1;
    private int anchoVentanaValorMedioHipoApnea = 2;

    //se da en tanto por ciento
    private int limiteEnergia = 50;
    //private float limiteEnergiaAlto = 2F;
    private int principioIntervaloFiltroEnergia = 60;
    private int finIntervaloFiltroEnergia = 70;
    //se da en tanto por ciento
    private int relacionPrimerFiltroDerivada = 33;
    //private int principioIntervaloSegundoFiltroEnergia = 90;
    private int finIntervaloSegundoFiltroEnergia = 95;
    private boolean considerarSoloOndasNegativas = false;
    private float ventanaCalculoDeltas = 1.5F;
    private int persistenciaFlujo = 2;

    //relaciones entre el desaturaciones y flujo nasal
    private TrapezoidalDistribution relacionTemporal = new TrapezoidalDistribution(6, 10, 30, 45);

    public ApneaAlgorithm() {
       //Empty
    }


    @Override
    public void runAlgorithm(SignalManager sm, List<SignalIntervalProperties>
            signals, AlgorithmRunner ar) {

        Signal satO2;
        Signal nasal;
        SignalIntervalProperties intervalo = signals.get(0);
        Signal s = intervalo.getSignal();
        String nombreSenal = s.getName().trim().toLowerCase();
        /**/if (nombreSenal.contains("fl") || nombreSenal.contains("mov")) {
            for (SignalIntervalProperties si : signals) {
                nasal = si.getSignal(); /**/

                float[] datosNasal = nasal.getValues();
                float frecuencia = nasal.getSRate();

                SortedSet<Intervalo> hipoapneasIntervalos = calcularDescensosFlujo(nasal, datosNasal, frecuencia, false);
                /**/

                SortedSet<Intervalo> apneasIntervalos = calcularDescensosFlujo(nasal, datosNasal, frecuencia, true);
                this.generarRepresetacionAnotaciones(nasal, hipoapneasIntervalos, apneasIntervalos);
            }
            // satO2 = signals.get(1).getSignal();
        } else
        /*(nombreSenal.contains("nasal")||nombreSenal.contains("flujo"))*/
        {
            satO2 = s;
            //nasal = signals.get(1).getSignal();
            //float[] datosSatO2 = satO2.getValues();
//            float frecuencia = satO2.getSRate();
            TreeSet<EpisodioDesaturacion> episodiosDes = calcularDesaturacion(/*sm,*/ satO2/*, datosSatO2, frecuencia*/);

            for (Intervalo a : episodiosDes) {
                DesatDetector.generarEpisodioDesaturacion(
                        (EpisodioDesaturacion) a, Color.BLUE, satO2);
            }

        }
        /* else{
             JOptionPane.showMessageDialog(JSWBManager.getJSWBManagerInstance().getParentWindow(),
                     "No se han seleccionado las senhales correctas o no tienen el hombre adecuado",
                     "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }*/
        //  float[] datosSatO2 = satO2.getValues();
        // float[] datosNasal = nasal.getValues();
        //  float frecuencia = nasal.getSRate();

        /*/
               TreeSet<Intervalo> hipoapneasIntervalos = calcularDescensosFlujo(nasal, datosNasal, frecuencia, false);
               //

               TreeSet<Intervalo> apneasIntervalos = calcularDescensosFlujo(nasal, datosNasal, frecuencia, true);
               //
             for (Intervalo elem : apneasIntervalos) {
                            ReduccionFlujo.generarMarca(elem, nasal.getName(), "Apnea", Color.BLACK);
             }

             for (Intervalo elem : hipoapneasIntervalos) {
                            ReduccionFlujo.generarMarca(elem, nasal.getName(), "Hipoapnea", Color.BLACK);
             }
               /**/



        /**/
        /*/
             C c = new C(hipoapneasIntervalos, apneasIntervalos, episodios, nasal,satO2);
           /**/



        /*/
              Hipoapnea episodiosDeHipoapnea = new Hipoapnea(sm);
              episodiosDeHipoapnea.setRelacionTemporal(relacionTemporal);
              episodiosDeHipoapnea.setEpisodios(episodios);
              episodiosDeHipoapnea.setHipoapneasIntervalos(hipoapneasIntervalos);
              episodiosDeHipoapnea.setFrecuencia(nasal.getSRate());

              episodiosDeHipoapnea.detectar();
               /**/
    }

    private void generarRepresetacionAnotaciones(Signal nasal, SortedSet<Intervalo> hipoapneasIntervalos,
            SortedSet<Intervalo> apneasIntervalos) throws SignalNotFoundException {
        buscarApneasContenidasEnHipoapneas(hipoapneasIntervalos, apneasIntervalos);
        for (Intervalo a : hipoapneasIntervalos) {
            ReduccionFlujo.generarMarca(a, nasal.getName(), "Hipoapnea", Color.YELLOW);
        }
        for (Intervalo a : apneasIntervalos) {

            ReduccionFlujo.generarMarca(a, nasal.getName(), "Apnea", Color.RED);
        }

    }

    private void buscarApneasContenidasEnHipoapneas(SortedSet<Intervalo> hipoapneasIntervalos,
            SortedSet<Intervalo> apneasIntervalos) {
        TreeSet<Intervalo> apneasCopia = new TreeSet<Intervalo>(apneasIntervalos);
        TreeSet<Intervalo> hipoapneasCopia = new TreeSet<Intervalo>(hipoapneasIntervalos);
        for (Intervalo apnea : apneasCopia) {
            //obtenemos la apnea detectada Que comience justo despues de la hipoapnea que estamos analizando
            Intervalo h = hipoapneasCopia.floor(
                    apnea);
            //si la apnea esta contenida
            if (h != null && h.getFin() >= apnea.getFin()) {
                 if (h.getDuracion() * 0.80 < apnea.getDuracion()) {
                     hipoapneasIntervalos.remove(h);
                 } else {
                     apneasIntervalos.remove(apnea);
                 }
            }
        }
    }

    private TreeSet<EpisodioDesaturacion> calcularDesaturacion(/*SignalManager sm, */Signal s/*, float[] datos,*/
            /*float frecuencia*/) {
        DetectorDesaturacionesWrapper detector = new DetectorDesaturacionesWrapper(s);
        return new TreeSet<EpisodioDesaturacion>(detector.ejecutar(s.getValues()));
    }

    private SortedSet<Intervalo> calcularDescensosFlujo(Signal s, float[] datos, float frecuencia, boolean apnea) {
        ReduccionFlujo d = new ReduccionFlujo();
        d.setMagnitudApnea(magnitudApnea);
        d.setMagnitudHipoapnea(magnitudHipoapnea);
        d.setDatos(datos);
        d.setFechaBase(s.getStart());
        d.setFrecuencia(frecuencia);
        d.setFinVentanaBasalApnea(this.finVentanaBasalFlujoApnea);
        d.setPrincipioVentanaBasalApnea(this.principioVentanaBasalFlujoApnea);
        d.setAnchoVentanaValorMedioHipoApnea(anchoVentanaValorMedioHipoApnea);
//        d.setDuracionApnea(this.duracionApnea);
        d.setDuracionHipoapnea(this.duracionHipoapnea);
        d.setPrincipioIntervaloFiltroEnerigia(this.
                                              principioIntervaloFiltroEnergia);
        //d.setRelacionPrimerFiltroDerivada(this.relacionPrimerFiltroDerivada);
        d.setFinIntervaloFiltroEnerigia(this.
                                        finIntervaloFiltroEnergia);
        d.setLimiteEnergiaBajo(this.limiteEnergia);
        d.setFinIntervaloSegundoFiltroEnergia(this.
                                              finIntervaloSegundoFiltroEnergia);
        d.setConsiderarSoloOndasNegativas(considerarSoloOndasNegativas);
        d.setVentanaCalculoDeltas(this.ventanaCalculoDeltas);
        d.setPersistencia(persistenciaFlujo);
        return d.detectar(s, apnea);
    }

    @Override
    public boolean hasOwnConfigureGUI() {
        return true;
    }

    @Override
    public void launchConfigureGUI(JSWBManager jswbManager) {
        ConfigurarDialog c = new ConfigurarDialog(JSWBManager.getParentWindow(),
                                                  "configurar", //pendienteNormal,
                                                  ventanaPendientesSaO2//,
//                                                  this.valorNormal,
//                                                  this.
//                                                  descensoAdmisibleRespectoBasal,
//                                                  this.
//                                                  valorAdmisibleDesaturacionRespectoBasal
                                                  );

        c.setPendienteNormal(this.pendienteNormal);
        c.setPendienteAscenso(this.pendienteAscenso);
        c.setPendienteDescenso(this.pendienteDescenso);
        c.setValorNormal(this.valorNormal);
        c.setDescensoAdmisibleRespectoBasal(this.
                                            descensoAdmisibleRespectoBasal);
        c.setValorAdmisibleDesaturacionRespectoBasal(this.
                valorAdmisibleDesaturacionRespectoBasal);
        c.setHipoapnea(this.magnitudHipoapnea);
        c.setApnea(this.magnitudApnea);
        c.setVentanaPendientesSaO2(this.ventanaPendientesSaO2);
        c.setPrincipioVentanaBasalSatO2(this.ventanaBasalSatO2);
        c.setPrincipioVentanaBasalFlujoApnea(this.
                                             principioVentanaBasalFlujoApnea);
        c.setFinVentanaBasalFlujoApnea(this.finVentanaBasalFlujoApnea);
        c.setAnchoVentanaValorMedioApnea(this.anchoVentanaValorMedioApnea);
        c.setAnchoVentanaValorMedioHipoApnea(this.
                                             anchoVentanaValorMedioHipoApnea);
        c.setPersistencia(this.persistencia);
        c.setLimiteArrayPosiblidades(this.limiteArrayPosiblidades);
        c.setDuracionMaximaEpisodiosDesaturacion(this.
                                                 duracionMaximaEpisodiosDesaturacion);
        c.setDuracionApnea(this.duracionApnea);
        c.setDuracionHipoapnea(this.duracionHipoapnea);
        c.setPrincipioIntervaloFiltroEnergia(this.
                                             principioIntervaloFiltroEnergia);
        c.setRelacionPrimerFiltroDerivada(this.relacionPrimerFiltroDerivada);
        c.setFinIntervaloFiltroEnergia(this.
                                       finIntervaloFiltroEnergia);
        c.setLimiteEnergia(this.limiteEnergia);
        c.setFinIntervaloSegundoFiltroEnergia(this.
                                              finIntervaloSegundoFiltroEnergia);
        c.setConsiderarSoloOndasNegativas(considerarSoloOndasNegativas);
        c.setVentanaCalculoDeltas(this.ventanaCalculoDeltas);

        c.setRelacionTemporal(this.relacionTemporal);
        c.setPersistenciaFlujo(persistenciaFlujo);
        c.setModal(true);
        c.setVisible(true);

        if (c.isAceptar()) {
            pendienteNormal = c.getPendienteNormal();
            pendienteAscenso = c.getPendienteAscenso();
            pendienteDescenso = c.getPendienteDescenso();
            this.valorNormal = c.getValorNormal();
            descensoAdmisibleRespectoBasal = c.
                                             getDescensoAdmisibleRespectoBasal();
            this.magnitudHipoapnea = c.getHipoapnea();
            this.magnitudApnea = c.getApnea();
            this.ventanaPendientesSaO2 = c.getVentanaPendientesSaO2();
            ventanaBasalSatO2 = c.getPrincipioVentanaBasalSatO2();
            principioVentanaBasalFlujoApnea = c.
                                              getPrincipioVentanaBasalFlujoApnea();
            finVentanaBasalFlujoApnea = c.getFinVentanaBasalFlujoApnea();
            anchoVentanaValorMedioApnea = c.getAnchoVentanaValorMedioApnea();
            anchoVentanaValorMedioHipoApnea = c.
                                              getAnchoVentanaValorMedioHipoApnea();
            valorAdmisibleDesaturacionRespectoBasal = c.
                    getValorAdmisibleDesaturacionRespectoBasal();
            persistencia = c.getPersistencia();
            limiteArrayPosiblidades = c.getLimiteArrayPosiblidades();
            duracionMaximaEpisodiosDesaturacion = c.
                                                  getDuracionMaximaEpisodiosDesaturacion();
            duracionApnea = c.getDuracionApnea();
            duracionHipoapnea = c.getDuracionHipoapnea();
            principioIntervaloFiltroEnergia = c.
                                              getPrincipioIntervaloFiltroEnergia();
            relacionPrimerFiltroDerivada = c.getRelacionPrimerFiltroDerivada();
            finIntervaloFiltroEnergia = c.
                                        getFinIntervaloFiltroEnergia();
            limiteEnergia = c.getLimiteEnergia();
            finIntervaloSegundoFiltroEnergia = c.
                                               getFinIntervaloSegundoFiltroEnergia();
            considerarSoloOndasNegativas = c.isConsiderarSoloOndasNegativas();
            this.ventanaCalculoDeltas = c.getVentanaCalculoDeltas();
            persistenciaFlujo = c.getPersistenciaFlujo();

            relacionTemporal = c.getRelacionTemporal();
        }

    }

    @Override
    public String getDataToSave() {
        String s = pendienteNormal.toShortString() + "*" +
                   this.pendienteAscenso.toShortString() + "*" +
                   this.pendienteDescenso.toShortString() + "*" +
                   valorNormal.toShortString() + "*" +
                   descensoAdmisibleRespectoBasal.toShortString() + "*" +
                   valorAdmisibleDesaturacionRespectoBasal.toShortString() +
                   "*" +
                   this.ventanaPendientesSaO2 + "*" +
                   this.ventanaBasalSatO2 + "*" +
                   this.persistencia + "*" +
                   this.limiteArrayPosiblidades + "*" +
                   this.duracionMaximaEpisodiosDesaturacion + "*" +
//aqui comienza la configuraci+n de la apnea
                   this.magnitudHipoapnea.toShortString() + "*" +
                   this.magnitudApnea.toShortString() + "*" +
                   this.principioVentanaBasalFlujoApnea + "*" +
                   this.finVentanaBasalFlujoApnea + "*" +
                   this.anchoVentanaValorMedioApnea + "*" +
                   this.anchoVentanaValorMedioHipoApnea + "*" +
                   duracionApnea.toShortString() + "*" +
                   duracionHipoapnea.toShortString() + "*" +
                   principioIntervaloFiltroEnergia + "*" +
                   relacionPrimerFiltroDerivada + "*" +
                   finIntervaloFiltroEnergia + "*" +
                   limiteEnergia + "*" +
                   finIntervaloSegundoFiltroEnergia + "*" +
                   considerarSoloOndasNegativas + "*" +
                   this.ventanaCalculoDeltas /* + "*" +
                   persistenciaFlujo + "*" +
//la relacipnTemporal
                              this.relacionTemporal.toShortString() */
                   ;

        LOGGER.log(Level.INFO, "%s", "por decir algo " + s);
        return s;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getName() {
        return "apnea";
    }

    @Override
    public String getPluginVersion() {
        return "0";
    }

    @Override
    public String getShortDescription() {
        return "";
    }

    @Override
    public int numberOfSignalsNeeded() {
        return 3;
    }

    @Override
    public boolean hasDataToSave() {
        return true;
    }

    @Override
    public boolean showInGUIOnthe(GUIPositions gUIPositions) {
        return gUIPositions == GUIPositions.MENU || gUIPositions == GUIPositions.TOOLBAR;
    }
}

/*filtro*
         float[] d= new float[datos.length];
         d[0]=datos[0];
         d[1]=datos[1];
         float media =0;
         for (int i = 2; i < d.length; i++) {
            d[i]=0.99F*d[i-1]+0.0049F*datos[i]+0.0049F*datos[i-1];
            media+=d[i];
         }
         media/=(d.length-2);

         float[] d3= new float[datos.length];
         for (int i = 0; i < d.length; i++) {
    d3[i]=d[i]/media;
         }
         Signal s2 = new Signal("Hipoapnea",
              d,
              frecuencia, s.getStart(), "posibilidad");
         sm.addSignal(s2);       Signal s3 = new Signal("Hipoapnea2",
              d3,
              frecuencia, s.getStart(), "posibilidad");
         sm.addSignal(s3);

         /*filtro*/
