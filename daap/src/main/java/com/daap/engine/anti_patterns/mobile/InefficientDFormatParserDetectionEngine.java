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
public class InefficientDFormatParserDetectionEngine {
    private static int total = 0;

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
        ResultDocument resultDocument = new ResultDocument(Constants.A_INEFFICIENT_DATA_FORMAT_PARSER);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        total = 0;
        System.out.println("======================STARTED-------------------");

        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
            for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {
                if (methodDeclaration.toString().contains("DocumentBuilderFactory")
                        || methodDeclaration.toString().contains("DocumentBuilder")
                        || methodDeclaration.toString().contains("NodeList")) {

                    System.out.println("legacyClass: " + legacyClass.getName() + " methodDeclaration: " + methodDeclaration.getNameAsString());
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
//        Helper.writeFile(doc, "InefficientDFormatParser");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
    }

}
