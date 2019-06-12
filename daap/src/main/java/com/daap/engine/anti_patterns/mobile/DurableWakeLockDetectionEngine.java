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
public class DurableWakeLockDetectionEngine {


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
        ResultDocument resultDocument = new ResultDocument(Constants.A_DURABLE_WAKE_LOCK);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        int total = 0;
        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {

            if (isDurableWakeLock(legacyClass)){
                total++;
//                DAP.writeMessage("Class: " + legacyClass.getName());
                DAP.writeMessage("Class Name: " + legacyClass.getName()
                        + "\nPath: " + legacyClass.getPath() + "\n"
                );
//                Helper.writeDoc(table, legacyClass, total);
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

        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        Helper.writeFile(doc, "DurableWakeLock");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
    }

    private static boolean isDurableWakeLock(LegacyClass legacyClass) {
        boolean acquireFound = false;
        for (MethodCallExpr methodCallExpr : legacyClass.getMethodCallExprs()) {
            if (methodCallExpr.getNameAsString().contains("acquire")) {
//                System.out.println("acquire found");
                acquireFound = true;
                break;
            }
        }

        boolean releaseFound = false;
        if (acquireFound) {
            for (MethodCallExpr methodCallExpr : legacyClass.getMethodCallExprs()) {
                if (methodCallExpr.getNameAsString().contains("release")) {
                    releaseFound = true;
                    break;
                }
            }

        }
        return acquireFound && !releaseFound;
    }

}
