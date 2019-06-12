package com.daap.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class implements a Component for browsing a file system for a folder.
 */
@SuppressWarnings("serial")
public class JFilePicker extends JPanel {
    private JLabel label;
    private JTextField textField;
    private JButton button;

    private JFileChooser fileChooser;

    private int mode;

    public enum type {
        Export, Project, Pattern
    }

    ;

    private type type;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;

    //	private final String defaultPath = "E:\\Study\\data\\jrefactory-master";  //home path
    private final String defaultPath = "C:\\Users\\Azhar\\Dropbox\\Suave\\data\\jrefactory-master"; //

    public JFilePicker(String textFieldLabel, String buttonLabel, type type) {
        this.type = type;

        fileChooser = new JFileChooser(defaultPath);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		fileChooser.setSelectedFile(new File("E:\\Study\\data\\jrefactory-master"));
//        fileChooser.setSize(300, 80);
        // disable the "All files" option.
        //
        fileChooser.setAcceptAllFileFilterUsed(false);
        //
//        fileChooser.setCurrentDirectory(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // creates the GUI
        label = new JLabel(textFieldLabel);
        label.setFont(new Font("Courier New", Font.BOLD, 20));
        Action action = new Action();
        textField = new JTextField(22);
        textField.setSize(200, 50);
        textField.addActionListener(action);
        button = new JButton(buttonLabel);
        button.setSize(120, 50);
        button.addActionListener(evt -> buttonActionPerformed(evt));

        add(label);
        add(textField);
        add(button);

    }


    public void rest() {
        fileChooser.setCurrentDirectory(null);
        textField.setText("");
    }

    private void buttonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String s = fileChooser.getSelectedFile().getAbsolutePath();
                textField.setText(s);
                switch (type) {
                    case Project:
//                        MainScreen.parseoccured = false;
                        DAP.parseoccured = false;
//                        MainScreen.projectSrcFolder = s;
                        DAP.projectSrcFolder = s;
//                        ThesisScreen.projectSrcFolder = s;
                        break;
                    default:
                }
            }
        }
    }

    public class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s;
            if (new File(s = textField.getText()).isDirectory()) {
                switch (type) {
                    case Project:
//                        MainScreen.parseoccured = false;
//                        MainScreen.projectSrcFolder = s;
                        DAP.parseoccured = false;
                        DAP.projectSrcFolder = s;
//                        ThesisScreen.projectSrcFolder = s;
                        break;
                    default:
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Path does not correspond to a folder!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                switch (type) {
                    case Project:
//                        MainScreen.parseoccured = false;
//                        MainScreen.projectSrcFolder = null;
                        DAP.parseoccured = false;
                        DAP.projectSrcFolder = null;
//                        ThesisScreen.projectSrcFolder = null;
                        break;
                    default:
                }
            }
        }
    }


    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getSelectedFilePath() {
        return textField.getText();
    }

    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
}
