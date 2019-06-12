package com.daap.engine.anti_patterns.mobile;

import com.daap.model.DetectedInstance;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.ui.DAP;
import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;

/**
 * Created by Azhar on 9/21/2017.
 */
public class MIMDetectionEngine {
    private static final int DIT = 6;
    private static int total = 0;
//    private static final String fileName = "Member Ignoring Method";

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
        ResultDocument resultDocument = new ResultDocument(Constants.A_MEMBER_IGNORING_METHOD);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();
        total = 0;  // check it.
        System.out.println("======================STARTED-------------------");


        for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {
            for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {

//                if (methodDeclaration.isStatic()){
//                    continue;
//                }
//                boolean contains = false;
//                for (FieldDeclaration  fieldDeclaration: legacyClass.getFieldDeclarations()){
////                    getFieldsAccessList(methodDeclaration);
//
//                    if (getFieldsAccessList(methodDeclaration).contains(fieldDeclaration.getVariable(0).getNameAsString())){
//                        contains = true;
//                        break;
//                    }
//                }
//
//                if (!contains){
//                    total++;
//                    DAP.writeMessage("Class: " + legacyClass.getName());
//                    System.out.println("legacyClass: " + legacyClass.getName() + " methodDeclaration: " + methodDeclaration.getNameAsString());
//                }
//                methodDeclaration.accept(new VoidVisitorAdapter<Object>() {
//                    @Override
//                    public void visit(final FieldAccessExpr fieldAccessExpr, final Object arg) {
//                        super.visit(fieldAccessExpr, arg);
//
////                        System.out.println("FieldAccessExpr: " + fieldAccessExpr.getNameAsString());
//                        boolean is = true;
//                        for (FieldDeclaration fieldDeclaration : legacyClass.getFieldDeclarations()) {
//                            if (fieldAccessExpr.getNameAsString().equals(fieldDeclaration.getVariable(0).getNameAsString())) {
//                                is = false;
//                                break;
//                            }
//                        }
//                        if (is) {
//                            total++;
//                            DAP.writeMessage("Class: " + legacyClass.getName());
//                            System.out.println("legacyClass: " + legacyClass.getName() + " methodDeclaration: " + methodDeclaration.getNameAsString());
//                        }
//                    }
//                }, null);

                if (methodDeclaration.isStatic()) {
                    break;
                }
                NodeList<Statement> fieldsAccessList = getFieldsAccessList(methodDeclaration);

//                System.out.println("fieldsAccessList size: "+fieldsAccessList.size());
                boolean contains = false;
                outer:
                for (FieldDeclaration fieldDeclaration : legacyClass.getFieldDeclarations()) {
//                    System.out.println("fieldDeclaration: " +fieldDeclaration.getVariable(0).getNameAsString());
                    inner:
                    for (Statement statement : fieldsAccessList) {
                        if (statement.toString().contains(fieldDeclaration.getVariable(0).getNameAsString())) {
                            contains = true;
                            System.out.println("found ");
                            break outer;
                        }
                    }
//                    if (fieldsAccessList.contains(fieldDeclaration.getVariable(0).getNameAsString())) {
//                        contains = true;
//                        System.out.println("found ");
//                        break;
//                    }
                }

                if (!contains) {
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
                    System.out.println("legacyClass: " + legacyClass.getName() + " methodDeclaration: " + methodDeclaration.getNameAsString());
                    break;
                }
            }


//            for (ObjectCreationExpr objectCreationExpr : legacyClass.getObjectCreationExprs()) {
//                System.out.println("legacyClass: "+legacyClass.getName()+" objectCreationExpr: "+objectCreationExpr.toString());
//            }
        }
        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        Helper.writeFile(doc, "MemberIgnoringMethod");
        System.out.println("======================FINISHED-------------------");


        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
//        dsfdsf

    }


    private static NodeList<Statement> getFieldsAccessList(MethodDeclaration methodDeclaration) {

//        System.out.println(methodDeclaration.getBody().get().getStatements().toString());
//        System.out.println(methodDeclaration.getChildNodes().toString());

        NodeList<Statement> statements = new NodeList<>();

        try {
            statements = methodDeclaration.getBody().get().getStatements();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statements;

    }

//    private static  ArrayList<String> getFieldsAccessList(MethodDeclaration methodDeclaration){
//
//        ArrayList<String> result = new ArrayList<>();
//        methodDeclaration.accept(new VoidVisitorAdapter<Object>() {
//            @Override
//            public void visit(final FieldAccessExpr fieldAccessExpr, final Object arg) {
//                super.visit(fieldAccessExpr, arg);
//
//                result.add(fieldAccessExpr.getNameAsString());
//            }
//        }, null);
//
//        return result;
//    }
}
