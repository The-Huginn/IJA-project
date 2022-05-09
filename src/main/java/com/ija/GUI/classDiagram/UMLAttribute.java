/**
 * @file UMLAttribute.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains GUI representation of Attribute
 */
package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.Attribute;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UMLAttribute extends UMLElement {
    private Label label;
    
    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        others
    }

    public UMLAttribute(Attribute element, UMLEntity parent, ElementType type) {
        super(element, parent, type);

        label = new Label();
        label.setWrapText(true);
        label.setPrefHeight(30);

        getChildren().add(label);

        updateContent();
    }

    @Override
    public void select() {
        label.setStyle("-fx-text-fill: #c3de49;");;
    }

    @Override
    public void unselect() {
        label.setStyle(null);;
    }

    @Override
    public void updateContent() {
        label.setText(getElement().toString());
    }

    @Override
    public void addUndo() {
        undo_stack.addFirst(UndoType.others);
    }

    @Override
    public void undo() {
        if (undo_stack.isEmpty())
            return;
        
        super.undo();

        undo_stack.pop();
        updateContent();
    }

    @Override
    public void removeSelf(Pane fromPane) {
        if (getType() == ElementType.VARIABLE) {
            ((UMLEntity)getUMLParent()).removeVariable(this);
        } else {
            ((UMLEntity)getUMLParent()).removeMethod(this);
        }
    }

    @Override
    public void checkCorrect() {}
}
