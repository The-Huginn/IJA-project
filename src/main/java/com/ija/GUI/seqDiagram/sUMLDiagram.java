/**
 * @file sUMLDiagram.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains GUI class for Sequence Diagram representation.
 */
package com.ija.GUI.seqDiagram;

import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.Diagram;

import javafx.scene.control.Label;

public class sUMLDiagram extends UMLElement {
    private Label name;

    private Deque<UndoType> undo_stack = new ArrayDeque<>();

    private enum UndoType {
        others
    }

    public sUMLDiagram(Diagram diagram, Label name) {
        super(diagram, ElementType.SEQ_DIAGRAM);

        this.name = name;
    }

    @Override
    public void select() {}

    @Override
    public void unselect() {}

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
