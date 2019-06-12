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
public class StaticViewDetectionEngine {


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
        ResultDocument resultDocument = new ResultDocument(Constants.A_STATIC_VIEW);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        int total = 0;

        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
            for (FieldDeclaration fieldDeclaration : legacyClass.getFieldDeclarations()) {
                if (fieldDeclaration.isStatic() && isTypeView(fieldDeclaration)) {
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
//        Helper.writeFile(doc, "StaticView");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
    }

    private static boolean isTypeView(FieldDeclaration fieldDeclaration) {

        if (fieldDeclaration.getCommonType().toString().equals(Constants.TextView) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.AutoCompleteTextView) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.EditText) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.AbsoluteLayout) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.LinearLayout) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.RelativeLayout) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.FrameLayout) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.TableLayout) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.GridLayout) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.GridView) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.Button) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.ViewPager) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.RecyclerView) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.ImageButton) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.ImageView) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.CheckBox) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.RadioGroup) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.RadioButton) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.ListView) ||
                fieldDeclaration.getCommonType().toString().equals(Constants.Spinner)
                ) {
            return true;
        }
        return false;
    }


}
