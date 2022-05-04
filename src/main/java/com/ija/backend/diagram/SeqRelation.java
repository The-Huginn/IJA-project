/**
 * @file SeqRelation.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains class SeqRelation
 */
package com.ija.backend.diagram;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.json.JSONObject;

import com.ija.backend.diagram.ClassRelation.ClassRelEnum;
import com.ija.backend.diagramObject.Method;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagramObject.UMLObject;
import com.ija.backend.diagramObject.Attribute.Visibility;
import javafx.util.Pair;

public class SeqRelation extends Relation{
    private SeqRelEnum type = SeqRelEnum.SYNCHROUNOUS;
    private String methodName = "Missing method name";
    private String methodParams = "Missing method params";
    private String note = "";

    public enum SeqRelEnum {
        SYNCHROUNOUS,
        ASYNCHRONOUS,
        RETURN,
        CREATION,
        DESTRUCTION
    }

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Pair<UMLObject, Integer>> undo_pair = new ArrayDeque<>();
    private Deque<Pair<String, String>> undo_method = new ArrayDeque<>();
    private Deque<SeqRelEnum> undo_type = new ArrayDeque<>();
    private Deque<String> undo_note = new ArrayDeque<>();

    private enum UndoType {
        setFirst,
        setSecond,
        setSecond2,
        setMethod,
        setNote,
        setType,
        others
    }

    /**
     * @param name
     * @param parent Under which parent this SeqRelation lives
     */
    public SeqRelation(String name, Diagram parent) {
        super(name, parent);
    }

    /**
     * @param name
     * @param parent Under which parent this SeqRelation lives
     * @param firstInstance SeqRelation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance SeqRelation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     */
    public SeqRelation(String name, Diagram parent, UMLClass firstInstance, int firstInstanceNumber, UMLClass secondInstance, int secondInstanceNumber) {
        super(name, parent, firstInstance, firstInstanceNumber, secondInstance, secondInstanceNumber);
    }

    public SeqRelation(String name, Diagram parent, UMLClass firstInstance, int firstInstanceNumber, UMLClass secondInstance, int secondInstanceNumber, SeqRelEnum type) {
        super(name, parent, firstInstance, firstInstanceNumber, secondInstance, secondInstanceNumber);
        this.type = type;
    }

    @Override
    public boolean equals(Object anotherObject) {

        if (anotherObject == this)
            return true;

        if (!(anotherObject instanceof ClassRelation))
            return false;

        SeqRelation object = (SeqRelation) anotherObject;

        return object.getName().equals(this.getName()) &&
                object.getFirst().getKey().equals(this.getFirst().getKey()) &&
                object.getFirst().getValue().equals(this.getFirst().getValue()) &&
                object.getSecond().getKey().equals(this.getSecond().getKey()) &&
                object.getSecond().getValue().equals(this.getSecond().getValue()) &&
                object.getType() == this.getType() &&
                object.getParent() == this.getParent();
    }

    @Override
    public boolean setName(String newName) {
        undo_stack.addFirst(UndoType.others);
        return super.setName(newName);
    }

    @Override
    public boolean checkCorrect() {
        return checkValidity((SeqDiagram) this.getParent(),
                            (UMLClass) this.getFirst().getKey(),
                            this.getFirst().getValue(),
                            (UMLClass) this.getSecond().getKey(),
                            this.getSecond().getValue(),
                            methodName,
                            methodParams,
                            getType());
    }

    /**
     * @note Checks, whether instance with it's instanceNumber exists in parent
     */
    @Override
    public boolean setFirst(UMLObject instance, Integer instanceNumber) {
        
        if (instance == null || (instance instanceof UMLInterface))
            return false;

        SeqDiagram parent = (SeqDiagram) this.getParent();

        // Just for test cases
        if (parent == null) {
            undo_stack.addFirst(UndoType.setFirst);
            undo_pair.addFirst(this.first);

            this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        for (Pair<UMLClass, Integer> instance1 : parent.getInstances()){
            if (instance1.getKey().equals(instance) && instance1.getValue() == instanceNumber) {

                undo_stack.addFirst(UndoType.setFirst);
                undo_pair.addFirst(this.first);

                this.first = new Pair<UMLObject,Integer>(instance, instanceNumber);
                return true;
            }
        }
        
        return false;      
    }

    /**
     * @note Checks, whether instance with it's instanceNumber exists in parent
     */
    @Override
    public boolean setSecond(UMLObject instance, Integer instanceNumber) {
        
        if (instance == null || (instance instanceof UMLInterface) || this.getFirst().getKey() == null)
            return false;

        SeqDiagram parent = (SeqDiagram) this.getParent();
        
        // Just for test cases
        if (parent == null) {
            undo_stack.addFirst(UndoType.setSecond);
            undo_pair.addFirst(this.second);

            this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
            return true;
        }

        for (Pair<UMLClass, Integer> instance1 : parent.getInstances())
            if (instance1.getKey().equals(instance) && instance1.getValue() == instanceNumber) {

                undo_stack.addFirst(UndoType.setSecond);
                undo_pair.addFirst(this.second);

                this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);    
                return true;
            }
        
        return false;
    }

    /**
     * @brief Checks if the combination of first, second and type of the relationship is correct
     * @param instance
     * @param instanceNumber
     * @param newType
     * @return True upon success otherwise false
     */
    public boolean setSecond(UMLObject instance, Integer instanceNumber, SeqRelEnum newType) {

        if (instance == null || (instance instanceof UMLInterface) || this.getFirst().getKey() == null)
            return false;

        SeqDiagram parent = (SeqDiagram) this.getParent();
        
        // Just for test cases
        if (parent == null) {
            undo_stack.addFirst(UndoType.setSecond2);
            undo_pair.addFirst(this.second);
            undo_type.addFirst(this.type);

            this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
            this.type = newType;
            return true;
        }

        boolean found = false;
        for (Pair<UMLClass, Integer> instance1 : parent.getInstances())
            if (instance1.getKey().equals(instance) && instance1.getValue() == instanceNumber) {
                this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
                found = true;
            }
        if (!found)
            return false;

        if (!checkValidity(parent,
                            (UMLClass)this.getFirst().getKey(),
                            this.getFirst().getValue(),
                            (UMLClass)this.getSecond().getKey(),
                            this.getSecond().getValue(),
                            this.methodName,
                            this.methodParams,
                            getType()))
            return false;

        undo_stack.addFirst(UndoType.setSecond2);
        undo_pair.addFirst(this.second);
        undo_type.addFirst(this.type);

        this.second = new Pair<UMLObject,Integer>(instance, instanceNumber);
        this.type = newType;

        return true;
    }

    /**
     * @return String with method invoking relation
     */
    public String getMethod() {
        return this.methodName;
    }

    /**
     * @return String with method call
     */
    public String getMethodString() {
        return this.methodName + "(" + this.methodParams + ")";
    }

    /**
     * @brief Accepts the method which invokes this relation, must be visible
     * @param methodCall
     * @return True if method was changed
     * @implNote No syntax analysis is made, operator "," cannot be passed into function
     */
    public boolean setMethod(String methodCall) {

        if (methodCall.charAt(methodCall.length() - 1) != ')' || methodCall.indexOf("(") == -1)
            return false;


        String auxMethodName = methodCall.substring(0, methodCall.indexOf("("));
        String auxParams = methodCall.substring(methodCall.indexOf("(") + 1).substring(0, methodCall.length() - auxMethodName.length() - 2);

        if (!checkValidity((SeqDiagram) this.getParent(),
                (UMLClass) this.getFirst().getKey(),
                this.getFirst().getValue(),
                (UMLClass) this.getSecond().getKey(),
                this.getSecond().getValue(),
                auxMethodName,
                auxParams,
                getType()))
            return false;

        undo_stack.addFirst(UndoType.setMethod);
        undo_method.addFirst(new Pair<String,String>(this.methodName, this.methodParams));

        this.methodName = auxMethodName;
        this.methodParams = auxParams;

        return true;
    }
    
    /**
     * @return String containing note
     */
    public String getNote() {
        return this.note;
    }

    /**
     * @param newNote
     */
    public void setNote(String newNote) {
        undo_stack.addFirst(UndoType.setNote);
        undo_note.addFirst(this.note);

        this.note = newNote;
    }

    /**
     * @return
     */
    public SeqRelEnum getType() {
        return this.type;
    }

    /**
     * @param newType
     */
    public boolean setType(SeqRelEnum newType) {
        undo_stack.addFirst(UndoType.setType);
        undo_type.addFirst(this.type);

        this.type = newType;
        return true;
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
        } else if (type == UndoType.setMethod) {
            Pair<String, String> top = undo_method.pop();

            this.methodName = top.getKey();
            this.methodParams = top.getValue();
        } else if (type == UndoType.setNote) {
            this.note = undo_note.pop();
        } else if (type == UndoType.setType) {
            this.type = undo_type.pop();
        } else if (type == UndoType.others) {
            super.undo();
        }
    }

    private static boolean checkValidity(   SeqDiagram parent,
                                            UMLClass firstInstance,
                                            Integer firstInstanceNumber,
                                            UMLClass secondInstance,
                                            Integer secondInstanceNumber,
                                            String methodName,
                                            String methodParams,
                                            SeqRelEnum type) {

        ClassDiagram grandParent = null;

        // Just for test cases
        if (parent != null) {
            grandParent = parent.getParent();

            // We check for instances
            Pair<Integer, Integer> occurences = new Pair<Integer,Integer>(0, 0);
            for (Pair<UMLClass, Integer> instance : parent.getInstances()) {
                
                if (instance.getKey().equals(firstInstance) && instance.getValue() == firstInstanceNumber)
                    occurences = new Pair<Integer,Integer>(occurences.getKey() + 1, occurences.getValue());

                if (instance.getKey().equals(secondInstance) && instance.getValue() == secondInstanceNumber)
                    occurences = new Pair<Integer,Integer>(occurences.getKey(), occurences.getValue() + 1);
            }

            if (occurences.getKey() != 1 || occurences.getValue() != 1)
                return false;
        }

        String parameters[] = methodParams.split(",");
        
        for (String param : parameters) {
            if (param.replaceAll(" ","").equals(""))
                if (parameters.length == 1)
                    parameters = new String[] {""};
                else 
                    return false;
        }

        int paramCount = parameters.length == 1 && parameters[0].length() == 0 ? 0 : parameters.length;
        
        if (type == SeqRelEnum.ASYNCHRONOUS || type == SeqRelEnum.SYNCHROUNOUS) {
            return findMethod(grandParent, firstInstance, methodName, paramCount);
        }

        return true;
    }

    /**
     * @brief This function checks if methodName with paramCount amount of parameters is inherited from another object
     * @param diagram
     * @param current
     * @param methodName
     * @param paramCount
     * @return True upon success otherwise false
     */
    private static boolean findMethod(ClassDiagram diagram, UMLObject current, String methodName, int paramCount) {

        for (Method method : current.getMethods())
            if (method.getName().equals(methodName) && method.getParameters().size() == paramCount)
                if (method.getVisibility() == Visibility.PRIVATE)
                    return false;
                else
                    return true;

        // Just for test cases
        if (diagram == null)
            return false;

        if (current instanceof UMLClass) {
            List<Relation> relations = diagram.getRelations();

            for (Relation relation : relations) {
                ClassRelation classRelation = (ClassRelation) relation;

                if (classRelation.getFirst().getKey().equals(current) &&
                    (classRelation.getType() == ClassRelEnum.GENERALIZATION || classRelation.getType() == ClassRelEnum.IMPLEMENTS))

                    // We check if it returns true otherwise we search further
                    if (findMethod(diagram, classRelation.getSecond().getKey(), methodName, paramCount))
                        return true;
            }
        }
        else {
            List<Relation> relations = diagram.getRelations();

            for (Relation relation : relations) {
                ClassRelation classRelation = (ClassRelation) relation;

                if (classRelation.getFirst().getKey().equals(current) && classRelation.getType() == ClassRelEnum.GENERALIZATION)
                    return findMethod(diagram, classRelation.getSecond().getKey(), methodName, paramCount);
            }
        }

        return false;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject json = super.getJSON();

        json.put("relationType", getType().ordinal());
        json.put("methodName", getMethod());
        json.put("methodParams", methodParams);
        json.put("note", getNote());

        return json;
    }

    @Override
    public boolean setJSON(JSONObject json) {

        if (!json.has("first") || !json.has("second") || !json.has("relationType") || !json.has("methodName") || !json.has("methodParams") || !json.has("note"))
            return false;

        JSONObject firstJSON = json.getJSONObject("first");
        JSONObject secondJSON = json.getJSONObject("second");

        if (!firstJSON.has("instance") || !firstJSON.has("instanceNumber"))
            return false;

        if (!secondJSON.has("instance") || !secondJSON.has("instanceNumber"))
            return false;

        ClassDiagram grandparent = ((SeqDiagram)getParent()).getParent();
        
        for (UMLClass class1 : grandparent.getClasses()) {
            if (class1.getName().equals(firstJSON.getString("instance")))
                first = new Pair<UMLObject,Integer>(class1, firstJSON.getInt("instanceNumber"));

            if (class1.getName().equals(secondJSON.getString("instance")))
                second = new Pair<UMLObject,Integer>(class1, secondJSON.getInt("instanceNumber"));
        }

        int typ = json.getInt("relationType");
        if (typ < 0 || typ >= SeqRelEnum.values().length)
            return false;

        type = SeqRelEnum.values()[typ];
        methodName = json.getString("methodName");
        methodParams = json.getString("methodParams");
        note = json.getString("note");

        return super.setJSON(json);
    }
}
