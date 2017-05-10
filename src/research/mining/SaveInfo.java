package research.mining;

import java.io.*;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import research.mining.TemporalEvent.DETAILLEVEL;

public class SaveInfo {

    private static final Logger LOGGER = Logger.getLogger(SaveInfo.class.getName());
    private static final String DEFAULT_TEMP_FILE = "C:\\tmp.csv";

    //Nivel de detalle a emplear al generar la informacion
    private DETAILLEVEL level;
    private SortedSet<Desaturation> desatTree;
    private String fileName = DEFAULT_TEMP_FILE;
    private long beginingRecording;
    //si vale true indica que debe de incluirse el tipo de episodio correspondiente
    //en la informacion generada; en caso contrario nos incluiran los episodios
    //correspondientes
    private boolean includeDesat = true;
    private boolean includeFlux = true;
    private boolean includeThorax = true;
    private boolean includeAbdomen = true;

    public SaveInfo(SortedSet<Desaturation> desatTree) {
        this.desatTree = desatTree;
    }

    /**
     * Este metodo debe devolver una cadena de caracteres conteniendo  toda la
     * informacion a guardar en el fichero.
     *
     * @return String
     */
    public String genrateDescriptors() {
        StringBuilder descriptors = new StringBuilder();
        for(Desaturation desaturation: desatTree){
            //Solo desaturaciones bien identificadas y asociadas con una
            //unica limitacion de flujo
            if (desaturation.getDesaturatedArea()<= 0 ||
                    desaturation.getLimitations().isEmpty()) {
                continue;
            }
            descriptors.append(desaturation.generateDescriptors(level, beginingRecording));
        }
        return descriptors.toString();
    }

    public void saveDescriptors() {
        File f = new File(fileName);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(f);
            printHead(pw);
            pw.print(this.genrateDescriptors());
            pw.close();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(null,
                                          "Ha sucedido un error",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        } finally{
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void printHead(PrintWriter pw) {
        pw.print("timeDesat	tBeginingDesat	DurationDesat	minValueDesat"+
                 "	meanValueDesat	beginValueDesat	endValueDesat	fallValueDesat"+
                 "	riseValueDesat	fallDurationDesat	riseDurationDesat"+
                 "	fallSlopeDesat	riseSlopeDesat	desaturatedArea	timeFlux"+
                 "	tBeginingFlux	durationFlux	A|H	energyFlux"+
                 "	basalEnergyBefore	BasalEnergyAfter	CompositeEvent	NumLimitations	tBeginingBegining"+
                 "	tEndMin	tEndEnd" +
                 "	Age	Weight	BMI	AHI	Afternoon	MeanDurationAp	MeanDurationHy"+
                 "	MeanDurationDesat	MeandValSpO2Com	BasalSpO2Com	MeandValSpO2	BasalSpO2	Diagnostic	ID\n");
    }

    public boolean isIncludeAbdomen() {
        return includeAbdomen;
    }

    public boolean isIncludeThorax() {
        return includeThorax;
    }

    public boolean isIncludeFlux() {
        return includeFlux;
    }

    public boolean isIncludeDesat() {
        return includeDesat;
    }

    public DETAILLEVEL getLevel() {
        return level;
    }

    public long getBeginingRecording() {
        return beginingRecording;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIncludeAbdomen(boolean includeAbdomen) {
        this.includeAbdomen = includeAbdomen;
    }

    public void setIncludeDesat(boolean includeDesat) {
        this.includeDesat = includeDesat;
    }

    public void setIncludeFlux(boolean includeFlux) {
        this.includeFlux = includeFlux;
    }

    public void setIncludeThorax(boolean includeThorax) {
        this.includeThorax = includeThorax;
    }

    public void setLevel(DETAILLEVEL level) {
        this.level = level;
    }

    public void setBeginingRecording(long beginingRecording) {
        this.beginingRecording = beginingRecording;
    }
}
