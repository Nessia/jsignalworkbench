package research.mining;

import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

public class ThoracicMovementLimutation extends FluxLimitation {

    public ThoracicMovementLimutation() {}

    public ThoracicMovementLimutation(long absoluteBeginingTime, long duration) {
        super(absoluteBeginingTime, duration);
    }

    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_TAPNEA|GEN_THYPO} \t
     * {duration} \n
     *
     * MEDIUM:{absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \n
     *
     * HIGH: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * EVERYTHING: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}
     * \t {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
    public String generateDescriptors(DETAILLEVEL level) {
        StringBuilder descriptors = new StringBuilder(TimeRepresentation.timeToString(
                            this.getAbsoluteBeginingTime(),false,true,false));

        if(level == DETAILLEVEL.LOW){
            if(this.getType() == Type.APNEA){
                descriptors.append("\tGEN_TAPNEA\t");
            } else{
                descriptors.append("\tGEN_THYPO\t");
            }

            descriptors.append(Long.toString(this.getDuration()));
        }
        else{
            descriptors.append("\tGEN_LIM\t" + this.getDuration());
            if(this.getType() == Type.APNEA){
                descriptors.append("\tA");
            } else {
                descriptors.append("\tH");
            }

            if(level == DETAILLEVEL.HIGH || level == DETAILLEVEL.EVERYTHING)
                descriptors.append("\t" + this.getEnergy() + "\t"
                        + this.getBasalEnergyBefore() + "\t"
                        + this.getBasalEnergyAfter());
        }

        descriptors.append("\n");
        return descriptors.toString();
    }
}
