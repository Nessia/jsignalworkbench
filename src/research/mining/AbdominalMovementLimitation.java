package research.mining;

//import net.javahispano.jsignalwb.jsignalmonitor.TimeRepresentation;

class AbdominalMovementLimitation extends FluxLimitation {

    /**
     * LOW: {absoluteBeginingTime: HH:MM:SS} \t {GEN_AAPNEA|GEN_AHYPO} \t
     * {duration}  \n
     *
     * MEDIUM:{absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \n
     *
     * HIGH: {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t
     * {type: A|H}  \t {energy}  \t {Tipo : basalEnergyBefore}   \t {Tipo :
     * EVERYTHING: basalEnergyAfter}  \n
     *
     * {absoluteBeginingTime: HH:MM:SS} \t {GEN_LIM} \t {duration}  \t {type:
     * A|H}  \t {energy}  \t {basalEnergyBefore}   \t {Tipo :
     * basalEnergyAfter}  \n
     *
     * @param level DETAILLEVEL
     * @return String
     */
//    public String generateDescriptors(DETAILLEVEL level) {
//        StringBuilder descriptors = new StringBuilder(TimeRepresentation.timeToString(this.getAbsoluteBeginingTime(),
//                false, true, false));
//
//        if(level == DETAILLEVEL.LOW){
//            if(this.getType() == Type.APNEA){
//                descriptors.append("\tGEN_AAPNEA\t");
//            } else {
//                descriptors.append("\tGEN_AHYPO\t");
//            }
//
//            descriptors.append(this.getDuration());
//        } else {
//            descriptors.append("\tGEN_LIM\t" + this.getDuration());
//            if(this.getType() == Type.APNEA){
//                descriptors.append("\tA");
//            } else {
//                descriptors.append("\tH");
//            }
//
//            if(level == DETAILLEVEL.HIGH || level == DETAILLEVEL.EVERYTHING)
//                descriptors.append("\t" + this.getEnergy() + "\t"
//                        + this.getBasalEnergyBefore() + "\t"
//                        + this.getBasalEnergyAfter());
//        }
//
//        descriptors.append("\n");
//        return descriptors.toString();
//    }
}
