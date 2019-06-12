package com.daap.engine.anti_patterns.mobile;

import com.daap.model.DetectedInstance;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.model.Relation;
import com.daap.model.Relations;
import com.daap.ui.DAP;
import com.daap.util.Constants;
import com.daap.util.Helper;
import com.daap.util.ResultDocument;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * Created by Azhar on 9/21/2017.
 */
public class LeakingInnerClassDetectionEngine {

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

        total = 0;
        DAP.clearConsole();
        ResultDocument resultDocument = new ResultDocument(Constants.A_LEAKING_INNER_CLASS);
        ArrayList<DetectedInstance> detectedInstances = new ArrayList<>();

        System.out.println("======================STARTED-------------------");

        Relations hasRelations = LegacySystem.getInstance().getHasRelations();
        for (Relation relation : hasRelations) {
//            System.out.println("legacyClass: " + connection.printRelation());
            if (relation.getFrom().getClassOrInterfaceDeclaration().isInnerClass()) {
//                System.out.println("legacyClass: " + connection.getTo().getName());

                LegacyClass innerClass = relation.getFrom();
                LegacyClass outerClass = relation.getTo();

                outerClass.getClassOrInterfaceDeclaration().accept(new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration classDeclaration, Object javaParser) {
                        super.visit(classDeclaration, javaParser);
//                System.out.println("Inner LegacyClass : " + classDeclaration.getNameAsString());
                        if (classDeclaration.isInnerClass() && classDeclaration.getNameAsString().equals(innerClass.getName())) {

                            total++;
//                            DAP.writeMessage("Class: " + classDeclaration.getName());
                            DAP.writeMessage("Class Name: " + innerClass.getName()
                                    + "\nPath: " + outerClass.getPath() + "\n"
                            );
//                            Helper.writeDoc(table, innerClass, total);
                            System.out.println("Inner Class : " + classDeclaration.getNameAsString() + " has Outer Class: " + outerClass.getName());
//                            System.out.println("Total : " + total);
                            boolean exists = true;
                            for (DetectedInstance detectedInstance: detectedInstances){
                                if (detectedInstance.getName().equals(innerClass.getName())){
                                    detectedInstance.increment();
                                    exists = false;
                                    break;
                                }
                            }
                            if (exists){
                                detectedInstances.add(new DetectedInstance(innerClass.getName(), innerClass.getPath(), 1));
                            }
                        }
                    }
                }, null);
            }
        }
        System.out.println("total: " + total);
        DAP.writeMessage("Total: " + total);
        DAP.detectionDone();
//        Helper.writeFile(doc, "LeakingInnerClass");
        System.out.println("======================FINISHED-------------------");
        int srNo = 1;
        for (DetectedInstance detectedInstance : detectedInstances) {
            Helper.writeDoc(resultDocument, detectedInstance, srNo++);
        }
        Helper.writeFile(resultDocument);
    }

    private static String getClassHeririchy(String className) {
        LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(className);
        if (legacyClass == null) {
            return className;
        } else {
            if (legacyClass.getParentClass() != null) {
                return className + " -> " + getClassHeririchy(legacyClass.getParentClass());
            } else {
                return className;
            }
        }
    }
}
