package com.daap.util;

import com.daap.model.DetectedInstance;
import com.daap.model.Layout;
import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.model.Relation;
import com.daap.ui.DAP;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private static String NEW_LINE = "\n";
    private static String CAR_RETURN = "\r";
    private static String TAB = "\t";
    private static String SPACE = " ";

    public static String removeWhiteSpaces(String body) {
        return body.replace(NEW_LINE, SPACE).replace(CAR_RETURN, SPACE).replaceAll(TAB, SPACE);
    }


    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isTypePrimitive(String s) {
        if (s.equalsIgnoreCase("String") || s.equalsIgnoreCase("Integer") || s.equalsIgnoreCase("Int")
                || s.equalsIgnoreCase("Void") || s.equalsIgnoreCase("Boolean") || s.equalsIgnoreCase("Character")
                || s.equalsIgnoreCase("Char") || s.equalsIgnoreCase("Byte") || s.equalsIgnoreCase("Short")
                || s.equalsIgnoreCase("Long") || s.equalsIgnoreCase("Float") || s.equalsIgnoreCase("Double")
                || s.equalsIgnoreCase("NullPointerException") || s.equalsIgnoreCase("") || s.contains("[]")
                || s.contains(">"))
            return true;
        else
            return false;
    }

    public static void printPattern(Relation... connections) {
        System.out.println("\n");
        for (Relation relation : connections) {
            System.out.println(relation.printRelation());
            DAP.writeMessage(relation.printRelation() + "\n");
            // do what ever you want
        }
//        System.out.println("\n");
    }

    public static String getString(Relation... connections) {
        String result = "";
        for (Relation relation : connections) {
            result += relation.printRelation() + "\n";
        }
        return result;
    }

    public static boolean hasSameMethod(LegacyClass legacyClass, LegacyClass legacyClass2) {
        for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {
            for (MethodDeclaration methodDeclaration1 : legacyClass2.getMethodDeclarations()) {
                if (methodDeclaration.getNameAsString().equals(methodDeclaration1.getNameAsString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int beginLine(Node node) {
        return node.getBegin().get().line;
    }

    public static int endLine(Node node) {
        return node.getEnd().get().line;
    }

    public static boolean isConcrete(ClassOrInterfaceDeclaration declaration) {
        return !declaration.isAbstract() && !declaration.isInterface();
    }

    public static boolean isStarter(String name) {
        if (name.startsWith("Init") || name.startsWith("init")
                || name.startsWith("Begin") || name.startsWith("begin")
                || name.startsWith("Start") || name.startsWith("start")
                || name.startsWith("Second") || name.startsWith("second"))
            return true;
        else
            return false;
    }


    public static void writeDoc(XWPFTable table, Layout layout, int total) {
//        for (int i = 1; i <= 3; i++) {
        XWPFTableRow row1 = table.createRow(); //Note: There's no second row by default, we have to create one
        row1.getCell(0).setText(String.valueOf(total));
        row1.getCell(1).setText(layout.getName()); //The second column has already been created, now we just take it
//            row1.getCell(2).setText("Method " + i);
        row1.getCell(2).setText(layout.getPath());
//        }
    }
//    public static void writeDoc(XWPFTable table, Layout layout, int total, int lineNo) {
////        for (int i = 1; i <= 3; i++) {
//        XWPFTableRow row1 = table.createRow(); //Note: There's no second row by default, we have to create one
//        row1.getCell(0).setText(String.valueOf(total));
//        row1.getCell(1).setText(layout.getName()); //The second column has already been created, now we just take it
////            row1.getCell(2).setText("Method " + i);
//        row1.getCell(2).setText(layout.getPath());
//        row1.getCell(3).setText(String.valueOf(lineNo));
////        }
//    }

    public static void writeDoc(XWPFTable table, LegacyClass legacyClass, int total) {
//        for (int i = 1; i <= 3; i++) {
        XWPFTableRow row1 = table.createRow(); //Note: There's no second row by default, we have to create one
        row1.getCell(0).setText(String.valueOf(total));
        row1.getCell(1).setText(legacyClass.getName()); //The second column has already been created, now we just take it
//            row1.getCell(2).setText("Method " + i);
        row1.getCell(2).setText(legacyClass.getPath());
//        }
    }

    public static void writeDoc(XWPFTable table, DetectedInstance detectedInstance, int total) {
//        for (int i = 1; i <= 3; i++) {
        XWPFTableRow row1 = table.createRow(); //Note: There's no second row by default, we have to create one
        row1.getCell(0).setText(String.valueOf(total));
        row1.getCell(1).setText(detectedInstance.getName()+" ("+detectedInstance.getCount()+")"); //The second column has already been created, now we just take it
//            row1.getCell(2).setText("Method " + i);
        row1.getCell(2).setText(detectedInstance.getPath());
//        }
    }
    public static void writeDoc(ResultDocument resultDocument, DetectedInstance detectedInstance, int total) {
//        for (int i = 1; i <= 3; i++) {
        XWPFTableRow row1 = resultDocument.getTable().createRow(); //Note: There's no second row by default, we have to create one
        row1.getCell(0).setText(String.valueOf(total));
        row1.getCell(1).setText(detectedInstance.getName()+" ("+detectedInstance.getCount()+")"); //The second column has already been created, now we just take it
//            row1.getCell(2).setText("Method " + i);
        row1.getCell(2).setText(detectedInstance.getPath());
//        }
    }

    public static void writeFile(ResultDocument doc) {
        try {
            System.out.println("try block");
            FileOutputStream fileOutputStream = new FileOutputStream(LegacySystem.resultsPath + doc.getName()+ ".docx");
            doc.getDoc().write(fileOutputStream);
//            doc1.writeFile(fileOutputStream);
            fileOutputStream.close();
            System.out.println("end try block");
        } catch (Exception e) {
            System.out.println("catch block e: " + e.toString());
            e.printStackTrace();
        }
    }
    public static void writeFile(XWPFDocument doc, String fileName) {
        try {
            System.out.println("try block");
            FileOutputStream fileOutputStream = new FileOutputStream(LegacySystem.resultsPath + fileName + ".docx");
            doc.write(fileOutputStream);
//            doc1.writeFile(fileOutputStream);
            fileOutputStream.close();
            System.out.println("end try block");
        } catch (Exception e) {
            System.out.println("catch block e: " + e.toString());
            e.printStackTrace();
        }
    }

    public static boolean contains(ArrayList<DetectedInstance> detectedInstances, String name){

        for (DetectedInstance detectedInstance: detectedInstances){
            if (detectedInstance.equals(name)){
                return true;
            }
        }

        return false;
    }

}
