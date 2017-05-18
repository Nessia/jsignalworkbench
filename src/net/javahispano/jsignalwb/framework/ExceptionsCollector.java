/*
 * ExceptionsCollector.java
 *
 * Created on 10 de mayo de 2007, 10:34
 */

package net.javahispano.jsignalwb.framework;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 *
 * @author Roman
 */
public class ExceptionsCollector {

    private ArrayList<Exception> exceptions;
    private Component parentComponent;

    /** Creates a new instance of ExceptionsCollector */
    public ExceptionsCollector(Component parentComponent) {
        exceptions = new ArrayList<Exception>();
        this.parentComponent = parentComponent;
    }

    public void addException(Exception exception) {
        exceptions.add(exception);
    }

    public void showExceptions(String anterior) {

        int van = 0;
        if (!exceptions.isEmpty()) {
             StringBuilder s = new StringBuilder("<p><Font Color=RED>" + anterior + "</Font><p>");
            for (Exception e : exceptions) {
                s.append("<p>-" + e.getMessage());
                van++;
                if (van > 10) {
                    s.append("<p>- Hay mas problemas....");
                    break;
                }
            }
            JOptionPane.showMessageDialog(parentComponent,
                                          "<html>" + s + "<p><p></html>",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
