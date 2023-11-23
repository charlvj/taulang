/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.charlware.taulang.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import javax.swing.JFileChooser;

/**
 *
 * @author charlvj
 */
public class TauEditorPane extends javax.swing.JPanel {

    private File file = null;
    private File workFile = null;
    private boolean saved = false;
    private boolean newFile = true;
    
    private JFileChooser fileChooser = null;
    
    /**
     * Creates new form TauEditorPane
     */
    public TauEditorPane() {
        initComponents();
    }

    public boolean isSaved() {
        return saved;
    }
    
    public boolean isNew() {
        return newFile;
    }
    
    public File getFile() {
        return file;
    }
    
    public void setFile(File file) {
        this.file = file;
    }
    
    public void newFile(String filename) {
        saved = false;
        file = null;
        newFile = true;
        file = new File(System.getProperty("java.io.tmpdir"), filename);
        file.deleteOnExit();
    }
    
    public void openFile(File file) throws IOException {
        this.file = file;
        StringBuilder sb = new StringBuilder();
        Files.lines(file.toPath())
                .forEach(line -> sb.append(line).append("\n"));
        txContents.setText(sb.toString());
        newFile = false;
    }
    
    public void saveFile() throws FileNotFoundException {
    	saveFile(false);
    }
    
    public void saveFile(boolean keepNew) throws FileNotFoundException {
//        if(newFile) {
//            int res = getFileChooser().showSaveDialog(this);
//            if(res == JFileChooser.APPROVE_OPTION)  {
//                file = getFileChooser().getSelectedFile();
//            }
//        }
        try(PrintStream stream = new PrintStream(file)) {
            stream.print(txContents.getText());
            if(!keepNew) {
	            saved = true;
	            newFile = false;
            }
        }
    }
    
    public void saveFile(File file) throws FileNotFoundException {
        if(file != null) {
            this.file = file;
        }
        saveFile();
    }

    public JFileChooser getFileChooser() {
        if(fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txContents = new javax.swing.JTextArea();

        txContents.setColumns(20);
        txContents.setRows(5);
        jScrollPane1.setViewportView(txContents);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txContents;
    // End of variables declaration//GEN-END:variables
}
