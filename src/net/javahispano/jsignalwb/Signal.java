package net.javahispano.jsignalwb;

import java.awt.Color;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import net.javahispano.jsignalwb.jsignalmonitor.ChannelProperties;
import net.javahispano.jsignalwb.plugins.GridPlugin;
import net.javahispano.jsignalwb.plugins.MarkPlugin;
import net.javahispano.jsignalwb.plugins.defaults.DefaultGridPlugin;
import org.joda.time.DateTime;

/**
 * Representa una senhal dentro de JSignalWorkbench. Contiene informacion tanto
 * sobre las senhales si misma como sobre su representacion en pantalla.
 *
 * @author Roman Segador y Abraham Otero
 *    Copyright 2006-2007. This software is under the Apache License Version 2.0
 *   (http://www.apache.org/licenses/).
 */
public class Signal implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7219179744727512231L;
    /** Almacena el nombre de la senal*/
    private String name;
    /** Almacena el valor de la senal en cada instante de tiempo*/
    protected float[] values;
    protected short[] emphasis;
    private List<MarkPlugin> marks;
    private boolean hasColors;
    private boolean imaginary;
    /**Frecuencia de muestreo de la senal*/
    private float frecuency;
    /**Instante de inicio de la senal*/
    private long start;
    /** Almacena la magnitud de la senal*/
    private String magnitude;
    private ChannelProperties properties;
    private GridPlugin grid;
    private Map<String, Object> externalProperties;
    //private boolean ownGrid;

    /**
     * Crea una nueva instancia de Signal. sName indica el nombre de la senal
     * mientras que sValues almacena los valores de la senal ordenados para cada
     * instante de tiempo. Se asigna por defecto 1 al rango de tiempo entre
     * valores y el instance actual al tiempo de inicio de la senal. Se indica
     * que la magnitud es desconocida
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     */
    public Signal(String sName, float[] sValues) {
        this(sName, sValues, null);
    }

    /**
     * Crea una nueva instancia de Signal.
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     */
    public Signal(String sName, float[] sValues, short[] emphasis) {
        this(sName, sValues, 1, new DateTime().getMillis(), "Unknown", emphasis);
    }

    /**
     * Crea una nueva instancia de Signal.
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     * @param sStart long instante de inicio de la senhal medido en milisegundos desde
     *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     * @param sMagnitude magnitud de la senhal.
     */
    public Signal(String sName, float[] sValues, float frec, long sStart, String sMagnitude) {
        this(sName, sValues, frec, sStart, sMagnitude, null);
    }

    /**
     * Crea una nueva instancia de Signal.
     *
     * @param sName nombre de la senhal.
     * @param sValues valores de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     * @param sStart long instante de inicio de la senhal medido en milisegundos desde
     *   00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     * @param sMagnitude magnitud de la senhal.
     * @param emphasis Nivel de enfasis con que se debe representar la senhal.
     *   Debe contener valores entre [0, 100].
     */
    public Signal(String sName, float[] sValues, float frec, long sStart, String sMagnitude, short[] emphasis) {
        name = sName;
        values = sValues;
        frecuency = frec;
        start = sStart;
        imaginary = false;
        properties = new ChannelProperties(sName, sStart, frec, sValues.length);
        setMagnitude(sMagnitude);
        if (emphasis != null) {
            this.emphasis = emphasis;
            if (emphasis.length == sValues.length) {
                setHasEmphasis(true);
            } else {
                setHasEmphasis(false);
            }
        } else {
            setHasEmphasis(false);
        }
        marks = new ArrayList<MarkPlugin>();
        DefaultGridPlugin defaultGridPlugin = new DefaultGridPlugin();
        setGrid(defaultGridPlugin);
        defaultGridPlugin.setSignal(this);
        externalProperties = Collections.synchronizedMap(new HashMap<String, Object>());
    }

    /*private short[] getColors(int i) {
         short[] temp = new short[i];
         for (int index = 0; index < temp.length; index++) {
         temp[index] = (short) (index % 100);
         }
         return temp;
         }*/
    /**
     * Devuelve el objeto {@link ChannelProperties} asociado con esta senhal.
     *
     * @return {@link ChannelProperties}
     */
    public ChannelProperties getProperties() {
        return properties;
    }

    /**
     * getName
     *
     * @return nombre asociado con la senhal.
     */
    public String getName() {
        return name;
    }

    /**
     * getSRate
     *
     * @return frecuencia de muestreo de la senhal medida en hercios.
     */
    public float getSRate() {
        return frecuency;
    }

    /**
     * getStart
     *
     * @return instante de inicio de la senhal medido en milisegundos
     * desde 00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     */
    public long getStart() {
        return start;
    }

    /**
     * getMagnitude
     *
     * @return  magnitud de la senhal.
     */
    public String getMagnitude() {
        return magnitude;
    }

    /**
     * getValues
     *
     * @return valores de la senhal.
     */
    public float[] getValues() {
        return values;
    }

    /**
     * getEmphasisLevel
     *
     * @return Valores de enfasis de la senhal. Deben ser numeros enteros
     * comprendidos entre [0, 100].
     */
    public short[] getEmphasisLevel() {
        return emphasis;
    }

    public Color getDataColor() {
        return properties.getDataColor();
    }

    /**
     * setEmphasisLevel
     *
     * @param emphasis Valores de enfasis de la senhal. Deben ser numeros
     * enteros comprendidos entre [0, 100].
     */
    public void setEmphasisLevel(short[] emphasis) {
        this.emphasis = emphasis;
        setHasEmphasis(true);
    }

    /**
     * hasColors
     *
     * @return boolean true si tienen niveles de enfasis asociado,
     * false en caso contrario.
     */
    public boolean hasEmphasisLevel() {
        return hasColors;
    }

    public void eraseEmphasis() {
        this.emphasis = null;
        this.setHasEmphasis(false);
    }

    public boolean setHasEmphasis(boolean hasColors) {
        if (hasColors && (emphasis == null || emphasis.length != values.length)) {
            return false;
        }
        properties.setHasEmphasis(hasColors);
        this.hasColors = hasColors;
        return true;
    }

    /**
     * setName
     *
     * @param name nombre de la senhal.
     */
    protected void setName(String name) {
        this.name = name;
        properties.setName(name);
    }

    /**
     * setFrecuency
     *
     * @param frecuency frecuencia de la senhal medida en hercios.
     */
    public void setFrecuency(float frecuency) {
//        if (this.frecuency != frecuency) {
            this.frecuency = frecuency;
            properties.setDataRate(frecuency);
//        }
    }

    /**
     * setStart
     *
     * @param start instante de inicio de la senhal medido en milisegundos
     * desde 00:00:00 01/01/1970. Ver {@link TimePositionConverter}.
     */
    public void setStart(long start) {
        if (this.start != start) {
            this.start = start;
            properties.setStartTime(start);
            properties.setDataSize(this.values.length);
        }
    }

    /**
     * setMagnitude
     *
     * @param magnitude magnitud de la senhal.
     */
    public void setMagnitude(String magnitude) {
        if (this.magnitude == null || !this.magnitude.equals(magnitude)) {
            this.magnitude = magnitude;
            properties.setMagnitude(magnitude);
        }
    }

    /**
     * setZoom
     *
     * @param zoom empleado en la representacion en pantalla.
     */
    public void setZoom(float zoom) {
//        if (properties.getZoom() != zoom) {
            properties.setZoom(zoom);
//        }
    }

    /**
     * setVisible
     *
     * @param visible true si se desea mostrar en pantalla, false en caso
     * contrario.
     */
    public void setVisible(boolean visible) {
        properties.setVisible(visible);
    }


    public boolean isVisible() {
         return properties.isVisible();
    }



    public void setDataColor(Color color) {
        properties.setDataColor(color);
    }

    /**
     * setAbscissaOffset
     *
     * @param abscissaOffset el valor de las abcisas en el cual
     * se debe representar el eje temporal.
     */
//    public void setAbscissaOffset(float abscissaOffset) {
//        if (properties.getAbscissaOffset() != abscissaOffset) {
//            properties.setAbscissaOffset(abscissaOffset);
//        }
//    }

    /**
     * setAbscissaValue
     *
     * @param abscissaValue obtiene el valor de las abcisas en el cual
     * se debe representar el eje temporal.
     */
    public void setAbscissaValue(float abscissaValue) {
//        if (properties.getAbscissaValue() != abscissaValue) {
            properties.setAbscissaValue(abscissaValue);
//        }
    }

    /**
     * setVisibleRange
     *
     * @param minValue float valor minimo del eje de abcisas.
     * @param maxValue float valor maximo del eje de artistas. debe cumplirse
     * que maxValue>minValue.
     * @param abscissaValue valor en el que se mostrara el eje de abscissas.
     * @return boolean true si la operacion se pudo completar con exito,
     * false en caso contrario.
     */
    public boolean setVisibleRange(float abscissaValue, float maxValue, float range) {
        return properties.setVisibleRange(abscissaValue, maxValue, range);
    }

    public boolean setVisibleRange(float abscissaValue, float maxValue) {
        return setVisibleRange(abscissaValue, maxValue, 1);
    }

    public float getAbscissaValue() {
       return properties.getAbscissaValue();
    }

    public float getMaxValue() {
   return properties.getMaxValue();
}


    /**
     * adjustVisibleRange
     *
     * @param channelHeight altura que se desea tenga el canal en el cual
     * se visualice esta senhal.
     * @return boolean true si la operacion se pudo completar con exito,
     * false en caso contrario.
     */
    public boolean adjustVisibleRange() {
        return adjustVisibleRange(1);
    }

    public boolean adjustVisibleRange(float range) {

        float min = values[0];
        float max = values[0];
        for (int index = 0; index < values.length; index++) {
            if (values[index] < min) {
                min = values[index];
            }
            if (values[index] > max) {
                max = values[index];
            }
        }
        return setVisibleRange(min, max, range);
    }

    public void addMark(MarkPlugin mp) {
        mp.setSignal(this);
        marks.add(mp);
    }

    public void removeMark(MarkPlugin mp) {
        if (marks.contains(mp)) {
            marks.remove(mp);
        }
    }

    public void removeAllMarks() {
        marks.clear();
    }

    public List<MarkPlugin> getMarks(long startTime, long endTime) {
        ArrayList<MarkPlugin> tempMarks = new ArrayList<MarkPlugin>();
        for (MarkPlugin mp : marks) {
            if (mp.isInterval()) {
                if (mp.getEndTime() >= startTime && mp.getMarkTime() < endTime) {
                    tempMarks.add(mp);
                }
            } else {
                if (mp.getMarkTime() >= startTime && mp.getMarkTime() < endTime) {
                    tempMarks.add(mp);
                }
            }
        }
        return tempMarks;
    }

    public List<MarkPlugin> getAllMarks() {
        return new LinkedList<MarkPlugin>(this.marks);
    }

    /** Always call first to the hasOwnGrid method
     *   @return {@link GridPlugin} plugin asociado a la senhal
     */
    public GridPlugin getGrid() {
        return grid;
    }

    public void setGrid(GridPlugin grid) {
        this.grid = grid;
        //ownGrid=true;
    }

    /* public boolean hasOwnGrid() {
         return ownGrid;
         }*/

    public boolean isImaginary() {
        return imaginary;
    }

    void setImaginary(boolean imaginary) {
        this.imaginary = imaginary;
    }

    public void setValues(float[] values) {
        this.values = values;
        this.properties.setDataSize(values.length);
    }

    public Object setProperty(String property, Object value) {
        synchronized (externalProperties) {
            return externalProperties.put(property, value);
        }
    }

    public Object getProperty(String property) {
        synchronized (externalProperties) {
            return externalProperties.get(property);
        }
    }

    public void removeProperty(String property) {
        synchronized (externalProperties) {
            if (externalProperties.containsKey(property)) {
                externalProperties.remove(property);
            }
        }
    }

    public void cleanProperties() {
        synchronized (externalProperties) {
            externalProperties.clear();
        }
    }

    public List<String> getAvailableProperties() {
        List<String> tempProp = new ArrayList<String>();
        synchronized (externalProperties) {
            Iterator<String> it = externalProperties.keySet().iterator();
            while (it.hasNext()) {
                tempProp.add(it.next());
            }
        }
        return tempProp;
    }
}
