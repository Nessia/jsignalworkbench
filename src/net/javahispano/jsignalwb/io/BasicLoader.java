/*
 * basicLoader.java
 *
 * Created on 24 de mayo de 2007, 18:18
 *
 */

package net.javahispano.jsignalwb.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.javahispano.jsignalwb.SignalManager;
import net.javahispano.jsignalwb.plugins.LoaderAdapter;

/**
 * {@link Loader} que permite cargar en el entorno ficheros ASCII donde cada
 * senhal se organiza como una columna y a distintas columnas (senhales) se
 * separan mediante espacios en blanco o tabulaciones.
 *
 * @author This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/). Copyright 2006-2007 Roman Segador y
 *   Abraham Otero
 */
public class BasicLoader extends LoaderAdapter {

    /**
     *
     */
    private static final long serialVersionUID = 4569098628633139057L;
    private static final Logger LOGGER = Logger.getLogger(BasicLoader.class.getName());

    /** Creates a new instance of basicLoader */
    public BasicLoader() {
        // Vacio
    }

    @Override
    public String getName() {
        return "basicLoader";
    }

    @Override
    public String getShortDescription() {
        return "Basic Loader";
    }

    @Override
    public List<String> getAvalaibleExtensions() {
        List<String> ext = new ArrayList<String>();
        ext.add("txt");
        return ext;
    }

    @Override
    protected float[][] loadSignals(File f) throws Exception {
        float[][] values = null;
        FileReader fr2 = null;
        FileReader fr = null;
        BufferedReader input = null;
        try {
            fr = new FileReader(f);
            input = new BufferedReader(fr);
            String line;
            StringTokenizer st;
            int index1 = 0;
            int pos = 0;
            String temp = "";
            while ((line = input.readLine()) != null) {

                st = new StringTokenizer(line, "; \t", false);
                if (index1 == 0) {
                    while (st.hasMoreTokens()) {
                        temp = st.nextToken();

                        if (!";".equals(temp)) {
                            pos++;
                        }
                    }
                }
                index1++;
            }

            values = new float[pos][index1];
            input.close();
            fr.close();

            fr2 = new FileReader(f);
            input = new BufferedReader(fr2);

            index1 = 0;
            pos = 0;
            temp = "";
            while ((line = input.readLine()) != null) {
                st = new StringTokenizer(line, "; \t");
                while (st.hasMoreTokens()) {
                    temp = st.nextToken();
                    values[pos][index1] = Float.parseFloat(temp);//@CNIC *10000000;
                    pos++;

                }
                pos = 0;
                index1++;
            }
        } catch(IOException ioe){
            throw ioe;
        } finally {
            if(fr != null){
                try {
                     fr.close();
                } catch (IOException e) {
                    LOGGER.log(Level.FINEST, e.getMessage(), e);
                }
            }
            if(fr2 != null){
                try {
                     fr2.close();
                } catch (IOException e) {
                    LOGGER.log(Level.FINEST, e.getMessage(), e);
                }
            }
        }
        return values;

    }
    //@CNIC
    @Override
    protected boolean load(File f, SignalManager sm) throws Exception {
        boolean flag = true;
             super.load(f, sm);/*
        ((Signal)sm.getSignals().toArray()[0]).setFrecuency(500);
        ((Signal)sm.getSignals().toArray()[1]).setFrecuency(500);
        ((Signal)sm.getSignals().toArray()[2]).setFrecuency(500);//@CNIC
       for (Signal s: sm.getSignals()) {
                s.setFrecuency(100);
            }*/
        return flag;
    }
}
