package net.javahispano.jsignalwb;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SignalConstants {

    public static final String SENAL_FLUJO = "Flujo";
    public static final String SENAL_SATURACION_02 = "Sat02";
    public static final String SENAL_MOVIMIENTO_ABDOMINAL = "Movimiento abdominal";
    public static final String SENAL_MOVIMIENTO_TORACICO = "Movimiento toracico";

    public static final String SENAL_GUANINE = "G";
    public static final String SENAL_ADENINE = "A";
    public static final String SENAL_THYMINE = "T";
    public static final String SENAL_CYTOSINE = "C";

    public static final String SENAL_SA_O2 = "SaO2";

    //public static final String[] NOMBRES = {SENAL_FLUJO, SENAL_SATURACION_O2, SENAL_MOVIMIENTO_ABDOMINAL,
//            SENAL_MOVIMIENTO_TORACICO};
    public static final List<String> NOMBRES = Collections.unmodifiableList(Arrays.asList(SENAL_FLUJO,
            SENAL_SATURACION_02, SENAL_MOVIMIENTO_ABDOMINAL, SENAL_MOVIMIENTO_TORACICO));

    private SignalConstants() {
        // ocultar constructor
    }

}
