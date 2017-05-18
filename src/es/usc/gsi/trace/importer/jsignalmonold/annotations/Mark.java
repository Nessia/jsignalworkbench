package es.usc.gsi.trace.importer.jsignalmonold.annotations;

/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Mark extends ClinicalEvent {

    /**
     *
     */
    private static final long serialVersionUID = 4087419635725536796L;

    private boolean isMit;
    private byte codigoMit;

    public Mark() {
        this.tipo = ClinicalEvent.Tipo.MARCA;
    }

    public boolean getIsMit() {
        return isMit;
    }

    public void setIsMit(boolean _isMit) {
        isMit = _isMit;
    }

    public byte getCodigoMit() {
        return codigoMit;
    }

    public void setCodigoMit(byte _codigoMit) {
        codigoMit = _codigoMit;
    }
}
