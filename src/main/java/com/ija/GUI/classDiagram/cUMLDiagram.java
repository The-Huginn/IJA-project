/**
 * @file cUMLDiagram.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains GUI class for Class Diagram representation.
 */
package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import com.ija.Application.App;
import com.ija.GUI.MainWindowController;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.Diagram;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class cUMLDiagram extends UMLElement {
    private Label name;

    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<UMLEntity> undo_removes = new ArrayDeque<>();
    private Deque<List<cUMLRelation>> undo_relations = new ArrayDeque<>();
    private List<cUMLRelation> relations = new ArrayList<>();

    private enum UndoType {
        addEntity,
        removeEntity,
        addRelation,
        removeRelation,
        others
    }

    public cUMLDiagram(Diagram diagram, Label name) {
        super(diagram, null, ElementType.CLASS_DIAGRAM);

        this.name = name;
    }

    /**
     * @brief Should be called upon adding an existing relation upon creation
     * @param relation
     */
    public void addRelation(cUMLRelation relation) {
        relations.add(relation);
    }

    /**
     * Should be called upon adding new relation in the editor
     * @param relation
     */
    public boolean addNewRelation(ClassRelation relation) {
        if (!App.getClassDiagram().addRelation(relation))
            return false;

        UMLEntity starting = ((UMLEntity) App.getSelected());

        double y = starting.getLayoutY() + ((UMLEntity) App.getSelected()).getHeight() / 2;
        double x = starting.getLayoutX() + ((UMLEntity) App.getSelected()).getWidth() / 2;

        cUMLRelation newRelation = new cUMLRelation(relation, App.getCurrentPane(), this, y, x);

        relations.add(newRelation);
        undo_stack.addFirst(UndoType.addRelation);
        List<cUMLRelation> list = new ArrayList<>();
        list.add(newRelation);
        undo_relations.addFirst(list);

        App.setSelected(this);
        App.addClearUndo();
        App.setSelected(newRelation);

        for (Node node : App.getCurrentPane().getChildren()) {
            if (!(node instanceof UMLEntity))
                continue;

            UMLEntity entity = (UMLEntity) node;
            if (relation.getSecond().getKey() == entity.getElement()) {
                entity.updateRelation(newRelation);
            }
        }
        starting.updateRelations();

        return true;
    }

    public void removeRelation(cUMLRelation relation) {
        ClassDiagram diagram = ((ClassDiagram)getElement());
        for (int i = 0; i < diagram.getRelations().size(); i++) {
            if (diagram.getRelations().get(i) == relation.getElement()) {
                diagram.removeRelation(i);
                break;
            }
        }

        relations.remove(relation);
        undo_stack.addFirst(UndoType.removeRelation);
        List<cUMLRelation> list = new ArrayList<>();
        list.add(relation);
        undo_relations.addFirst(list);

        App.setSelected(this);
        App.addClearUndo();
    }

    public List<cUMLRelation> getRelations() {
        return Collections.unmodifiableList(relations);
    }

    /**
     * @brief This function should be called upon adding new class in the editor
     * @param name
     * @param y
     * @param x
     * @return
     */
    public boolean addClass(String name) {
        UMLClass var = new UMLClass(name, App.getClassDiagram());

        if (!((ClassDiagram)getElement()).addClass(var))
            return false;

        UMLEntity newEntity = new UMLEntity(var,
            App.getCurrentPane(),
            this,
            ElementType.CLASS,
            App.getTopLeft().getKey().intValue(),
            App.getTopLeft().getValue().intValue());

        undo_stack.addFirst(UndoType.addEntity);
        undo_removes.addFirst(newEntity);
        App.addClearUndo();

        return true;
    }

    /**
     * @brief This function should be called upon adding new interface in the editor
     * @param name
     * @param y
     * @param x
     * @return
     */
    public boolean addInterface(String name) {
        UMLInterface var = new UMLInterface(name, App.getClassDiagram());

        if (!((ClassDiagram)getElement()).addInterface(var))
            return false;

        UMLEntity newEntity = new UMLEntity(var,
            App.getCurrentPane(),
            this,
            ElementType.INTERFACE,
            App.getTopLeft().getKey().intValue(),
            App.getTopLeft().getValue().intValue());

        undo_stack.addFirst(UndoType.addEntity);
        undo_removes.addFirst(newEntity);
        App.addClearUndo();

        return true;
    }

    /**
     * @brief Removes entity from the canvas
     * @param entity
     */
    public void removeClass(UMLEntity entity) {

        ClassDiagram diagram = ((ClassDiagram)getElement());
        for (int i = 0; i < diagram.getClasses().size(); i++) {
            if (diagram.getClasses().get(i) == entity.getElement()) {
                diagram.removeClass(i);
                removeEntityWithRels(entity);
            }
        }
    }

    /**
     * @brief Removes entity from the canvas
     * @param entity
     */
    public void removeInterface(UMLEntity entity) {

        ClassDiagram diagram = ((ClassDiagram)getElement());
        for (int i = 0; i < diagram.getInterfaces().size(); i++) {
            if (diagram.getInterfaces().get(i) == entity.getElement()) {
                diagram.removeInterface(i);
                removeEntityWithRels(entity);
            }
        }
    }

    private void removeEntityWithRels(UMLEntity entity) {

        List<cUMLRelation> remove_rels = new ArrayList<>();
        for (int i = 0; i < relations.size(); i++) {
            cUMLRelation rel = relations.get(i);

            if (((ClassRelation) rel.getElement()).getFirst().getKey() == entity.getElement() ||
                    ((ClassRelation) rel.getElement()).getSecond().getKey() == entity.getElement()) {
                remove_rels.add(rel);
                rel.removeFromPane(App.getCurrentPane());
                relations.remove(i);
                i--;
            }
        }

        undo_stack.addFirst(UndoType.removeEntity);
        undo_removes.addFirst(entity);
        undo_relations.addFirst(remove_rels);
        App.getCurrentPane().getChildren().remove(entity);
        App.setSelected(this);
        App.addClearUndo();
    }

    public void updateAll() {
        for (Node node : App.getCurrentPane().getChildren()) {
            if (!(node instanceof UMLEntity))
                continue;

            ((UMLEntity)node).updateRelations();
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
    public void removeSelf(Pane fromPane) {
        ((MainWindowController)App.getLoader().getController()).removeClassDiagram();
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

        if (type == UndoType.removeEntity) {
            App.getCurrentPane().getChildren().add(undo_removes.pop());
            List<cUMLRelation> top = undo_relations.pop();

            for (cUMLRelation rel : top) {
                rel.addToPane(App.getCurrentPane());
                relations.add(rel);
            }
        } else if (type == UndoType.addEntity) {
            App.getCurrentPane().getChildren().remove(undo_removes.pop());
        } else if (type == UndoType.removeRelation) {
            cUMLRelation top = (undo_relations.pop()).get(0);
            top.addToPane(App.getCurrentPane());
            relations.add(top);
        } else if (type == UndoType.addRelation) {
            cUMLRelation top = (undo_relations.pop()).get(0);
            top.removeFromPane(App.getCurrentPane());
            relations.remove(top);
        }
    }

    @Override
    public void checkCorrect() {
        if (((Diagram)getElement()).checkCorrect())
            return;

        name.setStyle("-fx-text-fill: red;");

        for (cUMLRelation rel : relations) {
            rel.checkCorrect();
        }
    }
}
