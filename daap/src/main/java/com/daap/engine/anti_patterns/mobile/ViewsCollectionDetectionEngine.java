package com.daap.engine.anti_patterns.mobile;

import com.daap.model.DetectedInstance;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.ui.DAP;
import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.util.ArrayList;

/**
 * Created by Azhar on 9/21/2017.
 */
public class ViewsCollectionDetectionEngine {


    public static void detect() {
//        XWPFDocument doc;
//        XWPFTable table;
//        doc = new XWPFDocument();
//        table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("Class");
////        table.getRow(0).createCell().setText("Method");
//        table.getRow(0).createCell().setText("Path");

        DAP.clearConsole();
        ResultDocument resultDocument = new ResultDocument(Constants.A_VIEWS_IN_COLLECTION);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        int total = 0;
        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
            for (FieldDeclaration fieldDeclaration : legacyClass.getFieldDeclarations()) {
                System.out.println("fieldDeclaration : " + fieldDeclaration.toString());
                System.out.println("fieldDeclaration : " + fieldDeclaration.getCommonType());
                if (isTypeCollection(fieldDeclaration) && isTypeView(fieldDeclaration)) {
                    total++;
//                    DAP.writeMessage("Class: " + legacyClass.getName());
                    DAP.writeMessage("Class Name: " + legacyClass.getName()
                            + "\nPath: " + legacyClass.getPath() + "\n"
                    );
//                    Helper.writeDoc(table, legacyClass, total);
                    boolean exists = true;
                    for (DetectedInstance detectedInstance: detectedInstances){
                        if (detectedInstance.getName().equals(legacyClass.getName())){
                            detectedInstance.increment();
                            exists = false;
                            break;
                        }
                    }
                    if (exists){
                        detectedInstances.add(new DetectedInstance(legacyClass.getName(), legacyClass.getPath(), 1));
                    }
                }
            }
        }

        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        Helper.writeFile(doc, "ViewCollection");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
    }

    private static boolean isTypeCollection(FieldDeclaration fieldDeclaration) {
        if (fieldDeclaration.getCommonType().toString().contains(Constants.List) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.ArrayList) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.LinkedList) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.LinkedHashSet) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.LinkedHashMap) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.HashMap) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.Map) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.TreeMap) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.Vector) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.Hashtable) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.HashSet) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.Collection)
                ) {
            return true;
        }
        return false;
    }

    private static boolean isTypeView(FieldDeclaration fieldDeclaration) {

        if (fieldDeclaration.getCommonType().toString().contains(Constants.TextView) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.AutoCompleteTextView) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.EditText) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.AbsoluteLayout) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.LinearLayout) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.RelativeLayout) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.FrameLayout) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.TableLayout) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.GridLayout) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.GridView) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.Button) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.ViewPager) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.RecyclerView) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.ImageButton) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.ImageView) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.CheckBox) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.RadioGroup) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.RadioButton) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.ListView) ||
                fieldDeclaration.getCommonType().toString().contains(Constants.Spinner)
                ) {
            return true;
        }
        return false;
    }
}
