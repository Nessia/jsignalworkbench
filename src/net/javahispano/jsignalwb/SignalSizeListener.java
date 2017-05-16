/*
 * SignalManagerSizeListener.java
 *
 * Created on 16 de mayo de 2007, 18:18
 */
package net.javahispano.jsignalwb;

import java.io.Serializable;

/**
 *
 * @author Roman
 */
public interface SignalSizeListener extends Serializable {
    public void signalSizeActionPerformed(SignalSizeEvent event);
}
