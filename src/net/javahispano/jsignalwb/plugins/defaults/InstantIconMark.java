/*
 * InstantIconMark.java
 *
 * Created on 9 de julio de 2007, 13:49
 */

package net.javahispano.jsignalwb.plugins.defaults;


/**
 *
 * @author Roman Segador
 */
public class InstantIconMark extends DefaultInstantMark {

    /**
     *
     */
    private static final long serialVersionUID = -3174982684139886793L;

    public InstantIconMark() {
        super();
        setIsImage(true);
    }

    @Override
    public String getName() {
        return "Instant Icon Mark";
    }
}
