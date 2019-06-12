package com.daap.model;

import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;


public class LegacySystem {
    private static LegacySystem legacySystem = null;

    public static LegacySystem getInstance() {
        if (legacySystem == null) {
            legacySystem = new LegacySystem();
        }
        return legacySystem;
    }

    private LegacySystem() {
        this.classList = new ArrayList<>();
        this.smellyLayouts = new ArrayList<>();
        this.classNameMap = new HashMap<String, Integer>();
//        Relations default initialization
        this.relations = new Relations();
//        this.allRelations = new Relations();
        this.callsRelations = new Relations();
        this.createsRelations = new Relations();
        this.referencesRelations = new Relations();
        this.usesRelations = new Relations();
        this.inheritsRelations = new Relations();
        this.hasRelations = new Relations();
        this.relatesRelations = new Relations();
//        this.isDebugable = false;
//        this.configChanges = 0;
//        this.notDescriptiveUI = 0;
//        this.nestedLayout = 0;
    }


    //    private boolean isDebugable;
//    private int configChanges;
//    private int notDescriptiveUI;
//    private int nestedLayout;
    public static final String resultsPath = "C:\\Users\\Ali\\Dropbox\\Suave\\results\\";

    private List<Layout> smellyLayouts;
    private List<LegacyClass> classList;
    //Map that has as key the classname and as value
    //the position of className in the classNameList
    private HashMap<String, Integer> classNameMap;
    private Relations relations;
    //    private Relations allRelations;
    private Relations callsRelations;
    private Relations createsRelations;
    private Relations referencesRelations;
    private Relations usesRelations;
    private Relations inheritsRelations;
    private Relations hasRelations;
    private Relations relatesRelations;

    public HashMap<String, Integer> getClassNameMap() {
        return classNameMap;
    }

    public void setRelations(Relations relations) {
        this.relations = relations;
    }

    public Relations getRelations() {
        return relations;
    }

//    public void setAllRelations(Relations allRelations) {
//        this.allRelations = allRelations;
//    }

//    public Relations getAllRelations() {
//        return allRelations;
//    }

    public void reset() {
        this.classList = new ArrayList<LegacyClass>();
        this.smellyLayouts = new ArrayList<>();
        this.classNameMap = new HashMap<String, Integer>();
//        Relations default initialization
        this.relations = new Relations();
//        this.allRelations = new Relations();
        this.callsRelations = new Relations();
        this.createsRelations = new Relations();
        this.referencesRelations = new Relations();
        this.usesRelations = new Relations();
        this.inheritsRelations = new Relations();
        this.hasRelations = new Relations();
        this.relatesRelations = new Relations();
//        this.isDebugable = false;
//        this.configChanges = 0;
//        this.notDescriptiveUI = 0;
//        this.nestedLayout = 0;
    }


    public int getDebuggable() {
        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.DEBUGGABLE_RELEASE) {

                XWPFDocument doc;
                XWPFTable table;
                doc = new XWPFDocument();
                table = doc.createTable();
                table.getRow(0).getCell(0).setText("Sr. No");
                table.getRow(0).createCell().setText("XML File");
                table.getRow(0).createCell().setText("Path");

                Helper.writeDoc(table, layout, 1);

                Helper.writeFile(doc, "DebuggableRelease");

                return 1;
            }
        }
        return 0;
    }

    public int getNestedLayouts() {
        int count = 0;
//        XWPFDocument doc = new XWPFDocument();
//        XWPFTable table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("XML File");
//        table.getRow(0).createCell().setText("Path");
        ResultDocument resultDocument = new ResultDocument(Constants.A_NESTED_LAYOUT);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();

        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.NESTED_LAYOUT) {
                count++;
//                Helper.writeDoc(table, layout, count);
                boolean exists = true;
                for (DetectedInstance detectedInstance: detectedInstances){
                    if (detectedInstance.getName().equals(layout.getName())){
                        detectedInstance.increment();
                        exists = false;
                        break;
                    }
                }
                if (exists){
                    detectedInstances.add(new DetectedInstance(layout.getName(), layout.getPath(), 1));
                }

            }
        }
//        Helper.writeFile(doc, "NestedLayout");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
        return count;
    }

//    public int getUntouchable() {
//        int count = 0;
//        for (Layout layout : smellyLayouts) {
//            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.UNTOUCHABLE) {
//                count++;
//            }
//        }
//        return count;
//    }

    public int getNotDescriptiveUi() {
        int count = 0;

//        XWPFDocument doc = new XWPFDocument();
//        XWPFTable table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("XML File");
//        table.getRow(0).createCell().setText("Path");
        ResultDocument resultDocument = new ResultDocument(Constants.A_NOT_DESCRIPTIVE_UI);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.NOT_DESCRIPTIVE_UI) {
                count++;
//                Helper.writeDoc(table, layout, count);
                boolean exists = true;
                for (DetectedInstance detectedInstance: detectedInstances){
                    if (detectedInstance.getName().equals(layout.getName())){
                        detectedInstance.increment();
                        exists = false;
                        break;
                    }
                }
                if (exists){
                    detectedInstances.add(new DetectedInstance(layout.getName(), layout.getPath(), 1));
                }
            }
        }
//        Helper.writeFile(doc, "NotDescriptiveUI");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
        return count;
    }

    public int getConfigChanges() {
        int count = 0;

//        XWPFDocument doc = new XWPFDocument();
//        XWPFTable table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("XML File");
//        table.getRow(0).createCell().setText("Path");
        ResultDocument resultDocument = new ResultDocument(Constants.A_CONFIG_CHANGES);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.CONFIG_CHANGES) {
                count++;
//                Helper.writeDoc(table, layout, count);
                boolean exists = true;
                for (DetectedInstance detectedInstance: detectedInstances){
                    if (detectedInstance.getName().equals(layout.getName())){
                        detectedInstance.increment();
                        exists = false;
                        break;
                    }
                }
                if (exists){
                    detectedInstances.add(new DetectedInstance(layout.getName(), layout.getPath(), 1));
                }
            }
        }
//        Helper.writeFile(doc, "ConfigChnages");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
        return count;
    }

    public int getUntouchables() {
        int count = 0;
//        XWPFDocument doc = new XWPFDocument();
//        XWPFTable table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("XML File");
//        table.getRow(0).createCell().setText("Path");
        ResultDocument resultDocument = new ResultDocument(Constants.A_UNTOUCHABLE);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.UNTOUCHABLE) {
                count++;
//                Helper.writeDoc(table, layout, count);
                boolean exists = true;
                for (DetectedInstance detectedInstance: detectedInstances){
                    if (detectedInstance.getName().equals(layout.getName())){
                        detectedInstance.increment();
                        exists = false;
                        break;
                    }
                }
                if (exists){
                    detectedInstances.add(new DetectedInstance(layout.getName(), layout.getPath(), 1));
                }
            }
        }

//        Helper.writeFile(doc, "Untouchables");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
        return count;
    }

    public int getUncontrolledFocusOrder() {
        int count = 0;

//        XWPFDocument doc = new XWPFDocument();
//        XWPFTable table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("XML File");
//        table.getRow(0).createCell().setText("Path");
        ResultDocument resultDocument = new ResultDocument(Constants.A_UNCONTROLLED_FOCUS_ORDER);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.UNCONTROLLED_FOCUS_ORDER) {
                count++;
//                Helper.writeDoc(table, layout, count);
                boolean exists = true;
                for (DetectedInstance detectedInstance: detectedInstances){
                    if (detectedInstance.getName().equals(layout.getName())){
                        detectedInstance.increment();
                        exists = false;
                        break;
                    }
                }
                if (exists){
                    detectedInstances.add(new DetectedInstance(layout.getName(), layout.getPath(), 1));
                }
            }
        }
//        Helper.writeFile(doc, "UncontrolledFocusOrder");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
        return count;
    }

    public int getOverDrawnPixel() {
        int count = 0;
//        XWPFDocument doc = new XWPFDocument();
//        XWPFTable table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("XML File");
//        table.getRow(0).createCell().setText("Path");
        ResultDocument resultDocument = new ResultDocument(Constants.A_OVERDRAWN_PIXEL);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        for (Layout layout : smellyLayouts) {
            if (layout.getXmlAntiPattern() == Constants.XmlAntiPattern.OVERDRAWN_PIXEL) {
                count++;
//                Helper.writeDoc(table, layout, count);
                boolean exists = true;
                for (DetectedInstance detectedInstance: detectedInstances){
                    if (detectedInstance.getName().equals(layout.getName())){
                        detectedInstance.increment();
                        exists = false;
                        break;
                    }
                }
                if (exists){
                    detectedInstances.add(new DetectedInstance(layout.getName(), layout.getPath(), 1));
                }
            }
        }
//        Helper.writeFile(doc, "OverdrawnPixel");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
        return count;
    }

    public void addSmellyLayout(Layout layout) {
        if (smellyLayouts == null) {
            smellyLayouts = new ArrayList<>();
        }
        smellyLayouts.add(layout);
    }

    public List<Layout> getSmellyLayouts() {
        return smellyLayouts;
    }

    public void setSmellyLayouts(List<Layout> smellyLayouts) {
        this.smellyLayouts = smellyLayouts;
    }

//    public int getNestedLayout() {
//        return nestedLayout;
//    }

//    public void setNestedLayout(int nestedLayout) {
//        this.nestedLayout = nestedLayout;
//    }

//    public void addNestedLayout() {
//        this.nestedLayout++;
//    }

//    public int getNotDescriptiveUI() {
//        return notDescriptiveUI;
//    }

//    public void setNotDescriptiveUI(int notDescriptiveUI) {
//        this.notDescriptiveUI = notDescriptiveUI;
//    }

//    public void addNotDescriptiveUI() {
//        this.notDescriptiveUI++;
//    }

//    public int getConfigChanges() {
//        return configChanges;
//    }

//    public void setConfigChanges(int configChanges) {
//        this.configChanges = configChanges;
//    }

//    public void addConfigChanges() {
//        this.configChanges++;
//    }

//    public boolean isDebugable() {
//        return isDebugable;
//    }
//
//    public void setDebugable(boolean debugable) {
//        isDebugable = debugable;
//    }

    public Relations getCallsRelations() {
        return callsRelations;
    }

    public Relations getCreatesRelations() {
        return createsRelations;
    }

    public Relations getReferencesRelations() {
        return referencesRelations;
    }

    public Relations getUsesRelations() {
        return usesRelations;
    }

    public Relations getInheritsRelations() {
        return inheritsRelations;
    }

    public Relations getHasRelations() {
        return hasRelations;
    }

    public Relations getRelatesRelations() {
        return relatesRelations;
    }

    public void setCallsRelations(Relations callsRelations) {
        this.callsRelations = callsRelations;
    }

    public void setCreatesRelations(Relations createsRelations) {
        this.createsRelations = createsRelations;
    }

    public void setReferencesRelations(Relations referencesRelations) {
        this.referencesRelations = referencesRelations;
    }

    public void setUsesRelations(Relations usesRelations) {
        this.usesRelations = usesRelations;
    }

    public void setInheritsRelations(Relations inheritsRelations) {
        this.inheritsRelations = inheritsRelations;
    }

    public void setHasRelations(Relations hasRelations) {
        this.hasRelations = hasRelations;
    }

    public void setRelatesRelations(Relations relatesRelations) {
        this.relatesRelations = relatesRelations;
    }


    //    public LegacySystem() {
//        this.classList = new ArrayList<LegacyClass>();
//        this.classNameMap = new HashMap<String,Integer>();
//    }

    public void addClass(LegacyClass c) {
//        classNameMap.put(c.getName().toString(),classList.size());
        classNameMap.put(c.getClassOrInterfaceDeclaration().getNameAsString(), classList.size());
        classList.add(c);
    }

    public LegacyClass getLegacyClass(String className) {
        Integer pos = classNameMap.get(className);
        if (pos != null)
            return getLegacyClass(pos);
        else
            return null;
    }

    public LegacyClass getLegacyClass(int pos) {
        return classList.get(pos);
    }

    public ListIterator<LegacyClass> getClassListIterator() {
        return classList.listIterator();
    }

    public int totalClasses() {
        return classList.size();
    }

    public int getClassNumber() {
        return classList.size();
    }

    public List<LegacyClass> getAllClasses() {
        return classList;
    }

    public int getPositionInClassList(String className) {
        Integer pos = classNameMap.get(className);
        if (pos != null)
            return pos;
        else
            return -1;
    }

    public List<String> getClassNames() {
        List<String> names = new ArrayList<String>();

        for (int i = 0; i < classList.size(); i++) {
            names.add(getLegacyClass(i).getClassOrInterfaceDeclaration().getNameAsString());
        }
        return names;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LegacyClass classObject : classList) {
            sb.append(classObject.toString());
            sb.append("\n--------------------------------------------------------------------------------\n");
        }
        return sb.toString();
    }
}