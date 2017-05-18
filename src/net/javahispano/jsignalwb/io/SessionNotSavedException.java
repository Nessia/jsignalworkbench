package net.javahispano.jsignalwb.io;

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
public class SessionNotSavedException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -7674722707092830927L;

    public SessionNotSavedException() {
        super();
    }

    SessionNotSavedException(String message) {
        super(message);
    }

}
