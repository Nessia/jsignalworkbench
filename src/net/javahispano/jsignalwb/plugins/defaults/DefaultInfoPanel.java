package net.javahispano.jsignalwb.plugins.defaults;

import java.awt.Window;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;

import org.joda.time.DateTime;

import com.michaelbaranov.microba.calendar.DatePicker;

import net.javahispano.jsignalwb.ui.JTextFieldDate;

abstract class DefaultInfoPanel extends JPanel implements PropertyChangeListener {

    /**
     *
     */
    private static final long serialVersionUID = -4031410447356022827L;

    // GUI GEN-BEGIN
    protected JTextField colorTextField;
    protected JTextArea commentaryTextArea;
    protected DatePicker datePicker1;
    protected JTextField jTextField1;
    protected JTextFieldDate jTextFieldDate1;
    protected JLabel kindLabel;
    protected JTextField markTitleTextField;
    // GUI GEN-END

    protected JWindow jw;

    /**
     * @param old long
     * @param newTime long
     * @return long
     */
    protected static long swapDateNoChangeTime(long old, long newTime) {
        DateTime dt = new DateTime(old);
        DateTime newDateTime = new DateTime(newTime).withTime(dt.getHourOfDay(),
                dt.getMinuteOfHour(), dt.getSecondOfMinute(),
                dt.getMillisOfSecond());

        return newDateTime.getMillis();
    }

    void showJWindow(Window owner) {
       jw = new JWindow(owner);
       jw.add(this);
       jw.setSize(this.getPreferredSize());
       jw.setLocationRelativeTo(owner);
       jw.setVisible(true);
    }

}
