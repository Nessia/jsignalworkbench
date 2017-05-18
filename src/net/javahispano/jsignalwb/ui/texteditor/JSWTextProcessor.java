package net.javahispano.jsignalwb.ui.texteditor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import com.borland.dbswing.DBTextDataBinder;
import com.borland.dbswing.FontChooser;

class JSWTextProcessor extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -4401263930483576859L;

    private static final Logger LOGGER = Logger.getLogger(JSWTextProcessor.class.getName());

    private ByteArrayOutputStream inMemoryDucyment;
    private Border normal = BorderFactory.createEtchedBorder();
    private Border selected = BorderFactory.createRaisedBevelBorder();


//    private JPanel contentPane;
    private JMenuBar menuBar1 = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JToolBar toolBar = new JToolBar();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
//    private ImageIcon gifAbrir;
//    private ImageIcon gifGuardar;
//    private ImageIcon gifNuevo;
    private JLabel statusBar = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JEditorPane editorpane = new JEditorPane();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();
    private JMenuItem jMenuItem4 = new JMenuItem();
    private FontChooser fontChooser1 = new FontChooser();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem7 = new JMenuItem();
    private JFileChooser jFileChooser1 = new JFileChooser();
    private String currFileName = null;
    private boolean dirty = false;
    private Document document1;
    private DBTextDataBinder dBTextDataBinder1 = new DBTextDataBinder();
    private JButton jButton3 = new JButton();
    private JMenu jMenu2 = new JMenu();
    private JMenuItem jMenuItem5 = new JMenuItem();

    JSWTextProcessor(File file, WindowListener windowFocusListener, ActionListener actionListener) {
        this.currFileName = file.toString();
        this.addWindowListener(windowFocusListener);
        menuFileExit.addActionListener(actionListener);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
            updateCaption();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(JSWTextProcessor.class.getResource("comentario.gif")));
        jButton1.setBorder(normal);
        jButton2.setBorder(normal);
        jButton3.setBorder(normal);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.openFile(currFileName);
    }

    //Component initialization
    private void jbInit() throws Exception {
        ImageIcon gifAbrir = new ImageIcon(JSWTextProcessor.class.getResource("openFile.gif"));
        ImageIcon gifGuardar = new ImageIcon(JSWTextProcessor.class.getResource("closeFile.gif"));
        ImageIcon gifNuevo = new ImageIcon(JSWTextProcessor.class.getResource("new.gif"));

        JPanel contentPane = (JPanel)this.getContentPane();
        document1 = editorpane.getDocument();
        editorpane.setContentType("text/rtf");
        contentPane.setLayout(borderLayout1);
        this.updateCaption();
        statusBar.setText(" ");
        menuFile.setText("File");
        menuFileExit.setToolTipText("Exits the text editor");
        menuFileExit.setText("Exit");
        menuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(69, java.awt.event.KeyEvent.CTRL_MASK, false));
        menuFileExit.addActionListener(new TextEditFrameMenuFileExitActionAdapter(this));
        jButton1.setIcon(gifAbrir);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1MouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton1MouseExited();
            }
        });
        jButton1.addActionListener(new TextEditFrameJButton1ActionAdapter(this));
        jButton1.setBorder(normal);
        jButton1.setToolTipText("Opens an rtf document");
        jButton2.setIcon(gifGuardar);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton2ActionPerformed();
            }
        });
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton2MouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton2MouseExited();
            }
        });
        jButton2.addActionListener(new TextEditFrameJButton2ActionAdapter(this));
        jButton2.setBorder(normal);
        jButton2.setToolTipText("Saves a document as an rtf file");
        editorpane.setBackground(Color.white);
        jMenuItem1.setToolTipText("Creates a new document");
        jMenuItem1.setIcon(gifNuevo);
        jMenuItem1.setText("New");
        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuItem1.addActionListener(new TextEditFrameJMenuItem1ActionAdapter(this));
        jMenuItem2.setToolTipText("Opens an rtf document");
        jMenuItem2.setIcon(gifAbrir);
        jMenuItem2.setText("Open");
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(79, java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuItem2.addActionListener(new TextEditFrameJMenuItem2ActionAdapter(this));
        jMenuItem3.setToolTipText("Saves a document as an rtf file");
        jMenuItem3.setIcon(gifGuardar);
        jMenuItem3.setText("Save");
        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(83, java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuItem3.addActionListener(new TextEditFrameJMenuItem3ActionAdapter(this));
        jMenuItem4.setToolTipText("Exports the document as an rtf independent from the working session");
        jMenuItem4.setText("Export as rtf");
        jMenuItem4.addActionListener(new TextEditFrameJMenuItem4ActionAdapter(this));
        fontChooser1.setFrame(this);
        fontChooser1.setTitle("Font");
        jMenu1.setText("Edit");
        jMenuItem7.setToolTipText("Changes document's background color.");
        jMenuItem7.setText("Background color");
        jMenuItem7.addActionListener(new TextEditFrameMenuItem7ActionAdapter(this));
        document1.addDocumentListener(new TextEditFrameDocument1DocumentAdapter(this));
        dBTextDataBinder1.setJTextComponent(editorpane);
        dBTextDataBinder1.setEnableFileLoading(false);
        dBTextDataBinder1.setEnableURLLoading(false);
        dBTextDataBinder1.setEnableFileSaving(false);
        jButton3.setBorder(normal);
        jButton3.setToolTipText("Creates a new document");
        jButton3.setIcon(gifNuevo);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton3MouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton3MouseExited();
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton3ActionPerformed();
            }
        });
        jMenu2.setText("About");
        jMenuItem5.setText("About...");
        jMenuItem5.addActionListener(new JSWTextProcessor_jMenuItem5_actionAdapter(this));
        toolBar.add(jButton3, null);
        toolBar.addSeparator(new Dimension(8, toolBar.getSize().height));
        toolBar.add(jButton1);
        toolBar.addSeparator(new Dimension(3, toolBar.getSize().height));
        toolBar.add(jButton2);
        menuFile.add(jMenuItem1);
        menuFile.addSeparator();
        menuFile.add(jMenuItem2);
        menuFile.add(jMenuItem3);
        menuFile.add(jMenuItem4);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar1.add(menuFile);
        menuBar1.add(jMenu1);
        menuBar1.add(jMenu2);
        this.setJMenuBar(menuBar1);
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(editorpane, null);
        jMenu1.add(jMenuItem7);
        jMenu2.add(jMenuItem5);
        //Codigo mio
        jFileChooser1.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return (f.getName().toLowerCase().endsWith(".rtf") || f.isDirectory());
            }

            @Override
            public String getDescription() {
                return "rtf documents";
            }
        });

    }

    private void fileOpen() {
        if (!isSaved()) {
            return;
        }
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
            openFile(jFileChooser1.getSelectedFile().getPath());
        }
        this.repaint();
    }

    private boolean openFile(String fileName) {
        File file = new File(fileName);
        FileInputStream out = null;
        try {
            out = new FileInputStream(file);
            editorpane.getEditorKit().read(out, editorpane.getDocument(), 0);
            out.close();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Error opening the file", "Error", JOptionPane.ERROR_MESSAGE);
            statusBar.setText("Error opening the file comments file of " + getJSWFileName());
            return false;
        } finally {
            if(out != null){
                try{
                    out.close();
                } catch(Exception ex){
                    LOGGER.log(Level.FINEST, ex.getMessage(), ex);
                }
            }
        }
        this.dirty = false;
        //  this.currFileName = fileName;
        //  statusBar.setText("Editing " + currFileName);
        updateCaption();
        return true;
    }

    private boolean saveFile(String file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            editorpane.getEditorKit().write(out, editorpane.getDocument(), 0, editorpane.getDocument().getLength());
            out.close();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Error scaving the file", "Error", JOptionPane.ERROR_MESSAGE);
            statusBar.setText("Error saving the file in" + getJSWFileName());
            return false;
        } finally {
           if(out != null){
              try{
                  out.close();
              } catch(Exception ex){
                  LOGGER.log(Level.FINEST, ex.getMessage(), ex);
              }
          }
      }
        this.dirty = false;
        statusBar.setText("Saved in" + getJSWFileName());
        updateCaption();
        return true;
    }


    /**
     * saveFile
     */
    void saveFile() {
        saveFile(this.currFileName);
    }


    private String getJSWFileName() {
        return this.currFileName.substring(0, currFileName.lastIndexOf(System.getProperty("file.separator")));
    }

    private boolean exportFile() {
        this.repaint();
        if (JFileChooser.APPROVE_OPTION == jFileChooser1.showSaveDialog(this)) {
            String file = jFileChooser1.getSelectedFile().getPath();
            if (!file.endsWith(".rtf")) {
                file += ".rtf";
            }
            this.repaint();
            return saveFile(file);
        } else {
            this.repaint();
            return false;
        }
    }


    /**
     * Comprueba si el archivo esta o no guardado.
     *
     * @return true si esta guardado, false en caso contrario.
     */
    private boolean isSaved() {
        if (!dirty) {
            return true;
        }
        int value = JOptionPane.showConfirmDialog(this, "Save changes?",
                                                  "Text Edit", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (value) {
        case JOptionPane.YES_OPTION:

            return saveFile(this.currFileName);
        case JOptionPane.NO_OPTION:

            return true;
        case JOptionPane.CANCEL_OPTION:
        default:

            return false;
        }
    }

    private void updateCaption() {
        String caption;
        caption = "Editing the comment of " + getJSWFileName();
        if (dirty) {
            caption = "* " + caption;
            caption = "JSignalWorkbench Editor - " + caption;
        }

        this.setTitle(caption);
    }

    private void fileExit_actionPerformed() {
        this.setVisible(false);
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            fileExit_actionPerformed();
        }
    }


    private void jMenuItem7ActionPerformed() {
        Color color = JColorChooser.showDialog(this, "Background Color", editorpane.getBackground());
        if (color != null) {
            editorpane.setBackground(color);
        }
        this.repaint();
    }

    private void jMenuItem1ActionPerformed() {
        if (isSaved()) {
            editorpane.setText("");
            statusBar.setText("New");
            this.setTitle("New");
            //currFileName = null;
            dirty = false;
            updateCaption();
        }
    }

    private void jMenuItem2ActionPerformed() {
        fileOpen();
    }

    private void jMenuItem3ActionPerformed() {
        saveFile(this.currFileName);
    }

    private void jMenuItem4ActionPerformed() {
        exportFile();
    }

    private void jButton1ActionPerformed() {
        fileOpen();
    }

    private void jButton2ActionPerformed() {
        saveFile(this.currFileName);
    }

    private void document1ChangedUpdate() {
        dirty = true;
        updateCaption();
    }

    private void document1InsertUpdate() {
        if (!dirty) {
            dirty = true;
            updateCaption();
        }
    }

    private void document1RemoveUpdate() {
        if (!dirty) {
            dirty = true;
            updateCaption();
        }
    }

    public String getTextoComentario() {
        return editorpane.getText();
    }

    private void jButton3ActionPerformed() {
        jMenuItem1ActionPerformed();
    }

    private void jButton3MouseEntered() {
        jButton3.setBorder(selected);
    }

    private void jButton3MouseExited() {
        jButton3.setBorder(normal);
    }

    private void jButton1MouseEntered() {
        jButton1.setBorder(selected);
    }

    private void jButton1MouseExited() {
        jButton1.setBorder(normal);
    }

    private void jButton2MouseEntered() {
        jButton2.setBorder(selected);
    }

    private void jButton2MouseExited() {
        jButton2.setBorder(normal);
    }

    public ByteArrayOutputStream getInMemoryDucyment() {
        return inMemoryDucyment;
    }

    public void setInMemoryDucyment(ByteArrayOutputStream inMemoryDucyment) {
        this.inMemoryDucyment = inMemoryDucyment;
    }

    public void setCurrFileName(String currFileName) {
        this.currFileName = currFileName;
    }

    void jMenuItem5_actionPerformed() {
        JOptionPane.showMessageDialog(this,
                                      "<html></head><body><p><font color=\"#FF0000\" size=\"5\">About</font>" +
                                      "</p><p><font " +
                                      "color=\"#0000FF\" size=\"4\">" +
                                      "</font></p><p><font color=\"#0000FF\" size=\"4\"> text editor.<br>" +
                                      "Developed by Abraham Otero and Roman Segador</font></p></body></html>",
                                      "About", JOptionPane.INFORMATION_MESSAGE);
    }


    private class TextEditFrameMenuFileExitActionAdapter implements ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameMenuFileExitActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.fileExit_actionPerformed();
        }
    }


    private class TextEditFrameMenuItem7ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameMenuItem7ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem7ActionPerformed();
        }
    }


    private class TextEditFrameJMenuItem1ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameJMenuItem1ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem1ActionPerformed();
        }
    }


    private class TextEditFrameJMenuItem2ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameJMenuItem2ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem2ActionPerformed();
        }
    }


    private class TextEditFrameJMenuItem3ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameJMenuItem3ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem3ActionPerformed();
        }
    }


    private class TextEditFrameJMenuItem4ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameJMenuItem4ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jMenuItem4ActionPerformed();
        }
    }


    private class TextEditFrameJButton1ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameJButton1ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jButton1ActionPerformed();
        }
    }


    private class TextEditFrameJButton2ActionAdapter implements java.awt.event.ActionListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameJButton2ActionAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            adaptee.jButton2ActionPerformed();
        }

    }


    private class TextEditFrameDocument1DocumentAdapter implements javax.swing.event.DocumentListener {
        private JSWTextProcessor adaptee;

        private TextEditFrameDocument1DocumentAdapter(JSWTextProcessor adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            adaptee.document1ChangedUpdate();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            adaptee.document1InsertUpdate();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            adaptee.document1RemoveUpdate();
        }
    }


}


class JSWTextProcessor_jMenuItem5_actionAdapter implements ActionListener {
    private JSWTextProcessor adaptee;
    JSWTextProcessor_jMenuItem5_actionAdapter(JSWTextProcessor adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem5_actionPerformed();
    }
}
