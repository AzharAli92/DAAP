package com.daap.engine.anti_patterns.mobile;

import com.daap.model.DetectedInstance;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.ui.DAP;
import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;

/**
 * Created by Azhar on 9/21/2017.
 */
public class LowMemoryDetectionEngine {

//    private static final String fileName = "No Low Memory Resolver";

    public static void detect() {
//        XWPFDocument doc;
//        XWPFTable table;
//        doc = new XWPFDocument();
//        table = doc.createTable();
////        Helper.writeHeader(table, "No Low Memory Resolver");
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("Class");
////        table.getRow(0).createCell().setText("Method");
//        table.getRow(0).createCell().setText("Path");


        DAP.clearConsole();
        ResultDocument resultDocument = new ResultDocument(Constants.A_LOW_MEMORY);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();

        int total = 0;
        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {

            if (legacyClass.getParentClass() != null && legacyClass.getParentClass().contains("Activity")) {
                if (isLowMemoryMethodEsists(legacyClass)) {
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

                    System.out.println("legacyClass: " + legacyClass.getName());
                }
            }
        }

        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        Helper.writeFile(doc, "LowMemory");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument.getTable(), detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument.getDoc(), "LowMemory");
    }

    private static boolean isLowMemoryMethodEsists(LegacyClass legacyClass) {
        boolean isLowMemoryExists = true;
        for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {
            if (methodDeclaration.getNameAsString().equals("onLowMemory")) {
                return false;
            }
        }
        return isLowMemoryExists;
    }
}
