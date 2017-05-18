package research.mining;

import java.util.LinkedList;
import java.util.List;
import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;


class FluxLimitation extends TemporalEvent {
    enum Type { APNEA, HIPOAPNEA }

    //Energia de la senhal normalizada por la duracion del episodio
    private float energy;
    //Buscar 10 segundos de senhal de flujo respiratorio sin limitacion justo antes
    //de la senhal y calcular su energia normalizada por su duracion.
    private float basalEnergyBefore;
    //Buscar 10 segundos de senhal de flujo respiratorio sin limitacion justo Despues
    //de la senhal y calcular su energia normalizada por su duracion.
    private float basalEnergyAfter;
    //si es hipoapnea o apnea
    private Type type;
    //lista de limitaciones de movimiento abdominal
    private List<FluxLimitation> abdominalLimitations = new LinkedList<FluxLimitation>();
    //lista de limitaciones de movimiento toracico
    private List<FluxLimitation> thoracicLimitations = new LinkedList<FluxLimitation>();

//    public FluxLimitation() {
//        // vacio
//    }

//    FluxLimitation(long absoluteBeginingTime, long duration) {
//        super(absoluteBeginingTime, duration);
//    }


    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_APNEA|GEN_HYPO} \t
     * {duration} \n
     *
     * MEDIUM: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \n
     *
     * HIGH: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * EVERYTHING: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}
     * \t {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \t
     * //info de la limitacion toracica mas grande (mas tiempo)
     * \t {duration}  \t {type: A|H}  \t {energy}  \t
     * {Tipo : basalEnergyBefore}   \t {Tipo : basalEnergyAfter}  \t
     * //info de la limitacion abdominal mas grande
     * \t {duration}  \t {type: A|H}  \t {energy}  \t
     * {Tipo : basalEnergyBefore}   \t {Tipo : basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    String generateDescriptors(DETAILLEVEL level, long beginingRecording) {
        //@Emma que valores hay que poner si no hay ninguna limitacion toracica o abdominal??(duration == 0)
        // ahora mismo no se añade nada al string que se devuelve

        //long duration;
        AbdominalMovementLimitation abdLim = new AbdominalMovementLimitation();
        ThoracicMovementLimutation thoraxLim = new ThoracicMovementLimutation();

        abdLim.setDuration(0);
        thoraxLim.setDuration(0);

        StringBuilder descriptors = new StringBuilder(TimeRepresentation.timeToString(
                                this.getAbsoluteBeginingTime(),false,true,false));

        if(level == DETAILLEVEL.LOW){
            descriptors.append("\tGEN_" + (this.getType() == Type.APNEA ? "APNEA" : "HYPO"));
            descriptors.append("\t" + this.getDuration());
        } else{
            descriptors.append("\t" + (this.getAbsoluteBeginingTime() - beginingRecording)/1000F
                     + "\t" + this.getDuration()/1000F);
            descriptors.append("\t" + (this.getType() == Type.APNEA ? "A" : "H"));

            if(level == DETAILLEVEL.HIGH || level == DETAILLEVEL.EVERYTHING){
                descriptors.append("\t" + this.getEnergy()
                        + "\t" + this.getBasalEnergyBefore()
                        + "\t" + this.getBasalEnergyAfter());
                if(level == DETAILLEVEL.EVERYTHING){
                    descriptorWithEverything(thoraxLim, thoracicLimitations, descriptors);
                    descriptorWithEverything(abdLim, abdominalLimitations, descriptors);
                }
            }
        }
        return descriptors.toString();
    }


    private void descriptorWithEverything(FluxLimitation limit, List<FluxLimitation> limitations, StringBuilder descriptors){
        FluxLimitation maxLim = limit;
       for(FluxLimitation lim: limitations){
           if(lim.getDuration() > maxLim.getDuration()){
               maxLim = lim;
           }
       }
       duration = maxLim.getDuration();
       if(duration!=0){
           descriptors.append("\t" + maxLim.getDuration());
           descriptors.append("\t" + (maxLim.getType() == Type.APNEA ? "A" : "H"));
           descriptors.append("\t" + maxLim.getEnergy()
                   + "\t" + maxLim.getBasalEnergyBefore()
                   + "\t" + maxLim.getBasalEnergyAfter());
       }
    }

//    public void addAbdomenLimitation(AbdominalMovementLimitation l) {
//        abdominalLimitations.add(l);
//    }
//
//    public void addToraxLimitation(ThoracicMovementLimutation l) {
//        thoracicLimitations.add(l);
//    }

    public float getBasalEnergyAfter() {
        return basalEnergyAfter;
    }

    public float getBasalEnergyBefore() {
        return basalEnergyBefore;
    }

    public float getEnergy() {
        return energy;
    }

    public research.mining.FluxLimitation.Type getType() {
        return type;
    }

    public List<FluxLimitation> getAbdominalLimitations(){
        return new LinkedList<FluxLimitation>(abdominalLimitations);
    }

    public List<FluxLimitation> getThoracicLimitations(){
        return new LinkedList<FluxLimitation>(thoracicLimitations);
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public void setBasalEnergyBefore(float basalEnergyBefore) {
        this.basalEnergyBefore = basalEnergyBefore;
    }

    public void setBasalEnergyAfter(float basalEnergyAfter) {
        this.basalEnergyAfter = basalEnergyAfter;
    }

    public void setType(research.mining.FluxLimitation.Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
