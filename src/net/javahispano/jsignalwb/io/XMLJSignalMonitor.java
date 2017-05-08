package net.javahispano.jsignalwb.io;

import org.jdom.Element;

/**
 *
 * @author Roman
 */
class XMLJSignalMonitor extends Element {

    /**
     *
     */
    private static final long serialVersionUID = -5052457133638309055L;

    /** Creates a new instance of XMLJSignalMonitor */
    public XMLJSignalMonitor(float frecuency, long scrollPosition,
                             String leftPanelConfig) {
        super("JSignalMonitor");
        setAttribute("Frecuency", String.valueOf(frecuency));
        setAttribute("ScrollPosition", String.valueOf(scrollPosition));
        setAttribute("LeftPanelConfig", leftPanelConfig);

    }

}
