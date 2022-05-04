/**
 * @file ClassRelation.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains class ClassRelation
 */
package com.ija.backend.diagram;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.json.JSONObject;

import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagramObject.UMLObject;
import javafx.util.Pair;

public class ClassRelation extends Relation{
    private ClassRelEnum type = ClassRelEnum.ASSOCIATION;
    private static String[] cardinalities = {"1", "0..1", "0..n", "1..n"};

    public enum ClassRelEnum {
        ASSOCIATION,
        AGGREGATION,
        COMPOSITION,
        GENERALIZATION,
        IMPLEMENTS
    }

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Pair<UMLObject, Integer>> undo_pair = new ArrayDeque<>();
    private Deque<ClassRelEnum> undo_type = new ArrayDeque<>();

    private enum UndoType {
        setFirst,
        setSecond,
        setSecond2,
        setType,
        others
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     */
    public ClassRelation(String name, Diagram parent) {
        super(name, parent);
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     * @param firstInstance ClassRelation starts from this UMLObject
     * @param firstClass instance number of the first UMLObject instance
     * @param secondInstance ClassRelation ends in this UMLObject
     * @param secondClass instance number of the second UMLObject instance
     */
    public ClassRelation(String name, Diagram parent, UMLObject firstClass, int firstInstance, UMLObject secondClass, int secondInstance) {
        super(name, parent, firstClass, firstInstance, secondClass, secondInstance);

        if ((firstClass instanceof UMLInterface) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.GENERALIZATION;

        if ((firstClass instanceof UMLClass) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.IMPLEMENTS;
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     * @param firstInstance ClassRelation starts from this UMLObject
     * @param firstClass instance number of the first UMLObject instance
     * @param secondInstance ClassRelation ends in this UMLObject
     * @param secondClass instance number of the second UMLObject instance
     * @param type Type of relations
     */
    public ClassRelation(String name, Diagram parent, UMLObject firstClass, int firstInstance, UMLObject secondClass, int secondInstance, ClassRelEnum type) {
        super(name, parent, firstClass, firstInstance, secondClass, secondInstance);
        this.type = type;

        // Overrides to correct type
        if ((firstClass instanceof UMLInterface) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.GENERALIZATION;

        // Overrides to correct type
        if ((firstClass instanceof UMLClass) && (secondClass instanceof UMLInterface))
            this.type = ClassRelEnum.IMPLEMENTS;
    }

    /**
     * @brief This function determinates what cardinality integer means
     * @param cardinality
     * @return String represantation of the cardinality
     */
    public static String getCardinality(int cardinality) {
        if (cardinality < 0 || cardinality >= cardinalities.length)
            return "unknown";

        return cardinalities[cardinality];
    }

    public static int getCardinality(String cardinality) {
        return Arrays.asList(cardinalities).indexOf(cardinality);
    }

    public static List<String> getCardinalities() {
        return Collections.unmodifiableList(Arrays.asList(cardinalities));
    }

    @Override
    public boolean setName(String newName) {
        undo_stack.addFirst(UndoType.others);
        return super.setName(newName);
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof ClassRelation))
            return false;

        ClassRelation object = (ClassRelation) anotherObject;

        return object.getName().equals(this.getName()) &&
                object.getFirst() == this.getFirst() &&
                object.getSecond() == this.getSecond() &&
                object.getType() == this.getType() &&
                object.getParent() == this.getParent();
    }

    @Override
    public boolean checkCorrect() {
        return checkValidity(this.getParent(), this.getFirst().getKey(), this.getSecond().getKey(), this.getType());
    }

    /**
     * @note Checks, whether instance exists in parent and if it's correct combination
     */
    @Override
    public boolean setFirst(UMLObject instance, Integer instanceNumber) {

        if (this.getSecond().getKey() == null) {
            undo_stack.addFirst(UndoType.setFirst);
            undo_pair.addFirst(this.first);

            this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        if (instance == null)
            return false;

        if (!checkValidity(this.getParent(), instance, this.getSecond().getKey(), this.getType()))
            return false;
    
        undo_stack.addFirst(UndoType.setFirst);
        undo_pair.addFirst(this.first);

        this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
    
        return true;
    }

    /**
     * @note Checks, whether instance exists in parent and if it's correct combination
     */
    @Override
    public boolean setSecond(UMLObject instance, Integer instanceNumber) {

        if (this.getFirst().getKey() == null || instance == null)
            return false;

        if (!checkValidity(this.getParent(), this.getFirst().getKey(), instance, this.getType()))
            return false;

        undo_stack.addFirst(UndoType.setSecond);
        undo_pair.addFirst(this.second);
        
        this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
        
        return true;
    }

    /**
     * @brief Checks if the combination of first, second and type of the relationship is correct
     * @param instance
     * @param instanceNumber
     * @param newType
     * @return True upon success otherwise false
     */
    public boolean setSecond(UMLObject instance, Integer instanceNumber, ClassRelEnum newType) {
        if (this.getFirst().getKey() == null || instance == null)
            return false;

        if (!checkValidity(this.getParent(), this.getFirst().getKey(), instance, newType))
            return false;

        undo_stack.addFirst(UndoType.setSecond2);
        undo_pair.addFirst(this.second);
        undo_type.addFirst(this.type);

        this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
        this.type = newType;

        return true;
    }

    /**
     * @return
     */
    public ClassRelEnum getType() {
        return this.type;
    }

    /**
     * @param newType
     * @return
     */
    public boolean setType(ClassRelEnum newType) {

        if (this.getFirst().getKey() == null || this.getSecond().getKey() == null) {
            undo_stack.addFirst(UndoType.setType);
            undo_type.addFirst(this.type);

            this.type = newType;
            return true;
        }

        if (checkValidity(this.getParent(), this.getFirst().getKey(), this.getSecond().getKey(), newType)){
            undo_stack.addFirst(UndoType.setType);
            undo_type.addFirst(this.type);

            this.type = newType;
            return true;
        }

        return false;
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.setFirst) {
            this.first = undo_pair.pop();
        } else if (type == UndoType.setSecond) {
            this.second = undo_pair.pop();
        } else if (type == UndoType.setSecond2) {
            this.second = undo_pair.pop();
            this.type = undo_type.pop();
        } else if (type == UndoType.setType) {
            this.type = undo_type.pop();
        } else if (type == UndoType.others) {
            super.undo();
        }
    }

    /**
     * @brief This function checks for validity of the combination of arguments
     * @note For test cases we allow currentParent to be null
     * @param currentParent
     * @param firstInstance
     * @param secondInstance
     * @param type
     * @return
     */
    private static boolean checkValidity(   Diagram currentParent,
                                            UMLObject firstInstance,
                                            UMLObject secondInstance,
                                            ClassRelEnum type) {

        if (currentParent != null)
            if (!(currentParent instanceof ClassDiagram))
                return false;

        ClassDiagram parent = (ClassDiagram) currentParent;

        // Class to Class relationship
        if ((firstInstance instanceof UMLClass) && (secondInstance instanceof UMLClass)) {
            
            if (parent != null)
                if (!parent.getClasses().contains(firstInstance) || !parent.getClasses().contains(secondInstance))
                    return false;

            if (! Arrays.asList(ClassRelEnum.AGGREGATION, ClassRelEnum.ASSOCIATION, ClassRelEnum.COMPOSITION, ClassRelEnum.GENERALIZATION).contains(type))
                return false;
        }
        // Class to Interface relationship
        else if ((firstInstance instanceof UMLClass) && (secondInstance instanceof UMLInterface)) {
            if (parent != null)
                if (!parent.getClasses().contains(firstInstance) || !parent.getInterfaces().contains(secondInstance))
                    return false;

            if (! Arrays.asList(ClassRelEnum.IMPLEMENTS).contains(type))
                return false;
        }
        // Interface to Interface relationship
        else if ((firstInstance instanceof UMLInterface) && (secondInstance instanceof UMLInterface)) {
            if (parent != null)
                if (!parent.getInterfaces().contains(firstInstance) || !parent.getInterfaces().contains(secondInstance))
                    return false;

            if (! Arrays.asList(ClassRelEnum.GENERALIZATION).contains(type))
                return false;
        }
        // Interface to Class relationship
        else
            return false;

        return true;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject json = super.getJSON();

        json.put("relationType", getType().ordinal());

        return json;
    }

    @Override
    public boolean setJSON(JSONObject json) {

        if (!json.has("first") || !json.has("second") || !json.has("relationType"))
            return false;

        JSONObject firstJSON = json.getJSONObject("first");
        JSONObject secondJSON = json.getJSONObject("second");

        if (!firstJSON.has("instance") || !firstJSON.has("instanceNumber"))
            return false;

        if (!secondJSON.has("instance") || !secondJSON.has("instanceNumber"))
            return false;

        ClassDiagram grandparent = (ClassDiagram)getParent();

        for (UMLClass class1 : grandparent.getClasses()) {
            if (class1.getName().equals(firstJSON.getString("instance")))
                first = new Pair<UMLObject,Integer>(class1, firstJSON.getInt("instanceNumber"));

            if (class1.getName().equals(secondJSON.getString("instance")))
                second = new Pair<UMLObject,Integer>(class1, secondJSON.getInt("instanceNumber"));
        }

        int typ = json.getInt("relationType");
        if (typ < 0 || typ >= ClassRelEnum.values().length)
            return false;

        type = ClassRelEnum.values()[typ];

        return super.setJSON(json);
    }
}
