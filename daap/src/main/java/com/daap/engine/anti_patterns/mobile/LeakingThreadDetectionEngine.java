package com.daap.engine.anti_patterns.mobile;

import com.daap.model.DetectedInstance;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.ui.DAP;
import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.ArrayList;

/**
 * Created by Azhar on 9/21/2017.
 */
public class LeakingThreadDetectionEngine {


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
        ResultDocument resultDocument = new ResultDocument(Constants.A_LEAKING_THREAD);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        int total = 0;
        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {

            if (isClassLeakingThread(legacyClass)) {
                total++;
//                DAP.writeMessage("Class: " + legacyClass.getName());
                DAP.writeMessage("Class Name: " + legacyClass.getName()
                        + "\nPath: " + legacyClass.getPath() + "\n"
                );
//                Helper.writeDoc(table, legacyClass, total);
                System.out.println("legacyClass: " + legacyClass.getName());
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

        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        Helper.writeFile(doc, "LeakingThread");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);

    }

//    private static boolean isClassLeakingThread(LegacyClass legacyClass) {
//
//        if (legacyClass.toString().contains("run()")) {
//            if (!legacyClass.toString().contains("stop()")) {
//                return true;
//            }
//        }
//        return false;
//    }


    private static boolean isClassLeakingThread(LegacyClass legacyClass) {
        for (MethodCallExpr methodCallExpr : legacyClass.getMethodCallExprs()) {
            if (methodCallExpr.getNameAsString().equals("run")) {
                if (!isStopFound(legacyClass)) {
                    System.out.println("true detection.");
                    return true;
                }
            }
//            System.out.println("methodCallExpr: " + methodCallExpr.toString());
//            System.out.println("methodCallExpr: " + methodCallExpr.getName());
//            System.out.println("methodCallExpr: " + methodCallExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class));
        }
//        if (legacyClass.toString().contains("run()")) {
//            if (!legacyClass.toString().contains("stop()")) {
//                return true;
//            }
//        }
        return false;
    }

    private static boolean isStopFound(LegacyClass legacyClass) {
        for (MethodCallExpr methodCallExpr : legacyClass.getMethodCallExprs()) {
            if (methodCallExpr.getNameAsString().equals("stop")) {
                return true;
            }
        }
        return false;
    }
}
