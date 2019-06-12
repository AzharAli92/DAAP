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
public class DroppedDataDetectionEngine {


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
        ResultDocument resultDocument = new ResultDocument(Constants.A_DROPPED_DATA);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        int total = 0;
        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
            if (legacyClass.getParentClass() != null && (legacyClass.getParentClass().contains("Activity")
                    || legacyClass.getParentClass().contains("Fragment"))) {
                if (isDroppedDataMethodEsists(legacyClass)) {
                    total++;
//                    DAP.writeMessage("Class: " + legacyClass.getName());
                    DAP.writeMessage("Class Name: " + legacyClass.getName()
                            + "\nPath: " + legacyClass.getPath() + "\n"
                    );
                    System.out.println("legacyClass: " + legacyClass.getName());
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
//                    writeDoc(legacyClass, total);
                }
            }
        }

        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        writeFile();
//        Helper.writeFile(doc, "DroppedData");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
    }

//    private static void writeDoc(LegacyClass legacyClass, int total) {
////        for (int i = 1; i <= 3; i++) {
//        XWPFTableRow row1 = table.createRow(); //Note: There's no second row by default, we have to create one
//        row1.getCell(0).setText(String.valueOf(total));
//        row1.getCell(1).setText(legacyClass.getName()); //The second column has already been created, now we just take it
////            row1.getCell(2).setText("Method " + i);
//        row1.getCell(2).setText(legacyClass.getPath());
////        }
//    }

//    private static void writeFile() {
//        try {
//            System.out.println("try block");
//            FileOutputStream fileOutputStream = new FileOutputStream(LegacySystem.resultsPath + "DroppedData.docx");
//            doc.write(fileOutputStream);
////            doc1.writeFile(fileOutputStream);
//            fileOutputStream.close();
//            System.out.println("end try block");
//        } catch (Exception e) {
//            System.out.println("catch block e: " + e.toString());
//            e.printStackTrace();
//        }
//    }

    private static boolean isDroppedDataMethodEsists(LegacyClass legacyClass) {
        boolean isOnSaveInstanceState = true;
        boolean isOnRestoreInstanceState = true;
        for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {
            if (methodDeclaration.getNameAsString().equals("onSaveInstanceState")) {
                isOnSaveInstanceState = false;
                break;
            }
        }
        for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {
            if (methodDeclaration.getNameAsString().equals("onRestoreInstanceState")) {
                isOnRestoreInstanceState = false;
                break;
            }
        }
        return isOnSaveInstanceState && isOnRestoreInstanceState;
    }
}
