/*
 * JSignalMonitorModeEvent.java
 *
 * Created on 1 de agosto de 2007, 14:12
 */

package net.javahispano.jsignalwb.jsignalmonitor;

/**
 *
 * @author Roman Segador
 */
public class JSignalMonitorModeEvent {
//    public static final int REPRESENT_XY_VALUES = 1;
//    public static final int MARK_CREATION = 2;
//    public static final int INTERVAL_SELECTION = 3;
   public enum Modo { REPRESENT_XY_VALUES, MARK_CREATION, INTERVAL_SELECTION }

    private Modo mode;
    private boolean value;

    /*
     * Constructor
     */

    public JSignalMonitorModeEvent(Modo mode, boolean value) {
//        if (mode != REPRESENT_XY_VALUES &&
//            mode != MARK_CREATION &&
//            mode != INTERVAL_SELECTION) {
//            throw new RuntimeException("Invalid property on JSignalMonitorModeEvent");
//        }
        this.mode = mode;
        this.value = value;
    }

    public Modo getMode() {
        return mode;
    }

    public boolean getValue() {
        return value;
    }

}
