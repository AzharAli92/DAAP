package com.daap.model;

import com.daap.util.Helper;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALI on 8/15/2017.
 */

public class LegacyClass {
    private String name;
    private String path;
    private String parentClass;
    private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;
    private List<FieldDeclaration> fieldDeclarations;   // instance variables only
    private List<FieldAccessExpr> fieldAccessExprs;
    private List<VariableDeclarationExpr> variableDeclarationExprs;  // only local variables declared in methods
    private List<VariableDeclarator> variableDeclarators;    // all fields + variables
    private List<ObjectCreationExpr> objectCreationExprs;
    private List<MethodDeclaration> methodDeclarations;
    private List<MethodCallExpr> methodCallExprs;
    private List<ConstructorDeclaration> constructorDeclarations;
    //    private List<LegacyClass> innerClassesList;
    private List<ClassOrInterfaceDeclaration> innerInterfacesList;
    //    private HashMap<String, Integer> fieldsMap;
//    private HashMap<String, Integer> methodsMap;
//    private HashMap<String, Integer> constructorsMap;

    private Relations relations;
    private Relations allRelations;

    private boolean isCloneable;
    private boolean isClosable;

    public LegacyClass() {
        this.fieldDeclarations = new ArrayList<>();
        this.fieldAccessExprs = new ArrayList<>();
        this.variableDeclarationExprs = new ArrayList<>();
        this.variableDeclarationExprs = new ArrayList<>();
        this.variableDeclarators = new ArrayList<>();
        this.methodDeclarations = new ArrayList<>();
        this.methodCallExprs = new ArrayList<>();
        this.constructorDeclarations = new ArrayList<>();
        this.objectCreationExprs = new ArrayList<>();
//        this.innerClassesList = new ArrayList<>();
        this.innerInterfacesList = new ArrayList<>();

//        fieldsMap = new HashMap<>();
//        methodsMap = new HashMap<>();
//        constructorsMap = new HashMap<>();

        relations = new Relations();
        allRelations = new Relations();

        isCloneable = false;
        isClosable = false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAllRelations(Relations allRelations) {
        this.allRelations = allRelations;
    }

    public Relations getAllRelations() {
        return allRelations;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public String getParentClass() {
        return parentClass;
    }

    public void setCloneable(boolean cloneable) {
        isCloneable = cloneable;
    }

    public boolean isCloneable() {
        return isCloneable;
    }

    public void setClosable(boolean closable) {
        isClosable = closable;
    }

    public boolean isClosable() {
        return isClosable;
    }

    //  Class
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ClassOrInterfaceDeclaration getClassOrInterfaceDeclaration() {
        return classOrInterfaceDeclaration;
    }

    public void setClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        this.classOrInterfaceDeclaration = classOrInterfaceDeclaration;
    }
// End Class


    public void setFieldAccessExprs(List<FieldAccessExpr> fieldAccessExprs) {
        this.fieldAccessExprs = fieldAccessExprs;
    }

    public List<FieldAccessExpr> getFieldAccessExprs() {
        return fieldAccessExprs;
    }

    public void setRelations(Relations relations) {
        this.relations = relations;
    }

    public Relations getRelations() {
        return relations;
    }

    public int totalRelations() {
        return relations.size();
    }

    public void setObjectCreationExprs(List<ObjectCreationExpr> objectCreationExprs) {
        this.objectCreationExprs = objectCreationExprs;
    }

    public List<ObjectCreationExpr> getObjectCreationExprs() {
        return objectCreationExprs;
    }

    //    objectCreationExprs
    public ObjectCreationExpr getObjectCreation(String type) {
        for (ObjectCreationExpr objectCreationExpr : objectCreationExprs) {
            if (objectCreationExpr.getType().toString().equals(type)) {
                return objectCreationExpr;
            }
        }
//        Integer pos = fieldsMap.get(fieldName);
//        if (pos != null)
//            return getField(pos);
//        else
//            return null;
        return null;
    }

    public List<VariableDeclarator> getVariableDeclarators() {
        return variableDeclarators;
    }

    public void setVariableDeclarators(List<VariableDeclarator> variableDeclarators) {
        this.variableDeclarators = variableDeclarators;
    }

    public VariableDeclarator getVariableDeclarator(String vaiableName) {
        for (VariableDeclarator variableDeclarator : variableDeclarators) {
            if (variableDeclarator.getNameAsString().equals(vaiableName)) {
                return variableDeclarator;
            }
        }
//        Integer pos = fieldsMap.get(fieldName);
//        if (pos != null)
//            return getField(pos);
//        else
//            return null;
        return null;
    }

    public void setVariableDeclarationExprs(List<VariableDeclarationExpr> variableDeclarationExprs) {
        this.variableDeclarationExprs = variableDeclarationExprs;
    }

    public List<VariableDeclarationExpr> getVariableDeclarationExprs() {
        return variableDeclarationExprs;
    }

    public VariableDeclarationExpr getVariable(String vaiableName) {
        for (VariableDeclarationExpr variableDeclarationExpr : variableDeclarationExprs) {
            if (variableDeclarationExpr.getVariable(0).toString().equals(vaiableName)) {
                return variableDeclarationExpr;
            }
        }
//        Integer pos = fieldsMap.get(fieldName);
//        if (pos != null)
//            return getField(pos);
//        else
//            return null;
        return null;
    }

    //    Fields
    public List<FieldDeclaration> getFieldDeclarations() {
        return fieldDeclarations;
    }

    public void setFieldDeclarations(List<FieldDeclaration> fieldDeclarations) {
        this.fieldDeclarations = fieldDeclarations;
//        for (FieldDeclaration declaration : fieldDeclarations) {
//            this.addField(declaration);
//        }
    }

    public void addField(FieldDeclaration declaration) {
//        fieldsMap.put(declaration.getVariables().get(0).getNameAsString(), fieldDeclarations.size());
        fieldDeclarations.add(declaration);
    }

    public FieldDeclaration getField(String fieldName) {
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            if (fieldDeclaration.getVariable(0).toString().equals(fieldName)) {
                return fieldDeclaration;
            }
        }
//        Integer pos = fieldsMap.get(fieldName);
//        if (pos != null)
//            return getField(pos);
//        else
//            return null;
        return null;
    }

//    public FieldDeclaration getField(int pos) {
//        return fieldDeclarations.get(pos);
//    }
//    End Fields

    //    methods

    public List<MethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    public int totalMethods() {
        return methodDeclarations.size();
    }

    public void setMethodDeclarations(List<MethodDeclaration> methodDeclarations) {
        this.methodDeclarations = methodDeclarations;
//        this.methodDeclarations = new ArrayList<>();
//        for (MethodDeclaration declaration : methodDeclarations) {
//            this.addMethod(declaration);
//        }
    }

    public void addMethod(MethodDeclaration methodDeclaration) {
//        this.methodsMap.put(methodDeclaration.getNameAsString(), this.methodDeclarations.size());
        this.methodDeclarations.add(methodDeclaration);
    }

    public MethodDeclaration getMethod(String methodName) {

        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            if (methodDeclaration.getNameAsString().equals(methodName)) {
                return methodDeclaration;
            }
        }
//        Integer pos = methodsMap.get(methodName);
//        if (pos != null)
//            return getMethod(pos);
//        else
//            return null;
        return null;
    }

    public MethodDeclaration getMethod(int pos) {
        return methodDeclarations.get(pos);
    }
//    end methods

    //  Constructor
    public List<ConstructorDeclaration> getConstructorDeclarations() {
        return constructorDeclarations;
    }

    public void setConstructorDeclarations(List<ConstructorDeclaration> constructorDeclarations) {

        this.constructorDeclarations = constructorDeclarations;
//        this.constructorDeclarations = new ArrayList<>();
//        for (ConstructorDeclaration declaration : constructorDeclarations) {
//            this.addConstructor(declaration);
//        }
    }

    public void addConstructor(ConstructorDeclaration declaration) {
//        constructorsMap.put(declaration.getNameAsString(), constructorDeclarations.size());
        constructorDeclarations.add(declaration);
    }

    public ConstructorDeclaration getConstructorDeclaration(String constructorName) {
        for (ConstructorDeclaration constructorDeclaration : constructorDeclarations) {
            if (constructorDeclaration.getNameAsString().equals(constructorName)) {
                return constructorDeclaration;
            }
        }
//        Integer pos = constructorsMap.get(ConstructorName);
//        if (pos != null)
//            return getConstructor(pos);
//        else
//            return null;
        return null;
    }

    public ConstructorDeclaration getConstructor(int pos) {
        return constructorDeclarations.get(pos);
    }

    //  End Constructor

    public void setInnerInterfacesList(List<ClassOrInterfaceDeclaration> innerInterfacesList) {
        this.innerInterfacesList = innerInterfacesList;
    }

//    public void setInnerClassesList(List<LegacyClass> innerClassesList) {
//        this.innerClassesList = innerClassesList;
//    }
//    public List<LegacyClass> getInnerClassesList() {
//        return innerClassesList;
//    }

    public List<ClassOrInterfaceDeclaration> getInnerInterfacesList() {
        return innerInterfacesList;
    }


    public void setMethodCallExprs(List<MethodCallExpr> methodCallExprs) {
        this.methodCallExprs = methodCallExprs;
    }

    public List<MethodCallExpr> getMethodCallExprs() {
        return methodCallExprs;
    }

    public MethodCallExpr getMethodCallExpr(String methodName) {
        for (MethodCallExpr methodCallExpr : methodCallExprs) {
            if (methodCallExpr.getNameAsString().equals(methodName)) {
                return methodCallExpr;
            }
        }
//        Integer pos = constructorsMap.get(ConstructorName);
//        if (pos != null)
//            return getConstructor(pos);
//        else
//            return null;
        return null;
    }

    @Override
    public String toString() {
        return "LegacyClass{" +
                "name='" + name + '\'' +
                ", parentClass='" + parentClass + '\'' +
                ", classOrInterfaceDeclaration=" + classOrInterfaceDeclaration +
                ", fieldDeclarations=" + fieldDeclarations +
                ", fieldAccessExprs=" + fieldAccessExprs +
                ", variableDeclarationExprs=" + variableDeclarationExprs +
                ", variableDeclarators=" + variableDeclarators +
                ", objectCreationExprs=" + objectCreationExprs +
                ", methodDeclarations=" + methodDeclarations +
                ", methodCallExprs=" + methodCallExprs +
                ", constructorDeclarations=" + constructorDeclarations +
                ", innerInterfacesList=" + innerInterfacesList +
                ", relations=" + relations +
                ", isCloneable=" + isCloneable +
                '}';
    }


    // ------------------------------------ Uses ----------------------------------------

    /**
     * This function finds all the use relations starting from this ClassObject.
     * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
     * use relations are saved in both a static arraylist containing all use relations(parser.ProjectASTParser.Uses)
     * and a ClassObject specific arraylist(Uses)
     */
    public void findUses() {
        Relation relation;
        boolean alreadyAdded = false;
        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            if (!(Helper.isTypePrimitive(methodDeclaration.getType().toString()))) {
                LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(methodDeclaration.getType().toString());
                if (legacyClass != null) {
                    for (Relation h : relations.getRelationsByType(Relation.Type.has)) {
                        if (h.getTo().getName().equalsIgnoreCase(legacyClass.getName())) {
                            alreadyAdded = true;
                        }
                    }
                    relation = new Relation(this, legacyClass, Relation.Type.uses);
                    allRelations.add(relation);
                    if (!alreadyAdded) {
                        relations.add(relation);
                        LegacySystem.getInstance().getUsesRelations().add(relation);
                        LegacySystem.getInstance().getRelations().add(relation);
                    }
                }
            }
        }
    }

    /**
     * Returns true if this ClassObject uses the input ClassObject c,
     * returns false otherwise
     */
    public boolean uses(LegacyClass c) {
        for (Relation u : relations.getRelationsByType(Relation.Type.uses)) {
            if (u.getTo().equals(c))
                return true;
        }
        return false;
    }

    // ------------------------------------ Uses ----------------------------------------


    // ------------------------------------ Inherits ----------------------------------------

    /**
     * This function finds all the inherit relations starting from this ClassObject.
     * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
     * inh relations are saved in both a static arraylist containing all inh
     * relations(parser.ProjectASTParser.Inherits)
     * and a ClassObject specific arraylist(Inherits)
     */
    public void findInherits() {
//        System.out.println("Parents: " + this.getClassOrInterfaceDeclaration().getExtendedTypes().toString());
        NodeList<ClassOrInterfaceType> extendedTypes = this.getClassOrInterfaceDeclaration().getExtendedTypes();
        if (!Helper.isNullOrEmpty(extendedTypes)) {
            String parentClass = extendedTypes.get(0).getNameAsString();
            addInherit(parentClass);
            this.setParentClass(parentClass);
        }
        NodeList<ClassOrInterfaceType> implementedTypes = this.getClassOrInterfaceDeclaration().getImplementedTypes();
        if (!Helper.isNullOrEmpty(implementedTypes)) {
            for (ClassOrInterfaceType classOrInterfaceType : implementedTypes) {
                addInherit(classOrInterfaceType.getNameAsString());
            }
        }
    }


    private void addInherit(String parentClass) {
//        System.out.println("legacyClass parent class: " + parentClass.toString());
//        Cloneable
        if (parentClass.equals("Cloneable")) {
            this.setCloneable(true);
//            System.out.println("Cloneable legacyClass: " + this.toString());
            return;
        }else if (parentClass.equals("Closeable")) {
            this.setClosable(true);
//            System.out.println("Cloneable legacyClass: " + this.toString());
            return;
        }

        Relation relation;
        boolean alreadyAdded = false;
        LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(parentClass);
        if (legacyClass != null) {
            if (!alreadyAdded && !legacyClass.getName().equals(this.getName())) {
                if (legacyClass != null) {
                    relation = new Relation(this, legacyClass, Relation.Type.inherits);
                    allRelations.add(relation);
                    relations.add(relation);
                    LegacySystem.getInstance().getInheritsRelations().add(relation);
                    LegacySystem.getInstance().getRelations().add(relation);
                }
            }
        }
    }

    /**
     * Returns true if this ClassObject inherits the input ClassObject c,
     * returns false otherwise
     */
    public boolean inherits(LegacyClass c) {
        for (Relation i : relations.getRelationsByType(Relation.Type.inherits)) {
            if (i.getTo().equals(c))
                return true;
        }
        return false;
    }

    // ------------------------------------ Inherits ----------------------------------------


    // ------------------------------------ Has ----------------------------------------

    /**
     * This function finds all the has relations starting from this ClassObject.
     * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
     * has relations are saved in both a static arraylist containing all has relations(parser.ProjectASTParser.Has)
     * and a ClassObject specific arraylist(Has)
     */
    public void findHas() {
        Relation relation;
//        int flag;
        boolean alreadyAdded = false;
        String fieldType;
//        System.out.println("FieldDeclaration: "+fieldDeclarations.size());

//        for (FieldDeclaration declaration : fieldDeclarations) {
//            fieldType = declaration.getCommonType().toString();
//            if (LegacySystem.getInstance().getClassNameMap().containsKey(fieldType)) {
//                // Check for duplicates (one has is enough)
//                for (Relation h : relations.getRelationsByType(Relation.Type.has)) {
//                    if (h.getTo().getName().equalsIgnoreCase(fieldType)) {
//                        alreadyAdded = true;
//                    }
//                }
//                // End of duplicate checking
//                LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(fieldType);
//                if (!alreadyAdded && !legacyClass.getName().equals(this.getName())) {
//                    if (legacyClass != null) {
//                        temphas = new Relation(this, legacyClass, Relation.Type.has);
//                        relations.add(temphas);
//                    }
//                }
//            }
//        }

        for (VariableDeclarator declaration : variableDeclarators) {
            fieldType = declaration.getType().toString();
            if (LegacySystem.getInstance().getClassNameMap().containsKey(fieldType)) {
                // Check for duplicates (one has is enough)

                for (Relation h : relations.getRelationsByType(Relation.Type.has)) {
                    if (h.getTo().getName().equalsIgnoreCase(fieldType)) {
                        alreadyAdded = true;
                    }
                }
                // End of duplicate checking
                LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(fieldType);
//                LegacySystem.getInstance().getAllRelations().add(new Relation(this, legacyClass, Relation.Type.has));

                relation = new Relation(this, legacyClass, Relation.Type.has);
                allRelations.add(relation);
                if (!alreadyAdded && !legacyClass.getName().equals(this.getName())) {
                    if (legacyClass != null) {
                        relations.add(relation);
                        LegacySystem.getInstance().getHasRelations().add(relation);
                        LegacySystem.getInstance().getRelations().add(relation);
                    }
                }
            }
        }
    }

    /**
     * Returns true if this ClassObject has the input ClassObject c,
     * returns false otherwise
     */
    public boolean has(LegacyClass c) {
        for (Relation h : relations.getRelationsByType(Relation.Type.has)) {
            if (h.getTo().equals(c))
                return true;
        }
        return false;
    }

    // ------------------------------------ Has ----------------------------------------

//    // ------------------------------------ Calls ----------------------------------------

    /**
     * This function finds all the call relations starting from this ClassObject.
     * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
     * call relations are saved in both a static arraylist containing all call
     * relations(parser.ProjectASTParser.Calls)
     * and a ClassObject specific arraylist(Calls)
     */
    public void findCalls() {
        Relation relation;
        boolean alreadyAdded = false;
        for (MethodCallExpr methodCallExpr : methodCallExprs) {

            for (LegacyClass legacyClass : LegacySystem.getInstance().getAllClasses()) {

                for (MethodDeclaration methodDeclaration : legacyClass.getMethodDeclarations()) {
                    if (methodCallExpr.getNameAsString().equals(methodDeclaration.getNameAsString()) && methodCallExpr.getArguments().size() == methodDeclaration.getParameters().size()) {

                        for (Relation ca : relations.getRelationsByType(Relation.Type.calls)) {
                            if (ca.getTo().getName().equalsIgnoreCase(legacyClass.getName())) {
                                alreadyAdded = true;
                                break;
                            }
                        }
//                        System.out.println("asdfasdfasf");
                        relation = new Relation(this, legacyClass, Relation.Type.calls);
                        allRelations.add(relation);
                        if (!alreadyAdded && !legacyClass.getName().equals(this.getName())) {
                            relations.add(relation);
                            LegacySystem.getInstance().getCallsRelations().add(relation);
                            LegacySystem.getInstance().getRelations().add(relation);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns true if this ClassObject calls the input ClassObject c,
     * returns false otherwise
     */
    public boolean calls(LegacyClass c) {
        for (Relation cll : relations.getRelationsByType(Relation.Type.calls)) {
            if (cll.getTo().equals(c))
                return true;
        }
        return false;
    }

    public Relation getCallConnectionTo(LegacyClass c) {
        for (Relation cll : relations.getRelationsByType(Relation.Type.calls)) {
            if (cll.getTo().equals(c))
                return cll;
        }
        return null;
    }

    // ------------------------------------ Calls ----------------------------------------

    // ------------------------------------ Creates ----------------------------------------

    /**
     * This function finds all the create relations starting from this ClassObject.
     * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
     * create relations are saved in both a static arraylist containing all create
     * relations(parser.ProjectASTParser.Creates)
     * and a ClassObject specific arraylist(Creates)
     */
    public void findCreates() {
        Relation relation;
        boolean alreadyAdded = false;

        for (ObjectCreationExpr objectCreationExpr : objectCreationExprs) {
            LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(objectCreationExpr.getType().asString());
            if (legacyClass != null) {
                for (Relation c : relations.getRelationsByType(Relation.Type.creates)) {
                    if (c.getTo().getName().equalsIgnoreCase(objectCreationExpr.getType().asString()))
                        alreadyAdded = true;
                }
                // End of duplicate checking
//                if (!alreadyAdded && !legacyClass.getName().equals(this.getName())) {
                relation = new Relation(this, legacyClass, Relation.Type.creates);
                allRelations.add(relation);
                if (!alreadyAdded) {
                    relations.add(relation);
                    LegacySystem.getInstance().getCreatesRelations().add(relation);
                    LegacySystem.getInstance().getRelations().add(relation);
                }
            }
        }

    }

    /**
     * Returns true if this ClassObject creates the input ClassObject c,
     * returns false otherwise
     */
    public boolean creates(LegacyClass c) {
        for (Relation cr : relations.getRelationsByType(Relation.Type.creates)) {
            if (cr.getTo().equals(c))
                return true;
        }
        return false;
    }

    // ------------------------------------ Creates ----------------------------------------


    // ------------------------------------ References ----------------------------------------

    /**
     * This function finds all the reference relations starting from this ClassObject.
     * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
     * ref relations are saved in both a static arraylist containing all ref
     * relations(parser.ProjectASTParser.References)
     * and a ClassObject specific arraylist(References)
     */
    public void findReferences() {
        Relation relation;

        boolean alreadyAdded = false;

        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            for (Parameter parameter : methodDeclaration.getParameters()) {
                if (!(Helper.isTypePrimitive(parameter.getType().asString()))) {
                    for (Relation r : relations.getRelationsByType(Relation.Type.references)) {
                        if (r.getTo().getName().equalsIgnoreCase(parameter.getType().asString())) {
                            alreadyAdded = true;
                        }
                    }
                    // End of duplicate checking
                    // If it is not duplicate (flag is 0)
                    LegacyClass legacyClass = LegacySystem.getInstance().getLegacyClass(parameter.getType().asString());

                    relation = new Relation(this, legacyClass, Relation.Type.references);
                    allRelations.add(relation);
                    if (!alreadyAdded && legacyClass != null && !legacyClass.getName().equals(this.getName())) {
                        relations.add(relation);
                        LegacySystem.getInstance().getReferencesRelations().add(relation);
                        LegacySystem.getInstance().getRelations().add(relation);

                    }
                }
            }
        }


//        for (Method m : Methods) {
//            for (String s : m.getInputtypes()) {
//                flag = 0;
//                if (!(GeneralMethods.isPrimitive(s))) {
//                    // Check for duplicates (one reference is enough)
//                    for (Relation r : relations.getRelationsByType(Type.references)) {
//                        if (r.getTo().getName().equalsIgnoreCase(s))
//                            flag = 1;
//                    }
//                    // End of duplicate checking
//                    // If it is not duplicate (flag is 0)
//                    if (flag == 0) {
//                        tempref = new Relation(this, ProjectASTParser.Classes.get(s), Relation.Type.references);
//                        relations.add(tempref);
//                    }
//                }
//            }
//        }
    }

    /**
     * Returns true if this ClassObject references the input ClassObject c,
     * returns false otherwise
     */
    public boolean references(LegacyClass c) {
        for (Relation r : relations.getRelationsByType(Relation.Type.references)) {
            if (r.getTo().equals(c))
                return true;
        }
        return false;
    }
    // ------------------------------------ References ----------------------------------------

    public boolean relates(LegacyClass c) {
        for (Relation relation : relations) {
            if (relation.getTo().equals(c))
                return true;
        }
        return false;
    }


//    Legacy class utility methods

    public boolean isContainingClone() {
        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            if (methodDeclaration.getNameAsString().equals("clone") && methodDeclaration.getParameters().isEmpty()) {
                methodDeclaration.accept(new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ObjectCreationExpr n, Object arg) {
                        super.visit(n, arg);
                        if (n.getType().asString().equals(LegacyClass.this.getName())) {
                            isCloneable = true;
                            return;
                        }
                    }
                }, null);
//                return true;
            }
        }
        return isCloneable;
    }

    public boolean isCallingClone() {
        for (MethodCallExpr methodCallExpr : methodCallExprs) {
            if (methodCallExpr.getNameAsString().equals("clone") && methodCallExpr.getArguments().isEmpty()) {
                return true;
            }
        }
        return isCloneable;
    }
}
