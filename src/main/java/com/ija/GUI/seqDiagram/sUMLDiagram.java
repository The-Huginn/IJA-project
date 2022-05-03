/**
 * @file sUMLDiagram.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains GUI class for Sequence Diagram representation.
 */
package com.ija.GUI.seqDiagram;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import com.ija.Application.App;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagramObject.UMLClass;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class sUMLDiagram extends UMLElement {
    private Label name;
    private HBox header;
    public static int SPACING = 50;

    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Pair<UMLInstance, Integer>> undo_removes = new ArrayDeque<>();
    private Deque<List<sUMLRelation>> undo_relations = new ArrayDeque<>();
    private List<sUMLRelation> relations = new ArrayList<>();

    private enum UndoType {
        addInstance,
        removeInstance,
        addRelation,
        removeRelation,
        others
    }

    public sUMLDiagram(SeqDiagram diagram, UMLElement parent, Label name, Pane parentPane) {
        super(diagram, parent, ElementType.SEQ_DIAGRAM);

        this.name = name;
        header = new HBox();
        header.setLayoutX(0);
        header.setLayoutY(0);
        header.setSpacing(SPACING);

        parentPane.getChildren().add(header);
    }

    /**
     * @brief Should be called upon adding an existing relation upon creation
     * @param relation
     */
    public void addRelation(sUMLRelation relation) {
        relations.add(relation);
    }

    public ObservableList<Node> getHeader() {
        return header.getChildren();
    }

    /**
     * Should be called upon adding new relation in the editor
     * @param relation
     */
    public void addNewRelation(sUMLRelation relation) {
        relations.add(relation);
        undo_stack.addFirst(UndoType.addRelation);
        List<sUMLRelation> list = new ArrayList<>();
        list.add(relation);
        undo_relations.addFirst(list);

        App.setSelected(this);
        App.addClearUndo();
        App.setSelected(relation);
    }

    public void removeRelation(sUMLRelation relation) {
        relations.remove(relation);
        undo_stack.addFirst(UndoType.removeRelation);
        List<sUMLRelation> list = new ArrayList<>();
        list.add(relation);
        undo_relations.addFirst(list);

        App.setSelected(this);
        App.addClearUndo();
    }

    public List<sUMLRelation> getRelations() {
        return Collections.unmodifiableList(relations);
    }

    /**
     * @brief This function should be called upon adding existing instance
     * @param name
     * @param y
     * @param x
     * @return
     */
    public boolean addInstance(UMLClass instance, int number) {
        UMLInstance newEntity = new UMLInstance(instance, this, number);

        header.getChildren().add(newEntity);

        return true;
    }

    /**
     * @brief This function should be called upon adding new instance in the editor
     * @param name
     * @param y
     * @param x
     * @return
     */
    public boolean addNewInstance(UMLClass instance, int number) {
        UMLInstance newEntity = new UMLInstance(instance, this, number);

        header.getChildren().add(newEntity);
        header.setLayoutX(0);
        header.setLayoutY(0);

        undo_stack.addFirst(UndoType.addInstance);
        undo_removes.addFirst(new Pair<UMLInstance,Integer>(newEntity, header.getChildren().indexOf(newEntity)));
        App.addClearUndo();

        return true;
    }

    /**
     * @brief Removes entity from the canvas
     * @param entity
     */
    public void removeInstance(UMLInstance entity) {

        SeqDiagram diagram = ((SeqDiagram)getElement());
        for (int i = 0; i < diagram.getInstances().size(); i++) {
            if (diagram.getInstances().get(i).getKey() == entity.getElement() &&
                diagram.getInstances().get(i).getValue() == entity.getInstanceNumber()) {

                diagram.removeInstance(i);
                removeEntityWithRels(entity);
            }
        }

        updateRelations();
    }

    private void removeEntityWithRels(UMLInstance entity) {

        List<sUMLRelation> remove_rels = new ArrayList<>();
        for (int i = 0; i < relations.size(); i++) {
            sUMLRelation rel = relations.get(i);

            if (((SeqRelation) rel.getElement()).getFirst().getKey() == entity.getElement() ||
                    ((SeqRelation) rel.getElement()).getSecond().getKey() == entity.getElement()) {
                remove_rels.add(rel);
                rel.removeFromPane(App.getCurrentPane());
                relations.remove(i);
                i--;
            }
        }

        undo_stack.addFirst(UndoType.removeInstance);
        undo_removes.addFirst(new Pair<UMLInstance,Integer>(entity, header.getChildren().indexOf(entity)));
        undo_relations.addFirst(remove_rels);

        header.getChildren().remove(entity);

        App.getCurrentPane().getChildren().remove(entity);
        App.setSelected(this);
        App.addClearUndo();
    }

    @Override
    public void select() {}

    @Override
    public void unselect() {}

    @Override
    public void updateContent() {
        name.setText(getElement().getName());
    }

    @Override
    public void addUndo() {
        undo_stack.addFirst(UndoType.others);
        updateContent();
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        super.undo();
        updateContent();

        if (type == UndoType.removeInstance) {
            Pair<UMLInstance, Integer> pair = undo_removes.pop();
            header.getChildren().add(pair.getValue(), pair.getKey());
            List<sUMLRelation> top = undo_relations.pop();

            for (sUMLRelation rel : top) {
                rel.addToPane(App.getCurrentPane());
                relations.add(rel);
            }
        } else if (type == UndoType.addInstance) {
            header.getChildren().remove(undo_removes.pop().getKey());
        } else if (type == UndoType.removeRelation) {
            sUMLRelation top = (undo_relations.pop()).get(0);
            top.addToPane(App.getCurrentPane());
            relations.add(top);
            
            updateRelations();
        } else if (type == UndoType.addRelation) {
            sUMLRelation top = (undo_relations.pop()).get(0);
            top.removeFromPane(App.getCurrentPane());
            relations.remove(top);
        }
    }

    private void updateRelations() {
        for (sUMLRelation rel : relations) {
            rel.updatePosition();
        }
    }
}
