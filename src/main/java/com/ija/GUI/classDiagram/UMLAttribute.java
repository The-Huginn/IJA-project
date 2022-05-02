package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.Element;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class UMLAttribute extends UMLElement {
    private UMLEntity parent;
    private Label label;
    private Paint defaultPaint;
    
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
}
