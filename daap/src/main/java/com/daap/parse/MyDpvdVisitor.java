package com.daap.parse;

import com.daap.model.LegacyClass;
import com.daap.model.LegacySystem;
import com.daap.util.DirExplorer;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import com.github.javaparser.ast.Modifier;
//import com.github.javaparser.ast.body.ModifierSet;
//import com.google.common.base.Strings;

public class MyDpvdVisitor {

//    public static LegacyObject legacyObject = new LegacyObject();
//    public static SingletonData singletonData = new SingletonData();


//    public static LegacySystem legacySystem;

    public static void listClasses(File projectDir) {
//    public static void listClasses(File projectDir) {
//        LegacySystem legacySystem = LegacySystem.getInstance();
//         legacySystem = new LegacySystem();

        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {

//            System.out.println(path);
//			System.out.println(Strings.repeat("=", path.length()));
//            System.out.println("file: " + file.getName());

//            final ClassObject co = new ClassObject();
//            final LegacyClass legacyClass = new LegacyClass();
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration classDeclaration, Object javaParser) {
                        super.visit(classDeclaration, javaParser);


//                        classDeclaration.accept(new VoidVisitorAdapter<Object>() {
//                            @Override
//                            public void visit(VariableDeclarator variableDeclarationExpr, Object javaParserFacade) {
//                                super.visit(variableDeclarationExpr, javaParserFacade);
////                                System.out.println("classDeclaration: " + classDeclaration.getName() + " variableDeclarationExpr: " + variableDeclarationExpr.toString());
////                                System.out.println("classDeclaration: " + classDeclaration.getName() + " variableDeclarationExpr: " + variableDeclarationExpr.getType());
////                                System.out.println("classDeclaration: " + classDeclaration.getName() + " variableDeclarationExpr: " + variableDeclarationExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString());
//                            }
//                        }, null);


//                        System.out.println("classDeclaration: " + classDeclaration.getNameAsString() + " Inner: " + classDeclaration.isInnerClass());

                        addClass(classDeclaration, javaParser, path);

//                        if (classDeclaration.isInterface()) {
//                            LegacyClass legacyClass = new LegacyClass();
//                            legacyClass.setClassOrInterfaceDeclaration(classDeclaration);
//                            legacyClass.setName(classDeclaration.getName().toString());
//                            legacyClass.setFieldDeclarations(getFieldList(classDeclaration, classDeclaration.getName(), javaParser));
//                            legacyClass.setMethodDeclarations(getMethodsList(classDeclaration, classDeclaration.getName(), javaParser));
////                            System.out.println("Inner Class is interface: " + classDeclaration.getNameAsString());
//                            LegacySystem.getInstance().addClass(legacyClass);
//
//                        } else {
//
//
//                        }

//                        if (classDeclaration.isInnerClass() || (classDeclaration.isNestedType() && !classDeclaration.isInterface())) {
//                            if (classDeclaration.isInterface()) {
//                                legacyClass.getInnerInterfacesList().add(classDeclaration);
////                    legacyClass.getInnerInterfacesList().add(getClassOrInterfaceDeclaration(classDeclaration, javaParserFacade));
//                            } else {
////                                System.out.println("classDeclaration: "+classDeclaration.isStatic());
//                                legacyClass.getInnerClassesList().add(getClassOrInterfaceDeclaration(classDeclaration, javaParser));
//                            }
//
////                System.out.println("ClassOrInterfaceDeclaration isInnerClass getParentNode: " + classDeclaration.getParentNode().getClass().getName());
//                        } else {
//                            legacyClass.setClassOrInterfaceDeclaration(classDeclaration);
//                            legacyClass.setName(classDeclaration.getName().toString());
//                            legacyClass.setFieldDeclarations(getFieldList(classDeclaration, classDeclaration.getName(), javaParser));
//                            legacyClass.setVariableDeclarationExprs(getVariableList(classDeclaration, classDeclaration.getName(), javaParser));
//                            legacyClass.setMethodDeclarations(getMethodsList(classDeclaration, classDeclaration.getName(), javaParser));
//                            legacyClass.setMethodCallExprs(getMethodsCallList(classDeclaration, classDeclaration.getName(), javaParser));
//                            legacyClass.setObjectCreationExprs(getObjectCreationList(classDeclaration, classDeclaration.getName(), javaParser));
//                            legacyClass.setConstructorDeclarations(getConstructorList(classDeclaration, classDeclaration.getName(), javaParser));
//                        }
                    }


                }.visit(JavaParser.parse(file), null);
//                 System.out.println("legacyClass: "+legacyClass.getName());
//                LegacySystem.getInstance().addClass(legacyClass);
//                System.out.println("End Visiting File"); // empty line
            } catch (ParseProblemException | IOException e) {
//                System.out.println("in catch "+e.getMessage());
                new RuntimeException(e);
            }
//             System.out.println("after catch");
        }).explore(projectDir);

//         System.out.println("after try catch");
//        return legacySystem;
    }


    private static void addClass(ClassOrInterfaceDeclaration classDeclaration, Object javaParser, String path) {
        if (classDeclaration.isInterface()) {
            LegacyClass legacyClass = new LegacyClass();
            legacyClass.setPath(path);
            legacyClass.setClassOrInterfaceDeclaration(classDeclaration);
            legacyClass.setName(classDeclaration.getName().toString());
            legacyClass.setFieldDeclarations(getFieldList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            legacyClass.setFieldAccessExprs((getFieldAccessExprList(classDeclaration, classDeclaration.getNameAsString(), javaParser)));
            legacyClass.setMethodDeclarations(getMethodsList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            LegacySystem.getInstance().addClass(legacyClass);
        } else {
            LegacyClass legacyClass = new LegacyClass();
            legacyClass.setPath(path);
            legacyClass.setClassOrInterfaceDeclaration(classDeclaration);
            legacyClass.setName(classDeclaration.getName().toString());
            legacyClass.setFieldDeclarations(getFieldList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            legacyClass.setFieldAccessExprs((getFieldAccessExprList(classDeclaration, classDeclaration.getNameAsString(), javaParser)));
            legacyClass.setVariableDeclarationExprs(getVariableList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            legacyClass.setVariableDeclarators((getVariableDeclarators(classDeclaration, classDeclaration.getNameAsString(), javaParser)));
            legacyClass.setMethodDeclarations(getMethodsList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            legacyClass.setMethodCallExprs(getMethodsCallList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            legacyClass.setObjectCreationExprs(getObjectCreationList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            legacyClass.setConstructorDeclarations(getConstructorList(classDeclaration, classDeclaration.getNameAsString(), javaParser));
            LegacySystem.getInstance().addClass(legacyClass);
        }
    }


//    private static LegacyClass getClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration classDeclaration, Object javaParser) {
//        LegacyClass legacyClass = new LegacyClass();
//        legacyClass.setClassOrInterfaceDeclaration(classDeclaration);
//        legacyClass.setName(classDeclaration.getName().toString());
//        legacyClass.setFieldDeclarations(getFieldList(classDeclaration, classDeclaration.getName(), javaParser));
//        legacyClass.setVariableDeclarationExprs(getVariableList(classDeclaration, classDeclaration.getName(), javaParser));
//        legacyClass.setMethodDeclarations(getMethodsList(classDeclaration, classDeclaration.getName(), javaParser));
//        legacyClass.setMethodCallExprs(getMethodsCallList(classDeclaration, classDeclaration.getName(), javaParser));
//        legacyClass.setObjectCreationExprs(getObjectCreationList(classDeclaration, classDeclaration.getName(), javaParser));
//        legacyClass.setConstructorDeclarations(getConstructorList(classDeclaration, classDeclaration.getName(), javaParser));
//
//        return legacyClass;
//    }

    private static List<FieldDeclaration> getFieldList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {

        List<FieldDeclaration> fieldDeclarations = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(FieldDeclaration fieldDeclaration, Object javaParserFacade) {
                super.visit(fieldDeclaration, javaParserFacade);
//                System.out.println("FieldDeclaration parent: " + fieldDeclaration.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getName() + " " + simpleName);
                if (fieldDeclaration.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName.toString())) {
//                    System.out.println("FieldDeclaration parser: " + fieldDeclaration.toString());
                    fieldDeclarations.add(fieldDeclaration);
                }
            }
        }, javaParserFacade);
        return fieldDeclarations;
    }

    private static List<FieldAccessExpr> getFieldAccessExprList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {

        List<FieldAccessExpr> fieldAccessExprs = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(FieldAccessExpr fieldAccessExpr, Object javaParserFacade) {
                super.visit(fieldAccessExpr, javaParserFacade);
//                System.out.println("FieldDeclaration parent: " + fieldDeclaration.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getName() + " " + simpleName);
                if (fieldAccessExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName.toString())) {
//                    System.out.println("FieldDeclaration parser: " + fieldDeclaration.toString());
                    fieldAccessExprs.add(fieldAccessExpr);
                }
            }
        }, javaParserFacade);
        return fieldAccessExprs;
    }

    private static List<VariableDeclarationExpr> getVariableList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {

        List<VariableDeclarationExpr> variableDeclarationExprs = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(VariableDeclarationExpr variableDeclarationExpr, Object javaParserFacade) {
                super.visit(variableDeclarationExpr, javaParserFacade);
                if (variableDeclarationExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName)) {
                    variableDeclarationExprs.add(variableDeclarationExpr);
                }
            }
        }, javaParserFacade);
        return variableDeclarationExprs;
    }

    private static List<VariableDeclarator> getVariableDeclarators(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {

        List<VariableDeclarator> variableDeclarators = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(VariableDeclarator variableDeclarationExpr, Object javaParserFacade) {
                super.visit(variableDeclarationExpr, javaParserFacade);
                if (variableDeclarationExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName)) {
                    variableDeclarators.add(variableDeclarationExpr);
                }
            }
        }, javaParserFacade);
        return variableDeclarators;
    }

    private static List<ConstructorDeclaration> getConstructorList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {
        List<ConstructorDeclaration> constructorDeclarations = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ConstructorDeclaration constructorDeclaration, Object javaParserFacade) {
                super.visit(constructorDeclaration, javaParserFacade);
                if (constructorDeclaration.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName)) {
                    constructorDeclarations.add(constructorDeclaration);
                }
            }
        }, javaParserFacade);

        return constructorDeclarations;
    }

    private static List<MethodDeclaration> getMethodsList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {
//        LegacyClass legacyClass= new LegacyClass();
//        legacyClass.setClassOrInterfaceDeclaration(declaration);
        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration methodDeclaration, Object javaParserFacade) {
                super.visit(methodDeclaration, javaParserFacade);
                if (methodDeclaration.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName)) {
                    methodDeclarations.add(methodDeclaration);
                }
            }
        }, javaParserFacade);

        return methodDeclarations;
    }

    private static List<MethodCallExpr> getMethodsCallList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {
//        LegacyClass legacyClass= new LegacyClass();
//        legacyClass.setClassOrInterfaceDeclaration(declaration);
        List<MethodCallExpr> methodCallExprs = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodCallExpr methodCallExpr, Object javaParserFacade) {
                super.visit(methodCallExpr, javaParserFacade);
//                if ()
                if (methodCallExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName)) {
                    methodCallExprs.add(methodCallExpr);
                }
            }
        }, javaParserFacade);

        return methodCallExprs;
    }

    private static List<ObjectCreationExpr> getObjectCreationList(ClassOrInterfaceDeclaration declaration, String simpleName, Object javaParserFacade) {
//        LegacyClass legacyClass= new LegacyClass();
//        legacyClass.setClassOrInterfaceDeclaration(declaration);
        List<ObjectCreationExpr> objectCreationExprs = new ArrayList<>();
        declaration.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ObjectCreationExpr objectCreationExpr, Object javaParserFacade) {
                super.visit(objectCreationExpr, javaParserFacade);
//                if ()
                if (objectCreationExpr.getAncestorOfType(ClassOrInterfaceDeclaration.class).get().getNameAsString().equals(simpleName)) {
                    objectCreationExprs.add(objectCreationExpr);
                }
            }
        }, javaParserFacade);

//        classDeclaration.accept(new VoidVisitorAdapter<Object>() {
//            @Override
//            public void visit(ObjectCreationExpr objectCreationExpr, Object javaParser) {
//                super.visit(objectCreationExpr, javaParser);
//
//                System.out.println("objectCreationExpr: " + objectCreationExpr.toString());
//                System.out.println("objectCreationExpr: " + objectCreationExpr.toString());
//                System.out.println("objectCreationExpr: " + objectCreationExpr.getType().toString());
//            }
//        }, null);

        return objectCreationExprs;
    }
}
