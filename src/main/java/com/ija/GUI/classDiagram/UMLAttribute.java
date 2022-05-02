package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.Element;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class UMLAttribute extends UMLElement {
    private Label label;
    private Paint defaultPaint;
    private final UMLEntity parent;
    
    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        others
    }

    public UMLAttribute(Element element, ElementType type, UMLEntity parent) {
        super(element, type);

        this.parent = parent;
        label = new Label();
        label.setWrapText(true);
        label.setPrefHeight(30);
        defaultPaint = label.getTextFill();

        getChildren().add(label);

        updateContent();
    }

    @Override
    public void select() {
        label.setTextFill(Color.YELLOW);
    }

    @Override
    public void unselect() {
        label.setTextFill(defaultPaint);
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

        undo_stack.pop();
        updateContent();
    }

    public void deleteMe() {
        if (getType() == ElementType.VARIABLE) {
            parent.removeVariable(this);
        } else {
            parent.removeMethod(this);
        }
    }
}
