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
import com.ija.GUI.MainWindowController;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.Diagram;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagramObject.UMLClass;

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
    private final Pane parentPane;

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
        this.parentPane = parentPane;
        header = new HBox();
        header.setLayoutX(0);
        header.setLayoutY(0);
        header.setSpacing(SPACING);
        header.toFront();

        parentPane.getChildren().add(header);
    }

    /**
     * @brief Calculates the offset of Instance on index place
     * @param index
     * @return
     */
    private int getX(int index) {
        return index * (sUMLDiagram.SPACING + UMLInstance.WIDTH) + UMLInstance.WIDTH / 2;
    }

    /**
     * @brief Should be called upon adding an existing relation upon creation
     * @param relation
     */
    public void addRelation(sUMLRelation relation) {
        relations.add(relation);
    }

    public HBox getHeader() {
        return header;
    }

    /**
     * Should be called upon adding new relation in the editor
     * @param relation
     */
    public boolean addNewRelation(SeqRelation relation) {

        if (!((SeqDiagram)getElement()).addRelation(relation))
            return false;

        double maxY = 0;
        for (sUMLRelation rel : relations) {
            maxY = Math.max(maxY, rel.getY());
        }
        maxY += sUMLRelation.OFFSET;

        sUMLRelation newRelation = new sUMLRelation(relation, App.getCurrentPane(), this, maxY);

        relations.add(newRelation);
        undo_stack.addFirst(UndoType.addRelation);
        List<sUMLRelation> list = new ArrayList<>();
        list.add(newRelation);
        undo_relations.addFirst(list);

        App.setSelected(this);
        App.addClearUndo();
        App.setSelected(newRelation);

        return true;
    }

    /**
     * Removes relation from the sequence diagram
     * @param relation
     */
    public void removeRelation(sUMLRelation relation) {
        SeqDiagram diagram = ((SeqDiagram)getElement());
        for (int i = 0; i < diagram.getRelations().size(); i++) {
            if (diagram.getRelations().get(i) == relation.getElement()) {
                diagram.removeRelation(i);
                break;
            }
        }

        relations.remove(relation);
        undo_stack.addFirst(UndoType.removeRelation);
        List<sUMLRelation> list = new ArrayList<>();
        list.add(relation);
        undo_relations.addFirst(list);

        App.setSelected(this);
        App.addClearUndo();
    }

    /**
     * @return
     */
    public List<sUMLRelation> getRelations() {
        return Collections.unmodifiableList(relations);
    }

    /**
     * @brief This function should be called upon adding existing instance
     * @param instance
     * @param name
     * @return
     */
    public boolean addInstance(UMLClass instance, int number) {
        new UMLInstance(instance, this, parentPane, number, getX(header.getChildren().size()));

        return true;
    }

    /**
     * @brief This function should be called upon adding new instance in the editor
     * @param instance
     * @param number
     * @return
     */
    public boolean addNewInstance(UMLClass instance, int number) {
        UMLInstance newEntity = new UMLInstance(instance, this, parentPane, number, getX(header.getChildren().size()));

        undo_stack.addFirst(UndoType.addInstance);
        undo_removes.addFirst(new Pair<UMLInstance,Integer>(newEntity, header.getChildren().indexOf(newEntity.getLabel())));
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

    /**
     * Removes the entity and all relations connected to it
     * @param entity
     */
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
        undo_removes.addFirst(new Pair<UMLInstance,Integer>(entity, header.getChildren().indexOf(entity.getLabel())));
        undo_relations.addFirst(remove_rels);

        entity.removeFromPane();

        App.setSelected(this);
        App.addClearUndo();
    }

    /**
     * Updates all relations - to repaint them
     */
    public void updateAll() {
        for (sUMLRelation rel : relations) {
            rel.updateContent();
        }
    }

    @Override
    public void select() {
        name.setStyle(null);
    }

    @Override
    public void unselect() {}

    @Override
    public void updateContent() {
        name.setText(getElement().getName());
    }

    @Override
    public void removeSelf(Pane newPane) {
        for (int i = 0; i < App.getClassDiagram().getDiagrams().size(); i++) {
            SeqDiagram var = App.getClassDiagram().getDiagrams().get(i);
            if (var.equals(getElement())) {
                App.getClassDiagram().removeDiagram(i);
                ((MainWindowController)App.getLoader().getController()).removeSeqDiagram((SeqDiagram)getElement());
                break;
            }
        }
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
            pair.getKey().addToPane(pair.getValue());
            List<sUMLRelation> top = undo_relations.pop();

            for (sUMLRelation rel : top) {
                rel.addToPane(App.getCurrentPane());
                relations.add(rel);
            }
        } else if (type == UndoType.addInstance) {
            undo_removes.pop().getKey().removeFromPane();
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

    @Override
    public void checkCorrect() {
        if (((Diagram)getElement()).checkCorrect())
            return;

        name.setStyle("-fx-text-fill: red;");

        for (sUMLRelation rel : relations) {
            rel.checkCorrect();
        }
    }

    /**
     * Updates positions of relations
     */
    private void updateRelations() {
        for (sUMLRelation rel : relations) {
            rel.updatePosition();
        }
    }
}
