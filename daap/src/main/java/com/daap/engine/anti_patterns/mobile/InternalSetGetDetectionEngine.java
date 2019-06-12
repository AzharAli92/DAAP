package com.daap.engine.anti_patterns.mobile;

import com.daap.model.DetectedInstance;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.ui.DAP;
import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * Created by Azhar on 9/21/2017.
 */
public class InternalSetGetDetectionEngine {

    private static int total = 0;
//    private static final String fileName = "InternalSetGet";

    public static void detect() {
//        XWPFDocument doc;
//        XWPFTable table;
//        doc = new XWPFDocument();
//        table = doc.createTable();
//        table.getRow(0).getCell(0).setText("Sr. No");
//        table.getRow(0).createCell().setText("Class");
////        table.getRow(0).createCell().setText("Method");
//        table.getRow(0).createCell().setText("Path");

        total = 0;
        DAP.clearConsole();
        ResultDocument resultDocument = new ResultDocument(Constants.A_INTERNAL_GETTER_SETTER);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        System.out.println("======================STARTED-------------------");


//        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
//
//            for (MethodCallExpr methodCallExpr : legacyClass.getMethodCallExprs()) {
//                if (isGetterSetterMethod(methodCallExpr, legacyClass)) {
//                    if (isInternalMethod(methodCallExpr, legacyClass)) {
//                        System.out.println("legacyClass: " + legacyClass.getName() + " methodCallExpr: " + methodCallExpr.getNameAsString());
//                        total++;
//                        DAP.writeMessage("Class: " + legacyClass.getName());
//
//                    }
//                }
//            }
//        }

//        outer:
        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {

            for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {

                for (MethodCallExpr methodCallExpr : getMethodCalls(methodDeclaration)) {

                    if (isGetterSetterMethod(methodCallExpr, legacyClass)) {
                        if (isInternalMethod(methodCallExpr, legacyClass)) {
                            System.out.println("legacyClass: " + legacyClass.getName() + " methodCallExpr: " + methodCallExpr.getNameAsString());
                            total++;
//                            DAP.writeMessage("Class: " + legacyClass.getName());
                            DAP.writeMessage("Class Name: " + legacyClass.getName()
                                    + "\nPath: " + legacyClass.getPath() + "\n"
                            );
//                            Helper.writeDoc(table, legacyClass, total);
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

//                            continue outer;
                        }
                    }
                }
            }
        }

        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();

//        Helper.writeFile(doc, "InternalSetGet");
        System.out.println("======================FINISHED-------------------");

        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);

    }

    private static ArrayList<MethodCallExpr> getMethodCalls(MethodDeclaration methodDeclaration) {
        ArrayList<MethodCallExpr> methodCallExprs = new ArrayList<>();
        methodDeclaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr methodCallExpr, Object javaParserFacade) {
                super.visit(methodCallExpr, javaParserFacade);
                methodCallExprs.add(methodCallExpr);
            }
        }, null);
        return methodCallExprs;
    }

    private static boolean isGetterSetterMethod(MethodCallExpr methodDeclaration, LegacyClass legacyClass) {
        for (FieldDeclaration var : legacyClass.getFieldDeclarations()) {
            if (methodDeclaration.getNameAsString().toLowerCase().equals("get" + var.getVariables().get(0).getNameAsString())) {
                return true;
            } else if (methodDeclaration.getNameAsString().toLowerCase().equals("set" + var.getVariables().get(0).getNameAsString())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInternalMethod(MethodCallExpr methodCallExpr, LegacyClass legacyClass) {
        for (MethodDeclaration method : legacyClass.getMethodDeclarations()) {
            if (methodCallExpr.getName().equals(method.getName())) {
                return true;
            }
        }
        return false;
    }
}
