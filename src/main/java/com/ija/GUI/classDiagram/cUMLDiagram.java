/**
 * @file cUMLDiagram.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains GUI class for Class Diagram representation.
 */
package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.Diagram;

import javafx.scene.control.Label;

public class cUMLDiagram extends UMLElement {
    private Label name;

    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        others
    }

    public cUMLDiagram(Diagram diagram, Label name) {
        super(diagram, null, ElementType.CLASS_DIAGRAM);

        this.name = name;
    }

    @Override
    public void select() {}

    @Override
    public void unselect() {}

    @Override
    public void updateContent() {}

    @Override
    public void addUndo() {
        undo_stack.addFirst(UndoType.others);
        name.setText(getElement().getName());
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        undo_stack.pop();

        super.undo();
        name.setText(getElement().getName());
    }
}
