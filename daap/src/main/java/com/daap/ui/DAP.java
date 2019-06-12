package com.daap.ui;

import com.daap.engine.anti_patterns.mobile.BitmapsCollectionDetectionEngine;
import com.daap.engine.anti_patterns.mobile.DroppedDataDetectionEngine;
import com.daap.engine.anti_patterns.mobile.DurableWakeLockDetectionEngine;
import com.daap.engine.anti_patterns.mobile.InefficientDFormatParserDetectionEngine;
import com.daap.engine.anti_patterns.mobile.InefficientDSDetectionEngine;
import com.daap.engine.anti_patterns.mobile.InternalSetGetDetectionEngine;
import com.daap.engine.anti_patterns.mobile.LeakingInnerClassDetectionEngine;
import com.daap.engine.anti_patterns.mobile.LeakingThreadDetectionEngine;
import com.daap.engine.anti_patterns.mobile.LowMemoryDetectionEngine;
import com.daap.engine.anti_patterns.mobile.MIMDetectionEngine;
import com.daap.engine.anti_patterns.mobile.PublicDataDetectionEngine;
import com.daap.engine.anti_patterns.mobile.RigidAlarmManagerDetectionEngine;
import com.daap.engine.anti_patterns.mobile.SlowForLoopDetectionEngine;
import com.daap.engine.anti_patterns.mobile.StaticBitmapDetectionEngine;
import com.daap.engine.anti_patterns.mobile.StaticContextDetectionEngine;
import com.daap.engine.anti_patterns.mobile.StaticViewDetectionEngine;
import com.daap.engine.anti_patterns.mobile.UnclosedCloseableDetectionEngine;
import com.daap.engine.anti_patterns.mobile.ViewsCollectionDetectionEngine;
import com.daap.helper.StaticFinals;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.parse.MyDpvdVisitor;
import com.daap.parse.MyDpvdXmlVisitor;
import com.daap.util.Constants;

import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

/**
 * Created by ALI on 10/1/2017.
 */
public class DAP {
    public static String projectSrcFolder = null;
    public static boolean parseoccured = false;
    private static ParsingStages parsingStage;
    private static JButton btnDetect;
    private static JLabel detectionLoader, parsingLoader;
    private static Constants.SELECTED_AP_ANDROID selectedApAndroid = Constants.SELECTED_AP_ANDROID.NONE;

    private static int BUTTON_FONT_SIZE = 16;
    static JTextArea jtaConsole;
    static JLabel jLabelAntiPattern;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("DAAP-(Detection of Android Anti Patterns)");
        JPanel panel = (JPanel) frame.getContentPane();

        panel.setLayout(null);

        JLabel title = new JLabel("Detection of Android Anti Patterns (DAAP)");
        title.setFont(new Font("Courier New", Font.ITALIC + Font.BOLD, 30));
        panel.add(title);
        Dimension labelSize = title.getPreferredSize();
        title.setBounds(550 - labelSize.width / 2, 60, labelSize.width, labelSize.height);

        JPanel jpBrowseProject = new JPanel();
        jpBrowseProject.setLocation(title.getX(), title.getY() + 60);
        panel.add(jpBrowseProject);

        final JFilePicker browseProjectPicker = new JFilePicker("Android Project Folder", "Browse", JFilePicker.type.Project);
        browseProjectPicker.setMode(JFilePicker.MODE_SAVE);
        jpBrowseProject.add(browseProjectPicker);
        jpBrowseProject.setSize(jpBrowseProject.getPreferredSize().width, 65);

        jLabelAntiPattern = new JLabel("Android Anti Patterns:");
        jLabelAntiPattern.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(jLabelAntiPattern);
        jLabelAntiPattern.setBounds(jpBrowseProject.getX() + 80, jpBrowseProject.getY() + 60, 200, 50);

        final JComboBox cbAntiPatternsAndroid = new JComboBox<>(new String[]{"Choose Android Anti Pattern",
                Constants.A_MEMBER_IGNORING_METHOD, Constants.A_LOW_MEMORY,
                Constants.A_INTERNAL_GETTER_SETTER, Constants.A_INEFFICIENT_DATA_FORMAT_PARSER,
                Constants.A_INEFFICIENT_DATA_STRUCTURE, Constants.A_LEAKING_THREAD,
                Constants.A_SLOW_FOR_LOOP, Constants.A_PUBLIC_DATA, Constants.A_DURABLE_WAKE_LOCK,
                Constants.A_RIGID_ALARM_MANAGER, Constants.A_UNCLOSED_CLOSEABLE, Constants.A_LEAKING_INNER_CLASS,
                Constants.A_DEBUGGABLE_RELEASE, Constants.A_STATIC_VIEW, Constants.A_STATIC_CONTEXT,
                Constants.A_STATIC_BITMAP, Constants.A_VIEWS_IN_COLLECTION, Constants.A_COLLECTION_BITMAPS,
                Constants.A_DROPPED_DATA, Constants.A_UNTOUCHABLE, Constants.A_UNCONTROLLED_FOCUS_ORDER,
                Constants.A_NESTED_LAYOUT, Constants.A_NOT_DESCRIPTIVE_UI, Constants.A_CONFIG_CHANGES, Constants.A_OVERDRAWN_PIXEL
        });

        cbAntiPatternsAndroid.setSize(220, 35);
        cbAntiPatternsAndroid.setLocation(jLabelAntiPattern.getX() + 200, jLabelAntiPattern.getY() + 10);
        cbAntiPatternsAndroid.setEditable(true);
        cbAntiPatternsAndroid.getEditor().getEditorComponent().setFocusable(false);
        cbAntiPatternsAndroid.setVisible(true);
        panel.add(cbAntiPatternsAndroid);

        cbAntiPatternsAndroid.addActionListener(actionEvent -> {

            JComboBox comboBox = (JComboBox) actionEvent.getSource();

            Object selected = comboBox.getSelectedItem();

            if (selected.toString().equals(Constants.CHOOSE_ANTI_PATTERN)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.NONE;
            } else if (selected.toString().equals(Constants.A_LEAKING_INNER_CLASS)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.LEAKING_INNER_CLASS;
            } else if (selected.toString().equals(Constants.A_MEMBER_IGNORING_METHOD)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.MEMBER_IGNORING_METHOD;
            } else if (selected.toString().equals(Constants.A_LEAKING_THREAD)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.LEAKING_THREAD;
            } else if (selected.toString().equals(Constants.A_INTERNAL_GETTER_SETTER)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.INTERNAL_GETTER_SETTER;
            } else if (selected.toString().equals(Constants.A_INEFFICIENT_DATA_STRUCTURE)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.INEFFICIENT_DATA_STRUCTURE;
            } else if (selected.toString().equals(Constants.A_INEFFICIENT_DATA_FORMAT_PARSER)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.INEFFICIENT_DATA_FORMAT_PARSER;
            } else if (selected.toString().equals(Constants.A_SLOW_FOR_LOOP)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.SLOW_FOR_LOOP;
            } else if (selected.toString().equals(Constants.A_PUBLIC_DATA)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.PUBLIC_DATA;
            } else if (selected.toString().equals(Constants.A_LOW_MEMORY)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.LOW_MEMORY;
            } else if (selected.toString().equals(Constants.A_DURABLE_WAKE_LOCK)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.DURABLE_WAKE_LOCK;
            } else if (selected.toString().equals(Constants.A_RIGID_ALARM_MANAGER)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.RIGID_ALARM_MANAGER;
            } else if (selected.toString().equals(Constants.A_UNCLOSED_CLOSEABLE)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.UNCLOSED_CLOSEABLE;
            } else if (selected.toString().equals(Constants.A_STATIC_CONTEXT)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.STATIC_CONTEXT;
            } else if (selected.toString().equals(Constants.A_STATIC_VIEW)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.STATIC_VIEW;
            } else if (selected.toString().equals(Constants.A_STATIC_BITMAP)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.STATIC_BITMAP;
            } else if (selected.toString().equals(Constants.A_VIEWS_IN_COLLECTION)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.VIEWS_IN_COLLECTION;
            } else if (selected.toString().equals(Constants.A_COLLECTION_BITMAPS)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.COLLECTION_BITMAPS;
            } else if (selected.toString().equals(Constants.A_DEBUGGABLE_RELEASE)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.DEBUGGABLE_RELEASE;
            } else if (selected.toString().equals(Constants.A_DROPPED_DATA)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.DROPPED_DATA;
            } else if (selected.toString().equals(Constants.A_CONFIG_CHANGES)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.CONFIG_CHANGES;
            } else if (selected.toString().equals(Constants.A_NOT_DESCRIPTIVE_UI)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.NOT_DESCRIPTIVE_UI;
            } else if (selected.toString().equals(Constants.A_NESTED_LAYOUT)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.NESTED_LAYOUT;
            } else if (selected.toString().equals(Constants.A_UNTOUCHABLE)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.UNTOUCHABLE;
            } else if (selected.toString().equals(Constants.A_UNCONTROLLED_FOCUS_ORDER)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.UNCONTROLLED_FOCUS_ORDER;
            } else if (selected.toString().equals(Constants.A_OVERDRAWN_PIXEL)) {
                selectedApAndroid = Constants.SELECTED_AP_ANDROID.OVERDRAWN_PIXEL;
            }
        });
        cbAntiPatternsAndroid.setEnabled(true);

        JButton btnParse = new JButton("Start Parsing");
        btnParse.setEnabled(true);
        panel.add(btnParse);
        Dimension btnPredictSize = btnParse.getPreferredSize();
        btnParse.setBounds(jLabelAntiPattern.getX() - 100, jLabelAntiPattern.getY() + 80, btnParse.getPreferredSize().width, btnParse.getPreferredSize().height);
        btnParse.setSize(180, 50);
        btnParse.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
        parsingLoader = new JLabel(new ImageIcon("resources/images/35.gif"));
        parsingLoader.setBounds(parsingLoader.getPreferredSize().width, parsingLoader.getPreferredSize().height, btnParse.getX(), btnParse.getY());
        parsingLoader.setLocation(btnParse.getX() + btnParse.getPreferredSize().width / 2 - parsingLoader.getPreferredSize().width / 2, btnParse.getY() + 50);
        parsingLoader.setSize(100, 100);
        panel.add(parsingLoader);
        parsingLoader.setVisible(false);

        btnDetect = new JButton("Detect Selected Anti-Pattern");

        panel.add(btnDetect);
        btnDetect.setEnabled(false);
        btnDetect.setBounds(btnParse.getX() + btnParse.getPreferredSize().width + 55, btnParse.getY(), btnDetect.getPreferredSize().width, btnDetect.getPreferredSize().height);
        btnDetect.setSize(270, 50);
        btnDetect.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));

        JButton btnReset = new JButton("Reset");
        btnReset.setEnabled(true);
        panel.add(btnReset);
        btnReset.setBounds(btnDetect.getX() + btnDetect.getPreferredSize().width + 30, btnDetect.getY(), btnReset.getPreferredSize().width, btnReset.getPreferredSize().height);
        btnReset.setSize(120, 50);
        btnReset.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));

        detectionLoader = new JLabel(new ImageIcon("resources/images/35.gif"));
        detectionLoader.setBounds(parsingLoader.getPreferredSize().width, parsingLoader.getPreferredSize().height, btnParse.getX(), btnParse.getY() - 200);
        detectionLoader.setLocation(btnDetect.getX() + btnDetect.getPreferredSize().width / 2 - detectionLoader.getPreferredSize().width / 2, btnDetect.getY() + 50);
        detectionLoader.setSize(100, 100);
        panel.add(detectionLoader);
        detectionLoader.setVisible(false);


        final ButtonGroup buttonGroup = new ButtonGroup();

        btnParse.addActionListener(arg0 -> {
            if (parsingStage == ParsingStages.PARSING) {
                JOptionPane.showMessageDialog(new JFrame(), "Parsing is in progress, Please wait.", "Parsing",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (parsingStage == ParsingStages.DONE) {
                JOptionPane.showMessageDialog(new JFrame(), "Parsing completed, Please start detection process.", "Parsing",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (projectSrcFolder == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Please Select a valid path to parse the source-code", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                parsingStage = ParsingStages.PARSING;
//                    isParsing = true;
                parse();
            }
        });

        btnDetect.addActionListener(arg0 -> {
            switch (selectedApAndroid) {
                case LEAKING_INNER_CLASS:
                    detectionLoader.setVisible(true);
                    LeakingInnerClassDetectionEngine.detect();
                    break;
                case MEMBER_IGNORING_METHOD:
                    detectionLoader.setVisible(true);
                    MIMDetectionEngine.detect();
                    break;
                case LEAKING_THREAD:
                    detectionLoader.setVisible(true);
                    LeakingThreadDetectionEngine.detect();
                    break;
                case INTERNAL_GETTER_SETTER:
                    detectionLoader.setVisible(true);
                    InternalSetGetDetectionEngine.detect();
                    break;
                case INEFFICIENT_DATA_STRUCTURE:
                    detectionLoader.setVisible(true);
                    InefficientDSDetectionEngine.detect();
                    break;
                case INEFFICIENT_DATA_FORMAT_PARSER:
                    detectionLoader.setVisible(true);
                    InefficientDFormatParserDetectionEngine.detect();
                    break;
                case SLOW_FOR_LOOP:
                    detectionLoader.setVisible(true);
                    SlowForLoopDetectionEngine.detect();
                    break;
                case PUBLIC_DATA:
                    detectionLoader.setVisible(true);
                    PublicDataDetectionEngine.detect();
                    break;
                case LOW_MEMORY:
                    detectionLoader.setVisible(true);
                    LowMemoryDetectionEngine.detect();
                    break;
                case DURABLE_WAKE_LOCK:
                    detectionLoader.setVisible(true);
                    DurableWakeLockDetectionEngine.detect();
                    break;
                case RIGID_ALARM_MANAGER:
                    detectionLoader.setVisible(true);
                    RigidAlarmManagerDetectionEngine.detect();
                    break;
                case UNCLOSED_CLOSEABLE:
                    detectionLoader.setVisible(true);
                    UnclosedCloseableDetectionEngine.detect();
                    break;
                case STATIC_CONTEXT:
                    detectionLoader.setVisible(true);
                    StaticContextDetectionEngine.detect();
                    break;
                case STATIC_VIEW:
                    detectionLoader.setVisible(true);
                    StaticViewDetectionEngine.detect();
                    break;
                case STATIC_BITMAP:
                    detectionLoader.setVisible(true);
                    StaticBitmapDetectionEngine.detect();
                    break;
                case VIEWS_IN_COLLECTION:
                    detectionLoader.setVisible(true);
                    ViewsCollectionDetectionEngine.detect();
                    break;
                case COLLECTION_BITMAPS:
                    detectionLoader.setVisible(true);
                    BitmapsCollectionDetectionEngine.detect();
                    break;
                case DROPPED_DATA:
                    detectionLoader.setVisible(true);
                    DroppedDataDetectionEngine.detect();
                    break;
                case DEBUGGABLE_RELEASE:
                    DAP.clearConsole();
                    writeMessage("Class: " + LegacySystem.getInstance().getDebuggable());
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getDebuggable());
//                            }
                    break;
                case CONFIG_CHANGES:
                    DAP.clearConsole();
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getConfigChanges());
                    break;
                case NESTED_LAYOUT:
                    DAP.clearConsole();
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getNestedLayouts());
                    break;
                case NOT_DESCRIPTIVE_UI:
                    DAP.clearConsole();
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getNotDescriptiveUi());
                    break;
                case UNTOUCHABLE:
                    DAP.clearConsole();
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getUntouchables());
                    break;
                case UNCONTROLLED_FOCUS_ORDER:
                    DAP.clearConsole();
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getUncontrolledFocusOrder());
                    break;
                case OVERDRAWN_PIXEL:
                    DAP.clearConsole();
                    DAP.writeMessage("Total: " + LegacySystem.getInstance().getOverDrawnPixel());
                    break;
                case NONE:
                    JOptionPane.showMessageDialog(new JFrame(), "Please Choose an Anti-Pattern for detection.", "INFO",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
//            }
        });


        btnReset.addActionListener(arg0 -> {
            projectSrcFolder = null;
            btnDetect.setEnabled(false);
            parsingStage = ParsingStages.NONE;
            buttonGroup.clearSelection();
            browseProjectPicker.rest();
            LegacySystem.getInstance().reset();
            clearConsole();
        });


        jtaConsole = new JTextArea("");
        jtaConsole.setFont(new Font("Courier New", Font.BOLD, 15));

        jtaConsole.setBounds((int) (frame.getSize().width * 0.5), parsingLoader.getY() + 280, 1000, 300); //parsingLoader.getY() + 300
        JScrollPane jScrollPane = new JScrollPane(jtaConsole);

        frame.add(jScrollPane);
        jScrollPane.setBounds(10, 60, 1000, 200); // 200
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        frame.setLayout(null);
        frame.setVisible(true);

        frame.setResizable(false);
        frame.setSize(1100, 720);
        frame.setVisible(true);
        jScrollPane.setLocation(50, parsingLoader.getY() + 100);
        jtaConsole.setLocation(2, jtaConsole.getY());

    }

    private static void findRelations() {
        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
//            if (!legacyClass.getClassOrInterfaceDeclaration().isInterface()) {
            legacyClass.findInherits();
            legacyClass.findUses();
            legacyClass.findCalls();
            legacyClass.findCreates();
            legacyClass.findHas();
            legacyClass.findReferences();
        }
        System.out.println("Finding Relations Done.");

        MyDpvdXmlVisitor.detectFromXmlFiles(new File(projectSrcFolder));

    }

    private static void parse() {

        parsingLoader.setVisible(true);

        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                MyDpvdVisitor.listClasses(new File(projectSrcFolder));
                System.out.println("Parsing Done. ");
                System.out.println("LegacySystem Total calsses : " + LegacySystem.getInstance().getAllClasses().size());
                System.out.println("Finding Relations");
                findRelations();

                return null;
            }
        };

        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        System.out.println("Parsing and relations Done. ");
                        parsingLoader.setIcon(new ImageIcon("resources/images/tick3.jpg"));
                        parsingStage = ParsingStages.DONE;
                        btnDetect.setEnabled(true);
                    }
                }
            }
        });
        mySwingWorker.execute();
    }

    public static void writeMessage(String msg) {
        jtaConsole.append("\n");
        jtaConsole.append(msg);
    }

    public static void detectionDone() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        detectionLoader.setVisible(false);
                    }
                },
                2000
        );
    }

    public static void clearConsole() {
        jtaConsole.setText("");
    }

    public enum ParsingStages {
        NONE, PARSING, DONE
    }

}
