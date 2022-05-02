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
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagram.Diagram;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;

import javafx.scene.control.Label;

public class cUMLDiagram extends UMLElement {
    private Label name;

    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<UMLEntity> undo_removes = new ArrayDeque<>();
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
    public void addNewRelation(cUMLRelation relation) {
        // TODO add to UMLdiagram
        relations.add(relation);
        undo_stack.addFirst(UndoType.addRelation);
    }

    public void removeRelation(cUMLRelation relation) {
        // TODO problem with removal of UMLObject
        relations.remove(relation);
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
            App.getTopLeft().getKey().intValue(),
            App.getTopLeft().getValue().intValue());

        undo_stack.addFirst(UndoType.addEntity);
        undo_removes.addFirst(newEntity);

        return true;
    }

    /**
     * @brief This function should be called upon adding new class in the editor
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
            App.getTopLeft().getKey().intValue(),
            App.getTopLeft().getValue().intValue());

        undo_stack.addFirst(UndoType.addEntity);
        undo_removes.addFirst(newEntity);

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
                undo_stack.addFirst(UndoType.removeEntity);
                undo_removes.addFirst(entity);
                App.getCurrentPane().getChildren().remove(entity);
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
                undo_stack.addFirst(UndoType.removeEntity);
                undo_removes.addFirst(entity);
                App.getCurrentPane().getChildren().remove(entity);
            }
        }
    }

    @Override
    public void select() {}

    @Override
    public void unselect() {}

    @Override
    public void updateContent() {
        name.setText(getElement().getName());
        // TODO repaint relations
    }

    @Override
    public void addUndo() {
        undo_stack.addFirst(UndoType.others);
        name.setText(getElement().getName());
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        super.undo();

        if (type == UndoType.removeEntity) {
            App.getCurrentPane().getChildren().add(undo_removes.pop());
        } else if (type == UndoType.addEntity) {
            App.getCurrentPane().getChildren().remove(undo_removes.pop());
        }
    }
}
