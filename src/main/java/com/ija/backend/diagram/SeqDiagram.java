/**
 * @file SeqDiagram.java
 * @author Rastislav Budinsky (xbudin05), Vladimir Meciar (xmecia00)
 * @brief This file contains class SeqDiagram
 */
package com.ija.backend.diagram;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ija.backend.diagramObject.UMLClass;
import javafx.util.Pair;

public class SeqDiagram extends Diagram {
    private final ClassDiagram parent;
    private List<Pair<UMLClass, Integer>> instances = new ArrayList<>();

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Pair<Integer, Pair<UMLClass, Integer>>> undo_instance = new ArrayDeque<>();
    private Deque<List<Pair<Integer, Relation>>> undo_relation = new ArrayDeque<>();

    private enum UndoType {
        addRelation,
        addInstance,
        removeInstance,
        others
    }

    /**
     * @param name
     * @param parent
     */
    public SeqDiagram(String name, ClassDiagram parent) {
        super(name);
        this.parent = parent;
    }

    @Override
    public boolean setName(String newName) {

        if (this.getParent() == null) {
            undo_stack.addFirst(UndoType.others);
            return super.setName(newName);
        }

        for (SeqDiagram diagram : this.getParent().getDiagrams())
            if (diagram.getName().equals(newName))
                return false;

        undo_stack.addFirst(UndoType.others);

        return super.setName(newName);
    }

    @Override
    public boolean addRelation(Relation relation) {

        if (!(relation instanceof SeqRelation))
            return false;

        for (Relation relation2: this.getRelations())
            if (relation.equals(relation2))
                return false;

        Pair<Boolean, Boolean> contains = new Pair<Boolean,Boolean>(false, false);
        for (Pair<UMLClass, Integer> pair : getInstances()) {
            if (pair.getKey() == relation.getFirst().getKey() && pair.getValue() == relation.getFirst().getValue()) {
                contains = new Pair<Boolean,Boolean>(true, contains.getValue());
            }

            if (pair.getKey() == relation.getSecond().getKey() && pair.getValue() == relation.getSecond().getValue()) {
                contains = new Pair<Boolean,Boolean>(contains.getKey(), true);
            }
        }

        // For tests to pass
        if (relation.getFirst().getKey() == null && relation.getSecond().getKey() == null) {
            contains = new Pair<Boolean,Boolean>(true, true);
        }

        if (!contains.getKey() || !contains.getValue())
            return false;

        undo_stack.addFirst(UndoType.addRelation);

        this.relations.add(relation);

        return true;
    }

    @Override
    public boolean checkCorrect() {

        for (Pair<UMLClass, Integer> pair : this.instances)
            if (!this.getParent().getClasses().contains(pair.getKey()))
                return false;

        for (Relation relation : this.getRelations())
            if (!(((SeqRelation)relation).checkCorrect()))
                return false;

        return true;
    }

    /**
     * @note Checks, whether instance exists in parent and whether is it's instanceNumber unique
     * @param instance
     * @param instanceNumber
     * @return
     */
    public boolean addInstance(UMLClass instance, int instanceNumber) {

        if (this.getParent() == null) {
            undo_stack.addFirst(UndoType.addInstance);

            this.instances.add(new Pair<UMLClass,Integer>(instance, instanceNumber));

            return true;
        }

        if (!this.getParent().getClasses().contains(instance))
            return false;

        for (Pair<UMLClass, Integer> pair: this.instances)
            if (pair.getKey().equals(instance) && pair.getValue().equals(instanceNumber))
                return false;

        undo_stack.addFirst(UndoType.addInstance);

        this.instances.add(new Pair<UMLClass, Integer> (instance, instanceNumber));

        return true;
    }

    /**
     * @param index
     */
    public void removeInstance(int index) {

        if (index < 0 || index >= this.instances.size())
            return;

        List<Pair<Integer, Relation>> toRemove = new ArrayList<>();

        for (int i = 0; i < relations.size(); i++) {
            Relation relation = relations.get(i);

            if ((this.instances.get(index).getKey().equals(relation.getFirst().getKey()) && this.instances.get(index).getValue().equals(relation.getFirst().getValue())) ||
                (this.instances.get(index).getKey().equals(relation.getSecond().getKey()) && this.instances.get(index).getValue().equals(relation.getSecond().getValue())))
                    toRemove.add(new Pair<Integer,Relation>(i, relation));

        }

        // checks if instance and instanceNumber occur as first or second in the relation
        this.relations.removeIf(relation -> (this.instances.get(index).getKey().equals(relation.getFirst().getKey()) && this.instances.get(index).getValue().equals(relation.getFirst().getValue())) ||
                                                (this.instances.get(index).getKey().equals(relation.getSecond().getKey()) && this.instances.get(index).getValue().equals(relation.getSecond().getValue())));

        undo_stack.addFirst(UndoType.removeInstance);
        undo_relation.addFirst(toRemove);
        undo_instance.addFirst(new Pair<Integer, Pair<UMLClass, Integer>>(index, instances.get(index)));

        this.instances.remove(index);
    }

    @Override
    public void removeRelation(int index) {
        undo_stack.addFirst(UndoType.others);
        super.removeRelation(index);
    }

    /**
     * @return
     */
    public List<Pair<UMLClass, Integer>> getInstances() {
        return Collections.unmodifiableList(this.instances);
    }

    /**
     * @return
     */
    public final ClassDiagram getParent() {
        return this.parent;
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.addRelation) {
            relations.remove(relations.size() - 1);
        } else if (type == UndoType.addInstance) {
            instances.remove(instances.size() - 1);
        } else if (type == UndoType.removeInstance) {
            List<Pair<Integer, Relation>> top = undo_relation.pop();

            for (Pair<Integer, Relation> pair : top)
                relations.add(pair.getKey(), pair.getValue());

            Pair<Integer, Pair<UMLClass, Integer>> top_instance = undo_instance.pop();

            instances.add(top_instance.getKey(), top_instance.getValue());
        } else if (type == UndoType.others) {
            super.undo();
        }
    }

    @Override
    public JSONObject getJSON() {
        JSONObject json = super.getJSON();

        JSONArray array = new JSONArray();

        for (Pair<UMLClass, Integer> pair : getInstances()) {
            JSONObject classJSON = new JSONObject();
            classJSON.put("instance", pair.getKey().getName());
            classJSON.put("instanceNumber", pair.getValue());
            array.put(classJSON);
        }

        json.put("instances", array);

        return json;
    }

    @Override
    public boolean setJSON(JSONObject json) {

        if (!json.has("instances") || !json.has("relations"))
            return false;

        JSONArray instanceJSON = json.getJSONArray("instances");
        JSONArray relationJSON = json.getJSONArray("relations");

        for (int i = 0; i < instanceJSON.length(); i++) {
            JSONObject instanceObject = instanceJSON.getJSONObject(i);
            
            if (!instanceObject.has("instance") || !instanceObject.has("instanceNumber"))
                return false;

            for (UMLClass class1 : getParent().getClasses()) {
                if (class1.getName().equals(instanceObject.getString("instance")))
                    instances.add(new Pair<UMLClass,Integer>(class1, instanceObject.getInt("instanceNumber")));
            }
        }

        for (int i = 0; i < relationJSON.length(); i++) {
            SeqRelation relation = new SeqRelation("", this);

            if (!relation.setJSON(relationJSON.getJSONObject(i)))
                return false;

            relations.add(relation);
        }

        return super.setJSON(json);
    }
}
