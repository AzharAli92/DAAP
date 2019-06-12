package com.daap.parse;

import com.daap.model.Layout;
import com.daap.model.LegacySystem;
import com.daap.util.Constants;
import com.daap.util.DirExplorer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static com.daap.util.Constants.ANDROID_MANIFEST;
import static com.daap.util.Constants.AbsoluteLayout;
import static com.daap.util.Constants.AutoCompleteTextView;
import static com.daap.util.Constants.Button;
import static com.daap.util.Constants.COLOR_HEX;
import static com.daap.util.Constants.COLOR_VAL;
import static com.daap.util.Constants.CheckBox;
import static com.daap.util.Constants.EditText;
import static com.daap.util.Constants.FrameLayout;
import static com.daap.util.Constants.ImageButton;
import static com.daap.util.Constants.ImageView;
import static com.daap.util.Constants.LinearLayout;
import static com.daap.util.Constants.ListView;
import static com.daap.util.Constants.RadioButton;
import static com.daap.util.Constants.RadioGroup;
import static com.daap.util.Constants.RelativeLayout;
import static com.daap.util.Constants.Spinner;
import static com.daap.util.Constants.TableLayout;
import static com.daap.util.Constants.TextView;
import static com.daap.util.Constants.View;

public class MyDpvdXmlVisitor {


    public static void detectFromXmlFiles(File projectDir) {
        new DirExplorer((level, path, file) -> path.endsWith(".xml"), (level, path, file) -> {

            System.out.println(path);
            System.out.println("Name: " + file.getName());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = dBuilder.parse(file);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            if (file.getName().equals(ANDROID_MANIFEST)) {
                System.out.println("ANDROID_MANIFEST file path : " + path);
            }
            if (file.getName().equals(ANDROID_MANIFEST) && !path.contains("debug")) {
                detectFromManifest(doc, file.getName(), path);
            }

            if (path.contains("res") && path.contains("layout")) {
                System.out.println("path is res layout");
                detectNotDescriptionUi(doc, file.getName(), path);
                detectNestedLayout(doc, file.getName(), path);
                detectUntouchable(doc, file.getName(), path);
                detectUncontrolledFocusOrder(doc, file.getName(), path);
                detectOverDrawnPixel(doc, file.getName(), path);
            }
        }).explore(projectDir);
    }


    private static void detectFromManifest(Document doc, String fileName, String path) {
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        detectConfigChanges(doc, fileName, path);
        detectDebuggable(doc, fileName, path);

    }

    private static void detectConfigChanges(Document doc, String fileName, String path) {
        NodeList activityList = doc.getElementsByTagName("activity");
        System.out.println("activityList size: " + activityList.getLength());

        for (int temp = 0; temp < activityList.getLength(); temp++) {
            Node nNode = activityList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.hasAttribute("android:configChanges")) {
                    System.out.println("configChanges detected");
                    LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.CONFIG_CHANGES));
//                    LegacySystem.getInstance().addConfigChanges();
                }
            }
        }
    }

    private static void detectDebuggable(Document doc, String fileName, String path) {
        NodeList applicationList = doc.getElementsByTagName("application");

        for (int temp = 0; temp < applicationList.getLength(); temp++) {
            Node nNode = applicationList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getAttribute("android:debuggable").equals("true")) {
                    System.out.println("Debuggable detected");
//                    LegacySystem.getInstance().setDebugable(true);
                    LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.DEBUGGABLE_RELEASE));
                }
                System.out.println("Debuggable : " + eElement.getAttribute("android:debuggable"));
            }
        }
    }


    private static boolean detectNotDescriptionUi(NodeList viewElements) {
        for (int temp = 0; temp < viewElements.getLength(); temp++) {
            Node nNode = viewElements.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (!eElement.hasAttribute("android:contentDescription")) {
                    System.out.println("NotDescriptionUi detected");
//                    LegacySystem.getInstance().addNotDescriptiveUI();
                    return true;
                    //                    break;
                }
            }
        }
        return false;
    }

    private static void detectNotDescriptionUi(Document doc, String fileName, String path) {
//        NodeList viewElements = doc.getElementsByTagName(EditText);
        if (detectNotDescriptionUi(doc.getElementsByTagName(View))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(EditText))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(TextView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(AutoCompleteTextView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(Button))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(ImageView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(ImageButton))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(CheckBox))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(RadioGroup))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(RadioButton))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(ListView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        } else if (detectNotDescriptionUi(doc.getElementsByTagName(Spinner))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI));
        }
    }

    private static void detectNestedLayout(Document doc, String fileName, String path) {
        NodeList applicationList = doc.getElementsByTagName(LinearLayout);

        for (int temp = 0; temp < applicationList.getLength(); temp++) {
            Node nNode = applicationList.item(temp);
            NodeList childNodes = nNode.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeName().equals(LinearLayout)) {
                    System.out.println("NestedLayout detected");
                    LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.NESTED_LAYOUT));
                    return;
                }
            }
        }
    }


    private static void detectUntouchable(Document doc, String fileName, String path) {
        if (detectUntouchable(doc.getElementsByTagName(Button))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNTOUCHABLE));
        } else if (detectUntouchable(doc.getElementsByTagName(ImageView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNTOUCHABLE));
        } else if (detectUntouchable(doc.getElementsByTagName(ImageButton))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNTOUCHABLE));
        } else if (detectUntouchable(doc.getElementsByTagName(CheckBox))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNTOUCHABLE));
        } else if (detectUntouchable(doc.getElementsByTagName(RadioButton))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNTOUCHABLE));
        } else if (detectUntouchable(doc.getElementsByTagName(ListView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNTOUCHABLE));
        }
    }

    private static boolean detectUntouchable(NodeList viewElements) {
        for (int temp = 0; temp < viewElements.getLength(); temp++) {
            Node nNode = viewElements.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.hasAttribute("android:layout_height") && eElement.hasAttribute("android:layout_width")) {
                    System.out.println("layout_height detected");
                    System.out.println("Height Value: " + eElement.getAttribute("android:layout_height"));
                    String height = eElement.getAttribute("android:layout_height").replaceAll("[^0-9]", "");
                    System.out.println("intValue: " + height);
                    String width = eElement.getAttribute("android:layout_height").replaceAll("[^0-9]", "");
                    System.out.println("intValue: " + width);
                    if (!height.isEmpty() && !width.isEmpty()) {
                        if (Integer.valueOf(height) < 48 || Integer.valueOf(width) < 48) {

                            System.out.println("Untouchable detected");
                            System.out.println("Untouchable detected");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private static boolean detectUncontrolledFocusOrder(NodeList viewElements) {
        for (int temp = 0; temp < viewElements.getLength(); temp++) {
            Node nNode = viewElements.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (!eElement.hasAttribute("android:nextFocusDown") &&
                        !eElement.hasAttribute("android:nextFocusUp") &&
                        !eElement.hasAttribute("android:nextFocusRight") &&
                        !eElement.hasAttribute("android:nextFocusLeft")) {
                    System.out.println("UncontrolledFocusOrder detected");
                    return true;
//                    LegacySystem.getInstance().addConfigChanges();
//                    break;
                }
            }
        }
        return false;
    }

    private static void detectUncontrolledFocusOrder(Document doc, String fileName, String path) {

        if (detectUncontrolledFocusOrder(doc.getElementsByTagName(Button))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(ImageView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(ImageButton))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(CheckBox))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(RadioButton))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(ListView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(EditText))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(TextView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        } else if (detectUncontrolledFocusOrder(doc.getElementsByTagName(AutoCompleteTextView))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER));
        }
    }

    private static boolean detectOverDrawnPixels(NodeList viewElements) {
        for (int temp = 0; temp < viewElements.getLength(); temp++) {
            Node nNode = viewElements.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.hasAttribute("android:background") && (
                        eElement.getAttribute("android:background").contains(COLOR_VAL) ||
                                eElement.getAttribute("android:background").contains(COLOR_HEX))) {
                    NodeList childNodes = eElement.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        Element cElement = (Element) nNode;
                        if (cElement.hasAttribute("android:background") && (
                                cElement.getAttribute("android:background").contains(COLOR_VAL) ||
                                        cElement.getAttribute("android:background").contains(COLOR_HEX))) {
                            System.out.println("OverDrawn Pixel detected");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void detectOverDrawnPixel(Document doc, String fileName, String path) {
        if (detectOverDrawnPixels(doc.getElementsByTagName(LinearLayout))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.OVERDRAWN_PIXEL));
        } else if (detectOverDrawnPixels(doc.getElementsByTagName(FrameLayout))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.OVERDRAWN_PIXEL));
        } else if (detectOverDrawnPixels(doc.getElementsByTagName(RelativeLayout))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.OVERDRAWN_PIXEL));
        } else if (detectOverDrawnPixels(doc.getElementsByTagName(TableLayout))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.OVERDRAWN_PIXEL));
        } else if (detectOverDrawnPixels(doc.getElementsByTagName(AbsoluteLayout))) {
            LegacySystem.getInstance().addSmellyLayout(new Layout(path, fileName, Constants.XmlAntiPattern.OVERDRAWN_PIXEL));
        }
    }
}
